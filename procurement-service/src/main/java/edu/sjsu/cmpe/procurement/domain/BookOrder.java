package edu.sjsu.cmpe.procurement.domain;

import java.util.ArrayList;
import java.util.List;

public class BookOrder {
	private final String id = "69377";
	private List<Integer> order_book_isbns = new ArrayList<Integer>();

	public String getId() {
		return id;
	}

	public List<Integer> getOrder_book_isbns() {
		return order_book_isbns;
	}

	public void setOrder_book_isbns(List<Integer> order_book_isbns) {
		this.order_book_isbns = order_book_isbns;
	}

}
