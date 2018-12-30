package systeme;

import java.util.ArrayList;
import java.util.TreeMap;

import evenement.modele.Evenement;
import exception.EcheancierException;
import systeme.enumeration.TypeCritereArret;
import temps.Date;

public class Echeancier extends TreeMap<Date, ArrayList<Evenement>>  {
	private static final long serialVersionUID = 1L;

	private int evenementsDeclenches;
	
	public Echeancier() {
		evenementsDeclenches = 0;
	}

	public void ajouterPremierEvenementEnDate(Evenement evenement, Date date) {
		ArrayList<Evenement> liste = get(date);
		
		if (liste != null) {
			liste.add(0, evenement);
		}
		else {
			liste = new ArrayList<Evenement>() ;
			liste.add(evenement);
			put(date, liste);
		}
	}
	
	public void ajouterDernierEvenementEnDate(Evenement evenement, Date date) {
		ArrayList<Evenement> liste = get(date);

		if (liste != null) {
			liste.add(liste.size(), evenement);
		}
		else {
			liste = new ArrayList<Evenement>() ;
			liste.add(evenement);
			put(date, liste);
		}
	}
	
	public Date dateProchainEvenement() {
		return firstKey();
	}
	
	public void declencherProchainEvenement() throws Exception {
		Date prochaineDate = firstKey();
		ArrayList<Evenement> listeEvenements = get(prochaineDate);
		
		if (listeEvenements != null) {
			
			Evenement evenement = listeEvenements.get(0);
			
			if (evenement != null) {
				
				// On retire l'evenement avant de le declencher
				listeEvenements.remove(evenement);
				if (listeEvenements.isEmpty()) {
					remove(prochaineDate);
				}

				if (Systeme.lireTypeCritereArret() == TypeCritereArret.DATE_ATTEINTE && prochaineDate.lireValeur() >= Systeme.lireObjectifCritereArret()) {
					Systeme.declencherEvenementFinal(evenement);
				}

				evenementsDeclenches++;
				if (Systeme.lireTypeCritereArret() == TypeCritereArret.EVENEMENTS_DECLENCHES && evenementsDeclenches >= Systeme.lireObjectifCritereArret()) {
					Systeme.declencherEvenementFinal(evenement);
				}

				evenement.declencher();
			}
			else {
				throw new EcheancierException("Situation anormale : aucun evenement a declencher");
			}
		}
		else {
			throw new EcheancierException("Situation anormale : aucun evenement a declencher");
		}
	}
}
