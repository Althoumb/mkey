package market;

import agent.Agent;
import agent.Firm;
import agent.Household;

public class Goods {
	public enum Good {
		LABOR(Household.class, 0, 0.25, 1, 0, 0), GOODA(Firm.class, 0.75, 0.25, 10, 0.5, 0.5),
		GOODB(Firm.class, 0.75, 0.25, 10, 0.5, 0.5);

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
