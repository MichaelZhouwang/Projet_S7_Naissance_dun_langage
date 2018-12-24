package strategie.implementation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import evenement.enumeration.IssueEvenement;
import evenement.enumeration.TypeEvenement;
import lexique.Lemme;
import lexique.TableOccurrencesLemmes;
import strategie.modele.StrategieSelection;
import systeme.Individu;

public class StrategieSelectionMoinsEmis extends StrategieSelection {

	@Override
	public Lemme selectionnerLemme(Individu individuCourant, Lemme lemmeCourant) {
		int nombreMinEmissions = Integer.MAX_VALUE;
		int nombreEmissionsLemmeCourant = Integer.MAX_VALUE;
		Lemme lemmeMinEmissions = null;

		TableOccurrencesLemmes tableOccurrencesLemmes = individuCourant.obtenirTableOccurrencesLemmes();
		
		Collection<Lemme> lemmes = new ArrayList<Lemme>();
		if (tableOccurrencesLemmes.containsKey(TypeEvenement.EMISSION) && tableOccurrencesLemmes.get(TypeEvenement.EMISSION).containsKey(IssueEvenement.SUCCES)) {
			lemmes = tableOccurrencesLemmes.get(TypeEvenement.EMISSION).get(IssueEvenement.SUCCES).keySet();
		}
	
		for (Lemme _lemmeCourant : lemmes) {
			nombreEmissionsLemmeCourant = individuCourant.obtenirTableOccurrencesLemmes().obtenirListeOccurrencesLemmes(_lemmeCourant, TypeEvenement.EMISSION, IssueEvenement.SUCCES).size();
			
			if (individuCourant.connaitLemme(_lemmeCourant) && nombreEmissionsLemmeCourant < nombreMinEmissions) {
				lemmeMinEmissions = _lemmeCourant;
			}
		}
		
		return lemmeMinEmissions;
	}
}
