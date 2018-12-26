package condition.implementation;

import condition.modele.Condition;
import lexique.Lemme;
import systeme.Individu;

public class ConditionToujoursVerifiee extends Condition {

	@Override
	public boolean estSatisfaite(Individu individuCourant, Lemme lemmeCourant) {
		return true;
	}

}
