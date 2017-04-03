package src.prog.sys.dyna;

public class Manager {

	/**
	 * 1 Quantum = 1000ns par defaut
	 */
	public static long QUANTUM = 1000;

	private static Manager manager = new Manager();

	public final int prioMax;
	public final int instrMax;
	public final double esMax;
	public final int esDuree; // En unite Quantum
	public final double proba;

	private Manager() {
		prioMax = 0;
		instrMax = 0;
		esMax = 0;
		esDuree = 0;
		proba = 0.5;
	}

	public static Manager getInstance() {
		return manager;
	}

}
