package architecture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import condition.ConditionDependante;
import condition.ConditionIndependante;
import condition.enums.CategorieConditionArret;
import condition.enums.ImplementationCondition;
import condition.enums.UtilisationCondition;
import evenement.Evenemen;
import strategie.StrategieSelectionLemmeDependante;
import strategie.StrategieSelectionLemmeIndependante;
import strategie.StrategieSuccession;
import strategie.enums.ImplementationStrategie;
import strategie.enums.UtilisationStrategie;

public class Individu {
	private static int effectif = 0;
	private int ID;
	private char lettre;
	
	private Lexique lexique;
	
	private ArrayList<Individu> voisins;
	private HashMap<Individu, Delais> delaisVoisins;
	
	private HashMap<Lemme, OccurrencesLemme> occurrencesLemmes;
	
	private ConditionDependante conditionEmission;
	private ConditionDependante conditionReception;
	private ConditionDependante conditionMemorisation;
	
	private StrategieSelectionLemmeDependante strategieSelectionLemme;
	private StrategieSelectionLemmeDependante strategieEliminationLemme;
	private StrategieSuccession strategieSuccession;
	
	public Individu(int tailleInitialeLexique, int tailleMaximaleLexique) {
		ID = effectif++;
		lettre = (char)(65 + ID);
		
		lexique = new Lexique(tailleInitialeLexique, tailleMaximaleLexique);
		lexique.generer(this);
		
		delaisVoisins = new HashMap<Individu, Delais>();
		voisins = new ArrayList<Individu>();
		occurrencesLemmes = new HashMap<Lemme, OccurrencesLemme>(); 
		
		for (Lemme lemme : lexique) {
			occurrencesLemmes.put(lemme, new OccurrencesLemme(lemme));
		}
	}

	// Lettre
	
	public char lireLettre() {
		return lettre;
	}
	
	// Lexique
	
	public ArrayList<Lemme> lireLexique() {
		return lexique;
	}
	
	public int lireTailleLexique() {
		return lexique.size();
	}
	
	public boolean aLexiquePlein() {
		return lexique.estPlein();
	}
	
	public boolean connaitLemme(Lemme lemme) {
		return lexique.contains(lemme);
	}
	
	public void memoriserLemme(Lemme lemme) {
		lexique.add(lemme);
	}
	
	public void remplacerLemme(Lemme lemmeARemplacer, Lemme nouveauLemme) {
		if (lexique.remove(lemmeARemplacer)) {
			lexique.add(nouveauLemme);
		}
	}
	
	// Voisins
	
	public void ajouterVoisin(Individu voisin) {
		voisins.add(voisin);
	}
	
	public ArrayList<Individu> lireVoisins() {
		return voisins;
	}
	
	public void ajouterDelaisVoisin(Individu voisin, Delais delais) {
		delaisVoisins.put(voisin, delais);
	}
	
	public Delais lireDelaisVoisin(Individu voisin) {
		return delaisVoisins.get(voisin);
	}
	
	// Occurrences lemmes
	
	public void nouvelleOccurrenceEmission(Lemme lemme, Date date) {
		if (!occurrencesLemmes.containsKey(lemme)) {
			occurrencesLemmes.put(lemme, new OccurrencesLemme(lemme));
		}
		occurrencesLemmes.get(lemme).nouvelleEmission(date);
	}
	
	public void nouvelleOccurrenceReception(Lemme lemme, Date date) {
		if (!occurrencesLemmes.containsKey(lemme)) {
			occurrencesLemmes.put(lemme, new OccurrencesLemme(lemme));
		}
		occurrencesLemmes.get(lemme).nouvelleReception(date);
	}
	
	public void nouvelleOccurrenceMemorisation(Lemme lemme, Date date) {
		if (!occurrencesLemmes.containsKey(lemme)) {
			occurrencesLemmes.put(lemme, new OccurrencesLemme(lemme));
		}
		occurrencesLemmes.get(lemme).nouvelleMemorisation(date);
	}
	
	public void nouvelleOccurrenceRemplacement(Lemme lemme, Date date) {
		if (!occurrencesLemmes.containsKey(lemme)) {
			occurrencesLemmes.put(lemme, new OccurrencesLemme(lemme));
		}
		occurrencesLemmes.get(lemme).nouveauRemplacement(date);
	}
	
	public HashMap<Lemme, OccurrencesLemme> lireOccurrencesLemmes() {
		return occurrencesLemmes;
	}

	// Conditions
	
	// Factory
	public void definirCondition(UtilisationCondition typeCondition, ImplementationCondition condition) {
		ConditionDependante conditionDependante = null;
		
		switch (condition) {
			case PROBABILITE_UNIFORME:
				conditionDependante = new ConditionProbabiliteUniforme();
			break;
			case TOUJOURS_VERIFIEE:
				conditionDependante = new ConditionToujoursVerifiee();
			break;
			case JAMAIS_VERIFIEE:
				conditionDependante = new ConditionJamaisVerifiee();
			break;
		}
		
		switch (typeCondition) {
			case EMISSION:
				conditionEmission = conditionDependante;
				break;
			case RECEPTION:
				conditionReception = conditionDependante;
				break;
			case MEMORISATION:
				conditionMemorisation = conditionDependante;
				break;
		}
	}

	public boolean peutEmettre(Lemme lemmeEnEmission) {
		return conditionEmission.estSatisfaite(lemmeEnEmission);
	}
	
	public boolean peutRecevoir(Lemme lemmeEnReception) {
		return conditionReception.estSatisfaite(lemmeEnReception);
	}

	public boolean peutMemoriser(Lemme lemmeEnMemorisation) {
		return conditionMemorisation.estSatisfaite(lemmeEnMemorisation);
	}
	
	// Algorithmes
	
	// Factory
	public void definirAlgorithme(UtilisationStrategie utilisationStrategie, ImplementationStrategie implementationStrategie) {
		StrategieSelectionLemmeDependante strategieSelectionLemmeDependante = null;
		StrategieSuccession strategieSuccession = null;
		
		switch (implementationStrategie) {
			case SELECTION_PREMIER:
				strategieSelectionLemmeDependante = new AlgorithmeSelectionPremier();
				break;
			case SELECTION_UNIFORME:
				strategieSelectionLemmeDependante = new AlgorithmeSelectionUniforme();
				break;
			case SELECTION_MOINS_EMIS:
				strategieSelectionLemmeDependante = new AlgorithmeSelectionMoinsEmis();
				break;
			case SUCCESSION_VOISIN_ALEATOIRE:
				strategieSuccession = new AlgorithmeSuccessionVoisinAleatoire();
				break;
		}
		
		switch (utilisationStrategie) {
			case SELECTION_LEMME:
				strategieSelectionLemme = strategieSelectionLemmeDependante;
				break;
			case ELIMINATION_LEMME:
				strategieEliminationLemme = strategieSelectionLemmeDependante;
				break;
			case SUCCESSION:
				this.strategieSuccession = strategieSuccession;
				break;
		}
	}
	
	public StrategieSelectionLemmeDependante lireAlgorithmeSelection() {
		return strategieSelectionLemme;
	}
	
	public StrategieSelectionLemmeDependante lireAlgorithmeElimination() {
		return strategieEliminationLemme;
	}
	
	public StrategieSuccession lireAlgorithmeSuccession() {
		return strategieSuccession;
	}
	
	
	public EvenementEmission genererEvenementEmission(Evenemen evenementInitiateur, Lemme lemmeEnEmission) {
		return new EvenementEmission(evenementInitiateur, lemmeEnEmission);
	}
	
	public EvenementReception genererEvenementReception(EvenementEmission evenementInitiateur) {
		return new EvenementReception(evenementInitiateur);
	}
	
	
	private class EvenementEmission extends Evenemen {

		private Evenemen evenementInitiateur;
		private Lemme lemmeEnEmission;
		
		public EvenementEmission(Evenemen evenementInitiateur, Lemme lemmeEnEmission) {
			super(evenementInitiateur);
			this.evenementInitiateur = evenementInitiateur;
			this.lemmeEnEmission = lemmeEnEmission;
		}
		
		public Evenemen lireEvenementInitiateur() {
			return evenementInitiateur;
		}
		
		public Lemme lireLemmeEnEmission() {
			return lemmeEnEmission;
		}

		@Override
		public void declencher() {
			Date date = Systeme.lireDateHorloge();
			
			if (peutEmettre(lemmeEnEmission)) {
				
				System.out.println("L'individu " + lettre + " émet le lemme " + lemmeEnEmission + " à la date " + Systeme.lireDateHorloge());
				
				nouvelleOccurrenceEmission(lemmeEnEmission, date);
				
				for (Individu voisin : lireVoisins()) {

					Systeme.ajouterDernierEvenementEnDate(
						voisin.genererEvenementReception(this),
						Systeme.lireDateHorloge().plusDelais(lireDelaisVoisin(voisin))
					);
				}
			}
			
			Individu successeur = lireAlgorithmeSuccession().determinerSuccesseur();	
			Lemme lemmeEnEmission = successeur.lireAlgorithmeSelection().determinerLemme();
			
			Systeme.ajouterDernierEvenementEnDate(
				successeur.genererEvenementEmission(this, lemmeEnEmission),
				Systeme.lireDateHorloge().plusDelais(Delais.delaisPassageParDéfaut)
			);
			
			Systeme.declencherProchainEvenement();
		}
	}
	
	public class EvenementReception extends Evenemen {

		private EvenementEmission evenementInitiateur;
		
		public EvenementReception(EvenementEmission evenementInitiateur) {
			super(evenementInitiateur);
			this.evenementInitiateur = evenementInitiateur;
		}
		
		public EvenementEmission lireEvenementInitiateur() {
			return evenementInitiateur;
		}

		@Override
		public void declencher() {
			Lemme lemmeEnReception = evenementInitiateur.lireLemmeEnEmission();
			
			if (peutRecevoir(lemmeEnReception)) {
				
				System.out.println("L'individu " + lettre + " recoit le lemme " + lemmeEnReception + " à la date " + Systeme.lireDateHorloge());
				nouvelleOccurrenceReception(lemmeEnReception, Systeme.lireDateHorloge());

				if (!connaitLemme(lemmeEnReception)) {
					if (peutMemoriser(lemmeEnReception)) {
						
						System.out.println("L'individu " + lettre + " mémorise le lemme " + lemmeEnReception + " à la date " + Systeme.lireDateHorloge());
						nouvelleOccurrenceMemorisation(lemmeEnReception, Systeme.lireDateHorloge());
						
						if (!aLexiquePlein()) {
							memoriserLemme(lemmeEnReception);
						}
						else {
							Lemme lemmeEnRemplacement = lireAlgorithmeElimination().determinerLemme(lemmeEnReception);
							nouvelleOccurrenceRemplacement(lemmeEnRemplacement, Systeme.lireDateHorloge());
							remplacerLemme(lemmeEnRemplacement, lemmeEnReception);
							
							// Evenement final
							if (Systeme.lireConditionArret() == CategorieConditionArret.LEXIQUE_PLEIN) {
								Systeme.ajouterPremierEvenementEnDate(Systeme.genererEvenementFinal(this), Systeme.lireDateHorloge());
							}
						}
					}
				}
			}
			
			Systeme.declencherProchainEvenement();
		}
	}
	
	// Classes de conditions
	
	private class ConditionProbabiliteUniforme extends ConditionIndependante {
		private final Random random = new Random();
		
		public boolean estSatisfaite() {
			return random.nextFloat() > 0.5;
		}
	}
	
	private class ConditionToujoursVerifiee extends ConditionIndependante {

		public boolean estSatisfaite() {
			return true;
		}
	}
	
	private class ConditionJamaisVerifiee extends ConditionIndependante {

		public boolean estSatisfaite() {
			return false;
		}
	}
	
	// Classes d'algorithmes
	
	private class AlgorithmeSelectionPremier extends StrategieSelectionLemmeIndependante {

		@Override
		public Lemme determinerLemme() {
			return lexique.get(0);
		}
	}
	
	private class AlgorithmeSelectionUniforme extends StrategieSelectionLemmeIndependante {
		private final Random random = new Random();

		@Override
		public Lemme determinerLemme() {
			return lexique.get(random.nextInt(lexique.size()));
		}
	}
	
	private class AlgorithmeSelectionMoinsEmis extends StrategieSelectionLemmeIndependante {
		
		@Override
		public Lemme determinerLemme() {
			int minEmissions = Integer.MAX_VALUE;
			Lemme lemmeMinEmissions = null;
			
			for (Lemme lemme : occurrencesLemmes.keySet()) {
				if (connaitLemme(lemme) && occurrencesLemmes.get(lemme).nombreEmissions() < minEmissions) {
					lemmeMinEmissions = lemme;
				}
			}
			
			return lemmeMinEmissions;
		}
	}
	
	private class AlgorithmeSuccessionVoisinAleatoire extends StrategieSuccession {
		private final Random random = new Random();
		
		@Override
		public Individu determinerSuccesseur() {
			return voisins.get(random.nextInt(voisins.size()));
		}
	}
	
	// toString
	
	@Override
	public String toString() {
		String string = "Individu " + lettre + " : ";
		
		string += lexique + "\n";
		
		for (Lemme lemme : occurrencesLemmes.keySet()) {
			string += occurrencesLemmes.get(lemme) + "\n";
		}

		return string;
	}
}
