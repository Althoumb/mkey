package agent;

import market.Goods.Good;

public class Household extends Agent {

	public Household(int id) {
		this.setID(id);
		this.goodProduced = Good.LABOR;
	}

	@Override
	public int getProduction() {
		// TODO Auto-generated method stub
		return 40;
	}

	@Override
	public double getPrice() {
		// TODO Auto-generated method stub
		return 0;
	}

}
