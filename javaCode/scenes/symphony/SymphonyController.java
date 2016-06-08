package scenes.symphony;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

import animation.AnimationOneToOneFly;
import animation.AnimationSkill;
import animation.ConnectorSkillAnim;
import animation.SkillLineDetector;
import application.Service;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import player.Player;
import player.crip.Crip;
import player.crip.ICripConstants;
import scenes.ISceneController;
import scenes.Room;
import scenes.Window;
import scenes.replay.ReplayController;
import skill.ISkillConstants;

/**
 * refresh vals
 * controlls sldList(collision detector)
 * catch user events - skill's casts
 * run animations
 * generateCrips
 * refreshVals() - hp, switch on/off bot
 * @author ShinShil
 *
 */
public class SymphonyController 
    implements
      ISceneController,
      ISizeSymphonyDraw,
      ICripConstants,
      ISkillConstants{
  private ConnectorSkillAnim connectSkillAnim;
  private ArrayList<SkillLineDetector> sldList;
  public Room room;
  private Timeline generatorCrip;
  private boolean autoPlay;
  private Timeline autoPlayTimer;

  private Window window;
  private SymphonyScene.Controls controls;
  public SymphonyController(SymphonyScene.Controls controls, Window window) {
    this.controls = controls;
    this.window = window;
    room = new Room(1);
    connectSkillAnim = new ConnectorSkillAnim();
    sldList = new ArrayList<SkillLineDetector>();
    for (int i = 0; i < towerAmount; ++i) {
      sldList.add(new SkillLineDetector());
      sldList.get(i).start();
    }
  }

  public void stop() throws NoSuchMethodException, SecurityException, ClassNotFoundException,
      InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, IOException, InterruptedException {
    if (generatorCrip != null) {
      generatorCrip.stop();
    }
    if (autoPlayTimer != null) {
      autoPlayTimer.stop();
    }
    Service.out("Stopped");
    for (int i = 0; i < sldList.size(); ++i) {
      sldList.get(i).stop();
    }
  }

  public void startGenerateCrips() throws NoSuchMethodException, ClassNotFoundException,
      InstantiationException, IllegalAccessException, InvocationTargetException, SecurityException,
      IllegalArgumentException {
    generatorCrip =
        new Timeline(new KeyFrame(Duration.millis(2000), new EventHandler<ActionEvent>() {
          public void handle(ActionEvent event) {
            try {
              generateCrip();
            } catch (NoSuchMethodException | SecurityException | ClassNotFoundException
                | InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
              e.printStackTrace();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        }));
    generatorCrip.setCycleCount(Animation.INDEFINITE);
    generatorCrip.play();
  }

  @Override
  public boolean ifAutoPlay() {
    return autoPlay;
  }

  public void generateCrip() throws NoSuchMethodException, SecurityException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, IOException {
    String className =
        "player.crip." + cripClassNames[Service.getRandomInt(Arrays.asList(cripClassNames).size())];
    @SuppressWarnings("rawtypes")
    Constructor constructor = Class.forName(className).getConstructor(ISceneController.class);
    Crip crip = (Crip) (constructor.newInstance(new Object[] {this}));
    crip.startCripTimeline();
    sldList.get(crip.getTowerIndex()).addCrip(crip);
    ReplayController.saveActionLastReplay("crip", crip.getClass().getSimpleName(),
        crip.getTowerIndex() + "");
  }

  public void pause() throws NoSuchMethodException, SecurityException, ClassNotFoundException,
      InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, IOException, InterruptedException {
    for (int i = 0; i < sldList.size(); ++i) {
      sldList.get(i).stop();
    }
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

  private void playAutoPlay() {
    if (autoPlayTimer == null) {
      autoPlayTimer =
          new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
              try {
                try {
                  autoPlayTick();
                } catch (InterruptedException | NoSuchMethodException | SecurityException
                    | ClassNotFoundException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                  e.printStackTrace();
                }
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
          }));
      autoPlayTimer.setCycleCount(Animation.INDEFINITE);
    }
    autoPlayTimer.play();
  }

  private void stopAutoPlay() {
    if (autoPlayTimer != null) {
      autoPlayTimer.stop();
    }
  }

  private void autoPlayTick() throws IOException, InterruptedException, NoSuchMethodException,
      SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    for (int i = 0; i < sldList.size(); ++i) {
      Crip crip = sldList.get(i).getTopFutureCrip();
      if (crip == null) {
        continue;
      } else {
        room.getCurrPlayer().setSkillClick(crip.getForAuto());
        refreshVals();
        try {
          int damageBySkill = 3;
          for (int j = 0; j <= (int) Math.ceil(crip.getHp() / damageBySkill); ++j) {
            clickCast((Label) controls.player1.getChildren().get(i));
          }
          sldList.get(i).removeTopFutureDead();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException
            | NoSuchMethodException | SecurityException | IllegalArgumentException
            | InvocationTargetException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public void switchAutoPlay() {
    if (controls.cbAutoPlay.isSelected()) {
      playAutoPlay();
    } else {
      stopAutoPlay();
    }
  }

  @Override
  public void refreshVals() throws InterruptedException, NoSuchMethodException, SecurityException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, IOException {
    controls.hp.setText(String.format("%d", room.getCurrPlayer().getHp()));
    controls.name.setText(room.getCurrPlayer().getName());
    for (int i = 0; i < controls.skillClickList.size(); ++i) {
      controls.skillClickList.get(i).update(room.getCurrPlayer());
    }
    if (!room.checkTheGameState()) {
      Service.out(room.getCurrPlayer().getHp() + "");
      this.stop();
      window.preChangeScene();
      window.startEnd();
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
  public Room getRoom() {
    return room;
  }
}
