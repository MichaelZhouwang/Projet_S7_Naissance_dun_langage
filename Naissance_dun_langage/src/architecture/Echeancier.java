package architecture;

import java.util.Stack;

import evenements.Evenement;

public class Echeancier {
	private Stack<Evenement> evenementsAVenir;
	private Stack<Evenement> evenementsPasses;
	
	public Echeancier() {
		evenementsAVenir = new Stack<Evenement>();
		evenementsPasses = new Stack<Evenement>();
	}
	
	public void ajouterEvenement(Evenement evenement) {
		evenementsAVenir.push(evenement);
	}
	
	public void declencherDernierEvenement() {
		if (!evenementsAVenir.empty()) {
			Evenement evenement = evenementsAVenir.pop();
			evenement.declencher();
			evenementsPasses.push(evenement);
		}
	}
	
	public boolean estVide() {
		return evenementsAVenir.empty();
	}
	
	public Stack<Evenement> obtenirHistorique() {
		return evenementsPasses;
	}
}
