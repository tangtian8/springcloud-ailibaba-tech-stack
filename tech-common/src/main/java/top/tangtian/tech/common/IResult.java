package top.tangtian.tech.common;

/**
 * @author tangtian
 * @date 2025-12-31 10:11
 */
public interface IResult {

	/**
	 * Get result code.
	 * @return result code
	 */
	Integer getCode();

	/**
	 * Get result message.
	 * @return result message
	 */
	String getMessage();

}
