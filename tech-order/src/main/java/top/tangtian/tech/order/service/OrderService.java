package top.tangtian.tech.order.service;

import top.tangtian.tech.common.BusinessException;
import top.tangtian.tech.common.Result;

/**
 * @author tangtian
 * @date 2026-01-22 16:11
 */
public interface OrderService {
	Result<?> createOrder(String userId, String commodityCode, Integer count)
			throws BusinessException;
}
