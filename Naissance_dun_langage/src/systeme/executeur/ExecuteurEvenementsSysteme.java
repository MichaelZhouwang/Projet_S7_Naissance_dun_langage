package systeme.executeur;

import evenement.modele.Evenement;
import lexique.Lemme;
import strategie.modele.StrategieSelection;
import systeme.Individu;
import systeme.Systeme;
import temps.Date;

public class ExecuteurEvenementsSysteme {

	public Evenement genererEvenementInitial() {
		return new EvenementInitial(null);
	}
	
	public Evenement genererEvenementFinal(Evenement evenementInitiateur) {
		return new EvenementFinal(evenementInitiateur);
	}
	
	private static class EvenementInitial extends Evenement {

		public EvenementInitial(Evenement evenementInitiateur) {
			super(evenementInitiateur);
		}

		@Override
		public void declencher() throws Exception {
			Individu individu = Systeme.obtenirIndividus().get(0);
			
			Lemme lemmeEnEmission = StrategieSelection.executerImplementationEmission(individu);
			Evenement evenementEmission = individu.obtenirEvenements().genererEvenementEmission(this);
			
			Systeme.ajouterLemmeCache(evenementEmission.lireID(), lemmeEnEmission);
			Systeme.ajouterOccurrenceCache(evenementEmission.lireID(), null);
			
			Systeme.ajouterPremierEvenementEnDate(
				evenementEmission,
				Date.dateSuivante(Systeme.lireDateHorloge())
			);

			Systeme.declencherProchainEvenement();
		}
	}
	
	private static class EvenementFinal extends Evenement {

		public EvenementFinal(Evenement evenementInitiateur) {
			super(evenementInitiateur);
		}

		@Override
		public void declencher() {
			// Fin de la simulation
		}
	}
}
