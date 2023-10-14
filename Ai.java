import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class Ai extends Sprite {
  int health;
  boolean dead;
  BufferedImage deadSprite;
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

    this.speed = speed;

    startMovement();
  }

  public double[] getPosition() {
    double[] position = {pos[0] + 0.5, pos[1] + 0.5};
    return position;
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
        boolean stop = QlearningAgent.moveBot(yPos, xPos, pos);
        updatePos();

        if (dead || stop) {
          timer.cancel();
        }
      }
    };

    timer.scheduleAtFixedRate(task, 0, speed);
  }

  private void updatePos(){
    super.setPosX(pos[1] + 0.5);
    super.setPosY(pos[0] + 0.5);
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
