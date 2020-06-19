package manager;

public class Main {

	public static void main(String[] args) {
		Manager manager;
		if (args.length == 2) {
			manager = new Manager(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		} else {
			System.out.println("No arguments specified. Assuming 4 firms and 100 households.");
			manager = new Manager(4, 100);
		}
		for (int i = 0; i < 2000; i++) {
			manager.Tick();
		}
		manager.dumpHousehold(0);
	}

}
