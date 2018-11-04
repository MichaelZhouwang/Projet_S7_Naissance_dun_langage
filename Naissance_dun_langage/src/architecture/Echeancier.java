package architecture;

import java.util.ArrayList;
import java.util.TreeMap;

import condition.enums.CategorieConditionArret;
import evenement.Evenemen;

public class Echeancier  {
	private int compteurEvenements;
	private TreeMap<Date, ArrayList<Evenemen>> evenementsAVenir;
	private TreeMap<Date, ArrayList<Evenemen>> evenementsPasses;
	
	public Echeancier() {
		compteurEvenements = 0;
		evenementsAVenir = new TreeMap<Date, ArrayList<Evenemen>>();
		evenementsPasses = new TreeMap<Date, ArrayList<Evenemen>>();
	}
	
	public TreeMap<Date, ArrayList<Evenemen>> lireEvenementsPasses() {
		return evenementsPasses;
	}
	
	public void ajouterPremierEvenementEnDate(Evenemen evenement, Date date) {
		ArrayList<Evenemen> liste = evenementsAVenir.get(date);
		
		if (liste != null) {
			liste.add(0, evenement);
		}
		else {
			liste = new ArrayList<Evenemen>() ;
			liste.add(evenement);
			evenementsAVenir.put(date, liste);
		}
	}
	
	public void ajouterDernierEvenementEnDate(Evenemen evenement, Date date) {
		ArrayList<Evenemen> liste = evenementsAVenir.get(date);

		if (liste != null) {
			liste.add(liste.size(), evenement);
		}
		else {
			liste = new ArrayList<Evenemen>() ;
			liste.add(evenement);
			evenementsAVenir.put(date, liste);
		}
	}
	
	public Date dateProchainEvenement() {
		return evenementsAVenir.firstKey();
	}
	
	public void declencherProchainEvenement() {
		Date prochaineDate = evenementsAVenir.firstKey();
		ArrayList<Evenemen> listeEvenements = evenementsAVenir.get(prochaineDate);
		
		if (listeEvenements != null) {
			
			Evenemen evenement = listeEvenements.get(0);
			
			if (evenement != null) {
				
				// On retire l'évènement avant de le déclencher	
				listeEvenements.remove(0);
				if (listeEvenements.isEmpty()) {
					evenementsAVenir.remove(prochaineDate);
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
			
			if (evenementsPasses.get(prochaineDate) == null) {
				ArrayList<Evenemen> listeHistorique = new ArrayList<Evenemen>();
				listeHistorique.add(evenement);
				evenementsPasses.put(prochaineDate, listeHistorique);
			}
			else {
				evenementsPasses.get(prochaineDate).add(evenementsPasses.get(prochaineDate).size(), evenement);
			}
		}
		else {
			System.out.println("Pas d'évènement trouvé, fin de la simulation");
		}
	}
}
