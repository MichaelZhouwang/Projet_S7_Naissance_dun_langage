package temps;

public class Delais {
	public static final Delais delaisReceptionParDefaut = new Delais(0);
	public static final Delais delaisPassageParDefaut = new Delais(1);
	
	private int valeur;
	
	public Delais(int valeur) {
		this.valeur = valeur;
	}
	
	public int lireValeur() {
		return valeur;
	}
}
