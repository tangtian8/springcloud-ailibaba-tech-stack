package top.tangtian.tech.consumer.listener;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import top.tangtian.tech.consumer.message.PraiseMessage;
import top.tangtian.tech.consumer.service.PraiseService;

import java.util.function.Consumer;
/**
 * @author tangtian
 * @date 2025-12-31 17:32
 */
@Configuration
public class ListenerAutoConfiguration {
	@Bean
	public Consumer<Message<PraiseMessage>> consumer(PraiseService praiseService) {
		return msg -> {
			praiseService.praiseItem(msg.getPayload().getItemId());
		};
	}
}