package strategie.modele;

import lexique.Lemme;
import strategie.enumeration.ImplementationStrategieSelection;
import strategie.implementation.StrategieSelectionAleatoire;
import strategie.implementation.StrategieSelectionMoinsEmis;
import strategie.implementation.StrategieSelectionPremier;
import systeme.Individu;

public abstract class StrategieSelection {
	protected abstract Lemme selectionnerLemme(Individu individuCourant, Lemme lemmeCourant);

	public static Lemme executerImplementationEmission(Individu individuCourant) {
		return executerImplementation(individuCourant.lireImplementationStrategieSelectionEmission(), individuCourant, null);
	}
	
	public static Lemme executerImplementationElimination(Individu individuCourant, Lemme lemmeCourant) {
		return executerImplementation(individuCourant.lireImplementationStrategieSelectionElimination(), individuCourant, lemmeCourant);
	}
	
	public static Lemme executerImplementation(ImplementationStrategieSelection implementation, Individu individuCourant, Lemme lemmeCourant) {
		
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
		}
		
		return strategie.selectionnerLemme(individuCourant, lemmeCourant);
	}
	
	private final static StrategieSelectionAleatoire strategieSelectionAleatoire = new StrategieSelectionAleatoire();
	private final static StrategieSelectionPremier strategieSelectionPremier = new StrategieSelectionPremier();
	private final static StrategieSelectionMoinsEmis strategieSelectionMoinsEmis = new StrategieSelectionMoinsEmis();
}
