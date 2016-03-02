package application;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class DisplayControl {
	ArrayList<Player> pls;
	ArrayList<Label> hp;
	ArrayList<Label> defs;
	ArrayList<Label> names;
	
	Player currPlayer;
	Label currHp;
	Label currDef;
	Label currName;
	
	public int allPlayersInGame() {
		return pls.size();
	}
	
	public DisplayControl(ArrayList<Player> pls, Player currPlayer) {
		this.currPlayer = currPlayer;
		
		currHp = new Label(String.format("%d", currPlayer.getHP()));
		currDef = new Label(String.format("%d", currPlayer.getDefFire()));
		currName = new Label(currPlayer.getName());
		
		currName.setFont(new Font("arial", 24));
		
		this.pls = pls;
		int size = pls.size();
		hp = new ArrayList<Label>(size);
		defs = new ArrayList<Label>(size);
		names = new ArrayList<Label>(size);
		for(int i = 0; i < pls.size(); ++i) {
			hp.add(new Label());
			defs.add(new Label());
			names.add(new Label());
		}
		System.out.println("SIZE: " + hp.size() + " Players size: " + size);
		this.refreshVals();
	}
	public Label getLabelIndexHp(int index) {
		System.out.println(index);
		System.out.println("SIZE: " + hp.size());
		return hp.get(index);
	}
	public Label getLabelIndexDef(int index) {
		return defs.get(index);
	}
	public Label getLabelIndexName(int index) {
		return names.get(index);
	}
	public Label getCurrHp() {
		return currHp;
	}
	public Label getCurrDef() {
		return currDef;
	}
	public Label getCurrName() {
		return currName;
	}
	
	public void addHpLabel(Label hpLabel) {
		hp.add(hpLabel);
	}
	public void addDefLabel(Label defLabel) {
		defs.add(defLabel);
	}
	public void addPlayer(Player pl) {
		pls.add(pl);
	}
	
	public void setNewCurrPlayer(Player pl) {
		currPlayer = pl;
	}
	
	public void refreshVals() {		
		currHp.setText(String.format("%d", currPlayer.getHP()));
		currDef.setText(String.format("%d", currPlayer.getDefFire()));
		currName.setText(currPlayer.getName());
		for(int i = 0; i<pls.size(); ++i) {
			hp.get(i).setText(String.format("%d", pls.get(i).getHP()));
			defs.get(i).setText(String.format("%d", pls.get(i).getDefFire()));
			names.get(i).setText(pls.get(i).getName());
		}
	} 
}
