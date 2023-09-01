package com.tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

/**
 * @author Sarah Qiao
 * @version 1.5
 * @date 2021/2/10
 * @Package : tankgame
 */
public class MyPanel extends JPanel implements KeyListener, Runnable {
    MyTank myTank = null;
    static Vector<EnemyTank> enemyTanks = new Vector<>();
    //Define a Vector seeking nodes object to restore coordinates and orientation of enemy tanks
    Vector<Node> nodes = new Vector<>();
    static Vector<Bomb> bombs = new Vector<>();

    int enemyTankSize = 3;
    //Define three bomb images to create an explosive effect, initialized to empty
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;
    //When the bullet hits the tank, the explosion effect is triggered, and three pictures are played in sequence


    public MyPanel(String key) {
        File file = new File(Recorder.getRecordFile());
        if (file.exists()) {
            nodes = Recorder.getNodesAndTanks();
        } else {
            System.out.println("Game save does not exist, start a new game");
            key = "1";
        }
        nodes = Recorder.getNodesAndTanks();
        myTank = new MyTank(150, 150);
        myTank.setSpeed(5);
        switch (key) {
            case "1":
                for (int i = 0; i < enemyTankSize; i++) {
                    EnemyTank enemyTank = new EnemyTank((100 * (i + 1)), 0);
                    enemyTank.setEnemyTanks(enemyTanks);
                    enemyTank.setDirect(2);
                    //Start the enemy tank movement thread and move randomly
                    new Thread(enemyTank).start();
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    enemyTank.shots.add(shot);
                    new Thread(shot).start();
                    enemyTanks.add(enemyTank);
                }
                break;
            //Continue the previous game
            case "2":
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());
                    enemyTank.setEnemyTanks(enemyTanks);
                    enemyTank.setDirect(node.getDirection());
                    new Thread(enemyTank).start();
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    enemyTank.shots.add(shot);
                    new Thread(shot).start();
                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("Wrong input");
        }

        image1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("../com/image/bomb3.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("../com/image/bomb2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("../com/image/bomb1.gif"));
        //Playing music
        new PlayAudio("../com/111.wav").start();
    }

    //Writing method, display our tank destroyed enemy tank information, import the brush tool
    public void showInfo(Graphics g) {
        g.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);
        g.drawString("You have destroyed enemy tanks:", 1020, 30);
        drawTank(1020, 60, g, 0, 0);
        g.setColor(Color.BLACK);
        g.drawString(Recorder.getAllEnemyTankNum() + "", 1080, 100);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);
        if (myTank != null && myTank.isLive()) {
            drawTank(myTank.getX(), myTank.getY(), g, myTank.getDirect(), 1);
        }

        //Draw our bullets
        //To determine if our bullet is still alive, the bullet needs to be within the range of the panel
        //Since multiple bullets can now be fired, the bullet cup is stored in the set, and to draw the bullet you need to traverse it out
        if (myTank.shots.size() > 0) {
            for (int i = 0; i < myTank.shots.size(); i++) {
                Shot shot = myTank.shots.get(i);
                if (shot != null && shot.isLive()) {
                    g.draw3DRect(shot.getX(), shot.getY(), 3, 3, false);
                } else {
                    myTank.shots.remove(shot);
                }
            }
        }

        //Drawing the enemy tank and loop through the Vector
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.isLive()) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
                //Drawing all enemy tank bullets
                Vector<Shot> shots = enemyTank.shots;
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    Shot shot = shots.get(j);
                    if (shot.isLive()) {
                        g.draw3DRect(shot.getX(), shot.getY(), 3, 3, false);
                    } else {
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }

        //Drawing bombs
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if (bomb.getLife() > 6) {
                g.drawImage(image1, bomb.getX(), bomb.getY(), 60, 60, this);
            } else if (bomb.getLife() > 3 && bomb.getLife() < 6) {
                g.drawImage(image2, bomb.getX(), bomb.getY(), 60, 60, this);
            } else if (bomb.getLife() <= 3) {
                g.drawImage(image3, bomb.getX(), bomb.getY(), 60, 60, this);
            }
            bomb.lifeDown();
            if (bomb.getLife() == 0) {
                bombs.remove(bomb);
            }
        }
    }

    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        switch (type) {
            case 0://Enemy tank
                g.setColor(Color.cyan);
                break;
            case 1://Our tank
                g.setColor(Color.yellow);
                break;
        }

        //Drawing tanks of different shapes depending on the direction of the tank.
        switch (direct) {
            case 0://Up
                //Left side
                g.fill3DRect(x, y, 10, 60, false);
                //Right side
                g.fill3DRect(x + 30, y, 10, 60, false);
                //Middle part
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                //Round cap
                g.fillOval(x + 10, y + 20, 20, 20);
                //Barrel
                g.drawLine(x + 20, y + 30, x + 20, y);
                break;
            case 1://Right
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x + 60, y + 20);
                break;
            case 2://Down
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 30, x + 20, y + 60);
                break;
            case 3://Left
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x, y + 20);
                break;
            default:
                System.out.println("Not solved");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //Handles methods for pressing wasd keys, with corresponding moves for each key
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            myTank.setDirect(0);
            if (myTank.getY() > 0) {
                myTank.moveUp();
            }
        } else if (code == KeyEvent.VK_D) {
            myTank.setDirect(1);
            if (myTank.getX() + 60 < 1000) {
                myTank.moveRight();
            }
        } else if (code == KeyEvent.VK_S) {
            myTank.setDirect(2);
            if (myTank.getY() + 60 < 750) {
                myTank.moveDown();
            }
        } else if (code == KeyEvent.VK_A) {
            myTank.setDirect(3);
            if (myTank.getX() > 0) {
                myTank.moveLeft();
            }
        }
        //If the user presses the J key, the firing behavior is initiated
        if (code == KeyEvent.VK_J) {
            myTank.ShotEnemyTank();
        }
        this.repaint();
    }

    /**
     *  Judging bullets and tanks
     *
     * @param shots Bullets collection
     * @param tank  Tank
     */
    public void hitTank(Vector<Shot> shots, Tank tank) {
        for (int i = 0; i < shots.size(); i++) {
            hitTank(shots.get(i), tank);
        }
    }

    public void hitTank(Shot s, Tank tank) {
        //To determine if a bullet hit the tank, see if the coordinates of the bullet are in the area of the enemy tank
        switch (tank.getDirect()) {
            //Enemy tanks occupy the same area facing up as they do facing down. categorize
            case 0://Up
            case 2://Down
                if (s.getX() > tank.getX() && s.getX() < tank.getX() + 40
                        && s.getY() > tank.getY() && s.getY() < tank.getY() + 60) {
                    s.setLive(false);
                    tank.setLive(false);
                    enemyTanks.remove(tank);
                    if (!(tank instanceof MyTank)) {
                        Recorder.addAllEnemyTankNum();
                    }

                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }

            case 1://Right
            case 3://Left
                if (s.getX() > tank.getX() && s.getX() < tank.getX() + 60
                        && s.getY() > tank.getY() && s.getY() < tank.getY() + 40) {
                    s.setLive(false);
                    tank.setLive(false);
                    enemyTanks.remove(tank);
                    if (!(tank instanceof MyTank)) {
                        Recorder.addAllEnemyTankNum();
                    }
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }

    @Override
    // Redraw the entire area every 100 milliseconds, equivalent to refreshing
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            hitEnemyTank();
            this.repaint();
        }
    }

/*
 Writing Method To determine whether our tank bullets hit enemy tanks, pass in a collection
 of neutron bombs and an enemy tank.
 If our tank can fire multiple bullets, when judging whether our tank can hit the enemy
 Need to go through all bullets in the bullet set, and all enemy tanks to determine
 */

    public void hitEnemyTank() {
        if (myTank.shot != null && myTank.shot.isLive()) {
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                hitTank(myTank.shot, enemyTank);
            }
        }
    }

    //Writing a method to determine whether the enemy tank hit our tank
    public void hitMyTank() {
        if (enemyTanks.size() > 0) {
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                Vector<Shot> shots = enemyTank.shots;
                if (shots.size() > 0) {
                    for (int j = 0; j < shots.size(); j++) {
                        Shot shot = shots.get(j);
                        //Determining if the bullet shot hit our tank
                        if (myTank.isLive() && shot.isLive()) {
                            hitTank(shot, myTank);
                        }
                    }
                    hitMyTank();
                    this.repaint();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
