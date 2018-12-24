package temps;

public class Horloge {
	private Date date;
	
	public Horloge() {
		date = Date.valeurInitiale;
	}
	
	public Date lireDate() {
		return date;
	}

	public void mettreAJourDate(Date date) {
		this.date = date;
	}
	
	public boolean aDepasseDate(Date date) {
		return this.date.lireValeur() > date.lireValeur(); 
	}
}