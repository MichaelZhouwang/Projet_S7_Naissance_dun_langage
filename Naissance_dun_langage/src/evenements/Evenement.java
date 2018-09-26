package evenements;

import architecture.Individu;

public abstract class Evenement {
	private String description;
	private Individu acteur;
	private int date;

	public abstract void declencher();
	
	public Evenement(int date, Individu acteur) {
		this.date = date;
		this.acteur = acteur;
		this.description = "";
	}
	
	public void dater(int date) {
		this.date = date;
	}
	
	public void decrire(String description) {
		this.description = description;
	}
	
	public int lireDate() {
		return date;
	}
	
	public Individu lireActeur() {
		return acteur;
	}
	
	public String lireDescription() {
		return description;
	}
	
	@Override
	public String toString() {
		return description;
	}
}
