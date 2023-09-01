package com.tankgame;

public class Tank {
    private int x;//横坐标
    private int y;//纵坐标
    //坦克的方向(上下左右0123)，在写程序的过程中发现这个需要被设置成变量以简化代码
    private int direct;
    //编写可以被调用的方法，实现上下左右移动
    //因为方向这个属性为私有，在MyPanel中调用就会比较麻烦，所以直接在这里编写方法封装
    //新属性控制坦克移动速度
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

    public void moveUp(){
        y -= speed;
    }
    public void moveRight(){
        x += speed;
    }
    public void moveLeft(){
        x -= speed;
    }
    public void moveDown(){
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

    public static void main(String[] args) {

    }
}
