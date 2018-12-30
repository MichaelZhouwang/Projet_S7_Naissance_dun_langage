package systeme;

import java.text.Normalizer;
import java.util.ArrayList;

import condition.enumeration.ImplementationCondition;
import evenement.enumeration.IssueEvenement;
import evenement.enumeration.TypeEvenement;
import lexique.Lemme;
import lexique.Lexique;
import lexique.OccurrenceLemme;
import lexique.TableOccurrencesLemmes;
import strategie.enumeration.ImplementationStrategieSelection;
import strategie.enumeration.ImplementationStrategieSuccession;
import systeme.executeur.ExecuteurEvenementsIndividu;
import temps.Date;

public class Individu {
	private static int compteur = 0;
	private int ID;
	private char lettre;
	
	private Lexique lexique;
	private TableOccurrencesLemmes tableOccurrencesLemmes;
	private ArrayList<Voisin> voisins;
	
	private ExecuteurEvenementsIndividu evenements;
	
	private ImplementationCondition implConditionEmission;
	private ImplementationCondition implConditionReception;
	private ImplementationCondition implConditionMemorisation;
	
	private ImplementationStrategieSelection implStrategieSelectionEmission;
	private ImplementationStrategieSelection implStrategieSelectionElimination;
	private ImplementationStrategieSuccession implStrategieSuccession;
	
	public Individu() {
		ID = ++compteur;
		lettre = (char)(64 + ID);

		lexique = new Lexique();
		tableOccurrencesLemmes = new TableOccurrencesLemmes();
		voisins = new ArrayList<Voisin>();

		evenements = new ExecuteurEvenementsIndividu(this);
	}
	
	// toString
	
	@Override
	public String toString() {
		return "Individu " + lettre;
	}
	
	public String lireNomClasse() {
		return Normalizer.normalize(toString().replace(' ', '-').toLowerCase(), Normalizer.Form.NFD);
	}
	
	// ID
	
	public int lireID() {
		return ID;
	}

	// Lettre
	
	public char lireLettre() {
		return lettre;
	}
	
	// Lexique
	
	public void genererLexique(int tailleInitiale, int tailleMaximale) {
		lexique.generer(tailleInitiale, tailleMaximale, this);
	}
	
	public TableOccurrencesLemmes obtenirTableOccurrencesLemmes() {
		return tableOccurrencesLemmes;
	}
	
	public OccurrenceLemme nouvelleOccurrenceLemme(OccurrenceLemme occurrenceInitiatrice, Lemme lemme, TypeEvenement typeEvenement, IssueEvenement issueEvenement, Date date) {
		return tableOccurrencesLemmes.nouvelleOccurenceLemme(occurrenceInitiatrice, this, lemme, typeEvenement, issueEvenement, date);
	}
	
	public Lexique obtenirLexique() {
		return lexique;
	}
	
	public Lexique retrouverLexique(Date date) {
		Lexique lexiqueRetrouve = new Lexique();
		lexiqueRetrouve.generer(obtenirLexique().lireTailleInitiale(), obtenirLexique().lireTailleMaximale(), this);
		
		ArrayList<OccurrenceLemme> occurrencesLemmes = tableOccurrencesLemmes.obtenirListeOccurrencesLemmesOrdonnee(
			Lemme.QUELCONQUE, TypeEvenement.QUELCONQUE, IssueEvenement.SUCCES, Date.valeurInitiale, date
		);

		for (OccurrenceLemme occurrenceLemme : occurrencesLemmes) {
			if (occurrenceLemme.getTypeEvenement() == TypeEvenement.MEMORISATION) {
				lexiqueRetrouve.add(occurrenceLemme.getLemme());
			}
			else if (occurrenceLemme.getTypeEvenement() == TypeEvenement.ELIMINATION) {
				lexiqueRetrouve.remove(occurrenceLemme.getLemme());
			}
		}

		return lexiqueRetrouve;
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
	
	public ExecuteurEvenementsIndividu obtenirEvenements() {
		return evenements;
	}
	
	// Conditions
	
	public ImplementationCondition lireImplConditionEmission() {
		return implConditionEmission;
	}
	
	public void definirImplConditionEmission(ImplementationCondition impl) {
		implConditionEmission = impl;
	}
	
	public ImplementationCondition lireImplConditionReception() {
		return implConditionReception;
	}
	
	public void definirImplConditionReception(ImplementationCondition impl) {
		implConditionReception = impl;
	}
	
	public ImplementationCondition lireImplConditionMemorisation() {
		return implConditionMemorisation;
	}
	
	public void definirImplConditionMemorisation(ImplementationCondition impl) {
		implConditionMemorisation = impl;
	}
	
	// Stratégies
	
	public ImplementationStrategieSelection lireImplStrategieSelectionEmission() {
		return implStrategieSelectionEmission;
	}
	
	public void definirImplStrategieSelectionEmission(ImplementationStrategieSelection impl) {
		implStrategieSelectionEmission = impl;
	}
	
	public ImplementationStrategieSelection lireImplStrategieSelectionElimination() {
		return implStrategieSelectionElimination;
	}
	
	public void definirImplStrategieSelectionElimination(ImplementationStrategieSelection impl) {
		implStrategieSelectionElimination = impl;
	}
	
	public void definirImplStrategieSuccession(ImplementationStrategieSuccession impl) {
		implStrategieSuccession = impl;
	}
	
	public ImplementationStrategieSuccession lireImplStrategieSuccession() {
		return implStrategieSuccession;
	}
	
	// Voisins
	
	public void ajouterVoisin(Voisin voisin) {
		voisins.add(voisin);
	}
	
	public ArrayList<Voisin> obtenirVoisins() {
		return voisins;
	}
}
