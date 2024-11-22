import Tankgame.MyPanel;
import Tankgame.Recording;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class Game extends JFrame {
    MyPanel panel;

    public static void main(String[] args) {
        new Game();
    }

    public Game() {
        System.out.println("是否继续上局游戏？0新游戏 1继续");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        while (!(i == 0 || i == 1)) {
            System.out.println("please enter the correct number！");
            i = scanner.nextInt();
        }
        if (i == 1) {
            if (new File("src\\game.dat").exists()) {
                try {
                    MyPanel.enemies = Recording.load();
                    panel = new MyPanel(1);
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                    System.out.println("存档损坏，自动开启新游戏");
                    MyPanel.enemies = new Vector<>();
                    panel = new MyPanel(0);
                }


            } else {
                System.out.println("游戏不存在,自动开启新游戏");
                MyPanel.enemies = new Vector<>();
                panel = new MyPanel(0);
            }
        } else {
            MyPanel.enemies = new Vector<>();
            panel = new MyPanel(0);
        }
        scanner.close();
        this.add(panel);
        this.setTitle("坦克大战");
        this.setSize(1000, 550);
        this.addKeyListener(panel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Recording.save(MyPanel.enemies);
                System.exit(0);
            }
        });

    }
}
