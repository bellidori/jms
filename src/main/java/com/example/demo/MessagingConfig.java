package com.example.demo;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJms
@EnableTransactionManagement
public class MessagingConfig {
	@Autowired
	private ConnectionFactory connectionFactory;

	@Bean
	public JmsListenerContainerFactory<?> containerFactory() {
		/*
		 * final ConnectionFactory connectionFactory, final
		 * DefaultJmsListenerContainerFactoryConfigurer configurer) { final
		 * DefaultJmsListenerContainerFactory factory = new
		 * DefaultJmsListenerContainerFactory(); // This provides all boot's
		 * default to this factory, including the // message converter
		 * configurer.configure(factory, connectionFactory); configurer. // You
		 * could still override some of Boot's default if necessary.
		 */
		final DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(this.connectionFactory);
		factory.setErrorHandler(new DefaultErrorHandler());
		factory.setMessageConverter(this.messageConverter());
		return factory;
	}

	@Bean
	public MessageConverter messageConverter() {
		final MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

	@Bean
	public JmsTransactionManager txManager(final JmsTemplate jmsTemplate) {
		// Create a transaction manager object that will be used to control
		// commit/rollback of operations.
		final JmsTransactionManager tm = new JmsTransactionManager();
		// Associate the connection final factory with the final transaction
		// manager
		tm.setConnectionFactory(jmsTemplate.getConnectionFactory());
		return tm;
	}
}
