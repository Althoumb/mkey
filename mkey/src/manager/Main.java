package manager;

public class Main {

	public static void main(String[] args) {
		Manager manager;
		if (args.length == 2) {
			manager = new Manager(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		} else {
			System.out.println("No arguments specified. Assuming 10 firms and 1000 households.");
			manager = new Manager(10, 1000);
		}
		for (int i = 0; i < 500; i++) {
			manager.Tick();
		}
		manager.dumpFirm(0);
	}

}
