package player.crip;

import player.effect.Damage;
import player.effect.DamageType;
import player.effect.Defense;
import scenes.ISceneController;
import skill.CripAttack;

public class Phoenix extends Crip {
  public Phoenix(ISceneController controller) {
    super(controller);
    imgPath = phoenixSprite;
    speed = 30;
    skills = null;
    hp = 10;
    defs.add(new Defense(1000, DamageType.FIRE));
    defs.add(new Defense(1000, DamageType.WATER));
    defs.add(new Defense(1000, DamageType.EARTH));
    cripAttack = new CripAttack(new Damage(3, DamageType.PURE));
    spriteAmount = 4;
    spriteWidth = 80;
    name = "Odin";
    weakness = DamageType.WIND;
    forAuto = "wind";
  }
}
