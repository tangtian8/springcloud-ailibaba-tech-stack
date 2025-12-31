package top.tangtian.tech.consumer.service;

/**
 * @author tangtian
 * @date 2025-12-31 17:34
 */
public interface PraiseService {

	void praiseItem(Integer itemId);

	int getPraise(Integer itemId);

}
