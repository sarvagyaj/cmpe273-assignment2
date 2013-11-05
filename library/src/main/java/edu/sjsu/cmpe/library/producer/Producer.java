package edu.sjsu.cmpe.library.producer;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.fusesource.stomp.jms.StompJmsDestination;

import edu.sjsu.cmpe.library.connection.BrokerConnection;
import edu.sjsu.cmpe.library.util.LibraryConfig;

public class Producer {

	private Session session;
	private Destination destination;
	private MessageProducer producer;

	public void sendMessageToQueue(long isbn) {

		try {
			destination = new StompJmsDestination(LibraryConfig
					.getLibraryConfig().getStompQueueName());
			session = BrokerConnection.getConnection().createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			System.out.println("Sending messages to "
					+ LibraryConfig.getLibraryConfig().getStompQueueName()
					+ "...");

			// message to be sent to queue
			String data = LibraryConfig.getLibraryConfig().getLibraryName()
					+ ":" + isbn;
			TextMessage msg = session.createTextMessage(data);
			msg.setLongProperty("isbn", isbn);
			msg.setLongProperty("id", System.currentTimeMillis());
			producer.send(msg);
		} catch (JMSException e) {
				e.printStackTrace();
		}
	}
}
