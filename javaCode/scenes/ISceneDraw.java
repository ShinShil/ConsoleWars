package scenes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javafx.scene.Scene;

public interface ISceneDraw {
  public Scene draw(Window window) throws NoSuchMethodException, SecurityException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, IOException; // 0 - success, else - code
                                                                        // of error
}
