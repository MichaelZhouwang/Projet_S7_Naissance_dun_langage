package strategie;

import architecture.Lemme;

public abstract class StrategieSelectionLemmeDependante {
	public abstract Lemme determinerLemme(Lemme lemme);
	
	public Lemme determinerLemme() {
		return determinerLemme(null);
	}
}
