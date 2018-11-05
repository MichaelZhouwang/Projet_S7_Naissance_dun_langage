package architecture;

import java.util.ArrayList;

public class Graphe {

	private ArrayList<Individu> individus;
	private String nomGraphe;

	public Graphe(String nomGraphe, double[][] matSomSom, int[][] matLex) throws Exception {
		
		this.nomGraphe = nomGraphe;
		individus = new ArrayList<Individu>();

		for (int numIndividu = 0; numIndividu < matSomSom.length; numIndividu++) {
			individus.add(new Individu());
		}

		for (int numIndividu = 0; numIndividu < matSomSom.length; numIndividu++) {

			individus.get(numIndividu).genererLexique(matLex[numIndividu][0], matLex[numIndividu][1]);

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
	
	public String lireNom() {
		return nomGraphe;
	}

}
