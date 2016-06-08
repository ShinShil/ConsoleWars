package scenes.menu;

import java.io.IOException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Light.Distant;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import scenes.IScene;
import scenes.Window;

/**
 * main menu
 * @author ShinShil
 *
 */
public class MainMenu implements IScene {

  private void setBtn(Button btn) {
    btn.setCursor(Cursor.HAND);
    btn.defaultButtonProperty().bind(btn.focusedProperty());
    btn.setPrefWidth(200);
    btn.setFont(new Font("Arial", 24));

    Distant light = new Distant();
    light.setAzimuth(-135.0f);
    Lighting l = new Lighting();
    l.setLight(light);
    l.setSurfaceScale(5.0f);

    btn.setEffect(l);
  }

  public int startScene(Window window) {
    VBox root = new VBox();
    VBox menu = new VBox();
    Label header = new Label("Console Wars");
    header.setFont(Font.font("arial", FontWeight.BOLD, 40));
    header.setTextFill(Color.AQUAMARINE);
    header.setPadding(new Insets(20, 0, 20, 0));
    header.setAlignment(Pos.BASELINE_CENTER);
    header.setPrefWidth(600);

    Distant light = new Distant();
    light.setAzimuth(-135.0f);
    Lighting l = new Lighting();
    l.setLight(light);
    l.setSurfaceScale(5.0f);

    header.setEffect(l);


    Button settings = new Button("Settings");
    settings.setOnAction(e -> {
      window.startSettings();
    });
    Button exit = new Button("Exit Game");
    exit.setOnAction(e -> {
      try {
        window.confirmCloseProgram(window.getStage());
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    });
    Button symphony = new Button("Symphony");
    symphony.setOnAction(e -> {
      try {
        window.startSymphony();
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    });
    Button replay = new Button("Replay");
    replay.setOnAction(e -> {
      try {
        window.startReplay("lastReplay.txt");
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    });
    Button replayGenerator = new Button("Replay generator");
    replayGenerator.setOnAction(e -> {
      try {
        window.startReplayGenerator();
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    });
    Button replayViewer = new Button("Replay viewer");
    replayViewer.setOnAction(e -> {
      try {
        window.startReplayViewer();
      }catch(Exception e1) {
        e1.printStackTrace();
      }
    });
    setBtn(replayViewer);
    setBtn(replayGenerator);
    setBtn(replay);
    setBtn(symphony);
    setBtn(settings);
    setBtn(exit);
    menu.getChildren().addAll(symphony, replay, replayGenerator, replayViewer, settings, exit);
    menu.setAlignment(Pos.BASELINE_CENTER);
    menu.setSpacing(15);

    Label copyright = new Label("By Nikita Starostin");
    copyright.setFont(Font.font("arial", FontWeight.BOLD, 40));
    copyright.setTextFill(Color.AQUAMARINE);
    copyright.setPadding(new Insets(20, 0, 20, 0));
    copyright.setAlignment(Pos.BASELINE_CENTER);
    copyright.setPrefWidth(600);
    copyright.setEffect(l);
    copyright.setPadding(new Insets(80, 0, 0, 0));

    root.getStylesheets().add("application.css");
    root.getStyleClass().add("root");
    root.getChildren().addAll(header, menu, copyright);
    BackgroundImage bi = new BackgroundImage(new Image("file:resources/theme/mainBg.jpg"),
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
        new BackgroundPosition(Side.LEFT, 0, false, Side.TOP, 0, false), BackgroundSize.DEFAULT);
    root.setBackground(new Background(bi));
    Scene scene = new Scene(root, 600, 500);

    window.setScene(scene);
    window.show();
    return 0;
  }

  @Override
  public void endScene() throws IOException {

  }
}
