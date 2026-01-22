package top.tangtian.tech.order.service.impl;

import org.apache.seata.core.context.RootContext;
import org.apache.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tangtian.tech.common.BusinessException;
import top.tangtian.tech.common.Result;
import top.tangtian.tech.order.entity.Order;
import top.tangtian.tech.order.feign.AccountServiceFeignClient;
import top.tangtian.tech.order.feign.StorageServiceFeignClient;
import top.tangtian.tech.order.feign.dto.AccountDTO;
import top.tangtian.tech.order.feign.dto.StorageDTO;
import top.tangtian.tech.order.mapper.OrderMapper;
import top.tangtian.tech.order.service.OrderService;

import java.sql.Timestamp;

import static top.tangtian.tech.common.ResultEnum.COMMON_FAILED;

/**
 * @author tangtian
 * @date 2026-01-22 16:12
 */
@Service
public class OrderServiceImpl implements OrderService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private AccountServiceFeignClient accountService;

	@Autowired
	private StorageServiceFeignClient storageService;

	@Override
	@GlobalTransactional
	public Result<?> createOrder(String userId, String commodityCode, Integer count) {

		logger.info("[createOrder] current XID: {}", RootContext.getXID());

		// deduct storage
		StorageDTO storageDTO = new StorageDTO();
		storageDTO.setCommodityCode(commodityCode);
		storageDTO.setCount(count);
		Integer storageCode = storageService.reduceStock(storageDTO).getCode();
		if (storageCode.equals(COMMON_FAILED.getCode())) {
			throw new BusinessException("stock not enough");
		}

		// deduct balance
		int price = count * 2;
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setUserId(userId);
		accountDTO.setPrice(price);
		Integer accountCode = accountService.reduceBalance(accountDTO).getCode();
		if (accountCode.equals(COMMON_FAILED.getCode())) {
			throw new BusinessException("balance not enough");
		}

		// save order
		Order order = new Order();
		order.setUserId(userId);
		order.setCommodityCode(commodityCode);
		order.setCount(count);
		order.setMoney(price);
		order.setCreateTime(new Timestamp(System.currentTimeMillis()));
		order.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		orderMapper.saveOrder(order);
		logger.info("[createOrder] orderId: {}", order.getId());

		return Result.success(order);
	}

}
