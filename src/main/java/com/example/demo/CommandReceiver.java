package com.example.demo;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Validated
public class CommandReceiver {
	static boolean warned = false;
	@Value("${queue}")
	private String qName;

	@JmsListener(destination = "${queue}", containerFactory = "containerFactory")
	public void receiveMessage(final @Payload @Valid Command cmd) {
		CommandReceiver.log.info("receive {}  from {}", cmd, this.qName);
		// CommandReceiver.log.debug("header {}", cmd.getHeaders());
		// final Random random = new Random();
		// if (random.nextBoolean()) {
		// CommandReceiver.log.warn("throw an error !", cmd);
		// throw new CommandProcessingException("");
		// }
	}
}
