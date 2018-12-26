package systeme;

import java.util.ArrayList;
import java.util.HashMap;

import temps.Delais;

public class SystemeIntermediaire {

	private ArrayList<Individu> individus;
	private int tailleInitialeLexique;
	private int tailleMaximaleLexique;
	private int[][] matSomSom;

	public SystemeIntermediaire(int[][] matSomSom, int tailleInitialeLexique, int tailleMaximaleLexique)
			throws Exception {
		this.tailleInitialeLexique = tailleInitialeLexique;
		this.tailleMaximaleLexique = tailleMaximaleLexique;
		this.matSomSom = matSomSom;
		individus = new ArrayList<Individu>();

	}
	
	private Individu copierIndividuSpecifique(Individu individu, HashMap<Integer, Individu> lIndividusSpecifiques) {
		for (int idSpec : lIndividusSpecifiques.keySet()) {
			Individu individuSpec = lIndividusSpecifiques.get(idSpec);

			if (individu.lireID() == idSpec) {
				System.out.println("Testing");

				individu.setImplementationConditionEmission(individuSpec.getImplementationConditionEmission());
				individu.setImplementationConditionReception(individuSpec.getImplementationConditionReception());
				individu.setImplementationConditionMemorisation(
						individuSpec.getImplementationConditionMemorisation());

				individu.setImplementationStrategieSelectionEmission(
						individuSpec.getImplementationStrategieSelectionEmission());

				individu.setImplementationStrategieSelectionElimination(
						individuSpec.getImplementationStrategieSelectionElimination());

				individu.setImplementationStrategieSuccession(individuSpec.getImplementationStrategieSuccession());

				individu.obtenirLexique().generer(individuSpec.getLexique().lireTailleMaximale(), individuSpec.getLexique().lireTailleInitiale(), individu);

			}
		}
		
		return individu;
	}

	public ArrayList<Individu> genererIndividus(HashMap<Integer, Individu> lIndividusSpecifiques) {

		for (int numIndividu = 0; numIndividu < matSomSom.length; numIndividu++) {
			Individu individu = new Individu();
			individu.setID(numIndividu + 1);
			individu.obtenirLexique().generer(tailleMaximaleLexique, tailleInitialeLexique, individu);

			individu = copierIndividuSpecifique(individu, lIndividusSpecifiques);

			System.out.println(individu.obtenirLexique());
			individus.add(individu);
		}

		for (int numIndividu = 0; numIndividu < matSomSom.length; numIndividu++) {

			for (int numVoisin = 0; numVoisin < matSomSom.length; numVoisin++) {

				if (matSomSom[numIndividu][numVoisin] != 0) {
					if (matSomSom[numIndividu][numVoisin] == 1) {
						individus.get(numIndividu)
								.ajouterVoisin(new Voisin(individus.get(numVoisin), Delais.delaisReceptionParDefaut));
					} else {
						individus.get(numIndividu).ajouterVoisin(
								new Voisin(individus.get(numVoisin), new Delais(matSomSom[numIndividu][numVoisin])));
					}
				}

			}
		}

		return individus;
	}

	public int getNombreIndividus() {
		return matSomSom.length;
	}
}
