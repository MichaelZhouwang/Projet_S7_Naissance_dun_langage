package ihm.bean;

import lexique.Lemme;
import systeme.Individu;
import temps.Date;

public class LemmeMemorise {
	private Individu emetteur;
	private Individu recepteur;
	private Lemme lemme;
	private Date dateMemorisation;
	
	public LemmeMemorise(Individu emetteur, Individu recepteur, Lemme lemme, Date dateMemorisation) {
		this.emetteur = emetteur;
		this.recepteur = recepteur;
		this.lemme = lemme;
		this.dateMemorisation = dateMemorisation;
	}

	public Individu getEmetteur() {
		return emetteur;
	}

	public void setEmetteur(Individu emetteur) {
		this.emetteur = emetteur;
	}
	
	public Individu getRecepteur() {
		return recepteur;
	}

	public void setRecepteur(Individu recepteur) {
		this.recepteur = recepteur;
	}

	public Lemme getLemme() {
		return lemme;
	}

	public void setLemme(Lemme lemme) {
		this.lemme = lemme;
	}

	public Date getDateMemorisation() {
		return dateMemorisation;
	}

	public void setDateMemorisation(Date dateMemorisation) {
		this.dateMemorisation = dateMemorisation;
	}
}
