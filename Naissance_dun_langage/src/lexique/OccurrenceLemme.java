package lexique;

import evenement.enums.IssueEvenement;
import evenement.enums.TypeEvenement;
import systeme.Individu;
import temps.Date;

public class OccurrenceLemme {
	private static int compteur = 0;
	private int ID;
	
	private Lemme lemme;
	private Date date;
	private Individu individu;
	private TypeEvenement typeEvenement;
	private IssueEvenement issueEvenement;
	
	public OccurrenceLemme(Lemme lemme, Date date, Individu individu, TypeEvenement typeEvenement, IssueEvenement issueEvenement) {
		ID = ++compteur;
		this.lemme = lemme;
		this.date = date;
		this.individu = individu;
		this.typeEvenement = typeEvenement;
		this.issueEvenement = issueEvenement;
	}
	
	public static int lireCompteur() {
		return compteur;
	}
	
	public int lireID() {
		return ID;
	}
	
	public Lemme lireLemme() {
		return lemme;
	}
	
	public Date lireDate() {
		return date;
	}
	
	public Individu lireIndividu() {
		return individu;
	}
	
	public TypeEvenement lireTypeEvenement() {
		return typeEvenement;
	}
	
	public IssueEvenement lireIssueEvenement() {
		return issueEvenement;
	}
}
