package test;

import java.sql.SQLException;

/**
 * 削除をおこなうアクション。
 */
public class DeleteAction extends AbstractDBAction {

	/**
	 * シリアルバージョンUID。
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * アイテムIDを保持します。
	 */
	private String _itemID;

	/**
	 * アイテムを保持します。
	 */
	private Item _item;

	/**
	 * 構築します。
	 */
	public DeleteAction() {
		_itemID = null;
		_item = null;
	}

	/**
	 * アイテムIDを設定します。
	 * @param id
	 */
	public void setItem_id(String id) {
		_itemID = id;
	}

	/**
	 * アイテムIDを取得します。
	 * @return
	 */
	public String getItem_id() {
		return _itemID;
	}

	/**
	 * アイテムを取得します。
	 * @return
	 */
	public Item getItem() {
		return _item;
	}

	/**
	 * 追加画面を表示します。
	 *
	 * @return
	 */
	public String show() {
		try {
			_item = queryItem(_itemID);

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
			Item currentItem = queryItem(_itemID);

			int updateCount = executeUpdate(createDeleteSQL(currentItem));
			if (updateCount != 1) {
				return showError("更新に失敗しました。");
			}

			return "success";
		} catch (SQLException e) {
			return showError(e.getMessage());
		}
	}

	/**
	 * 削除用のSQL文を生成します。
	 *
	 * @param targetItem
	 * @return
	 */
	private String createDeleteSQL(Item targetItem) {
		StringBuffer buf = new StringBuffer();
		buf.append("DELETE FROM ");
		buf.append("TODO_ITEM");
		buf.append(" WHERE ID='");
		buf.append(targetItem.getId());
		buf.append("'");

		return buf.toString();
	}
}
