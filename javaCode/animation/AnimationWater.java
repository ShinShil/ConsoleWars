package animation;

import javafx.scene.Node;
import player.Player;

public class AnimationWater extends AnimationOneToOneFly {

  public AnimationWater(Player onWhom, Node where, String name) {
    super(onWhom, where, name);
    animImgPath = "file:resources/animation/55x80_water.gif";
  }

  public AnimationWater(String name) {
    super(name);
    animImgPath = "file:resources/animation/55x80_water.gif";
  }

}
