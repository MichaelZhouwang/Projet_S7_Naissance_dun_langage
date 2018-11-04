package architecture;

import java.util.ArrayList;
import java.util.HashMap;

import condition.enums.CategorieConditionArret;
import condition.enums.ImplementationCondition;
import condition.enums.UtilisationCondition;
import evenement.Evenemen;
import strategie.enums.ImplementationStrategie;
import strategie.enums.UtilisationStrategie;

public class Systeme {
	private static ArrayList<Individu> individus = new ArrayList<Individu>();;
	
	private static Echeancier echeancier = new Echeancier();
	private static Horloge horloge = new Horloge();
	private static CategorieConditionArret conditionArret;
	
	public static Date lireDateHorloge() {
		return horloge.lireDate();
	}
	
	public static void ajouterPremierEvenementEnDate(Evenemen evenement, Date date) {
		echeancier.ajouterPremierEvenementEnDate(evenement, date);
	}
	
	public static void ajouterDernierEvenementEnDate(Evenemen evenement, Date date) {
		echeancier.ajouterDernierEvenementEnDate(evenement, date);
	}
	
	public static void declencherProchainEvenement() {
		horloge.mettreAJourDate(echeancier.dateProchainEvenement());
		echeancier.declencherProchainEvenement();
	}
	
	public static CategorieConditionArret lireConditionArret() {
		return conditionArret;
	}
	
	public static EvenementInitial genererEvenementInitial() {
		return new EvenementInitial(null);
	}
	
	public static EvenementFinal genererEvenementFinal(Evenemen evenementInitiateur) {
		return new EvenementFinal(evenementInitiateur);
	}

	private static void generer() {
		int nombreIndividus = 4;
		
		HashMap<UtilisationCondition, ImplementationCondition> parametresConditions = new HashMap<UtilisationCondition, ImplementationCondition>();
		
		parametresConditions.put(UtilisationCondition.EMISSION, ImplementationCondition.TOUJOURS_VERIFIEE);
		parametresConditions.put(UtilisationCondition.RECEPTION, ImplementationCondition.TOUJOURS_VERIFIEE);
		parametresConditions.put(UtilisationCondition.MEMORISATION, ImplementationCondition.TOUJOURS_VERIFIEE);
		
		HashMap<UtilisationStrategie, ImplementationStrategie> parametresAlgorithmes = new HashMap<UtilisationStrategie, ImplementationStrategie>();
		
		parametresAlgorithmes.put(UtilisationStrategie.SELECTION_LEMME, ImplementationStrategie.SELECTION_PREMIER);
		parametresAlgorithmes.put(UtilisationStrategie.ELIMINATION_LEMME, ImplementationStrategie.SELECTION_MOINS_EMIS);
		parametresAlgorithmes.put(UtilisationStrategie.SUCCESSION, ImplementationStrategie.SUCCESSION_VOISIN_ALEATOIRE);
		
		for (int i = 0; i < nombreIndividus; i++) {
			individus.add(new Individu(5, 10));
		}
		
		for (Individu individu : individus) {
			for (Individu voisin : individus) {
				if (voisin != individu) {
					individu.ajouterVoisin(voisin);
					individu.ajouterDelaisVoisin(voisin, Delais.delaisReceptionParDéfaut);
				}
			}
			
			for (UtilisationCondition typeCondition : parametresConditions.keySet()) {
				individu.definirCondition(typeCondition, parametresConditions.get(typeCondition));
			}
			for (UtilisationStrategie typeAlgorithme : parametresAlgorithmes.keySet()) {
				individu.definirAlgorithme(typeAlgorithme, parametresAlgorithmes.get(typeAlgorithme));
			}
		}
		
		conditionArret = CategorieConditionArret.DEUXCENT_ITERATIONS;
	}
	
	public static void routine() {
		generer();

		ajouterPremierEvenementEnDate(genererEvenementInitial(), horloge.lireDate());
		declencherProchainEvenement();
		
		for (Individu individu : individus) {
			System.out.println(individu);
		}
	}
	
	// Évènement initial
	
	private static class EvenementInitial extends Evenemen {

		public EvenementInitial(Evenemen evenementInitiateur) {
			super(evenementInitiateur);
		}

		@Override
		public void declencher() {
			Individu individu = individus.get(0);
			Lemme lemmeEnEmission = individu.lireAlgorithmeSelection().determinerLemme();
			
			ajouterPremierEvenementEnDate(
				individu.genererEvenementEmission(this, lemmeEnEmission),
				horloge.lireDate().plusDelais(Delais.delaisPassageParDéfaut)
			);
			
			declencherProchainEvenement();
		}
	}
	
	private static class EvenementFinal extends Evenemen {

		public EvenementFinal(Evenemen evenementInitiateur) {
			super(evenementInitiateur);
		}

		@Override
		public void declencher() {
			// Aucun prochain évènement n'est déclenché
		}

	}
}
