package scenes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import animation.SkillLineDetector;
import javafx.scene.layout.Pane;

public interface ISceneController {
  public void refreshVals() throws IOException, InterruptedException, NoSuchMethodException,
      SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException;

  public Pane getAnimationPlace();

  public ArrayList<SkillLineDetector> getSldList();

  public Room getRoom();

  public boolean ifAutoPlay();
}
