package top.tangtian.tech.account.service;

import top.tangtian.tech.common.BusinessException;
import top.tangtian.tech.common.Result;

/**
 * @author tangtian
 * @date 2025-12-31 10:08
 */
public interface AccountService {

	void reduceBalance(String userId, Integer price) throws BusinessException;

	Result<?> getRemainAccount(String userId);
}
