package gameDisplay;

import java.util.ArrayList;

import gameLogic.Player;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class DisplayControl implements SceneControl {
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
		
		currHp.setFont(new Font("Arial", 16));
		currDef.setFont(new Font("Arial", 16));
		currName.setFont(new Font("Arial", 16));
		
		this.pls = pls;
		int size = pls.size();
		hp = new ArrayList<Label>(size);
		defs = new ArrayList<Label>(size);
		names = new ArrayList<Label>(size);
		for(int i = 0; i < pls.size(); ++i) {
			hp.add(new Label());
			defs.add(new Label());
			names.add(new Label());
			hp.get(i).setFont(new Font("Arial", 16));
			defs.get(i).setFont(new Font("Arial", 16));
			names.get(i).setFont(new Font("Arial", 16));
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
			String tHp = String.format("%d", pls.get(i).getHP());
			String tDef = String.format("%d", pls.get(i).getDefFire());
			if(hp.get(i).getText().equals(tHp)) {
				hp.get(i).setTextFill(Color.BLACK);
			}else {
				hp.get(i).setTextFill(Color.RED);
			}
			if(defs.get(i).getText().equals(tDef)) {
				defs.get(i).setTextFill(Color.BLACK);
			}else {
				defs.get(i).setTextFill(Color.RED);
			}
			hp.get(i).setText(tHp);
			defs.get(i).setText(tDef);
			names.get(i).setText(pls.get(i).getName());
		}
	} 
}
