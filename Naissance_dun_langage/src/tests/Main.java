package tests;

import architecture.Graphe;
import lecture.LireFichier;

import architecture.Systeme;

/**
 * @author Charles
 * @author Yongda
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("Debut simulation");

		LireFichier testLire = new LireFichier("./config/My_config.xml");

		try {
			
			Graphe grapheFichier;
			Systeme systeme = new Systeme();
			// systeme.generer();

			grapheFichier = testLire.LireGraphe();

			systeme.generer2(grapheFichier);
			systeme.routine();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Fin simulation");
	}

}
