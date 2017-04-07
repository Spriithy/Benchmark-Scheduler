package src.prog.sys.dyna;

import src.prog.sys.Manager;
import src.prog.sys.Processus;
import src.prog.sys.Saisie;

import java.util.LinkedList;

public class Dynamique extends Manager {

	private static Dynamique dyna = new Dynamique();
	private LinkedList<Processus> liste = new LinkedList<Processus>();
	
	boolean maxAtteint = false;
	int iter = 0;

	private Dynamique() {
		prioMax = Saisie.prioMax();
		instrMax = Saisie.instrMax();
		esMax = Saisie.esMax();
		esDuree = Saisie.esDuree();
		proba = Saisie.proba();
		argIncr = Saisie.argIncr();
		pMax = Saisie.pMax();
	}

	public static Dynamique getInstance() {
		return dyna;
	}
	
	public void incrementer(LinkedList<Processus> l){
		for (int i=0; i < l.size(); i++){
			Processus p = l.get(i);
			p.prioTmp += argIncr ;
			l.set(i,p);
		}
	}
	
	/**
	 * Fonction qui place un processus apr�s les processus de la liste ayant meme priorit�
	 * @param l
	 */
	public void placerApr�sprio(LinkedList<Processus> l){
		// @sup arr�te la boucle lorsque l'on rencontre des processus d'une priorit� inf�rieur � 
		// la priorit� du processus p en cours de gestion 
		boolean sup = true;
		Processus p = l.pop();
		
		// On initialise la fronti�re @limite entre les processus de prio sup�rieur et inf�rieur � la variable prio
		// � 0 dans le cas ou le processus soit celui de plus haute priorit� (on le place en d�but de liste).
		int limite = 0; 
		
		for (int i = 0; i < l.size() && sup ; i++){
			Processus tmp = l.get(i);
			if ( tmp.prioTmp < p.prio ){
				sup = false;
				limite = i;
			}
		}
		l.add(limite,p);
	}
	
	/**
	 * Ajoute un nouveau processus dans une liste chain�e devant ceux de m�me priorit�
	 */
	public void ajoutProc(LinkedList<Processus> l){
		Processus p = new Processus(dyna);
		boolean inf = true;
		int limite = 0;
		
		for (int i = 0; i < l.size() && inf; i++){
			Processus tmp = l.get(i);
			if ( tmp.prioTmp <= p.prio){
				inf = false;
				limite = i;
			}
		l.add(limite,p);
		}
	}

	public void traitement() throws InterruptedException {
		Processus p;
		
		// On ins�re un premier �lement dans la liste chain�e
		liste.addFirst(new Processus(this));
		
		while ( liste.size() > 0 ){
			if (liste.size() > pMax)
				maxAtteint = true;
			iter++;
			
			// R�cup�ration 1er �lement de la liste
			p = liste.getFirst();
			
			if (p.es && p.tES == 0){
				System.out.println("[" + iter + "][" + liste.size() + "] E/S termin�e pour " + p.toString());

				p.es = false; // On r�initialise le processus sur ses E/S
				p.prioTmp = p.prio; // On r�initialise la priorit� du processus
				placerApr�sprio(liste);// On place le processus apr�s ceux de m�me prio
			}
			else {
				// Sinon on regarde "l'etat" du processus et on agit en fonction
				switch (p.exec()) {
				case 0:	
					System.out.println(
							"[" + iter + "][" + liste.size() + "] Quantum �puis� pour " + liste.peek().toString());
					p.prioTmp = p.prio;
					placerApr�sprio(liste);
					break;
				case 1:
					System.out.println(
							"[" + iter + "][" + liste.size() + "] E/S en cours pour " + liste.peek().toString());
					p.prioTmp = p.prio;
					placerApr�sprio(liste);
					break;
				case 2: 
					Processus tmp = liste.getFirst();
					System.out.println("[" + iter + "][" + liste.size() + "] Processus termin�");
					System.out.println("\ttotal = " + tmp.tempsCum * quantum + "ms");
					System.out.println("\tE/S = " + tmp.totalES * quantum + "ms");
					liste.pop();
					break;
					
				}
			}
			// On simule une proba uniforme et on compare a la proba entrer par
			// l'utilisateur Si la simulation est <= a la proba de
			// l'utilisateur, et si la taille max de la liste n'est pas atteinte. On place le processus avant ceux de m�me prio
			if (Math.random() >= proba && !maxAtteint) {
			ajoutProc(liste);
			System.out.println("[" + iter + "][" + liste.size() + "] Nouveau processus " + liste.peek().hashCode());
			}
		}
	}
}
