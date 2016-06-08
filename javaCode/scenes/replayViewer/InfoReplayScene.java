package scenes.replayViewer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import scenes.IScene;
import scenes.Window;
import sortReplays.InfoReplays;

public class InfoReplayScene implements IScene, ISizeReplayViewerDraw {

  private String fileName;
  private Window window;
  public InfoReplayScene(String fileName) {
    this.fileName = fileName;
  }
  @Override
  public int startScene(Window window) throws NoSuchMethodException, SecurityException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, IOException, InterruptedException {
    this.window = window;
    Scene scene = new Scene(createContent(fileName));
    window.setScene(scene);
    window.show();
    return 0;
  }

  @Override
  public void endScene() throws IOException, NoSuchMethodException, SecurityException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, InterruptedException {
  }
  
  private Parent createContent(String fileName) {
    
    Pane root = new Pane();
    Label headerLabel = new Label();
    VBox gameMapWrapper = new VBox();
    HBox btns = new HBox();
    
    root.getChildren().addAll(headerLabel, gameMapWrapper, btns);
    
    String[] tmp = fileName.split("/");
    headerLabel.setText(tmp[tmp.length - 1]);
    headerLabel.setPrefWidth(rootWidth);
    headerLabel.setAlignment(Pos.BASELINE_CENTER);
    headerLabel.setFont(Font.font("arial", FontWeight.BOLD, 32));
    headerLabel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null,
        new BorderWidths(2))));
    headerLabel.setPadding(new Insets(10, 0, 10, 0));
    
    gameMapWrapper.setLayoutY(100);
    InfoReplays infoRepls = new InfoReplays();
    String[] info = infoRepls.getReplayInfo(fileName);
    for(int i = 0; i< info.length; ++i) {
      gameMapWrapper.getChildren().add(createInfoLabel(info[i]));
    }
    btns.setLayoutY(250);
    btns.getChildren().add(createBtnMenu());
    btns.getChildren().add(createBtnReplays());
    btns.getChildren().add(createBtnReplaysGenerator());
    
    return root;
  }
  
  private Label createInfoLabel(String info) {
    Label label = new Label(info);
    return label;
  }
  
  private Button createBtnMenu() {
    Button menu = new Button("Меню");
    menu.setOnAction(e -> {
      try {
        window.preChangeScene();
      } catch (Exception e1) {
        e1.printStackTrace();
      }
      this.window.startMenu();
    });
    return menu;
  }
  private Button createBtnReplaysGenerator() {
    Button menu = new Button("Генератор реплеев");
    menu.setOnAction(e -> {
      try {
        window.preChangeScene();
      } catch (Exception e1) {
        e1.printStackTrace();
      }
      try {
        this.window.startReplayGenerator();
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    });
    return menu;
  }
  
  private Button createBtnReplays() {
    Button replayViewer = new Button("Просмотр доступных реплеев");
    replayViewer.setOnAction(e -> {
      try {
        window.startReplayViewer();
      }catch(Exception e1) {
        e1.printStackTrace();
      }
    });
    return replayViewer;
  }
}
