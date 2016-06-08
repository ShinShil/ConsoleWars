package application;

import java.util.ArrayList;

import skill.SkEarth;
import skill.SkFire;
import skill.SkWater;
import skill.SkWind;
import skill.Skill;

/**
 * all skills that can be use in game - for correct initialisation of game objects(crips, players, scenes)
 * @author ShinShil
 *
 */
public class GameSkills {
  public static ArrayList<Skill> skills;

  static {
    skills = new ArrayList<Skill>();
    skills.add(new SkFire());
    skills.add(new SkWater());
    skills.add(new SkEarth());
    skills.add(new SkWind());
  }
}
