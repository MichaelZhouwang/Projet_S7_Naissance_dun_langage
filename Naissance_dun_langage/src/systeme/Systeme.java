package systeme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import condition.enumeration.ImplementationCondition;
import evenement.enumeration.IssueEvenement;
import evenement.enumeration.TypeEvenement;
import evenement.modele.Evenement;
import lecture.LireFichier;
import lexique.Lemme;
import lexique.OccurrenceLemme;
import lexique.ComparateurOccurrenceLemmeDate;
import strategie.enumeration.ImplementationStrategieSelection;
import strategie.enumeration.ImplementationStrategieSuccession;
import systeme.enumeration.TypeCritereArret;
import systeme.executeur.ExecuteurEvenementsSysteme;

import temps.Date;
import temps.Delais;
import temps.Horloge;

public class Systeme {

	private static ExecuteurEvenementsSysteme executeurEvenements = new ExecuteurEvenementsSysteme();

	private static int nombreIndividus;

	private static int tailleInitialeLexiqueParDefaut;
	private static int tailleMaximaleLexiqueParDefaut;

	private static ImplementationCondition implementationConditionEmissionParDefaut = null;
	private static ImplementationCondition implementationConditionReceptionParDefaut = null;
	private static ImplementationCondition implementationConditionMemorisationParDefaut = null;

	private static ImplementationStrategieSelection implementationStrategieSelectionEmissionParDefaut = null;
	private static ImplementationStrategieSelection implementationStrategieSelectionEliminationParDefaut = null;
	private static ImplementationStrategieSuccession implementationStrategieSuccessionParDefaut = null;

	private static CritereArret critereArret;

	private static ArrayList<Individu> individus = new ArrayList<Individu>();
	private static Echeancier echeancier = new Echeancier();
	private static Horloge horloge = new Horloge();
	private static HashMap<Integer, Lemme> cacheLemmes = new HashMap<Integer, Lemme>();
	private static HashMap<Integer, OccurrenceLemme> cacheOccurrencesLemmes = new HashMap<Integer, OccurrenceLemme>();

	private static boolean estArrete = false;

	public static int lireTailleInitialeLexiqueParDefaut() {
		return tailleInitialeLexiqueParDefaut;
	}

	public static int lireTailleMaximaleLexiqueParDefaut() {
		return tailleMaximaleLexiqueParDefaut;
	}

	public static int lireNombreIndividus() {
		return nombreIndividus;
	}

	public static ImplementationCondition lireImplementationConditionEmissionParDefaut() {
		return implementationConditionEmissionParDefaut;
	}

	public static ImplementationCondition lireImplementationConditionReceptionParDefaut() {
		return implementationConditionReceptionParDefaut;
	}

	public static ImplementationCondition lireImplementationConditionMemorisationParDefaut() {
		return implementationConditionMemorisationParDefaut;
	}

	public static ImplementationStrategieSelection lireImplementationStrategieSelectionEmissionParDefaut() {
		return implementationStrategieSelectionEmissionParDefaut;
	}

	public static ImplementationStrategieSelection lireImplementationStrategieSelectionEliminationParDefaut() {
		return implementationStrategieSelectionEliminationParDefaut;
	}

	public static ImplementationStrategieSuccession lireImplementationStrategieSuccessionParDefaut() {
		return implementationStrategieSuccessionParDefaut;
	}

	public static TypeCritereArret lireTypeCritereArret() {
		return critereArret.lireTypeCritere();
	}

	public static int lireObjectifCritereArret() {
		return critereArret.lireObjectif();
	}

	public static ArrayList<Individu> obtenirIndividus() {
		return individus;
	}

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
		if (!estArrete) {
			horloge.mettreAJourDate(echeancier.dateProchainEvenement());
			echeancier.declencherProchainEvenement();
		}
	}

	public static void declencherEvenementFinal(Evenement evenementInitiateur) {
		estArrete = true;
		executeurEvenements.genererEvenementFinal(evenementInitiateur).declencher();
	}

	public static Lemme consommerLemmeCache(int IDEvenement) {
		return cacheLemmes.remove(IDEvenement);
	}

	public static void ajouterLemmeCache(int IDEvenement, Lemme lemme) {
		cacheLemmes.put(IDEvenement, lemme);
	}

	public static OccurrenceLemme consommerOccurrenceCache(int IDEvenement) {
		return cacheOccurrencesLemmes.remove(IDEvenement);
	}

	public static void ajouterOccurrenceCache(int IDEvenement, OccurrenceLemme occurrence) {
		cacheOccurrencesLemmes.put(IDEvenement, occurrence);
	}

	// Generation d'evenement

	public static void afficherBilan() {
		ArrayList<OccurrenceLemme> listeOccurrences = new ArrayList<OccurrenceLemme>();

		for (Individu individu : individus) {
			listeOccurrences.addAll(individu.obtenirTableOccurrencesLemmes().obtenirListeOccurrencesLemmes(
					Lemme.QUELCONQUE, TypeEvenement.QUELCONQUE, IssueEvenement.QUELCONQUE));
		}

		Collections.sort(listeOccurrences, new ComparateurOccurrenceLemmeDate());

		for (OccurrenceLemme _occurrenceLemme : listeOccurrences) {
			System.out.println(_occurrenceLemme);
		}
	}

	public static void generer() {
		// Il faut recuperer les informations du systeme de la lecture du fichier XML
		nombreIndividus = 8;

		tailleInitialeLexiqueParDefaut = 5;
		tailleMaximaleLexiqueParDefaut = 10;

		implementationStrategieSelectionEmissionParDefaut = ImplementationStrategieSelection.SELECTION_ALEATOIRE;
		implementationStrategieSelectionEliminationParDefaut = ImplementationStrategieSelection.SELECTION_MOINS_EMIS;
		implementationStrategieSuccessionParDefaut = ImplementationStrategieSuccession.SUCCESSION_VOISIN_ALEATOIRE;

		implementationConditionEmissionParDefaut = ImplementationCondition.CONDITION_TOUJOURS_VERIFIEE;
		implementationConditionReceptionParDefaut = ImplementationCondition.CONDITION_TOUJOURS_VERIFIEE;
		implementationConditionMemorisationParDefaut = ImplementationCondition.CONDITION_PROBABILITE_UNIFORME;

		// On cree les individus
		for (int i = 0; i < nombreIndividus; i++) {
			Individu individu = new Individu();
			individu.obtenirLexique().generer(tailleMaximaleLexiqueParDefaut, tailleInitialeLexiqueParDefaut, individu);
			System.out.println(individu.obtenirLexique());
			individus.add(individu);
		}

		for (Individu individu : individus) {
			// Ici on gere les cas particuliers pour chaque individus et on ajoute les
			// voisins
			for (Individu voisin : individus) {
				if (!voisin.equals(individu)) {
					individu.ajouterVoisin(new Voisin(voisin, Delais.delaisReceptionParDefaut));
				}
			}
		}

		// individus.get(1).definirImplementationConditionEmission(ImplementationCondition.CONDITION_JAMAIS_VERIFIEE);

		critereArret = new CritereArret(TypeCritereArret.DATE, 20);
		// critereArret = new CritereArret(TypeCritereArret.LEXIQUE_PLEIN, 100);
	}

	public static void genererParXml(String sXml) throws Exception {

		LireFichier fichier = new LireFichier(sXml);

		SystemeIntermediaire systemeIntermediaire = fichier.lireSyeteme();
		nombreIndividus = systemeIntermediaire.getNombreIndividus();

		HashMap<String, ImplementationCondition> paramCondition = fichier.lireConditions();

		implementationStrategieSelectionEmissionParDefaut = fichier.lireStrategiesEmissionSelection();
		implementationStrategieSelectionEliminationParDefaut = fichier.lireStrategiesEliminationSelection();
		implementationStrategieSuccessionParDefaut = fichier.lireStrategiesSuccession();

		implementationConditionEmissionParDefaut = paramCondition.get("EMISSION");
		implementationConditionReceptionParDefaut = paramCondition.get("RECEPTION");
		implementationConditionMemorisationParDefaut = paramCondition.get("MEMORISATION");

		try {

			individus = systemeIntermediaire.genererIndividus(fichier.lireIndividusSpecifiques());

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Echec de lire le systeme de fichier");
		}

		critereArret = fichier.lireCritereArret();
	}

	public static void routine() throws Exception {
		// generer();
		// genererParXml("./config/Example_config.xml");

		executeurEvenements.genererEvenementInitial().declencher();

		afficherBilan();
	}
}
