package src.prog.sys;

public abstract class Manager {
	
	/**
	 * Un Quantum est par defaut de 1000ns
	 */
	public long quantum = 1000;

	public int prioMax;
	public int instrMax;
	public double esMax;
	public double esDuree; // En unite Quantum
	public double proba;

}
