package scenes.replayGenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

import animation.SkillLineDetector;
import application.Service;
import javafx.scene.layout.Pane;
import player.crip.Crip;
import player.crip.ICripConstants;
import scenes.ISceneController;
import scenes.Room;
import skill.ISkillConstants;

public class ReplayGeneratorController implements ISceneController, ICripConstants, ISkillConstants {

  ReplayGeneratorScene.Controls controls;

  public ReplayGeneratorController(ReplayGeneratorScene.Controls controls) {
    this.controls = controls;
  }

  @Override
  public void refreshVals() throws IOException, InterruptedException, NoSuchMethodException,
      SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {

  }

  public void generateReplays(int amount) throws IOException, NoSuchMethodException,
      SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    for (int i = 0; i < amount; ++i) {
      Service.out("generator:" + i + "");
      generateReplay("replay" + (i + 1) + ".txt");
    }
  }

  public void generateReplay(String replayName) throws IOException, NoSuchMethodException,
      SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    // action/detail/towerIndex/millis
    long timeCrip = 0;
    long timeCast = 0;
    int towerIndex = 0;
    int hp = 10;
    
    FileWriter writer = new FileWriter("replays/" + replayName, false);
    while (hp > 0) {
      
      timeCrip += (600 + Service.getRandomInt(700));
      timeCast = timeCrip + (10 + Service.getRandomInt(10));
      towerIndex = Service.getRandomInt(9);
      String className = "player.crip."
          + cripClassNames[Service.getRandomInt(Arrays.asList(cripClassNames).size())];
      @SuppressWarnings("rawtypes")
      Constructor constructor = Class.forName(className).getConstructor(ISceneController.class);
      Crip crip = (Crip) (constructor.newInstance(new Object[] {this}));
      String cripDetail = crip.getClass().getSimpleName();
      if(cripDetail.equals("Leviathan")) {
        //4361 4393 - I take it by hands, two deltas between creation of Leviathan and damage by his water skill
        hp -= waterSkillDamage;
        if(hp < 0) {
          timeCrip += 4393;
          String exitRec = "exit/exit/exit/" + timeCrip + "\n";
          writer.write(exitRec);
          writer.close();
          break;
        }
      }
      String castDetail = crip.getForAuto();
      String cripAction = "crip/" + cripDetail + "/" + towerIndex + "/" + timeCrip + "\n";
      String castAction = "cast/" + castDetail + "/" + towerIndex + "/" + timeCast + "\n";
      writer.write(cripAction);
      for (int j = 0; j <= (int) Math.ceil(crip.getHp() / waterSkillDamage); ++j) {
        writer.write(castAction);
      }
      timeCast  += 1;
    }
    writer.close();
  }

  public void parseAmountTextFiled() {

  }

  @Override
  public Pane getAnimationPlace() {
    return null;
  }

  @Override
  public ArrayList<SkillLineDetector> getSldList() {
    return null;
  }

  @Override
  public Room getRoom() {
    return null;
  }

  @Override
  public boolean ifAutoPlay() {
    return false;
  }

}
