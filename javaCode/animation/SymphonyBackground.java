package animation;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
/**
 * animate background at scenes.symphony, now not used
 * @author ShinShil
 *
 */
public class SymphonyBackground {
  private Timeline timeline;
  private VBox bg;
  private double deltaMove;
  private double startX;
  private double startY;

  public double getDeltaMove() {
    return deltaMove;
  }

  public void setDeltaMove(double deltaMove) {
    this.deltaMove = deltaMove;
  }

  public SymphonyBackground(VBox bg) {
    this.bg = bg;
    deltaMove = 1;
    startX = bg.getLayoutX();
    startY = bg.getLayoutY();
  }

  public void start() {
    timeline = new Timeline(new KeyFrame(Duration.millis(10), ae -> tick()));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
  }

  public void tick() {
    bg.setLayoutY(bg.getLayoutY() + deltaMove);
    if (bg.getLayoutY() >= 0) {
      bg.setLayoutX(startX);
      bg.setLayoutY(startY);
    }
  }

  public void stop() {
    if (timeline != null) {
      timeline.stop();
    }
  }
}
