package Tankgame;

public class Enemy extends Tank{
    public Enemy(int x, int y) {
       super(x, y);
//       Random r = new Random();
//        setDirection(r.nextInt(3));
        setDirection((int)(Math.random()*10) % 4);
    }
}
