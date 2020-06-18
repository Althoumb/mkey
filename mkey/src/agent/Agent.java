package agent;

import manager.Manager;
import market.Goods.Good;

public abstract class Agent {
	protected double cash;
	protected Good goodProduced;

	protected Manager manager;

	protected int capital = 1; // HARDCODED VARIABLE: BAD PRACTICE FIX

	protected int offersLookedAtBeforeBuying = 10; // HARDCODED VARIABLE: BAD PRACTICE FIX

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

	public Good getGood() {
		return goodProduced;
	}

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
