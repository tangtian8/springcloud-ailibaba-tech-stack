package top.tangtian.tech.provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.tangtian.tech.provider.message.PraiseMessage;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author tangtian
 * @date 2025-12-31 19:02
 */
@RestController
@RequestMapping("/praise")
public class PraiseController {
	private static final String BINDING_NAME = "praise-output";
	@Autowired
	private StreamBridge streamBridge;

	@GetMapping({ "/rocketmq", "/sentinel" })
	public boolean praise(@RequestParam Integer itemId) {
		PraiseMessage message = new PraiseMessage();
		message.setItemId(itemId);
		Message<PraiseMessage> praiseMessage = MessageBuilder.withPayload(message)
				.build();
		return streamBridge.send(BINDING_NAME, praiseMessage);
	}

}
