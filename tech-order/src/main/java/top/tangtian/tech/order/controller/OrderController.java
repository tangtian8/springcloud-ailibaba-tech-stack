package top.tangtian.tech.order.controller;

/**
 * @author tangtian
 * @date 2026-01-22 16:10
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.tangtian.tech.common.BusinessException;
import top.tangtian.tech.common.Result;

/**
 * @author TrevorLink
 */
@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/create")
	public Result<?> createOrder(@RequestParam("userId") String userId,
								 @RequestParam("commodityCode") String commodityCode,
								 @RequestParam("count") Integer count) {
		Result<?> res = null;
		try {
			res = orderService.createOrder(userId, commodityCode, count);
		}
		catch (BusinessException e) {
			return Result.failed(e.getMessage());
		}
		return res;
	}

}