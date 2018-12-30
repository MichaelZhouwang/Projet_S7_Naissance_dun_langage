package lecture;

import java.util.ArrayList;
import condition.enumeration.ImplementationCondition;
import strategie.enumeration.ImplementationStrategieSelection;
import strategie.enumeration.ImplementationStrategieSuccession;
import systeme.CritereArret;
import systeme.Individu;
import systeme.Voisin;
import temps.Delais;

public class ConfigurationSysteme {

	private int nombreIndividus;

	private int tailleInitialeLexiqueParDefaut;
	private int tailleMaximaleLexiqueParDefaut;
	
	private ImplementationStrategieSelection implStrategieSelectionEmissionParDefaut;
	private ImplementationStrategieSelection implStrategieSelectionEliminationParDefaut;
	private ImplementationStrategieSuccession implStrategieSuccessionParDefaut;
	
	private ImplementationCondition implConditionEmissionParDefaut;
	private ImplementationCondition implConditionReceptionParDefaut;
	private ImplementationCondition implConditionMemorisationParDefaut;
	
	private CritereArret critereArret;
	
	private ArrayList<Individu> individus;
	
	public ConfigurationSysteme() {
		individus = new ArrayList<Individu>();
	}

	public int getNombreIndividus() {
		return nombreIndividus;
	}

	public void setNombreIndividus(int nombreIndividus) {
		this.nombreIndividus = nombreIndividus;
	}

	public int getTailleInitialeLexiqueParDefaut() {
		return tailleInitialeLexiqueParDefaut;
	}

	public void setTailleInitialeLexiqueParDefaut(int tailleInitialeLexiqueParDefaut) {
		this.tailleInitialeLexiqueParDefaut = tailleInitialeLexiqueParDefaut;
	}

	public int getTailleMaximaleLexiqueParDefaut() {
		return tailleMaximaleLexiqueParDefaut;
	}

	public void setTailleMaximaleLexiqueParDefaut(int tailleMaximaleLexiqueParDefaut) {
		this.tailleMaximaleLexiqueParDefaut = tailleMaximaleLexiqueParDefaut;
	}

	public ImplementationStrategieSelection getImplStrategieSelectionEmissionParDefaut() {
		return implStrategieSelectionEmissionParDefaut;
	}

	public void setImplStrategieSelectionEmissionParDefaut(ImplementationStrategieSelection implStrategieSelectionEmissionParDefaut) {
		this.implStrategieSelectionEmissionParDefaut = implStrategieSelectionEmissionParDefaut;
	}

	public ImplementationStrategieSelection getImplStrategieSelectionEliminationParDefaut() {
		return implStrategieSelectionEliminationParDefaut;
	}

	public void setImplStrategieSelectionEliminationParDefaut(ImplementationStrategieSelection implStrategieSelectionEliminationParDefaut) {
		this.implStrategieSelectionEliminationParDefaut = implStrategieSelectionEliminationParDefaut;
	}

	public ImplementationStrategieSuccession getImplStrategieSuccessionParDefaut() {
		return implStrategieSuccessionParDefaut;
	}

	public void setImplStrategieSuccessionParDefaut(ImplementationStrategieSuccession implStrategieSuccessionParDefaut) {
		this.implStrategieSuccessionParDefaut = implStrategieSuccessionParDefaut;
	}

	public ImplementationCondition getImplConditionEmissionParDefaut() {
		return implConditionEmissionParDefaut;
	}

	public void setImplConditionEmissionParDefaut(ImplementationCondition implConditionEmissionParDefaut) {
		this.implConditionEmissionParDefaut = implConditionEmissionParDefaut;
	}

	public ImplementationCondition getImplConditionReceptionParDefaut() {
		return implConditionReceptionParDefaut;
	}

	public void setImplConditionReceptionParDefaut(ImplementationCondition implConditionReceptionParDefaut) {
		this.implConditionReceptionParDefaut = implConditionReceptionParDefaut;
	}

	public ImplementationCondition getImplConditionMemorisationParDefaut() {
		return implConditionMemorisationParDefaut;
	}

	public void setImplConditionMemorisationParDefaut(ImplementationCondition implConditionMemorisationParDefaut) {
		this.implConditionMemorisationParDefaut = implConditionMemorisationParDefaut;
	}

	public ArrayList<Individu> getIndividus() {
		return individus;
	}
	
	public void setIndividus(ArrayList<Individu> individus) {
		this.individus = individus;
	}
	
	public CritereArret getCritereArret() {
		return critereArret;
	}

	public void setCritereArret(CritereArret critereArret) {
		this.critereArret = critereArret;
	}
	
	public void genererVoisinageDepuisMatrice(int[][] matriceVoisinage) {
		for (int indiceIndividu = 0; indiceIndividu < matriceVoisinage.length; indiceIndividu++) {
			for (int indiceVoisin = 0; indiceVoisin < matriceVoisinage.length; indiceVoisin++) {
				if (matriceVoisinage[indiceIndividu][indiceVoisin] > 0) {
					individus.get(indiceIndividu).ajouterVoisin(
						new Voisin(individus.get(indiceVoisin),
						new Delais(matriceVoisinage[indiceIndividu][indiceVoisin]))
					);
				}
			}
		}
	}
}
