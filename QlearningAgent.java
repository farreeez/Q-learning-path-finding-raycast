import java.util.Random;

public class QlearningAgent {
    private int[][] worldMap = World.getMap();
    private int[][] rewardsMap = new int[worldMap.length][worldMap[0].length];
    private double[][][] qVals = new double[worldMap.length][worldMap[0].length][4];
    private char[] actions = { 'u', 'd', 'l', 'r' };
    private int[] agentPos = new int[2];
    private Random random = new Random(System.currentTimeMillis());
    private double epsilon = 0.9;
    private double gamma = 0.95;
    private double alpha = 0.9;

    public QlearningAgent(int targetY, int targetX, Ai bot) {
        for (int i = 0; i < worldMap.length; i++) {
            for (int j = 0; j < worldMap[i].length; j++) {
                if (worldMap[i][j] == 0) {
                    rewardsMap[i][j] = -1;
                } else if (worldMap[i][j] == 1){
                    rewardsMap[i][j] = -100;
                }
            }
        }

        agentPos = bot.getPosition();

        rewardsMap[targetY][targetY] = 100;

        learn();
    }

    public boolean moveBot(){
        int action = getNextMove(agentPos[0], agentPos[1]);
        move(agentPos, action);

        return isTerminal(agentPos);
    }

    private void learn() {
        for (int i = 0; i < 1000; i++) {
            int[] pos = { agentPos[0], agentPos[1] };
            while (!isTerminal(pos)) {
                int y = pos[0];
                int x = pos[1];
                int action = epsilonGreedy(y, x);
                move(pos, action);
                qVals[y][x][action] = qVals[y][x][action]
                        + alpha * (rewardsMap[y][x] + gamma * qVals[pos[0]][pos[1]][max(qVals[y][x])] - qVals[y][x][action]);
            }
        }
    }

    private void move(int[] arr, int actionNum) {
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

    private boolean isTerminal(int[] arr) {
        return rewardsMap[arr[0]][arr[1]] != -1;
    }

    private int epsilonGreedy(int y, int x) {
        return getMoveHelper(y, x, epsilon);
    }

    private int getNextMove(int y, int x) {
        return getMoveHelper(y, x, 1.1);
    }

    private int getMoveHelper(int y, int x, double epsilon) {
        if (random.nextDouble() < epsilon) {
            return max(qVals[y][x]);
        } else {
            return (int) qVals[y][x][random.nextInt(4)];
        }
    }

    private int max(double[] arr) {
        int max = 0;

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > arr[max]) {
                max = i;
            }
        }

        return max;
    }

    
}
