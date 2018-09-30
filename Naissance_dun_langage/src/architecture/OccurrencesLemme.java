package architecture;

import java.util.ArrayList;

public class OccurrencesLemme {
	private Lemme lemme;

	private ArrayList<Integer> emissions;
	private ArrayList<Integer> receptions;
	private ArrayList<Integer> memorisations;
	private ArrayList<Integer> remplacements;
	
	public OccurrencesLemme(Lemme lemme) {
		this.lemme = lemme;
		
		emissions = new ArrayList<Integer>();
		receptions = new ArrayList<Integer>();
		memorisations = new ArrayList<Integer>();
		remplacements = new ArrayList<Integer>();
	}
	
	public Lemme lireLemme() {
		return lemme;
	}
	
	public void nouvelleEmission(int date) {
		emissions.add(date);
	}
	
	public void nouvelleReception(int date) {
		receptions.add(date);
	}
	
	public void nouvelleMemorisation(int date) {
		memorisations.add(date);
	}
	
	public void nouveauRemplacement(int date) {
		remplacements.add(date);
	}
	
	public int nombreEmissions() {
		return emissions.size();
	}
	
	public int nombreReceptions() {
		return receptions.size();
	}
	
	public int nombreMemorisations() {
		return memorisations.size();
	}
	
	public int nombreRemplacements() {
		return remplacements.size();
	}
	
	public String toString() {
		return lemme + " |"
					 + " \tE :\t" + nombreEmissions()
					 + ",\tR :\t" + nombreReceptions()
					 + ",\tM :\t" + nombreMemorisations()
					 + ",\tR :\t" + nombreRemplacements();
	}
}
