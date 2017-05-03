package src.prog.sys;

import src.prog.sys.circ.Circulaire;;

public class Main {

	public static void main(String[] args) {
		try {
			Circulaire.getInstance().traitement();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
