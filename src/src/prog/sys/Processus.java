package src.prog.sys;

import java.util.Random;

public class Processus {

	private Manager manager;

	static int nn = 0;

	int num;

	/**
	 * La priorite d'un processus a un instant donne
	 */
	public int prio;

	/**
	 * Le nombre total d'instructions processeurs du processus a executer a
	 * chaque fois que le processus est elu
	 */
	public int nbInstr;

	/**
	 * Le nombre d'E/S (en instr. proc.) qui seront executees a chaques fois que
	 * le processus est elu
	 */
	public int nbES;

	/**
	 * Le temps total (en Quantum) occupe par le Processus
	 */
	public long tempsCum;

	/**
	 * Le temps total de ce processus passé en E/S
	 */
	public long totalES;

	/**
	 * Le temps d'E/S restant
	 */
	public double tES;

	/**
	 * Permet de savoir si oui ou non le processus est en E/S
	 */
	public boolean es;

	/**
	 * Priorité temporaire d'un processus pour l'algorithme dynamique
	 */
	public double prioTmp;

	public ListeInstruction li;

	public int at;

	public Processus(Manager manager) {
		num = nn++;
		this.manager = manager;
		Random random = new Random();
		prio = random.nextInt(manager.prioMax == -1 ? 0 : manager.prioMax);
		// On initialise la priorité temporaire à la priorité "atomique"
		prioTmp = prio;

		// On utilise la reparatition de la loi normale pour choisir un nombre
		// aleatoire d'instruction d'un processus. Cela permet d'avoir des
		// nombres d'instructions "realistes".
		nbInstr = (int) (random.nextGaussian() * (manager.instrMax / 4) + (manager.instrMax / 2));
		// On reajuste le nombre d'instruction au cas ou la val obtenue soit
		// trop eloignee de ce que l'on desire
		if (nbInstr <= 0)
			nbInstr = manager.instrMax / 2;
		if (nbInstr > manager.instrMax)
			nbInstr = manager.instrMax;

		// On choisit un nombre d'E/S valide
		nbES = random.nextInt(1 + (int) (manager.esMax * nbInstr));

		tempsCum = 0;
		es = false;

		li = new ListeInstruction(nbInstr, nbES);
		at = 0;
	}

	/**
	 * Renvoie un code pour la raison de la fin de l'execution de ce processus -
	 * - 0 : Le processus a epuise son Quantum de temps<br>
	 * - 1 : Le processus a rencontre une instruction d'E/S<br>
	 * - 2 : Le processus a fini toutes les instructions<br>
	 * 
	 * @return le code associe a l'evenement de fin d'execution du processus
	 * @throws InterruptedException
	 */
	public synchronized int exec() throws InterruptedException {
		long nano = System.nanoTime();

		for (; System.nanoTime() - nano <= manager.quantum; at++) {
			if (at >= nbInstr)
				return 2;

			if (li.instr[at]) {
				tES = manager.esDuree;
				es = true;
				return 1;
			}
		}

		tempsCum += 1;
		wait(0, manager.quantum);
		return 0;
	}

	public synchronized void es() throws InterruptedException {
		if (es) {
			tES -= 1;
			totalES += 1;
			tempsCum += 1;

			if (tES <= 0)
				es = false;
			at++;
		}
	}

	@Override
	public String toString() {
		return "(P" + num + ")";
	}

}
