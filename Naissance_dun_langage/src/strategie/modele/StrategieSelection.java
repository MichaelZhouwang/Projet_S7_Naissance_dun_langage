package strategie.modele;

import exception.StrategieException;
import lexique.Lemme;
import strategie.enumeration.ImplementationStrategieSelection;
import strategie.implementation.StrategieSelectionAleatoire;
import strategie.implementation.StrategieSelectionMoinsEmis;
import strategie.implementation.StrategieSelectionPremier;
import systeme.Individu;

public abstract class StrategieSelection {
	protected abstract Lemme selectionnerLemme(Individu individuCourant, Lemme lemmeCourant);

	public static Lemme executerImplementationEmission(Individu individuCourant) throws StrategieException {
		return executerImplementation(individuCourant.lireImplStrategieSelectionEmission(), individuCourant, null);
	}
	
	public static Lemme executerImplementationElimination(Individu individuCourant, Lemme lemmeCourant) throws StrategieException {
		return executerImplementation(individuCourant.lireImplStrategieSelectionElimination(), individuCourant, lemmeCourant);
	}
	
	public static Lemme executerImplementation(ImplementationStrategieSelection implementation, Individu individuCourant, Lemme lemmeCourant) throws StrategieException {
		StrategieSelection strategie = null;

		switch (implementation) {
			case SELECTION_ALEATOIRE:
				strategie = strategieSelectionAleatoire;
				break;
			case SELECTION_PREMIER:
				strategie = strategieSelectionPremier;
				break;
			case SELECTION_MOINS_EMIS:
				strategie = strategieSelectionMoinsEmis;
				break;
			default:
				throw new StrategieException("L'implementation de strategie '" + implementation + "' n'est associee a aucune classe concrete (switch incomplet)");
		}
		
		try {
			return strategie.selectionnerLemme(individuCourant, lemmeCourant);
		}
		catch (Exception exception) {
			throw new StrategieException("Strategie '" + implementation + "' a provoquee une exception lors de son execution (contexte illegal)");
		}
	}
	
	private final static StrategieSelectionAleatoire strategieSelectionAleatoire = new StrategieSelectionAleatoire();
	private final static StrategieSelectionPremier strategieSelectionPremier = new StrategieSelectionPremier();
	private final static StrategieSelectionMoinsEmis strategieSelectionMoinsEmis = new StrategieSelectionMoinsEmis();
}
