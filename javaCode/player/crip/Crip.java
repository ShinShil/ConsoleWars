package player.crip;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import animation.AnimationSkill;
import application.Service;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import player.BasePlayer;
import player.effect.DamageType;
import player.effect.Defense;
import scenes.ISceneController;
import scenes.symphony.ISizeSymphonyDraw;
import skill.CripAttack;

/**
 * desctibes the crip - main task create the iamge of crip and launch it
 * all crips are extend this class
 * @author ShinShil
 *
 */
public class Crip extends BasePlayer implements ISizeSymphonyDraw, ICripConstants {

  // fields that set in the sons(inheritance)
  protected int speed;
  protected int cripHeight;
  protected int cripWidth;
  protected CripAttack cripAttack;
  protected String imgPath;
  protected int spriteWidth = 80;

  protected Timeline actions; //f.e. castWater
  protected ArrayList<AnimationSkill> actionsSkills; //f.e. AnimationWater, animations, that launched by crip
  protected double spriteX = 0;
  protected double spriteY = 0;
  protected int spriteN = 0;
  protected int spriteAmount;
  protected ImageView imageView;
  protected Timeline timeline;
  protected ISceneController controller;
  protected int spriteFrequencyBorder = 5;
  protected int spriteFrequency = 0;
  protected int towerIndex;
  protected int maxInt;
  protected int imageWidth = 80;

  protected DamageType weakness;
  protected String forAuto;

  public String getForAuto() {
    return forAuto;
  }

  public ImageView getImageView() {
    return imageView;
  }

  public int getTowerIndex() {
    return towerIndex;
  }

  public void setTowerIndex(int towerIndex) {
    this.towerIndex = towerIndex;
  }

  public Crip(ISceneController controller) {
    defs = new ArrayList<Defense>();
    this.maxInt = (int) Math.floor(canvasW / imageWidth);
    this.controller = controller;
    towerIndex = -1;
  }

  public void pause() throws NoSuchMethodException, SecurityException, ClassNotFoundException,
      InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, IOException, InterruptedException {
    timeline.stop();
    if (actions != null) {
      actions.pause();

    }
    if (actionsSkills != null) {
      for (int i = 0; i < actionsSkills.size(); ++i) {
        actionsSkills.get(i).pause();
      }
    }
  }

  public void stop() throws IOException, InterruptedException, NoSuchMethodException,
      SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    imageView.setVisible(false);
    if (actions != null) {
      actions.stop();
    }
    if (actionsSkills != null) {
      for (int i = 0; i < actionsSkills.size(); ++i) {
        actionsSkills.get(i).stopBot();
      }
    }
    controller.refreshVals();
    timeline.stop();
  }

  public void startCripTimeline() throws IOException {
    init();
    timeline = new Timeline(new KeyFrame(Duration.millis(speed), new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        try {
          cripAction();
        } catch (IOException | NoSuchMethodException | SecurityException | ClassNotFoundException
            | InstantiationException | IllegalAccessException | IllegalArgumentException
            | InvocationTargetException | InterruptedException e1) {
          e1.printStackTrace();
        }
      }

    }));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
    if(actions != null)
      actions.play();
  }

  public void cast() {

  }

  protected void cripAction() throws IOException, InterruptedException, NoSuchMethodException,
      SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    messager();
    imageView.setLayoutY(imageView.getLayoutY() + 1);
    imageView.toFront();
    if (imageView.getLayoutY() >= controller.getAnimationPlace().getHeight() - dummyH) {
      controller.getRoom().getCurrPlayer().effectBySkill(cripAttack);
      controller.getSldList().get(towerIndex).removeTopCrip();
      stop();
    } else {
      ++spriteFrequency;
      if (spriteFrequency == spriteFrequencyBorder) {
        spriteN = Service.cycleIncrement(spriteN, spriteAmount);
        spriteX = spriteN * 80;
        Rectangle2D ivClip = new Rectangle2D(spriteX, spriteY, dummyW, dummyH);
        imageView.setViewport(ivClip);
        spriteFrequency = 0;
      }
    }
  }

  private void init() {
    imageView = generate();
    controller.getAnimationPlace().getChildren().add(imageView);
    imageView.toFront();
  }

  // returns the index of towersArray
  private ImageView generate() {
    Image img = new Image(imgPath);
    double imgW = 80;
    double imgH = 80;

    ImageView iv = new ImageView(img);
    Rectangle2D ivClip = new Rectangle2D(spriteX, spriteY, imgW, imgH);
    iv.setViewport(ivClip);
    iv.setLayoutY(0);
    if (towerIndex == -1) towerIndex = Service.getRandomInt(maxInt);
    iv.setLayoutX(towerIndex * imgW);
    return iv;
  }

  public void messager() {

  }

  @Override
  public void initSkills() {
    skills = null;
  }

}
