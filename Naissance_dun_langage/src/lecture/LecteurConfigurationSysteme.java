package lecture;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import condition.enumeration.ImplementationCondition;
import exception.ConfigurationException;
import strategie.enumeration.ImplementationStrategieSelection;
import strategie.enumeration.ImplementationStrategieSuccession;
import systeme.CritereArret;
import systeme.Individu;
import systeme.enumeration.TypeCritereArret;

public class LecteurConfigurationSysteme {
	private final String stringSysteme = "systeme";
	private final String stringIndividu = "individu";
	
	private final String stringNombreIndividus = "nombreIndividus";
	private final String stringFichierGraphe = "fichierGraphe";
	private final String stringTailleInitialeLexique = "tailleInitialeLexique";
	private final String stringTailleMaximaleLexique = "tailleMaximaleLexique";
	private final String stringImplConditionEmission = "implConditionEmission";
	private final String stringImplConditionReception = "implConditionReception";
	private final String stringImplConditionMemorisation = "implConditionMemorisation";
	private final String stringImplStrategieSelectionEmission = "implStrategieSelectionEmission";
	private final String stringImplStrategieSelectionElimination = "implStrategieSelectionElimination";
	private final String stringImplStrategieSuccession = "implStrategieSuccession";
	private final String stringCritereArret = "critereArret";
	private final String stringTypeCritereArret = "typeCritereArret";
	private final String stringObjectifCritereArret = "objectifCritereArret";
	private final String stringID = "id";
	
	private final String stringParDefaut = "ParDefaut";
	
	private ConfigurationSysteme configurationSysteme;
	
	private Element elementSysteme;

	public ConfigurationSysteme lireConfigSystemeDepuisFichier(String nomFichier) throws Exception {

		configurationSysteme = new ConfigurationSysteme();
		
		File fichierConfiguration = new File(nomFichier);
		SAXBuilder saxBuilder = new SAXBuilder();
		Document document = saxBuilder.build(fichierConfiguration);
		Element racineDocument = document.getRootElement();
		
		if ((elementSysteme = racineDocument.getChild(stringSysteme)) == null) {
			throw new ConfigurationException(messageErreurElemAbsent(stringSysteme));
		}
		else {
			configurationSysteme.setNombreIndividus(lireNombreIndividus());
			configurationSysteme.setTailleInitialeLexiqueParDefaut(lireTailleInitialeLexiqueParDefaut());
			configurationSysteme.setTailleMaximaleLexiqueParDefaut(lireTailleMaximaleLexiqueParDefaut());
			configurationSysteme.setImplConditionEmissionParDefaut(lireImplConditionEmissionParDefaut());
			configurationSysteme.setImplConditionReceptionParDefaut(lireImplConditionReceptionParDefaut());
			configurationSysteme.setImplConditionMemorisationParDefaut(lireImplConditionMemorisationParDefaut());
			configurationSysteme.setImplStrategieSelectionEmissionParDefaut(lireImplStrategieSelectionEmissionParDefaut());
			configurationSysteme.setImplStrategieSelectionEliminationParDefaut(lireImplStrategieSelectionEliminationParDefaut());
			configurationSysteme.setImplStrategieSuccessionParDefaut(lireImplStrategieSuccessionParDefaut());
			configurationSysteme.setImplConditionEmissionParDefaut(lireImplConditionEmissionParDefaut());
			configurationSysteme.setCritereArret(lireCritereArret());
		}
		
		List<Element> listeElementsIndividus = racineDocument.getChildren(stringIndividu);
		ArrayList<Individu> individus = new ArrayList<Individu>();
		ArrayList<Integer> listeIndividusIDs = new ArrayList<Integer>();
		
		boolean individuParticulier = false;
		for (int individuID = 1; individuID <= configurationSysteme.getNombreIndividus(); individuID++) {
			Individu individu = new Individu();
			individuParticulier = false;

			for (Element elementIndividu : listeElementsIndividus) {
				int elementIndividuID = lireID(elementIndividu);

				if (listeIndividusIDs.contains(elementIndividuID)) {
					throw new ConfigurationException("Attribut '" + stringID  + "' invalide : cet attribut doit etre unique et est deja existant");
				}
				
				if (elementIndividuID == individuID) {
					listeIndividusIDs.add(individuID);
					individuParticulier = true;
					individu.genererLexique(lireTailleInitialeLexique(elementIndividu), lireTailleMaximaleLexique(elementIndividu));
					individu.definirImplConditionEmission(lireImplConditionEmission(elementIndividu));
					individu.definirImplConditionReception(lireImplConditionReception(elementIndividu));
					individu.definirImplConditionMemorisation(lireImplConditionMemorisation(elementIndividu));
					individu.definirImplStrategieSelectionEmission(lireImplStrategieSelectionEmission(elementIndividu));
					individu.definirImplStrategieSelectionElimination(lireImplStrategieSelectionElimination(elementIndividu));
					individu.definirImplStrategieSuccession(lireImplStrategieSuccession(elementIndividu));
				}
			}
			listeIndividusIDs.clear();
			
			if (!individuParticulier) {
				individu.genererLexique(configurationSysteme.getTailleInitialeLexiqueParDefaut(), configurationSysteme.getTailleMaximaleLexiqueParDefaut());
				individu.definirImplConditionEmission(configurationSysteme.getImplConditionEmissionParDefaut());
				individu.definirImplConditionReception(configurationSysteme.getImplConditionReceptionParDefaut());
				individu.definirImplConditionMemorisation(configurationSysteme.getImplConditionMemorisationParDefaut());
				individu.definirImplStrategieSelectionEmission(configurationSysteme.getImplStrategieSelectionEmissionParDefaut());
				individu.definirImplStrategieSelectionElimination(configurationSysteme.getImplStrategieSelectionEliminationParDefaut());
				individu.definirImplStrategieSuccession(configurationSysteme.getImplStrategieSuccessionParDefaut());
			}
			
			individus.add(individu);
		}
		
		configurationSysteme.setIndividus(individus);
		configurationSysteme.genererVoisinageDepuisMatrice(lireMatriceVoisinage());
		
		return configurationSysteme;
	}

	public int[][] lireMatriceVoisinage() throws Exception {
		int nombreIndividus = configurationSysteme.getNombreIndividus();
		int[][] matriceVoisinage = new int[nombreIndividus][nombreIndividus];

		FileReader lecteurFichier = new FileReader(lireFichierGraphe());
		BufferedReader lecteurATampon = new BufferedReader(lecteurFichier);
		
		try {
			String ligne = lecteurATampon.readLine();
			while (ligne.startsWith("#")) {
				ligne = lecteurATampon.readLine();
			}
			
			for (int indiceIndividu = 0; indiceIndividu < nombreIndividus; indiceIndividu++) {
				int sommeDelaisMemeIndividu = 0;
				String[] stringDelaisVoisinsMemeIndividu = ligne.split(" ");
				
				if (stringDelaisVoisinsMemeIndividu.length != nombreIndividus) {
					throw new ConfigurationException("Nombre de colonnes invalide pour la matrice : la matrice de voisinage doit etre carree de taille nombreIndividus");
				}
				
				int valeurDelais;
				for (int indiceVoisin = 0; indiceVoisin < nombreIndividus; indiceVoisin++) {
					valeurDelais = Integer.parseUnsignedInt(stringDelaisVoisinsMemeIndividu[indiceVoisin]);
					matriceVoisinage[indiceIndividu][indiceVoisin] = valeurDelais;
					sommeDelaisMemeIndividu += valeurDelais;
				}

				if (sommeDelaisMemeIndividu < 1) {
					throw new ConfigurationException("Chaque individu doit posseder au moins un voisin : la somme des elements d'une ligne ne peut pas etre nulle");
				}
				if (matriceVoisinage[indiceIndividu][indiceIndividu] > 0) {
					throw new ConfigurationException("Un individu ne peut etre son propre voisin : la diagonale doit etre nulle");
				}
				
				if ((ligne = lecteurATampon.readLine()) == null ) {
					if (indiceIndividu < nombreIndividus - 1) {
						throw new ConfigurationException("Nombre de lignes invalide pour la matrice : la matrice de voisinage doit etre carree de taille nombreIndividus");
					}
				}
			}
		}
		catch (NumberFormatException exception) {
			throw new ConfigurationException("La matrice de voisinage doit contenir exclusivement des unsigned int, separes par un espace");
		}
		finally {
			lecteurATampon.close();
			lecteurFichier.close();
		}

		return matriceVoisinage;
	}
	
	public String lireFichierGraphe() throws ConfigurationException {
		if (elementSysteme.getAttribute(stringFichierGraphe) != null) {
			return elementSysteme.getAttribute(stringFichierGraphe).getValue();
		}
		throw new ConfigurationException(messageErreurAttrAbsent(stringFichierGraphe));
	}
	
	public int lireNombreIndividus() throws ConfigurationException {
		int nombreIndividus, nombreMin = 2, nombreMax = 10;
		if (elementSysteme.getAttribute(stringNombreIndividus) == null) {
			throw new ConfigurationException(messageErreurElemAbsent(stringNombreIndividus));
		}
		try {
			nombreIndividus = elementSysteme.getAttribute(stringNombreIndividus).getIntValue();
			if (nombreIndividus < nombreMin || nombreIndividus > nombreMax) {
				throw new ConfigurationException();
			}
		}
		catch (DataConversionException exception) {
			throw new ConfigurationException(messageErreurAttrAbsentOuInvalide(
				stringNombreIndividus, String.valueOf(nombreMin), String.valueOf(nombreMax)
			));
		}
		return nombreIndividus;
	}

	public int lireTailleInitialeLexiqueParDefaut() throws ConfigurationException {
		int tailleInitialeLexique, tailleMin = 1, tailleMax = 20;
		try {
			tailleInitialeLexique = elementSysteme.getAttribute(stringTailleInitialeLexique + stringParDefaut).getIntValue();
			if (tailleInitialeLexique < 1 || tailleInitialeLexique > 20) {
				throw new ConfigurationException();
			}
		}
		catch (Exception exception) {
			throw new ConfigurationException(messageErreurAttrAbsentOuInvalide(
				stringTailleInitialeLexique + stringParDefaut, String.valueOf(tailleMin), String.valueOf(tailleMax)
			));
		}
		return tailleInitialeLexique;
	}
	
	public int lireTailleInitialeLexique(Element element) throws ConfigurationException {
		int tailleInitialeLexique, tailleMax, tailleMin = 1;
		if (element.getAttribute(stringTailleInitialeLexique) != null) {
			try {
				tailleInitialeLexique = element.getAttribute(stringTailleInitialeLexique).getIntValue();
				tailleMax = configurationSysteme.getTailleMaximaleLexiqueParDefaut();
				if (tailleInitialeLexique < tailleMin || tailleInitialeLexique > tailleMax) {
					throw new ConfigurationException();
				}
			}
			catch (Exception exception) {
				throw new ConfigurationException(messageErreurAttrInvalide(
					stringTailleInitialeLexique, String.valueOf(tailleMin), stringTailleMaximaleLexique + stringParDefaut
				));
			}
			return tailleInitialeLexique;
		}
		return configurationSysteme.getTailleInitialeLexiqueParDefaut();
	}
	
	public int lireTailleMaximaleLexiqueParDefaut() throws ConfigurationException {
		int tailleMaximaleLexique, tailleMin, tailleMax = 20;
		try {
			tailleMaximaleLexique = elementSysteme.getAttribute(stringTailleMaximaleLexique + stringParDefaut).getIntValue();
			tailleMin = configurationSysteme.getTailleInitialeLexiqueParDefaut();
			if (tailleMaximaleLexique < tailleMin || tailleMaximaleLexique > tailleMax) {
				throw new ConfigurationException();
			}
		}
		catch (Exception exception) {
			throw new ConfigurationException(messageErreurAttrAbsentOuInvalide(
				stringTailleMaximaleLexique + stringParDefaut, stringTailleInitialeLexique + stringParDefaut, String.valueOf(tailleMax)
			));
		}
		return tailleMaximaleLexique;
	}
	
	public int lireTailleMaximaleLexique(Element element) throws ConfigurationException {
		int tailleMaximaleLexique, tailleMin, tailleMax = 20;
		if (element.getAttribute(stringTailleMaximaleLexique) != null) {
			try {
				try {
					tailleMin = lireTailleInitialeLexique(element);
				}
				catch (Exception exception) {
					tailleMin = configurationSysteme.getTailleInitialeLexiqueParDefaut();
				}
				tailleMaximaleLexique = element.getAttribute(stringTailleMaximaleLexique).getIntValue();
				if (tailleMaximaleLexique < tailleMin || tailleMaximaleLexique > tailleMax) {
					throw new ConfigurationException();
				}
			}
			catch (Exception exception) {
				throw new ConfigurationException(messageErreurAttrInvalide(
					stringTailleMaximaleLexique, stringTailleInitialeLexique + "(" + stringParDefaut + ")", String.valueOf(tailleMax)
				));
			}
			return tailleMaximaleLexique;
		}
		return configurationSysteme.getTailleMaximaleLexiqueParDefaut();
	}
	
	public ImplementationCondition lireImplConditionEmissionParDefaut() throws ConfigurationException {
		try {
			return ImplementationCondition.valueOf(elementSysteme.getChild(stringImplConditionEmission + stringParDefaut).getValue());
		}
		catch (Exception exception) {
			throw new ConfigurationException(messageErreurElemAbsentOuInvalide(
				stringImplConditionEmission + stringParDefaut, ImplementationCondition.class.getName()
			));
		}
	}
	
	public ImplementationCondition lireImplConditionEmission(Element element) throws ConfigurationException {
		if (element.getChild(stringImplConditionEmission) != null) {
			try {
				return ImplementationCondition.valueOf(element.getChild(stringImplConditionEmission).getValue());
			}
			catch (Exception exception) {
				throw new ConfigurationException(messageErreurElemInvalide(
					stringImplConditionEmission, ImplementationCondition.class.getName()
				));
			}
		}
		return configurationSysteme.getImplConditionEmissionParDefaut();
	}

	public ImplementationCondition lireImplConditionReceptionParDefaut() throws ConfigurationException {
		try {
			return ImplementationCondition.valueOf(elementSysteme.getChild(stringImplConditionReception + stringParDefaut).getValue());
		}
		catch (Exception exception) {
			throw new ConfigurationException(messageErreurElemAbsentOuInvalide(
				stringImplConditionReception + stringParDefaut, ImplementationCondition.class.getName()
			));
		}
	}
	
	public ImplementationCondition lireImplConditionReception(Element element) throws ConfigurationException {
		if (element.getChild(stringImplConditionReception) != null) {
			try {
				return ImplementationCondition.valueOf(element.getChild(stringImplConditionReception).getValue());
			}
			catch (Exception exception) {
				throw new ConfigurationException(messageErreurElemInvalide(
					stringImplConditionReception, ImplementationCondition.class.getName()
				));
			}
		}
		return configurationSysteme.getImplConditionReceptionParDefaut();
	}
	
	public ImplementationCondition lireImplConditionMemorisationParDefaut() throws ConfigurationException {
		try {
			return ImplementationCondition.valueOf(elementSysteme.getChild(stringImplConditionMemorisation + stringParDefaut).getValue());
		}
		catch (Exception exception) {
			throw new ConfigurationException(messageErreurElemAbsentOuInvalide(
				stringImplConditionMemorisation + stringParDefaut, ImplementationCondition.class.getName()
			));
		}
	}
	
	public ImplementationCondition lireImplConditionMemorisation(Element element) throws ConfigurationException {
		if (element.getChild(stringImplConditionMemorisation) != null) {
			try {
				return ImplementationCondition.valueOf(element.getChild(stringImplConditionMemorisation).getValue());
			}
			catch (Exception exception) {
				throw new ConfigurationException(messageErreurElemInvalide(
					stringImplConditionMemorisation, ImplementationCondition.class.getName()
				));
			}
		}
		return configurationSysteme.getImplConditionMemorisationParDefaut();
	}
	
	public ImplementationStrategieSelection lireImplStrategieSelectionEmissionParDefaut() throws ConfigurationException {
		try {
			return ImplementationStrategieSelection.valueOf(elementSysteme.getChild(stringImplStrategieSelectionEmission + stringParDefaut).getValue());
		}
		catch (Exception exception) {
			throw new ConfigurationException(messageErreurElemAbsentOuInvalide(
				stringImplStrategieSelectionEmission + stringParDefaut, ImplementationStrategieSelection.class.getName()
			));
		}
	}
	
	public ImplementationStrategieSelection lireImplStrategieSelectionEmission(Element element) throws ConfigurationException {
		if (element.getChild(stringImplStrategieSelectionEmission) != null) {
			try {	
				return ImplementationStrategieSelection.valueOf(element.getChild(stringImplStrategieSelectionEmission).getValue());
			}
			catch (Exception exception) {
				throw new ConfigurationException(messageErreurElemInvalide(
					stringImplStrategieSelectionEmission, ImplementationStrategieSelection.class.getName()
				));
			}
		}
		return configurationSysteme.getImplStrategieSelectionEmissionParDefaut();
	}
	
	public ImplementationStrategieSelection lireImplStrategieSelectionEliminationParDefaut() throws ConfigurationException {
		try {
			return ImplementationStrategieSelection.valueOf(elementSysteme.getChild(stringImplStrategieSelectionElimination + stringParDefaut).getValue());
		}
		catch (Exception exception) {
			throw new ConfigurationException(messageErreurElemAbsentOuInvalide(
				stringImplStrategieSelectionElimination + stringParDefaut, ImplementationStrategieSelection.class.getName()
			));
		}
	}
	
	public ImplementationStrategieSelection lireImplStrategieSelectionElimination(Element element) throws ConfigurationException {
		if (element.getChild(stringImplStrategieSelectionElimination) != null) {
			try {
				return ImplementationStrategieSelection.valueOf(element.getChild(stringImplStrategieSelectionElimination).getValue());
			}
			catch (Exception exception) {
				throw new ConfigurationException(messageErreurElemInvalide(
					stringImplStrategieSelectionElimination, ImplementationStrategieSelection.class.getName()
				));
			}
		}
		return configurationSysteme.getImplStrategieSelectionEliminationParDefaut();
	}
	
	public ImplementationStrategieSuccession lireImplStrategieSuccessionParDefaut() throws ConfigurationException {
		try {
			return ImplementationStrategieSuccession.valueOf(elementSysteme.getChild(stringImplStrategieSuccession + stringParDefaut).getValue());
		}
		catch (Exception exception) {
			throw new ConfigurationException(messageErreurElemAbsentOuInvalide(
				stringImplStrategieSuccession + stringParDefaut, ImplementationStrategieSuccession.class.getName()
			));
		}
	}
	
	public ImplementationStrategieSuccession lireImplStrategieSuccession(Element element) throws ConfigurationException {
		if (element.getChild(stringImplStrategieSuccession) != null) {
			try {
				return ImplementationStrategieSuccession.valueOf(element.getChild(stringImplStrategieSuccession).getValue());
			}
			catch (Exception exception) {
				throw new ConfigurationException(messageErreurElemInvalide(
					stringImplStrategieSuccession, ImplementationStrategieSuccession.class.getName()
				));
			}
		}
		return configurationSysteme.getImplStrategieSuccessionParDefaut();
	}

	public CritereArret lireCritereArret() throws ConfigurationException {
		TypeCritereArret type; int objectif, minObjectif = 1, maxObjectif = 1000;
		
		if (elementSysteme.getChild(stringCritereArret) == null) {
			throw new ConfigurationException(messageErreurElemAbsent(stringCritereArret));
		}
		Element elementCritereArret = elementSysteme.getChild(stringCritereArret);
		try {
			objectif = elementCritereArret.getAttribute(stringObjectifCritereArret).getIntValue();
			if (objectif < 1 || objectif > 1000) {
				throw new ConfigurationException();
			}
		}
		catch (Exception exception) {
			throw new ConfigurationException(messageErreurAttrAbsentOuInvalide(
				stringObjectifCritereArret, String.valueOf(minObjectif), String.valueOf(maxObjectif)
			));
		}
		try {
			type = TypeCritereArret.valueOf(elementCritereArret.getChild(stringTypeCritereArret).getValue());
		}
		catch (Exception exception) {
			throw new ConfigurationException(messageErreurElemAbsentOuInvalide(
				stringTypeCritereArret, TypeCritereArret.class.getName()
			));
		}
		return new CritereArret(type, objectif);
	}
	
	public int lireID(Element element) throws ConfigurationException {
		int ID, minID = 1, maxID = configurationSysteme.getNombreIndividus();
		
		try {
			ID = element.getAttribute(stringID).getIntValue();
			if (ID < minID || ID > maxID) {
				throw new ConfigurationException();
			}
			return ID;
		}
		catch (Exception exception) {
			throw new ConfigurationException(messageErreurAttrAbsentOuInvalide(
				stringID, String.valueOf(minID), stringNombreIndividus
			));
		}
	}
	
	public String messageErreurElemAbsent(String nomElement) {
		return "Element '" + nomElement + "' absent";
	}
	
	public String messageErreurElemInvalide(String nomElement, String typeElement) {
		return "Element '" + nomElement + "' invalide : " + typeElement + " attendu";
	}
	
	public String messageErreurElemAbsentOuInvalide(String nomElement, String typeElement) {
		return "Element '" + nomElement + "' absent ou invalide : " + typeElement + " attendu";
	}
	
	public String messageErreurAttrAbsent(String nomAttribut) {
		return "Attribut '" + nomAttribut + "' absent";
	}
	
	public String messageErreurAttrInvalide(String nomAttribut, String valeurMin, String valeurMax) {
		return "Attribut '" + nomAttribut + "' invalide : int dans [" + valeurMin + ", " + valeurMax + "] attendu";
	}
	
	public String messageErreurAttrAbsentOuInvalide(String nomAttribut, String valeurMin, String valeurMax) {
		return "Attribut '" + nomAttribut + "' absent ou invalide : int dans [" + valeurMin + ", " + valeurMax + "] attendu";
	}
}