package src.prog.sys.dyna;

import java.util.Random;

public class Processus {

	private Manager manager = Manager.getInstance();

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
	 * Le nombre de Quantums a passer restant (lors d'une E/S)
	 */
	public double quantTmp;

	/**
	 * Le temps total (en ns) occupe par le Processus
	 */
	public long tempsCum;

	public Processus() {
		Random random = new Random();
		prio = random.nextInt(manager.prioMax);

		// On utilise la réparatition de la loi normale pour choisir un nombre aléatoire 
		// d'instruction d'un processus. Cela permet d'avoir des nombres d'instructions "réalistes". 
		nbInstr = (int)(random.nextGaussian()*(manager.instrMax/4)+(manager.instrMax/2));
		// On réajuste le nombre d'instruction au cas où la val obtenue soit trop éloignée de ce que l'on désire
		if (nbInstr <= 0) nbInstr = 1; 
		if (nbInstr > manager.instrMax) nbInstr = manager.instrMax;
		

		// On choisit un nombre d'E/S valide
		nbES = random.nextInt((int) (manager.esMax * nbInstr));
		esRest = nbES;

		tempsCum = 0;
		quantTmp = 0;
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
		if (quantTmp > 0) {
			es();
			return 1;
		}

		long debut = System.nanoTime();

		// On tourne tant que le Quantum de temps alloue n'est pas epuise
		for (int instr = 0; System.nanoTime() - debut <= Manager.QUANTUM; instr++) {
			tempsCum += System.nanoTime() - debut;

			// Probabilite de rencontrer une E/S
			if (Math.random() < ((double) esRest) / nbInstr) {
				quantTmp = manager.esDuree;
				esRest--;
				// On a rencontre une E/S
				return 1;
			}

			// On a fini d'executer ce Processus
			if (instr >= nbInstr)
				return 2;
		}

		// Quantum epuise
		return 0;
	}

	/**
	 * Gere les E/S. Decremente le nombre de Quantums a temporiser durant une
	 * E/S et passe le temps correspondant
	 */
	private void es() {
		long debut = System.nanoTime();

		// On temporise 1 Quantum
		while (System.nanoTime() - debut < Manager.QUANTUM)
			;
		tempsCum += System.nanoTime() - debut;
		quantTmp--;
	}

	@Override
	public String toString() {
		return "<thread@" + hashCode() + " t=" + tempsCum + ", p=" + prio + ">";
	}

}
