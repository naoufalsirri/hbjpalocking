package fr.naf.hbjpa.hbjpalocking.optimisticlocking;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.OptimisticLockException;
import javax.persistence.Persistence;




public class DaoOptimisticLocking {

	private static EntityManagerFactory emf;
	private static EntityManager em1;	
	private static EntityManager em2;
	private static EntityTransaction ts1;
	private static EntityTransaction ts2;
	
	
	public DaoOptimisticLocking() {
		
		
		 emf = Persistence.createEntityManagerFactory("persistence");    						         
	     //premier contexte persistence		          
		 em1 = emf.createEntityManager();
		 ts1= em1.getTransaction();
		 
		 //dexieme contexte persistence		    	         
		 em2 = emf.createEntityManager();
		 ts2= em2.getTransaction();
		 ts1= em1.getTransaction();
		 
		
	}
	
	public Guide ajouterGuide() {
		Guide guide =new Guide();
		guide.setNom("Farad");
		guide.setSalaire(3000);		
		ts1.begin();
        em1.persist(guide);                 
        ts1.commit(); 
		return guide;
	}
	
	
	
	public void modifierSalarie(long guideid,int salaire) {
		
		//ce test de Optimistic locking est pour ne pas permetre à d'autres users de modifier la meme donnée apres
		
		
		em1 = emf.createEntityManager();
		ts1= em1.getTransaction();
		
		//recupere l'objet dans le premier contexte persistence JPA em1
		Guide guide =em1.find(Guide.class, guideid);
		em1.close();	
		
		
		//modification des objets en dehors du contexte c'est à dire des objets dectaches
		guide.setSalaire(salaire);
		
		
		try {
			
			//sotckage dans la base dans un autre contexte de persistence
			ts2.begin();
			em2.merge(guide);
			ts2.commit();
			
		}catch(OptimisticLockException ex) {
			if(ts2!=null)
			{
				ts2.rollback();
				System.out.println("enregistrement est modifie par un autre utilisateur");
			}
		}
		
	
	}
	
	
	
}
