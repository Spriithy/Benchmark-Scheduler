package src.prog.sys.dyna;

import java.util.LinkedList;

import com.sun.javafx.collections.SortableList;

import src.prog.sys.Manager;
import src.prog.sys.Processus;

public class Dynamique extends Manager {

	private static Dynamique dyna = new Dynamique();
	private LinkedList<Processus> liste = new LinkedList<Processus>();

	boolean maxAtteint = false;
	int iter = 0;

	private Dynamique() {
		prioMax = 10;
		instrMax = 150000;
		esMax = 0.6;
		esDuree = 11;
		proba = 0.75;
		prioMax = 1;
		pMax = 100;
		argIncr = 1;
	}

	public static Dynamique getInstance() {
		return dyna;
	}

	public void incrementer() {
		for (Processus p : liste)
			p.prioTmp += argIncr;
	}

	/**
	 * Fonction qui place un processus apr�s les processus de la liste ayant
	 * meme prioritŽ
	 * 
	 * @param l
	 */
	public void placerApresPrio() {
		// @sup arr�te la boucle lorsque l'on rencontre des processus d'une
		// prioritŽ infŽrieur ˆ
		// la prioritŽ du processus p en cours de gestion
		boolean sup = true;
		Processus p = liste.pop();

		// On initialise la fronti�re @limite entre les processus de prio
		// supŽrieur et infŽrieur ˆ la variable prio
		// ˆ 0 dans le cas ou le processus soit celui de plus haute prioritŽ (on
		// le place en dŽbut de liste).
		int limite = 0;

		for (int i = 0; i < liste.size() && sup; i++) {
			Processus tmp = liste.get(i);
			if (tmp.prioTmp < p.prio) {
				sup = false;
				limite = i;
			}
		}
		liste.add(limite, p);
	}

	/**
	 * Ajoute un nouveau processus dans une liste chainŽe devant ceux de m�me
	 * prioritŽ
	 */
	public void ajoutProc() {
		liste.addLast(new Processus(this));
		liste.sort((p1, p2) -> {
			return p1.prio - p2.prio;
		});
	}

	public void traitement() throws InterruptedException {
		Processus p;

		// On ins�re un premier Žlement dans la liste chainŽe
		liste.addFirst(new Processus(this));

		while (liste.size() > 0) {
			if (liste.size() > pMax)
				maxAtteint = true;
			iter++;

			// RŽcupŽration 1er Žlement de la liste
			p = liste.getFirst();

			if (p.es && p.tES == 0) {
				System.out.println("[" + iter + "][" + liste.size() + "] E/S terminŽe pour " + p.toString());

				p.es = false; // On rŽinitialise le processus sur ses E/S
				p.prioTmp = p.prio; // On rŽinitialise la prioritŽ du processus
				placerApresPrio();// On place le processus apr�s ceux de
									// m�me prio
			} else {
				// Sinon on regarde "l'etat" du processus et on agit en fonction
				switch (p.exec()) {
				case 0:
					System.out.println(
							"[" + iter + "][" + liste.size() + "] Quantum ŽpuisŽ pour " + liste.peek().toString());
					p.prioTmp = p.prio;
					placerApresPrio();
					break;
				case 1:
					System.out.println(
							"[" + iter + "][" + liste.size() + "] E/S en cours pour " + liste.peek().toString());
					p.prioTmp = p.prio;
					placerApresPrio();
					break;
				case 2:
					Processus tmp = liste.getFirst();
					System.out.println("[" + iter + "][" + liste.size() + "] Processus terminŽ");
					System.out.println("\ttotal = " + tmp.tempsCum * quantum + "ms");
					System.out.println("\tE/S = " + tmp.totalES * quantum + "ms");
					liste.pop();
					break;

				}
			}
			// On simule une proba uniforme et on compare a la proba entrer par
			// l'utilisateur Si la simulation est <= a la proba de
			// l'utilisateur, et si la taille max de la liste n'est pas
			// atteinte. On place le processus avant ceux de m�me prio
			if (Math.random() >= proba && !maxAtteint) {
				ajoutProc();
				System.out.println("[" + iter + "][" + liste.size() + "] Nouveau processus " + liste.peek().hashCode());
			}
		}
	}
}
