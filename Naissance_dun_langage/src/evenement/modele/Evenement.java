package evenement.modele;

import systeme.Systeme;
import systeme.enumeration.TypeCritereArret;

public abstract class Evenement {
	private static int compteur = 0;
	
	private int ID;
	private Evenement evenementInitiateur;
	
	public abstract void declencher();
	
	public Evenement(Evenement evenementInitiateur) {
		ID = ++compteur;
		this.evenementInitiateur = evenementInitiateur;

		if (Systeme.lireTypeCritereArret() == TypeCritereArret.EVENEMENTS && compteur == Systeme.lireObjectifCritereArret()) {
			Systeme.declencherEvenementFinal(this);
		}
	}
	
	public int lireID() {
		return ID;
	}
	
	public Evenement lireEvenementInitiateur() {
		return evenementInitiateur;
	}
	
	public static int lireCompteur() {
		return compteur;
	}
}
