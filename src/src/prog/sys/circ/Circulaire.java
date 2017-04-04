package src.prog.sys.circ;

import src.prog.sys.Manager;
import src.prog.sys.Saisie;

public class Circulaire extends Manager {

	private static Circulaire circulaire = new Circulaire();

	private Circulaire() {
		instrMax = Saisie.instrMax();
		esMax = Saisie.esMax();
		esDuree = Saisie.esDuree();
		proba = Saisie.proba();
	}

	public static Circulaire getInstance() {
		return circulaire;
	}

}
