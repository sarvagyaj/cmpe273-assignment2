package edu.sjsu.cmpe.library.subscriber;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.fusesource.stomp.jms.StompJmsDestination;

import edu.sjsu.cmpe.library.connection.BrokerConnection;
import edu.sjsu.cmpe.library.util.LibraryConfig;

public class Subscriber {
	private MessageConsumer messageConsumer;
	private Session session;
	private Destination destination;

	private static final Subscriber subscriber = new Subscriber();

	private Subscriber() {
		createSubscriber();
	}

	private void createSubscriber() {
		try {
			session = BrokerConnection.getConnection().createSession(false,
					Session.AUTO_ACKNOWLEDGE);

			destination = new StompJmsDestination(LibraryConfig
					.getLibraryConfig().getStompTopicName());
			messageConsumer = session.createConsumer(destination);

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public static MessageConsumer getMessageSubscriber() {
		return subscriber.messageConsumer;
	}

}
