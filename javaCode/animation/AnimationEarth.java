package animation;

import javafx.scene.Node;
import player.Player;

public class AnimationEarth extends AnimationOneToOneFly {

  public AnimationEarth(Player onWhom, Node where, String name) {
    super(onWhom, where, name);
    animImgPath = "file:resources/animation/earth.gif";
  }

  public AnimationEarth(String name) {
    super(name);
    animImgPath = "file:resources/animation/earth.gif";
  }

}
