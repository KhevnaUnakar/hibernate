package com.jpa.jpaexample;

import java.util.*;

import jakarta.persistence.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	int choice, trainerId, traineeId, value;
    	String trainerName, traineeName;
    	Scanner sc=new Scanner(System.in);
    	
    	EntityManagerFactory entityManagerFactory=null;
        EntityManager entityManager=null;
        EntityTransaction transaction=null;
        Query query = null;
        Trainer trainer=null;
        Trainee trainee=null;
        
		List<Trainer> trainerList;
		
		List<Trainee> traineeList;
		
		entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
		entityManager = entityManagerFactory.createEntityManager();
			
		do
        {
			System.out.println();
            System.out.println("Enter 1 to insert record");
            System.out.println("Enter 2 to print records");
            System.out.println("Enter 3 to search records with TrainerId");
            System.out.println("Enter 4 to delete records with TrainerId");
			System.out.println("Enter 5 to search trainee records");
            System.out.println("Enter 6 to delete trainee records with TRAINERid");
            System.out.println("Enter 7 to delete trainee records using TRAINEEid");
			System.out.println("Enter 8 to update trainer records");
			System.out.println("Enter 9 to update trainee records using TRAINERid");
            System.out.println("Enter 0 to exit");
            choice=sc.nextInt();
            
            switch(choice)
            {
            	case 1:
            	{
					trainee = new Trainee();

            		System.out.println("Enter Trainer ID:");
            		trainerId=sc.nextInt();
            		
            		System.out.println("Enter Trainer Name:");
            		trainerName=sc.next();
            		
            		System.out.println("Enter no. of trainees:");
            		value=sc.nextInt();
            		
            		traineeList = new ArrayList<Trainee>(value);
            		for(int i=0; i<value; i++)
            		{
            			System.out.println("Enter Trainee ID:");
                		traineeId=sc.nextInt();
                		
                		System.out.println("Enter Trainee Name:");
                		traineeName=sc.next();

                		trainee.setEid(traineeId);
                		trainee.setEname(traineeName);
                		
                		traineeList.add(trainee);
						entityManager.persist(trainee);
            		}

					trainer = new Trainer();

					trainer.setTrainid(trainerId);
					trainer.setTrainname(trainerName);
					trainer.setTraineeList(traineeList);;


					transaction = entityManager.getTransaction();
					transaction.begin();


					try
            		{
						entityManager.persist(trainer);
						
						transaction.commit();
						System.out.println("Record Inserted");
            		}
            		catch(Exception e)
            		{
            			System.out.println(e.getMessage());
            			transaction.rollback();
            		}
            		break;
	            }
            	
            	case 2:
            	{
            		try
            		{
            			String select = "from Trainer";
            			query = entityManager.createQuery(select);
            			
            			trainerList=query.getResultList();
            			
            			for(int i=0; i<trainerList.size(); i++)
            			{
            				trainer = trainerList.get(i);
            				
            				trainerId = trainer.getTrainid();
            				trainerName = trainer.getTrainname();
            	            				
            				System.out.println("TrainerID: " + trainerId + " Name:" + trainerName);
            				traineeList = trainer.getTrList();
            				
            				Iterator<Trainee> it=traineeList.iterator();
            				
            				while(it.hasNext())
            				{
            					trainee=it.next();
            					
            					traineeId=trainee.getEid();
            					traineeName=trainee.getEname();
            					
            					System.out.println("TraineeID:" + traineeId + " Name:" + traineeName);
            				}
            			}            			
            		}
            		catch(Exception e)
            		{
            			System.out.println(e.getMessage());
            		}
            		break;
            	}
            	
            	case 3:
            	{
            		System.out.println("Enter Trainer ID to search:");
            		int trainId=sc.nextInt();
            		
            		try
            		{
	            		trainer = entityManager.find(Trainer.class, trainId);
	            		
	            		if(trainer!=null)
	            		{
	            			System.out.println("Trainer found.");
	            			
	            			trainerId=trainer.getTrainid();
	            			trainerName=trainer.getTrainname();
	            			System.out.println("TrainerID:" + trainerId + " Name:" + trainerName);
	            			
	            			traineeList=trainer.getTraineeList();
	            			Iterator<Trainee> traineeit=traineeList.iterator();
	            			
	            			while(traineeit.hasNext())
	            			{
	            				trainee=traineeit.next();
	            				
	            				traineeId=trainee.getEid();
	            				traineeName=trainee.getEname();
	            				System.out.println("TraineeID:" + traineeId + " Name:" + traineeName);
	            			}
	            			
	            		}
	            		else
	            		{
	            			System.out.println("Trainer not found.");
	            			
	            		}
            		}
            		catch(Exception e)
            		{
            			System.out.println(e.getMessage());
            		}
            		break;
            	}
            	
            	case 4:
				{
					transaction = entityManager.getTransaction();
					transaction.begin();

            		System.out.println("Enter Trainer ID to search:");
            		int trainId=sc.nextInt();
            		
            		try
            		{
	            		trainer = entityManager.find(Trainer.class, trainId);
	            		
	            		if(trainer!=null)
	            		{

	            			System.out.println("Trainer found.");
	            			entityManager.remove(trainer);
	            			
	            			transaction.commit();
	            			System.out.println("Trainer details deleted.");
	            		}
	            		else
	            		{
	            			System.out.println("Trainer not found.");
	            		}
            		}
            		catch(Exception e)
            		{
            			System.out.println(e.getMessage());
            			transaction.rollback();
            		}
            		break;
            	}

				case 5:
				{
					System.out.println("Enter Trainee ID to search:");
					traineeId = sc.nextInt();

					trainee = entityManager.find(Trainee.class,traineeId);

					if(trainee!=null)
					{
						traineeName=trainee.getEname();

						trainer = trainee.getTrainer();
						trainerId=trainer.getTrainid();
						trainerName = trainer.getTrainname();

						System.out.println("TraineeID:" + traineeId + " Name:" + traineeName + " TRAINER: id:" + trainerId + " Name:" + trainerName);
					}
					else
					{
						System.out.println("Trainee not found.");
					}
					break;
				}
            	
            	case 6:
            	{
					transaction=entityManager.getTransaction();
					transaction.begin();

					System.out.println("Enter Trainer ID to delete:");
            		int trainId=sc.nextInt();
            		
            		try
            		{
	        			trainer = entityManager.find(Trainer.class, trainId);
	            		
	            		if(trainer!=null)
	            		{
	            			System.out.println("Trainer found.");
	            			
	            			traineeList=trainer.getTraineeList();
	            			for(int i=0; i<traineeList.size(); i++)
	            			{
	            				Trainee t=traineeList.get(i);
	            				int tID=t.getEid();
	            				
	            				trainee = entityManager.find(Trainee.class, tID);
	            				entityManager.remove(trainee);
	            			}
	            			transaction.commit();
	        				System.out.println("Trainee record deleted.");
	            		}
	            		else
	            		{
	            			System.out.println("Trainer Not Found");
	            		}
            		}
            		catch(Exception e)
            		{
            			System.out.println(e.getMessage());
            			transaction.rollback();
            		}
            		break;
            	}
            	
            	case 7:
            	{
            		transaction=entityManager.getTransaction();
            		transaction.begin();

            		System.out.println("Enter trainee id to be deleted");
            		int tId=sc.nextInt();
            		
            		try
            		{
	            		trainee = entityManager.find(Trainee.class, tId);
	            		
	            		if(trainee!=null)
	            		{
	            			System.out.println("Trainee record found.");
	            			
	            			entityManager.remove(trainee);            			
	            			transaction.commit();
	            			System.out.println("Trainee record DELETED.");
	            		}
	            		else
	            		{
	            			System.out.println("Trainee record NOT found.");
	            		}
            		}
            		catch(Exception e)
            		{
            			System.out.println(e.getMessage());
            			transaction.rollback();
            		}
            		break;
            	}

				case 8:
				{
					transaction=entityManager.getTransaction();
					transaction.begin();

					System.out.println("Enter trainer id to be updated:");
					int t_Id=sc.nextInt();

					try
					{
						trainer = entityManager.find(Trainer.class, t_Id);

						if(trainer!=null)
						{
							System.out.println("Enter updated trainer id:");
							trainerId=sc.nextInt();

							System.out.println("Enter updated trainer name:");
							trainerName=sc.next();

							trainer.setTrainid(trainerId);
							trainer.setTrainname(trainerName);

							entityManager.persist(trainer);
							transaction.commit();

							System.out.println("Trainer updated.");
						}
						else
						{
							System.out.println("Enter valid trainer id.");
						}
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
					}
					break;
				}

				case 9:
				{
					transaction = entityManager.getTransaction();
					transaction.begin();


					System.out.println("Enter trainer id:");
					int t_Id=sc.nextInt();

					try {
						trainer = entityManager.find(Trainer.class, t_Id);

						if (trainer != null) {
							traineeList = trainer.getTraineeList();

							for (int i = 0; i < traineeList.size(); i++) {
								Trainee tnee = traineeList.get(i);
								int tne_id = tnee.getEid();

								trainee = entityManager.find(Trainee.class, tne_id);

								if (trainee != null) {
									System.out.println("Enter updated trainee name:");
									traineeName = sc.next();
									trainee.setEname(traineeName);

									entityManager.persist(trainee);
									transaction.commit();
									System.out.println("Trainee names updated.");
								} else {
									System.out.println("Enter valid trainee id.");
								}
							}
						}
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
						transaction.rollback();
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
