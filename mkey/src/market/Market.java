package market;

import java.util.ArrayList;
import java.util.Collections;

import agent.Agent;
import market.Goods.Good;
import util.GoodOfferComparator;
import util.Pair;

public class Market {
	Good good;
	ArrayList<Pair<Double, Agent>> goodsForSale = new ArrayList<Pair<Double, Agent>>();

	public Market(Good good) {
		this.good = good;
	}

	public void addGoodToMarket(double price, int quantity, Agent agent) {
		for (int i = 0; i < quantity; i++) {
			goodsForSale.add(new Pair<Double, Agent>(price, agent));
		}
	}

	public void sortMarket() {
		goodsForSale.sort(new GoodOfferComparator());
	}

	public void shuffleMarket() {
		Collections.shuffle(goodsForSale);
	}

	public ArrayList<Pair<Double, Agent>> getOffers() {
		return goodsForSale;
	}

	public double getPriceOfOffer(int index) {
		if (index < goodsForSale.size()) {
			return goodsForSale.get(index).getL();
		} else {
			return Double.MAX_VALUE;
		}
	}

	public void buyGood(int index) {
		Pair<Double, Agent> offer = goodsForSale.get(index);
		offer.getR().addCash(offer.getL());
		goodsForSale.remove(index);
	}
}
