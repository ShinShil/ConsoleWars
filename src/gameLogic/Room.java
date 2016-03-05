package gameLogic;
import java.util.ArrayList;
import java.util.Scanner;
public class Room {
	ArrayList<Player> pls;
	int active;
	public Room(int playerAmount) {
		pls = new ArrayList<Player>();
		pls.add(new Player("p1"));
		pls.add(new Player("p2"));
		active = 0;
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
	
	public Player getNextPlayer() {
		if(active + 1 == pls.size()) 
		{
			active = 0;
			return pls.get(0);
		}
		else {
			++active;
			return pls.get(active);
		}
	}
	
	public int getCurrPlayerIndex() {
		return active;
	}
	
	public Player getCurrPlayer() {
		return pls.get(active);
	}
	
	public ArrayList<Player> getPlayers() {
		return pls;
	}
	
	public boolean checkTheGameState() {
		for(Player pl:pls) {
			if(pl.getHP()<0) {
				return false;
			}
		}
		return true;
	}
}
