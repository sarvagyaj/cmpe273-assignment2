package edu.sjsu.cmpe.procurement.broker;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.fusesource.stomp.jms.message.StompJmsMessage;

import de.spinscale.dropwizard.jobs.Job;
import de.spinscale.dropwizard.jobs.annotations.Every;
import edu.sjsu.cmpe.procurement.publisher.PublisherJobs;
import edu.sjsu.cmpe.procurement.util.ProcurementConfig;

@Every("5min")
public class ReceiveMessage extends Job {

	@Override
	public void doJob() {
		List<Integer> requestedISBNs = new ArrayList<Integer>();
		System.out
				.println("\n ===========================================Waiting for messages from "
						+ ProcurementConfig.getProcurementConfig()
								.getStompQueueName()
						+ "...====================================================");
		while (true) {
			try {
				Message msg = Consumer.getMessageConsumer().receive(20000);
				if (msg == null) {
					System.out
							.println("No new messages. Exiting due to timeout of 20 seconds.");
					break;
				} else if (msg instanceof TextMessage) {
					String body = ((TextMessage) msg).getText();
					System.out.println("Received message = " + body);
					requestedISBNs.add(msg.getIntProperty("isbn"));
				} else if (msg instanceof StompJmsMessage) {
					StompJmsMessage smsg = ((StompJmsMessage) msg);
					String body = smsg.getFrame().contentAsString();
					System.out
							.println("Received message of type StompJmsMessage = "
									+ body);
				} else {
					System.out.println("Unexpected message type from doJob : "
							+ msg.getClass());
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		if (requestedISBNs != null && requestedISBNs.size() > 0) {
			PublisherJobs.getInstance().submitBookOrder(requestedISBNs);
		} else {
			PublisherJobs.getInstance().listenBookOrder();
		}
	}
}
