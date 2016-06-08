package scenes.replay;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import animation.AnimationOneToOneFly;
import animation.AnimationSkill;
import animation.ConnectorSkillAnim;
import animation.SkillLineDetector;
import application.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import player.Player;
import player.crip.Crip;
import player.crip.ICripConstants;
import scenes.ISceneController;
import scenes.Room;
import scenes.symphony.ISizeSymphonyDraw;

/**
 * controller for replay load replayRecs from "lastReplay.txt" and play it very similar to
 * SymphonyController
 * 
 * @author ShinShil
 *
 */
public class ReplayController extends ReplayScene
    implements
      ISceneController,
      ISizeSymphonyDraw,
      ICripConstants {
  private ConnectorSkillAnim connectSkillAnim;
  private ArrayList<SkillLineDetector> sldList;
  private boolean stopped;
  public Room room;


  public ReplayController(Controls controls) {
    this.controls = controls;
    room = new Room(1);
    connectSkillAnim = new ConnectorSkillAnim();
    sldList = new ArrayList<SkillLineDetector>();
    for (int i = 0; i < towerAmount; ++i) {
      sldList.add(new SkillLineDetector());
      sldList.get(i).start();
    }
  }

  public void startReplay(String fileName) throws IOException, InstantiationException, IllegalAccessException,
      ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalArgumentException,
      InvocationTargetException, InterruptedException {
    this.stopped = false;
    File f = new File(fileName);
    @SuppressWarnings("resource")
    FileReader fr = new FileReader(f);
    char[] buffer = new char[(int) f.length()];
    fr.read(buffer);
    ArrayList<String> res = new ArrayList<String>();
    String tmp = new String();
    for (int i = 0; i < (int) f.length(); ++i) {
      if (buffer[i] != '\n') {
        tmp += buffer[i];
      } else {
        res.add(tmp);
        tmp = "";
      }
    }
    ArrayList<String[]> comms = new ArrayList<String[]>();
    for (int i = 0; i < res.size(); ++i) {
      comms.add(res.get(i).split("/"));
    }

    int i = 0;
    loopStep(i, comms);

  }


  private void loopStep(int i, ArrayList<String[]> comms) throws InstantiationException,
      IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException,
      IllegalArgumentException, InvocationTargetException, IOException, InterruptedException {

    switch (comms.get(i)[0]) {
      case "cast":
        caseCast(i, comms);
        break;
      case "crip":
        caseCrip(i, comms);
        break;
      case "exit":
        caseExit();
        break;
    }

    if (i != comms.size() - 1 && !this.stopped) {
      long bot = Long.parseLong(comms.get(i)[3]);
      long top = Long.parseLong(comms.get(i + 1)[3]);
      Service.out(comms.get(i)[0] + ": " + (top - bot) + "");
      Task<Void> sleeper = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
          try {
            Thread.sleep(top - bot);
          } catch (InterruptedException ex) {

          }
          return null;
        }
      };
      sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
        @Override
        public void handle(WorkerStateEvent event) {
          try {
            try {
              loopStep(i + 1, comms);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          } catch (InstantiationException | IllegalAccessException | ClassNotFoundException
              | NoSuchMethodException | SecurityException | IllegalArgumentException
              | InvocationTargetException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      });
      new Thread(sleeper).start();
    }
  }

  private void caseExit() throws NoSuchMethodException, SecurityException, ClassNotFoundException,
      InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, IOException, InterruptedException {
    for (int i = 0; i < sldList.size(); ++i) {
      sldList.get(i).stop();
    }
  }

  private void caseCrip(int i, ArrayList<String[]> comms) throws NoSuchMethodException,
      SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, IOException {
    String className = "player.crip." + comms.get(i)[1];
    @SuppressWarnings("rawtypes")
    Constructor constructor = Class.forName(className).getConstructor(ISceneController.class);
    Crip crip = (Crip) (constructor.newInstance(new Object[] {this}));
    crip.setTowerIndex(Integer.parseInt(comms.get(i)[2]));
    crip.startCripTimeline();
    
    sldList.get(crip.getTowerIndex()).addCrip(crip);
  }

  private void caseCast(int i, ArrayList<String[]> comms) throws InstantiationException,
      IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException,
      IllegalArgumentException, InvocationTargetException, IOException {
    int tmpIndex = Integer.parseInt(comms.get(i)[2]);
    Label tower = (Label) controls.player1.getChildren().get(tmpIndex);

    String skill = comms.get(i)[1];
    AnimationSkill anim = connectSkillAnim.getAnimation(skill);
    String animSuperName = anim.getClass().getSuperclass().getSimpleName();
    String animName = anim.getClass().getSimpleName();
    if (animSuperName.equals("AnimationOneToOneFly")) {
      AnimationOneToOneFly animation = connectSkillAnim.getOneToOneFly(animName, skill);
      int towerIndex = Integer.parseInt(tower.getText()) - 1;
      sldList.get(towerIndex).addSkill(animation);
      animation.init(null, tower, skill, this);
      animation.start();
    }
  }

  public void setTest() throws IOException {
    FileWriter fw = new FileWriter("testReplay.txt", false);
    String str;
    // action/detail/towerIndex/millis

    str = "crip/Bahamut/2/" + "0" + "\n";
    fw.write(str);
    str = "cast/fire/2/" + "1000" + "\n";
    fw.write(str);
    str = "cast/water/3/" + "2000" + "\n";
    fw.write(str);
    fw.flush();
    fw.close();
  }

  public static void saveActionLastReplay(String action, String second, String towerIndex)
      throws IOException {
    String time = System.currentTimeMillis() + "";
    String replayRec = action + "/" + second + "/" + towerIndex + "/" + time + "\n";
    FileWriter fw = new FileWriter("lastReplay.txt", true);
    fw.write(replayRec);
    fw.close();
  }

  public void changeActiveSkill(String skill) {
    room.getCurrPlayer().setSkillClick(skill);
    for (int i = 0; i < controls.skillClickList.size(); ++i) {
      controls.skillClickList.get(i).update(room.getCurrPlayer());
    }
  }

  public void clickCast(Label tower) throws InstantiationException, IllegalAccessException,
      ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalArgumentException,
      InvocationTargetException, IOException {
    Player who = room.getCurrPlayer();
    String skill = who.getSkilClick().getName();
    AnimationSkill anim = connectSkillAnim.getAnimation(skill);
    String animSuperName = anim.getClass().getSuperclass().getSimpleName();
    String animName = anim.getClass().getSimpleName();
    if (animSuperName.equals("AnimationOneToOneFly")) {
      AnimationOneToOneFly animation = connectSkillAnim.getOneToOneFly(animName, skill);
      int towerIndex = Integer.parseInt(tower.getText()) - 1;
      sldList.get(towerIndex).addSkill(animation);
      animation.init(null, tower, skill, this);
      animation.start();
    }
  }

  @Override
  public void refreshVals() {
    controls.hp.setText(String.format("%d", room.getCurrPlayer().getHp()));
    controls.name.setText(room.getCurrPlayer().getName());
    for (int i = 0; i < controls.skillClickList.size(); ++i) {
      controls.skillClickList.get(i).update(room.getCurrPlayer());
    }
  }

  @Override
  public Pane getAnimationPlace() {
    return controls.animationPlace;
  }

  @Override
  public ArrayList<SkillLineDetector> getSldList() {
    return sldList;
  }

  @Override
  public boolean ifAutoPlay() {
    return false;
  }

  public void run() {
    try {
      startReplay("lastReplay.txt");
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException
        | NoSuchMethodException | SecurityException | IllegalArgumentException
        | InvocationTargetException | IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Room getRoom() {
    return room;
  }

  public void stop() throws NoSuchMethodException, SecurityException, ClassNotFoundException,
      InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, IOException, InterruptedException {
    this.stopped = true;
    for (int i = 0; i < sldList.size(); ++i) {
      sldList.get(i).stop();
    }
  }
}
