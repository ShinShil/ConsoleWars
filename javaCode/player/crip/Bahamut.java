package player.crip;

import java.io.IOException;
import java.util.ArrayList;

import animation.AnimationFire;
import animation.AnimationSkill;
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
import skill.SkFire;
import skill.Skill;

public class Bahamut extends Crip {
  public Bahamut(ISceneController controller) {
    super(controller);
    actionsSkills = new ArrayList<AnimationSkill>();
    imgPath = bahamutSprite;
    speed = 30;
    skills = new ArrayList<Skill>();
    skills.add(new SkFire());
    hp = 5;
    defs.add(new Defense(1000, DamageType.FIRE));
    defs.add(new Defense(1000, DamageType.EARTH));
    defs.add(new Defense(1000, DamageType.WIND));
    cripAttack = new CripAttack(new Damage(3, DamageType.FIRE));
    spriteAmount = 4;
    spriteWidth = 80;
    actions = new Timeline(new KeyFrame(Duration.millis(4000), new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        try {
          castFire();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }));
    actions.setCycleCount(Animation.INDEFINITE);
    name = "Bahamut";
    weakness = DamageType.WATER;
    forAuto = "water";
  }

  @Override
  public void messager() {}

  public void castFire() throws IOException {
    Player onWhom = controller.getRoom().getCurrPlayer();
    Node where = null;
    String name = "fire";
    AnimationFire af = new AnimationFire(onWhom, where, name);
    af.setController(controller);
    af.startByCrip(this);
    actionsSkills.add(af);
  }
}
