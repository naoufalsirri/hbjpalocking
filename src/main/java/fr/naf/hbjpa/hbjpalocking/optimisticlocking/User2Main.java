package fr.naf.hbjpa.hbjpalocking.optimisticlocking;

public class User2Main {

	public static void main(String[] args) {
		DaoOptimisticLocking dao =new DaoOptimisticLocking();
		System.out.println("aaaa");
		dao.modifierSalarie(18,5000);

	}

}
