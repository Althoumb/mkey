package manager;

import java.util.ArrayList;

import agent.Firm;
import agent.Household;

public class Manager {
	ArrayList<Firm> firms = new ArrayList<Firm>();
	ArrayList<Household> households = new ArrayList<Household>();

	public Manager(int noFirms, int noHouseholds) {
		for (int i = 0; i < noFirms; i++) {
			firms.add(new Firm(i));
		}
		for (int i = 0; i < noHouseholds; i++) {
			households.add(new Household(i));
		}
	}

	public void Tick() {
		// First, get labor production.
		// Then, add to market and sort.
		// Let firms buy labor and produce.
		// Then, add to market and sort.
		// Let households buy goods.
		// Roll everything over.
	}
}
