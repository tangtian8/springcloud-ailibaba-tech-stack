package top.tangtian.tech.consumer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tangtian.tech.consumer.mapper.PraiseMapper;
import top.tangtian.tech.consumer.service.PraiseService;

import java.sql.Timestamp;

/**
 * @author tangtian
 * @date 2025-12-31 17:35
 */
@Service
public class PraiseServiceImpl implements PraiseService {

	@Autowired
	private PraiseMapper praiseMapper;

	@Override
	public void praiseItem(Integer itemId) {
		Timestamp updateTime = new Timestamp(System.currentTimeMillis());
		praiseMapper.praiseItem(itemId, updateTime);
	}

	@Override
	public int getPraise(Integer itemId) {
		return praiseMapper.getPraise(itemId);
	}

}
