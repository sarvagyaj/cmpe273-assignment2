package edu.sjsu.cmpe.procurement.broker;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.fusesource.stomp.jms.StompJmsDestination;

import edu.sjsu.cmpe.procurement.connection.BrokerConnection;
import edu.sjsu.cmpe.procurement.util.ProcurementConfig;

public class Consumer {

	private MessageConsumer messageConsumer;
	private Session session;
	private Destination destination;

	private static final Consumer consumer = new Consumer();

	private Consumer() {
		createConsumer();
	}

	private void createConsumer() {
		try {
			destination = new StompJmsDestination(ProcurementConfig
					.getProcurementConfig().getStompQueueName());
			session = BrokerConnection.getConnection().createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			messageConsumer = session.createConsumer(destination);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public static MessageConsumer getMessageConsumer() {
		return consumer.messageConsumer;
	}

}
