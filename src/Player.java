package application;

public class Player implements INamedObject {
	//Players attributes
	private int healthPoint;
	private String name;
	Skill skill;//will be a list, the magicBook
	
	//commandLine
	int indexOfName;//when the game becomes wider it possible: "cast fire _target"
	int indexOfSkill;
	
	
	public String getName() {
		return name;
	}
	
	public void show() {
		System.out.println(this.name + " : " + this.healthPoint);
	}
	
	public Player(String name) {
		super();
		this.name = name;
		healthPoint = 10;
		skill = new Skill("fire", new ISkillAction() {
			@Override
			public void action(Player who, Player onWhom) {
				onWhom.healthPoint -= 3;
			}
		});
	}
	
	//returns index of the search, to use, -1 if fail
	public static int FindByName(INamedObject[] names, String name) {
		for(int i = 0; i<names.length; ++i) {
			if(names[i].getName().equals(name)) {
				return i;
			}
		}
		return -1;
	}
	private String getNameFromCastLine(String castLine) {
		String[] names = castLine.split(" +");
		if(names.length > 1) {
			return names[1];
		}else {
			return "error";
		}
	}
	public void cast(String castLine, Player[] pls) {
		//will the search in a list 
		int index = FindByName(pls, getNameFromCastLine(castLine));
		if(index != -1) {
			skill.Cast(this, pls[index]);
		}else {
			System.out.println("doesn't find this player");
		}
	}
}