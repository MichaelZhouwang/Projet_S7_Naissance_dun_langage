package strategie.modele;

import exception.StrategieException;
import strategie.enumeration.ImplementationStrategieSuccession;
import strategie.implementation.StrategieSuccessionPremierVoisin;
import strategie.implementation.StrategieSuccessionVoisinAleatoire;
import systeme.Individu;
import systeme.Voisin;

public abstract class StrategieSuccession {
	protected abstract Voisin determinerSuccesseur(Individu individuCourant);

	public static Voisin executerImplementation(Individu individuCourant) throws StrategieException {
		ImplementationStrategieSuccession implementation = individuCourant.lireImplStrategieSuccession();
		StrategieSuccession strategie = null;
		
		switch (implementation) {
			case SUCCESSION_VOISIN_ALEATOIRE:
				strategie = strategieSuccessionVoisinAleatoire;
				break;
			case SUCCESSION_PREMIER_VOISIN:
				strategie = strategieSuccessionPremierVoisin;
				break;
			default:
				throw new StrategieException("L'implementation de strategie '" + implementation + "' n'est associee a aucune classe concrete (switch incomplet)");
		}
		
		try {
			return strategie.determinerSuccesseur(individuCourant);
		}
		catch (Exception exception) {
			throw new StrategieException("Strategie '" + implementation + "' a provoquee une exception lors de son execution (contexte illegal)");
		}
	}

	private final static StrategieSuccessionVoisinAleatoire strategieSuccessionVoisinAleatoire = new StrategieSuccessionVoisinAleatoire();
	private final static StrategieSuccessionPremierVoisin strategieSuccessionPremierVoisin = new StrategieSuccessionPremierVoisin();
}
