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
	@Autowired
	JmsTransactionManager tm;

	/**
	 * send command to queue.
	 * @param cmd command to send
	 * @param commit commit
	 */
	public void send(final Command cmd, final boolean commit) {
		CommandSender.log.debug("prepare sending {} on {}", cmd, this.qName);
		// This starts a new transaction scope. "null" can be used to get a
		// default transaction model
		final TransactionStatus status = this.tm.getTransaction(null);
		// This operation will be made part of the transaction that we
		// initiated.
		this.jmsTemplate.convertAndSend(this.qName, cmd);
		if (commit) {
			CommandSender.log.debug("commit", status);
			// Commit the transaction so the message is now visible
			this.tm.commit(status);
		}
		else {
			CommandSender.log.debug("rollback", status);
			// This time we decide to rollback the transaction so the receive()
			// and send() are
			// reverted. We end up with the message still on qName1.
			this.tm.rollback(status);
		}
	}
}
