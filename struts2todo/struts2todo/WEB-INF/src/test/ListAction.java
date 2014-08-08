package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletException;

/**
 * 一覧を表示するアクション。
 */
public class ListAction extends AbstractDBAction {

	/**
	 * シリアルバージョンUID。
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * アイテムを保持します。
	 */
	private Item[] _items;

	/**
	 * 構築します。
	 */
	public ListAction() {
		_items = null;
	}

	/**
	 * アイテムの一覧を取得します。
	 * @return
	 */
	public Item[] getItems() {
		return _items;
	}

	/**
	 * 現在時刻を取得します。
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
		try{
			_items = queryItems();

			return "success";
		}catch(SQLException e) {
			return showError(e.getMessage());
		}
	}

	/**
	 * アイテムを取得します。
	 *
	 * @return
	 * @throws ServletException
	 */
	private Item[] queryItems() throws SQLException {
		Statement statement = null;
		try {
			// SQL文を発行
			statement = getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(createSQL(null));
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

}
