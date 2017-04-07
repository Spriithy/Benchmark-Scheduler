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
	 * Fonction qui place un processus après les processus de la liste ayant meme priorité
	 * @param l
	 */
	public void placerAprèsprio(LinkedList<Processus> l){
		// @sup arrête la boucle lorsque l'on rencontre des processus d'une priorité inférieur à 
		// la priorité du processus p en cours de gestion 
		boolean sup = true;
		Processus p = l.pop();
		
		// On initialise la frontière @limite entre les processus de prio supérieur et inférieur à la variable prio
		// à 0 dans le cas ou le processus soit celui de plus haute priorité (on le place en début de liste).
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
	 * Ajoute un nouveau processus dans une liste chainée devant ceux de même priorité
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
		
		// On insère un premier élement dans la liste chainée
		liste.addFirst(new Processus(this));
		
		while ( liste.size() > 0 ){
			if (liste.size() > pMax)
				maxAtteint = true;
			iter++;
			
			// Récupération 1er élement de la liste
			p = liste.getFirst();
			
			if (p.es && p.tES == 0){
				System.out.println("[" + iter + "][" + liste.size() + "] E/S terminée pour " + p.toString());

				p.es = false; // On réinitialise le processus sur ses E/S
				p.prioTmp = p.prio; // On réinitialise la priorité du processus
				placerAprèsprio(liste);// On place le processus après ceux de même prio
			}
			else {
				// Sinon on regarde "l'etat" du processus et on agit en fonction
				switch (p.exec()) {
				case 0:	
					System.out.println(
							"[" + iter + "][" + liste.size() + "] Quantum épuisé pour " + liste.peek().toString());
					p.prioTmp = p.prio;
					placerAprèsprio(liste);
					break;
				case 1:
					System.out.println(
							"[" + iter + "][" + liste.size() + "] E/S en cours pour " + liste.peek().toString());
					p.prioTmp = p.prio;
					placerAprèsprio(liste);
					break;
				case 2: 
					Processus tmp = liste.getFirst();
					System.out.println("[" + iter + "][" + liste.size() + "] Processus terminé");
					System.out.println("\ttotal = " + tmp.tempsCum * quantum + "ms");
					System.out.println("\tE/S = " + tmp.totalES * quantum + "ms");
					liste.pop();
					break;
					
				}
			}
			// On simule une proba uniforme et on compare a la proba entrer par
			// l'utilisateur Si la simulation est <= a la proba de
			// l'utilisateur, et si la taille max de la liste n'est pas atteinte. On place le processus avant ceux de même prio
			if (Math.random() >= proba && !maxAtteint) {
			ajoutProc(liste);
			System.out.println("[" + iter + "][" + liste.size() + "] Nouveau processus " + liste.peek().hashCode());
			}
		}
	}
}
