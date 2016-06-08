package skill;

import player.BasePlayer;
import player.effect.Damage;

public class CripAttack extends Skill {
  private Damage damage;

  public CripAttack(Damage damage) {
    super(cripAtackName);
    this.damage = damage;
  }

  @Override
  public void effect(BasePlayer onWhom) {
    onWhom.damage(damage);
  }
}
