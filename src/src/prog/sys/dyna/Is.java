package src.prog.sys.dyna;
import java.util.Scanner; 

public class Is {
	Scanner reader = new Scanner(System.in); 
	private Manager manager = Manager.getInstance();
	
	// Renvoie -1 comme valeur "erreur" // 
	
	public int prioMax(){
		System.out.println("Entrer une priorit� maximale : ");
		int n = reader.nextInt(); 
		if (n >= 0) return n; // Autorisation des priorit�s nulles ? //
		System.out.println("Erreur[1] : Entr�e non valide; valeur -1 retourn�e" );
		return -1;
	}
	
	public int instrMax(){
		System.out.println("Entrer un nombre d'instruction maximum : ");
		int n = reader.nextInt(); 
		if (n>0) return n;
		System.out.println("Erreur[1] : Entr�e non valide; valeur -1 retourn�e" );
		return -1;
	}
	
	public double esMax(){
		System.out.println("Entrer un ratio d'Entr�e/Sortie maximum (entre 0 et 1 exclus) : ");
		double val = reader.nextInt(); 
		if ((1 < val) && (val < 0 )) return val;
		System.out.println("Erreur[1] : Entr�e non valide; valeur -1 retourn�e" );
		return -1;
	}
	
	public double esDuree(){
		// En unit� QUANTUM //
		// TODO: Terminer en prenant en compte le processus
		System.out.println("Entrer la dur�e fixe d'une entr�e sortie  : ");
		double val = reader.nextInt(); 
		if (manager.esMax*manager.instrMax > val) return val;
		System.out.println("Erreur[1] : Entr�e non valide; valeur -1 retourn�e" );
		return -1;
	}
	
	public double proba(){
		// On autorise ici les probas nulles // 
		System.out.println("Entrer une probabilit� d'arriv�e d'un nouveau processus : ");
		double val = reader.nextInt(); 
		if ((1 < val) && (val <= 0 )) return val;
		System.out.println("Erreur[1] : Entr�e non valide; valeur -1 retourn�e" );
		return -1;
	}
}
