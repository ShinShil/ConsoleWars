package scenes.symphony;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import animation.SymphonyBackground;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import scenes.IScene;
import scenes.Window;
import scenes.controls.CBAutoPlay;
import scenes.controls.SkillClick;
import scenes.replay.ReplayController;

/**
 * symphony scene
 * connects controller and draw
 * run the scene
 * stop animations timelines before closing
 * @author ShinShil
 *
 */
public class SymphonyScene implements IScene {

  protected class Controls {
    public Pane animationPlace;
    public Label hp;
    public Label def;
    public Label name;
    public HBox player1;
    public TextField input;
    public SymphonyBackground animBg;
    public ArrayList<SkillClick> skillClickList;
    public CBAutoPlay cbAutoPlay;
    public Pane root;
  }

  protected Scene scene;
  protected Controls controls;
  protected SymphonyController controller;
  protected SymphonyDraw draw;

  @Override
  public int startScene(Window window) throws NoSuchMethodException, SecurityException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, IOException, InterruptedException {
    if (window != null) {
      window.close();
    }
    init(window);
    window.setIScene(this);
    window.setScene(scene);
    window.show();
    return 0;
  }

  private void init(Window window) throws NoSuchMethodException, ClassNotFoundException,
      InstantiationException, IllegalAccessException, InvocationTargetException, SecurityException,
      IllegalArgumentException, IOException, InterruptedException {
    controls = new Controls();
    controller = new SymphonyController(controls, window);
    draw = new SymphonyDraw(controls, controller, window);
    scene = draw.draw(window);
    controls.animBg = new SymphonyBackground((VBox) controls.animationPlace.getChildren().get(0));
    controller.startGenerateCrips();
    controller.refreshVals();
    FileWriter fw = new FileWriter("lastReplay.txt", false);
    fw.close();
  }

  public void endScene() throws IOException, NoSuchMethodException, SecurityException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, InterruptedException {
    controller.stop();

    ReplayController.saveActionLastReplay("exit", "exit", "exit");
  }
}
