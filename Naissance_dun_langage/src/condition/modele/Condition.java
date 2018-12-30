package condition.modele;

import condition.enumeration.ImplementationCondition;
import condition.implementation.ConditionJamaisVerifiee;
import condition.implementation.ConditionProbabiliteUniforme;
import condition.implementation.ConditionToujoursVerifiee;
import exception.ConditionException;
import lexique.Lemme;
import systeme.Individu;

public abstract class Condition {
	public abstract boolean estSatisfaite(Individu individuCourant, Lemme lemmeCourant);
	
	public static boolean executerImplementationEmission(Individu individuCourant) throws ConditionException {
		return executerImplementation(individuCourant.lireImplConditionEmission(), individuCourant, null);
	}
	
	public static boolean executerImplementationReception(Individu individuCourant, Lemme lemmeEnReception) throws ConditionException {
		return executerImplementation(individuCourant.lireImplConditionReception(), individuCourant, lemmeEnReception);
	}
	
	public static boolean executerImplementationMemorisation(Individu individuCourant, Lemme lemmeEnMemorisation) throws ConditionException {
		return executerImplementation(individuCourant.lireImplConditionMemorisation(), individuCourant, lemmeEnMemorisation);
	}
	
	private static boolean executerImplementation(ImplementationCondition implementation, Individu individuCourant, Lemme lemmeCourant) throws ConditionException {

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
			default:
				throw new ConditionException("L'implementation de condition '" + implementation + "' n'est associee a aucune classe concrete (switch incomplet)");
		}
		
		try {
			return condition.estSatisfaite(individuCourant, lemmeCourant);
		}
		catch (Exception exception) {
			throw new ConditionException("Condition '" + implementation + "' a provoquee une exception lors de son execution (contexte illegal)");
		}
	}
	
	private final static ConditionProbabiliteUniforme conditionProbabiliteUniforme = new ConditionProbabiliteUniforme();
	private final static ConditionToujoursVerifiee conditionToujoursVerifiee = new ConditionToujoursVerifiee();
	private final static ConditionJamaisVerifiee conditionJamaisVerifiee = new ConditionJamaisVerifiee();
}
