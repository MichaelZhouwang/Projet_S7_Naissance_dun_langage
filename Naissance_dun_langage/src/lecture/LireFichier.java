package lecture;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import architecture.Graphe;
import architecture.Individu;

public class LireFichier {

	private Properties systemeConfig;

	public LireFichier(String sXmlName) {

		System.out.println("Lecture XML");

		// lire properties
		systemeConfig = new Properties();

		try {
			// "./config/My_config.xml"
			systemeConfig.loadFromXML(new FileInputStream(sXmlName));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Individu LireIndividu() {

		System.out.println("Lecture Individu");

		// charger dynamiquement la classe Individu
		Individu individu = null;

		try {
			Class<?> c = Class.forName(systemeConfig.getProperty("individu"));
			individu = (Individu) c.newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return individu;
	}

	@SuppressWarnings("resource")
	public Graphe LireGraphe() throws Exception {

		System.out.println("Lecture Graphe");

		String numFichier = systemeConfig.getProperty("graphe");
		boolean lireTete = true;
		boolean lireCoordonnees = false;
		boolean lireLexique = false;
		int curSommet = 0, nbrSommet = 0;
		double[][] matSomSom = null;
		int[][] matLex = null;

		String nomTete;
		String valeurTete;
		String[] articles;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(numFichier));
			String line = reader.readLine().trim();
			while (!line.isEmpty() && !line.equals("EOF")) {
				if (lireTete) {
					articles = line.split(":");
					nomTete = articles[0].trim().toUpperCase();
					valeurTete = articles.length > 1 ? articles[1].trim() : "";
					if (nomTete.equals("NOMBRESOMMET")) {
						nbrSommet = Integer.parseInt(valeurTete);
						matSomSom = new double[nbrSommet][nbrSommet];
						matLex = new int[nbrSommet][2];
					}
					if (nomTete.equals("SOMMET_SOMMET_SECTION")) {
						lireTete = false;
						lireCoordonnees = true;
					}
				} else if (lireCoordonnees && curSommet < nbrSommet && !lireLexique) {
					if (matSomSom == null) {
						throw new IllegalArgumentException("Vous devez ecrire le nombre de sommets");
					}
					articles = line.split("(\\s)+");
					if (articles.length != nbrSommet) {
						throw new IllegalArgumentException("Le nombre de sommets sur la matrice n'est pas correct");
					}
					for (int i = 0; i < nbrSommet; i++) {
						matSomSom[curSommet][i] = Double.parseDouble(articles[i]);
					}
					curSommet++;
				} else if (curSommet == nbrSommet) {
					articles = line.split(":");
					nomTete = articles[0].trim().toUpperCase();
					valeurTete = articles.length > 1 ? articles[1].trim() : "";
					if (nomTete.equals("LEXIQUE_SECTION")) {
						lireCoordonnees = false;
						lireLexique = true;
					}
					curSommet = 0;
				} else if (lireLexique && curSommet < nbrSommet) {
					if (matLex == null) {
						throw new IllegalArgumentException("Vous devez ecrire le nombre de sommets");
					}
					articles = line.split("(\\s)+");
					if (articles.length != 2) {
						throw new IllegalArgumentException(
								"Le nombre de lexique infos sur la matrice n'est pas correct");
					}
					for (int i = 0; i < 2; i++) {
						matLex[curSommet][i] = Integer.parseInt(articles[i]);
					}
					curSommet++;
				}
				line = reader.readLine().trim();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// charger la classe Graphe
		Graphe graphe = new Graphe(matSomSom, matLex);

		return graphe;

	}

}
