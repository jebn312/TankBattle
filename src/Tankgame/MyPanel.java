package Tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener, Runnable {
    Hero hero;
    Vector<Enemy> enemies = new Vector<>();
    Vector<Boob> boobs = new Vector<>();
    Image boom1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/images/boom1.png"));
    Image boom2 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/images/boom2.png"));
    Image boom3 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/images/boom3.png"));
    int enemySize = 3;

    public MyPanel() {
        hero = new Hero(100, 100);
        hero.setSpeed(10);
        for (int i = 0; i < enemySize; i++) {
            enemies.add(new Enemy(100 * (i + 1), 0));
        }
        new Thread(this).start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 800, 550);
        drawTank(hero.getX(), hero.getY(), g, hero.getDirection(), 1);
        drawHeroBullet(g);
        drawEnemies(g);
        drawBoom(g);
    }

    public void drawEnemies(Graphics g) {
        Enemy e;
        for (int i = 0; i < enemies.size(); i++) {
            e = enemies.get(i);
            if (e.isLive) drawTank(e.getX(), e.getY(), g, e.getDirection(), 0);
            else {
                enemies.remove(e);
                i--;
            }
        }
    }

    public void drawBoom(Graphics g) {
        Boob b;
        for (int i = 0; i < boobs.size(); i++) {
            b = boobs.get(i);
            if (b.getLiveTime() > 6) g.drawImage(boom1, b.getX(), b.getY(), 60, 60, this);
            else if (b.getLiveTime() > 3) g.drawImage(boom2, b.getX(), b.getY(), 60, 60, this);
            else g.drawImage(boom3, b.getX(), b.getY(), 60, 60, this);
            if(b.isLive == false) {
                boobs.remove(b);
                i--;
            }
        }
    }

    public void drawHeroBullet(Graphics g) {
        Bullet bullet;
        for (int i = 0; i < hero.bullets.size(); i++) {
            bullet = hero.bullets.get(i);
            if (bullet.isLive()) g.fill3DRect(bullet.getX(), bullet.getY(), 3, 3, false);
            else {
                hero.bullets.remove(bullet);
                i--;
            }
        }
    }

    /**
     * @param x         坦克x坐标
     * @param y         坦克y坐标
     * @param g         画笔
     * @param direction 0上1下2左3右
     * @param type      坦克颜色
     */
    public void drawTank(int x, int y, Graphics g, int direction, int type) {
        switch (type) {
            case 0:
                g.setColor(Color.CYAN);
                break;
            case 1:
                g.setColor(Color.YELLOW);
                break;
        }
        switch (direction) {
            case 0://UP
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.drawLine(x + 20, y, x + 20, y + 30);
                g.fillOval(x + 10, y + 20, 20, 20);
                break;
            case 1://DOWN
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.drawLine(x + 20, y + 30, x + 20, y + 60);
                g.fillOval(x + 10, y + 20, 20, 20);
                break;
            case 2://LEFT
                g.fill3DRect(x - 10, y + 10, 60, 10, false);
                g.fill3DRect(x, y + 20, 40, 20, false);
                g.fill3DRect(x - 10, y + 40, 60, 10, false);
                g.drawLine(x - 10, y + 30, x + 10, y + 30);
                g.fillOval(x + 10, y + 20, 20, 20);
                break;
            case 3://RIGHT
                g.fill3DRect(x - 10, y + 10, 60, 10, false);
                g.fill3DRect(x, y + 20, 40, 20, false);
                g.fill3DRect(x - 10, y + 40, 60, 10, false);
                g.drawLine(x + 50, y + 30, x + 30, y + 30);
                g.fillOval(x + 10, y + 20, 20, 20);
                break;
            default:
                System.out.println("Not Processed");
        }
    }

    public void hitTank(Bullet b, Enemy e) {
        Boob boob = new Boob(e.getX(), e.getY());
        if (e.getDirection() == 0 || e.getDirection() == 1) {
            if (e.getX() <= b.getX() && e.getX() + 40 >= b.getX() && e.getY() <= b.getY() && e.getY() + 60 >= b.getY()) {
                e.isLive = false;
                b.setLive(false);
                boobs.add(boob);
                new Thread(boob).start();
            }
        } else {
            if (e.getX() <= b.getX() && e.getX() + 60 >= b.getX() && e.getY() <= b.getY() && e.getY() + 40 >= b.getY()) {
                e.isLive = false;
                b.setLive(false);
                boobs.add(boob);
                new Thread(boob).start();
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < hero.bullets.size(); i++) {
                Bullet b = hero.bullets.get(i);
                for (int j = 0; j < enemies.size(); j++) {
                    hitTank(b, enemies.get(j));
                }
            }

            repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
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
            hero.shot();
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
