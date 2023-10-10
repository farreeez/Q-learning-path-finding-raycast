public class QlearningAgent {
    private int[][] worldMap = World.getMap();
    private int[][] rewardsMap = new int[worldMap.length][worldMap[0].length];
    private int[] agentPos = new int[2];

    public QlearningAgent(int x, int y){
        for(int i = 0; i < worldMap.length; i++){
            for(int j = 0; j < worldMap[i].length; j++){
                if(worldMap[i][j] == 0){
                    rewardsMap[i][j] = -1;
                } else {
                    rewardsMap[i][j] = -100;
                }
            }
        }

        agentPos[0] = y;
        agentPos[1] = x;

        rewardsMap[y][x] = 100;
    }
}
