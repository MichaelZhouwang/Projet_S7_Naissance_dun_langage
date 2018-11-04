package condition;

import architecture.Lemme;

public abstract class ConditionDependante {
	public abstract boolean estSatisfaite(Lemme lemme);
	
	public boolean estSatisfaite() {
		return estSatisfaite(null);
	}
}