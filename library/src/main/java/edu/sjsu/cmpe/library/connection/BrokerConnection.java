package edu.sjsu.cmpe.library.connection;

import javax.jms.Connection;
import javax.jms.JMSException;

import org.fusesource.stomp.jms.StompJmsConnectionFactory;

import edu.sjsu.cmpe.library.util.LibraryConfig;

public class BrokerConnection {
	private static Connection connection= createConnection();;
	
	public static Connection createConnection() {
		String user = LibraryConfig.getLibraryConfig().getApolloUser();
		String password = LibraryConfig.getLibraryConfig().getApolloPassword();
		String host = LibraryConfig.getLibraryConfig().getApolloHost();
		String apolloPort = LibraryConfig.getLibraryConfig().getApolloPort();

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
	
	public void closeConnection() {
		try {
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

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
	
	public static void setConnection(Connection connection) {
		BrokerConnection.connection = connection;
	}
}
