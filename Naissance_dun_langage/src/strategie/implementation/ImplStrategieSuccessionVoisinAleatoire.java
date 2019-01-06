package strategie.implementation;

import java.util.Random;

import strategie.modele.StrategieSuccession;
import systeme.Individu;
import systeme.Voisin;

/**
 * Implementation de la classe StrategieSuccession determinant un voisin aleatoire de l'individu courant comme successeur
 * 
 * @author Charles MECHERIKI & Yongda LIN
 *
 */
public class ImplStrategieSuccessionVoisinAleatoire extends StrategieSuccession {
	private final Random random = new Random();
	
	@Override
	protected Voisin determinerSuccesseur(Individu individuCourant) {
		return individuCourant.obtenirVoisins().get(random.nextInt(individuCourant.obtenirVoisins().size()));
	}
}