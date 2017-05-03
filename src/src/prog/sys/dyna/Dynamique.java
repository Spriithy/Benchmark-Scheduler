package src.prog.sys.dyna;

import java.util.LinkedList;

import src.prog.sys.Manager;
import src.prog.sys.Processus;

public class Dynamique extends Manager {

	private static Dynamique dyna = new Dynamique();
	private LinkedList<Processus> liste = new LinkedList<Processus>();

	boolean maxAtteint = false;
	int iter = 0;

	private Dynamique() {
		prioMax = 10;
		instrMax = 150;
		esMax = 0.2;
		esDuree = 1;
		proba = 0.75;
		prioMax = 1;
		pMax = 10;
		argIncr = 1;
	}

	public static Dynamique getInstance() {
		return dyna;
	}

	public void ajoutProcessus() {
		placerDevant(new Processus(this));
	}

	public void placerDevant(Processus p) {
		int i = 0;

		if (liste.size() == 0) {
			liste.add(p);
			return;
		}

		while (i < liste.size() && liste.get(i).prio > p.prio)
			i++;

		if (i >= liste.size())
			liste.addLast(p);
		else
			liste.add(i, p);
	}

	public void placerApres(Processus p) {
		int i = 0;

		if (liste.size() == 0) {
			liste.add(p);
			return;
		}

		while (i < liste.size() && liste.get(i).prio >= p.prio)
			i++;

		if (i >= liste.size())
			liste.addLast(p);
		else
			liste.add(i, p);
	}

	public void trierPrio() {
		liste.sort((p1, p2) -> {
			return p1.prio - p2.prio;
		});
	}

	public void incrAll() {
		for (Processus p : liste)
			p.prioTmp += argIncr;
	}

	public void traitement() throws InterruptedException {
		liste.add(new Processus(this));

		while (liste.size() >= 0) {
			if (liste.size() >= pMax)
				maxAtteint = true;
			iter++;

			incrAll();

			for (Processus p : liste)
				p.es();

			if (liste.isEmpty()) break;
			Processus p = liste.pop();

			if (p.es) {
				p.prioTmp = p.prio;
				placerApres(p);
				continue;
			}

			switch (p.exec()) {
			case 0:
				System.out.println(iter + " : " + (1 + liste.size()) + " : Quantum epuise pour " + p.toString());
				p.prioTmp = p.prio;
				placerApres(p);
				break;
			case 1:
				System.out.println(iter + " : " + (1 + liste.size()) + " : Debut E/S pour " + p.toString());
				p.prioTmp = p.prio;
				placerApres(p);
				break;
			case 2:
				System.out.println(iter + " : Fin Processus " + p.toString());
				System.out.println("\ttotal = " + p.tempsCum + " qt");
				System.out.println("\t   ES = " + p.totalES + " qt");
			}

			if (Math.random() <= proba && !maxAtteint)
				ajoutProcessus();
		}
		System.out.println("Simulation Dynamique terminee !");
	}
}
