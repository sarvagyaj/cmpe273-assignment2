package edu.sjsu.cmpe.library.subscriber;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.fusesource.stomp.jms.message.StompJmsMessage;

import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.repository.BookRepositoryInterface;
import edu.sjsu.cmpe.library.util.LibraryConfig;

public class SubscribeMessages {

	public void ListenMessages(BookRepositoryInterface bookRepository) {
		System.out
				.println("-----------------------------------------------Waiting for messages from "
						+ LibraryConfig.getLibraryConfig().getStompTopicName()
						+ "---------------------------------------------------------");
		while (true) {
			Book newBook = new Book();
			try {
				Message msg = Subscriber.getMessageSubscriber().receive();
				if (msg == null) {
					System.out
							.println("No new messages. Exiting due to timeout of 5 seconds.");
					break;
				} else if (msg instanceof TextMessage) {
					String body = ((TextMessage) msg).getText();
					System.out.println("Received message = " + body);
					newBook.setIsbn(msg.getLongProperty("isbn"));
					newBook.setTitle(msg.getStringProperty("title"));
					newBook.setCategory(msg.getStringProperty("category"));
					String decodedUrl=null;
					try {
						decodedUrl = URLDecoder.decode(msg.getStringProperty("coverimage"), "UTF-8");
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}
					try {
						newBook.setCoverimage(new URL(decodedUrl));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					if (newBook != null) {
						bookRepository.updateBook(newBook);
					}
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
	}
}
