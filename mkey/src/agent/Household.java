package agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import manager.Manager;
import market.Goods.Good;
import market.Market;
import util.BoundedRandomNormal;

public class Household extends Agent {
	HashMap<Good, Integer> quantityEachGood = new HashMap<Good, Integer>();
	ArrayList<Good> purchaseableGoods = new ArrayList<Good>();

	double futureConsumptionPenalty = 0.94; // 0.9 converted into weekly

	private double income;

	private HashMap<Integer, Double> avgProfitBuckets = new HashMap<Integer, Double>();
	private HashMap<Integer, Integer> bucketSizes = new HashMap<Integer, Integer>();
	private ArrayList<Double> profitHistory = new ArrayList<Double>();
	private ArrayList<Double> priceHistory = new ArrayList<Double>();

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
		this.cash = 1000;
	}

	@Override
	public int getProduction() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public double getPrice() {
		// TODO Auto-generated method stub
		if (profitHistory.size() < 52) {
			double price = BoundedRandomNormal.getBoundedRandomNormal(0.0, 20.0, 7.5, 10.0);
			priceHistory.add(price);
			return price;
		} else {
			double maxProfit = Double.NEGATIVE_INFINITY;
			int priceBucket = -1;
			for (Map.Entry<Integer, Double> entry : avgProfitBuckets.entrySet()) {
				if (entry.getValue() > maxProfit) {
					priceBucket = entry.getKey();
					maxProfit = entry.getValue();
				}
			}
			double price = (priceBucket + 0.5) * BoundedRandomNormal.getBoundedRandomNormal(0.5, 1.5, 1, 0.05);
			priceHistory.add(price);
			return price;
		}
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

	@Override
	public void addCash(double cash) {
		this.cash += cash;
		this.income += cash;
	}

	public void addNonIncomeCash(double cash) {
		this.cash += cash;
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
		double futureConsumptionMultiplier = -futureConsumptionPenalty / (futureConsumptionPenalty - 1.0);
		double incomeStream = interestRate * cash;
		double reducedIncomeStream = interestRate * (cash - price);
		/*
		 * if ((cash > price) && (indexOfBestGood != -1) &&
		 * (getMarginalUtility(goodType) + futureConsumptionMultiplier
		 * buyGoodsFor(reducedIncomeStream) > futureConsumptionMultiplier *
		 * buyGoodsFor(incomeStream))) {
		 */
		if ((cash > price) && (indexOfBestGood != -1)) {
			manager.getMarkets().get(goodType).buyGood(indexOfBestGood);
			cash -= price;
			// System.out.println(price);
			return true;
		} else {
			return false;
		}
	}

	public double buyGoodsFor(double cash) {
		HashMap<Good, Integer> tempQuantityEachGood = new HashMap<Good, Integer>(quantityEachGood);
		quantityEachGood.clear();
		for (Good good : purchaseableGoods) {
			quantityEachGood.put(good, 0);
		}
		double bestUtilityPerPrice = 0;
		for (Good good : purchaseableGoods) {
			Market market = manager.getMarkets().get(good);
			if (market.getOffers().size() != 0) {
				double cheapestGood = Double.MAX_VALUE;
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
						}
					}
					if ((ints.size() >= offersLookedAtBeforeBuying) || (ints.size() >= market.getOffers().size())) {
						loop = false;
					}
				}
				double marginalUtil = getMarginalUtility(good);
				double utilsPerPrice = (marginalUtil / cheapestGood);
				if (utilsPerPrice > bestUtilityPerPrice) {
					bestUtilityPerPrice = utilsPerPrice;
				}
			}
		}
		quantityEachGood.clear();
		quantityEachGood.putAll(tempQuantityEachGood);
		return bestUtilityPerPrice * cash;
	}

	public double getLastPrice() {
		return priceHistory.get(priceHistory.size() - 1);
	}

	@Override
	public void rollOver() {
		// TODO Auto-generated method stub
		for (Good good : purchaseableGoods) {
			quantityEachGood.replace(good, 0);
		}

		double profit = income;
		profitHistory.add(profit);
		int intPrice = (int) getLastPrice();
		int bucketSize;
		double avgProfit;
		if (bucketSizes.containsKey(intPrice)) {
			bucketSize = bucketSizes.get(intPrice);
			avgProfit = avgProfitBuckets.get(intPrice);
			avgProfit *= bucketSize;
			avgProfit += profit;
			avgProfit = avgProfit / (bucketSize + 1.0);
			avgProfitBuckets.replace(intPrice, avgProfit);
			bucketSizes.replace(intPrice, bucketSize + 1);
		} else {
			avgProfitBuckets.put(intPrice, profit);
			bucketSizes.put(intPrice, 1);
		}
		income = 0;
		cash *= (Math.pow(interestRate + 1, 1.0 / 52.0));
	}

	public void dumpHistory() {
		for (int i = 0; i < profitHistory.size(); i++) {
			System.out.println(priceHistory.get(i) + ", " + profitHistory.get(i));
		}
	}
}
