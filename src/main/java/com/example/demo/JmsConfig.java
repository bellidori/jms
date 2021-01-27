package com.example.demo;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
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
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJms
@EnableTransactionManagement
public class JmsConfig {
	@Autowired
	private ConnectionFactory connectionFactory;

	@Bean
	public JmsListenerContainerFactory<?> queueConnectionFactory(
			final ConnectionFactory connectionFactory,
			final DefaultJmsListenerContainerFactoryConfigurer configurer) {
		final DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		configurer.configure(factory, connectionFactory);
		factory.setPubSubDomain(false);
		factory.setErrorHandler(new DefaultErrorHandler());
		factory.setMessageConverter(this.messageConverter());
		return factory;
	}

	@Bean
	public JmsListenerContainerFactory<?> topicConnectionFactory(
			final ConnectionFactory connectionFactory,
			final DefaultJmsListenerContainerFactoryConfigurer configurer) {
		final DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		configurer.configure(factory, connectionFactory);
		factory.setPubSubDomain(true);
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

	@Bean
	public DynamicDestinationResolver destinationResolver() {
		return new DynamicDestinationResolver() {
			@Override
			public Destination resolveDestinationName(final Session session,
					final String destinationName, boolean pubSubDomain)
					throws JMSException {
				if (destinationName.contains("TOPIC")) {
					pubSubDomain = true;
				}
				return super.resolveDestinationName(session, destinationName,
						pubSubDomain);
			}
		};
	}
}
