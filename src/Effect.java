package application;

abstract public class Effect implements INamedObject {
	String name;

	public String getName() {
		return name;
	}
	
	public Effect(String name) {
		super();
		this.name = name;
	}
	public void action() {
		
	}
	
}
