package application;
import java.util.Scanner;
import java.util.ArrayList;
public class Room {
	ArrayList<Player> pls;
	Room(int playerAmount) {
		pls = new ArrayList<Player>();
		pls.add(new Player("p1"));
		pls.add(new Player("p2"));
	}
	
	public void startGame() {
		String commandLine = "qwe";
		Scanner sc = new Scanner(System.in);
		System.out.println("Game was started");
		while(!commandLine.equals("exit")) {
			for(Player pl: pls) {
				pl.show();
			}
			commandLine = sc.nextLine();
			pls.get(0).cast(commandLine, pls);
		}
		sc.close();
	}
}
