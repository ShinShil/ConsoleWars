package skill;


import application.INamedObject;
import player.BasePlayer;
import player.effect.Effect;

/**
 * action that can inflict player's attributes(hp) : fire - skill, cripAttack - skill, etc.
 * @author ShinShil
 *
 */
public abstract class Skill implements INamedObject, ISkillConstants {
  protected String name;
  protected Effect effect;
  protected String clickImgPath;

  public Skill(String name) {
    super();
    this.name = name;
  }

  public String getClickImgPath() {
    return clickImgPath;
  }

  public String getName() {
    return name;
  }

  public boolean equals(Skill skill) {
    if (skill.name.equals(this.name)) {
      return true;
    } else {
      return false;
    }
  }

  /*
   * public void effect(ArrayList<BasePlayer> onWhom) { //for global skills }
   */
  public abstract void effect(BasePlayer onWhom);
  // public abstract void cast(BasePlayer player)
}
