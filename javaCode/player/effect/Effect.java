package player.effect;

/**
 * describes effect that can affect the player:
 * damage, defense, heal, etc.
 * 
 * @author ShinShil
 *
 */
public class Effect {

  private int points;
  private DamageType type;

  public Effect(int points, DamageType type) {
    this.points = points;
    this.type = type;
  }

  public int getPoints() {
    return points;
  }

  public void setPoints(int points) {
    this.points = points;
  }

  public void addPoints(int points) {
    this.points += points;
  }

  public void removePoints(int points) {
    this.points -= points;
  }

  public DamageType getType() {
    return type;
  }

  public void setDamageType(DamageType type) {
    this.type = type;
  }
}
