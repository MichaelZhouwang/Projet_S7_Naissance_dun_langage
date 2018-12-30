package systeme;

import java.util.ArrayList;
import java.util.HashMap;

import evenement.modele.Evenement;
import lecture.ConfigurationSysteme;
import lexique.Lemme;
import lexique.OccurrenceLemme;
import systeme.enumeration.TypeCritereArret;
import systeme.executeur.ExecuteurEvenementsSysteme;
import temps.Date;
import temps.Horloge;

public class Systeme {
	private static ConfigurationSysteme configurationSysteme = null;
	
	private static Echeancier echeancier = new Echeancier();
	private static Horloge horloge = new Horloge();

	private static HashMap<Integer, Lemme> cacheLemmes = new HashMap<Integer, Lemme>();
	private static HashMap<Integer, OccurrenceLemme> cacheOccurrencesLemmes = new HashMap<Integer, OccurrenceLemme>();
	
	private static ExecuteurEvenementsSysteme executeurEvenements = new ExecuteurEvenementsSysteme();
	
	private static boolean estArrete = false;
	
	public static ConfigurationSysteme obtenirConfigurationSysteme() {
		return configurationSysteme;
	}
	
	public static int lireNombreIndividus() {
		return configurationSysteme.getNombreIndividus();
	}
	
	public static TypeCritereArret lireTypeCritereArret() {
		return configurationSysteme.getCritereArret().lireTypeCritere();
	}
	
	public static int lireObjectifCritereArret() {
		return configurationSysteme.getCritereArret().lireObjectif();
	}
	
	public static ArrayList<Individu> obtenirIndividus() {
		return configurationSysteme.getIndividus();
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
	
	public static void declencherProchainEvenement() throws Exception {
		if (!estArrete) {
			horloge.mettreAJourDate(echeancier.dateProchainEvenement());
			echeancier.declencherProchainEvenement();
		}
	}

	public static void declencherEvenementFinal(Evenement evenementInitiateur) throws Exception {
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

	public static void configurer(ConfigurationSysteme configurationSysteme) {
		Systeme.configurationSysteme = configurationSysteme;
	}
	
	public static void lancer() throws Exception {
		if (configurationSysteme == null)
			throw new Exception("Impossible de lancer le systeme, ce dernier n'a pas ete correctement configure");
		
		executeurEvenements.genererEvenementInitial().declencher();
	}
}
