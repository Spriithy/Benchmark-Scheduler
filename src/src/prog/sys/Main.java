package src.prog.sys;

import src.prog.sys.dyna.Dynamique;;

public class Main {

	public static void main(String[] args) {
		try {
			Dynamique.getInstance().traitement();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
