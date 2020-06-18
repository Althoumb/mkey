package market;

import agent.Agent;
import agent.Firm;
import agent.Household;

public class Goods {
	public enum Good {
		LABOR(Household.class, 0, 0.25, 1), GOODS(Firm.class, 0.75, 0.25, 1);

		private final Class<? extends Agent> placeProduced;
		private double laborExponent;
		private double capitalExponent;
		private double productionMultiplier;

		Good(Class<? extends Agent> placeProduced, double laborExponent, double capitalExponent,
				double productionMultiplier) {
			this.placeProduced = placeProduced;
			this.laborExponent = laborExponent;
			this.capitalExponent = capitalExponent;
			this.productionMultiplier = productionMultiplier;
		}

		public Class<? extends Agent> getPlaceProduced() {
			return placeProduced;
		}

		public double getLaborExponent() {
			return laborExponent;
		}

		public double getCapitalExponent() {
			return capitalExponent;
		}

		public double getProductionMultiplier() {
			return productionMultiplier;
		}
	}
}
