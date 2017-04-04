package src.prog.sys.dyna;

import src.prog.sys.Manager;
import src.prog.sys.Saisie;

public class Dynamique extends Manager {

	private static Dynamique dyna = new Dynamique();

	private Dynamique() {
		prioMax = Saisie.prioMax();
		instrMax = Saisie.instrMax();
		esMax = Saisie.esMax();
		esDuree = Saisie.esDuree();
		proba = Saisie.proba();
	}

	public static Dynamique getInstance() {
		return dyna;
	}

}
