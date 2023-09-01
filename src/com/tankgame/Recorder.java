package com.tankgame;

import java.io.*;
import java.util.Vector;

/**
 * @author Sarah Qiao
 * @version 1.5
 * @date 2021/2/10
 * @Package : tankgame
 * @Classname Recorder
 * @Description Recording related information and interact with files
 */
public class Recorder {
    private static int allEnemyTankTotal = 0;
    private static BufferedWriter bufferedWriter = null;
    private static BufferedReader bufferedReader = null;
    private static String recordFile = "../com/MyRecord.txt";
    private static Vector<EnemyTank> enemyTanks = null;

    //Define a node Vector that holds information about enemy nodes
    private static Vector<Node> nodes = new Vector<>();

    static {
        String parentPath = System.getProperty("user.dir");
        recordFile = parentPath + "d:\\record.txt";
    }

    /**
     * This method is called when the previous office continues
     * Used to read the record.txt file and recover related information
     * @return enemy tank information
     */
    public static Vector<Node> getNodesAndTanks() {
        try {
            bufferedReader = new BufferedReader(new FileReader(recordFile));
            //Destroyed quantity
            allEnemyTankTotal = Integer.parseInt(bufferedReader.readLine());
            String dataLine = "";
            while ((dataLine = bufferedReader.readLine()) != null) {
                String[] split = dataLine.split(" ");
                if (split.length == 3) {
                    Node node = new Node(Integer.parseInt(split[0]),
                            Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                    nodes.add(node);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return nodes;
    }

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {

        Recorder.enemyTanks = enemyTanks;
    }

    public static int getAllEnemyTankNum() {
        return allEnemyTankTotal;
    }

    public static void setAllEnemyTankNum(int allEnemyTankTotal) {
        Recorder.allEnemyTankTotal = allEnemyTankTotal;
    }

    /**
     * When our tank destroys an enemy tank, ++ should be applied to this value
     */
    public static void addAllEnemyTankNum() {

        Recorder.allEnemyTankTotal++;
    }

    /**
     * 当游戏退出时，我们将 allEnemyTankNum 保存到 recordFile
     */
    public static void keepRecord() {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(recordFile));
            bufferedWriter.write(allEnemyTankTotal + "\r\n");
            //Variable enemy tank collection, then save depending on the situation
            if (enemyTanks.size() > 0) {
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank.isLive()) {
                        String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect();
                        bufferedWriter.write(record);
                        bufferedWriter.newLine();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getRecordFile() {
        return recordFile;
    }

    public static void setRecordFile(String recordFile) {
        Recorder.recordFile = recordFile;
    }
}