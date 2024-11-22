package Tankgame;

import java.awt.*;
import java.io.*;
import java.util.Properties;
import java.util.Vector;

public class Recording {
    private static int score;
    private static Vector<Enemy> enemies = new Vector<>();

    public static void draw(Graphics g) {
        int x = 830;
        int y = 60;
        g.fill3DRect(x, y, 10, 60, false);
        g.fill3DRect(x + 10, y + 10, 20, 40, false);
        g.fill3DRect(x + 30, y, 10, 60, false);
        g.drawLine(x + 20, y, x + 20, y + 30);
        g.fillOval(x + 10, y + 20, 20, 20);
        g.setColor(Color.black);
        g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g.drawString("分数" + score, 900, 95);

    }

    public static void addScore() {
        score++;
    }

    public static Vector load() throws IOException, ClassNotFoundException {
        loadItems();
        loadScore();
        return enemies;
    }

    public static void save(Vector<Enemy> es) {
        try {
            saveScore();
            saveItems(es);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadScore() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("src\\config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        score = Integer.parseInt(properties.getProperty("score"));
    }

    private static Vector<Enemy> loadItems() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src\\game.dat"));
        Object o;
        try {
            while (true) {
                o = ois.readObject();
                enemies.add((Enemy) o);
            }
        } catch (EOFException e) {
            ois.close();
        }
        return enemies;
    }

    private static void saveScore() throws IOException {
        Properties properties = new Properties();
        properties.setProperty("score", String.valueOf(score));
        properties.store(new FileOutputStream("src\\config.properties"), null);
    }

    private static void saveItems(Vector<Enemy> enemies) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src\\game.dat"));
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).isLive) oos.writeObject(enemies.get(i));
        }
        oos.close();
    }

    public static void setEnemies(Vector<Enemy> enemies) {
        Recording.enemies = enemies;
    }
}