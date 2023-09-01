package com.tankgame;
/**
 * @author Sarah Qiao
 * @version 1.5
 * @date 2021/2/10
 * @Package : tankgame
 */

public class Tank {
    private int x;
    private int y;
    private int direct;
    /*
    Writing methods that can be called to move up, down, left and right
    Because the direction property is private, calling it in MyPanel is cumbersome, so write the method wrapper directly here
    New attribute controls tank movement speed
    */
    private int speed = 1;
    private boolean isLive = true;


    public Tank(boolean isLive) {
        this.isLive = isLive;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public void moveUp() {
        y -= speed;
    }

    public void moveRight() {
        x += speed;
    }

    public void moveLeft() {
        x -= speed;
    }

    public void moveDown() {
        y += speed;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
