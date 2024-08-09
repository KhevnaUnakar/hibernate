package com.jpa.jpaexample;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class OneToManySystem
{

	public static void main(String[] args) 
	{
		int value;
		int choice;
		int e_id;
		String e_name;
		int c_id;
		String c_name;
		EntityTransaction tm = null;
		EntityManager entityManager = null;
		EntityManagerFactory entityManagerFactory = null;
		Company company = null;
		Emp emp = null;
		List<Emp> elist;
		List<Company> clist;
		
		Scanner sc = new Scanner(System.in);
		
		entityManagerFactory=Persistence.createEntityManagerFactory("persistence");
	    entityManager=entityManagerFactory.createEntityManager();
	    
	    tm = entityManager.getTransaction();

	    
	    
	    do
	    {
	    	System.out.println("Enter 1 to insert company record.");
	    	System.out.println("Enter 2 to insert employee record.");
	    	System.out.println("Enter 3 to print record via company.");
	    	System.out.println("Enter 4 to print record via employee.");
	    	System.out.println("Enter 5 to insert emp in an existing company");
			System.out.println("Enter 6 to update name of emp");
			System.out.println("Enter 7 to update name of company");
			System.out.println("Enter 8 to search data via company");
			System.out.println("Enter 9 to search data via employee");
			System.out.println("Enter 10 to delete company");
			System.out.println("Enter 11 to delete employee in company");
			System.out.println("Enter 0 to exit.");
	    	System.out.println("Enter your choice");
	    	choice = sc.nextInt();
	    	
	    	switch(choice)
	    	{
		    	case 1:
		    	{
		    		company = new Company();
		    		
		    		System.out.println("Enter company id: ");
		    		c_id=sc.nextInt();
		    		
		    		System.out.println("Enter company name: ");
		    		c_name  = sc.next();
		    		
		    		company.setC_id(c_id);
		    		company.setC_name(c_name);
		    		
		    		System.out.println("Enter number of employees: ");
		    		value = sc.nextInt();
		    		
		    		try
		    		{
		    			tm = entityManager.getTransaction();
		    			tm.begin();
		    		
			    		elist = new ArrayList<Emp>(value);
			    		for(int i=0; i<value; i++)
			    		{
			    			System.out.println("Enter employee id: ");
			    			e_id = sc.nextInt();
			    			
			    			System.out.println("Enter employee name: ");
			    			e_name = sc.next();
			    			
			    			emp = new Emp();
			    			emp.setE_id(e_id);
			    			emp.setE_name(e_name);
			    			
			    			elist.add(emp);
			    			entityManager.persist(emp);
			    		}
			    		
			    		company.setEmplist(elist);
			    		entityManager.persist(company);
			    		
			    		tm.commit();
		    			System.out.println("Record Inserted");
		    		}
		    		catch(Exception e)
		    		{
		    			System.out.println("--error in catch block--"+e.getMessage());
		    		}
		    		
		    		break;
		    	}
		    	case 3:
		    	{
		    		String hql = "from Company";
		    		
		    		Query query = entityManager.createQuery(hql);
		    		
		    		clist = query.getResultList();
		    		
		    		for(int i=0; i<clist.size(); i++)
		    		{
		    			company = clist.get(i);
		    			System.out.println("Company id:" + company.getC_id() + " Company name:" + company.getC_name());
		    			
		    			elist = company.getEmplist();
		    			for(int j=0; j<elist.size();j++)
		    			{
		    				emp = elist.get(j);
		    				System.out.println("Emp id:" + emp.getE_id() + " Emp name:" + emp.getE_name());
		    			}
		    		}
		    		break;
		    	}

				case 4:
				{
					System.out.println("Enter emp id: ");
					int id = sc.nextInt();

					emp = entityManager.find(Emp.class, id);

					if(emp!=null)
					{
						e_id = emp.getE_id();
						e_name = emp.getE_name();

						company = emp.getCompany();

						c_id = company.getC_id();
						c_name = company.getC_name();

						System.out.println("EMPLOYEE:" + " ID: " + e_id + " NAME:" + e_name);
						System.out.println("COMPANY:" + " ID: " + c_id + " NAME:" + c_name);
					}
					else
					{
						System.out.println("Does not exist");
					}
					break;
				}

				case 5:
				{
					try
					{
						System.out.println("Enter emp id: ");
						e_id = sc.nextInt();

						emp = entityManager.find(Emp.class, e_id);

						if(emp!=null)
						{
							System.out.println("User already exsist.");
						}

						else
						{
							tm = entityManager.getTransaction();
							tm.begin();

							emp = new Emp();
							System.out.println("Enter emp name: ");
							e_name = sc.next();

							emp.setE_id(e_id);
							emp.setE_name(e_name);

							System.out.println("Enter company id: ");
							c_id = sc.nextInt();

							company = entityManager.find(Company.class, c_id);
							if(company!=null)
							{
								emp.setCompany(company);
							}

							else
							{
								System.out.println("Enter company name: ");
								c_name = sc.next();

								company = new Company();

								company.setC_id(c_id);
								company.setC_name(c_name);
							}

							entityManager.persist(emp);

							tm.commit();
							System.out.println("Record Inserted");
						}
					}
					catch(Exception e)
					{
						System.out.println("--error in catch block--"+e.getMessage());
						tm.rollback();
					}
					break;
				}

				case 6:
				{
					System.out.println("Enter emp id to change name:");
					int id= sc.nextInt();

					emp = entityManager.find(Emp.class,id);

					if(emp!=null)
					{
						try {
							tm = entityManager.getTransaction();
							tm.begin();

							System.out.println("Enter new name: ");
							e_name = sc.next();
							emp.setE_name(e_name);

							entityManager.persist(emp);

							tm.commit();
							System.out.println("Record Updated");
						}
						catch(Exception e)
						{
							System.out.println("--error in catch block--"+e.getMessage());
							tm.rollback();
						}
					}
					else
					{
						System.out.println("Employee does not exist");
					}
					break;
				}

				case 7:
				{
					System.out.println("Enter company id to be updated:");
					c_id = sc.nextInt();

					company = entityManager.find(Company.class,c_id);

					if(company!=null)
					{
						try
						{
							tm = entityManager.getTransaction();
							tm.begin();

							System.out.println("Enter new name: ");
							c_name = sc.next();

							company.setC_name(c_name);

							entityManager.persist(company);
							tm.commit();
							System.out.println("Record Updated");
						}
						catch(Exception e)
						{
							System.out.println("--error in catch block--"+e.getMessage());
							tm.rollback();
						}
					}
					else
					{
						System.out.println("Company does not exist");
					}
					break;
				}

				case 8:
				{
					System.out.println("Enter company id to be searched:");
					int id = sc.nextInt();

					company = entityManager.find(Company.class,id);

					if(company!=null)
					{
						c_id = company.getC_id();
						c_name = company.getC_name();

						System.out.println("Company id:" + c_id + " Company name:" + c_name + " EMPLOYEES:");

						elist = company.getEmplist();

						for(int i=0; i<elist.size(); i++)
						{
							emp = elist.get(i);

							e_id = emp.getE_id();
							e_name = emp.getE_name();

							System.out.println("Emp id:" + e_id + " Emp name:" + e_name);
						}
					}
					else
					{
						System.out.println("Company not found!");
					}
					break;
				}

				case 9:
				{
					System.out.println("Enter emp id to be searched:");
					e_id = sc.nextInt();

					emp = entityManager.find(Emp.class,e_id);

					if(emp!=null)
					{
						e_name = emp.getE_name();

						company = emp.getCompany();
						c_id = company.getC_id();
						c_name = company.getC_name();

						System.out.println("Emp id:" + e_id + "Emp name:" + e_name + " Company id:" + c_id + " Company name:" + c_name);
					}
					else
					{
						System.out.println("Employee not found!");
					}
					break;
				}

				case 10:
				{
					System.out.println("Enter company id to be deleted:");
					c_id = sc.nextInt();

					company = entityManager.find(Company.class,c_id);

					if(company!=null)
					{
						try
						{
							tm = entityManager.getTransaction();
							tm.begin();

							entityManager.remove(company);

							tm.commit();
							System.out.println("Company Record Deleted");
						}
						catch(Exception e)
						{
							System.out.println("--error in catch block--"+e.getMessage());
							tm.rollback();
						}
					}
					else
					{
						System.out.println("Company not found!");
					}
					break;
				}

				case 11:
				{
					System.out.println("Enter company id of the to-be-deleted employee:");
					c_id = sc.nextInt();

					company = entityManager.find(Company.class,c_id);

					if(company!=null)
					{
						System.out.println("Enter emp id to be deleted:");
						e_id = sc.nextInt();

						emp = entityManager.find(Emp.class, e_id);

						if (emp != null) {
							try {
								tm = entityManager.getTransaction();
								tm.begin();

								entityManager.remove(emp);
								tm.commit();
								System.out.println("Emp Record Deleted");
							} catch (Exception e) {
								System.out.println("--error in catch block--" + e.getMessage());
								tm.rollback();
							}
						} else {
							System.out.println("Employee not found!");
						}
					}
					else
					{
						System.out.println("Company not found!");
					}
					break;
				}

				case 0:
				{
					System.out.println("Exited");
					System.exit(0);
				}
	    	}
	    }
	    while(true);
	    
	}

}

//Emp mein manytoone
// insert from emp side
//view from emp