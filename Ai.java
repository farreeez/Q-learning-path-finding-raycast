import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class Ai extends Sprite {
  int health;
  boolean dead;
  BufferedImage deadSprite;
  QlearningAgent aiMovement;
  int[] pos = { (int) Math.round(posY), (int) Math.round(posX) };
  int speed;

  public Ai(double posY, double posX, BufferedImage sprite, int health, BufferedImage deadSprite) {
    super(posX, posY, sprite);
    this.health = health;
    this.deadSprite = deadSprite;
    if (health <= 0) {
      dead = true;
      this.sprite = deadSprite;
    }
  }

  public Ai(double posY, double posX, BufferedImage sprite, int health, BufferedImage deadSprite, int speed) {
    super(posX, posY, sprite);
    this.health = health;
    this.deadSprite = deadSprite;
    if (health <= 0) {
      dead = true;
      this.sprite = deadSprite;
    }

    aiMovement = new QlearningAgent(this);
    this.speed = speed;

    startMovement();
  }

  public int[] getPosition() {
    return pos;
  }

  public void setPosition(int[] position) {
    pos = position;
  }

  private void startMovement() {
    Timer timer = new Timer();

    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        double[] playerPosition = Player.getPosition();
        double yPos = playerPosition[0];
        double xPos = playerPosition[1];
        if (dead || aiMovement.moveBot(yPos, xPos)) {
          timer.cancel();
        }
      }
    };

    timer.scheduleAtFixedRate(task, 0, 1000);
  }

  public void shot(int damage) {
    health -= damage;
    if (health <= 0) {
      dead = true;
      this.sprite = deadSprite;
    }
  }

  public boolean isDead() {
    return dead;
  }
}
