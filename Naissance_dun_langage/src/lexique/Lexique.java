package lexique;

import java.util.ArrayList;

import systeme.Individu;

public class Lexique extends ArrayList<Lemme> {
	private static final long serialVersionUID = 1L;
	
	private int tailleInitiale;
	private int tailleMaximale;
	
	public Lexique() {
		tailleInitiale = 0;
		tailleMaximale = 0;
	}
	
	public Lexique(Lexique lexique) {
		tailleInitiale = lexique.lireTailleInitiale();
		tailleMaximale = lexique.lireTailleMaximale();
		
		for (Lemme lemme : lexique) {
			add(lemme);
		}
	}
	
	public void generer(int tailleInitiale, int tailleMaximale, Individu initiateur) {
		this.tailleInitiale = tailleInitiale;
		this.tailleMaximale = tailleMaximale;
		
		for (int indice = 1; indice <= tailleInitiale; indice++) {
			add(new Lemme(initiateur, Character.toString(initiateur.lireLettre()) + indice));
		}
	}
	
	public int lireTailleInitiale() {
		return tailleInitiale;
	}
	
	public int lireTailleMaximale() {
		return tailleMaximale;
	}
	
	public boolean estPlein() {
		return size() >= tailleMaximale;
	}
}