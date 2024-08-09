package org.example;

import java.util.*;
import jakarta.persistence.*;
//import jakarta.transaction.TransactionManager;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        int choice;
        Scanner sc=new Scanner(System.in);

        EntityManagerFactory entityManagerFactory=null;
        EntityManager entityManager=null;
        EntityTransaction tm=null;

        entityManagerFactory=Persistence.createEntityManagerFactory("persistence");

        entityManager=entityManagerFactory.createEntityManager();

        do
        {
            System.out.println("Enter 1 to insert record.");
            System.out.println("Enter 2 to delete record.");
            System.out.println("Enter 3 to update record.");
            System.out.println("Enter 4 to view record.");
            System.out.println("Enter 5 to search record.");
            System.out.println("Enter 6 to exit.");

            System.out.println("Enter your choice:");
            choice=sc.nextInt();

            switch(choice)
            {

                case 1:
                {
                    tm=entityManager.getTransaction();
                    tm.begin();
                    try
                    {
	                    System.out.println("Enter ID:");
	                    int id=sc.nextInt();

	                    System.out.println("Enter name:");
	                    String name=sc.next();

	                    System.out.println("Enter salary:");
	                    float salary=sc.nextFloat();

	                    AbcEmp AbcEmp=new AbcEmp();
	                    AbcEmp.setId(id);
	                    AbcEmp.setName(name);
	                    AbcEmp.setSalary(salary);

	                    entityManager.persist(AbcEmp);
	                    tm.commit();

	                    System.out.println("Record Inserted.");
                    }
                    catch(Exception e)
                    {
                    	tm.rollback();
                    	System.out.println(e.getMessage());
                    }
                    break;
                }

                case 2:
                {
                	tm=entityManager.getTransaction();
                    tm.begin();

                    try
                    {
	                    System.out.println("Enter ID to be deleted:");
	                    int id=sc.nextInt();

	                    AbcEmp e1=entityManager.find(AbcEmp.class,id);
	                    entityManager.remove(e1);

	                    entityManager.getTransaction().commit();
	                    System.out.println("Record Deleted.");
                    }
                    catch(Exception e)
                    {
                    	tm.rollback();
                    	System.out.println(e.getMessage());
                    }
                    break;
                }

                case 3:
                {
                	tm=entityManager.getTransaction();
                    tm.begin();

                    try
                    {
	                    System.out.println("Enter ID of record to be updated:");
	                    int id=sc.nextInt();

	                    System.out.println("Enter updated name:");
	                    String name=sc.next();

	                    System.out.println("Enter updated salary:");
	                    float sal=sc.nextFloat();

	                    AbcEmp e2=entityManager.find(AbcEmp.class,id);
	                    e2.setName(name);
	                    e2.setSalary(sal);

	                    entityManager.persist(e2);
	                    tm.commit();
	                    System.out.println("Record Updated.");
                    }
                    catch(Exception e)
                    {
                    	tm.rollback();
                    	System.out.println(e.getMessage());
                    }
                    break;
                }

                case 4:
                {
                    String hql="from AbcEmp";
                    Query query=entityManager.createQuery(hql);

                    ArrayList<AbcEmp> empList=(ArrayList<AbcEmp>) query.getResultList();

                    for(int i=0; i<empList.size(); i++)
                    {
                        AbcEmp e=empList.get(i);
                        System.out.println("ID:" + e.getId() + " Name:" + e.getName() + " Salary:" + e.getSalary());
                        System.out.println("------------");
                    }
                    break;
                }

                case 5:
                {
                    System.out.println("Enter id:");
                    int id=sc.nextInt();

                    AbcEmp emp=entityManager.find(AbcEmp.class,id);

                    if(emp!=null)
                    {
                        System.out.println("ID:" + emp.getId() + " Name:" + emp.getName() + " Salary:" + emp.getSalary());
                        System.out.println("------------");
                    }
                    else
                    {
                        System.out.println("Record not found.");
                    }
                    break;
                }

                case 6:
                {
                    System.exit(0);
                }

                default:
                {
                    System.out.println("Wrong number input.");
                }
            }
        }
        while(choice!=0);
    }
}
