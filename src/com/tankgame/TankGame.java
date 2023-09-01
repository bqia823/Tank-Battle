package com.tankgame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;
/**
 * @author Sarah Qiao
 * @version 1.5
 * @date 2021/2/10
 * @Package : tankgame
 **/
public class TankGame extends JFrame {
    MyPanel mp = null;
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        TankGame tankGame01 = new TankGame();
    }

    public TankGame() {
        System.out.println("Please input selection 1：New game 2：Contineous");
        String key = scanner.next();
        mp = new MyPanel(key);
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);
        this.setSize(1300, 950);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //Add the corresponding closing window handling in JFrame
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
                System.exit(0);
            }
        });
    }
}
