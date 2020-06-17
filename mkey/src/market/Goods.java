package market;

import agent.Agent;
import agent.Firm;
import agent.Household;

public class Goods {
	public enum Good {
		LABOR(LaborMarket.class, Household.class), GOODS(GoodMarket.class, Firm.class);

		private final Class<? extends Market> placeSold;
		private final Class<? extends Agent> placeProduced;

		Good(Class<? extends Market> placeSold, Class<? extends Agent> placeProduced) {
			this.placeSold = placeSold;
			this.placeProduced = placeProduced;
		}
	}
}
