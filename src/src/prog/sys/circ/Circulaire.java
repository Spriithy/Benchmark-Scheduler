package src.prog.sys.dyna;

import src.prog.sys.dyna.Manager;
import src.prog.sys.dyna.Saisie;

public class Circulaire {

	/**
	 * 1 Quantum = 1000ns par defaut
	 */
	public static long QUANTUM = 1000;

	private static Ciruclaire manager = new Manager();
	private static Saisie saisie = new Saisie();

	public final int instrMax;
	public final double esMax;
	public final double esDuree; // En unite Quantum
	public final double proba;

	private Manager() {
		instrMax = saisie.instrMax();
		esMax = saisie.esMax();
		esDuree = saisie.esDuree();
		proba = saisie.proba();
	}

	public static Manager getInstance() {
		return manager;
	}

}
