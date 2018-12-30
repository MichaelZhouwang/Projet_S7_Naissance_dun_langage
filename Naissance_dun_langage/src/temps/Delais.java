package temps;

public class Delais {
	public static final Delais delaisSuccesionParDefaut = new Delais(1);
	
	private int valeur;
	
	public Delais(int valeur) {
		this.valeur = valeur;
	}
	
	@Override
	public String toString() {
		return String.valueOf(valeur);
	}
	
	public int lireValeur() {
		return valeur;
	}
}
