package animation;


import javafx.scene.Node;
import player.Player;

public class AnimationFire extends AnimationOneToOneFly {

  public AnimationFire(Player onWhom, Node where, String name) {
    super(onWhom, where, name);
    animImgPath = "file:resources/animation/fire7.gif";
  }

  public AnimationFire(String name) {
    super(name);
    animImgPath = "file:resources/animation/fire7.gif";
  }
}
