package top.tangtian.tech.storage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.tangtian.tech.common.BusinessException;
import top.tangtian.tech.common.Result;
import top.tangtian.tech.storage.dto.StorageDTO;
import top.tangtian.tech.storage.service.StorageService;

/**
 * @author tangtian
 * @date 2025-12-31 19:32
 */
@RestController
@RequestMapping("/storage")
public class StorageController {

	@Autowired
	private StorageService storageService;

	@PostMapping("/reduce-stock")
	public Result<?> reduceStock(@RequestBody StorageDTO storageDTO) {
		try {
			storageService.reduceStock(storageDTO.getCommodityCode(),
					storageDTO.getCount());
		}
		catch (BusinessException e) {
			return Result.failed(e.getMessage());
		}
		return Result.success("");
	}

	@GetMapping("/")
	public Result<?> getRemainCount(String commodityCode) {
		return storageService.getRemainCount(commodityCode);
	}

}