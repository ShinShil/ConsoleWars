package skill;

import player.BasePlayer;
import player.effect.Damage;
import player.effect.DamageType;

public class SkFire extends Skill  {
  private Damage fireDamage;

  public SkFire() {
    super(fireSkillName);
    fireDamage = new Damage(fireSkillDamage, DamageType.FIRE);
    this.clickImgPath = fireSkillClickImg;
  }

  @Override
  public void effect(BasePlayer onWhom) {
    onWhom.damage(fireDamage);
  }
}
