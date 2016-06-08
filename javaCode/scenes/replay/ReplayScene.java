package scenes.replay;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import animation.SymphonyBackground;
import application.Service;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import scenes.IScene;
import scenes.Window;
import scenes.controls.CBAutoPlay;
import scenes.controls.SkillClick;
import scenes.symphony.ISizeSymphonyDraw;

/**
 * replay scene - connects ReplayDraw.java and ReplayController.java
 * 
 * @author ShinShil
 *
 */
public class ReplayScene implements ISizeSymphonyDraw, IScene {

  /**
   * controls that are shown ReplayDraw.java and ReplayController.java use the same controls
   * 
   * @author ShinShil
   */
  protected class Controls {
    public Pane animationPlace;
    public Label hp;
    public Label def;
    public Label name;
    public HBox player1;
    public SymphonyBackground animBg;
    public ArrayList<SkillClick> skillClickList;
    public CBAutoPlay cbAutoPlay;
    public Pane root;
  }

  protected Scene scene;
  protected Controls controls;
  protected ReplayController controller;
  protected ReplayDraw draw;
  protected String fileName;

  public ReplayScene() {

  }
  public void setReplayName(String fileName) {
    this.fileName = fileName;
  }
  /**
   * called after all elements have been drawn
   */
  protected void postStart() {
    Label label = new Label();
    label.setPrefWidth(rootW - infoW);
    label.setPrefHeight(rootH);
    label.setLayoutX(infoW);
    label.setLayoutY(0);
    label.setStyle("-fx-background-color:rgba(255,255,0,0.1)");
    controls.root.getChildren().add(label);
  }

  /**
   * init all fields and display the scene
   */
  @Override
  public int startScene(Window window) throws NoSuchMethodException, SecurityException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, IOException, InterruptedException {
    controls = new Controls();
    controller = new ReplayController(controls);
    draw = new ReplayDraw(controls, controller, window);
    scene = draw.draw(window);
    controls.animBg = new SymphonyBackground((VBox) controls.animationPlace.getChildren().get(0));
    
    if (window != null) {
      window.close();
    }
    window.setScene(scene);
    window.setIScene(this);
    postStart();
    window.show();
    controller.startReplay(fileName);
    return 0;
  }

  public ReplayController getController() {
    return controller;
  }

  /**
   * actions for calling before scene window close, see scenes.Window.preChangeScene()
   * 
   * @throws InterruptedException
   * @throws InvocationTargetException
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   * @throws InstantiationException
   * @throws ClassNotFoundException
   * @throws SecurityException
   * @throws NoSuchMethodException
   */
  @Override
  public void endScene() throws IOException, NoSuchMethodException, SecurityException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, InterruptedException {
    controller.stop();
    Service.out("called");
  }
}
