package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;

/**
 * ログイン処理。
 */
public class LoginAction extends AbstractDBAction {

	/**
	 * シリアルバージョンUID。
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ユーザIDを保持します。
	 */
	private String _userID;

	/**
	 * パスワードを保持します。
	 */
	private String _password;

	/**
	 * 構築します。
	 */
	public LoginAction() {
		_userID = null;
		_password = null;
	}

	/**
	 * ユーザIDを設定します。
	 * @param userId
	 */
	public void setUser_id(String userID) {
		_userID = userID;
	}

	/**
	 * パスワードを設定します。
	 * @param password
	 */
	public void setPassword(String password) {
		_password = password;
	}

	/**
	 * 実行します。
	 */
	public String show() {
		return "success";
	}

	/**
	 * 実行します。
	 */
	@Override
	public String execute() {
		try{
			User user = queryUser();
			if(user == null) {
				return showError("ユーザIDもしくはパスワードが不正です。");
			}

			setCurrentUser(user);
			return "success";
		}catch(SQLException e) {
			return showError(e.getMessage());
		}
	}

	/**
	 * ユーザを取得します。
	 *
	 * @return
	 * @throws ServletException
	 */
	private User queryUser() throws SQLException {
		Statement statement = null;
		try {
			// パラメータのチェック
			if(_userID == null) {
				return null;
			}
			if(_password == null) {
				return null;
			}
			if(isValidUserID(_userID) == false) {
				return null;
			}
			if(isValidPassword(_password) == false) {
				return null;
			}

			// SQL文を発行
			statement = getConnection().createStatement();
			ResultSet resultSet = statement
					.executeQuery("SELECT ID,NAME FROM TODO_USER WHERE ID='"
							+ _userID + "' AND PASSWORD='" + _password + "'");
			boolean br = resultSet.first();
			if (br == false) {
				return null;
			}
			User user = new User();
			user.setId(resultSet.getString("ID"));
			user.setName(resultSet.getString("NAME"));

			return user;
		} catch (SQLException e) {
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
				statement = null;
			}
		}
	}

	/**
	 * 有効なユーザIDかどうかを判定します。
	 * @param name
	 * @return
	 */
	private boolean isValidUserID(String name) {
		return isAlphaOrDigit(name);
	}

	/**
	 * 有効なパスワードかどうかを判定します。
	 * @param password
	 * @return
	 */
	private boolean isValidPassword(String password) {
		return isAlphaOrDigit(password);
	}

	/**
	 * 文字列が半角英数字から構成されているかどうかを判定します。
	 * @param str
	 * @return
	 */
	private boolean isAlphaOrDigit(String str) {
		for(int i = 0; i < str.length(); i ++) {
			char ch = str.charAt(i);
			if(isAlphaOrDigit(ch) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 文字が半角英数字かどうかを判定します。
	 * @param ch
	 * @return
	 */
	private boolean isAlphaOrDigit(char ch) {
		if('A' <= ch && ch <= 'Z') {
			return true;
		}
		if('a' <= ch && ch <= 'z') {
			return true;
		}
		if('0' <= ch && ch <= '9') {
			return true;
		}
		return false;
	}

}
