package top.tangtian.tech.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author tangtian
 * @date 2025-12-31 12:30
 */
@Controller
public class IntegrationController {

	@RequestMapping("/order")
	public String order() {
		return "order";
	}

	@RequestMapping("/rocketmq")
	public String rocketmq() {
		return "rocketmq";
	}

	@RequestMapping("/sentinel")
	public String sentinel() {
		return "sentinel";
	}

}
