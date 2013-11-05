package edu.sjsu.cmpe.procurement.publisher;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import edu.sjsu.cmpe.procurement.broker.SendMessage;
import edu.sjsu.cmpe.procurement.domain.BookOrder;
import edu.sjsu.cmpe.procurement.util.ProcurementConfig;

public class PublisherJobs {
	private Client client;
	private static final PublisherJobs bookResource = new PublisherJobs(
			ProcurementConfig.getProcurementConfig().getClient());

	private PublisherJobs(Client client) {
		this.client = client;
	}

	public static PublisherJobs getInstance() {
		return bookResource;
	}

	public void submitBookOrder(List<Integer> requestedISBNs) {
		BookOrder bookOrder = new BookOrder();
		bookOrder.setOrder_book_isbns(requestedISBNs);
		WebResource webResource = client
				.resource("http://54.215.210.214:9000/orders");
		String request = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			request = mapper.writeValueAsString(bookOrder);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("Request sent : " + request);
		ClientResponse response = webResource.type("application/json").post(
				ClientResponse.class, request);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}
		System.out
				.println("When request is sent to Publisher,it responds :  \n"
						+ response);
		String output = response.getEntity(String.class);
		System.out.println(output);

		listenBookOrder();
	}

	public void listenBookOrder() {
		String bookOrder = null;
		try {
			WebResource webResource = client
					.resource("http://54.215.210.214:9000/orders/69377");
			ClientResponse response = webResource.accept("application/json")
					.get(ClientResponse.class);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			bookOrder = response.getEntity(String.class);
			System.out.println("Publisher Sent the following request : \n");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (bookOrder != null) {
			SendMessage message = new SendMessage();
			message.sendMessageToBroker(bookOrder);
		} else {
			System.out.println("Null message came from Publisher ");
		}
	}

}
