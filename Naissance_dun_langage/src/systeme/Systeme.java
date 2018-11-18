package systeme;

import java.util.ArrayList;
import java.util.HashMap;

import condition.enums.CategorieConditionArret;
import condition.enums.ImplementationCondition;
import condition.enums.UtilisationCondition;
import evenement.Evenement;
import lexique.Lemme;
import lexique.OccurrenceLemme;
import strategie.enums.ImplementationStrategie;
import strategie.enums.UtilisationStrategie;
import temps.Date;
import temps.Delais;
import temps.Horloge;

public class Systeme {
	private static ArrayList<Individu> individus = new ArrayList<Individu>();
	
	private static Echeancier echeancier = new Echeancier();
	private static Horloge horloge = new Horloge();
	
	private static HashMap<Integer, Lemme> cacheLemmes = new HashMap<Integer, Lemme>();
	
	private static CategorieConditionArret conditionArret;
	
	public static Date lireDateHorloge() {
		return horloge.lireDate();
	}
	
	public static void ajouterPremierEvenementEnDate(Evenement evenement, Date date) {
		echeancier.ajouterPremierEvenementEnDate(evenement, date);
	}
	
	public static void ajouterDernierEvenementEnDate(Evenement evenement, Date date) {
		echeancier.ajouterDernierEvenementEnDate(evenement, date);
	}
	
	public static void declencherProchainEvenement() {
		horloge.mettreAJourDate(echeancier.dateProchainEvenement());
		echeancier.declencherProchainEvenement();
	}
	
	public static Lemme consommerLemmeCacheEvenement(int idEvenement) {
		return cacheLemmes.remove(idEvenement);
	}
	
	public static void ajouterLemmeCacheEvenement(int idEvenement, Lemme lemme) {
		cacheLemmes.put(idEvenement, lemme);
	}
	
	public static CategorieConditionArret lireConditionArret() {
		return conditionArret;
	}
	
	public static EvenementInitial genererEvenementInitial() {
		return new EvenementInitial(null);
	}
	
	public static EvenementFinal genererEvenementFinal(Evenement evenementInitiateur) {
		return new EvenementFinal(evenementInitiateur);
	}
	
	public static void afficherBilanOccurrences() {
		ArrayList<OccurrenceLemme> occurrencesLemmes = new ArrayList<OccurrenceLemme>();
		
		for (int indice = 0; indice < OccurrenceLemme.lireCompteur(); indice++) {
			occurrencesLemmes.add(null);
		}
		
		for (Individu _individu : individus) {
			for (ArrayList<OccurrenceLemme> _occurrencesLemmes : _individu.lireTableOccurrencesLemmes().values()) {
				for (OccurrenceLemme _occurrenceLemme : _occurrencesLemmes) {
					occurrencesLemmes.remove(_occurrenceLemme.lireID() - 1);
					occurrencesLemmes.add(_occurrenceLemme.lireID() - 1, _occurrenceLemme);
				}
			}
		}
		
		for (OccurrenceLemme _occurrenceLemme : occurrencesLemmes) {
			if (_occurrenceLemme != null) {
				System.out.println(
					_occurrenceLemme.lireTypeEvenement() + " ("
					+ _occurrenceLemme.lireIssueEvenement() + ") du lemme "
					+ _occurrenceLemme.lireLemme() + " par l'individu "
					+ _occurrenceLemme.lireIndividu().lireLettre() + " à la date "
					+ _occurrenceLemme.lireDate() + " [ev. "
					+ _occurrenceLemme.lireID() + "]"
				);
			}
		}
	}

	private static void generer() {
		int nombreIndividus = 4;
		
		HashMap<UtilisationCondition, ImplementationCondition> parametresConditions = new HashMap<UtilisationCondition, ImplementationCondition>();
		
		parametresConditions.put(UtilisationCondition.EMISSION, ImplementationCondition.TOUJOURS_VERIFIEE);
		parametresConditions.put(UtilisationCondition.RECEPTION, ImplementationCondition.TOUJOURS_VERIFIEE);
		parametresConditions.put(UtilisationCondition.MEMORISATION, ImplementationCondition.TOUJOURS_VERIFIEE);
		
		HashMap<UtilisationStrategie, ImplementationStrategie> parametresStrategies = new HashMap<UtilisationStrategie, ImplementationStrategie>();
		
		parametresStrategies.put(UtilisationStrategie.SELECTION_LEMME, ImplementationStrategie.SELECTION_UNIFORME);
		parametresStrategies.put(UtilisationStrategie.ELIMINATION_LEMME, ImplementationStrategie.SELECTION_MOINS_EMIS);
		parametresStrategies.put(UtilisationStrategie.SUCCESSION, ImplementationStrategie.SUCCESSION_VOISIN_ALEATOIRE);
		
		for (int i = 0; i < nombreIndividus; i++) {
			individus.add(new Individu(8, 5));
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
			for (UtilisationStrategie typeStrategie : parametresStrategies.keySet()) {
				individu.definirStrategie(typeStrategie, parametresStrategies.get(typeStrategie));
			}
		}
		
		conditionArret = CategorieConditionArret.LEXIQUE_PLEIN;
	}
	
	public static void routine() {
		generer();

		ajouterPremierEvenementEnDate(genererEvenementInitial(), horloge.lireDate());
		declencherProchainEvenement();
		
		for (Individu individu : individus) {
			System.out.println(individu);
		}
		
		afficherBilanOccurrences();
	}
	
	// Évènement initial
	
	private static class EvenementInitial extends Evenement {

		public EvenementInitial(Evenement evenementInitiateur) {
			super(evenementInitiateur);
		}

		@Override
		public void declencher() {
			Individu individu = individus.get(0);
			
			Lemme lemmeEnEmission = individu.lireStrategieSelection().determinerLemme();
			Evenement evenementEmission = individu.genererEvenementEmission(this);
			
			ajouterLemmeCacheEvenement(evenementEmission.lireID(), lemmeEnEmission);
			
			ajouterPremierEvenementEnDate(
				evenementEmission,
				horloge.lireDate().plusDelais(Delais.delaisPassageParDéfaut)
			);
			
			declencherProchainEvenement();
		}
	}
	
	private static class EvenementFinal extends Evenement {

		public EvenementFinal(Evenement evenementInitiateur) {
			super(evenementInitiateur);
		}

		@Override
		public void declencher() {
			System.out.println("Fin de la simulation : " + conditionArret);
			// Aucun prochain évènement n'est déclenché
		}

	}
}
