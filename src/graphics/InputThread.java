package graphics;

import java.util.Scanner;

public class InputThread extends Thread {

	/**
	 * Reads input through the console for the duration of the program
	 */
	@Override
	public void run() {
		Scanner in = new Scanner(System.in);
		while (true) {
			System.out.print("> ");
			String input = in.nextLine();
			if (input.equals("pause")) {
				JSENNPanel.togglePause();
			}
			else if (input.equals("close")) {
				in.close();
				break;
			}
		}
	}
}
