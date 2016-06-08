package skill;

import application.Service;
import player.BasePlayer;
import player.effect.Damage;
import player.effect.DamageType;

public class SkWater extends Skill {

  private Damage waterDamage;

  public SkWater() {
    super(waterSkillName);
    waterDamage = new Damage(waterSkillDamage, DamageType.WATER);
    this.clickImgPath = waterSkillClickImg;
  }

  @Override
  public void effect(BasePlayer onWhom) {
    onWhom.damage(waterDamage);
    if(onWhom.getClass().getSimpleName().equals("Player")) {
      
      Service.out("water damage by Leviathan: " + System.currentTimeMillis());
    }
  }
}
