package architecture;

public class Date implements Comparable<Date> {
	public static int valeurInitiale = 0;
	
	private int valeur;

	public Date(int valeur) {
		this.valeur = valeur;
	}
	
	public Date plusDelais(Delais delais) {
		return new Date(valeur + delais.lireValeur());
	}
	
	public static Date dateSuivante(Date date) {
		return new Date(date.valeur + 1);
	}
	
	public int lireValeur() {
		return valeur;
	}
	
	public boolean estApres(Date date) {
		return valeur > valeur;
	}
	
	@Override
	public String toString() {
		return Integer.toString(valeur);
	}

	@Override
	public int compareTo(Date date) {
		return lireValeur() - date.lireValeur();
	}
}
