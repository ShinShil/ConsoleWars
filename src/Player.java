package application;
import java.util.ArrayList;

public class Player implements INamedObject {
	//Players attributes
	private int healthPoint;
	private String name;
	private Defense fireDefense;
	ArrayList<Skill> skills;
	//commandLine
	int indexOfName;//when the game becomes wider it possible: "cast fire _target"
	int indexOfSkill;
	
	//constructors
	public Player(String name) {
		super();
		this.name = name;
		healthPoint = 10;
		skills = new ArrayList<Skill>(100);
		skills.add(new Skill("fire", new ISkillAction() {
			@Override
			public void action(Player who, Player onWhom) {
				onWhom.damage(3, "fire");
			}
		}));
		skills.add(new Skill("heal", new ISkillAction() {
			@Override
			public void action(Player who, Player onWhom) {
				onWhom.heal(3, "pure");
			}
		}));
		skills.add(new Skill("defFire", new ISkillAction() {
			@Override
			public void action(Player Who, Player onWhom) {
				onWhom.fireDefense.addDefPoints(2);
			}
		}));
		fireDefense = new Defense("fire");
	}
	
	//getter
	public String getName() {
		return name;
	}
	public int getHP() {
		return healthPoint;
	}
	public int getDefFire() {
		return fireDefense.getPoints();
	}
	
	public void show() {
		System.out.println(this.name + " : " + "Health " + this.healthPoint + "; FireDefense " + this.fireDefense.getPoints());
	}
	
	//cast
	//returns index of the search, to use, -1 if fail
	public static int findByName(ArrayList<? extends INamedObject> names, String name) {
		for(int i = 0; i<names.size(); ++i) {
			if(names.get(i).getName().equals(name)) {
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
	private String getSkillFromCastLine(String castLine) {
		String[] names = castLine.split(" +");
		if(names.length > 1) {
			return names[0];
		}else {
			return "error";
		}
	}
	public int cast(String castLine, ArrayList<Player> pls) {
		//will the search in a list 
		String tmp = getNameFromCastLine(castLine);
		System.out.println("TMP: " + tmp);
		int index = findByName(pls, getNameFromCastLine(castLine));
		if(index != -1) {
			int indexSkill = findByName(skills, getSkillFromCastLine(castLine));
			if(indexSkill == -1) {
				System.out.println(getSkillFromCastLine(castLine) + " wasn't found");
				return 1;
			}
			skills.get(indexSkill).Cast(this, pls.get(index));
		}else {
			System.out.println("doesn't find this player");
			return 2;
		}
		return 0;
	}
	//effects
	public int damage(int points, String type) {
		if(points<0) {
			return -1;
		}else {
			switch(type) {
			case "fire":
				if(fireDefense.getPoints()>0) {
					fireDefense.removeDefPoints(points);
				}else {
					healthPoint -= points;
				}
				break;
			default:
				healthPoint -= points;
			}
			return points;
		}
	}
	public int heal(int points, String type) {
		if(points<0) {
			return -1;
		}else {
			healthPoint += points;
			return points;
		}
	}
}