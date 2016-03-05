package gameLogic;

public class Defense implements INamedObject {
	private int points;
	String name;//better to use enum
	public String getName() {
		return name;
	}
	public int getPoints() {
		return points;
	}
	public Defense(String name) {
		this.name = name;
		points = 0;
	}
	public int addDefPoints(int defPoints) {
		if(defPoints<0) {
			return -1;
		}else {
			this.points+=defPoints;
			return defPoints;
		}
	}
	public int removeDefPoints(int defPoints) {
		if(defPoints<0) {
			return -1;
		}else {
			this.points -= defPoints;
			return defPoints;
		}
	}
}
