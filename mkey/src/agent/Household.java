package agent;

import market.Goods.Good;
import util.BoundedRandomNormal;

public class Household extends Agent {

	public Household(int id) {
		this.setID(id);
		this.goodProduced = Good.LABOR;
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

}
