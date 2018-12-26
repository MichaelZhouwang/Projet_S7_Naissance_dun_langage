package strategie.implementation;

import strategie.modele.StrategieSuccession;
import systeme.Individu;

public class StrategieSuccessionPremierVoisin extends StrategieSuccession {

	@Override
	protected Individu determinerSuccesseur(Individu individuCourant) {
		return individuCourant.obtenirVoisins().get(0).obtenirIndividu();
	}
}