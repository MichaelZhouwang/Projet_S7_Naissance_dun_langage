package lecture;

import java.io.*;
import java.util.*;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import condition.enumeration.ImplementationCondition;
import strategie.enumeration.ImplementationStrategieSelection;
import strategie.enumeration.ImplementationStrategieSuccession;
import systeme.*;
import systeme.enumeration.TypeCritereArret;

public class LireFichier {

	private Element systemeConfig;
	private List<Element> individusConfig = new ArrayList<Element>();

	private int tailleInitialeLexique, tailleMaximaleLexique;

	public LireFichier(String sXmlName) {

		System.out.println("Lecture XML");

		try {
			// lire properties
			// "./config/My_config.xml"

			File inputFile = new File(sXmlName);
			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(inputFile);
			Element classElement = document.getRootElement();

			List<Element> configList = classElement.getChildren();

			for (int temp = 0; temp < configList.size(); temp++) {
				Element property = configList.get(temp);
				System.out.println("\nCurrent Element :" + property.getName());
				if (property.getName().equals("individu")) {
					individusConfig.add(property);
					Attribute attribute = property.getAttribute("id");
					System.out.println("Individu id : " + attribute.getValue());
				} else if (property.getName().equals("systeme")) {
					systemeConfig = property;
				}
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	public SystemeIntermediaire lireSyeteme() throws Exception {

		System.out.println("Lecture Graphe");

		String numFichier = systemeConfig.getChild("Graphe").getValue();
		tailleInitialeLexique = Integer.parseInt(systemeConfig.getChild("TailleInitialeLexique").getValue());
		tailleMaximaleLexique = Integer.parseInt(systemeConfig.getChild("TailleMaximaleLexique").getValue());

		boolean lireTete = true;
		boolean lireCoordonnees = false;
		int curSommet = 0, nbrSommet = 0;
		int[][] matSomSom = null;

		String nomTete;
		String valeurTete;
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
						System.out.println(valeurTete);
					}
					if (nomTete.equals("NOMBRESOMMET")) {
						nbrSommet = Integer.parseInt(valeurTete);
						matSomSom = new int[nbrSommet][nbrSommet];
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
						matSomSom[curSommet][i] = Integer.parseInt(articles[i]);
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
		SystemeIntermediaire systemeIntermediaire = new SystemeIntermediaire(matSomSom, tailleInitialeLexique,
				tailleMaximaleLexique);

		return systemeIntermediaire;

	}

	public HashMap<String, ImplementationCondition> lireConditions() {

		HashMap<String, ImplementationCondition> paramCondition = new HashMap<String, ImplementationCondition>();

		String paramEMISSION = systemeConfig.getChild("EMISSION").getValue();
		String paramRECEPTION = systemeConfig.getChild("RECEPTION").getValue();
		String paramMEMORISATION = systemeConfig.getChild("MEMORISATION").getValue();

		paramCondition.put("EMISSION", ImplementationCondition.valueOf(paramEMISSION));
		paramCondition.put("RECEPTION", ImplementationCondition.valueOf(paramRECEPTION));
		paramCondition.put("MEMORISATION", ImplementationCondition.valueOf(paramMEMORISATION));

		return paramCondition;

	}

	public ImplementationStrategieSelection lireStrategiesEmissionSelection() {

		String paramSelectionEmission = systemeConfig.getChild("SelectionEmission").getValue();

		return ImplementationStrategieSelection.valueOf(paramSelectionEmission);

	}

	public ImplementationStrategieSelection lireStrategiesEliminationSelection() {

		String paramSelectionElimination = systemeConfig.getChild("SelectionElimination").getValue();

		return ImplementationStrategieSelection.valueOf(paramSelectionElimination);

	}

	public ImplementationStrategieSuccession lireStrategiesSuccession() {

		String paramStrategieSuccession = systemeConfig.getChild("StrategieSuccession").getValue();

		return ImplementationStrategieSuccession.valueOf(paramStrategieSuccession);

	}

	public CritereArret lireCritereArret() {

		String paramCritereArret = systemeConfig.getChild("CritereArret").getValue();
		int paramObjectif = Integer.parseInt(systemeConfig.getChild("Objectif").getValue());

		return new CritereArret(TypeCritereArret.valueOf(paramCritereArret), paramObjectif);

	}

	public HashMap<Integer, Individu> lireIndividusSpecifiques() {
		HashMap<Integer, Individu> individusSpec = new HashMap<Integer, Individu>();

		for (Element individuElement : individusConfig) {
			Individu individu = new Individu();
			Attribute attribute = individuElement.getAttribute("id");
			int id = Integer.parseInt(attribute.getValue());

			individu.setID(id);

			individu.setImplementationConditionEmission(
					ImplementationCondition.valueOf(individuElement.getChild("EMISSION").getValue()));
			individu.setImplementationConditionReception(
					ImplementationCondition.valueOf(individuElement.getChild("RECEPTION").getValue()));
			individu.setImplementationConditionMemorisation(
					ImplementationCondition.valueOf(individuElement.getChild("MEMORISATION").getValue()));

			individu.setImplementationStrategieSelectionEmission(
					ImplementationStrategieSelection.valueOf(individuElement.getChild("SelectionEmission").getValue()));

			individu.setImplementationStrategieSelectionElimination(ImplementationStrategieSelection
					.valueOf(individuElement.getChild("SelectionElimination").getValue()));

			individu.setImplementationStrategieSuccession(ImplementationStrategieSuccession
					.valueOf(individuElement.getChild("StrategieSuccession").getValue()));

			individu.obtenirLexique().generer(
					Integer.parseInt(individuElement.getChild("TailleMaximaleLexique").getValue()),
					Integer.parseInt(individuElement.getChild("TailleInitialeLexique").getValue()), individu);

			individusSpec.put(id, individu);
		}
		return individusSpec;
	}

}
