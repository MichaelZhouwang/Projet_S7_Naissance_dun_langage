package condition.implementation;

import condition.modele.Condition;
import lexique.Lemme;
import systeme.Individu;

public class ConditionJamaisVerifiee extends Condition {

	@Override
	public boolean estSatisfaite(Individu individuCourant, Lemme lemmeCourant) {
		return false;
	}

}
