package animation;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import player.crip.Crip;

/**
 * watches by animation objects that are active - detect collisions 
 * @author ShinShil
 *
 */
public class SkillLineDetector {

 
  private ArrayList<Crip> crips;
  private ArrayList<AnimationSkill> skills;
  /**
   * futureDead - use for bot as far as we can suggest the future
   */
  private ArrayList<Crip> futureDead;
  private Timeline timeline;

  public void addFutureDead(Crip crip) {
    futureDead.add(crip);
  }

  public SkillLineDetector() {
    crips = new ArrayList<Crip>();
    skills = new ArrayList<AnimationSkill>();
    futureDead = new ArrayList<Crip>();
  }

  /**
   * start the main timeline, it checks the state of animation objects
   */
  public void start() {
    timeline = new Timeline(new KeyFrame(Duration.millis(5), new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          tick();
        } catch (IOException | InterruptedException | NoSuchMethodException | SecurityException
            | ClassNotFoundException | InstantiationException | IllegalAccessException
            | IllegalArgumentException | InvocationTargetException e) {
          e.printStackTrace();
        }
      }
    }));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
  }

  /**
   * add Crip's animation object for watching, also add for futureDead(use by bot)
   * @param crip
   */
  public void addCrip(Crip crip) {
    crips.add(crip);
    futureDead.add(crip);
  }

  /**
   * add Skill's animation object for watching
   * @param skill
   */
  public void addSkill(AnimationSkill skill) {
    skills.add(skill);
  }

  /**
   * check the state of animation objects, if they collision - remove them and call appropriate events
   * @throws IOException
   * @throws InterruptedException
   * @throws NoSuchMethodException
   * @throws SecurityException
   * @throws ClassNotFoundException
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   * @throws InvocationTargetException
   */
  private void tick() throws IOException, InterruptedException, NoSuchMethodException,
      SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    if (crips.size() > 0 && skills.size() > 0) {
      if (crips.size() > 1) {
        if (crips.get(1).getImageView().getLayoutY() > crips.get(0).getImageView().getLayoutY()) {
          Crip tmp = crips.get(0);
          crips.set(0, crips.get(1));
          crips.set(1, tmp);
        }
      }
      ImageView skill = skills.get(0).getImageView();
      ImageView crip = crips.get(0).getImageView();
      if (skill.getLayoutY() <= crip.getLayoutY() + crip.getFitHeight()) {
        skills.get(0).setOnWhom(crips.get(0));
        skills.get(0).stop();
        if (crips.size() > 0 && crips.get(0).getHp() < 0) {
          crips.get(0).stop();
          crips.remove(0);
        }
        skills.remove(0);
      }
    }
  }

  /**
   * stop timeline, that checks animations
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
  public void stop() throws NoSuchMethodException, SecurityException, ClassNotFoundException,
      InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, IOException, InterruptedException {
    for (int i = 0; i < crips.size(); ++i) {
      crips.get(i).pause();
    }
    for (int i = 0; i < skills.size(); ++i) {
      skills.get(i).pause();
    }
  }

  /**
   * get crip that will killed next
   * @return
   */
  public Crip getTopCrip() {
    return crips.size() > 0 ? crips.get(0) : null;
  }

  /**
   * get crip for bot
   * @return
   */
  public Crip getTopFutureCrip() {
    return futureDead.size() > 0 ? futureDead.get(0) : null;
  }

  /**
   * remove skill that should collised next
   * @throws IOException
   * @throws InterruptedException
   * @throws NoSuchMethodException
   * @throws SecurityException
   * @throws ClassNotFoundException
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   * @throws InvocationTargetException
   */
  public void removeTopSkill() throws IOException, InterruptedException, NoSuchMethodException,
      SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    if (skills.size() > 0) {
      skills.get(0).stop();
      skills.remove(0);
    }
  }

  /**
   * remove crip that should collised next
   */
  public void removeTopCrip() {
    if (crips.size() > 0) {
      crips.remove(0);
    }
  }
  /**
   * remove crip that should died next
   */
  public void removeTopFutureDead() {
    if (futureDead.size() > 0) {
      futureDead.remove(0);
    }
  }
}
