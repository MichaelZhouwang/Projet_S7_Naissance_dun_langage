package tests;

import architecture.Graphe;
import lecture.LireFichier;
import record.GrapheImage;
import record.Histogram;
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
			grapheFichier = testLire.LireGraphe();
			
			GrapheImage grapheImage = new GrapheImage(grapheFichier);
			grapheImage.imprimerGraphe();

			Systeme systeme = new Systeme();
			systeme.generer2(grapheFichier);
			systeme.routine();

			double[] result = { 0.5, 0.8, 0.2 };
			Histogram histogram = new Histogram(result);
			histogram.draw();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Fin simulation");
	}

}
