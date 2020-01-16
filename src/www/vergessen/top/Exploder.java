package www.vergessen.top;

import java.awt.*;

public class Exploder {
    public static int WIDTH = ResourceMgr.explodes[0].getWidth();
    public static int HEIGHT = ResourceMgr.explodes[0].getHeight();
    private int x,y;
    private boolean living = true;
    private GameModel gameModel;

    private int step = 0;

    public Exploder(int x, int y,GameModel tankFrame) {
        this.x = x;
        this.y = y;
        this.gameModel = tankFrame;

        new Thread(()->new Audio("audio/explode.wav").play()).start();
    }

    public void paint(Graphics g){
        g.drawImage(ResourceMgr.explodes[step++],x,y,null);
        if(step >= ResourceMgr.explodes.length)
            gameModel.explodes.remove(this);


    }
}
