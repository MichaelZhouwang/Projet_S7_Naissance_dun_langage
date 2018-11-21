package strategie;

import lexique.Lemme;

public abstract class StrategieSelectionLemmeIndependante extends StrategieSelectionLemmeDependante {
	public abstract Lemme determinerLemme();
	
	public Lemme determinerLemme(Lemme lemme) {
		return determinerLemme();
	}
}
