package agent;

public abstract class Agent {
	protected double cash;

	private int id;

	abstract int getProduction();

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
