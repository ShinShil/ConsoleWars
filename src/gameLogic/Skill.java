package gameLogic;

public class Skill implements INamedObject{
	private ISkillAction actionDelegate;//delegate
	private int available; //true or false, for skills with cd
	private String name;
	
	public Skill(String name, ISkillAction action) {
		super();
		this.available = 1;
		this.name = name;
		this.actionDelegate = action;
	}
	
	public String getName() {
		return name;
	}
	
	public void cast(Player who, Player onWhom) {
		System.out.println(who.getName() + " has casted " + name + " on the " + onWhom.getName());
		actionDelegate.action(who, onWhom);
	}
}
