package systeme;

import java.util.ArrayList;
import java.util.HashMap;

import condition.enums.ImplementationCondition;
import condition.enums.UtilisationCondition;
import strategie.enums.ImplementationStrategie;
import strategie.enums.UtilisationStrategie;
import temps.Delais;

public class SystemeIntermediaire {
	
	private ArrayList<Individu> individus;
	private String nomSysteme;
	private int[][] matSomSom;

	public SystemeIntermediaire(String nomSysteme, int[][] matSomSom, int tailleInitialeLexique, int tailleMaximaleLexique) throws Exception {
		
		this.nomSysteme = nomSysteme;
		this.matSomSom = matSomSom;		
		individus = new ArrayList<Individu>();

		for (int numIndividu = 0; numIndividu < matSomSom.length; numIndividu++) {
			individus.add(new Individu(tailleInitialeLexique, tailleMaximaleLexique));
		}
		
	}
	
	public ArrayList<Individu> genererIndividus(HashMap<UtilisationCondition, ImplementationCondition> parametresConditions, HashMap<UtilisationStrategie, ImplementationStrategie> parametresStrategies) {
		
		for (int numIndividu = 0; numIndividu < matSomSom.length; numIndividu++) {

			for (int numVoisin = 0; numVoisin < matSomSom.length; numVoisin++) {
				if (matSomSom[numIndividu][numVoisin] != 0) {
					individus.get(numIndividu).ajouterVoisin(individus.get(numVoisin));
					
					if (matSomSom[numIndividu][numVoisin] == 1) {
						individus.get(numIndividu).ajouterDelaisVoisin(individus.get(numVoisin), Delais.delaisReceptionParDefaut);
					} else {
						individus.get(numIndividu).ajouterDelaisVoisin(individus.get(numVoisin), new Delais(matSomSom[numIndividu][numVoisin]));
					}
				}
			}

			for (UtilisationCondition typeCondition : parametresConditions.keySet()) {
				individus.get(numIndividu).definirCondition(typeCondition, parametresConditions.get(typeCondition));
			}
			for (UtilisationStrategie typeStrategie : parametresStrategies.keySet()) {
				individus.get(numIndividu).definirStrategie(typeStrategie, parametresStrategies.get(typeStrategie));
			}
		}
		
		return individus;
	}

	public String lireNom() {
		return nomSysteme;
	}
}
