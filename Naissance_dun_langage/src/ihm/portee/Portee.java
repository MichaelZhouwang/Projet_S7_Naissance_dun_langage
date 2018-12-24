package ihm.portee;

import java.util.ArrayList;
import java.util.HashMap;

import beans.LemmeMemorise;
import beans.Parametre;
import evenement.enumeration.TypeEvenement;
import lexique.OccurrenceLemme;
import systeme.Individu;
import temps.Date;

public abstract class Portee {
	public abstract String toString();
	
	public abstract HashMap<Individu, Integer> obtenirDonneesDiagrammeCompositionLexique(Date date);
	
	public abstract HashMap<Date, HashMap<Individu, Integer>> obtenirDonneesDiagrammeEvolutionCompositionLexique(ArrayList<Date> datesRepere);
	
	public abstract HashMap<Date, HashMap<Individu, Integer>> obtenirDonneesDiagrammeEvolutionOccurrences(ArrayList<Date> datesRepere, TypeEvenement typeEvenement);
	
	public abstract ArrayList<Parametre> obtenirListeParametres();
	
	public abstract ArrayList<OccurrenceLemme> obtenirListeOccurrences(Date date, TypeEvenement typeEvenement);

	public abstract ArrayList<LemmeMemorise> obtenirListeLemmesMemorises(Date date);
}