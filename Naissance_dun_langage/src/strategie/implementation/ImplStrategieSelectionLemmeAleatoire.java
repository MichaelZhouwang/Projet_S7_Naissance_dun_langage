package strategie.implementation;

import java.util.Random;

import strategie.modele.StrategieSelection;
import systeme.Individu;
import systeme.lexique.Lemme;

/**
 * Implementation de la classe StrategieSelection selectionnant un lemme de maniere aleatoire dans le lexique de l'individu courant
 * 
 * @author Charles MECHERIKI & Yongda LIN
 *
 */
public class ImplStrategieSelectionLemmeAleatoire extends StrategieSelection {
	private final Random random = new Random();
	
	@Override
	protected Lemme selectionnerLemme(Individu individuCourant, Lemme lemmeCourant) {
		return individuCourant.obtenirLexique().get(random.nextInt(individuCourant.obtenirLexique().size()));
	}
}