package top.tangtian.tech.common;

/**
 * @author tangtian
 * @date 2025-12-31 10:13
 */
public enum ResultEnum implements IResult {

	/**
	 * return success result.
	 */
	SUCCESS(2001, "接口调用成功"),
	/**
	 * return business common failed.
	 */
	COMMON_FAILED(2003, "接口调用失败");

	private Integer code;

	private String message;

	ResultEnum() {
	}

	ResultEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}