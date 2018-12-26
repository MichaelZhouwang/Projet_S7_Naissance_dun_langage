package systeme;

import temps.Delais;

public class Voisin {
	private Individu individu;
	private Delais delais;
	
	public Voisin(Individu individu, Delais delais) {
		this.individu = individu;
		this.delais = delais;
	}
	
	public Individu obtenirIndividu() {
		return individu;
	}
	
	public Delais lireDelais() {
		return delais;
	}
}
