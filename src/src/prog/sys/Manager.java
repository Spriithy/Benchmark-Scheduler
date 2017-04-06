package src.prog.sys;

public abstract class Manager {
	
	/**
	 * Un Quantum est par defaut de 1ms
	 */
	public long quantum = 1;

	public int pMax;
	public int prioMax;
	public int instrMax;
	public double esMax;
	public double esDuree; // En unite Quantum
	public double proba;

}
