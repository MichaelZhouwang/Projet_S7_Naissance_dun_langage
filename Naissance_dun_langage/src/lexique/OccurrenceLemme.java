package lexique;

import evenement.enumeration.IssueEvenement;
import evenement.enumeration.TypeEvenement;
import systeme.Individu;
import temps.Date;

public class OccurrenceLemme {
	private static int compteur = 0;
	private int ID;
	private OccurrenceLemme occurrenceInitiatrice;
	private Individu individu;
	private Lemme lemme;
	private TypeEvenement typeEvenement;
	private IssueEvenement issueEvenement;
	private Date date;
	
	public OccurrenceLemme(OccurrenceLemme occurrenceInitiatrice, Individu individu, Lemme lemme, TypeEvenement typeEvenement, IssueEvenement issueEvenement, Date date) {
		this.ID = ++compteur;
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
		
		switch (getTypeEvenement()) {
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
		string += getTypeEvenement() + " ("
			+ getIssueEvenement() + ") par "
			+ getIndividu().lireLettre() + " de "
			+ getLemme() + " à la date "
			+ getDate() + " [ev. "
			+ getID() + " initié par " + ((getOccurrenceInitiatrice() == null) ? "null" : getOccurrenceInitiatrice().getID()) + "]";
		
		return string;
	}

	public int getID() {
		return ID;
	}
	
	public OccurrenceLemme getOccurrenceInitiatrice() {
		return occurrenceInitiatrice;
	}
	
	public Individu getIndividu() {
		return individu;
	}
	
	public Lemme getLemme() {
		return lemme;
	}

	public TypeEvenement getTypeEvenement() {
		return typeEvenement;
	}
	
	public IssueEvenement getIssueEvenement() {
		return issueEvenement;
	}
	
	public Date getDate() {
		return date;
	}
}
