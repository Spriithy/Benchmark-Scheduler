package src.prog.sys;

import java.util.Scanner;

public class Saisie {
	private static Scanner reader = new Scanner(System.in);

	// Renvoie -1 comme valeur "erreur"

	public static int prioMax() {
		System.out.print("Entrer une priorite maximale : ");
		int n = reader.nextInt();
		if (n >= 0) return Math.abs(n); // Autorisation des priorites nulles ?
		return -1;
	}

	public static int instrMax() {
		System.out.print("Entrer un nombre d'instruction maximum : ");
		int n = reader.nextInt();
		if (n > 0) return Math.abs(n);
		return -1;
	}

	public static double esMax() {
		System.out.print("Entrer un ratio d'Entree/Sortie maximum (entre 0 et 1 exclus) : ");
		double val = Double.parseDouble(reader.next());
		if ((1 < val) && (val < 0)) return Math.abs(val);
		return -1;
	}

	public static double esDuree() {
		// En unite Quantum
		System.out.print("Entrer la duree fixe d'une entree sortie  : ");
		double val = Double.parseDouble(reader.next());
		if (val > 0) return Math.abs(val);
		return -1;
	}

	public static double proba() {
		// On autorise ici les probas nulles
		System.out.print("Entrer une probabilite d'arrivee d'un nouveau processus : ");
		double val = Double.parseDouble(reader.next());
		if ((1 < val) && (val <= 0)) return Math.abs(val);
		return -1;
	}
	
	public static double argIncr(){
		System.out.print("Entrer une probabilite d'arrivee d'un nouveau processus : ");
		double val = Double.parseDouble(reader.next());
		if (( val > 0 ) && ( val <= 1 ))
			return val;
		return -1;
	}
	
	public static int pMax(){
		System.out.print("Entrer une priorite maximale : ");
		int n = reader.nextInt();
		if (n > 0)
			return n;
		return -1;
	}
}
