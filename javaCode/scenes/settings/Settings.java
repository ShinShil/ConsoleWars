package scenes.settings;

import java.io.IOException;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import scenes.IScene;
import scenes.Window;

/**
 * settings scene
 * @author ShinShil
 *
 */
public class Settings implements IScene {
  public int startScene(Window window) {
    BorderPane root = new BorderPane();
    Label msg = new Label("Coming soon");
    msg.setFont(new Font("Arial", 36));
    msg.setAlignment(Pos.CENTER);
    root.setCenter(msg);
    Scene scene = new Scene(root, 800, 600);
    window.setScene(scene);
    window.show();
    return 0;
  }

  @Override
  public void endScene() throws IOException {

  }
}
