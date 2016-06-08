package scenes.replayGenerator;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javafx.scene.Scene;
import scenes.IScene;
import scenes.Window;

public class ReplayGeneratorScene implements IScene {
  
  class Controls {
    
  }
  protected ReplayGeneratorController controller;
  protected ReplayGeneratorDraw draw;
  protected Controls controls;
  protected Scene scene;
  
  @Override
  public int startScene(Window window) throws NoSuchMethodException, SecurityException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, IOException, InterruptedException {
    controller = new ReplayGeneratorController(controls);
    draw = new ReplayGeneratorDraw(controls, controller, window);
    scene = draw.draw(window);
     
    if (window != null) {
      window.close();
    }
    window.setScene(scene);
    window.setIScene(this);
    window.show();
    return 0;
  }

  @Override
  public void endScene() throws IOException, NoSuchMethodException, SecurityException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, InterruptedException {
    // TODO Auto-generated method stub
    
  }

}
