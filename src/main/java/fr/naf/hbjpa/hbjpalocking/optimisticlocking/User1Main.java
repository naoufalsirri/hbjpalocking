package fr.naf.hbjpa.hbjpalocking.optimisticlocking;

public class User1Main {

	public static long idguide = 1;
	
	public static void main(String[] args) {
		DaoOptimisticLocking dao =new DaoOptimisticLocking();
		//Guide ajouterGuide = dao.ajouterGuide();
		//idguide =ajouterGuide.getId();
		dao.modifierSalarie(idguide,4000);

	}

}
