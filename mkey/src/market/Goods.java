package market;

import agent.Agent;
import agent.Firm;
import agent.Household;

public class Goods {
	public enum Good {
		LABOR(Household.class, 0, 0.25, 1, 0, 0),
		// GOODA(Firm.class, 0.75, 0.25, 20, 0.2, 500),
		GOODB(Firm.class, 0.75, 0.25, 30, 0.3, 400);
		// GOODC(Firm.class, 0.75, 0.25, 40, 0.4, 300),
		// GOODD(Firm.class, 0.75, 0.25, 50, 0.5, 200);
		/*
		 * SERVICES(Firm.class, 0.75, 0.25, 10, 0.5, 50), TRANSPORTATION(Firm.class,
		 * 0.75, 0.25, 10, 0.2, 200), CONSUMER_GOODS(Firm.class, 0.75, 0.25, 20, 0.4,
		 * 80);
		 */

		private final Class<? extends Agent> placeProduced;
		private double laborExponent;
		private double capitalExponent;
		private double productionMultiplier;
		private double consumptionExponent;
		private double consumptionMultiplier;

		Good(Class<? extends Agent> placeProduced, double laborExponent, double capitalExponent,
				double productionMultiplier, double consumptionExponent, double consumptionMultiplier) {
			this.placeProduced = placeProduced;
			this.laborExponent = laborExponent;
			this.capitalExponent = capitalExponent;
			this.productionMultiplier = productionMultiplier;
			this.consumptionExponent = consumptionExponent;
			this.consumptionMultiplier = consumptionMultiplier;
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

		public double getConsumptionExponent() {
			return consumptionExponent;
		}

		public double getConsumptionMultiplier() {
			return consumptionMultiplier;
		}
	}
}
