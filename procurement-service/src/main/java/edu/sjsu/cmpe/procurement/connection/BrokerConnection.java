package edu.sjsu.cmpe.procurement.connection;

import javax.jms.Connection;
import javax.jms.JMSException;

import org.fusesource.stomp.jms.StompJmsConnectionFactory;

import edu.sjsu.cmpe.procurement.util.ProcurementConfig;

public class BrokerConnection {
	private static Connection connection = createConnection();

	private BrokerConnection() {
	}

	private static Connection createConnection() {
		String user = ProcurementConfig.getProcurementConfig().getApolloUser();
		String password = ProcurementConfig.getProcurementConfig()
				.getApolloPassword();
		String host = ProcurementConfig.getProcurementConfig().getApolloHost();
		String apolloPort = ProcurementConfig.getProcurementConfig()
				.getApolloPort();

		user = env("APOLLO_USER", user);
		password = env("APOLLO_PASSWORD", password);
		host = env("APOLLO_HOST", host);
		int port = Integer.parseInt(env("APOLLO_PORT", apolloPort));

		StompJmsConnectionFactory factory = new StompJmsConnectionFactory();
		factory.setBrokerURI("tcp://" + host + ":" + port);
		try {
			connection = factory.createConnection(user, password);
			connection.start();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return connection;
	}

	/*
	 * public void closeConnection() { try { connection.close(); } catch
	 * (JMSException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 */

	// Environment Variable
	private static String env(String key, String defaultValue) {
		String rc = System.getenv(key);
		if (rc == null) {
			return defaultValue;
		}
		return rc;
	}

	public static Connection getConnection() {
		return connection;
	}

}
