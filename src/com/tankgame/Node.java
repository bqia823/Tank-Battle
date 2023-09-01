package com.tankgame;

/**
 * @author Sarah Qiao
 * @version 1.5
 * @date 2021/2/10
 * @Package : tankgame
 * @Classname Node
 * @Description A node object that represents information about an enemy tank
 **/

public class Node {
    private int x;
    private int y;
    private int direction;

    public Node(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
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

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}