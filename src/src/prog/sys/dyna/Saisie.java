package src.prog.sys.dyna;
import java.util.Scanner; 

public class Saisie {
	Scanner reader = new Scanner(System.in); 
	
	// Renvoie -1 comme valeur "erreur" // 
	
	public int prioMax(){
		System.out.println("Entrer une prioritŽ maximale : ");
		int n = reader.nextInt(); 
		if (n >= 0) return n; // Autorisation des prioritŽs nulles ? //
		System.out.println("Erreur[1] : Entree non valide; valeur -1 retournee" );
		return -1;
	}
	
	public int instrMax(){
		System.out.println("Entrer un nombre d'instruction maximum : ");
		int n = reader.nextInt(); 
		if (n>0) return n;
		System.out.println("Erreur[1] : Entree non valide; valeur -1 retournee" );
		return -1;
	}
	
	public double esMax(){
		System.out.println("Entrer un ratio d'Entree/Sortie maximum (entre 0 et 1 exclus) : ");
		double val = reader.nextDouble(); 
		if ((1 < val) && (val < 0 )) return val;
		System.out.println("Erreur[1] : Entree non valide; valeur -1 retournee" );
		return -1;
	}
	
	public double esDuree(){
		// En unitŽ QUANTUM //
		System.out.println("Entrer la duree fixe d'une entree sortie  : ");
		double val = reader.nextDouble(); 
		if (val > 0) return val;
		System.out.println("Erreur[1] : Entree non valide; valeur -1 retournee" );
		return -1;
	}
	
	public double proba(){
		// On autorise ici les probas nulles // 
		System.out.println("Entrer une probabilite d'arrivee d'un nouveau processus : ");
		double val = reader.nextDouble(); 
		if ((1 < val) && (val <= 0 )) return val;
		System.out.println("Erreur[1] : Entree non valide; valeur -1 retournee" );
		return -1;
	}
}
