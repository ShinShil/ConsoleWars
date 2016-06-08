package scenes.controls;

import javafx.scene.control.CheckBox;

/**
 * controll for switching bot
 * @author ShinShil
 *
 */
public class CBAutoPlay extends CheckBox {
  public CBAutoPlay(boolean val) {
    super();
    this.setIndeterminate(false);
    this.setSelected(val);
  }
}
