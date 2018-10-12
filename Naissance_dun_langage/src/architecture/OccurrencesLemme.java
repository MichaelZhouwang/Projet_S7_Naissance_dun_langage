package architecture;

public class OccurrencesLemme {
	private Lemme lemme;
	
	private int premiereEmission;
	private int derniereEmission;
	private int nombreEmissions;
	
	private int premiereReception;
	private int derniereReception;
	private int nombreReceptions;
	
	private int premiereMemorisation;
	private int derniereMemorisation;
	private int nombreMemorisations;
	
	public OccurrencesLemme(Lemme lemme) {
		this.lemme = lemme;
		
		premiereEmission = -1;
		derniereEmission = -1;
		nombreEmissions = 0;
		
		premiereReception = -1;
		derniereReception = -1;
		nombreReceptions = 0;
		
		premiereMemorisation = -1;
		derniereMemorisation = -1;
		nombreMemorisations = 0;
	}
	public Lemme lireLemme() {
		return lemme;
	}
	
	public int lirePremierEmission() {
		return premiereEmission;
	}
	
	public int lireDerniereEmission() {
		return derniereEmission;
	}
	
	public int lireNombreEmissions() {
		return nombreEmissions;
	}
	
	public void nouvelleEmission(int dateOccurrence) {
		if (premiereEmission == -1) {
			premiereEmission = dateOccurrence;
		}
		derniereEmission = dateOccurrence;
		nombreEmissions++;
	}

	public int lirePremierReception() {
		return premiereReception;
	}
	
	public int lireDerniereReception() {
		return derniereReception;
	}
	
	public int lireNombreReceptions() {
		return nombreReceptions;
	}
	
	public void nouvelleReception(int dateOccurrence) {
		if (premiereReception == -1) {
			premiereReception = dateOccurrence;
		}
		derniereReception = dateOccurrence;
		nombreReceptions++;
	}
	
	public int lirePremierMemorisation() {
		return premiereMemorisation;
	}
	
	public int lireDerniereMemorisation() {
		return derniereMemorisation;
	}
	
	public int lireNombreMemorisations() {
		return nombreMemorisations;
	}
	
	public void nouvelleMemorisation(int dateOccurrence) {
		if (premiereMemorisation == -1) {
			premiereMemorisation = dateOccurrence;
		}
		derniereMemorisation = dateOccurrence;
		nombreMemorisations++;
	}
	
	@Override
	public String toString() {
		return "Occurrences " + lemme.toString() +
			" : \tNE : " + nombreEmissions +
			", \tPE : " + premiereEmission +
			", \tDE : " + derniereEmission +
			", \tNR : " + nombreReceptions +
			", \tPR : " + premiereReception +
			", \tDR : " + derniereReception +
			", \tNM : " + nombreMemorisations +
			", \tPM : " + premiereMemorisation +
			", \tDM : " + derniereMemorisation +
			"\n";
	}
}
