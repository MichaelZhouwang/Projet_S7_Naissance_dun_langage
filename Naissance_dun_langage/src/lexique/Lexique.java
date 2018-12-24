package lexique;

import java.util.ArrayList;

import systeme.Individu;

public class Lexique extends ArrayList<Lemme> {
	private static final long serialVersionUID = 1L;
	
	private int tailleMaximale;
	private int tailleInitiale;
	
	public Lexique() {
		tailleMaximale = 0;
		tailleInitiale = 0;
	}
	
	public Lexique(Lexique lexique) {
		tailleMaximale = lexique.lireTailleMaximale();
		tailleInitiale = lexique.lireTailleInitiale();
		
		for (Lemme lemme : lexique) {
			add(lemme);
		}
	}
	
	public void generer(int tailleMaximale, int tailleInitiale, Individu initiateur) {
		this.tailleMaximale = tailleMaximale;
		this.tailleInitiale = Math.min(tailleMaximale, tailleInitiale);
		
		for (int i = 1; i <= tailleInitiale; i++) {
			add(new Lemme(initiateur, Character.toString(initiateur.lireLettre()) + i));
		}
	}
	
	public int lireTailleMaximale() {
		return tailleMaximale;
	}

	public int lireTailleInitiale() {
		return tailleInitiale;
	}
	
	public boolean estPlein() {
		return size() == tailleMaximale;
	}
}