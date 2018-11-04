package architecture;

import java.util.ArrayList;

public class OccurrencesLemme {
	private Lemme lemme;

	private ArrayList<Date> emissions;
	private ArrayList<Date> receptions;
	private ArrayList<Date> memorisations;
	private ArrayList<Date> remplacements;
	
	public OccurrencesLemme(Lemme lemme) {
		this.lemme = lemme;
		
		emissions = new ArrayList<Date>();
		receptions = new ArrayList<Date>();
		memorisations = new ArrayList<Date>();
		remplacements = new ArrayList<Date>();
	}
	
	public Lemme lireLemme() {
		return lemme;
	}
	
	public void nouvelleEmission(Date date) {
		emissions.add(date);
	}
	
	public void nouvelleReception(Date date) {
		receptions.add(date);
	}
	
	public void nouvelleMemorisation(Date date) {
		memorisations.add(date);
	}
	
	public void nouveauRemplacement(Date date) {
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
