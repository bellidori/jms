package com.example.demo;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {
	public static void main(final String[] args) {
		// Launch the application
		final ConfigurableApplicationContext context = SpringApplication
				.run(Application.class, args);
		final CommandSender commandSender = context
				.getBean(CommandSender.class);
		commandSender.sendToQueue(new Command1(UUID.randomUUID(), "queue !"));
		// commandSender.sendToQueue(new Command1(null, "queue !"));
		commandSender.sendToTopic(new Command1(UUID.randomUUID(), "topic !"));
		// commandSender.sendToTopic(new Command1(null, "topic !"));
		commandSender.sendToTopic(new Command2(UUID.randomUUID(), "topic !"));
	}
}
