package systeme;

import java.util.ArrayList;
import java.util.TreeMap;

import condition.enums.CategorieConditionArret;
import evenement.Evenement;
import temps.Date;

public class Echeancier  {
	private int compteurEvenements;
	private TreeMap<Date, ArrayList<Evenement>> evenements;
	
	public Echeancier() {
		compteurEvenements = 0;
		evenements = new TreeMap<Date, ArrayList<Evenement>>();
	}
	
	public TreeMap<Date, ArrayList<Evenement>> lireEvenementsPasses() {
		return evenements;
	}
	
	public void ajouterPremierEvenementEnDate(Evenement evenement, Date date) {
		ArrayList<Evenement> liste = evenements.get(date);
		
		if (liste != null) {
			liste.add(0, evenement);
		}
		else {
			liste = new ArrayList<Evenement>() ;
			liste.add(evenement);
			evenements.put(date, liste);
		}
	}
	
	public void ajouterDernierEvenementEnDate(Evenement evenement, Date date) {
		ArrayList<Evenement> liste = evenements.get(date);

		if (liste != null) {
			liste.add(liste.size(), evenement);
		}
		else {
			liste = new ArrayList<Evenement>() ;
			liste.add(evenement);
			evenements.put(date, liste);
		}
	}
	
	public Date dateProchainEvenement() {
		return evenements.firstKey();
	}
	
	public void declencherProchainEvenement() {
		Date prochaineDate = evenements.firstKey();
		ArrayList<Evenement> listeEvenements = evenements.get(prochaineDate);
		
		if (listeEvenements != null) {
			
			Evenement evenement = listeEvenements.get(0);
			
			if (evenement != null) {
				
				// On retire l'évènement avant de le déclencher	
				listeEvenements.remove(0);
				if (listeEvenements.isEmpty()) {
					evenements.remove(prochaineDate);
				}
				
				compteurEvenements++;
				if (Systeme.lireConditionArret() == CategorieConditionArret.DEUXCENT_ITERATIONS && compteurEvenements == 200) {
					Systeme.ajouterPremierEvenementEnDate(Systeme.genererEvenementFinal(evenement), Systeme.lireDateHorloge());
				}
				
				evenement.declencher();
			}
			else {
				System.out.println("Pas d'évènement trouvé, fin de la simulation");
			}
		}
		else {
			System.out.println("Pas d'évènement trouvé, fin de la simulation");
		}
	}
}
