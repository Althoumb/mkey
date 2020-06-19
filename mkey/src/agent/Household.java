package agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
		return BoundedRandomNormal.getBoundedRandomNormal(8.0, 20.0, 15.0, 2.0);
	}

	private double getUtility() {
		double utility = 1;
		for (Good good : purchaseableGoods) {
			double factor = Math.pow(quantityEachGood.get(good), good.getConsumptionExponent())
					* good.getConsumptionMultiplier();
			utility += factor;
		}
		return utility;
	}

	private double getMarginalUtility(Good good) {
		return (Math.pow(quantityEachGood.get(good) + 1, good.getConsumptionExponent())
				- Math.pow(quantityEachGood.get(good), good.getConsumptionExponent()))
				* good.getConsumptionMultiplier();
	}

	public boolean buyGoods() {
		double bestUtilityPerPrice = 0;
		double price = 0;
		int indexOfBestGood = -1;
		Good goodType = null;
		for (Good good : purchaseableGoods) {
			Market market = manager.getMarkets().get(good);
			if (market.getOffers().size() != 0) {
				double cheapestGood = Double.MAX_VALUE;
				int indexOfCheapestGood = -1;
				// market.shuffleMarket();
				ArrayList<Integer> ints = new ArrayList<Integer>();
				/*
				 * if (market.getOffers().size() > offersLookedAtBeforeBuying) { ints = new
				 * ArrayList<Integer>(ThreadLocalRandom.current().ints(0,
				 * market.getOffers().size() - 1)
				 * .distinct().limit(offersLookedAtBeforeBuying).boxed().collect(Collectors.
				 * toList())); } else { ints = new
				 * ArrayList<Integer>(ThreadLocalRandom.current().ints(0,
				 * market.getOffers().size() - 1)
				 * .distinct().limit(market.getOffers().size()).boxed().collect(Collectors.
				 * toList())); }
				 */
				boolean loop = true;
				Random rand = new Random();
				while (loop) {
					int i = rand.nextInt(market.getOffers().size());
					if (!ints.contains(i)) {
						ints.add(i);
						if (market.getPriceOfOffer(i) < cheapestGood) {
							cheapestGood = market.getPriceOfOffer(i);
							indexOfCheapestGood = i;
						}
					}
					if ((ints.size() >= offersLookedAtBeforeBuying) || (ints.size() >= market.getOffers().size())) {
						loop = false;
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
		}
		if ((cash > price) && (indexOfBestGood != -1)) {
			manager.getMarkets().get(goodType).buyGood(indexOfBestGood);
			cash -= price;
			// System.out.println(price);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void rollOver() {
		// TODO Auto-generated method stub
		oldCash = cash;
		for (Good good : purchaseableGoods) {
			quantityEachGood.replace(good, 0);
		}
	}

}
