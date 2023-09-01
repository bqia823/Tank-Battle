package com.tankgame;

import java.util.Vector;

//设置自己的坦克
public class MyTank extends Tank {
    public MyTank(int x, int y) {
        super(x, y);
    }

    //因为射击行为由我方坦克实行，所以在这里创建一个shot对象
    //表示一个设计行为(线程)
    Shot shot = null;
    //可以发射多颗子弹
    Vector<Shot> shots = new Vector<>();

    //创建射击方法，子弹出发点根据我方坦克当前位置决定
    public void ShotEnemyTank() {
        //要求一次最多发射5颗子弹
        if (shots.size() >= 5) {
            //直接返回，不再创建新子弹
            return;
        }

        switch (getDirect()) {//得到我方坦克的方向
            //子弹从炮筒口位置出现
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
        //将新创建的shots放到集合中
        shots.add(shot);
        //启动射击线程
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


