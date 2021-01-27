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
		commandSender.send(new Command(UUID.randomUUID(), "commit !"), true);
		commandSender.send(new Command(null, "bad !"), true);
		commandSender.send(new Command(UUID.randomUUID(), "rollback !"), false);
	}
}
