package com.tankgame;

//发射行为做成一个线程
public class Shot implements Runnable {
    //记录子弹横纵坐标,方向和速度
    private int x;
    private int y;
    private int direct = 0;
    private int speed = 2;
    //设定一个布尔值判断子弹是否还存在(碰到边缘或敌人则子弹被销毁)
    private boolean isLive = true;

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

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }


    //构造器

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }


    @Override
    public void run() {//射击
        while (true) {
            //需要子弹休眠以呈现动画效果
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //根据方向改变x和y的值
            switch (direct){
                case 0://上
                    y -= speed;
                    break;
                case 1://右
                    x += speed;
                    break;
                case 2://下
                    y += speed;
                    break;
                case 3://左
                    x -= speed;
                    break;
            }
            //这里在不考虑敌人的情况下，子弹在碰到边界以后会被销毁。
            //边界按照面板的长宽来决定
            //设定好面板范围后取反
            //同时当子弹碰到敌方坦克后线程也会结束
            if(!(x >= 0 && x <= 1000 && y >= 0 && y <= 750 && isLive)){
                //子弹被销毁
                isLive = false;
                break;
            }

        }
    }
}
