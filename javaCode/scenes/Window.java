package scenes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import application.ConfirmBox;
import application.Service;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import scenes.end.EndScene;
import scenes.menu.MainMenu;
import scenes.replay.ReplayScene;
import scenes.replayGenerator.ReplayGeneratorScene;
import scenes.replayViewer.DecodeReplayScene;
import scenes.replayViewer.InfoReplayScene;
import scenes.replayViewer.ReplayViewerScene;
import scenes.settings.Settings;
import scenes.symphony.SymphonyScene;

/**
 * wrapper for the Stage window methods with prefix start - realization of switching Scenes
 * 
 * @author ShinShil
 *
 */
public class Window {
  private Stage window;
  private IScene scene;

  /**
   * add to window CloseHandler
   * 
   * @param stage
   */
  public Window(Stage stage) {
    window = stage;
    window.setOnCloseRequest(e -> {
      e.consume();
      try {
        confirmCloseProgram(window);
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    });

    window.setTitle("Console wars");
    window.getIcons().add(new Image("file:resources/icon.png"));
  }


  public Stage getStage() {
    return window;
  }

  /**
   * show end scene
   * 
   * @throws InterruptedException
   * @throws NoSuchMethodException
   * @throws SecurityException
   * @throws ClassNotFoundException
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   * @throws InvocationTargetException
   * @throws IOException
   */
  public void startEnd() throws InterruptedException, NoSuchMethodException, SecurityException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, IOException {
    EndScene scene = new EndScene();
    scene.startScene(this);
  }

  /**
   * should called before next scene would be shown calls IScene scene; scene.endScene(); before use
   * this method should call setIScene
   * 
   * @throws IOException
   * @throws NoSuchMethodException
   * @throws SecurityException
   * @throws ClassNotFoundException
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   * @throws InvocationTargetException
   * @throws InterruptedException
   */
  public void preChangeScene() throws IOException, NoSuchMethodException, SecurityException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, InterruptedException {
    if (this.scene != null) {
      scene.endScene();
    }
  }

  /**
   * show settins scene
   */
  public void startSettings() {
    Settings set = new Settings();
    set.startScene(this);
  }

  /**
   * show last replay scene
   * 
   * @throws NoSuchMethodException
   * @throws SecurityException
   * @throws ClassNotFoundException
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   * @throws InvocationTargetException
   * @throws IOException
   * @throws InterruptedException
   */
  public void startReplay(String fileName) throws NoSuchMethodException, SecurityException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, IOException, InterruptedException {
    ReplayScene replSc = new ReplayScene();
    replSc.setReplayName(fileName);
    replSc.startScene(this);
  }

  public void startReplayGenerator() throws NoSuchMethodException, SecurityException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, IOException, InterruptedException {
    ReplayGeneratorScene replGen = new ReplayGeneratorScene();
    replGen.startScene(this);
    Service.out("startReplaGen");
  }

  public void startInfoReplay(String fileName) throws NoSuchMethodException, SecurityException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, IOException, InterruptedException {
    InfoReplayScene infoReplSc = new InfoReplayScene(fileName);
    infoReplSc.startScene(this);
  }
  
  public void startDecodeReplay(String fullFileName) throws NoSuchMethodException,
      SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, IOException, InterruptedException {
    DecodeReplayScene decReplSc = new DecodeReplayScene(fullFileName);
    decReplSc.startScene(this);
  }

  public void startReplayViewer() throws NoSuchMethodException, SecurityException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, IOException, InterruptedException {
    ReplayViewerScene replView = new ReplayViewerScene();
    replView.startScene(this);
  }

  public void setScene(Scene scene) {
    window.setScene(scene);
  }

  /**
   * set IScene - obvious if you use preChangeScene
   * 
   * @param scene
   */
  public void setIScene(IScene scene) {
    this.scene = scene;
  }

  public void show() {
    window.show();
  }

  public void startMenu() {
    MainMenu menu = new MainMenu();
    menu.startScene(this);
  }

  public void startSymphony() throws NoSuchMethodException, SecurityException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, IOException, InterruptedException {
    SymphonyScene symphony = new SymphonyScene();
    symphony.startScene(this);
  }

  public void close() {
    window.close();
  }

  public void confirmCloseProgram(Stage window) throws IOException, NoSuchMethodException,
      SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, InterruptedException {
    boolean answer = ConfirmBox.display("Exit?", "Are you sure you want to exit?");
    if (answer) {
      window.close();
      if (scene != null) {
        scene.endScene();
      }
    }
  }



}
