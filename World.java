import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

public class World {
  // private static int worldMap[][] = {
  //     { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
  //     { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  //     { 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  //     { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  //     { 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1 },
  //     { 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1 },
  //     { 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1 },
  //     { 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1 },
  //     { 1, 0, 0, 0, 0, 0, 1, 1, -1, 1, 1, 0, 0, 0, 0, 1, 1, -1, 1, 1, 0, 0, 0, 1 },
  //     { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  //     { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  //     { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  //     { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  //     { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  //     { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  //     { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  //     { 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  //     { 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  //     { 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  //     { 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  //     { 1, 1, -1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  //     { 1, 1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  //     { 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  //     { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }
  // };

  private static int worldMap[][] = new int[50][50];

  public static void initWorld(){
    for(int i = 0; i < worldMap.length; i++){
      worldMap[i][0] = 1;
      worldMap[i][worldMap.length - 1] = 1;
      worldMap[worldMap.length - 1][i] = 1;
      worldMap[0][i] = 1;
    }
  }

  // private static int worldMap[][] = {
  // { 1, 1, 1, 1, 1, 1, 1 },
  // { 1, 0, 0, 2, 0, 0, 1 },
  // { 1, 0, 0, 0, 0, 0, 1 },
  // { 1, 0, 0, 0, 0, 0, 1 },
  // { 1, 0, 0, 0, 0, 0, 1 },
  // { 1, 0, 0, 0, 0, 0, 1 },
  // { 1, 1, 1, 1, 1, 1, 1 },
  // };

  public static int floorTexture[][] = new int[100][100];

  private static List<BufferedImage> wallTextures = new ArrayList<>();
  private static List<BufferedImage> floorCeilingTextures = new ArrayList<>();
  private static boolean unloaded = true;
  private static List<Sprite> sprites = new ArrayList<>();
  private static Gun gun;
  public static BufferedImage black;

  private static void loadTextures() {
    if (unloaded) {
      try {
        wallTextures.add(ImageIO.read(new File("./wallTextures/2.jpg")));
        wallTextures.add(ImageIO.read(new File("./wallTextures/1.png")));
        wallTextures.add(ImageIO.read(new File("./wallTextures/2.png")));
        black = ImageIO.read(new File("./wallTextures/black.png"));
        floorCeilingTextures.add(ImageIO.read(new File("./floorAndCeilingTextures/floortiles.png")));
        BufferedImage gameMasterImg = ImageIO.read(new File("./sprites/stickman.png"));
        BufferedImage deadGameMasterImg = ImageIO.read(new File("./sprites/deadstickman.png"));
        BufferedImage unFired = ImageIO.read(new File("./fingerGun/noFire.png"));
        BufferedImage fired = ImageIO.read(new File("./fingerGun/fire.png"));
        Sprite gameMaster = new Ai(5, 3, gameMasterImg, 50, deadGameMasterImg, 400);
        // Sprite manman = new Ai(17.5, 6.5, gameMasterImg, 50, deadGameMasterImg);
        // Sprite pp = new Ai(11.5, 12.5, gameMasterImg, 50, deadGameMasterImg);
        gun = new Gun(unFired, 100, fired);

        Random random = new Random(System.currentTimeMillis());

        int squares = worldMap.length * worldMap.length;

        for(int i = 0; i < 100; i++){
          int pos = random.nextInt(squares);

          sprites.add(new Ai(pos/worldMap.length, pos%worldMap.length, gameMasterImg, 50, deadGameMasterImg, 250));
        }

        sprites.add(gameMaster);
        // sprites.add(manman);
        // sprites.add(pp);
      } catch (Exception e) {
        System.out.println(e);
        System.out.println("--------------------------------------------------------------------");
      }
      unloaded = false;
    }

    for (int i = 0; i < 100; i++) {
      for (int j = 0; j < 100; j++) {
        floorTexture[i][j] = 1;
      }
    }
  }

  public static Gun getGun() {
    loadTextures();
    return gun;
  }

  public static List<Sprite> getsprites() {
    loadTextures();
    return sprites;
  }

  public static int[][] getMap() {
    return worldMap;
  }

  public static List<BufferedImage> getWallTextures() {
    loadTextures();
    return wallTextures;
  }

  public static List<BufferedImage> getFloorCeilingTextures() {
    loadTextures();
    return floorCeilingTextures;
  }

  public static void setMap(int[][] worldMap2) {
    worldMap = worldMap2;
  }
}
