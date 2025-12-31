package top.tangtian.tech.order.feign.dto;

/**
 * @author tangtian
 * @date 2025-12-31 12:55
 */
public class AccountDTO {
	private String userId;

	private Integer price;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
}
