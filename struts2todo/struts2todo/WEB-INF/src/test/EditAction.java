package test;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

/**
 * 編集をおこなうアクション。
 */
public class EditAction extends AbstractDBAction {

	/**
	 * シリアルバージョンUID。
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * アイテムIDを保持します。
	 */
	private String _itemID;

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
	 * 完了したかどうかを保持します。
	 */
	private boolean _finished;

	/**
	 * 構築します。
	 */
	public EditAction() {
		_users = null;
		_name = null;
		_userID = null;
		_calendar = Calendar.getInstance();
		_calendar.clear();
		_itemID = null;
		_finished = false;
	}

	/**
	 * アイテムのIDを保持します。
	 * @param id
	 */
	public void setItem_id(String id) {
		_itemID = id;
	}

	/**
	 * アイテムのIDを取得思案す。
	 * @return
	 */
	public String getItem_id() {
		return _itemID;
	}

	/**
	 * 追加画面を表示します。
	 *
	 * @return
	 */
	public String show() {
		try {
			Item currentItem = queryItem(_itemID);

			_users = queryUsers();
			_calendar = Calendar.getInstance();
			_calendar.setTime(currentItem.getExpireDate());

			_name = currentItem.getName();
			_userID = currentItem.getUser().getId();
			_finished = (currentItem.getFinishedDate() != null);

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
			Item targetItem = queryItem(_itemID);
			User user = queryUser(_userID);
			if (user == null) {
				return showError("不正なパラメータです。");
			}
			targetItem.setUser(user);
			Date expireDate = new Date(_calendar.getTimeInMillis());
			targetItem.setExpireDate(expireDate);
			targetItem.setName(_name);
			if(_finished) {
				if (targetItem.getFinishedDate() == null) {
					Calendar calendar = Calendar.getInstance();
					targetItem.setFinishedDate(new Date(calendar
							.getTimeInMillis()));
				}
			} else {
				targetItem.setFinishedDate(null);
			}

			executeUpdate(createUpdateSQL(targetItem));

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
	 * ユーザIDを取得します。
	 * @return
	 */
	public String getUser_id() {
		return _userID;
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
	 * 名前を取得します。
	 * @return
	 */
	public String getName() {
		return _name;
	}

	/**
	 * 完了したかどうかを設定します。
	 * @param finished
	 */
	public void setFinished(String finished) {
		if("true".equals(finished)) {
			_finished = true;
		}else{
			_finished = false;
		}
	}

	/**
	 * 完了したかどうかを取得します。
	 * @return
	 */
	public String getFinished() {
		if(_finished) {
			return "true";
		}else{
			return "false";
		}
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
}
