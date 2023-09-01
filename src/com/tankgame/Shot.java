package com.tankgame;
/**
 * @author Sarah Qiao
 * @version 1.5
 * @date 2021/2/10
 * @Package : tankgame
 */
public class Shot implements Runnable {
    private int x;
    private int y;
    private int direct = 0;
    private int speed = 2;
    //Set a Boolean value to determine if the bullet still
    //exists (the bullet is destroyed if it hits an edge or an enemy)
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

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }


    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //Changing the values of x and y depending on the direction
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

            /*
            Here, regardless of the enemy, the bullet will be destroyed when it hits the border.
            The boundary is determined by the length and width of the panel. Set the panel range
            and reverse it. The thread also ends when the bullet hits an enemy tank
            */
            if(!(x >= 0 && x <= 1000 && y >= 0 && y <= 750 && isLive)){
                isLive = false;
                break;
            }
        }
    }
}
