package player;

import java.util.ArrayList;

import application.INamedObject;
import skill.SkEarth;
import skill.SkFire;
import skill.SkWater;
import skill.SkWind;
import skill.Skill;

/**
 * describes the human(bot) - skills
 * hp, def - see BasePlayer
 * @author ShinShil
 *
 */
public class Player extends BasePlayer implements INamedObject {
  /**
   * currentSkillClick - skill that will casted if press(1,2,3,4...)
   */
  ArrayList<Skill> currentSkillClickList;
  Skill currentSkillClick;

  // constructors
  public Player(String name) {
    initSkills();
    this.name = name;
    currentSkillClickList = new ArrayList<Skill>();
    for (int i = 0; i < 4; ++i) {
      currentSkillClickList.add(skills.get(i));
    }
    currentSkillClick = currentSkillClickList.get(0);
  }

  @Override
  public void initSkills() {
    skills = new ArrayList<Skill>();
    skills.add(new SkFire());
    skills.add(new SkWater());
    skills.add(new SkEarth());
    skills.add(new SkWind());
  }

  // getters
  public String getName() {
    return name;
  }

  public ArrayList<Skill> getSkills() {
    return skills;
  }

  public ArrayList<Skill> getSkillClickList() {
    return currentSkillClickList;
  }

  public Skill getSkillByName(String name) {
    for (int i = 0; i < skills.size(); ++i) {
      if (skills.get(i).getName().equals(name)) {
        return skills.get(i);
      }
    }
    return null;
  }
  /**
   * set the skill that will casted if press(1,2,3,4...)
   * @param name
   */
  public void setSkillClick(String name) {
    currentSkillClick = getSkillByName(name);
  }
  /**
   * set the skill that will casted if press(1,2,3,4...)
   * @return
   */
  public Skill getSkilClick() {
    return currentSkillClick;
  }

}
