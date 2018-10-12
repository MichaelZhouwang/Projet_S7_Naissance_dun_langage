package architecture;

import java.util.ArrayList;
import conditions.Condition;
import evenements.Evenement;

public class Systeme {
	private Horloge horloge;
	private Echeancier echeancier;
	private ArrayList<Individu> individus;

	private Condition conditionArret;

	public Systeme() {
		echeancier = new Echeancier();
		horloge = new Horloge();
		individus = new ArrayList<Individu>();
		conditionArret = new ConditionArretDate();
	}

	public void generer() {
		int nombreIndividus = 4;

		for (int i = 0; i < nombreIndividus; i++) {
			individus.add(new Individu());
		}

		for (Individu individu : individus) {
			try {
				individu.genererLexique(5, 10);
			} catch (Exception e) {
				e.printStackTrace();
			}

			for (Individu voisin : individus) {
				if (voisin != individu) {
					individu.ajouterVoisin(voisin);
				}
			}
		}
	}

	public void generer2(Graphe graphe) {
		individus = graphe.lireGraphe();
	}

	public void routine() {
		while (!conditionArret.estSatisfaite()) {
			for (Individu individu : individus) {

				horloge.avancerDate();

				EvenementEmission evenement = new EvenementEmission(horloge.dateActuelle(), individu);
				echeancier.ajouterEvenement(evenement);

				while (!echeancier.estVide() && !conditionArret.estSatisfaite()) {
					echeancier.declencherDernierEvenement();
				}

				if (conditionArret.estSatisfaite()) {
					break;
				}
			}
		}

		for (Evenement evenement : echeancier.obtenirHistorique()) {
			System.out.println(evenement);
		}
		System.out.println();
		for (Individu individu : individus) {
			System.out.println(individu);
		}
	}

	private class EvenementEmission extends Evenement {

		public EvenementEmission(int dateEmission, Individu emetteur) {
			super(dateEmission, emetteur);
		}

		public void genererDescriptionSucces(Lemme lemmeEnEmission) {
			decrire("L'individu " + lireActeur().lireLettre() + " a emis le lemme " + lemmeEnEmission + " a la date "
					+ lireDate());
		}

		public void genererDescriptionEchec() {
			decrire("L'individu " + lireActeur().lireLettre() + " n'a pas emis de lemme a la date " + lireDate());
		}

		@Override
		public void declencher() {
			Individu emetteur = lireActeur();
			int dateEmission = lireDate();

			if (emetteur.peutEmettre()) {
				Lemme lemmeEnEmission = emetteur.lireAlgorithmeSelection().determinerLemme();

				emetteur.ajouterOccurrenceLemmeEmis(dateEmission, lemmeEnEmission);
				genererDescriptionSucces(lemmeEnEmission);

				for (Individu voisin : emetteur.lireVoisins()) {
					EvenementReception evenement = new EvenementReception(horloge.dateActuelle(), voisin,
							lemmeEnEmission);
					echeancier.ajouterEvenement(evenement);
				}
			} else {
				genererDescriptionEchec();
			}
		}
	}

	private class EvenementReception extends Evenement {
		private Lemme lemmeEnReception;

		public EvenementReception(int date, Individu recepteur, Lemme lemmeEnReception) {
			super(date, recepteur);
			this.lemmeEnReception = lemmeEnReception;
		}

		public void genererDescriptionSucces(Lemme lemmeEnEmission) {
			decrire("\tL'individu " + lireActeur().lireLettre() + " a recu le lemme " + lemmeEnReception + " a la date "
					+ lireDate());
		}

		public void genererDescriptionEchec(Lemme lemmeEnEmission) {
			decrire("\tL'individu " + lireActeur().lireLettre() + " n'a pas recu le lemme " + lemmeEnReception
					+ " a la date " + lireDate());
		}

		@Override
		public void declencher() {
			Individu recepteur = lireActeur();
			int dateReception = lireDate();

			if (recepteur.peutRecevoir()) {
				recepteur.ajouterOccurrenceLemmeRecu(dateReception, lemmeEnReception);
				genererDescriptionSucces(lemmeEnReception);

				EvenementMemorisation evenement = new EvenementMemorisation(horloge.dateActuelle(), recepteur,
						lemmeEnReception);
				echeancier.ajouterEvenement(evenement);
			} else {
				genererDescriptionEchec(lemmeEnReception);
			}
		}
	}

	private class EvenementMemorisation extends Evenement {

		private Lemme lemmeEnMemorisation;

		public EvenementMemorisation(int date, Individu recepteur, Lemme lemmeEnMemorisation) {
			super(date, recepteur);
			this.lemmeEnMemorisation = lemmeEnMemorisation;
		}

		public void genererDescriptionSuccesAjout(Lemme lemmeEnMemorisation) {
			decrire("\t\tL'individu " + lireActeur().lireLettre() + " a reussi a memoriser le lemme "
					+ lemmeEnMemorisation + " a la date " + lireDate());
		}

		public void genererDescriptionSuccesRemplacement(Lemme lemmeEnMemorisation, Lemme lemmeRemplace) {
			decrire("\t\tL'individu " + lireActeur().lireLettre() + " a reussi a memoriser le lemme "
					+ lemmeEnMemorisation + " a la place du lemme " + lemmeRemplace + " a la date " + lireDate());
		}

		public void genererDescriptionEchec(Lemme lemmeEnMemorisation) {
			decrire("\t\tL'individu " + lireActeur().lireLettre() + " n'a pas reussi a memoriser le lemme "
					+ lemmeEnMemorisation + " a la date " + lireDate());
		}

		@Override
		public void declencher() {
			Individu recepteur = lireActeur();

			if (recepteur.peutMemoriser(lemmeEnMemorisation)) {
				recepteur.ajouterOccurrenceLemmeMemorise(horloge.dateActuelle(), lemmeEnMemorisation);

				ArrayList<Lemme> lexique = recepteur.lireLexique();

				if (lexique.indexOf(lemmeEnMemorisation) < 0) {
					if (lexique.size() < recepteur.lireTailleMaximaleLexique()) {
						lexique.add(lemmeEnMemorisation);
						genererDescriptionSuccesAjout(lemmeEnMemorisation);
					} else {
						Lemme lemmeEnRemplacement = recepteur.lireAlgorithmeElimination().determinerLemme();

						if (lexique.remove(lemmeEnRemplacement)) {
							lexique.add(lemmeEnMemorisation);
							genererDescriptionSuccesRemplacement(lemmeEnMemorisation, lemmeEnRemplacement);
						}
					}
				} else {
					genererDescriptionSuccesAjout(lemmeEnMemorisation);
				}
			} else {
				genererDescriptionEchec(lemmeEnMemorisation);
			}
		}
	}

	private class ConditionArretDate extends Condition {

		@Override
		public boolean estSatisfaite() {
			return horloge.aDepasseDate(500);
		}

	}
}
