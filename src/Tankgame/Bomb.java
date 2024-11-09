package Tankgame;

public class Bomb implements Runnable{
    final private int x;
    final private int y;
    private int liveTime = 9;
    boolean isLive = true;
    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void lifeDown() {
        if(liveTime > 0) liveTime--;
        else isLive = false;
    }

    @Override
    public void run() {
        while(isLive) {
            lifeDown();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLiveTime() {
        return liveTime;
    }
}