package scenes.end;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import scenes.IScene;
import scenes.Window;

/**
 * scene when the game ends
 * @author ShinShil
 *
 */
public class EndScene implements IScene {

  @Override
  public int startScene(Window window) throws NoSuchMethodException, SecurityException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, IOException, InterruptedException {
    Pane root = new Pane();
    root.setPrefWidth(100);
    root.setPrefHeight(100);
    Label label = new Label("Игра окончена");
    Button btn = new Button("Ok");
    btn.setOnAction(e -> {
      window.startMenu();
    });
    VBox vbox = new VBox();
    vbox.getChildren().addAll(label, btn);
    root.getChildren().add(vbox);
    Scene scene = new Scene(root);
    if (window != null) {
      window.close();
    }
    window.setScene(scene);
    window.show();
    return 0;
  }

  @Override
  public void endScene() throws IOException {

  }

}
