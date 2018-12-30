package systeme;

import systeme.enumeration.TypeCritereArret;

public class CritereArret {
	private TypeCritereArret type;
	private int objectif;

	public CritereArret(TypeCritereArret type, int objectif) {
		this.type = type;
		this.objectif = objectif;
	}
	
	public TypeCritereArret lireTypeCritere() {
		return type;
	}
	
	public int lireObjectif() {
		return objectif;
	}
}
