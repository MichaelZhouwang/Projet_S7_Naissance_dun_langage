package strategie.implementation;

import strategie.modele.StrategieSelection;
import systeme.Individu;
import systeme.lexique.Lemme;

/**
 * Implementation de la classe StrategieSelection selectionnant le premier lemme du lexique de l'individu courant
 * 
 * @author Charles MECHERIKI & Yongda LIN
 *
 */
public class ImplStrategieSelectionPremierLemme extends StrategieSelection {

	@Override
	public Lemme selectionnerLemme(Individu individuCourant, Lemme lemmeCourant) {
		return individuCourant.obtenirLexique().get(0);
	}
}
