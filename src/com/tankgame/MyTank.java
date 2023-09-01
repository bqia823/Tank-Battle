package com.tankgame;

import java.util.Vector;
/**
 * @author Sarah Qiao
 * @version 1.5
 * @date 2021/2/10
 * @Package : tankgame
 **/
public class MyTank extends Tank {
    public MyTank(int x, int y) {
        super(x, y);
    }

    //Since the shooting action is performed by our tank
    //Creating a shot object here represents a design action (thread).
    Shot shot = null;
    Vector<Shot> shots = new Vector<>();

    //Create the firing method, the starting point of the bullet is determined by the current position of our tank
    public void ShotEnemyTank() {
        //A maximum of five bullets are required to be fired at a time
        if (shots.size() >= 5) {
            return;
        }

        switch (getDirect()) {
            case 0:
                shot = new Shot(getX() + 20, getY(), 0);
                break;
            case 1:
                shot = new Shot(getX() + 60, getY() + 20, 1);
                break;
            case 2:
                shot = new Shot(getX() + 20, getY() + 60, 2);
                break;
            case 3:
                shot = new Shot(getX(), getY() + 20, 3);
                break;
        }
        shots.add(shot);
        new Thread(shot).start();
    }

    public Vector<Shot> getShots() {
        return shots;
    }

    public void setShots(Vector<Shot> shots) {
        this.shots = shots;
    }

    public Shot getShot() {
        return shot;
    }

    public void setShot(Shot shot) {
        this.shot = shot;
    }
}


