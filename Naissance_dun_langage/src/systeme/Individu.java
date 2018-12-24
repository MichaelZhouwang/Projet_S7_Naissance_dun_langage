package systeme;

import java.util.ArrayList;

import condition.enumeration.ImplementationCondition;
import evenement.enumeration.IssueEvenement;
import evenement.enumeration.TypeEvenement;
import ihm.Constantes;
import lexique.Lemme;
import lexique.Lexique;
import lexique.OccurrenceLemme;
import lexique.TableOccurrencesLemmes;
import strategie.enumeration.ImplementationStrategieSelection;
import strategie.enumeration.ImplementationStrategieSuccession;
import systeme.executeur.ExecuteurEvenementsIndividu;
import temps.Date;
import temps.Delais;

public class Individu {
	private static int compteur = 0;
	private int ID;
	private char lettre;
	
	private Lexique lexique;
	private TableOccurrencesLemmes tableOccurrencesLemmes;
	private ArrayList<Voisin> voisins;
	
	private String couleur;
	
	private ExecuteurEvenementsIndividu evenements;
	
	private ImplementationCondition implementationConditionEmission;
	private ImplementationCondition implementationConditionReception;
	private ImplementationCondition implementationConditionMemorisation;
	
	private ImplementationStrategieSelection implementationStrategieSelectionEmission;
	private ImplementationStrategieSelection implementationStrategieSelectionElimination;
	private ImplementationStrategieSuccession implementationStrategieSuccession;
	
	public Individu() {
		ID = ++compteur;
		lettre = (char)(64 + ID);

		couleur = Constantes.couleurs[(ID - 1) % Constantes.couleurs.length];
		
		lexique = new Lexique();
		tableOccurrencesLemmes = new TableOccurrencesLemmes();
		voisins = new ArrayList<Voisin>();

		evenements = new ExecuteurEvenementsIndividu(this);
		
		implementationConditionEmission = Systeme.lireImplementationConditionEmissionParDefaut();
		implementationConditionReception = Systeme.lireImplementationConditionReceptionParDefaut();
		implementationConditionMemorisation = Systeme.lireImplementationConditionMemorisationParDefaut();
		
		implementationStrategieSelectionEmission = Systeme.lireImplementationStrategieSelectionEmissionParDefaut();
		implementationStrategieSelectionElimination = Systeme.lireImplementationStrategieSelectionEliminationParDefaut();
		implementationStrategieSuccession = Systeme.lireImplementationStrategieSuccessionParDefaut();
	}
	
	public String lireCouleur() {
		return couleur;
	}
	
	// toString
	
	@Override
	public String toString() {
		return "Individu " + lettre;
	}
	
	public int lireID() {
		return ID;
	}

	// Lettre
	
	public char lireLettre() {
		return lettre;
	}
	
	// Lexique
	
	public TableOccurrencesLemmes obtenirTableOccurrencesLemmes() {
		return tableOccurrencesLemmes;
	}
	
	public OccurrenceLemme nouvelleOccurrenceLemme(int ID, OccurrenceLemme occurrenceInitiatrice, Lemme lemme, TypeEvenement typeEvenement, IssueEvenement issueEvenement, Date date) {
		return tableOccurrencesLemmes.nouvelleOccurenceLemme(ID, occurrenceInitiatrice, this, lemme, typeEvenement, issueEvenement, date);
	}
	
	public Lexique obtenirLexique() {
		return lexique;
	}
	
	public Lexique retrouverLexique(Date date) {
		Lexique lexiqueRetrouve = new Lexique();
		lexiqueRetrouve.generer(obtenirLexique().lireTailleMaximale(), obtenirLexique().lireTailleInitiale(), this);
		
		ArrayList<OccurrenceLemme> occurrencesLemmes = tableOccurrencesLemmes.obtenirListeOccurrencesLemmesOrdonnee(
			Lemme.QUELCONQUE, TypeEvenement.QUELCONQUE, IssueEvenement.SUCCES, Date.valeurInitiale, date
		);

		for (OccurrenceLemme occurrenceLemme : occurrencesLemmes) {
			if (occurrenceLemme.lireTypeEvenement() == TypeEvenement.MEMORISATION) {
				lexiqueRetrouve.add(occurrenceLemme.lireLemme());
			}
			else if (occurrenceLemme.lireTypeEvenement() == TypeEvenement.ELIMINATION) {
				lexiqueRetrouve.remove(occurrenceLemme.lireLemme());
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
	
	public ImplementationCondition lireImplementationConditionEmission() {
		return implementationConditionEmission;
	}
	
	public void definirImplementationConditionEmission(ImplementationCondition implementation) {
		implementationConditionEmission = implementation;
	}
	
	public ImplementationCondition lireImplementationConditionReception() {
		return implementationConditionReception;
	}
	
	public void definirImplementationConditionReception(ImplementationCondition implementation) {
		implementationConditionReception = implementation;
	}
	
	public ImplementationCondition lireImplementationConditionMemorisation() {
		return implementationConditionMemorisation;
	}
	
	public void definirImplementationConditionMemorisation(ImplementationCondition implementation) {
		implementationConditionMemorisation = implementation;
	}
	
	// Stratégies
	
	public ImplementationStrategieSelection lireImplementationStrategieSelectionEmission() {
		return implementationStrategieSelectionEmission;
	}
	
	public void definirImplementationStrategieSelectionEmission(ImplementationStrategieSelection implementation) {
		implementationStrategieSelectionEmission = implementation;
	}
	
	public ImplementationStrategieSelection lireImplementationStrategieSelectionElimination() {
		return implementationStrategieSelectionElimination;
	}
	
	public void definirImplementationStrategieSelectionElimination(ImplementationStrategieSelection implementation) {
		implementationStrategieSelectionElimination = implementation;
	}
	
	public void definirImplementationStrategieSuccession(ImplementationStrategieSuccession implementation) {
		implementationStrategieSuccession = implementation;
	}
	
	public ImplementationStrategieSuccession lireImplementationStrategieSuccession() {
		return implementationStrategieSuccession;
	}
	
	// Voisins
	
	public void ajouterVoisin(Voisin voisin) {
		voisins.add(voisin);
	}
	
	public ArrayList<Voisin> obtenirVoisins() {
		return voisins;
	}

	public Delais lireDelaisVoisin(Voisin voisin) {
		return voisin.lireDelais();
	}
}
