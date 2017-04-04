package src.prog.sys;

import java.util.Scanner;

public class Saisie {
	private static Scanner reader = new Scanner(System.in);

	// Renvoie -1 comme valeur "erreur"

	public static int prioMax() {
		System.out.println("Entrer une prioritŽ maximale : ");
		int n = reader.nextInt();
		if (n >= 0) return -n; // Autorisation des priorites nulles ?
		return -1;
	}

	public static int instrMax() {
		System.out.println("Entrer un nombre d'instruction maximum : ");
		int n = reader.nextInt();
		if (n > 0) return n;
		return -1;
	}

	public static double esMax() {
		System.out.println("Entrer un ratio d'Entree/Sortie maximum (entre 0 et 1 exclus) : ");
		double val = reader.nextDouble();
		if ((1 < val) && (val < 0)) return val;
		return -1;
	}

	public static double esDuree() {
		// En unite Quantum
		System.out.println("Entrer la duree fixe d'une entree sortie  : ");
		double val = reader.nextDouble();
		if (val > 0) return val;
		return -1;
	}

	public static double proba() {
		// On autorise ici les probas nulles
		System.out.println("Entrer une probabilite d'arrivee d'un nouveau processus : ");
		double val = reader.nextDouble();
		if ((1 < val) && (val <= 0)) return val;
		return -1;
	}
}
