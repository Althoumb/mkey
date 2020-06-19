package agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import manager.Manager;
import market.Goods.Good;
import market.Market;
import util.BoundedRandomNormal;

public class Firm extends Agent {
	private int labor;

	private ArrayList<Double> profitHistory = new ArrayList<Double>();
	private ArrayList<Double> priceHistory = new ArrayList<Double>();
	private double gradientDescentMultiplier = 0.002;
	private HashMap<Integer, Double> avgProfitBuckets = new HashMap<Integer, Double>();
	private HashMap<Integer, Integer> bucketSizes = new HashMap<Integer, Integer>();

	public Firm(int id, Good goodProduced, Manager manager) {
		this.setID(id);
		this.goodProduced = goodProduced;
		this.manager = manager;
	}

	@Override
	public int getProduction() {
		// TODO Auto-generated method stub
		double rand = Math.random();
		double unroundedProduction = getUnroundedProduction();
		double decimal = unroundedProduction - Math.floor(unroundedProduction);
		if (rand <= decimal) {
			return (int) unroundedProduction + 1;
		} else {
			return (int) unroundedProduction;
		}
	}

	double getUnroundedProduction() {
		return goodProduced.getProductionMultiplier() * Math.pow(labor + 1, goodProduced.getLaborExponent())
				* Math.pow(capital + 1, goodProduced.getCapitalExponent());
	}

	double getMarginalProduction() {
		return goodProduced.getProductionMultiplier() * Math.pow(labor + 2, goodProduced.getLaborExponent())
				* Math.pow(capital + 1, goodProduced.getCapitalExponent()) - getUnroundedProduction();
	}

	@Override
	public double getPrice() {
		// TODO Auto-generated method stub
		double price = getPriceWithoutUpdating();
		priceHistory.add(price);
		return price;
	}

	public double getPriceWithoutUpdating() {
		// TODO Auto-generated method stub
		/*
		 * if (profitHistory.size() < 2) { double price =
		 * BoundedRandomNormal.getBoundedRandomNormal(5.0, 10.0, 7.5, 2); return price;
		 * } else { int index = profitHistory.size() - 1; double priceDelta =
		 * gradientDescentMultiplier * (profitHistory.get(index) -
		 * profitHistory.get(index - 1)) / (priceHistory.get(index) -
		 * priceHistory.get(index - 1)); double price = priceHistory.get(index) +
		 * priceDelta; // System.out.println(profitHistory.get(index) -
		 * profitHistory.get(index - 1) + // " " + (priceHistory.get(index) -
		 * priceHistory.get(index - 1))); if ((price > 0) && (Double.isFinite(price))) {
		 * return price; } else { price =
		 * BoundedRandomNormal.getBoundedRandomNormal(5.0, 10.0, 7.5, 2); return price;
		 * } }
		 */
		/*
		 * if (profitHistory.size() < 52) { double price =
		 * BoundedRandomNormal.getBoundedRandomNormal(2.0, 20.0, 7.5, 5.0); return
		 * price; } else { ArrayList<Pair<Double, Integer>> sortedPriceHistory = new
		 * ArrayList<Pair<Double, Integer>>(); for (int i = 0; i < priceHistory.size();
		 * i++) { sortedPriceHistory.add(new Pair<Double, Integer>(priceHistory.get(i),
		 * i)); } Collections.sort(sortedPriceHistory, new GoodOfferComparator()); int
		 * index = profitHistory.size() - 1; double maxProfit = 0; int maxIndex = -1;
		 * for (int i = index; i > index - 52; i--) { int sortedIndex =
		 * sortedPriceHistory.indexOf(new Pair<Double, Integer>(priceHistory.get(i),
		 * i)); double sum = 0; int number = 0; for (int j = sortedIndex - 2; j <
		 * sortedIndex + 2; j++) { if ((j >= 0) && (j < sortedPriceHistory.size())) {
		 * sum += profitHistory.get(sortedPriceHistory.get(j).getR()); number++; } }
		 * double profit = sum / number; if (profit > maxProfit) { maxIndex = i;
		 * maxProfit = profit; } } if (maxIndex == -1) { return
		 * BoundedRandomNormal.getBoundedRandomNormal(5.0, 50.0, 10, 4); } else { return
		 * priceHistory.get(maxIndex) * BoundedRandomNormal.getBoundedRandomNormal(0.4,
		 * 1.6, 1, 0.1); } }
		 */
		if (profitHistory.size() < 52) {
			double price = BoundedRandomNormal.getBoundedRandomNormal(0.0, Double.MAX_VALUE, 7.5, 10.0);
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
			return (priceBucket + 0.5) * BoundedRandomNormal.getBoundedRandomNormal(0.5, 1.5, 1, 0.05);
		}
	}

	public double getLastPrice() {
		return priceHistory.get(priceHistory.size() - 1);
	}

	public boolean buyLabor() { // boolean lets us see if offer was made
		Market laborMarket = manager.getMarkets().get(Good.LABOR);
		double cheapestLabor = Double.MAX_VALUE;
		int indexOfCheapestGood = -1;
		laborMarket.shuffleMarket();
		for (int i = 0; i < offersLookedAtBeforeBuying; i++) {
			if (laborMarket.getPriceOfOffer(i) < cheapestLabor) {
				cheapestLabor = laborMarket.getPriceOfOffer(i);
				indexOfCheapestGood = i;
			}
		}
		double marginalProduction = getMarginalProduction();
		if ((marginalProduction * getPriceWithoutUpdating() > cheapestLabor) && (indexOfCheapestGood != -1)) {
			laborMarket.buyGood(indexOfCheapestGood);
			addCash(-cheapestLabor);
			labor += 1;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void rollOver() {
		double profit = cash - oldCash;
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
			if (bucketSize <= 3) {
				bucketSizes.replace(intPrice, bucketSize + 1);
			}
		} else {
			avgProfitBuckets.put(intPrice, profit);
			bucketSizes.put(intPrice, 1);
		}
		oldCash = cash;
		labor = 0;
	}

	public void dumpHistory() {
		for (int i = 0; i < profitHistory.size(); i++) {
			System.out.println(priceHistory.get(i) + ", " + profitHistory.get(i));
		}
	}

}
