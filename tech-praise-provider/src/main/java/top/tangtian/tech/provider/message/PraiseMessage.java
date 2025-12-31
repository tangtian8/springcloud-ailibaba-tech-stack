package top.tangtian.tech.provider.message;

/**
 * @author tangtian
 * @date 2025-12-31 19:02
 */
public class PraiseMessage {

	private Integer itemId;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	@Override
	public String toString() {
		return "PraiseMessage{" + "itemId=" + itemId + '}';
	}

}
