package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import lombok.extern.slf4j.Slf4j;

/**
 * send command on queue.
 * @author Richard
 */
@Service
@Slf4j
public class CommandSender {
	@Autowired
	private JmsTemplate jmsTemplate;
	@Value("${queue}")
	private String qName;
	@Value("${topic}")
	private String topic;
	@Autowired
	JmsTransactionManager tm;

	/**
	 * send command to queue.
	 * @param cmd command to send
	 * @param commit commit
	 */
	public void sendToQueue(final Command cmd) {
		CommandSender.log.debug("prepare sending {} on {}", cmd, this.qName);
		// This starts a new transaction scope. "null" can be used to get a
		// default transaction model
		final TransactionStatus status = this.tm.getTransaction(null);
		// This operation will be made part of the transaction that we
		// initiated.
		this.jmsTemplate.convertAndSend(this.qName, cmd);
		this.tm.commit(status);
	}

	/**
	 * send command to queue.
	 * @param cmd command to send
	 * @param commit commit
	 */
	public void sendToTopic(final Command cmd) {
		CommandSender.log.debug("prepare sending {} on {}", cmd, this.qName);
		// This starts a new transaction scope. "null" can be used to get a
		// default transaction model
		final TransactionStatus status = this.tm.getTransaction(null);
		// This operation will be made part of the transaction that we
		// initiated.
		this.jmsTemplate.convertAndSend(this.topic, cmd);
		this.tm.commit(status);
	}
}
