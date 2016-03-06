package pvp;

import gameLogic.Player;

public class EndAnimationAction {
	
	private Player who;
	private Player onWhom;
	private String skill;
	
	public EndAnimationAction(Player who, String skill, Player onWhom) {
		this.who = who;
		this.onWhom = onWhom;
		this.skill = skill;
	}

	public int action() {
		return who.cast(skill, onWhom);
	}
}
