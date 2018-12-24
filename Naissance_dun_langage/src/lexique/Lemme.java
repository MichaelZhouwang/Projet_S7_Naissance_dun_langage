package lexique;

import systeme.Individu;

public class Lemme {
	private String chaine;
	private Individu initiateur;
	
	public Lemme(Individu initiateur, String chaine) {
		this.chaine = new String(chaine);
		this.initiateur = initiateur;
	}
	
	public static final Lemme QUELCONQUE = new Lemme(null, "");
	
	public String lireChaine() {
		return chaine;
	}
	
	public Individu lireInitiateur() {
		return initiateur;
	}
	
	@Override
	public String toString() {
		return chaine;
	}
}