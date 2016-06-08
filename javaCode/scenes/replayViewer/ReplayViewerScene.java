package scenes.replayViewer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javafx.scene.Scene;
import scenes.IScene;
import scenes.Window;

public class ReplayViewerScene implements IScene{

  protected ReplayViewerDraw draw;
  protected Scene scene;
  protected Window window;
  @Override
  public int startScene(Window window) throws NoSuchMethodException, SecurityException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, IOException, InterruptedException {
    draw = new ReplayViewerDraw();
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
    
  }
  
}
