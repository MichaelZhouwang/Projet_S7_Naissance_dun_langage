package lexique;

import java.util.ArrayList;
import java.util.HashMap;

import evenement.enums.IssueEvenement;
import evenement.enums.TypeEvenement;
import systeme.Individu;
import temps.Date;

public class TableOccurrencesLemmes extends HashMap<Lemme, ArrayList<OccurrenceLemme>> {
	private static final long serialVersionUID = 1L;

	public void nouvelleOccurence(Lemme lemme, Date date, Individu individu, TypeEvenement typeEvenement, IssueEvenement issueEvenement) {
		ArrayList<OccurrenceLemme> listeOccurrences = new ArrayList<OccurrenceLemme>();
		
		if (!containsKey(lemme)) {
			listeOccurrences.add(new OccurrenceLemme(lemme, date, individu, typeEvenement, issueEvenement));
			put(lemme, listeOccurrences);
		}
		else {
			get(lemme).add(new OccurrenceLemme(lemme, date, individu, typeEvenement, issueEvenement));	
		}
	}
	
	public ArrayList<OccurrenceLemme> obtenirOccurrencesSelonCriteres(Lemme lemme, TypeEvenement typeEvenement, IssueEvenement issueEvenement) {
		ArrayList<OccurrenceLemme> listeOccurrencesFiltrees = new ArrayList<OccurrenceLemme>();
		ArrayList<OccurrenceLemme> listeOccurrences = new ArrayList<OccurrenceLemme>();
		
		if (lemme != null && get(lemme) == null) {
			return listeOccurrencesFiltrees;
		}
		else if (lemme != null) {
			listeOccurrences = get(lemme);
		}
		
		for (OccurrenceLemme _occurrence : listeOccurrences) {
				if ((typeEvenement == null || _occurrence.lireTypeEvenement() == typeEvenement)
				&& (issueEvenement == null || _occurrence.lireIssueEvenement() == issueEvenement)) {
				listeOccurrencesFiltrees.add(_occurrence);
			}
		}

		return listeOccurrencesFiltrees;
	}
	
	public int nombreOccurrencesLemmesSelonCriteres(Lemme lemme, Individu acteur, TypeEvenement typeEvenement, IssueEvenement issueEvenement) {
		ArrayList<OccurrenceLemme> occurrencesLemme = obtenirOccurrencesSelonCriteres(lemme, typeEvenement, issueEvenement);
		
		return (occurrencesLemme != null) ? occurrencesLemme.size() : 0;
	}
}
