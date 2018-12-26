package condition.modele;

import condition.enumeration.ImplementationCondition;
import condition.implementation.ConditionJamaisVerifiee;
import condition.implementation.ConditionProbabiliteUniforme;
import condition.implementation.ConditionToujoursVerifiee;
import lexique.Lemme;
import systeme.Individu;

public abstract class Condition {
	public abstract boolean estSatisfaite(Individu individuCourant, Lemme lemmeCourant);
	
	public static boolean executerImplementationEmission(Individu individuCourant) {
		return executerImplementation(individuCourant.lireImplementationConditionEmission(), individuCourant, null);
	}
	
	public static boolean executerImplementationReception(Individu individuCourant, Lemme lemmeEnReception) {
		return executerImplementation(individuCourant.lireImplementationConditionReception(), individuCourant, lemmeEnReception);
	}
	
	public static boolean executerImplementationMemorisation(Individu individuCourant, Lemme lemmeEnMemorisation) {
		return executerImplementation(individuCourant.lireImplementationConditionMemorisation(), individuCourant, lemmeEnMemorisation);
	}
	
	private static boolean executerImplementation(ImplementationCondition implementation, Individu individuCourant, Lemme lemmeCourant) {

		Condition condition = null;
		
		switch (implementation) {
			case CONDITION_PROBABILITE_UNIFORME:
				condition = conditionProbabiliteUniforme;
				break;
			case CONDITION_TOUJOURS_VERIFIEE:
				condition = conditionToujoursVerifiee;
				break;
			case CONDITION_JAMAIS_VERIFIEE:
				condition = conditionJamaisVerifiee;
				break;
		}
		
		return condition.estSatisfaite(individuCourant, lemmeCourant);
	}
	
	private final static ConditionProbabiliteUniforme conditionProbabiliteUniforme = new ConditionProbabiliteUniforme();
	private final static ConditionToujoursVerifiee conditionToujoursVerifiee = new ConditionToujoursVerifiee();
	private final static ConditionJamaisVerifiee conditionJamaisVerifiee = new ConditionJamaisVerifiee();
}
