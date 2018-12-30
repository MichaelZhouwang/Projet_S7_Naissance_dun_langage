package ihm.portee;

import java.util.ArrayList;
import java.util.HashMap;

import evenement.enumeration.IssueEvenement;
import evenement.enumeration.TypeEvenement;
import ihm.bean.LemmeMemorise;
import ihm.bean.Parametre;
import lexique.OccurrenceLemme;
import systeme.Individu;
import temps.Date;

public abstract class Portee {
	public abstract String toString();
	
	public abstract HashMap<Individu, Integer> obtenirDonneesDiagrammeCompositionLexique(Date date);
	
	public abstract HashMap<Date, HashMap<Individu, Integer>> obtenirDonneesDiagrammeEvolutionCompositionLexique(ArrayList<Date> datesRepere);
	
	public abstract HashMap<Date, HashMap<Individu, Integer>> obtenirDonneesDiagrammeEvolutionOccurrences(ArrayList<Date> datesRepere, TypeEvenement typeEvenement, IssueEvenement issueEvenement);
	
	public abstract ArrayList<Parametre> obtenirListeParametres();
	
	public abstract ArrayList<OccurrenceLemme> obtenirListeOccurrences(Date date, TypeEvenement typeEvenement, IssueEvenement issueEvenement);

	public abstract ArrayList<LemmeMemorise> obtenirListeLemmesMemorises(Date date);
}