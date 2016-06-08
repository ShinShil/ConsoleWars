package scenes;

import java.util.ArrayList;

import player.Player;

/**
 * main controller for player(s)-human - contains it's hp, ref to the object, 
 * also checked the state of the player(f.e. dead or alive?)
 * @author ShinShil
 *
 */
public class Room {
  ArrayList<Player> pls;
  int active;

  public Room(int playerAmount) {
    pls = new ArrayList<Player>();
    pls.add(new Player("p1"));
    active = 0;
    pls.get(0).setHp(1000);
  }

  public Player getNextPlayer() {
    return getNextPlayer(false);
  }

  public Player getNextPlayer(boolean incr) {
    int nextIndex = active + 1 == pls.size() ? 0 : active + 1;
    if (incr) {
      active = nextIndex;
    }
    return pls.get(nextIndex);
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
    for (Player pl : pls) {
      if (pl.getHp() < 0) {
        return false;
      }
    }
    return true;
  }
}
