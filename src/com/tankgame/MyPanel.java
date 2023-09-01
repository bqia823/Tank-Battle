package com.tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

//坦克大战的绘图区
//为了实现键盘控制，需要设置keyListener接口
//为了实现子弹动画，myPanel需要不停重绘
// 这里需要让myPanel实现Runnable接口，使用线程
public class MyPanel extends JPanel implements KeyListener, Runnable {
    //定义我的坦克,初始化为空
    MyTank myTank = null;
    //定义敌人坦克，放到Vector集合中
    static Vector<EnemyTank> enemyTanks = new Vector<>();
    //定义一个Vector寻访nodes对象，用于恢复敌人坦克的坐标和方向
    Vector<Node> nodes = new Vector<>();
    //定义一个Vector用于存放爆炸
    static Vector<Bomb> bombs = new Vector<>();

    int enemyTankSize = 3;
    //定义三张炸弹图片用于制造爆炸效果,初始化为空
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;
    //当子弹击中坦克时，触发爆炸效果，按顺序播放三张图片


    public MyPanel(String key) {
        //需要判断有没有被记录的游戏存档,如果存在就正常执行
        //如果不存在就提示只能开启新游戏，key必须为"1"
        File file = new File(Recorder.getRecordFile());
        if(file.exists()){
            nodes = Recorder.getNodesAndTanks();
        } else{
            System.out.println("游戏存档不存在，开始新游戏");
            key = "1";
        }
        nodes = Recorder.getNodesAndTanks();
        myTank = new MyTank(150, 150);//初始化坐标
        myTank.setSpeed(5);//设置坦克移动速度
        switch (key){
            case "1":
                //在构造器中初始化敌人的坦克
                for (int i = 0; i < enemyTankSize; i++) {
                    //创建一个新的敌人的坦克对象
                    EnemyTank enemyTank = new EnemyTank((100 * (i + 1)), 0);
                    //将enemyTanks设置给enemyTank，这样Vector中才会有对象存在
                    //敌方坦克才会遍历碰撞方法防止坦克重叠
                    enemyTank.setEnemyTanks(enemyTanks);
                    //设置好方向后再加入Vector
                    enemyTank.setDirect(2);
                    //启动敌方坦克的移动线程，随机移动
                    new Thread(enemyTank).start();
                    //给敌方坦克加入一颗子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    //加入敌方坦克的集合成员
                    enemyTank.shots.add(shot);
                    //启动shot对象
                    new Thread(shot).start();
                    //加入一个敌方
                    enemyTanks.add(enemyTank);
                }
                break;
            case "2"://继续上局游戏
                //在构造器中初始化敌人的坦克
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    //创建一个新的敌人的坦克对象
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());
                    //将enemyTanks设置给enemyTank，这样Vector中才会有对象存在
                    //敌方坦克才会遍历碰撞方法防止坦克重叠
                    enemyTank.setEnemyTanks(enemyTanks);
                    //设置好方向后再加入Vector
                    enemyTank.setDirect(node.getDirection());
                    //启动敌方坦克的移动线程，随机移动
                    new Thread(enemyTank).start();
                    //给敌方坦克加入一颗子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    //加入敌方坦克的集合成员
                    enemyTank.shots.add(shot);
                    //启动shot对象
                    new Thread(shot).start();
                    //加入一个敌方
                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("你的输入有误");
        }

        //初始化爆炸图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/3.gif"));
        //播放指定音乐
        new PlayAudio("src\\111.wav").start();
    }
//编写方法，显示我方坦克击毁敌方坦克的信息,导入画笔工具
    public void showInfo(Graphics g){
        //画出玩家总成绩
        g.setColor(Color.BLACK);
        //设置字体，加粗，字号25
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);
        g.drawString("您累计击毁敌方坦克", 1020, 30);
        //画出一个敌方坦克
        drawTank(1020, 60, g, 0, 0);
        //需要重新设置画笔为黑色，因为在画出敌人坦克的时候
        //调用画坦克方法时已经将画笔设置为青色了
        g.setColor(Color.BLACK);
        g.drawString(Recorder.getAllEnemyTankNum() + "", 1080, 100);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //填充矩形，默认黑色
        g.fillRect(0, 0, 1000, 750);
        //如果我方坦克存在，才画出
        if (myTank != null && myTank.isLive()) {
            //画出坦克，封装方法，传入坐标和画笔type1黄色坦克为我方坦克
            drawTank(myTank.getX(), myTank.getY(), g, myTank.getDirect(), 1);
        }

        //画出我方射出的子弹
        //要判断我方子弹是否还存活，同时子弹需要在面板范围内
        //因为现在可以发射多个子弹，子弹杯存在集合之中，想要画出子弹就需要遍历取出
        if (myTank.shots.size() > 0) {
            for (int i = 0; i < myTank.shots.size(); i++) {
                Shot shot = myTank.shots.get(i);
                if (shot != null && shot.isLive()) {
                    g.draw3DRect(shot.getX(), shot.getY(), 3, 3, false);
                } else {//如果该shot对象已经被销毁或者无效,就从集合中删除
                    myTank.shots.remove(shot);
                }
            }
        }


        //画出敌人坦克,遍历Vector
        for (int i = 0; i < enemyTanks.size(); i++) {
            //从Vector取出坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            //判断敌方坦克是否还活着
            if (enemyTank.isLive()) {//只有敌人的坦克还活着，才绘制
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
                //绘制敌方坦克所有子弹
                Vector<Shot> shots = enemyTank.shots;
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    //取出子弹
                    Shot shot = shots.get(j);
                    //子弹存活的话才绘制
                    if (shot.isLive()) {
                        g.draw3DRect(shot.getX(), shot.getY(), 3, 3, false);
                    } else {
                        //子弹死亡，从Vector移除子弹
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }


        //如果集合中存在bomb,也就是说需要爆炸效果存在，这里绘制爆炸效果
        for (int i = 0; i < bombs.size(); i++) {
            //取出炸弹
            Bomb bomb = bombs.get(i);
            //根据当前爆炸的life值输出对应的图片，形成动画效果
            if (bomb.getLife() > 6) {
                g.drawImage(image1, bomb.getX(), bomb.getY(), 60, 60, this);
            } else if (bomb.getLife() > 3 && bomb.getLife() < 6) {
                g.drawImage(image2, bomb.getX(), bomb.getY(), 60, 60, this);
            } else if (bomb.getLife() <= 3) {
                g.drawImage(image3, bomb.getX(), bomb.getY(), 60, 60, this);
            }
            //每画一次bomb生命值减一
            bomb.lifeDown();
            //直到生命值减少到0，这时删除bomb对象
            if (bomb.getLife() == 0) {
                bombs.remove(bomb);
            }
        }
    }

//    /**
//     * 显示 击毁敌方的坦克信息
//     *
//     * @param g 画图
//     */
//    public void showInfo(Graphics g) {
//        g.setColor(Color.BLACK);
//        Font font = new Font("宋体", Font.BOLD, 25);
//        g.setFont(font);
//        g.drawString("您累计击毁敌方坦克", 1020, 30);
//        drawTank(1020, 60, g, 0, 0);
//        g.setColor(Color.BLACK);
//        g.drawString(Recorder.getAllEnemyTankTotal() + "", 1080, 100);
//    }

    //在这里设置好参数，包括移动的方向，坦克的类型等等
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        switch (type) {
            case 0://敌方的坦克
                g.setColor(Color.cyan);
                break;
            case 1://我们的坦克
                g.setColor(Color.yellow);
                break;
        }

        //根据坦克方向绘制不同形状的坦克。朝向不同方向，坦克形状的表达也要改变
        switch (direct) {
            case 0://这里方向0表示向上
                //画出坦克左半部分
                g.fill3DRect(x, y, 10, 60, false);
                //坦克右半部分
                g.fill3DRect(x + 30, y, 10, 60, false);
                //中间部分
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                //圆形盖子
                g.fillOval(x + 10, y + 20, 20, 20);
                //炮筒
                g.drawLine(x + 20, y + 30, x + 20, y);
                break;
            case 1://这里方向1表示向右
                //画出坦克左半部分
                g.fill3DRect(x, y, 60, 10, false);
                //坦克右半部分
                g.fill3DRect(x, y + 30, 60, 10, false);
                //中间部分
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                //圆形盖子
                g.fillOval(x + 20, y + 10, 20, 20);
                //炮筒
                g.drawLine(x + 30, y + 20, x + 60, y + 20);
                break;
            case 2://这里方向2表示向下
                //画出坦克左半部分
                g.fill3DRect(x, y, 10, 60, false);
                //坦克右半部分
                g.fill3DRect(x + 30, y, 10, 60, false);
                //中间部分
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                //圆形盖子
                g.fillOval(x + 10, y + 20, 20, 20);
                //炮筒
                g.drawLine(x + 20, y + 30, x + 20, y + 60);
                break;
            case 3://这里方向3表示向左
                //画出坦克左半部分
                g.fill3DRect(x, y, 60, 10, false);
                //坦克右半部分
                g.fill3DRect(x, y + 30, 60, 10, false);
                //中间部分
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                //圆形盖子
                g.fillOval(x + 20, y + 10, 20, 20);
                //炮筒
                g.drawLine(x + 30, y + 20, x, y + 20);
                break;
            default:
                System.out.println("暂时没有处理");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //处理按下wasd键的方法，每个键有对应的移动
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {//按下w键
            //那么坦克会改变方向并向着对应的方向移动一个像素
            //同时设置坦克的运动轨迹不能超过墙壁的范围
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
        //如果用户按下J键，则启动射击行为
        if (code == KeyEvent.VK_J) {
            //同时判断我方坦克已经发出的子弹是否被销毁
            // 发射一颗子弹的情况
            //if (myTank.shot == null || !myTank.shot.isLive()) {
            //  myTank.ShotEnemyTank();
            //}
            //发射多颗子弹
            myTank.ShotEnemyTank();
        }
        //重绘面板,让坦克按照变化后的情况重新绘制一遍
        this.repaint();
    }

    /**
     * 判断子弹和坦克
     *
     * @param shots 子弹集合
     * @param tank  坦克
     */
    public void hitTank(Vector<Shot> shots, Tank tank) {
        for (int i = 0; i < shots.size(); i++) {
            hitTank(shots.get(i), tank);
        }
    }

    public void hitTank(Shot s, Tank tank) {
        //判断子弹是否击中坦克，看子弹的坐标在不在敌方坦克的区域内
        switch (tank.getDirect()) {
            //敌方坦克朝上和朝下所占区域相同。划分为一类
            case 0://坦克向上
            case 2://坦克向下
                if (s.getX() > tank.getX() && s.getX() < tank.getX() + 40
                        && s.getY() > tank.getY() && s.getY() < tank.getY() + 60) {
                    s.setLive(false);
                    tank.setLive(false);
                    //敌方坦克被击中后，从集合中移除
                    enemyTanks.remove(tank);
                   // 当我方击毁一辆敌方坦克时，就对值++(我方坦克被击毁这个值不变)
                    if (!(tank instanceof MyTank)) {
                        Recorder.addAllEnemyTankNum();
                    }

                    //创建Bombs对象，加入bombs集合
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }

            case 1://坦克向右
            case 3://坦克向左
                if (s.getX() > tank.getX() && s.getX() < tank.getX() + 60
                        && s.getY() > tank.getY() && s.getY() < tank.getY() + 40) {
                    s.setLive(false);
                    tank.setLive(false);
                    //敌方坦克被击中后，从集合中移除
                    enemyTanks.remove(tank);
                    // 当我方击毁一辆敌方坦克时，就对值++(我方坦克被击毁这个值不变)
                    if (!(tank instanceof MyTank)) {
                        Recorder.addAllEnemyTankNum();
                    }
                    //创建Bombs对象，加入bombs集合
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }

    //重写线程接口方法
    @Override
    public void run() {//每一百毫秒重绘一次整个区域,相当于刷新
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


    //编写方法判断我方坦克子弹是否击中敌方坦克,传入一个集合中子弹和一个敌方坦克
    //如果我们的坦克可以发射多颗子弹，在判断我方坦克是否可以击中敌人时
    //需要遍历子弹集合中所有的子弹，和所有敌方坦克进行判断
    public void hitEnemyTank() {
        if (myTank.shot != null && myTank.shot.isLive()) {
            //判断子弹是否击中敌方坦克
            //此时遍历敌方坦克看看是不是都还活着
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                hitTank(myTank.shot, enemyTank);
            }
        }
    }

    //编写方法判断敌人坦克是否击中我方坦克
    public void hitMyTank() {
        if (enemyTanks.size() > 0) {
            //遍历所有敌人坦克
            for (int i = 0; i < enemyTanks.size(); i++) {
                //取出敌人坦克
                EnemyTank enemyTank = enemyTanks.get(i);
                Vector<Shot> shots = enemyTank.shots;
                if (shots.size() > 0) {
                    //再遍历所有敌方坦克的所有子弹
                    for (int j = 0; j < shots.size(); j++) {
                        //取出子弹
                        Shot shot = shots.get(j);
                        //判断子弹shot是否击中我方坦克
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
