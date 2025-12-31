package top.tangtian.tech.common;

/**
 * @author tangtian
 * @date 2025-12-31 10:12
 */
public class Result<T> {

	private Integer code;

	private String message;

	private T data;

	public static <T> Result<T> success(T data) {
		return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(),
				data);
	}

	public static <T> Result<T> success(String message, T data) {
		return new Result<>(ResultEnum.SUCCESS.getCode(), message, data);
	}

	public static Result<?> failed() {
		return new Result<>(ResultEnum.COMMON_FAILED.getCode(),
				ResultEnum.COMMON_FAILED.getMessage(), null);
	}

	public static Result<?> failed(String message) {
		return new Result<>(ResultEnum.COMMON_FAILED.getCode(), message, null);
	}

	public static Result<?> failed(IResult errorResult) {
		return new Result<>(errorResult.getCode(), errorResult.getMessage(), null);
	}

	public Result() {
	}

	public Result(Integer code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public static <T> Result<T> instance(Integer code, String message, T data) {
		Result<T> result = new Result<>();
		result.setCode(code);
		result.setMessage(message);
		result.setData(data);
		return result;
	}

}