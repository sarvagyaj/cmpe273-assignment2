package edu.sjsu.cmpe.procurement.broker;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.sjsu.cmpe.procurement.domain.Book;

public class SendMessage {

	public void sendMessageToBroker(String bookOrder) {

		JSONObject jsonObj;
		try {
			jsonObj = new JSONObject(bookOrder);
			JSONArray jsonArray = jsonObj.getJSONArray("shipped_books");
			for (int i = 0; i < jsonArray.length(); i++) {
				Book shippedBook = new Book();
				shippedBook.setIsbn(jsonArray.getJSONObject(i).getLong("isbn"));
				shippedBook.setTitle(jsonArray.getJSONObject(i).getString(
						"title"));
				shippedBook.setCategory(jsonArray.getJSONObject(i).getString(
						"category"));
				URL url;
				try {
					url = new URL(jsonArray.getJSONObject(i).getString(
							"coverimage"));
					shippedBook.setCoverimage(url);
				} catch (MalformedURLException e2) {
					e2.printStackTrace();
				}
				
				String encodedUrl=null;
				try {
					encodedUrl = URLEncoder.encode(jsonArray.getJSONObject(i).getString(
							"coverimage"), "UTF-8");
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}

				String mess = shippedBook.getIsbn() + ":\""
						+ shippedBook.getTitle() + "\":\""
						+ shippedBook.getCategory() + "\":\""
						+ encodedUrl+ "\"";
				System.out.println(mess);
				try {
					MessageProducer messageProducer = Producer
							.getMessageProducer(shippedBook.getCategory());
					TextMessage msg = Producer.getSession().createTextMessage(
							mess);
					msg.setLongProperty("id", System.currentTimeMillis());
					msg.setLongProperty("isbn", shippedBook.getIsbn());
					msg.setStringProperty("title", shippedBook.getTitle());
					msg.setStringProperty("category", shippedBook.getCategory());
					msg.setStringProperty("coverimage",
							encodedUrl);
					messageProducer.send(msg);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
