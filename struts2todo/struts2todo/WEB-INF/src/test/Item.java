package test;

import java.sql.Date;

/**
 * アイテム情報を保持します。
 */
public class Item {

	/**
	 * IDを保持します。
	 */
	private String _id;

	/**
	 * 名前を保持します。
	 */
	private String _name;

	/**
	 * 担当者を保持します。
	 */
	private User _user;

	/**
	 * 期限を保持します。
	 */
	private Date _expireDate;

	/**
	 * 終了した日時を保持します。
	 */
	private Date _finishedDate;

	/**
	 * 構築します。
	 */
	public Item() {
		_id = null;
		_name = null;
		_user = null;
		_expireDate = null;
		_finishedDate = null;
	}

	/**
	 * IDを取得します。
	 *
	 * @return
	 */
	public String getId() {
		return _id;
	}

	/**
	 * IDを設定します。
	 *
	 * @param id
	 */
	public void setId(String id) {
		_id = id;
	}

	/**
	 * 名前を取得します。
	 *
	 * @return
	 */
	public String getName() {
		return _name;
	}

	/**
	 * 名前を設定します。
	 *
	 * @param name
	 */
	public void setName(String name) {
		_name = name;
	}

	/**
	 * ユーザを取得します。
	 *
	 * @return
	 */
	public User getUser() {
		return _user;
	}

	/**
	 * ユーザを設定します。
	 *
	 * @param user
	 */
	public void setUser(User user) {
		_user = user;
	}

	/**
	 * 期限を取得します。
	 *
	 * @return
	 */
	public Date getExpireDate() {
		return _expireDate;
	}

	/**
	 * 期限を設定します。
	 *
	 * @param expireDate
	 */
	public void setExpireDate(Date expireDate) {
		_expireDate = expireDate;
	}

	/**
	 * 終了日時を取得します。
	 *
	 * @return
	 */
	public Date getFinishedDate() {
		return _finishedDate;
	}

	/**
	 * 終了日時を設定します。
	 *
	 * @param finishedDate
	 */
	public void setFinishedDate(Date finishedDate) {
		_finishedDate = finishedDate;
	}
}
