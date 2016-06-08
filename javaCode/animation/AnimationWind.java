package animation;

import javafx.scene.Node;
import player.Player;

public class AnimationWind extends AnimationOneToOneFly {

  public AnimationWind(Player onWhom, Node where, String name) {
    super(onWhom, where, name);
    animImgPath = "file:resources/animation/wind.gif";
  }

  public AnimationWind(String name) {
    super(name);
    animImgPath = "file:resources/animation/wind.gif";
  }

}
