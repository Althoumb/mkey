package agent;

import manager.Manager;
import market.Goods.Good;
import market.Market;

public class Firm extends Agent {
	private int labor;

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
		return goodProduced.getProductionMultiplier() * Math.pow(labor, goodProduced.getLaborExponent())
				* Math.pow(capital, goodProduced.getCapitalExponent());
	}

	double getMarginalProduction() {
		return goodProduced.getProductionMultiplier() * Math.pow(labor + 1, goodProduced.getLaborExponent())
				* Math.pow(capital, goodProduced.getCapitalExponent()) - getUnroundedProduction();
	}

	@Override
	public double getPrice() {
		// TODO Auto-generated method stub
		return 5;
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
		if (marginalProduction * getPrice() > cheapestLabor) {
			laborMarket.buyGood(indexOfCheapestGood);
			addCash(-cheapestLabor);
			labor += 1;
			return true;
		} else {
			return false;
		}
	}

}
