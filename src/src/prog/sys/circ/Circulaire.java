package src.prog.sys.circ;

import src.prog.sys.Manager;
import src.prog.sys.Saisie;
import src.prog.sys.Processus;
import java.util.LinkedList;

public class Circulaire extends Manager {

	private static Circulaire circulaire = new Circulaire();
	LinkedList <Processus> liste = new LinkedList <Processus> ();

	private Circulaire() {
		instrMax = Saisie.instrMax();
		esMax = Saisie.esMax();
		esDuree = Saisie.esDuree();
		proba = Saisie.proba();
		prioMax = 0;
	}

	public static Circulaire getInstance() {
		return circulaire;
	}

	public void traitement(){
		
		// On place un premier processus aléatoire
		Processus first = new Processus(circulaire);
		liste.add(first);
		
		while ( liste.size() != 0 ){
			// On récupère le 1er processus de la liste
			Processus p = liste.peek();
			
			
			// On test si le processus est en cours d'E/S; si oui on place le processus en fin de liste
			if ( ( System.nanoTime() - p.debutES < esDuree )  && ( p.debutES != 0 ) ){
				Processus tmp = liste.pop();
				liste.addLast(tmp);
			}
			
			
			// Sinon on regarde "l'état" du processus et on agit en fonction
			else {
				// Quantum de temps épuisé, on le place en fin de liste
				if ( p.exec() == 0 ){
					Processus tmp = liste.pop();
					liste.addLast(tmp);
				}
				//Instruction d'E/S, on le place en fin de liste
				if ( p.exec() == 1 ){
					Processus tmp = liste.pop();
					liste.addLast(tmp);
				}
				//Le processus a fini toutes ses instructions, on le supprime
				if ( p.exec() == 2 ){
					liste.pop();
				}
			}
			
			
			/* On simule une proba uniforme et on compare à la proba entrer par l'utilisateur
			 * Si la simulation est <= à la proba de l'utilisateur, on ajoute un nouveau processus 
			 * en fin de liste
			 */
			if ( Math.random() <= proba ) 
				liste.addLast(new Processus(circulaire));
			
		}
	}
	
	
}
