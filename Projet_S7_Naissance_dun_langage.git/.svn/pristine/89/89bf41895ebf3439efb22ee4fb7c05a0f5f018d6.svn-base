package architecture;

import java.util.ArrayList;

public class Lexique extends ArrayList<Lemme> {
	private static final long serialVersionUID = 1L;
	
	private int tailleMaximale;
	private int tailleInitiale;
	
	public Lexique(int tailleMaximale, int tailleInitiale) {
		this.tailleMaximale = tailleMaximale;
		this.tailleInitiale = Math.min(tailleMaximale, tailleInitiale);
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
	
	public void generer(Individu initiateur) {
		for (int i = 1; i <= tailleInitiale; i++) {
			add(new Lemme(initiateur, Character.toString(initiateur.lireLettre()) + i));
		}
	}
}