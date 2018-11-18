package systeme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import condition.ConditionDependante;
import condition.ConditionIndependante;
import condition.enums.CategorieConditionArret;
import condition.enums.ImplementationCondition;
import condition.enums.UtilisationCondition;
import evenement.Evenement;
import evenement.enums.IssueEvenement;
import evenement.enums.TypeEvenement;
import lexique.Lemme;
import lexique.Lexique;
import lexique.OccurrenceLemme;
import lexique.TableOccurrencesLemmes;
import strategie.StrategieSelectionLemmeDependante;
import strategie.StrategieSelectionLemmeIndependante;
import strategie.StrategieSuccession;
import strategie.enums.ImplementationStrategie;
import strategie.enums.UtilisationStrategie;
import temps.Date;
import temps.Delais;

public class Individu {
	private static int effectif = 0;
	private int ID;
	private char lettre;
	
	private Lexique lexique;
	private TableOccurrencesLemmes tableOccurrencesLemmes;
	
	private ArrayList<Individu> voisins;
	private HashMap<Individu, Delais> delaisVoisins;
	
	private ConditionDependante conditionEmission;
	private ConditionDependante conditionReception;
	private ConditionDependante conditionMemorisation;
	
	private StrategieSelectionLemmeDependante strategieSelectionLemme;
	private StrategieSelectionLemmeDependante strategieEliminationLemme;
	private StrategieSuccession strategieSuccession;
	
	public Individu(int tailleInitialeLexique, int tailleMaximaleLexique) {
		ID = effectif++;
		lettre = (char)(65 + ID);
		
		tableOccurrencesLemmes = new TableOccurrencesLemmes();
		lexique = new Lexique(tailleInitialeLexique, tailleMaximaleLexique);
		lexique.generer(this);
		
		delaisVoisins = new HashMap<Individu, Delais>();
		voisins = new ArrayList<Individu>();
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
	
	public void nouvelleOccurrenceLemme(Lemme lemme, Date date, TypeEvenement typeEvenement, IssueEvenement issueEvenement) {
		tableOccurrencesLemmes.nouvelleOccurence(lemme, date, this, typeEvenement, issueEvenement);
	}
	
	public ArrayList<OccurrenceLemme> obtenirOccurrencesLemmesSelonCriteres(Lemme lemme, TypeEvenement typeEvenement, IssueEvenement issueEvenement) {
		return tableOccurrencesLemmes.obtenirOccurrencesSelonCriteres(lemme, typeEvenement, issueEvenement);
	}
	
	public int nombreOccurrencesLemmesSelonCriteres(Lemme lemme, TypeEvenement typeEvenement, IssueEvenement issueEvenement) {
		return tableOccurrencesLemmes.nombreOccurrencesLemmesSelonCriteres(lemme, this, typeEvenement, issueEvenement);
	}
	
	public TableOccurrencesLemmes lireTableOccurrencesLemmes() {
		return tableOccurrencesLemmes;
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
	
	// Strategies
	
	// Factory
	public void definirStrategie(UtilisationStrategie utilisationStrategie, ImplementationStrategie implementationStrategie) {
		StrategieSelectionLemmeDependante strategieSelectionLemmeDependante = null;
		StrategieSuccession strategieSuccession = null;
		
		switch (implementationStrategie) {
			case SELECTION_PREMIER:
				strategieSelectionLemmeDependante = new StrategieSelectionPremier();
				break;
			case SELECTION_UNIFORME:
				strategieSelectionLemmeDependante = new StrategieSelectionUniforme();
				break;
			case SELECTION_MOINS_EMIS:
				strategieSelectionLemmeDependante = new StrategieSelectionMoinsEmis();
				break;
			case SUCCESSION_VOISIN_ALEATOIRE:
				strategieSuccession = new StrategieSuccessionVoisinAleatoire();
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
	
	public StrategieSelectionLemmeDependante lireStrategieSelection() {
		return strategieSelectionLemme;
	}
	
	public StrategieSelectionLemmeDependante lireStrategieElimination() {
		return strategieEliminationLemme;
	}
	
	public StrategieSuccession lireStrategieSuccession() {
		return strategieSuccession;
	}
	
	
	public EvenementEmission genererEvenementEmission(Evenement evenementInitiateur) {
		return new EvenementEmission(evenementInitiateur);
	}
	
	public EvenementReception genererEvenementReception(EvenementEmission evenementInitiateur) {
		return new EvenementReception(evenementInitiateur);
	}
	
	private class EvenementEmission extends Evenement {

		public EvenementEmission(Evenement evenementInitiateur) {
			super(evenementInitiateur);
		}

		@Override
		public void declencher() {
			Date date = Systeme.lireDateHorloge();
			Lemme lemmeEnEmission = Systeme.consommerLemmeCacheEvenement(lireID());
			
			if (peutEmettre(lemmeEnEmission)) {
				nouvelleOccurrenceLemme(lemmeEnEmission, date, TypeEvenement.EMISSION, IssueEvenement.SUCCES);
				
				for (Individu voisin : lireVoisins()) {
					Evenement evenementReception = voisin.genererEvenementReception(this);
					Systeme.ajouterLemmeCacheEvenement(evenementReception.lireID(), lemmeEnEmission);
					
					Systeme.ajouterDernierEvenementEnDate(
						evenementReception,
						Systeme.lireDateHorloge().plusDelais(lireDelaisVoisin(voisin))
					);
				}
			}
			else {
				nouvelleOccurrenceLemme(lemmeEnEmission, date, TypeEvenement.EMISSION, IssueEvenement.ECHEC);
			}
			
			Individu successeur = lireStrategieSuccession().determinerSuccesseur();
			Lemme lemmeAEmettre = successeur.lireStrategieSelection().determinerLemme();
			Evenement evenementEmission = successeur.genererEvenementEmission(this);
			
			Systeme.ajouterLemmeCacheEvenement(evenementEmission.lireID(), lemmeAEmettre);
			
			Systeme.ajouterDernierEvenementEnDate(
				evenementEmission,
				Systeme.lireDateHorloge().plusDelais(Delais.delaisPassageParDéfaut)
			);
			
			Systeme.declencherProchainEvenement();
		}
	}
	
	public class EvenementReception extends Evenement {

		public EvenementReception(EvenementEmission evenementInitiateur) {
			super(evenementInitiateur);
		}

		@Override
		public void declencher() {
			Date date = Systeme.lireDateHorloge();
			Lemme lemmeEnReception = Systeme.consommerLemmeCacheEvenement(lireID());
			
			if (peutRecevoir(lemmeEnReception)) {
				nouvelleOccurrenceLemme(lemmeEnReception, date, TypeEvenement.RECEPTION, IssueEvenement.SUCCES);

				if (!connaitLemme(lemmeEnReception)) {
					if (peutMemoriser(lemmeEnReception)) {
						
						nouvelleOccurrenceLemme(lemmeEnReception, date, TypeEvenement.MEMORISATION, IssueEvenement.SUCCES);
						
						if (!aLexiquePlein()) {
							memoriserLemme(lemmeEnReception);
						}
						else {					
							Lemme lemmeEnRemplacement = lireStrategieElimination().determinerLemme(lemmeEnReception);
							
							nouvelleOccurrenceLemme(lemmeEnReception, date,  TypeEvenement.ELIMINATION, IssueEvenement.SUCCES);
							
							remplacerLemme(lemmeEnRemplacement, lemmeEnReception);
						}
						
						if (aLexiquePlein()) {
							if (Systeme.lireConditionArret() == CategorieConditionArret.LEXIQUE_PLEIN) {
								Systeme.ajouterPremierEvenementEnDate(Systeme.genererEvenementFinal(this), date);
							}
						}
					}
					else {
						nouvelleOccurrenceLemme(lemmeEnReception, date, TypeEvenement.MEMORISATION, IssueEvenement.ECHEC);
					}
				}
			}
			else {
				nouvelleOccurrenceLemme(lemmeEnReception, date, TypeEvenement.RECEPTION, IssueEvenement.ECHEC);
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
	
	// Classes d'Strategies
	
	private class StrategieSelectionPremier extends StrategieSelectionLemmeIndependante {

		@Override
		public Lemme determinerLemme() {
			return lexique.get(0);
		}
	}
	
	private class StrategieSelectionUniforme extends StrategieSelectionLemmeIndependante {
		private final Random random = new Random();

		@Override
		public Lemme determinerLemme() {
			return lexique.get(random.nextInt(lexique.size()));
		}
	}
	
	private class StrategieSelectionMoinsEmis extends StrategieSelectionLemmeIndependante {
		
		@Override
		public Lemme determinerLemme() {
			int nombreMinEmissions = Integer.MAX_VALUE;
			int nombreEmissionsLemmeCourant = Integer.MAX_VALUE;
			Lemme lemmeMinEmissions = null;

			for (Lemme _lemmeCourant : tableOccurrencesLemmes.keySet()) {
				nombreEmissionsLemmeCourant = nombreOccurrencesLemmesSelonCriteres(_lemmeCourant, TypeEvenement.EMISSION, IssueEvenement.SUCCES);
				
				if (connaitLemme(_lemmeCourant) && nombreEmissionsLemmeCourant < nombreMinEmissions) {
					lemmeMinEmissions = _lemmeCourant;
				}
			}
			
			return lemmeMinEmissions;
		}
	}
	
	private class StrategieSuccessionVoisinAleatoire extends StrategieSuccession {
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
		
		string += lexique;

		return string;
	}
}
