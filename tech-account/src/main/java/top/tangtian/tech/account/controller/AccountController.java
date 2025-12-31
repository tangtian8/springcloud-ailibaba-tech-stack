package top.tangtian.tech.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.tangtian.tech.account.dto.AccountDTO;
import top.tangtian.tech.account.service.AccountService;
import top.tangtian.tech.common.BusinessException;
import top.tangtian.tech.common.Result;

/**
 * @author tangtian
 * @date 2025-12-31 10:07
 */
@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PostMapping("/reduce-balance")
	public Result<?> reduceBalance(@RequestBody AccountDTO accountDTO) {
		try {
			accountService.reduceBalance(accountDTO.getUserId(), accountDTO.getPrice());
		}
		catch (BusinessException e) {
			return Result.failed(e.getMessage());
		}
		return Result.success("");
	}

	@GetMapping("/")
	public Result<?> getRemainAccount(String userId) {
		return accountService.getRemainAccount(userId);
	}

}