package edu.sjsu.cmpe.procurement.config;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.jersey.api.client.Client;
import com.yammer.dropwizard.client.JerseyClientConfiguration;
import com.yammer.dropwizard.config.Configuration;

public class ProcurementServiceConfiguration extends Configuration {
	@NotEmpty
	@JsonProperty
	private String stompQueueName;

	@NotEmpty
	@JsonProperty
	private String stompTopicName;
	private String stompTopicComputer;
	private String stompTopicComics;
	private String stompTopicManagement;
	private String stompTopicSelfImprovement;

	private String apolloUser;
	private String apolloPassword;
	private String apolloHost;
	private String apolloPort;

	@Valid
	@NotNull
	@JsonProperty
	private JerseyClientConfiguration httpClient = new JerseyClientConfiguration();

	public JerseyClientConfiguration getJerseyClientConfiguration() {
		return httpClient;

	}

	private Client client;

	/**
	 * @return the stompQueueName
	 */
	public String getStompQueueName() {
		return stompQueueName;
	}

	/**
	 * @param stompQueueName
	 *            the stompQueueName to set
	 */
	public void setStompQueueName(String stompQueueName) {
		this.stompQueueName = stompQueueName;
	}

	/**
	 * @return the stompTopicName
	 */
	public String getStompTopicName() {
		return stompTopicName;
	}

	/**
	 * @param stompTopicName
	 *            the stompTopicName to set
	 */
	public void setStompTopicName(String stompTopicName) {
		this.stompTopicName = stompTopicName;
	}

	public String getStompTopicComputer() {
		return stompTopicComputer;
	}

	public void setStompTopicComputer(String stompTopicComputer) {
		this.stompTopicComputer = stompTopicComputer;
	}

	public String getStompTopicComics() {
		return stompTopicComics;
	}

	public void setStompTopicComics(String stompTopicComics) {
		this.stompTopicComics = stompTopicComics;
	}

	public String getStompTopicManagement() {
		return stompTopicManagement;
	}

	public void setStompTopicManagement(String stompTopicManagement) {
		this.stompTopicManagement = stompTopicManagement;
	}

	public String getStompTopicSelfImprovement() {
		return stompTopicSelfImprovement;
	}

	public void setStompTopicSelfImprovement(String stompTopicSelfImprovement) {
		this.stompTopicSelfImprovement = stompTopicSelfImprovement;
	}

	public String getApolloUser() {
		return apolloUser;
	}

	public void setApolloUser(String apolloUser) {
		this.apolloUser = apolloUser;
	}

	public String getApolloPassword() {
		return apolloPassword;
	}

	public void setApolloPassword(String apolloPassword) {
		this.apolloPassword = apolloPassword;
	}

	public String getApolloHost() {
		return apolloHost;
	}

	public void setApolloHost(String apolloHost) {
		this.apolloHost = apolloHost;
	}

	public String getApolloPort() {
		return apolloPort;
	}

	public void setApolloPort(String apolloPort) {
		this.apolloPort = apolloPort;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
