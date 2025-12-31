package top.tangtian.tech.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.tangtian.tech.order.feign.dto.AccountDTO;
import top.tangtian.tech.common.Result;

/**
 * @author tangtian
 * @date 2025-12-31 12:51
 */
@FeignClient(name = "integrated-account")
public interface AccountServiceFeignClient {

	@PostMapping("/account/reduce-balance")
	Result<?> reduceBalance(@RequestBody AccountDTO accountReduceBalanceDTO);

}
