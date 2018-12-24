package ihm.portee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import beans.LemmeMemorise;
import beans.Parametre;
import evenement.enumeration.IssueEvenement;
import evenement.enumeration.TypeEvenement;
import lexique.Lemme;
import lexique.Lexique;
import lexique.OccurrenceLemme;
import lexique.ComparateurOccurrenceLemmeDate;
import systeme.Individu;
import systeme.Systeme;
import temps.Date;

public class PorteeSysteme extends Portee {

	@Override
	public String toString() {
		return "Système";
	}

	@Override
	public HashMap<Individu, Integer> obtenirDonneesDiagrammeCompositionLexique(Date date) {
		HashMap<Individu, Integer> repartitions = new HashMap<Individu, Integer>();
		
		for (Individu individu : Systeme.obtenirIndividus()) {
			repartitions.put(individu, 0);
		}
		
		for (Individu _individu : Systeme.obtenirIndividus()) {
			for (Lemme lemme : _individu.retrouverLexique(date)) {
				Individu individu = lemme.lireInitiateur();
				repartitions.put(individu, repartitions.get(individu) + 1);
			}
		}
		
		return repartitions;
	}
	
	@Override
	public ArrayList<LemmeMemorise> obtenirListeLemmesMemorises(Date date) {
		ArrayList<OccurrenceLemme> listeOccurrences = new ArrayList<OccurrenceLemme>();
		ArrayList<LemmeMemorise> listeLemmesMemorises = new ArrayList<LemmeMemorise>();
		HashMap<Individu, HashMap<Lemme, LemmeMemorise>> tableLemmesMemorises = new HashMap<Individu, HashMap<Lemme, LemmeMemorise>>();
		
		for (Individu individu : Systeme.obtenirIndividus()) {
			listeOccurrences.addAll(individu.obtenirTableOccurrencesLemmes().obtenirListeOccurrencesLemmes(Lemme.QUELCONQUE, TypeEvenement.MEMORISATION, IssueEvenement.SUCCES, Date.valeurInitiale, date));
			listeOccurrences.addAll(individu.obtenirTableOccurrencesLemmes().obtenirListeOccurrencesLemmes(Lemme.QUELCONQUE, TypeEvenement.ELIMINATION, IssueEvenement.SUCCES, Date.valeurInitiale, date));
		
			Lexique lexique = new Lexique();
			lexique.generer(individu.obtenirLexique().lireTailleMaximale(), individu.obtenirLexique().lireTailleInitiale(), individu);

			tableLemmesMemorises.put(individu, new HashMap<Lemme, LemmeMemorise>());
			for (Lemme lemme : lexique) {
				tableLemmesMemorises.get(individu).put(lemme, new LemmeMemorise(individu, individu, lemme, Date.valeurInitiale));
			}
		}
		
		Collections.sort(listeOccurrences, new ComparateurOccurrenceLemmeDate());

		for (OccurrenceLemme occurrence : listeOccurrences) {
			Lemme lemme = occurrence.lireLemme();
			Individu recepteur = occurrence.lireIndividu();
			switch (occurrence.lireTypeEvenement()) {
				case MEMORISATION :
					Individu initiateur = occurrence.lireLemme().lireInitiateur();
					Individu emetteur = occurrence.lireOccurrenceInitiatrice().lireOccurrenceInitiatrice().lireIndividu();
					Date dateMemorisation = occurrence.lireDate();
					tableLemmesMemorises.get(recepteur).put(lemme, new LemmeMemorise(emetteur, recepteur, lemme, dateMemorisation));
					break;
				case ELIMINATION :
					tableLemmesMemorises.get(recepteur).remove(lemme);
					break;
				default:
					break;
			}
		}
		
		for (Individu individu : Systeme.obtenirIndividus()) {	
			listeLemmesMemorises.addAll(tableLemmesMemorises.get(individu).values());
		}
		
		return listeLemmesMemorises;
	}

	@Override
	public ArrayList<Parametre> obtenirListeParametres() {
		ArrayList<Parametre> listeParametres = new ArrayList<Parametre>();
		
		listeParametres.add(new Parametre("NombreIndividus", String.valueOf(Systeme.lireNombreIndividus())));
		listeParametres.add(new Parametre("TailleLexiqueInitialeParDefaut", String.valueOf(Systeme.lireTailleInitialeLexiqueParDefaut())));
		listeParametres.add(new Parametre("TailleLexiqueMaximaleParDefaut", String.valueOf(Systeme.lireTailleMaximaleLexiqueParDefaut())));
		listeParametres.add(new Parametre("ImplementationConditionEmissionParDefaut", String.valueOf(Systeme.lireImplementationConditionEmissionParDefaut())));
		listeParametres.add(new Parametre("ImplementationConditionReceptionParDefaut", String.valueOf(Systeme.lireImplementationConditionReceptionParDefaut())));
		listeParametres.add(new Parametre("ImplementationConditionMemorisationParDefaut", String.valueOf(Systeme.lireImplementationConditionMemorisationParDefaut())));
		listeParametres.add(new Parametre("ImplementationStrategieSelectionEmissionParDefaut", String.valueOf(Systeme.lireImplementationStrategieSelectionEmissionParDefaut())));
		listeParametres.add(new Parametre("ImplementationStrategieSelectionEliminationParDefaut", String.valueOf(Systeme.lireImplementationStrategieSelectionEliminationParDefaut())));
		listeParametres.add(new Parametre("ImplementationStrategieSuccessionParDefaut", String.valueOf(Systeme.lireImplementationStrategieSuccessionParDefaut())));
		listeParametres.add(new Parametre("TypeCritereArret", String.valueOf(Systeme.lireTypeCritereArret())));		
		listeParametres.add(new Parametre("ObjectifCritereArret", String.valueOf(Systeme.lireObjectifCritereArret())));
		
		return listeParametres;
	}

	@Override
	public HashMap<Date, HashMap<Individu, Integer>> obtenirDonneesDiagrammeEvolutionCompositionLexique(ArrayList<Date> datesRepere) {
		HashMap<Date, HashMap<Individu, Integer>> repartitions = new HashMap<Date, HashMap<Individu, Integer>>();
		HashMap<Individu, Integer> repartitionDateCourante = null;
		
		for (Date date : datesRepere) {
			repartitions.put(date, new HashMap<Individu, Integer>());
			repartitionDateCourante = repartitions.get(date);
			
			for (Individu individu : Systeme.obtenirIndividus()) {
				repartitionDateCourante.put(individu, 0);
			}
			
			for (Individu individu : Systeme.obtenirIndividus()) {
				for (Lemme lemme : individu.retrouverLexique(date)) {
					Individu _individu = lemme.lireInitiateur();
					repartitionDateCourante.put(_individu, repartitionDateCourante.get(_individu) + 1);
				}
			}
		}
		
		return repartitions;
	}

	@Override
	public HashMap<Date, HashMap<Individu, Integer>> obtenirDonneesDiagrammeEvolutionOccurrences(ArrayList<Date> datesRepere, TypeEvenement typeEvenement) {
		ArrayList<OccurrenceLemme> occurrences = new ArrayList<OccurrenceLemme>();
		HashMap<Date, HashMap<Individu, Integer>> donnees = new HashMap<Date, HashMap<Individu, Integer>>();
		
		for (Date date : datesRepere) {
			donnees.put(date, new HashMap<Individu, Integer>());
			HashMap<Individu, Integer> donneesDateCourante = donnees.get(date);
			
			for (Individu individu : Systeme.obtenirIndividus()) {
				donneesDateCourante.put(individu, 0);
				occurrences.addAll(individu.obtenirTableOccurrencesLemmes().obtenirListeOccurrencesLemmes(Lemme.QUELCONQUE, typeEvenement, IssueEvenement.QUELCONQUE, Date.valeurInitiale, date));
			}
			for (OccurrenceLemme occurrenceLemme : occurrences) {
				donneesDateCourante.put(occurrenceLemme.lireLemme().lireInitiateur(), donneesDateCourante.get(occurrenceLemme.lireLemme().lireInitiateur()) + 1);
			}
		}
		
		return donnees;
	}
	
	@Override
	public ArrayList<OccurrenceLemme> obtenirListeOccurrences(Date date, TypeEvenement typeEvenement) {
		ArrayList<OccurrenceLemme> listeOccurrences = new ArrayList<OccurrenceLemme>();
		
		for (Individu individu : Systeme.obtenirIndividus()) {
			listeOccurrences.addAll(individu.obtenirTableOccurrencesLemmes().obtenirListeOccurrencesLemmesOrdonnee(Lemme.QUELCONQUE, typeEvenement, IssueEvenement.QUELCONQUE, Date.valeurInitiale, date));
		}

		Collections.sort(listeOccurrences, new ComparateurOccurrenceLemmeDate());
		
		return listeOccurrences;
	}
}
