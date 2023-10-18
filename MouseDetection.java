import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.event.MouseInputAdapter;

public class MouseDetection extends MouseInputAdapter {
    private boolean firing = false;
    private Robot robot;

    public MouseDetection(){
        try {
            robot = new Robot();
        } catch (AWTException e1) {
            e1.printStackTrace();
        }
    }

    public void mouseMoved(MouseEvent e) {
        aimLogic();
    }

    public void mousePressed(MouseEvent e) {

        Thread thread = new Thread (() -> {
            while(firing) {
                aimLogic();
                try {
                    Thread.sleep(10);
                } catch (Exception s) {
                    // TODO: handle exception
                }
            }
        });
        int fireRate = 100;
        int pressCode = e.getButton();
        Timer timer1 = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                World.getGun().fire(fireRate);
                boolean notHit = true;
                boolean spriteInWay = false;
                int ray = 0;
                for (int i = 0; i < Main.spriteAngles.length; i++) {
                    if ((Main.res - 1) / 2 >= Main.spriteAngles[i][0] && (Main.res - 1) / 2 <= Main.spriteAngles[i][1]) {
                        spriteInWay = true;
                        ray = i;
                    }
                }

                if (spriteInWay
                        && Main.isBehind((Main.res - 1) / 2, Main.spriteAngles[ray][2])
                        && Main.spriteAngles[ray][2] >= 0.5) {
                    // code for clicking on player or object
                    if (Main.sprites.get(ray) instanceof Ai) {
                        Ai ai = (Ai) Main.sprites.get(ray);
                        ai.shot(World.getGun().getDamage());
                        Sprite sp = (Sprite) ai;
                        Main.sprites.set(ray, sp);
                        notHit = false;
                    }
                }

                if (notHit) {
                    if (Main.imageArray[(Main.res - 1) / 2][1] == -1) {
                        int[][] worldMap = World.getMap();
                        worldMap[(int) Main.imageArray[(Main.res - 1) / 2][5]][(int) Main.imageArray[(Main.res - 1) / 2][6]] = 0;
                        World.setMap(worldMap);
                    }
                }
                if (!firing) {
                    timer1.cancel();
                }
            }
        };

        if (pressCode == MouseEvent.BUTTON1) {
            firing = true;
            thread.start();
            timer1.scheduleAtFixedRate(task, 0, fireRate);
        }
    }

    public void mouseReleased(MouseEvent e) {
        int pressCode = e.getButton();
        if (pressCode == MouseEvent.BUTTON1) {
            firing = false;
        }
    }

    private void aimLogic(){
        if (Main.mouseLock) {
            double factor = 50 / (double) Main.centreX;
            Point location = Main.frame.getLocation();
            Player.mouseAim(factor * (location.x + Main.centreX - MouseInfo.getPointerInfo().getLocation().x));
            robot.mouseMove(location.x + Main.centreX, location.y + Main.centreY);
        }
    }
}
