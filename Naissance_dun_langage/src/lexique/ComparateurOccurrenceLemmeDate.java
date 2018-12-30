package lexique;

import java.util.Comparator;

public class ComparateurOccurrenceLemmeDate implements Comparator<OccurrenceLemme> {

	@Override
    public int compare(OccurrenceLemme occurrence1, OccurrenceLemme occurrence2) {
		if (occurrence1.getDate().equals(occurrence2.getDate())) {
			return 0;
		}
		else if (occurrence1.getDate().estApres(occurrence2.getDate())) {
			return 1;
		}
		else {
			return -1;
		}
    }
}
