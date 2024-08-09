package org.example;

import jakarta.persistence.*;

@Entity
public class Book {

	@Id
	private int book_id;
	private String book_name;
	
//	@ManyToOne
//	Student student;

	public int getBook_id() {
		return book_id;
	}

	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	
}
