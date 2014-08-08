package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

import javax.servlet.ServletException;

/**
 * 検索をおこなうアクション。
 */
public class SearchAction extends AbstractDBAction {

	/**
	 * シリアルバージョンUID。
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * アイテムを保持します。
	 */
	private Item[] _items;

	/**
	 * キーワードを保持します。
	 */
	private String _keyword;

	/**
	 * 構築します。
	 */
	public SearchAction() {
		_items = null;
		_keyword = null;
	}

	/**
	 * アイテムの一覧を取得します。
	 *
	 * @return
	 */
	public Item[] getItems() {
		return _items;
	}

	/**
	 * キーワードを設定します。
	 *
	 * @param keyword
	 */
	public void setKeyword(String keyword) {
		_keyword = keyword;
	}

	/**
	 * キーワードを取得します。
	 *
	 * @return
	 */
	public String getKeyword() {
		return _keyword;
	}

	/**
	 * 現在時刻を取得します。
	 *
	 * @return
	 */
	public long getCurrentTime() {
		// 現在時刻を取得(期限比較用: 分以降は0にリセット)
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		long currentTime = calendar.getTimeInMillis();
		return currentTime;
	}

	/**
	 * 実行します。
	 */
	public String show() {
		try {
			_items = queryItems();

			return "success";
		} catch (SQLException e) {
			return showError(e.getMessage());
		}
	}

	/**
	 * アイテムを検索します。
	 *
	 * @return
	 * @throws ServletException
	 */
	private Item[] queryItems() throws SQLException {
		String regExp = createRegExp(_keyword);
		if (regExp == null) {
			return new Item[0];
		}
		StringBuffer where = new StringBuffer();
		String[] fields = new String[] { "TODO_ITEM.NAME",
				"TODO_ITEM.EXPIRE_DATE", "TODO_USER.NAME", "TODO_USER.ID",
				"TODO_ITEM.FINISHED_DATE" };
		for (int i = 0; i < fields.length; i++) {
			String field = fields[i];
			if (where.length() > 0) {
				where.append(" OR ");
			}
			where.append(field);
			where.append(" REGEXP '");
			where.append(regExp);
			where.append("'");
		}

		Statement statement = null;
		try {
			// SQL文を発行
			statement = getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(createSQL(" ("
					+ where.toString() + ")"));
			boolean br = resultSet.first();
			if (br == false) {
				return new Item[0];
			}
			ArrayList<Item> items = new ArrayList<Item>();
			do {
				Item item = new Item();
				item.setId(resultSet.getString("TODO_ITEM.ID"));
				item.setName(resultSet.getString("TODO_ITEM.NAME"));
				User user = new User();
				user.setId(resultSet.getString("TODO_USER.ID"));
				user.setName(resultSet.getString("TODO_USER.NAME"));
				item.setUser(user);
				item.setExpireDate(resultSet.getDate("EXPIRE_DATE"));
				item.setFinishedDate(resultSet.getDate("FINISHED_DATE"));

				items.add(item);
			} while (resultSet.next());

			return items.toArray(new Item[0]);
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
	 * 正規表現を生成します。
	 *
	 * @param keyword
	 * @return
	 */
	private String createRegExp(String keyword) {
		StringTokenizer tokenizer = new StringTokenizer(keyword, " \t");
		if (tokenizer.countTokens() == 0) {
			return null;
		}
		StringBuffer buf = new StringBuffer();
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (buf.length() > 0) {
				buf.append("|");
			}
			buf.append(token);
		}
		return "(" + buf.toString() + ")";
	}

}
