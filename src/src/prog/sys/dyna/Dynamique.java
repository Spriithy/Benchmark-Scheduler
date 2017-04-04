package src.prog.sys.dyna;

public class Dynamique {
	
	/**
	 * 1 Quantum = 1000ns par defaut
	 */
	public static long QUANTUM = 1000;

	private static Dynamique dyna = new Dynamique();
	private static Saisie saisie = new Saisie();

	public final int prioMax;
	public final int instrMax;
	public final double esMax;
	public final double esDuree; // En unite Quantum
	public final double proba;

	private Dynamique() {
		prioMax = saisie.prioMax();
		instrMax = saisie.instrMax();
		esMax = saisie.esMax();
		esDuree = saisie.esDuree();
		proba = saisie.proba();
	}

	public static Dynamique getInstance() {
		return dyna;
	}

}
