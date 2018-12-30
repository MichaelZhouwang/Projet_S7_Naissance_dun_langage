package systeme.executeur;

import condition.modele.Condition;
import evenement.enumeration.IssueEvenement;
import evenement.enumeration.TypeEvenement;
import evenement.modele.Evenement;
import lexique.Lemme;
import lexique.OccurrenceLemme;
import strategie.modele.StrategieSelection;
import strategie.modele.StrategieSuccession;
import systeme.Individu;
import systeme.Systeme;
import systeme.Voisin;
import systeme.enumeration.TypeCritereArret;
import temps.Date;

public class ExecuteurEvenementsIndividu {
	private Individu individu;
	
	public ExecuteurEvenementsIndividu(Individu individu) {
		this.individu = individu;
	}
	
	public Evenement genererEvenementEmission(Evenement evenementInitiateur) {
		return new EvenementEmission(evenementInitiateur);
	}
	
	public Evenement genererEvenementReception(EvenementEmission evenementInitiateur) {
		return new EvenementReception(evenementInitiateur);
	}
	
	public Evenement genererEvenementMemorisation(EvenementReception evenementInitiateur) {
		return new EvenementMemorisation(evenementInitiateur);
	}
	
	public Evenement genererEvenementElimination(EvenementMemorisation evenementInitiateur) {
		return new EvenementElimination(evenementInitiateur);
	}
	
	private class EvenementEmission extends Evenement {

		public EvenementEmission(Evenement evenementInitiateur) {
			super(evenementInitiateur);
		}

		@Override
		public void declencher() throws Exception {
			Date date = Systeme.lireDateHorloge();
			Lemme lemmeEnEmission = Systeme.consommerLemmeCache(lireID());
			OccurrenceLemme occurrenceInitiatrice = Systeme.consommerOccurrenceCache(lireID());
			OccurrenceLemme occurrenceEmission = null;
			
			if (Condition.executerImplementationEmission(individu)) {
				occurrenceEmission = individu.nouvelleOccurrenceLemme(occurrenceInitiatrice, lemmeEnEmission, TypeEvenement.EMISSION, IssueEvenement.SUCCES, date);

				for (Voisin voisin : individu.obtenirVoisins()) {
					Evenement evenementReception = voisin.obtenirIndividu().obtenirEvenements().genererEvenementReception(this);
					
					Systeme.ajouterLemmeCache(evenementReception.lireID(), lemmeEnEmission);
					Systeme.ajouterOccurrenceCache(evenementReception.lireID(), occurrenceEmission);
					
					Systeme.ajouterDernierEvenementEnDate(
						evenementReception,
						Systeme.lireDateHorloge().plusDelais(voisin.lireDelais())
					);
				}
			}
			else {
				occurrenceEmission = individu.nouvelleOccurrenceLemme(occurrenceInitiatrice, lemmeEnEmission, TypeEvenement.EMISSION, IssueEvenement.ECHEC, date);
			}
			
			Voisin successeur = StrategieSuccession.executerImplementation(individu);
			Lemme lemmeAEmettre = StrategieSelection.executerImplementationEmission(successeur.obtenirIndividu());

			Evenement evenementEmission = successeur.obtenirIndividu().obtenirEvenements().genererEvenementEmission(this);
			Systeme.ajouterLemmeCache(evenementEmission.lireID(), lemmeAEmettre);
			Systeme.ajouterOccurrenceCache(evenementEmission.lireID(), occurrenceEmission);
			
			Systeme.ajouterDernierEvenementEnDate(
				evenementEmission,
				Systeme.lireDateHorloge().plusDelais(successeur.lireDelais())
			);
			
			Systeme.declencherProchainEvenement();
		}
	}
	
	public class EvenementReception extends Evenement {

		public EvenementReception(EvenementEmission evenementInitiateur) {
			super(evenementInitiateur);
		}

		@Override
		public void declencher() throws Exception {
			Date date = Systeme.lireDateHorloge();
			Lemme lemmeEnReception = Systeme.consommerLemmeCache(lireID());
			OccurrenceLemme occurrenceInitiatrice = Systeme.consommerOccurrenceCache(lireID());
			OccurrenceLemme occurrenceReception = null;
			
			if (Condition.executerImplementationReception(individu, lemmeEnReception)) {
				occurrenceReception = individu.nouvelleOccurrenceLemme(occurrenceInitiatrice, lemmeEnReception, TypeEvenement.RECEPTION, IssueEvenement.SUCCES, date);

				if (!individu.connaitLemme(lemmeEnReception)) {
					Evenement evenementMemorisation = genererEvenementMemorisation(this);
					
					Systeme.ajouterLemmeCache(evenementMemorisation.lireID(), lemmeEnReception);
					Systeme.ajouterOccurrenceCache(evenementMemorisation.lireID(), occurrenceReception);
					
					Systeme.ajouterPremierEvenementEnDate(
						evenementMemorisation,
						Systeme.lireDateHorloge()
					);
				}
			}
			else {
				occurrenceReception = individu.nouvelleOccurrenceLemme(occurrenceInitiatrice, lemmeEnReception, TypeEvenement.RECEPTION, IssueEvenement.ECHEC, date);
			}
			Systeme.declencherProchainEvenement();
		}
	}
	
	public class EvenementMemorisation extends Evenement {

		public EvenementMemorisation(EvenementReception evenementInitiateur) {
			super(evenementInitiateur);
		}

		@Override
		public void declencher() throws Exception {
			Date date = Systeme.lireDateHorloge();
			Lemme lemmeEnMemorisation = Systeme.consommerLemmeCache(lireID());
			OccurrenceLemme occurrenceInitiatrice = Systeme.consommerOccurrenceCache(lireID());
			OccurrenceLemme occurrenceMemorisation = null;
			
			if (Condition.executerImplementationMemorisation(individu, lemmeEnMemorisation)) {
				
				occurrenceMemorisation = individu.nouvelleOccurrenceLemme(occurrenceInitiatrice, lemmeEnMemorisation, TypeEvenement.MEMORISATION, IssueEvenement.SUCCES, date);
				
				if (!individu.aLexiquePlein()) {
					individu.memoriserLemme(lemmeEnMemorisation);
				}
				else {
					Evenement evenementElimination = genererEvenementElimination(this);

					Systeme.ajouterLemmeCache(evenementElimination.lireID(), lemmeEnMemorisation);
					Systeme.ajouterOccurrenceCache(evenementElimination.lireID(), occurrenceMemorisation);
					
					Systeme.ajouterPremierEvenementEnDate(
						evenementElimination,
						Systeme.lireDateHorloge()
					);
				}
				
				if (individu.aLexiquePlein()) {
					if (Systeme.lireTypeCritereArret() == TypeCritereArret.PREMIER_LEXIQUE_PLEIN) {
						Systeme.declencherEvenementFinal(this);
					}
					else if (Systeme.lireTypeCritereArret() == TypeCritereArret.TOUS_LEXIQUES_PLEINS) {
						boolean tousLexiquesPleins = true;
						
						for (Individu individu : Systeme.obtenirIndividus()) {
							tousLexiquesPleins = tousLexiquesPleins && individu.aLexiquePlein();
						}
						
						if (tousLexiquesPleins) {
							Systeme.declencherEvenementFinal(this);
						}
					}
				}
			}
			else {
				occurrenceMemorisation = individu.nouvelleOccurrenceLemme(occurrenceInitiatrice, lemmeEnMemorisation, TypeEvenement.MEMORISATION, IssueEvenement.ECHEC, date);
			}
			
			Systeme.declencherProchainEvenement();
		}
	}
	
	private class EvenementElimination extends Evenement {

		public EvenementElimination(EvenementMemorisation evenementInitiateur) {
			super(evenementInitiateur);
		}

		@Override
		public void declencher() throws Exception {
			Date date = Systeme.lireDateHorloge();
			Lemme lemmeEnMemorisation = Systeme.consommerLemmeCache(lireID());
			OccurrenceLemme occurrenceInitiatrice = Systeme.consommerOccurrenceCache(lireID());
			
			Lemme lemmeEnRemplacement = StrategieSelection.executerImplementationElimination(individu, lemmeEnMemorisation);
			
			individu.nouvelleOccurrenceLemme(occurrenceInitiatrice, lemmeEnRemplacement, TypeEvenement.ELIMINATION, IssueEvenement.SUCCES, date);
			individu.remplacerLemme(lemmeEnRemplacement, lemmeEnMemorisation);

			Systeme.declencherProchainEvenement();
		}
	}
}
