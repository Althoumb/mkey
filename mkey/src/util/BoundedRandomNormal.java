package util;

import java.util.Random;

public class BoundedRandomNormal {
	static Random rand = new Random();

	public static double getBoundedRandomNormal(double lowerBound, double upperBound, double mean, double standardDev) {
		double randNormal = 0;
		boolean complete = false;
		while (!complete) {
			randNormal = rand.nextGaussian() * standardDev + mean;
			if ((randNormal >= lowerBound) && (randNormal <= upperBound)) {
				complete = true;
			}
		}
		return randNormal;
	}
}
