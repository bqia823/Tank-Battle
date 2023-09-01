package com.tankgame;

import java.util.Vector;

//敌人的坦克
public class EnemyTank extends Tank implements Runnable {
    //Using Vector to create a new property
    Vector<Shot> shots = new Vector<>();
    Vector<EnemyTank> enemyTanks = new Vector<>();
    boolean isLive = true;

    public EnemyTank(int x, int y) {
        super(x, y);
    }

    public Vector<Shot> getShots() {
        return shots;
    }

    public void setShots(Vector<Shot> shots) {
        this.shots = shots;
    }

    public Vector<EnemyTank> getEnemyTanks() {
        return enemyTanks;
    }

    /**
     * Provides a method to add myPanel member Vector<EnemyTank> enemyTanks = new Vector<>();
     * Set to private Vector<EnemyTank> enemyTanks = new Vector<>();
     *
     * @param enemyTanks
     */
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    //Determine if the current enemy tank is overlapping or colliding with one of the other enemyTanks in the enemyTanks service
    public boolean isTouchEnemyTank() {
        switch (this.getDirect()) {
            case 0: //Up
                //Comparing the current enemy tank to all other enemy tanks
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (this != enemyTank) {
                        /*
                        What is the range of x if the enemy tank is facing up/down? 【enemyTank.getX(),enemyTank.getX() + 40】
                        What is the range of y? 【enemyTank.getY(),enemyTank.getY() + 60】
                        */
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //Top left coordinates of the current tank 【this.getX(),this.getY()】
                            if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40 &&
                                    this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //Top right corner coordinates of the current tank 【this.getX() + 40,this.getY()】
                            if (this.getX() + 40 >= enemyTank.getX() && this.getX() + 40 <= enemyTank.getX() + 60 &&
                                    this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                        /*
                        1.如果敌人坦克是左/右方向   x的范围是什么【enemyTank.getX() ,enemyTank.getX() + 60】
                                                y的范围是什么【enemyTank.getY() ,enemyTank.getY() + 40】
                         */
                        //如果敌人坦克是左/右方向
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //2.当前坦克的左上角坐标【this.getX(),this.getY()】
                            if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 60 &&
                                    this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //3.当前坦克的右上角坐标【this.getX() + 40,this.getY()】
                            if (this.getX() + 40 >= enemyTank.getX() && this.getX() + 40 <= enemyTank.getX() + 60 &&
                                    this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 1: //右
                //让当前的this 敌人坦克 和 其他所有的敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从vector中取出一辆敌人的坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (this != enemyTank) {
                        /*
                        1.如果敌人坦克是上/下方向   x的范围是什么【enemyTank.getX() ,enemyTank.getX() + 40】
                                                y的范围是什么【enemyTank.getY(),enemyTank.getY()  + 40】
                         */
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //2.当前坦克的右上角坐标【this.getX() + 60,this.getY()】
                            if (this.getX() + 60 >= enemyTank.getX() && this.getX() + 60 <= enemyTank.getX() + 40 &&
                                    this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //3.当前坦克的右下角坐标【this.getX() + 60,this.getY() + 40】
                            if (this.getX() + 60 >= enemyTank.getX() && this.getX() + 60 <= enemyTank.getX() + 40 &&
                                    this.getY() + 40 >= enemyTank.getY() && this.getY() + 40 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        /*
                        1.如果敌人坦克是左/右方向   x的范围是什么【enemyTank.getX(),enemyTank.getX() + 60】
                                                y的范围是什么【enemyTank.getY(),enemyTank.getX() + 40】
                         */
                        //如果敌人坦克是左/右方向
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //2.当前坦克的右上角坐标【this.getX() + 60,this.getY()】
                            if (this.getX() + 60 >= enemyTank.getX() && this.getX() + 60 <= enemyTank.getX() + 60 &&
                                    this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //3.当前坦克的右下角坐标【this.getX() + 60,this.getY()  + 40】
                            if (this.getX() + 60 >= enemyTank.getX() && this.getX() + 60 <= enemyTank.getX() + 60 &&
                                    this.getY() + 40 >= enemyTank.getY() && this.getY() + 40 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2: //下
                //让当前的this 敌人坦克 和 其他所有的敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从vector中取出一辆敌人的坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (this != enemyTank) {
                        /*
                        1.如果敌人坦克是上/下方向   x的范围是什么【enemyTank.getX() ,enemyTank.getX() + 40】
                                                y的范围是什么【enemyTank.getY(),enemyTank.getY()  + 60】
                         */
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //2.当前坦克的左下角坐标【this.getX(),this.getY() + 60】
                            if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40 &&
                                    this.getY() + 60 >= enemyTank.getY() && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //3.当前坦克的右下角坐标【this.getX() + 40,this.getY() + 60】
                            if (this.getX() + 40 >= enemyTank.getX() && this.getX() + 40 <= enemyTank.getX() + 40 &&
                                    this.getY() + 60 >= enemyTank.getY() && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        /*
                        1.如果敌人坦克是左/右方向   x的范围是什么【enemyTank.getX(),enemyTank.getX() + 60】
                                                y的范围是什么【enemyTank.getY(),enemyTank.getX() + 40】
                         */
                        //如果敌人坦克是左/右方向
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //2.当前坦克的左下角坐标【this.getX(),this.getY() + 60】
                            if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 60 &&
                                    this.getY() + 60 >= enemyTank.getY() && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //3.当前坦克的右下角坐标【this.getX() + 40,this.getY() + 60】
                            if (this.getX() + 40 >= enemyTank.getX() && this.getX() + 40 <= enemyTank.getX() + 60 &&
                                    this.getY() + 60 >= enemyTank.getY() && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 3: //左
                //让当前的this 敌人坦克 和 其他所有的敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从vector中取出一辆敌人的坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (this != enemyTank) {
                        /*
                        1.如果敌人坦克是上/下方向   x的范围是什么【enemyTank.getX() ,enemyTank.getX() + 40】
                                                y的范围是什么【enemyTank.getY(),enemyTank.getY()  + 60】
                         */
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //2.当前坦克的左上角坐标【this.getX(),this.getY()】
                            if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40 &&
                                    this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //3.当前坦克的左下角坐标【this.getX(),this.getY() + 40】
                            if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40 &&
                                    this.getY() + 40 >= enemyTank.getY() && this.getY() + 40 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        /*
                        1.如果敌人坦克是左/右方向   x的范围是什么【enemyTank.getX(),enemyTank.getX() + 60】
                                                y的范围是什么【enemyTank.getY(),enemyTank.getX() + 40】
                         */
                        //如果敌人坦克是左/右方向
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //2.当前坦克的左上角坐标【this.getX(),this.getY()】
                            if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 60 &&
                                    this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //3.当前坦克的左下角坐标【this.getX(),this.getY() + 40】
                            if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 60 &&
                                    this.getY() + 40 >= enemyTank.getY() && this.getY() + 40 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public void run() {
        while (true) {
            //如果子弹集合为0，表示没有子弹了，需要再发射一发
            //否则敌方坦克总共只能发射一发子弹
            //不过我们设置在上一发子弹销毁之前不能发射下一发子弹
            //这里可以改变if(isLive && shots.size() < 5...)，将0改成其他数字
            //控制敌人能发几发子弹
            if (isLive && shots.size() < 3) {
                //临时变量
                Shot s = null;
                //先判断坦克方向
                switch (getDirect()) {
                    case 0:
                        s = new Shot(getX() + 20, getY(), 0);
                        break;
                    case 1:
                        s = new Shot(getX() + 60, getY() + 20, 1);
                        break;
                    case 2:
                        s = new Shot(getX() + 20, getY() + 60, 2);
                        break;
                    case 3:
                        s = new Shot(getX(), getY() + 20, 3);
                        break;
                }
                shots.add(s);
                new Thread(s).start();

            }
            //首先根据敌方坦克原本的方向继续移动30步,然后休眠50毫秒再做下一步动作
            //同时设定坦克的活动不能超出碰到边缘
            switch (getDirect()) {
                case 0:
                    for (int i = 0; i < 30; i++) {
                        //满足条件不超出范围的同时，不能和其他坦克发生重叠
                        if (getY() > 0 && !isTouchEnemyTank()) {
                            moveUp();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                case 1:
                    for (int i = 0; i < 30; i++) {
                        if (getX() + 60 < 1000 && !isTouchEnemyTank()) {
                            moveRight();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                case 2:
                    for (int i = 0; i < 30; i++) {
                        if (getY() + 60 < 750 && !isTouchEnemyTank()) {
                            moveDown();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                case 3:
                    for (int i = 0; i < 30; i++) {
                        if (getX() > 0 && !isTouchEnemyTank()) {
                            moveLeft();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            //然后随机移动，随机选取方向0-3
            setDirect((int) (Math.random() * 4));//返回[0 - 4)
            //在多线程中，要设置好什么时候结束线程
            if (!(isLive)) {
                break;
            }
        }
    }
}
