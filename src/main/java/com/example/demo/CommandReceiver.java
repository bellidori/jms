package com.example.demo;

import javax.validation.Valid;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Validated
public class CommandReceiver {
	@JmsListener(destination = "${queue}", containerFactory = "queueConnectionFactory")
	public void receiveFromQueue(final @Payload @Valid Command cmd) {
		CommandReceiver.log.info("receive from queue {}", cmd);
	}

	@JmsListener(destination = "${topic}", containerFactory = "topicConnectionFactory")
	public void receiveFromTopic(final @Payload @Valid Command cmd) {
		CommandReceiver.log.info("receive from topic {}", cmd);
	}
}
