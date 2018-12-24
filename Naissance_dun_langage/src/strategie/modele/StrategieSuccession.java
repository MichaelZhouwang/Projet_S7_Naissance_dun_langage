package strategie.modele;

import strategie.enumeration.ImplementationStrategieSuccession;
import strategie.implementation.StrategieSuccessionPremierVoisin;
import strategie.implementation.StrategieSuccessionVoisinAleatoire;
import systeme.Individu;

public abstract class StrategieSuccession {
	protected abstract Individu determinerSuccesseur(Individu individuCourant);

	public static Individu executerImplementation(Individu individuCourant) {
		ImplementationStrategieSuccession implementation = individuCourant.lireImplementationStrategieSuccession();
		StrategieSuccession strategie = null;
		
		switch (implementation) {
			case SUCCESSION_VOISIN_ALEATOIRE:
				strategie = strategieStrategieSuccessionAleatoire;
				break;
			case SUCCESSION_VOISIN_PREMIER:
				strategie = strategieStrategieSuccessionPremier;
				break;
		}
		
		return strategie.determinerSuccesseur(individuCourant);
	}
	
	private final static StrategieSuccessionVoisinAleatoire strategieStrategieSuccessionAleatoire = new StrategieSuccessionVoisinAleatoire();
	private final static StrategieSuccessionPremierVoisin strategieStrategieSuccessionPremier = new StrategieSuccessionPremierVoisin();
}
