package market;

import java.util.ArrayList;

import agent.Agent;
import market.Goods.Good;
import util.GoodOfferComparator;
import util.Pair;

public abstract class Market {
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
}
