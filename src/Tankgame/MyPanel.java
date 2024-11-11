package Tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener, Runnable {
    Hero hero;
    Vector<Enemy> enemies = new Vector<>();
    Vector<Bomb> bombs = new Vector<>();
    int enemySize = 3;

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < hero.bullets.size(); i++) {
                Bullet b = hero.bullets.get(i);
                //noinspection ForLoopReplaceableByForEach
                for (int j = 0; j < enemies.size(); j++) {
                    Tools.hitTank(b, enemies.get(j), bombs);
                }
            }

            //noinspection ForLoopReplaceableByForEach
            for (int i = 0; i < enemies.size(); i++) {
                for (int j = 0; j < enemies.get(i).bullets.size(); j++) {
                    Bullet b = enemies.get(i).bullets.get(j);
                    Tools.hitHero(b, hero, bombs);
                }
            }

            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public MyPanel() {
        hero = new Hero(100, 100);
        hero.setSpeed(10);
        for (int i = 0; i < enemySize; i++) {
            Enemy e = new Enemy(100 * (i + 1), 0);
            enemies.add(e);
            new Thread(e).start();
        }
        new Thread(this).start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 800, 550);
        if (hero.isLive) Tank.draw(hero.getX(), hero.getY(), g, hero.getDirection(), 1);
        Bullet.drawHeroBullet(g, hero);
        Enemy.draw(g, enemies);
        g.setColor(Color.cyan);
        Bullet.drawEnemyBullet(g, enemies);
        Bomb.draw(g, bombs);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) System.out.println(hero.getLifeBlood());
        if (!hero.isLive) return;
        if (e.getKeyCode() == KeyEvent.VK_W) {
            hero.setDirection(0);
            hero.move();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDirection(1);
            hero.move();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.setDirection(2);
            hero.move();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.setDirection(3);
            hero.move();
        } else if (e.getKeyCode() == KeyEvent.VK_J) {
            if (hero.bullets.size() <= 6) hero.shot();
        }
//        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
