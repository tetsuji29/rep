package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * DBアクセスするアクションの抽象クラス。
 */
public abstract class AbstractDBAction extends ActionSupport implements
		SessionAware {

	/**
	 * シリアルバージョンUID。
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 現在のユーザを保持するセッション変数を示します。
	 */
	protected static final String SESSION_CURRENT_USER = "currentUser";

	/**
	 * コネクションプール。
	 */
	private Connection _pooledConnection;

	/**
	 * エラーメッセージを保持します。
	 */
	private String _errorMessage;

	/**
	 * セッション情報を保持します。
	 */
	private Map<String, Object> _session;

	/**
	 * 構築します。
	 */
	public AbstractDBAction() {
		_pooledConnection = null;
		_errorMessage = null;
		_session = null;
	}

	/**
	 * エラーメッセージを取得します。
	 *
	 * @return
	 */
	public String getErrorMessage() {
		return _errorMessage;
	}

	/**
	 * セッション情報を保存します。
	 */
	@Override
	public void setSession(Map<String, Object> session) {
		_session = session;
	}

	/**
	 * 現在のユーザを取得します。
	 *
	 * @return
	 */
	public User getCurrentUser() {
		return (User) _session.get(SESSION_CURRENT_USER);
	}

	/**
	 * 現在のユーザを設定します。
	 *
	 * @param user
	 */
	protected void setCurrentUser(User user) {
		_session.put(SESSION_CURRENT_USER, user);
	}

	/**
	 * エラーを表示します。
	 *
	 * @param errorMessage
	 * @return
	 */
	protected String showError(String errorMessage) {
		_errorMessage = errorMessage;

		return "error";
	}

	/**
	 * 接続オブジェクトを生成します。
	 *
	 * @return
	 * @throws SQLException
	 */
	protected Connection getConnection() throws SQLException {
		// Connectionの準備
		if (_pooledConnection != null) {
			return _pooledConnection;
		}
		try {
			// 下準備
			Class.forName("org.h2.Driver");
			_pooledConnection = DriverManager.getConnection(
					"jdbc:h2:tcp://localhost/~/test", "sa", "");
			return _pooledConnection;
		} catch (ClassNotFoundException e) {
			_pooledConnection = null;
			throw new SQLException(e);
		} catch (SQLException e) {
			_pooledConnection = null;
			throw e;
		}
	}

	/**
	 * ユーザを取得します。
	 *
	 * @param userID
	 * @return
	 * @throws ServletException
	 */
	protected User queryUser(String userID) throws SQLException {
		Statement statement = null;
		try {
			// SQL文を発行
			statement = getConnection().createStatement();
			ResultSet resultSet = statement
					.executeQuery("SELECT ID,NAME FROM TODO_USER WHERE ID='"
							+ userID + "'");
			boolean br = resultSet.first();
			if (br == false) {
				return null;
			}
			User user = new User();
			user.setId(resultSet.getString("ID"));
			user.setName(resultSet.getString("NAME"));

			return user;
		} catch (SQLException e) {
			_pooledConnection = null;
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
				statement = null;
			}
		}
	}

	/**
	 * ユーザ一覧を取得します。
	 *
	 * @return
	 * @throws ServletException
	 */
	protected User[] queryUsers() throws SQLException {
		Statement statement = null;
		try {
			// SQL文を発行
			statement = getConnection().createStatement();
			ResultSet resultSet = statement
					.executeQuery("SELECT ID,NAME FROM TODO_USER");
			boolean br = resultSet.first();
			if (br == false) {
				return new User[0];
			}
			ArrayList<User> users = new ArrayList<User>();
			do {
				User user = new User();
				user.setId(resultSet.getString("ID"));
				user.setName(resultSet.getString("NAME"));
				users.add(user);
			} while (resultSet.next());

			return users.toArray(new User[0]);
		} catch (SQLException e) {
			_pooledConnection = null;
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
				statement = null;
			}
		}
	}

	/**
	 * INSERT/UPDATE/DELETE文を実行します。
	 *
	 * @param sql
	 * @return
	 * @throws ServletException
	 */
	protected int executeUpdate(String sql) throws SQLException {
		Statement statement = null;
		try {
			// SQL文を発行
			statement = getConnection().createStatement();
			int updateCount = statement.executeUpdate(sql);

			return updateCount;
		} catch (SQLException e) {
			_pooledConnection = null;
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
				statement = null;
			}
		}
	}

	/**
	 * アイテム取得用のSQL文を生成します。
	 *
	 * @param where
	 * @return
	 */
	protected String createSQL(String where) {
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT TODO_ITEM.ID,TODO_ITEM.NAME,TODO_USER.ID,TODO_USER.NAME,EXPIRE_DATE,FINISHED_DATE FROM TODO_USER,TODO_ITEM WHERE TODO_USER.ID=TODO_ITEM.USER");
		if (where != null) {
			buf.append(" AND ");
			buf.append(where);
		}
		return buf.toString();
	}

	/**
	 * 更新用のSQL文を生成します。
	 *
	 * @param targetItem
	 * @return
	 */
	protected String createUpdateSQL(Item targetItem) {
		StringBuffer buf = new StringBuffer();
		buf.append("UPDATE ");
		buf.append("TODO_ITEM");
		buf.append(" SET ");
		buf.append("NAME='");
		buf.append(targetItem.getName());
		buf.append("', ");
		buf.append("EXPIRE_DATE='");
		buf.append(targetItem.getExpireDate().toString());
		buf.append("', ");
		buf.append("USER='");
		buf.append(targetItem.getUser().getId());
		buf.append("', ");
		buf.append("FINISHED_DATE=");
		if (targetItem.getFinishedDate() != null) {
			buf.append("'");
			buf.append(targetItem.getFinishedDate().toString());
			buf.append("'");
		} else {
			buf.append("null");
		}
		buf.append(" WHERE ID='");
		buf.append(targetItem.getId());
		buf.append("'");

		return buf.toString();
	}

	/**
	 * アイテムを取得します。
	 *
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	protected Item queryItem(String id) throws SQLException {
		Statement statement = null;
		try {
			// SQL文を発行
			statement = getConnection().createStatement();
			ResultSet resultSet = statement
					.executeQuery(createSQL("TODO_ITEM.ID='" + id + "'"));
			boolean br = resultSet.first();
			if (br == false) {
				return null;
			}
			Item item = new Item();
			item.setId(resultSet.getString("TODO_ITEM.ID"));
			item.setName(resultSet.getString("TODO_ITEM.NAME"));
			User user = new User();
			user.setId(resultSet.getString("TODO_USER.ID"));
			user.setName(resultSet.getString("TODO_USER.NAME"));
			item.setUser(user);
			item.setExpireDate(resultSet.getDate("EXPIRE_DATE"));
			item.setFinishedDate(resultSet.getDate("FINISHED_DATE"));

			return item;
		} catch (SQLException e) {
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
				statement = null;
			}
		}
	}

}
