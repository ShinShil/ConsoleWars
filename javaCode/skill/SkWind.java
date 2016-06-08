package skill;

import player.BasePlayer;
import player.effect.Damage;
import player.effect.DamageType;

public class SkWind extends Skill {
  private Damage windDamage;

  public SkWind() {
    super("wind");
    windDamage = new Damage(windSkillDamage, DamageType.WIND);
    this.clickImgPath = windSkillClickImg;
  }

  @Override
  public void effect(BasePlayer onWhom) {
    onWhom.damage(windDamage);
  }
}
