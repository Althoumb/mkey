package manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import agent.Firm;
import agent.Household;
import market.Goods.Good;
import market.Market;

public class Manager {
	ArrayList<Firm> firms = new ArrayList<Firm>();
	ArrayList<Household> households = new ArrayList<Household>();
	HashMap<Good, Market> markets = new HashMap<Good, Market>();

	public Manager(int noFirms, int noHouseholds) {

		// this code makes it so that we can assign each firm to a random good
		ArrayList<Good> possibilities = new ArrayList<Good>();

		for (Good good : Good.values()) {
			if (Firm.class.equals(good.getPlaceProduced())) {
				possibilities.add(good);
			}
		}

		Random rand = new Random();

		for (int i = 0; i < noFirms; i++) {
			firms.add(new Firm(i, possibilities.get(rand.nextInt(possibilities.size())))); // that's a lot of parens
		}

		for (int i = 0; i < noHouseholds; i++) {
			households.add(new Household(i));
		}

		for (Good good : Good.values()) {
			markets.put(good, new Market(good));
		}

		Tick();
	}

	public void Tick() {
		// First, get labor production.
		// Then, add to market and sort.
		getLaborProduction();
		// Let firms buy labor and produce.
		// Then, add to market and sort.
		// Let households buy goods.
		// Roll everything over.
	}

	public void getLaborProduction() {
		Market laborMarket = markets.get(Good.LABOR);
		for (Household household : households) {
			double price = household.getPrice();
			int quantity = household.getProduction();
			laborMarket.addGoodToMarket(price, quantity, household);
		}

		laborMarket.sortMarket();
	}
}
