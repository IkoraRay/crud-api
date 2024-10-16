package com.onboarding.crud.api.producers;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageProducer {

	private final KafkaTemplate<String, String> kafkaTemplate;

	public KafkaMessageProducer (KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage(String message, String topic){
		kafkaTemplate.send(topic,message);
	}
}
