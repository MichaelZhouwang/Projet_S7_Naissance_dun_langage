package architecture;

import java.util.ArrayList;

public class Graphe {

	private ArrayList<Individu> individus;

	public Graphe(double[][] matSomSom, int[][] matLex) throws Exception {
		individus = new ArrayList<Individu>();

		for (int numIndividu = 0; numIndividu < matSomSom.length; numIndividu++) {
			individus.add(new Individu());

		}

		for (int numIndividu = 0; numIndividu < matSomSom.length; numIndividu++) {

			// Attend a mettre-a-jour
			individus.get(numIndividu).genererLexique(matLex[numIndividu][0], matLex[numIndividu][1]);
			// individus.get(numIndividu).genererLexique(5, 10);

			for (int numVoisin = 0; numVoisin < matSomSom.length; numVoisin++) {
				if (matSomSom[numIndividu][numVoisin] != 0) {
					individus.get(numIndividu).ajouterVoisin(individus.get(numVoisin));
				}
			}
		}

	}

	public ArrayList<Individu> lireGraphe() {
		return individus;
	}

}
