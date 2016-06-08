package scenes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public interface IScene {
  int startScene(Window window) throws NoSuchMethodException, SecurityException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, IOException, InterruptedException;

  void endScene() throws IOException, NoSuchMethodException, SecurityException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, InterruptedException;
}
