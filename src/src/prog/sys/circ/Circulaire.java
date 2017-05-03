package src.prog.sys.circ;

import java.util.LinkedList;

import src.prog.sys.Manager;
import src.prog.sys.Processus;

public class Circulaire extends Manager {

	private static final Circulaire circulaire = new Circulaire();
	private LinkedList<Processus> liste = new LinkedList<Processus>();

	int iter = 0;
	boolean maxAtteint = false;

	private Circulaire() {
		instrMax = 150;
		esMax = 0.2;
		esDuree = 1;
		proba = 0.75;
		prioMax = 1;
		pMax = 10;
	}

	public static Circulaire getInstance() {
		return circulaire;
	}

	public void traitement() throws InterruptedException {
		Processus tmp, p;

		// On place un premier processus aleatoire
		liste.addFirst(new Processus(this));

		while (liste.size() > 0) {
			if (liste.size() >= pMax)
				maxAtteint = true;
			iter++;
			
			for (Processus pp : liste)
				pp.es();
			
			// On recupere le 1er processus de la liste
			p = liste.getFirst();

			// On teste si le processus est en cours d'E/S; si oui on place le
			// processus en fin de liste
			if (p.es && p.tES == 0) {
				System.out.println("[" + iter + "][" + liste.size() + "] E/S terminée pour " + p.toString());

				p.es = false; // On réinitialise le processus sur ses E/S
				liste.addLast(p);
				liste.pop();

			} else {
				// Sinon on regarde "l'etat" du processus et on agit en fonction
				switch (p.exec()) {
				// Quantum de temps epuise, on le place en fin de liste
				// OU Instruction d'E/S, on le place en fin de liste
				case 0:
					System.out.println(
							"[" + iter + "][" + liste.size() + "] Quantum épuisé pour " + liste.peek().toString());
					tmp = liste.pop();
					liste.addLast(tmp);
					break;
				case 1:
					System.out.println(
							"[" + iter + "][" + liste.size() + "] E/S en cours pour " + liste.peek().toString());
					tmp = liste.pop();
					liste.addLast(tmp);
					break;
				// Le processus a fini toutes ses instructions, on le supprime
				case 2:
					tmp = liste.getFirst();
					System.out.println("[" + iter + "][" + liste.size() + "] Processus terminé");
					System.out.println("\ttotal = " + tmp.tempsCum + " qt");
					System.out.println("\tE/S = " + tmp.totalES + " qt");
					liste.pop();
					break;
				}
			}

			// On simule une proba uniforme et on compare a la proba entrer par
			// l'utilisateur Si la simulation est <= a la proba de
			// l'utilisateur, on ajoute un nouveau processus en fin de liste
			if (Math.random() >= proba && !maxAtteint) {
				liste.addLast(new Processus(this));
				System.out.println("[" + iter + "][" + liste.size() + "] Nouveau processus " + liste.peek().hashCode());
			}
		}
		System.out.println("[" + iter + "] Simulation Circulaire terminée");
	}

}
