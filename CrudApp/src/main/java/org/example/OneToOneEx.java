package org.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class OneToOneEx {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		int choice, id, bid;
		String name, b_name;
        Scanner sc=new Scanner(System.in);

        EntityManagerFactory entityManagerFactory=null;
        EntityManager entityManager=null;
        EntityTransaction tm=null;
        Student student=null;
        Library library=null;
  

        entityManagerFactory=Persistence.createEntityManagerFactory("persistence");
    
        entityManager=entityManagerFactory.createEntityManager();

        do
        {
            System.out.println("Enter 1 to insert record.");
            System.out.println("Enter 2 to delete Book record.");
            System.out.println("Enter 3 to delete Student record from Library.");
            System.out.println("Enter 4 to set Student null in Library.");
            System.out.println("Enter 5 to view Student record.");
            System.out.println("Enter 6 to view Library record.");
            System.out.println("Enter 7 to setStudent().");
            System.out.println("Enter 8 to issue book to student.");
            System.out.println("Enter 9 to delete Student record--find().");
            System.out.println("Enter 10 to exit.");

            System.out.println("Enter your choice:");
            choice=sc.nextInt();

            switch(choice)
            {

                case 1:
                {
                	student=new Student();
                	
                	System.out.println("Enter Student_id:");
                	id=sc.nextInt();
                	
                	System.out.println("Enter Student name:");
                	name=sc.next();
                	
                	student.setId(id);
                	student.setName(name);
                	
                	library=new Library();
                	
                	System.out.println("Enter Book_id:");
                	bid=sc.nextInt();
                	
                	System.out.println("Enter Book name:");
                	b_name=sc.next();
                	
                	library.setB_id(bid);
                	library.setB_name(b_name);
                	library.setStudent(student);
                	
                	try
                	{
                		tm=entityManager.getTransaction();
                        tm.begin();
                        
                        //entityManager.persist(student);
                        entityManager.persist(library);
                        
                        tm.commit();
                        System.out.println("Record Inserted");
                	}
                	catch(Exception e)
                	{
                		System.out.println(e.getMessage());
                		tm.rollback();
                	}
                	break;
                }
                
                case 2:
                {
                	System.out.println("Enter Book_ID to be deleted:");
                	id=sc.nextInt();
                	
                	try
                	{
                    	tm=entityManager.getTransaction();
                    	tm.begin();
                    	
                    	//student = entityManager.find(Student.class, id);

                    	library = entityManager.find(Library.class, id);
                    	entityManager.remove(library);

                    	tm.commit();
                    	System.out.println("Record Deleted");
                	}
                	catch(Exception e)
                	{
                		System.out.println(e.getMessage());
                		tm.rollback();
                	}
                	break;
                }
                
                case 3:
                {
                	System.out.println("Enter Student_ID to be deleted:");
                	id=sc.nextInt();
                	
                	try
                	{
                		tm=entityManager.getTransaction();
                		tm.begin();

                    	String hql="delete from Library where student.id =:kid";
                    	
                		Query query = entityManager.createQuery(hql);
                		query.setParameter("kid", id);
                		
                		int r=query.executeUpdate();
                		
                		tm.commit();
                		
                		if(r!=0)
                		{
                			System.out.println("Record Deleted");
                		}
                		else
                		{
                			System.out.println("Record Not Deleted");
                		}
                	}
                	catch(Exception e)
                	{
                		System.out.println(e.getMessage());
                		tm.rollback();
                	}
                	break;
                }
                
                case 4:
                {
                	System.out.println("Enter Student ID to be set null:");
                	id=sc.nextInt();
                	
                	try
                	{
                		tm=entityManager.getTransaction();
                		tm.begin();
                		
                		String hql="update Library set student=:val where student.id=:sid";
                		
                		Query query=entityManager.createQuery(hql);
                		query.setParameter("val", null);
                		query.setParameter("sid", id);
                		
                		int upd=query.executeUpdate();
                		
                		tm.commit();
                		
                		if(upd!=0)
                		{
                			System.out.println("Record Updated");
                		}
                		else
                		{
                			System.out.println("Record Not Updated");
                		}
                	}
                	catch(Exception e)
                	{
                		System.out.println(e.getMessage());
                		tm.rollback();
                	}
                	break;
                }
                
                case 5:
                {
                	String hql="from Student";
                    Query query=entityManager.createQuery(hql);

                    ArrayList<Student> studentList=(ArrayList<Student>) query.getResultList();

                    for(int i=0; i<studentList.size(); i++)
                    {
                        student=studentList.get(i);
                        id=student.getId();
                        name=student.getName();
                        
                        
                        System.out.println("Student ID:" + id + " Student Name:" + name);
                        System.out.println("------------");
                    }
                	break;
                }
                
                case 6:
                {
                	String hql="from Library";
                    Query query=entityManager.createQuery(hql);

                    ArrayList<Library> libList=(ArrayList<Library>) query.getResultList();

                    for(int i=0; i<libList.size(); i++)
                    {
                        library=libList.get(i);
                        bid=library.getB_id();
                        b_name=library.getB_name();
                        
                        student=library.getStudent();
                        if(student!=null)
                        {
	                        id=student.getId();
	                        name=student.getName();
	                        System.out.println("Book ID:" + bid + " Book Name:" + b_name + " -->Student ID:" + id + " Student Name:" + name);
                        }
                        else
                        {
                        	System.out.println("Book ID:" + bid + " Book Name:" + b_name + " ---  not issued by anyone");
                        }
                        
                        System.out.println("------------");
                    }
                	break;
                }
                
                case 7:
                {
                	System.out.println("Enter bookID:");
                	bid=sc.nextInt();
                	
                	System.out.println("Enter Student ID:");
                	id=sc.nextInt();
                	
                	try
                	{
                		tm=entityManager.getTransaction();
                		tm.begin();
                		
                		library=entityManager.find(Library.class, bid);

                		student=entityManager.find(Student.class, id);
                		
                		if(student==null)
                		{
                			System.out.println("Student does not exit");
                		}
                		
                		if(library==null)
                		{
                			System.out.println("Book is not available");
                		}
                		else
                		{
                			library.setStudent(student);
                		}
                		
                		tm.commit();
                		
                		System.out.println("Book issued by student");
                	}
                	catch(Exception e)
                	{
                		System.out.println(e.getMessage());
                		tm.rollback();
                	}
                	break;
                }
                
                case 8:
                {
                	try
                	{
                		tm=entityManager.getTransaction();
                		tm.begin();
                		
	                	//print student records
	                	String studQuery="from Student";
	                	Query query1 = entityManager.createQuery(studQuery);
	                
	                	ArrayList<Student> stuList=(ArrayList<Student>) query1.getResultList();
	
	                    for(int i=0; i<stuList.size(); i++)
	                    {
	                        student=stuList.get(i);
	                        id=student.getId();
	                        name=student.getName();
	                                        		
	                		System.out.println("ID:" + id + " Name=" + name);
	                	}
	                	
	                	System.out.println("Enter student ID:");
	                	id=sc.nextInt();
	                	
	                	//check if student exists
	                	student = entityManager.find(Student.class,id);
	                	
	                	if(student==null)
	                	{
	                		System.out.println("Student record not found.");
	                	}
	                	
	                	//print library records
	                	String libQuery="from Library";
	                    Query query2=entityManager.createQuery(libQuery);
	
	                    ArrayList<Library> libList=(ArrayList<Library>) query2.getResultList();
	
	                    for(int i=0; i<libList.size(); i++)
	                    {
	                        library=libList.get(i);
	                        bid=library.getB_id();
	                        b_name=library.getB_name();
	                        
	                        System.out.println("Book ID:" + bid + " Book Name:" + b_name);
	                    }
	                	
	                	System.out.println("Enter book ID:");
	                	bid=sc.nextInt();
	                	
	                	//check if book exists
	                	library = entityManager.find(Library.class, bid);
	                	
	                	if(library==null)
	                	{
	                		System.out.println("Book record not found.");
	                	}
	                	else
	                	{
		                	library.setStudent(student);
		                	tm.commit();
		                	System.out.println("Student Issued book");
	                	}
                	}
                	catch(Exception e)
                	{
                		System.out.println(e.getMessage());
                		tm.rollback();
                	}
                	
                	
                	
                	break;
                }
                
                case 9:
                {
                	System.out.println("Enter student_ID to be deleted:");
                	id=sc.nextInt();
                	
                	try
                	{
                    	tm=entityManager.getTransaction();
                    	tm.begin();

                    	student = entityManager.find(Student.class, id);
                    	entityManager.remove(student);

                    	tm.commit();
                    	System.out.println("Record Deleted");
                	}
                	catch(Exception e)
                	{
                		System.out.println(e.getMessage());
                		tm.rollback();
                	}
                	break;
                }
                
                case 10:
                {
                	System.out.println("Exiting...");
                	System.exit(0);
                }
            }
            
        }
        while(choice!=0);
	}

}
