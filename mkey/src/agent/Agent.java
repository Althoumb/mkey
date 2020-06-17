package agent;

public abstract class Agent {
	protected double cash;

	abstract int getProduction();

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
