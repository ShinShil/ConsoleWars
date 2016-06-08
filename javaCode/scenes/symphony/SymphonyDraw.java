package scenes.symphony;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import scenes.ISceneDraw;
import scenes.Window;
import scenes.controls.CBAutoPlay;
import scenes.controls.SkillClick;
import skill.Skill;

/**
 * draw the Scene and set events on controls
 * @author ShinShil
 *
 */
public class SymphonyDraw implements ISceneDraw, ISizeSymphonyDraw {

  Window window;
  SymphonyScene.Controls controls;
  SymphonyController controller;
  
  public SymphonyDraw(SymphonyScene.Controls controls, SymphonyController controller, Window window) {
    this.controls = controls;
    this.window = window;
    this.controller = controller;
  }

  @Override
  public Scene draw(Window window) {
    init();
    Scene scene = new Scene(createContent());
    setEvents();
    return scene;
  }

  private void init() {
    controls.hp = new Label();
    controls.def = new Label();
    controls.name = new Label();
    controls.player1 = new HBox();
    controls.animationPlace = new Pane();
    controls.skillClickList = new ArrayList<SkillClick>();
  }

  private void setEvents() {
    System.out.println("HI");
    for (int i = 0; i < controls.player1.getChildren().size(); ++i) {
      Label tmp = (Label) controls.player1.getChildren().get(i);
      tmp.setOnMouseClicked(e -> {
        try {
          controller.clickCast(tmp);
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      });
    }
    controls.cbAutoPlay.setOnMouseClicked(e -> controller.switchAutoPlay());

    controls.cbAutoPlay.setOnKeyPressed(e -> {
      if (e.getCode().getName().equals("Space")) {
        controller.switchAutoPlay();
      }
      if (e.getCode().getName().equals("F1")) {
        controller.changeActiveSkill("fire");
      }
      if (e.getCode().getName().equals("F2")) {
        controller.changeActiveSkill("water");
      }
      if (e.getCode().getName().equals("F3")) {
        controller.changeActiveSkill("earth");
      }
      if (e.getCode().getName().equals("F4")) {
        controller.changeActiveSkill("wind");
      }
      if (e.getCode().getName().equals("1")) {
        try {
          controller.clickCast((Label) (controls.player1.getChildren().get(0)));
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
      if (e.getCode().getName().equals("2")) {
        try {
          controller.clickCast((Label) (controls.player1.getChildren().get(1)));
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
      if (e.getCode().getName().equals("3")) {
        try {
          controller.clickCast((Label) (controls.player1.getChildren().get(2)));
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
      if (e.getCode().getName().equals("4")) {
        try {
          controller.clickCast((Label) (controls.player1.getChildren().get(3)));
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
      if (e.getCode().getName().equals("5")) {
        try {
          controller.clickCast((Label) (controls.player1.getChildren().get(4)));
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
      if (e.getCode().getName().equals("6")) {
        try {
          controller.clickCast((Label) (controls.player1.getChildren().get(5)));
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
      if (e.getCode().getName().equals("7")) {
        try {
          controller.clickCast((Label) (controls.player1.getChildren().get(6)));
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
      if (e.getCode().getName().equals("8")) {
        try {
          controller.clickCast((Label) (controls.player1.getChildren().get(7)));
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
      if (e.getCode().getName().equals("9")) {
        try {
          controller.clickCast((Label) (controls.player1.getChildren().get(8)));
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
    });
  }

  private Parent createContent() {
    Pane root = new Pane();
    root.setBackground(
        new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), new Insets(0))));
    VBox infoGame = createInfoGame(controls.hp, controls.def, controls.name);
    controls.player1 = createPlayer();
    controls.animationPlace = drawBg();
    controls.input = createInput();
    controls.player1.setLayoutX(infoW);
    controls.player1.setLayoutY(canvasH);

    root.setPrefWidth(rootW);
    root.setPrefHeight(rootH);
    root.getChildren().addAll(infoGame, controls.animationPlace, controls.player1, controls.input);
    ArrayList<Skill> clickSkills = controller.room.getCurrPlayer().getSkillClickList();
    for (int i = 0; i < clickSkills.size(); ++i) {
      Skill tmp = clickSkills.get(i);
      controls.skillClickList.add(new SkillClick(tmp.getClickImgPath(), i * clickW, canvasH, clickW,
          clickH, tmp.getName()));
      root.getChildren().add(controls.skillClickList.get(i).getImage());
    }
    controls.root = root;
    return root;
  }

  private VBox createInfoGame(Label hp, Label def, Label name) {
    VBox infoGame = new VBox();
    infoGame.setPrefSize(infoW, infoH);
    infoGame.setStyle("-fx-background-color:rgba(255,255,0,0.5)");
    infoGame.setLayoutX(0);
    infoGame.setLayoutY(0);

    HBox hitPoint = makeControlledLabel("Hit points: ", hp);
    HBox namePlayer = makeControlledLabel("Name: ", name);
    setStylePlayerInfo(hp);
    setStylePlayerInfo(name);
    VBox playerInfo = new VBox();
    Label book = new Label("Book:");
    book.setFont(Font.font("arial", 16));
    playerInfo.getChildren().addAll(namePlayer, hitPoint);
    playerInfo.getChildren().add(book);
    Label skill = new Label("\tfire");
    skill.setFont(Font.font("arial", 16));
    playerInfo.getChildren().add(skill);

    controls.cbAutoPlay = createCBAutoPlay();
    infoGame.getChildren().add(playerInfo);
    infoGame.getChildren().add(controls.cbAutoPlay);

    Button btnMenu = makeBtnMenu();
    infoGame.getChildren().add(btnMenu);
    return infoGame;
  }

  private HBox createPlayer() {
    HBox player = new HBox();
    // hBox has only labels, so player1.getChildren().get(index) - will be enough, instead of
    // creating playerLabels lists
    for (int i = 1; i < towerAmount + 1; ++i) {
      Label dummy = new Label(String.format("%d", i));
      dummy.setFont(Font.font("arial", dummyFont));
      dummy.setTextFill(Color.RED);
      dummy.setBorder(new Border(
          new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
      dummy.setPadding(new Insets(dummyInsetsPadding));
      dummy.setPrefSize(dummyW, dummyH);
      dummy.setAlignment(Pos.CENTER);
      dummy.setCursor(Cursor.HAND);
      player.getChildren().add(dummy);
      player.toFront();
    }

    return player;

  }

  private Pane drawBg() {
    Image bg = new Image("file:resources/theme/2.jpg");
    VBox animContainer = new VBox();
    int rows = 10, cols = 10;
    double width = canvasW / cols;
    double height = canvasH / rows;

    for (int i = 0; i < rows + 1; ++i) {
      HBox tmp = new HBox();
      for (int j = 0; j < cols + 1; ++j) {
        Canvas canvas = new Canvas(width, height);
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.drawImage(bg, 0, 0, width, height);
        canvas.setLayoutX(0);
        canvas.setLayoutY(0);
        tmp.getChildren().add(canvas);
      }
      animContainer.getChildren().add(tmp);
    }
    // animContainer.setClip(new Rectangle(canvasW, canvasH));

    animContainer.setLayoutX(0);
    animContainer.setLayoutY(
        -((Canvas) ((HBox) animContainer.getChildren().get(0)).getChildren().get(0)).getHeight());
    Pane anim = new Pane();
    anim.setPrefWidth(canvasW);
    anim.setPrefHeight(canvasH);
    anim.setLayoutX(infoW);
    anim.setLayoutY(0);
    anim.getChildren().add(animContainer);
    anim.setClip(new Rectangle(canvasW, canvasH));
    // anim.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DASHED, new
    // CornerRadii(0), new BorderWidths(2), new Insets(0))));
    return anim;
  }

  private TextField createInput() {
    TextField input = new TextField();
    input.setPrefWidth(rootW);
    input.setLayoutX(0);
    input.setLayoutY(canvasH + dummyH);
    input.setCursor(Cursor.HAND);
    return input;
  }

  private void setStylePlayerInfo(Label label) {
    label.setFont(Font.font("arial", 16));
  }

  private HBox makeControlledLabel(String name, Label label) {
    HBox res = new HBox();
    Label tmp = new Label(name);
    setStylePlayerInfo(tmp);
    if (label == null) {
      System.out.println("ERRO");
      return null;
    }
    res.getChildren().addAll(tmp, label);
    return res;
  }

  private CBAutoPlay createCBAutoPlay() {
    CBAutoPlay res = new CBAutoPlay(false);
    res.setText("Двое из ларца");
    res.setCursor(Cursor.HAND);
    return res;
  }

  private Button makeBtnMenu() {
    Button btn = new Button("Menu");
    btn.setOnAction(e -> {
      try {
        window.preChangeScene();
      } catch (Exception e1) {
        e1.printStackTrace();
      }
      this.window.startMenu();
    });
    return btn;
  }
}
