package pvp;

import animation.AnimationFire;
import animation.FireEmmiter;
import gameDisplay.SceneControl;
import gameLogic.Player;
import gameLogic.Room;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class PvpControl implements SceneControl{

	private Canvas canvas;
	private Room room = new Room(2);
	private Player activePlayer = room.getCurrPlayer(); 
	private int speed;
	private HBox hBoxP1;
	private HBox hBoxP2;
	
	public PvpControl(Canvas canvas, HBox hBoxP1, HBox hBoxP2) {
		this.canvas = canvas;
		this.hBoxP1 = hBoxP1;
		this.hBoxP2 = hBoxP2;
		this.setSpeed();
	}
	
	public void nextPlayer() {
		activePlayer = room.getNextPlayer();
	}
	public void cast(Canvas canvas, String commandLine, int index) {
		String[] parseRes = commandLine.split(" ");
		for(String str: parseRes) {
			System.out.print(str);
		}
		double dummyW, animX, animY;
		if(room.getCurrPlayerIndex() == 0) {
			dummyW = ((Region) hBoxP1.getChildren().get(0)).getPrefWidth();
			animX = dummyW * index - dummyW/2 - FireEmmiter.getRadius()/2;
			animY = canvas.getHeight();
		}else {
			dummyW = ((Region) hBoxP1.getChildren().get(0)).getPrefWidth();
			animX = dummyW * index - dummyW/2 - FireEmmiter.getRadius()/2;
			animY = 0;
		}
		String skill = parseRes[0];
		AnimationFire fire = new AnimationFire(canvas, new EndAnimationAction(activePlayer, skill, room.getNextPlayer()));
		activePlayer = room.getCurrPlayer(); //in the previous str used room.getNextPlayer() - it increase index in room
		fire.setPos(animX, animY);
		fire.setSpeed(speed);
		fire.start();
		setSpeed();
	}
	public void setSpeed() {
		if(room.getCurrPlayerIndex() == 0) {
			speed = -10;
		}else {
			speed = 10;
		}
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	@Override
	public void refreshVals() {
		
	}
	
}
