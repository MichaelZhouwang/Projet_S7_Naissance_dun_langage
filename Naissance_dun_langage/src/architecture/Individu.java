package architecture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import algorithmes.Algorithme;
import conditions.Condition;
import outils.DefinitionException;

public class Individu {
	private static int effectif = 0;
	private int ID;
	private char lettre;
	
	private ArrayList<Lemme> lexique;
	private int tailleMaximaleLexique;
	
	private ArrayList<Individu> voisins;
	
	private Condition conditionEmission;
	private Condition conditionReception;
	private Condition conditionMemorisation;
	private Algorithme algorithmeSelection;
	private Algorithme algorithmeElimination;
	
	private HashMap<Lemme, OccurrencesLemme> occurrencesLemmes;
	
	public Individu() {
		ID = effectif++;
		voisins = new ArrayList<Individu>();
		lettre = (char)(65 + ID);
		
		conditionEmission = new ConditionProbabiliteUniforme();
		conditionReception = new ConditionProbabiliteUniforme();
		conditionMemorisation = new ConditionProbabiliteUniforme();
		algorithmeSelection = new AlgorithmeSelectionUniforme();
		algorithmeElimination = new AlgorithmeSelectionUniforme();
		
		occurrencesLemmes = new HashMap<Lemme, OccurrencesLemme>();
	}

	// Lettre
	
	public char lireLettre() {
		return lettre;
	}
	
	// Lexique
	
	public void genererLexique(int tailleInitialeLexique, int tailleMaximaleLexique) throws DefinitionException {
		if (tailleInitialeLexique > tailleMaximaleLexique) {
			throw new DefinitionException();
		}
		
		this.tailleMaximaleLexique = tailleMaximaleLexique;
		
		lexique = new ArrayList<Lemme>();
		for (int i = 1; i <= tailleInitialeLexique; i++) {
			lexique.add(new Lemme(this, Character.toString(lettre) + i));
		}
	}
	
	public ArrayList<Lemme> lireLexique() {
		return lexique;
	}
	
	public int lireTailleLexique() {
		return lexique.size();
	}
	
	public int lireTailleMaximaleLexique() {
		return tailleMaximaleLexique;
	}
	
	// Voisins
	
	public void ajouterVoisin(Individu voisin) {
		voisins.add(voisin);
	}
	
	public ArrayList<Individu> lireVoisins() {
		return voisins;
	}

	// Conditions

	public boolean peutEmettre() {
		return conditionEmission.estSatisfaite();
	}
	
	public boolean peutRecevoir() {
		return conditionReception.estSatisfaite();
	}

	public boolean peutMemoriser(Lemme lemme) {
		return conditionMemorisation.estSatisfaite();
	}
	
	// Algorithmes
	
	public Algorithme lireAlgorithmeSelection() {
		return algorithmeSelection;
	}
	
	public Algorithme lireAlgorithmeElimination() {
		return algorithmeElimination;
	}
	
	// Occurrences lemmes
	
	public void ajouterOccurrenceLemmeEmis(int dateOccurrence, Lemme lemme) {
		if (occurrencesLemmes.get(lemme) == null) {
			occurrencesLemmes.put(lemme, new OccurrencesLemme(lemme));
		}
		occurrencesLemmes.get(lemme).nouvelleEmission(dateOccurrence);
	}
	
	public void ajouterOccurrenceLemmeRecu(int dateOccurrence, Lemme lemme) {
		if (occurrencesLemmes.get(lemme) == null) {
			occurrencesLemmes.put(lemme, new OccurrencesLemme(lemme));
		}
		occurrencesLemmes.get(lemme).nouvelleReception(dateOccurrence);
	}
	
	public void ajouterOccurrenceLemmeMemorise(int dateOccurrence, Lemme lemme) {
		if (occurrencesLemmes.get(lemme) == null) {
			occurrencesLemmes.put(lemme, new OccurrencesLemme(lemme));
		}
		occurrencesLemmes.get(lemme).nouvelleMemorisation(dateOccurrence);
	}
	
	// Algorithmes
	
	private class AlgorithmeSelectionUniforme extends Algorithme {
		private final Random random = new Random();
		
		public Lemme determinerLemme() {
			return lexique.get(random.nextInt(lexique.size()));
		}
	}
	
	// Conditions
	
	private class ConditionProbabiliteUniforme extends Condition {
		private final Random random = new Random();
		
		public boolean estSatisfaite() {
			return random.nextFloat() > 0.5;
		}
	}
	
	@Override
	public String toString() {
		String string = "Individu " + lettre + " : ";
		
		string += lexique + "\n\n";

		for (Lemme lemme : occurrencesLemmes.keySet()) {
			string += occurrencesLemmes.get(lemme).toString();
		}

		return string;
	}
}
