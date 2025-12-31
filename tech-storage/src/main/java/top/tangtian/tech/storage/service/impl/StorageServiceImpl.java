package top.tangtian.tech.storage.service.impl;

import top.tangtian.tech.common.Result;
import org.apache.seata.core.context.RootContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.tangtian.tech.common.BusinessException;
import top.tangtian.tech.storage.mapper.StorageMapper;
import top.tangtian.tech.storage.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Timestamp;

/**
 * @author tangtian
 * @date 2025-12-31 19:30
 */
@Service
public class StorageServiceImpl implements StorageService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private StorageMapper storageMapper;

	@Override
	@Transactional
	public void reduceStock(String commodityCode, Integer count)
			throws BusinessException {
		logger.info("[reduceStock] current XID: {}", RootContext.getXID());

		checkStock(commodityCode, count);

		Timestamp updateTime = new Timestamp(System.currentTimeMillis());
		int updateCount = storageMapper.reduceStock(commodityCode, count, updateTime);
		if (updateCount == 0) {
			throw new BusinessException("deduct stock failed");
		}
	}

	@Override
	public Result<?> getRemainCount(String commodityCode) {
		Integer stock = storageMapper.getStock(commodityCode);
		if (stock == null) {
			return Result.failed("commodityCode wrong,please check commodity code");
		}
		return Result.success(stock);
	}

	private void checkStock(String commodityCode, Integer count)
			throws BusinessException {
		Integer stock = storageMapper.getStock(commodityCode);
		if (stock < count) {
			throw new BusinessException("no enough stock");
		}
	}

}
