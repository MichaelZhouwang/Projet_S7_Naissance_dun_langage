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

public class PorteeIndividu extends Portee {
	private Individu individu;
	
	public PorteeIndividu(Individu individu) {
		this.individu = individu;
	}
	
	@Override
	public String toString() {
		return individu.toString();
	}

	@Override
	public HashMap<Individu, Integer> obtenirDonneesDiagrammeCompositionLexique(Date date) {
		HashMap<Individu, Integer> repartition = new HashMap<Individu, Integer>();
		
		for (Individu individu : Systeme.obtenirIndividus()) {
			repartition.put(individu, 0);
		}

		for (Lemme lemme : individu.retrouverLexique(date)) {
			Individu _individu = lemme.lireInitiateur();
			repartition.put(_individu, repartition.get(_individu) + 1);
		}
		
		return repartition;
	}
	
	@Override
	public ArrayList<LemmeMemorise> obtenirListeLemmesMemorises(Date date) {
		ArrayList<OccurrenceLemme> listeOccurrences = new ArrayList<OccurrenceLemme>();
		HashMap<Lemme, LemmeMemorise> tableLemmesMemorises = new HashMap<Lemme, LemmeMemorise>();
		
		listeOccurrences.addAll(individu.obtenirTableOccurrencesLemmes().obtenirListeOccurrencesLemmes(Lemme.QUELCONQUE, TypeEvenement.MEMORISATION, IssueEvenement.SUCCES, Date.valeurInitiale, date));
		listeOccurrences.addAll(individu.obtenirTableOccurrencesLemmes().obtenirListeOccurrencesLemmes(Lemme.QUELCONQUE, TypeEvenement.ELIMINATION, IssueEvenement.SUCCES, Date.valeurInitiale, date));
		
		Collections.sort(listeOccurrences, new ComparateurOccurrenceLemmeDate());
		
		Lexique lexique = new Lexique();
		lexique.generer(individu.obtenirLexique().lireTailleMaximale(), individu.obtenirLexique().lireTailleInitiale(), individu);

		for (Lemme _lemme : lexique) {
			tableLemmesMemorises.put(_lemme, new LemmeMemorise(individu, individu, _lemme, Date.valeurInitiale));
		}
		
		for (OccurrenceLemme occurrence : listeOccurrences) {
			Lemme lemme = occurrence.lireLemme();
			switch (occurrence.lireTypeEvenement()) {
				case MEMORISATION:
					Individu initiateur = occurrence.lireLemme().lireInitiateur();
					Individu emetteur = occurrence.lireOccurrenceInitiatrice().lireOccurrenceInitiatrice().lireIndividu();
					Individu recepteur = occurrence.lireIndividu();
					Date dateMemorisation = occurrence.lireDate();
					tableLemmesMemorises.put(lemme, new LemmeMemorise(emetteur, recepteur, lemme, dateMemorisation));
					break;
				case ELIMINATION :
					tableLemmesMemorises.remove(lemme);
					break;
				default:
					break;
			}
		}

		return new ArrayList<LemmeMemorise>(tableLemmesMemorises.values());
	}

	@Override
	public ArrayList<Parametre> obtenirListeParametres() {
		ArrayList<Parametre> listeParametres = new ArrayList<Parametre>();

		listeParametres.add(new Parametre("TailleLexiqueInitiale", String.valueOf(individu.obtenirLexique().lireTailleInitiale())));
		listeParametres.add(new Parametre("TailleLexiqueMaximale", String.valueOf(individu.obtenirLexique().lireTailleMaximale())));
		listeParametres.add(new Parametre("ImplementationConditionEmission", String.valueOf(individu.lireImplementationConditionEmission())));
		listeParametres.add(new Parametre("ImplementationConditionReception", String.valueOf(individu.lireImplementationConditionReception())));
		listeParametres.add(new Parametre("ImplementationConditionMemorisation", String.valueOf(individu.lireImplementationConditionMemorisation())));
		listeParametres.add(new Parametre("ImplementationStrategieSelectionEmission", String.valueOf(individu.lireImplementationStrategieSelectionEmission())));
		listeParametres.add(new Parametre("ImplementationStrategieSelectionElimination", String.valueOf(individu.lireImplementationStrategieSelectionElimination())));
		listeParametres.add(new Parametre("ImplementationStrategieSuccession", String.valueOf(individu.lireImplementationStrategieSuccession())));
		
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

			for (Lemme lemme : individu.retrouverLexique(date)) {
				Individu _individu = lemme.lireInitiateur();
				repartitionDateCourante.put(_individu, repartitionDateCourante.get(_individu) + 1);
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
			}

			occurrences = individu.obtenirTableOccurrencesLemmes().obtenirListeOccurrencesLemmes(Lemme.QUELCONQUE, typeEvenement, IssueEvenement.QUELCONQUE, Date.valeurInitiale, date);
			
			for (OccurrenceLemme occurrenceLemme : occurrences) {
				donneesDateCourante.put(occurrenceLemme.lireLemme().lireInitiateur(), donneesDateCourante.get(occurrenceLemme.lireLemme().lireInitiateur()) + 1);
			}
		}
		
		return donnees;
	}
	
	@Override
	public ArrayList<OccurrenceLemme> obtenirListeOccurrences(Date date, TypeEvenement typeEvenement) {
		ArrayList<OccurrenceLemme> listeOccurrences = new ArrayList<OccurrenceLemme>();
		
		listeOccurrences = individu.obtenirTableOccurrencesLemmes().obtenirListeOccurrencesLemmesOrdonnee(Lemme.QUELCONQUE, typeEvenement, IssueEvenement.QUELCONQUE, Date.valeurInitiale, date);

		return listeOccurrences;
	}
}
