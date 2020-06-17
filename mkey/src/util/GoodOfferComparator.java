package util;

import java.util.Comparator;

public class GoodOfferComparator implements Comparator<Pair<Double, ?>> { // this should be fine for now (6/9/2020)
	@Override
	public int compare(Pair<Double, ?> pair1, Pair<Double, ?> pair2) {
		if (pair1.getL() > pair2.getL()) {
			return -1;
		} else if (pair1.getL() < pair2.getL()) {
			return 1;
		} else {
			return 0;
		}
	}
}