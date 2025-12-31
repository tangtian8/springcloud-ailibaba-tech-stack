package top.tangtian.tech.account.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.tangtian.tech.account.mapper.AccountMapper;
import top.tangtian.tech.account.service.AccountService;
import org.apache.seata.core.context.RootContext;
import top.tangtian.tech.common.BusinessException;
import top.tangtian.tech.common.Result;

import java.sql.Timestamp;
/**
 * @author tangtian
 * @date 2025-12-31 10:09
 */
@Service
public class AccountServiceImpl implements AccountService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AccountMapper accountMapper;

	@Override
	@Transactional
	public void reduceBalance(String userId, Integer price) throws BusinessException {
		logger.info("[reduceBalance] currenet XID: {}", RootContext.getXID());

		checkBalance(userId, price);

		Timestamp updateTime = new Timestamp(System.currentTimeMillis());
		int updateCount = accountMapper.reduceBalance(userId, price, updateTime);
		if (updateCount == 0) {
			throw new BusinessException("reduce balance failed");
		}
	}

	@Override
	public Result<?> getRemainAccount(String userId) {
		Integer balance = accountMapper.getBalance(userId);
		if (balance == null) {
			return Result.failed("wrong userId,please check the userId");
		}
		return Result.success(balance);
	}

	private void checkBalance(String userId, Integer price) throws BusinessException {
		Integer balance = accountMapper.getBalance(userId);
		if (balance < price) {
			throw new BusinessException("no enough balance");
		}
	}

}
