package strategie.implementation;

import java.util.Random;

import strategie.modele.StrategieSuccession;
import systeme.Individu;

public class StrategieSuccessionVoisinAleatoire extends StrategieSuccession {
	private final Random random = new Random();
	
	@Override
	protected Individu determinerSuccesseur(Individu individuCourant) {
		return individuCourant.obtenirVoisins().get(random.nextInt(individuCourant.obtenirVoisins().size())).obtenirIndividu();
	}
}