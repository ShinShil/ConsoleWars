package player;

import java.util.ArrayList;

import application.GameSkills;
import application.INamedObject;
import application.Service;
import player.effect.Damage;
import player.effect.DamageType;
import player.effect.Defense;
import player.effect.Heal;
import skill.Skill;

/**
 * Player and Crip extend it
 * @author ShinShil
 *
 */
public abstract class BasePlayer implements INamedObject {

  protected String name;
  protected int hp;
  protected int sp;
  protected ArrayList<Defense> defs;
  protected ArrayList<Skill> skills;

  public BasePlayer() {
    defs = new ArrayList<Defense>();
    defs.add(new Defense(0, DamageType.FIRE));
    defs.add(new Defense(0, DamageType.WATER));
    defs.add(new Defense(0, DamageType.EARTH));
    defs.add(new Defense(0, DamageType.WIND));

    skills = new ArrayList<Skill>();
    initSkills();
  }

  public void setHp(int hp) {
    this.hp = hp;
  }

  public String getName() {
    return name;
  }

  public int getHp() {
    return hp;
  }

  public int getSp() {
    return sp;
  }

  public ArrayList<Defense> getDefs() {
    return defs;
  }

  public ArrayList<Skill> getSkills() {
    return skills;
  }

  public abstract void initSkills();


  public void effectBySkill(String skill) {
    getSkill(skill).effect(this);
  }

  public void effectBySkill(Skill skill) {
    skill.effect(this);
  }

  public int damage(Damage dmg) {
    Defense def = getDef(dmg.getType());
    if (def != null && def.getPoints() > 0) {
      def.removePoints(dmg.getPoints());
    } else {
      hp -= dmg.getPoints();
    }
    return hp;
  }

  public int heal(Heal heal) {
    hp += heal.getPoints();
    return hp;
  }

  public int addDef(Defense def) {
    Defense tmp = getDef(def.getType());
    tmp.addPoints(def.getPoints());
    return tmp.getPoints();
  }

  protected Defense getDef(DamageType type) {
    if (defs != null) {
      for (int i = 0; i < defs.size(); ++i) {
        if (defs.get(i).getType().equals(type)) {
          return defs.get(i);
        }
      }
    }
    return null;
  }

  protected Skill getSkill(String skill) {
    return (Skill) Service.getINamedObject(GameSkills.skills, skill);
  }

}
