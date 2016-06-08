package skill;

import player.BasePlayer;
import player.effect.Damage;
import player.effect.DamageType;

public class SkEarth extends Skill{

  private Damage earthDamage;

  public SkEarth() {
    super(earthSkillName);
    earthDamage = new Damage(earthSkillDamage, DamageType.EARTH);
    this.clickImgPath = earthSkillClickImg;
  }

  @Override
  public void effect(BasePlayer onWhom) {
    onWhom.damage(earthDamage);
  }

}
