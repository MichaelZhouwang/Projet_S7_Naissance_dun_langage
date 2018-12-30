package strategie.implementation;

import java.util.Random;

import strategie.modele.StrategieSuccession;
import systeme.Individu;
import systeme.Voisin;

public class StrategieSuccessionVoisinAleatoire extends StrategieSuccession {
	private final Random random = new Random();
	
	@Override
	protected Voisin determinerSuccesseur(Individu individuCourant) {
		return individuCourant.obtenirVoisins().get(random.nextInt(individuCourant.obtenirVoisins().size()));
	}
}