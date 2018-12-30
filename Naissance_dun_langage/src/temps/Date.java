package temps;

public class Date implements Comparable<Date> {
	public static final Date valeurInitiale = new Date(0);
	
	private int valeur;

	public Date(int valeur) {
		this.valeur = valeur;
	}
	
	public static Date depuisValeur(int valeur) {
		return new Date(valeur);
	}
	
	public static Date dateSuivante(Date date) {
		return new Date(date.valeur + 1);
	}
	
	public int lireValeur() {
		return valeur;
	}
	
	public Date plusDelais(Delais delais) {
		return new Date(valeur + delais.lireValeur());
	}
	
	public boolean estApres(Date date) {
		return valeur >= date.lireValeur();
	}
	
	public boolean estAvant(Date date) {
		return valeur <= date.lireValeur();
	}
	
	@Override
	public String toString() {
		return String.valueOf(valeur);
	}

	@Override
	public boolean equals(Object objet){
		if (objet == null)
			return false;
		if (objet == this)
			return true;
		if (!(objet instanceof Date))
			return false;
		
		Date date = (Date)objet;
		return lireValeur() == date.lireValeur();
	}

	public static Date depuisString(String date) throws NumberFormatException {
		int dateValue = Integer.parseUnsignedInt(date);
		
		return new Date(dateValue);
	}

	@Override
	public int compareTo(Date date) {
		return lireValeur() - date.lireValeur();
	}
}
