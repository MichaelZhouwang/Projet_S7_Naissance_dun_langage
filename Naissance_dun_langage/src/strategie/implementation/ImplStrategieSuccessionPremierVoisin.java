package strategie.implementation;

import strategie.modele.StrategieSuccession;
import systeme.Individu;
import systeme.Voisin;

/**
 * Implementation de la classe StrategieSuccession determinant le premier voisin de l'individu courant comme successeur
 * 
 * @author Charles MECHERIKI & Yongda LIN
 *
 */
public class ImplStrategieSuccessionPremierVoisin extends StrategieSuccession {

	@Override
	protected Voisin determinerSuccesseur(Individu individuCourant) {
		return individuCourant.obtenirVoisins().get(0);
	}
}