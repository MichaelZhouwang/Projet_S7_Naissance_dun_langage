package evenement;

public abstract class Evenemen {
	private Evenemen evenementInitiateur;

	public abstract void declencher();
	
	public Evenemen(Evenemen evenementInitiateur) {
		this.evenementInitiateur = evenementInitiateur;
	}
	
	public Evenemen lireEvenementInitiateur() {
		return evenementInitiateur;
	}
}
