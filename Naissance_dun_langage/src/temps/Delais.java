package temps;

public class Delais {
	public static final Delais delaisReceptionParDéfaut = new Delais(1);
	public static final Delais delaisPassageParDéfaut = new Delais(2);
	
	private int valeur;
	
	public Delais(int valeur) {
		this.valeur = valeur;
	}
	
	public int lireValeur() {
		return valeur;
	}
}
