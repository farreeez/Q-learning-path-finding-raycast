import java.util.Random;

public class QlearningAgent {

  private static int[][] worldMap = World.getMap();
  private static int[][] rewardsMap = new int[worldMap.length][worldMap[0].length];
  private static double[][][][][] qVals =
      new double[worldMap.length][worldMap[0].length][worldMap.length][worldMap[0].length][4];
  private static char[] actions = {'u', 'd', 'l', 'r'};
  private static Random random = new Random(System.currentTimeMillis());
  private static double epsilon = 0.9;
  private static double gamma = 0.95;
  private static double alpha = 0.9;

  public static void initialize() {
    for (int i = 0; i < worldMap.length; i++) {
      for (int j = 0; j < worldMap[i].length; j++) {
        if (worldMap[i][j] == 0) {
          rewardsMap[i][j] = -1;
        } else if (worldMap[i][j] == 1) {
          rewardsMap[i][j] = -100;
        }
      }
    }

    learn();
  }



  public static boolean moveBot(double targetY, double targetX, int[] agentPos) {
    int intTargetY = (int) Math.floor(targetY);
    int intTargetX = (int) Math.floor(targetX);


    if (intTargetX == 0) {
      intTargetX++;
    }

    if (intTargetY == 0) {
      intTargetY++;
    }
    int action = getNextMove(intTargetY, intTargetX, agentPos[0], agentPos[1]);
    move(agentPos, action);

    return isTerminal(agentPos);
  }

  private static void learn() {
    for (int i = 0; i < worldMap.length; i++) {
      for (int j = 0; j < worldMap[i].length; j++) {
        if (rewardsMap[i][j] != -100) {
          rewardsMap[i][j] = 100;
          for (int k = 0; k < 1000; k++) {
            int[] pos = randPos();
            while (!isTerminal(pos)) {
              int y = pos[0];
              int x = pos[1];
              int action = epsilonGreedy(i, j, y, x);
              move(pos, action);
              qVals[i][j][y][x][action] =
                  qVals[i][j][y][x][action]
                      + alpha
                          * (rewardsMap[pos[0]][pos[1]]
                              + gamma
                                  * qVals[i][j][pos[0]][pos[1]][max(qVals[i][j][pos[0]][pos[1]])]
                              - qVals[i][j][y][x][action]);
            }
          }
          rewardsMap[i][j] = -1;

        }
      }
    }

  }

  private static int[] randPos() {
    while (true) {
      int pos = random.nextInt(World.getMap().length * World.getMap().length);
      int y = pos / World.getMap().length;
      int x = pos % World.getMap().length;
      int[] position = {y, x};

      if (!isTerminal(position)) {
        return position;
      }
    }
  }

  // working
  private static void move(int[] arr, int actionNum) {
    char action = actions[actionNum];
    if (action == 'u' && arr[0] - 1 >= 0) {
      arr[0]--;
    } else if (action == 'd' && arr[0] + 1 < worldMap.length) {
      arr[0]++;
    } else if (action == 'r' && arr[1] + 1 < worldMap[0].length) {
      arr[1]++;
    } else if (action == 'l' && arr[1] - 1 >= 0) {
      arr[1]--;
    }
  }

  // working
  public static boolean isTerminal(int[] arr) {
    return rewardsMap[arr[0]][arr[1]] != -1;
  }

  // working
  private static int epsilonGreedy(int targetY, int targetX, int agentY, int agentX) {
    return getMoveHelper(targetY, targetX, agentY, agentX, epsilon);
  }

  // working
  private static int getNextMove(int i, int targetX, int agentY, int agentX) {
    return getMoveHelper(i, targetX, agentY, agentX, 1.1);
  }

  // working
  private static int getMoveHelper(int i, int targetX, int agentY, int agentX, double epsilon) {
    if (random.nextDouble() < epsilon) {
      return max(qVals[i][targetX][agentY][agentX]);
    } else {
      return random.nextInt(4);

    }
  }


  private static int max(double[] arr) {
    int max = 0;

    for (int i = 1; i < arr.length; i++) {
      if (arr[i] > arr[max]) {
        max = i;
      }
    }

    return max;
  }
}
