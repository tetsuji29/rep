package test;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

/**
 * 完了/未完了切替をおこなうアクション。
 */
public class FinishAction extends AbstractDBAction {

	/**
	 * シリアルバージョンUID。
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * アイテムIDを保持します。
	 */
	private String _itemID;

	/**
	 * キーワードを保持します。
	 */
	private String _keyword;

	/**
	 * 構築します。
	 */
	public FinishAction() {
		_itemID = null;
		_keyword = null;
	}

	/**
	 * アイテムのIDを設定します。
	 * @param id
	 * @return
	 */
	public void setItem_id(String id) {
		_itemID = id;
	}

	/**
	 * キーワードを設定します。
	 * @param keyword
	 */
	public void setKeyword(String keyword) {
		_keyword = keyword;
	}

	/**
	 * キーワードを取得します。
	 * @return
	 */
	public String getKeyword() {
		return _keyword;
	}

	/**
	 * 実行します。
	 *
	 * @return
	 */
	@Override
	public String execute() {
		try {
			Item currentItem = queryItem(_itemID);
			if (currentItem.getFinishedDate() == null) {
				// 完了に変更
				Calendar calendar = Calendar.getInstance();
				currentItem
						.setFinishedDate(new Date(calendar.getTimeInMillis()));
			} else {
				// 未完了に変更
				currentItem.setFinishedDate(null);
			}

			int updateCount = executeUpdate(createUpdateSQL(currentItem));
			if (updateCount != 1) {
				return showError("更新に失敗しました。");
			}

			if(_keyword != null) {
				return "search";
			}else{
				return "list";
			}
		} catch (SQLException e) {
			return showError(e.getMessage());
		}
	}
}
