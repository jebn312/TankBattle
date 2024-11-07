package Tankgame;

import javax.swing.*;

public class Game extends JFrame {
    MyPanel panel;
    public static void main(String[] args) {
        new Game();
    }

    public Game() {
        panel = new MyPanel();
        this.add(panel);
        this.setTitle("坦克大战");
        this.setSize(800, 550);
        this.addKeyListener(panel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
