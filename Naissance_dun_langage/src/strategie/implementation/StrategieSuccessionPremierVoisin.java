package strategie.implementation;

import strategie.modele.StrategieSuccession;
import systeme.Individu;
import systeme.Voisin;

public class StrategieSuccessionPremierVoisin extends StrategieSuccession {

	@Override
	protected Voisin determinerSuccesseur(Individu individuCourant) {
		return individuCourant.obtenirVoisins().get(0);
	}
}