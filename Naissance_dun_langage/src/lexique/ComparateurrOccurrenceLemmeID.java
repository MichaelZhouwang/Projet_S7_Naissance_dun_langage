package lexique;

import java.util.Comparator;

public class ComparateurrOccurrenceLemmeID implements Comparator<OccurrenceLemme> {

	@Override
    public int compare(OccurrenceLemme occurrence1, OccurrenceLemme occurrence2) {
        return Integer.compare(occurrence1.lireID(), occurrence2.lireID());
    }
}