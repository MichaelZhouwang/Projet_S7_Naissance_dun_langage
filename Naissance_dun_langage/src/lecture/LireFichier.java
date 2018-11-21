package lecture;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import condition.enums.CategorieConditionArret;
import condition.enums.ImplementationCondition;
import strategie.enums.ImplementationStrategie;
import systeme.SystemeIntermediaire;

public class LireFichier {

	private Properties systemeConfig;
	private int tailleInitialeLexique, tailleMaximaleLexique;

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
	
	public SystemeIntermediaire LireSyeteme() throws Exception {

		System.out.println("Lecture Graphe");

		String numFichier = systemeConfig.getProperty("Graphe");
		int tailleInitialeLexique, tailleMaximaleLexique;
		tailleInitialeLexique = Integer.parseInt(systemeConfig.getProperty("TailleInitialeLexique"));
		tailleMaximaleLexique = Integer.parseInt(systemeConfig.getProperty("TailleMaximaleLexique"));
		
		boolean lireTete = true;
		boolean lireCoordonnees = false;
		int curSommet = 0, nbrSommet = 0;
		double[][] matSomSom = null;

		String nomTete;
		String valeurTete;
		String nomSysteme = "";
		String[] articles;

		BufferedReader reader = new BufferedReader(new FileReader(numFichier));
		try {
			String line = reader.readLine().trim();
			while (!line.isEmpty() && !line.equals("EOF")) {
				if (lireTete) {
					articles = line.split(":");
					nomTete = articles[0].trim().toUpperCase();
					valeurTete = articles.length > 1 ? articles[1].trim() : "";
					if (nomTete.equals("NOM")) {
						nomSysteme = valeurTete;
					}
					if (nomTete.equals("NOMBRESOMMET")) {
						nbrSommet = Integer.parseInt(valeurTete);
						matSomSom = new double[nbrSommet][nbrSommet];
					}
					if (nomTete.equals("SOMMET_SOMMET_SECTION")) {
						lireTete = false;
						lireCoordonnees = true;
					}
				} else if (lireCoordonnees && curSommet < nbrSommet) {
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
				}
				line = reader.readLine().trim();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			reader.close();
		}

		// charger la classe systemeIntermediaire
		SystemeIntermediaire systemeIntermediaire = new SystemeIntermediaire(nomSysteme, matSomSom, tailleInitialeLexique, tailleMaximaleLexique);

		return systemeIntermediaire;

	}

	public HashMap<String, ImplementationCondition> LireUtilisationConditions() {
		
		HashMap<String, ImplementationCondition> paramUtilisation = new HashMap<String, ImplementationCondition> ();
		
		String paramEMISSION = systemeConfig.getProperty("EMISSION");
		String paramRECEPTION = systemeConfig.getProperty("RECEPTION");
		String paramMEMORISATION = systemeConfig.getProperty("MEMORISATION");

		paramUtilisation.put("EMISSION", ImplementationCondition.valueOf(paramEMISSION));
		paramUtilisation.put("RECEPTION", ImplementationCondition.valueOf(paramRECEPTION));
		paramUtilisation.put("MEMORISATION", ImplementationCondition.valueOf(paramMEMORISATION));
		
		return paramUtilisation;
		
	}

	public HashMap<String, ImplementationStrategie> LireStrategies() {
		
		HashMap<String, ImplementationStrategie> paramStrategies = new HashMap<String, ImplementationStrategie> ();
		
		String paramSELECTION_LEMME = systemeConfig.getProperty("SELECTION_LEMME");
		String paramELIMINATION_LEMME = systemeConfig.getProperty("ELIMINATION_LEMME");
		String paramSUCCESSION = systemeConfig.getProperty("SUCCESSION");

		paramStrategies.put("SELECTION_LEMME", ImplementationStrategie.valueOf(paramSELECTION_LEMME));
		paramStrategies.put("ELIMINATION_LEMME", ImplementationStrategie.valueOf(paramELIMINATION_LEMME));
		paramStrategies.put("SUCCESSION", ImplementationStrategie.valueOf(paramSUCCESSION));
		
		return paramStrategies;
		
	}

	public HashMap<String, CategorieConditionArret> LireArretConditions() {
		
		HashMap<String, CategorieConditionArret> paramArret = new HashMap<String, CategorieConditionArret> ();
		
		String paramArretCondition = systemeConfig.getProperty("ArretCondition");
		
		paramArret.put("ArretCondition", CategorieConditionArret.valueOf(paramArretCondition));


		return paramArret;
		
	}
	
}
