package gameLogic;

public class Skill implements INamedObject{
	public String getName() {
		return name;
	}
	ISkillAction actionDelegate;//delegate
	int available; //true or false, for skills with cd
	String name;
	public void Cast(Player who, Player onWhom) {
		System.out.println(who.getName() + " has casted " + name + " on the " + onWhom.getName());
		actionDelegate.action(who, onWhom);
	}
	
	public Skill(String name, ISkillAction action) {
		super();
		this.available = 1;
		this.name = name;
		this.actionDelegate = action;
	}
}
