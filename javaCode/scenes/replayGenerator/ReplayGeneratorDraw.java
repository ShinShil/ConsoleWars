package scenes.replayGenerator;



import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import application.Service;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import scenes.ISceneDraw;
import scenes.Window;

public class ReplayGeneratorDraw implements ISceneDraw, ISizeReplayGeneratorDraw {

  Window window;
  ReplayGeneratorScene.Controls controls;
  ReplayGeneratorController controller;

  public ReplayGeneratorDraw(ReplayGeneratorScene.Controls controls,
      ReplayGeneratorController controller, Window window) {
    this.controls = controls;
    this.window = window;
    this.controller = controller;
  }

  @Override
  public Scene draw(Window window) throws NoSuchMethodException, SecurityException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, IOException {
    Scene scene = new Scene(createContent());
    return scene;
  }

  private Parent createContent() throws NoSuchMethodException, SecurityException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, IOException {
    Pane root = new Pane();
    root.setPrefWidth(rootWidth);
    root.setPrefHeight(rootHeight);

    VBox mainWrapper = new VBox();
    HBox header = new HBox();
    HBox messageInput = new HBox();

    Label headerLabel = new Label("Генератор реплеев");
    headerLabel.setPrefWidth(rootWidth);
    headerLabel.setAlignment(Pos.BASELINE_CENTER);
    headerLabel.setFont(Font.font("arial", FontWeight.BOLD, 32));
    headerLabel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null,
        new BorderWidths(2), new Insets(25))));
    headerLabel.setPadding(new Insets(10, 0, 10, 0));

    header.getChildren().add(headerLabel);

    Label amountLabel = new Label("1)Введите количество генерируемых реплеев");
    amountLabel.setFont(Font.font("arial", FontWeight.SEMI_BOLD, 16));
    messageInput.setPrefWidth(rootWidth);
    Service.out(amountLabel.getWidth() + "");
    messageInput.setAlignment(Pos.BASELINE_CENTER);

    TextField amountText = new TextField();
    amountText.setPrefWidth(amountLabel.getWidth());
    messageInput.getChildren().addAll(amountLabel);

    Label step2 = new Label("2)Нажмите кнопку \"Генерировать\" или \"Меню\"");
    step2.setFont(Font.font("arial", FontWeight.SEMI_BOLD, 16));
    step2.setPrefWidth(rootWidth);
    step2.setAlignment(Pos.BASELINE_CENTER);
    Button generate = new Button("Генерировать");
    generate.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        try {
          controller.generateReplays(Integer.parseInt(amountText.getText()));
        } catch (NoSuchMethodException | SecurityException | ClassNotFoundException
            | InstantiationException | IllegalAccessException | IllegalArgumentException
            | InvocationTargetException | IOException e) {
          e.printStackTrace();
        }
      }
    });
    Button replayViewer = new Button("Просмор доступных реплеев");
    replayViewer.setOnAction(e -> {
      try {
        window.startReplayViewer();
      }catch(Exception e1) {
        e1.printStackTrace();
      }
    });
    Button menu = new Button("Меню");
    menu.setOnAction(e -> {
      try {
        window.preChangeScene();
      } catch (Exception e1) {
        e1.printStackTrace();
      }
      this.window.startMenu();
    });
    

    Label step3 = new Label("3)Прогресс генерации");
    step3.setFont(Font.font("arial", FontWeight.SEMI_BOLD, 16));
    step3.setPrefWidth(rootWidth);
    step3.setAlignment(Pos.BASELINE_CENTER);

    // progress bar, 1 replays of 1000

    mainWrapper.getChildren().addAll(header, messageInput, amountText, step2, generate, menu, replayViewer, step3);
    root.getChildren().add(mainWrapper);
    return root;
  }



}
