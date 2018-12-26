package strategie.implementation;

import java.util.Random;

import lexique.Lemme;
import strategie.modele.StrategieSelection;
import systeme.Individu;

public class StrategieSelectionAleatoire extends StrategieSelection {
	private final Random random = new Random();
	
	@Override
	protected Lemme selectionnerLemme(Individu individuCourant, Lemme lemmeCourant) {
		return individuCourant.obtenirLexique().get(random.nextInt(individuCourant.obtenirLexique().size()));
	}
}