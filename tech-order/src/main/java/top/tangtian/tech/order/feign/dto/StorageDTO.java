package top.tangtian.tech.order.feign.dto;

/**
 * @author tangtian
 * @date 2025-12-31 12:56
 */
public class StorageDTO {
	private String commodityCode;

	private Integer count;

	public String getCommodityCode() {
		return commodityCode;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
