package edu.sjsu.cmpe.library.ui.views;

import java.util.List;

import com.yammer.dropwizard.views.View;

import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.util.LibraryConfig;

public class HomeView extends View {
	private final List<Book> books;
	private String topicToSubscribe;

	public HomeView(List<Book> books) {
		super("home.mustache");
		this.books = books;
		topicToSubscribe=LibraryConfig.getLibraryConfig().getStompTopicName();
	}

	public List<Book> getBooks() {
		return books;
	}

	public String getTopicToSubscribe() {
		return topicToSubscribe;
	}
		
}
