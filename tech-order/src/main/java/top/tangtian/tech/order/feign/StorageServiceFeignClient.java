package top.tangtian.tech.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.tangtian.tech.order.feign.dto.StorageDTO;
import top.tangtian.tech.common.Result;

/**
 * @author tangtian
 * @date 2025-12-31 12:54
 */
@FeignClient(name = "integrated-storage")
public interface StorageServiceFeignClient {

	@PostMapping("/storage/reduce-stock")
	Result<?> reduceStock(@RequestBody StorageDTO productReduceStockDTO);

}