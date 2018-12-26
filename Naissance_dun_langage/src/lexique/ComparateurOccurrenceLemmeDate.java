package lexique;

import java.util.Comparator;

public class ComparateurOccurrenceLemmeDate implements Comparator<OccurrenceLemme> {

	@Override
    public int compare(OccurrenceLemme occurrence1, OccurrenceLemme occurrence2) {
		if (occurrence1.lireDate().equals(occurrence2.lireDate())) {
			return 0;
		}
		else if (occurrence1.lireDate().estApres(occurrence2.lireDate())) {
			return 1;
		}
		else {
			return -1;
		}
    }
}
