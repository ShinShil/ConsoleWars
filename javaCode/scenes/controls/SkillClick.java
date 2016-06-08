package scenes.controls;

import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import player.Player;

/**
 * control for skills that swtiched by pressing (f1,f2,f3,f4)
 * @author ShinShil
 *
 */
public class SkillClick {
  private ImageView iv;
  String skill;
  private String path;

  public SkillClick(String path, double lx, double ly, double w, double h, String skill) {
    this.path = path;
    iv = new ImageView(new Image(path));
    iv.setLayoutX(lx);
    iv.setLayoutY(ly);
    Rectangle2D viewPort = new Rectangle2D(0, 0, w, h);
    iv.setViewport(viewPort);
    iv.setCursor(Cursor.HAND);
    this.skill = skill;
  }

  public void setActive() {
    StringBuffer np = new StringBuffer(path);
    np.insert(np.indexOf("."), "Active");
    iv.setImage(new Image(np.toString()));
  }

  public void setDisable() {
    iv.setImage(new Image(path));
  }

  public String getSkill() {
    return skill;
  }

  public ImageView getImage() {
    return iv;
  }

  public void update(Player p) {
    if (p.getSkilClick().getName() == this.skill) {
      this.setActive();
    } else {
      this.setDisable();
    }
  }

  public void clicked() {
    this.setActive();
  }
}
