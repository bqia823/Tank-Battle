package com.tankgame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class HspTankGame03 extends JFrame {
    //定义MyPanel放在JFrame里面
    MyPanel mp = null;
    Scanner myScanner = new Scanner(System.in);
    public static void main(String[] args) {
        HspTankGame03 hspTankGame03 = new HspTankGame03();

    }


    public HspTankGame03() {
        System.out.println("请输入选择： 1：新游戏，2：继续上一局游戏");
        String key = myScanner.next();
        mp = new MyPanel(key);
        this.add(mp);//加入面板(也就是游戏的绘图区域)
        this.setSize(1300, 750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addKeyListener(mp);//加入JFrame监听功能，监听mp的键盘事件
        //在JFrame中添加相应关闭窗口的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("监听到窗口关闭");
                Recorder.keepRecord();
                System.exit(0);
            }
        });
        //将mp放到Thread中并启用
        Thread thread = new Thread(mp);
        thread.start();
    }
}
