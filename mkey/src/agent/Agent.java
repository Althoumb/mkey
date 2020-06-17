package agent;

import market.Goods.Good;

public abstract class Agent {
	protected double cash;
	protected Good goodProduced;

	private int id;

	abstract int getProduction();

	abstract double getPrice();

	/*
	 * Do we really need dynamic production? public ArrayList<Good>
	 * getProductionPossibilities() {
	 * 
	 * ArrayList<Good> possibilities = new ArrayList<Good>();
	 * 
	 * for (Good good : Good.values()) { if
	 * (this.getClass().equals(good.getPlaceProduced())) { possibilities.add(good);
	 * } }
	 * 
	 * return possibilities; }
	 */

	public double getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public double getCash() {
		return cash;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public void addCash(double cash) {
		this.cash += cash;
	}
}
