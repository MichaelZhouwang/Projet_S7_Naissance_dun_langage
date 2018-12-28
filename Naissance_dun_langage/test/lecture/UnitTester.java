package lecture;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import condition.enumeration.ImplementationCondition;
import strategie.enumeration.ImplementationStrategieSelection;
import strategie.enumeration.ImplementationStrategieSuccession;
import systeme.Individu;
import systeme.SystemeIntermediaire;
import systeme.enumeration.TypeCritereArret;

public class UnitTester {

	private static String configExample = "./config/Example_config.xml";
	private static LireFichier fichierExample;

	private static String cheminValide = "./config/Example_config.xml";
	private static String cheminNonValide = "";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		try {
			fichierExample = new LireFichier(configExample);
			Assert.assertNotNull(fichierExample);
		} catch (Exception e) {
			Assert.fail("No exception to be thrown");
		}

	}

	@Test
	public void testFichier() {
		try {
			Assert.assertNotNull(new LireFichier(cheminValide));
		} catch (Exception e) {
			Assert.fail("No exception to be thrown");
		}

		try {
			Assert.assertNotNull(new LireFichier(cheminNonValide));
			Assert.fail("Expected exception to be thrown");
		} catch (Exception e) {
			Assert.assertEquals(e.getMessage(), "Chemin non valide");
		}
	}

	@Test
	public void testSystemeIntermediaire() {

		try {
			SystemeIntermediaire systemeIntermediaire = fichierExample.lireSyeteme();
			Assert.assertNotNull(systemeIntermediaire);

			Assert.assertEquals(4, systemeIntermediaire.getNombreIndividus());

		} catch (Exception e) {
			Assert.fail("No exception to be thrown");
		}
	}

	@Test
	public void testConditions() {

		try {
			HashMap<String, ImplementationCondition> paramCondition = fichierExample.lireConditions();

			Assert.assertNotNull(paramCondition);

			Assert.assertEquals(ImplementationCondition.CONDITION_TOUJOURS_VERIFIEE, paramCondition.get("EMISSION"));
			Assert.assertEquals(ImplementationCondition.CONDITION_TOUJOURS_VERIFIEE, paramCondition.get("RECEPTION"));
			Assert.assertEquals(ImplementationCondition.CONDITION_PROBABILITE_UNIFORME,
					paramCondition.get("MEMORISATION"));

		} catch (Exception e) {
			Assert.fail("No exception to be thrown");
		}
	}

	@Test
	public void testStrategiesEmissionSelection() {

		try {

			Assert.assertEquals(ImplementationStrategieSelection.SELECTION_ALEATOIRE,
					fichierExample.lireStrategiesEmissionSelection());

		} catch (Exception e) {
			Assert.fail("No exception to be thrown");
		}
	}

	@Test
	public void testStrategiesEliminationSelection() {

		try {

			Assert.assertEquals(ImplementationStrategieSelection.SELECTION_MOINS_EMIS,
					fichierExample.lireStrategiesEliminationSelection());

		} catch (Exception e) {
			Assert.fail("No exception to be thrown");
		}

	}

	@Test
	public void testStrategiesSuccession() {

		try {

			Assert.assertEquals(ImplementationStrategieSuccession.SUCCESSION_VOISIN_ALEATOIRE,
					fichierExample.lireStrategiesSuccession());

		} catch (Exception e) {
			Assert.fail("No exception to be thrown");
		}

	}

	@Test
	public void testCritereArret() {
		try {

			Assert.assertNotNull(fichierExample.lireCritereArret());
			Assert.assertEquals(TypeCritereArret.DATE, fichierExample.lireCritereArret().lireTypeCritere());
			Assert.assertEquals(20, fichierExample.lireCritereArret().lireObjectif());

		} catch (Exception e) {
			Assert.fail("No exception to be thrown");
		}
	}

	@Test
	public void testIndividusSpecifiques() {
		try {
			HashMap<Integer, Individu> individusSpec = fichierExample.lireIndividusSpecifiques();
			Assert.assertNotNull(individusSpec);
			Assert.assertEquals(1, individusSpec.size());

			Individu individuSpec = individusSpec.get(1);
			Assert.assertEquals(1, individuSpec.getID());
			Assert.assertEquals(ImplementationCondition.CONDITION_TOUJOURS_VERIFIEE,
					individuSpec.getImplementationConditionEmission());
			Assert.assertEquals(ImplementationCondition.CONDITION_PROBABILITE_UNIFORME,
					individuSpec.getImplementationConditionMemorisation());
			Assert.assertEquals(ImplementationCondition.CONDITION_TOUJOURS_VERIFIEE,
					individuSpec.getImplementationConditionReception());
			Assert.assertEquals(ImplementationStrategieSelection.SELECTION_MOINS_EMIS,
					individuSpec.getImplementationStrategieSelectionElimination());
			Assert.assertEquals(ImplementationStrategieSelection.SELECTION_ALEATOIRE,
					individuSpec.getImplementationStrategieSelectionEmission());
			Assert.assertEquals(ImplementationStrategieSuccession.SUCCESSION_VOISIN_ALEATOIRE,
					individuSpec.getImplementationStrategieSuccession());
			Assert.assertEquals(50, individuSpec.getLexique().lireTailleInitiale());
			Assert.assertEquals(100, individuSpec.getLexique().lireTailleMaximale());

			SystemeIntermediaire systemeIntermediaire = fichierExample.lireSyeteme();
			Assert.assertNotNull(systemeIntermediaire);

			ArrayList<Individu> individus = systemeIntermediaire.genererIndividus(individusSpec);
			Assert.assertEquals(4, individus.size());
			for (Individu individu : individus) {
				if (individu.getID() == 1) {
					Assert.assertEquals(ImplementationCondition.CONDITION_TOUJOURS_VERIFIEE,
							individu.getImplementationConditionEmission());
					Assert.assertEquals(ImplementationCondition.CONDITION_PROBABILITE_UNIFORME,
							individu.getImplementationConditionMemorisation());
					Assert.assertEquals(ImplementationCondition.CONDITION_TOUJOURS_VERIFIEE,
							individu.getImplementationConditionReception());
					Assert.assertEquals(ImplementationStrategieSelection.SELECTION_MOINS_EMIS,
							individu.getImplementationStrategieSelectionElimination());
					Assert.assertEquals(ImplementationStrategieSelection.SELECTION_ALEATOIRE,
							individu.getImplementationStrategieSelectionEmission());
					Assert.assertEquals(ImplementationStrategieSuccession.SUCCESSION_VOISIN_ALEATOIRE,
							individu.getImplementationStrategieSuccession());
					Assert.assertEquals(50, individu.getLexique().lireTailleInitiale());
					Assert.assertEquals(100, individu.getLexique().lireTailleMaximale());
				}
			}

		} catch (Exception e) {
			Assert.fail("No exception to be thrown");
		}
	}

}
