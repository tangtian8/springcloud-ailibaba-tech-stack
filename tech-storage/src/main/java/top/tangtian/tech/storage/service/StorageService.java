package top.tangtian.tech.storage.service;

import top.tangtian.tech.common.BusinessException;
import top.tangtian.tech.common.Result;

/**
 * @author tangtian
 * @date 2025-12-31 19:29
 */
public interface StorageService {

	void reduceStock(String commodityCode, Integer orderCount) throws BusinessException;

	Result<?> getRemainCount(String commodityCode);

}
