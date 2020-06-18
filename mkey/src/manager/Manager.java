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
			firms.add(new Firm(i, possibilities.get(rand.nextInt(possibilities.size())), this)); // that's a lot of
																									// parens
		}

		for (int i = 0; i < noHouseholds; i++) {
			households.add(new Household(i, this));
		}

		for (Good good : Good.values()) {
			markets.put(good, new Market(good));
		}
	}

	public void Tick() {
		// First, get labor production.
		// Then, add to market and sort.
		getLaborProduction();
		// Let firms buy labor and produce.
		buyLabor();
		// Then, add to market and sort.
		getFirmProduction();
		// Let households buy goods.
		buyGoods();
		// Roll everything over.
	}

	public void getLaborProduction() {
		Market laborMarket = markets.get(Good.LABOR);
		for (Household household : households) {
			double price = household.getPrice();
			int quantity = household.getProduction();
			laborMarket.addGoodToMarket(price, quantity, household);
		}
	}

	public void getFirmProduction() {
		for (Firm firm : firms) {
			Market goodMarket = markets.get(firm.getGood());
			double price = firm.getPrice();
			int quantity = firm.getProduction();
			goodMarket.addGoodToMarket(price, quantity, firm);
		}
	}

	public void buyLabor() {
		boolean loopBool = true;
		while (loopBool) {
			loopBool = false;
			for (Firm firm : firms) {
				boolean firmBool = firm.buyLabor();
				loopBool = (loopBool || firmBool);
			}
		}
	}

	public void buyGoods() {
		boolean loopBool = true;
		while (loopBool) {
			loopBool = false;
			for (Household household : households) {
				boolean householdBool = household.buyGoods();
				loopBool = (loopBool || householdBool);
			}
		}
	}

	public HashMap<Good, Market> getMarkets() {
		return markets;
	}
}
