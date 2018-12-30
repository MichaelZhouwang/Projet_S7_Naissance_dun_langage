package strategie.implementation;

import lexique.Lemme;
import strategie.modele.StrategieSelection;
import systeme.Individu;

public class StrategieSelectionPremier extends StrategieSelection {

	@Override
	public Lemme selectionnerLemme(Individu individuCourant, Lemme lemmeCourant) {
		return individuCourant.obtenirLexique().get(0);
	}
}
