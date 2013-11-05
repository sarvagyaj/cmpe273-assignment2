package edu.sjsu.cmpe.procurement.broker;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.fusesource.stomp.jms.StompJmsDestination;

import edu.sjsu.cmpe.procurement.connection.BrokerConnection;
import edu.sjsu.cmpe.procurement.util.ProcurementConfig;

public class Producer {

	private MessageProducer messageProducerComputer;
	private MessageProducer messageProducerComics;
	private MessageProducer messageProducerManagement;
	private MessageProducer messageProducerSelfImprovement;
	private Session session;
	private Destination destination;

	private static final Producer producer = new Producer();

	private Producer() {
		createProducer();
	}

	private void createProducer() {
		try {
			session = BrokerConnection.getConnection().createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			destination = new StompJmsDestination(ProcurementConfig
					.getProcurementConfig().getStompTopicComputer());
			messageProducerComputer = session.createProducer(destination);
			messageProducerComputer
					.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			destination = new StompJmsDestination(ProcurementConfig
					.getProcurementConfig().getStompTopicComics());
			messageProducerComics = session.createProducer(destination);
			messageProducerComics.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			destination = new StompJmsDestination(ProcurementConfig
					.getProcurementConfig().getStompTopicManagement());
			messageProducerManagement = session.createProducer(destination);
			messageProducerManagement
					.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			destination = new StompJmsDestination(ProcurementConfig
					.getProcurementConfig().getStompTopicSelfImprovement());
			messageProducerSelfImprovement = session
					.createProducer(destination);
			messageProducerSelfImprovement
					.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public static Session getSession() {
		return producer.session;
	}

	public static MessageProducer getMessageProducer(String category) {
		if (category.equalsIgnoreCase("computer")) {
			return producer.messageProducerComputer;
		} else if (category.equalsIgnoreCase("comics")) {
			return producer.messageProducerComics;
		} else if (category.equalsIgnoreCase("management")) {
			return producer.messageProducerManagement;
		} else if (category.equalsIgnoreCase("selfimprovement")) {
			return producer.messageProducerSelfImprovement;
		} else {
			String dest = "/topic/69377.book." + category;
			try {
				MessageProducer messageProducer = producer.session
						.createProducer(new StompJmsDestination(dest));
				return messageProducer;
			} catch (JMSException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

}
