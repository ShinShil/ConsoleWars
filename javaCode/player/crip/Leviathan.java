package player.crip;

import java.io.IOException;
import java.util.ArrayList;

import animation.AnimationSkill;
import animation.AnimationWater;
import application.Service;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;
import player.Player;
import player.effect.Damage;
import player.effect.DamageType;
import player.effect.Defense;
import scenes.ISceneController;
import skill.CripAttack;
import skill.SkWater;
import skill.Skill;

public class Leviathan extends Crip {
  public Leviathan(ISceneController controller) {
    super(controller);
    actionsSkills = new ArrayList<AnimationSkill>();
    imgPath = levithanSprite;
    speed = 20;
    skills = new ArrayList<Skill>();
    skills.add(new SkWater());
    hp = 2;
    defs.add(new Defense(1000, DamageType.WATER));
    defs.add(new Defense(1000, DamageType.EARTH));
    defs.add(new Defense(1000, DamageType.WIND));
    cripAttack = new CripAttack(new Damage(3, DamageType.WATER));
    spriteAmount = 4;
    spriteWidth = 80;
    actions = new Timeline(new KeyFrame(Duration.millis(2000), new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          castWater();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }));
    actions.setCycleCount(Animation.INDEFINITE);
    
    name = "Leviathan";
    weakness = DamageType.FIRE;
    forAuto = "fire";
    Service.out("generated Leviathan: " + System.currentTimeMillis());
  }

  @Override
  public void messager() {}

  public void castWater() throws IOException {
    Player onWhom = controller.getRoom().getCurrPlayer();
    Node where = null;
    String name = "water";
    AnimationWater af = new AnimationWater(onWhom, where, name);
    af.setController(controller);
    af.startByCrip(this);
    actionsSkills.add(af);
  }
}
