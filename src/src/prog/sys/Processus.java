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

	public Processus(Manager manager) {
		this.manager = manager;
		Random random = new Random();
		prio = random.nextInt(manager.prioMax == -1 ? 0 : manager.prioMax);

		// On utilise la reparatition de la loi normale pour choisir un nombre
		// aleatoire d'instruction d'un processus. Cela permet d'avoir des
		// nombres d'instructions "realistes".
		nbInstr = (int) (random.nextGaussian() * (manager.instrMax / 4) + (manager.instrMax / 2));
		// On reajuste le nombre d'instruction au cas ou la val obtenue soit
		// trop eloignee de ce que l'on desire
		if (nbInstr <= 0) nbInstr = manager.instrMax / 2;
		if (nbInstr > manager.instrMax) nbInstr = manager.instrMax;

		// On choisit un nombre d'E/S valide
		nbES = random.nextInt(1 + (int) (manager.esMax * nbInstr));
		esRest = nbES;

		tempsCum = 0;
		es = false;
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
		// Si on est en E/S
		if (es) {
			// On calcule le temps a passer pour cette itération (en quantums)
			double dt = Math.min(1, tES);
			tempsCum += 1;
			totalES += dt;
			tES -= dt;
			wait(dt * manager.quantum);

			// code E/S
			return 1;
		}

		if (Math.random() <= ((double) esRest) / nbInstr) {
			es = true;
			esRest--;
			tES = manager.esDuree;
			return 1;
		}

		long ms = System.currentTimeMillis(), dt;
		for (int instr = 0; (dt = System.currentTimeMillis() - ms) <= manager.quantum; instr++) {
			if (instr >= nbInstr) {
				tempsCum += dt;
				return 2;				
			}
		}

		// Quantum epuise
		tempsCum += 1;
		return 0;
	}

	/**
	 * Est utilisée pour faire attendre le processeur
	 * 
	 * @param dt
	 *            le ratio en terme de quantums a attendre
	 * @throws InterruptedException
	 */
	private synchronized void wait(double dt) throws InterruptedException {
		wait((int) (1000000 * dt) / 1000000, (int) (1000000 * dt) % 1000000);
	}

	@Override
	public String toString() {
		return hashCode() + "";
	}

}
