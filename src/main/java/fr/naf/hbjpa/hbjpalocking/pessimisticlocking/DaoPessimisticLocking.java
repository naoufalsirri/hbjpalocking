package fr.naf.hbjpa.hbjpalocking.pessimisticlocking;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;

public class DaoPessimisticLocking {

	private static EntityManagerFactory emf;
	private static EntityManager em1;	
	private static EntityTransaction ts1;
	
	
	
	public DaoPessimisticLocking() {
		 emf = Persistence.createEntityManagerFactory("persistence");    						         	             
		 em1 = emf.createEntityManager();
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
	
	
	
	public void afficherTotalsalaires() {
		//LockModeType.PESSIMISTIC_READ pour permettre aux autres transactions de juste lire 
		//la donnee et ne pas faire des modifs jusqu'a que la transaction courante soit commite
		
        //LockModeType.PESSIMISTIC_WRITE pour ne pas autoriser aux autres transactions 
		//ni lire ni modifier la donnees jusqu'a que la transaction soit commite
		
		
		em1 = emf.createEntityManager();
		ts1= em1.getTransaction();
		ts1.begin();
		//blocage de l'ecriture dans la table  guide jusqu'a l'affichage de toutes les 
		//donnees avec le calcul du total des salaires
		
		//recupere l'objet dans le premier contexte persistence JPA em1
		
		List<Guide> guides =em1.createQuery("select guide from Guide guide").setLockMode(LockModeType.PESSIMISTIC_READ).getResultList();
		//List<Guide> guides =em1.createQuery("select guide from Guide guide").setLockMode(LockModeType.PESSIMISTIC_WRITE).getResultList();
		
		for(Guide guide:guides) {
			System.out.println("Name :"+guide.getNom()+ " , Salaire : "+guide.getSalaire());
		}
	
		long totalSalaire = (long) em1.createQuery("select sum(guide.salaire) from Guide guide").getSingleResult();
		System.out.println("Total :"+totalSalaire);
		
		//em1.createQuery("update Guide as guide set guide.salaire=guide.salaire*4 ").executeUpdate();	
		ts1.commit();
		em1.close();
	
	}
	
	
	
}
