package market;

import agent.Agent;
import agent.Firm;
import agent.Household;

public class Goods {
	public enum Good {
		LABOR(Household.class), GOODS(Firm.class);

		private final Class<? extends Agent> placeProduced;

		Good(Class<? extends Agent> placeProduced) {
			this.placeProduced = placeProduced;
		}

		public Class<? extends Agent> getPlaceProduced() {
			return placeProduced;
		}
	}
}
