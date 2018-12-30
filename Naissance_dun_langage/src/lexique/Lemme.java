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
	
	@Override
	public int hashCode() {
		return (chaine + initiateur.toString()).hashCode();
	}
	
	@Override
	public boolean equals(Object objet) {
		if (objet == null)
			return false;
		if (objet == this)
			return true;
		if (!(objet instanceof Lemme))
			return false;

		Lemme lemme = (Lemme)objet;
		return lireChaine().equals(lemme.lireChaine()) && lireInitiateur().equals(lemme.lireInitiateur());
	}
}