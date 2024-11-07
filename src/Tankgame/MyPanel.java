package Tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener {
    Hero hero;
    Vector<Enemy> enemies;
    int enemySize = 3;

    public MyPanel() {
        hero = new Hero(100, 100);
        enemies = new Vector<>();
        hero.setSpeed(10);
        for (int i = 0; i < enemySize; i++) {
            enemies.add(new Enemy(100 * (i + 1), 0));
        }

        new Thread(() -> {
            while (true) {
                repaint();
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 800, 550);
        drawTank(hero.getX(), hero.getY(), g, hero.getDirection(), 1);
        for (Enemy e : enemies) {
            drawTank(e.getX(), e.getY(), g, e.getDirection(), 0);
        }
        if (!hero.bullets.isEmpty()) {
            for (int i = 0; i < hero.bullets.size(); i++) {
                Bullet bullet = hero.bullets.get(i);
                if (bullet.isLive()) {
                    g.fill3DRect(bullet.getX(), bullet.getY(), 3, 3, false);
                } else hero.bullets.remove(bullet);
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
        } else if(e.getKeyCode() == KeyEvent.VK_G) {

        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
