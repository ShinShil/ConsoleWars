package player.crip;

import player.effect.Damage;
import player.effect.DamageType;
import player.effect.Defense;
import scenes.ISceneController;
import skill.CripAttack;

public class Odin extends Crip {

  public Odin(ISceneController controller) {
    super(controller);
    imgPath = odinSprite;
    speed = 10;
    skills = null;
    hp = 5;
    defs.add(new Defense(1000, DamageType.FIRE));
    defs.add(new Defense(1000, DamageType.WATER));
    defs.add(new Defense(1000, DamageType.WIND));
    cripAttack = new CripAttack(new Damage(5, DamageType.PURE));
    spriteAmount = 4;
    spriteWidth = 80;
    name = "Odin";
    weakness = DamageType.FIRE;
    forAuto = "earth";
  }

}
