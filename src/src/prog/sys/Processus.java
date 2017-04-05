package src.prog.sys;

import java.util.Random;

public class Processus {

	private Manager manager;

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
	 * Le nombre d'E/S restant a executer sur ce Processus
	 */
	public int esRest;

	/**
	 * Le temps total (en ns) occupe par le Processus
	 */
	public long tempsCum;
	
	/* 
	 * Temps du début d'entrée sortie
	 */
	public long debutES;

	public Processus(Manager manager) {
		this.manager = manager;
		Random random = new Random();
		prio = random.nextInt(manager.prioMax);

		// On utilise la reparatition de la loi normale pour choisir un nombre
		// aleatoire d'instruction d'un processus. Cela permet d'avoir des
		// nombres d'instructions "realistes".
		nbInstr = (int) (random.nextGaussian() * (manager.instrMax / 4) + (manager.instrMax / 2));
		// On reajuste le nombre d'instruction au cas ou la val obtenue soit
		// trop eloignee de ce que l'on desire
		if (nbInstr <= 0) nbInstr = manager.instrMax / 2;
		if (nbInstr > manager.instrMax) nbInstr = manager.instrMax;

		// On choisit un nombre d'E/S valide
		nbES = random.nextInt((int) (manager.esMax * nbInstr));
		esRest = nbES;

		tempsCum = 0;
		debutES = 0;
	}

	/**
	 * Renvoie un code pour la raison de la fin de l'execution de ce processus -
	 * - 0 : Le processus a epuise son Quantum de temps<br>
	 * - 1 : Le processus a rencontre une instruction d'E/S<br>
	 * - 2 : Le processus a fini toutes les instructions<br>
	 * 
	 * @return le code associe a l'evenement de fin d'execution du processus
	 */
	public int exec() {
		// On temporise si on est encore en E/S

		long debut = System.nanoTime();

		// On tourne tant que le Quantum de temps alloue n'est pas epuise
		for (int instr = 0; System.nanoTime() - debut <= manager.quantum; instr++) {
			tempsCum += System.nanoTime() - debut;

			// Probabilite de rencontrer une E/S
			if (Math.random() < ((double) esRest) / nbInstr) {
				esRest--;
				// On a rencontre une E/S et on relève la "date" de début d'E/S 
				debutES = System.nanoTime();
				return 1;
			}

			// On a fini d'executer ce Processus
			if (instr >= nbInstr)
				return 2;
		}

		// Quantum epuise
		return 0;
	}

	@Override
	public String toString() {
		return "<thread@" + hashCode() + " t=" + tempsCum + ", p=" + prio + ">";
	}

}
