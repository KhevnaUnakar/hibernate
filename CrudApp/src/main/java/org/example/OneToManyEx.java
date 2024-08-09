package org.example;

import java.util.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;


public class OneToManyEx {

	public static void main(String[] args) 
	{
		EntityManagerFactory factory = null;
		EntityManager entityManager = null;
		EntityTransaction transaction = null;
		List<Book> bookList=null;
		Person person = null;
		Book book1 = null;
		Book book2 = null;
		
		factory = Persistence.createEntityManagerFactory("persistence");
		entityManager = factory.createEntityManager();
		transaction = entityManager.getTransaction();
		transaction.begin();
		
		book1 = new Book();
		book1.setBook_id(1);
		book1.setBook_name("Java");
		
		book2 = new Book();
		book2.setBook_id(2);
		book2.setBook_name("Jonathan Seagull");
		

		bookList = new ArrayList<Book>();
		bookList.add(book1);
		bookList.add(book2);
		
		person = new Person();
		person.setId(52);
		person.setName("Khevna");
		person.setBookList(bookList);
		
		
		entityManager.persist(person);

		transaction.commit();
		
		System.out.println("Record Inserted");
	}

}
