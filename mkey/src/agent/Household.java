package agent;

import java.util.ArrayList;
import java.util.HashMap;

import manager.Manager;
import market.Goods.Good;
import market.Market;
import util.BoundedRandomNormal;

public class Household extends Agent {
	HashMap<Good, Integer> quantityEachGood = new HashMap<Good, Integer>();
	ArrayList<Good> purchaseableGoods = new ArrayList<Good>();

	public Household(int id, Manager manager) {
		this.setID(id);
		this.goodProduced = Good.LABOR;
		for (Good good : Good.values()) {
			if (good.getPlaceProduced().equals(Firm.class)) {
				quantityEachGood.put(good, 0);
				purchaseableGoods.add(good);
			}
		}
		this.manager = manager;
	}

	@Override
	public int getProduction() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public double getPrice() {
		// TODO Auto-generated method stub
		return BoundedRandomNormal.getBoundedRandomNormal(2.0, 10.0, 4.0, 2.0);
	}

	private double getUtility() {
		double utility = 1;
		for (Good good : purchaseableGoods) {
			double factor = Math.pow(quantityEachGood.get(good) + 1, good.getConsumptionExponent())
					* good.getConsumptionMultiplier();
			utility *= factor;
		}
		return utility;
	}

	private double getMarginalUtility(Good good) {
		double utility = getUtility();
		quantityEachGood.replace(good, quantityEachGood.get(good) + 1);
		double newUtility = getUtility();
		quantityEachGood.replace(good, quantityEachGood.get(good) - 1);
		return newUtility - utility;
	}

	public boolean buyGoods() {
		double bestUtilityPerPrice = 0;
		double price = 0;
		int indexOfBestGood = -1;
		Good goodType = null;

		for (Good good : purchaseableGoods) {
			Market market = manager.getMarkets().get(good);
			double cheapestGood = Double.MAX_VALUE;
			int indexOfCheapestGood = -1;
			market.shuffleMarket();
			for (int i = 0; i < offersLookedAtBeforeBuying; i++) {
				if (market.getPriceOfOffer(i) < cheapestGood) {
					cheapestGood = market.getPriceOfOffer(i);
					indexOfCheapestGood = i;
				}
			}
			double marginalUtil = getMarginalUtility(good);
			double utilsPerPrice = (marginalUtil / cheapestGood);
			if (utilsPerPrice > bestUtilityPerPrice) {
				goodType = good;
				indexOfBestGood = indexOfCheapestGood;
				bestUtilityPerPrice = utilsPerPrice;
				price = cheapestGood;
			}
		}

		if (cash > price) {
			manager.getMarkets().get(goodType).buyGood(indexOfBestGood);
			cash -= price;
			// System.out.println(price);
			return true;
		} else {
			return false;
		}
	}

}
