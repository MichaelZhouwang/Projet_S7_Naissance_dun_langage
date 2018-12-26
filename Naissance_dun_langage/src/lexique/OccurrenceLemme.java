package lexique;

import evenement.enumeration.IssueEvenement;
import evenement.enumeration.TypeEvenement;
import systeme.Individu;
import temps.Date;

public class OccurrenceLemme {

	private int ID;
	private OccurrenceLemme occurrenceInitiatrice;
	private Individu individu;
	private Lemme lemme;
	private TypeEvenement typeEvenement;
	private IssueEvenement issueEvenement;
	private Date date;
	
	public OccurrenceLemme(int ID, OccurrenceLemme occurrenceInitiatrice, Individu individu, Lemme lemme, TypeEvenement typeEvenement, IssueEvenement issueEvenement, Date date) {
		this.ID = ID;
		this.occurrenceInitiatrice = occurrenceInitiatrice;
		this.individu = individu;
		this.lemme = lemme;
		this.typeEvenement = typeEvenement;
		this.issueEvenement = issueEvenement;
		this.date = date;
	}
	
	@Override
	public String toString() {
		String string = "";
		
		switch (lireTypeEvenement()) {
			case EMISSION:
				break;
			case RECEPTION:
				string += "\t";
				break;
			case MEMORISATION:
				string += "\t\t";
				break;
			case ELIMINATION:
				string += "\t\t\t";
				break;
			case QUELCONQUE:
				break;
		}
		string += lireTypeEvenement() + " ("
			+ lireIssueEvenement() + ") par "
			+ lireIndividu().lireLettre() + " de "
			+ lireLemme() + " a la date "
			+ lireDate() + " [ev. "
			+ lireID() + " initie par " + ((lireOccurrenceInitiatrice() == null) ? "null" : lireOccurrenceInitiatrice().lireID()) + "]";
		
		return string;
	}


	public int lireID() {
		return ID;
	}
	

	public OccurrenceLemme lireOccurrenceInitiatrice() {
		return occurrenceInitiatrice;
	}
	
	public Individu lireIndividu() {
		return individu;
	}
	
	public Lemme lireLemme() {
		return lemme;
	}

	public TypeEvenement lireTypeEvenement() {
		return typeEvenement;
	}
	
	public IssueEvenement lireIssueEvenement() {
		return issueEvenement;
	}
	
	public Date lireDate() {
		return date;
	}
	
	/* Bean */
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public OccurrenceLemme getOccurrenceInitiatrice() {
		return occurrenceInitiatrice;
	}

	public void setOccurrenceInitiatrice(OccurrenceLemme occurrenceInitiatrice) {
		this.occurrenceInitiatrice = occurrenceInitiatrice;
	}

	public Individu getIndividu() {
		return individu;
	}

	public void setIndividu(Individu individu) {
		this.individu = individu;
	}

	public Lemme getLemme() {
		return lemme;
	}

	public void setLemme(Lemme lemme) {
		this.lemme = lemme;
	}

	public TypeEvenement getTypeEvenement() {
		return typeEvenement;
	}

	public void setTypeEvenement(TypeEvenement typeEvenement) {
		this.typeEvenement = typeEvenement;
	}

	public IssueEvenement getIssueEvenement() {
		return issueEvenement;
	}

	public void setIssueEvenement(IssueEvenement issueEvenement) {
		this.issueEvenement = issueEvenement;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
