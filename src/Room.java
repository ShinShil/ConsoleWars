package application;
import java.util.Scanner;
public class Room {
	Player[] pls;
	Room(int playerAmount) {
		pls = new Player[2];
		pls[0] = new Player("Player1");
		pls[1] = new Player("Player2");
	}
	
	public void startGame() {
		String commandLine = "qwe";
		Scanner sc = new Scanner(System.in);
		while(!commandLine.equals("exit")) {
			for(Player pl: pls) {
				pl.show();
			}
			commandLine = sc.nextLine();
			pls[0].cast(commandLine, pls);
		}
		sc.close();
	}
}
