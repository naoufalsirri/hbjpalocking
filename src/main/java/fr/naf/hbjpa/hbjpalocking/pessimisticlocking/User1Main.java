package fr.naf.hbjpa.hbjpalocking.pessimisticlocking;

public class User1Main {

	public static long idguide = 1;
	
	public static void main(String[] args) {
		
		DaoPessimisticLocking dao =new DaoPessimisticLocking();
		//Guide ajouterGuide = dao.ajouterGuide();
		//idguide =ajouterGuide.getId();
		dao.afficherTotalsalaires();

	}

}
