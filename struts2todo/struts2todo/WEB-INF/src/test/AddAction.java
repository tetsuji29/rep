package test;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

/**
 * 追加をおこなうアクションです。
 */
public class AddAction extends AbstractDBAction {

	/**
	 * シリアルバージョンUID。
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ユーザを保持します。
	 */
	private User[] _users;

	/**
	 * カレンダーを保持します。
	 */
	private Calendar _calendar;

	/**
	 * 作業項目名を保持します。
	 */
	private String _name;

	/**
	 * ユーザIDを保持します。
	 */
	private String _userID;

	/**
	 * 構築します。
	 */
	public AddAction() {
		_users = null;
		_name = null;
		_userID = null;
		_calendar = Calendar.getInstance();
		_calendar.clear();
	}

	/**
	 * 追加画面を表示します。
	 *
	 * @return
	 */
	public String show() {
		try {
			_users = queryUsers();
			_calendar = Calendar.getInstance();

			return "success";
		} catch (SQLException e) {
			return showError(e.getMessage());
		}
	}

	/**
	 * 実行します。
	 *
	 * @return
	 */
	@Override
	public String execute() {
		try {
			Item targetItem = new Item();
			User user = queryUser(_userID);
			if (user == null) {
				return showError("不正なパラメータです。");
			}
			targetItem.setUser(user);
			Date expireDate = new Date(_calendar.getTimeInMillis());
			targetItem.setExpireDate(expireDate);
			targetItem.setName(_name);

			executeUpdate(createInsertSQL(targetItem));

			return "success";
		} catch (SQLException e) {
			return showError(e.getMessage());
		}
	}

	/**
	 * ユーザIDを設定します。
	 *
	 * @param id
	 */
	public void setUser_id(String id) {
		_userID = id;
	}

	/**
	 * 名前を保持します。
	 *
	 * @param name
	 */
	public void setName(String name) {
		_name = name;
	}

	/**
	 * ユーザ一覧を取得します。
	 *
	 * @return
	 */
	public User[] getUsers() {
		return _users;
	}

	/**
	 * 年を取得します。
	 *
	 * @return
	 */
	public int getYear() {
		return _calendar.get(Calendar.YEAR);
	}

	/**
	 * 月を取得します。
	 *
	 * @return
	 */
	public int getMonth() {
		return _calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 日を取得します。
	 *
	 * @return
	 */
	public int getDay() {
		return _calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 年を設定します。
	 *
	 * @param year
	 */
	public void setYear(int year) {
		_calendar.set(Calendar.YEAR, year);
	}

	/**
	 * 月を設定します。
	 *
	 * @param year
	 */
	public void setMonth(int month) {
		_calendar.set(Calendar.MONTH, month - 1);
	}

	/**
	 * 日を設定します。
	 *
	 * @param day
	 */
	public void setDay(int day) {
		_calendar.set(Calendar.DAY_OF_MONTH, day);
	}

	/**
	 * 追加用のSQL文を生成します。
	 *
	 * @param targetItem
	 * @return
	 */
	private String createInsertSQL(Item targetItem) {
		StringBuffer buf = new StringBuffer();
		buf.append("INSERT INTO ");
		buf.append("TODO_ITEM");
		buf.append(" (NAME,USER,EXPIRE_DATE,FINISHED_DATE)");
		buf.append(" VALUES('");
		buf.append(targetItem.getName());
		buf.append("', '");
		buf.append(targetItem.getUser().getId());
		buf.append("', '");
		buf.append(targetItem.getExpireDate().toString());
		buf.append("', null)");

		return buf.toString();
	}
}
