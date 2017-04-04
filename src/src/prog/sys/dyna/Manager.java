package src.prog.sys.dyna;

public class Manager {

	/**
	 * 1 Quantum = 1000ns par defaut
	 */
	public static long QUANTUM = 1000;

	private static Manager manager = new Manager();
	private static Is is = new Is();

	public final int prioMax;
	public final int instrMax;
	public final double esMax;
	public final double esDuree; // En unite Quantum
	public final double proba;

	private Manager() {
		prioMax = is.prioMax();
		instrMax = is.instrMax();
		esMax = is.esMax();
		esDuree = is.esDuree();
		proba = is.proba();
	}

	public static Manager getInstance() {
		return manager;
	}

}
