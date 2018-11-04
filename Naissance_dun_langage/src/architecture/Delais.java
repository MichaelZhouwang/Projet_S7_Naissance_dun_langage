package architecture;

public class Delais {
	public static final Delais delaisReceptionParD�faut = new Delais(0);
	public static final Delais delaisPassageParD�faut = new Delais(1);
	
	private int valeur;
	
	public Delais(int valeur) {
		this.valeur = valeur;
	}
	
	public int lireValeur() {
		return valeur;
	}
}
