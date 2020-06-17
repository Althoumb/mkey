package agent;

import market.Goods.Good;

public class Firm extends Agent {

	public Firm(int id, Good goodProduced) {
		this.setID(id);
		this.goodProduced = goodProduced;
	}

	@Override
	int getProduction() {
		// TODO Auto-generated method stub
		return 40;
	}

	@Override
	double getPrice() {
		// TODO Auto-generated method stub
		return 0;
	}

}
