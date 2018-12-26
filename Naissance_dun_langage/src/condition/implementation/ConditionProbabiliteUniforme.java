package condition.implementation;

import java.util.Random;

import condition.modele.Condition;
import lexique.Lemme;
import systeme.Individu;

public class ConditionProbabiliteUniforme extends Condition {
	private final Random random = new Random();
	
	@Override
	public boolean estSatisfaite(Individu individuCourant, Lemme lemmeCourant) {
		return random.nextFloat() > 0.5;
	}
}
