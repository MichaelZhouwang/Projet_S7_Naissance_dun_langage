package condition.implementation;

import condition.modele.Condition;
import systeme.Individu;
import systeme.lexique.Lemme;

/**
 * Implementation de la classe Condition qui est toujours satisfaite
 * 
 * @author Charles MECHERIKI & Yongda LIN
 *
 */
public class ImplConditionToujoursSatisfaite extends Condition {

	@Override
	public boolean estSatisfaite(Individu individuCourant, Lemme lemmeCourant) {
		return true;
	}
}
