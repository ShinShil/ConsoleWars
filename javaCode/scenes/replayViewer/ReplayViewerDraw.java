package scenes.replayViewer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

import application.Service;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import scenes.ISceneDraw;
import scenes.Window;
import sortReplays.SortReplays;

public class ReplayViewerDraw implements ISceneDraw, ISizeReplayViewerDraw {

  Window window;
  ReplayViewerController controller;
  @Override
  public Scene draw(Window window) throws NoSuchMethodException, SecurityException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, IOException {
    Scene scene = new Scene(createContent());
    this.window = window;
    return scene;
  }

  //what the problem??? ScrollBar is realy horrible realization
  Parent createContent() throws IOException {
    Pane root = new Pane();
    VBox replaysVerticalBox = new VBox();
    ScrollBar replaysScroll = new ScrollBar();
    Label headerLabel = new Label("Записанные реплеи");
    Pane replaysWrapper = new Pane();
    HBox btnsJavaSort = new HBox();
    HBox btnsScalaSort = new HBox();
    HBox btns = new HBox();
    VBox btnsWrapper = new VBox();
    VBox replaysVerticalBoxGameLength = new VBox();
    VBox replaysVerticalBoxGameInfoBtns = new VBox();
    VBox replaysVerticalBoxGameDecodeBtns = new VBox();
    controller = new ReplayViewerController(replaysVerticalBox, replaysVerticalBoxGameLength);
    btnsWrapper.getChildren().addAll(btnsJavaSort, btnsScalaSort, btns);
    btnsWrapper.setLayoutY(menuY);
    root.getChildren().addAll(headerLabel, replaysWrapper, replaysScroll, btnsWrapper);
    root.setPrefHeight(rootHeight);
    replaysWrapper.getChildren().addAll(replaysVerticalBox, replaysVerticalBoxGameLength, replaysVerticalBoxGameInfoBtns, replaysVerticalBoxGameDecodeBtns);
    replaysWrapper.setLayoutY(secondLineY);
    replaysWrapper.setClip(new Rectangle(600, 260));
    headerLabel.setPrefWidth(rootWidth);
    headerLabel.setAlignment(Pos.BASELINE_CENTER);
    headerLabel.setFont(Font.font("arial", FontWeight.BOLD, 32));
    headerLabel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null,
        new BorderWidths(2))));
    headerLabel.setPadding(new Insets(10, 0, 10, 0));
    
    File replaysFolder = new File("replays");
    File[] replayFiles = replaysFolder.listFiles();
    btns.getChildren().addAll(createBtnMenu(), createBtnReplayGenerator(), createBtnDeleteReplays(replayFiles));
    Label forScala = new Label("Сортировки scala: ");
    btnsScalaSort.getChildren().addAll(forScala, createBtnScalaSort(replayFiles), createBtnScalaSortLength(replayFiles));
    Label forJava = new Label("Сортировки java:  ");
    btnsJavaSort.getChildren().addAll(forJava, createBtnJavaSort(replayFiles), createBtnJavaSortLength(replayFiles));
    for (int i = 0; i < replayFiles.length; ++i) {
      replaysVerticalBox.getChildren().add(createReplayRecord(replayFiles[i].getName()));
      replaysVerticalBoxGameLength.getChildren().add(createReplayRecord(ReplayViewerController.getGameLength(replayFiles[i]) + ""));
      replaysVerticalBoxGameInfoBtns.getChildren().add(createBtnInfoGame(replayFiles[i].getAbsolutePath()));
      replaysVerticalBoxGameDecodeBtns.getChildren().add(createBtnDecodeGame(replayFiles[i].getAbsolutePath()));
    }
    replaysVerticalBox.setPrefWidth(replaysWidht);
    replaysVerticalBoxGameLength.setPrefWidth(replaysWidht);
    replaysVerticalBoxGameLength.setLayoutX(replaysWidht);
    replaysVerticalBoxGameDecodeBtns.setPrefWidth(replaysWidht);
    replaysVerticalBoxGameDecodeBtns.setLayoutX(colWidth*3);
    replaysVerticalBoxGameInfoBtns.setLayoutX(colWidth*2);
    replaysVerticalBoxGameInfoBtns.setPrefWidth(replaysWidht);
    replaysVerticalBox.setPadding(new Insets(0,0,50,0));
    Service.out((replaysVerticalBox.getWidth() - replaysScroll.getWidth()) + "");
    replaysScroll.setLayoutX(rootWidth - replaysScroll.getWidth());
    replaysScroll.setLayoutY(secondLineY);
    replaysScroll.setMin(0);
    replaysScroll.setMax(replayFiles.length);
    replaysScroll.setOrientation(Orientation.VERTICAL);
    replaysScroll.setPrefHeight(replaysScrollHeight);
    replaysScroll.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> arg0, Number oldVal, Number newVal) {
        replaysVerticalBox.setLayoutY(-(Double)newVal*20);
        replaysVerticalBoxGameLength.setLayoutY(-(Double)newVal * 20);
        replaysVerticalBoxGameDecodeBtns.setLayoutY(-(Double)newVal * 20);
        replaysVerticalBoxGameInfoBtns.setLayoutY(-(Double)newVal * 20);
      }
        
    });
    return root;
  }

  private Button createBtnDecodeGame(String fullFileName) {
    Button btn = new Button("Decode");
    btn.setOnAction(e-> {
      try {
        window.preChangeScene();
      } catch (Exception e1) {
        e1.printStackTrace();
      }
      try {
        this.window.startDecodeReplay(fullFileName);
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    });
    return btn;
  }
  
  private Button createBtnInfoGame(String fileName) {
    Button btn = new Button("Info");
    btn.setOnAction(e -> {
        try {
          window.preChangeScene();
        } catch (Exception e1) {
          e1.printStackTrace();
        }
        try {
          this.window.startInfoReplay(fileName);
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      
    });
    return btn;
  }
  
  // better to return, because in this case we can reuse this code if need in ohter case
  private Label createReplayRecord(String name) {
    Label label = new Label(name);
    label.setCursor(Cursor.HAND);
    label.setFont(Font.font("arial", FontWeight.SEMI_BOLD, 16));
    //label.setPadding(new Insets(0, 0, 0, 100));
    label.setOnMouseClicked((e -> {
      try {
        window.startReplay("replays/" + name);
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    }));
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
  private Button createBtnReplayGenerator() {
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
  private Button createBtnDeleteReplays(File[] files) {
    Button menu = new Button("Удалить все реплеи");
    menu.setOnAction(e -> {
      for(int i = 0; i<files.length; ++i) {
        files[i].delete();
      }
    });
    return menu;
  }
  private Button createBtnScalaSort(File[] files) {
    Button btn = new Button("По дате игры");
    btn.setOnAction(e -> {
      Service.out("scala sorting by date");
      long t1 = System.currentTimeMillis();
      ArrayList<File> fileList = new ArrayList<File>(Arrays.asList(files));
      SortReplays sort = new SortReplays(fileList);
      File[] fileArray = (File[]) sort.sort("date");
      Service.out("scala sort by date, time spend: " + (System.currentTimeMillis() - t1));
      controller.refreshReplays(fileArray);
      try {
        controller.refreshLengths(sort.files().toArray(new File[0]));
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    });
    return btn;
  }
  private Button createBtnScalaSortLength(File[] files) {
    Button btn = new Button("По длине игры");
    btn.setOnAction(e -> {
      Service.out("scala sorting by length");
      long t1 = System.currentTimeMillis();
      ArrayList<File> fileList = new ArrayList<File>(Arrays.asList(files));
      SortReplays sort = new SortReplays(fileList);
      File[] fileArray = (File[]) sort.sort("length");
      Service.out("scala sort by length, time spend: " + (System.currentTimeMillis() - t1));
      try {
        controller.refreshLengths(fileArray);
        controller.refreshReplays(fileArray);
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    });
    return btn;
  }
  private Button createBtnJavaSort(File[] files) {
    Button btn = new Button("По дате игры");
    btn.setOnAction(e -> {
      Service.out("java sorting by date");
      long t1 = System.currentTimeMillis();
      ArrayList<File> fileList = new ArrayList<File>(Arrays.asList(files));
      controller.sortByDate(fileList);
      Service.out("java sort by date, time spend: " + (System.currentTimeMillis() - t1));
      controller.refreshReplays(fileList.toArray(new File[0]));
      try {
        controller.refreshLengths(fileList.toArray(new File[0]));
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    });
    return btn;
  }
  private Button createBtnJavaSortLength(File[] files) {
    Button btn = new Button("По длине игры");
    btn.setOnAction(e -> {
      Service.out("java sorting by length");
      long t1 = System.currentTimeMillis();
      ArrayList<File> fileList = new ArrayList<File>(Arrays.asList(files));
      controller.sortLengths(fileList);
      Service.out("java sort by length, time spend: " + (System.currentTimeMillis() - t1));
      try {
        controller.refreshLengths(fileList.toArray(new File[0]));
        controller.refreshReplays(fileList.toArray(new File[0]));
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    });
    return btn;
  }
}
