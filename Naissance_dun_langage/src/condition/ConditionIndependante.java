package condition;

import lexique.Lemme;

public abstract class ConditionIndependante extends ConditionDependante {
	public abstract boolean estSatisfaite();
	
	public boolean estSatisfaite(Lemme lemme) {
		return estSatisfaite();
	}
}
