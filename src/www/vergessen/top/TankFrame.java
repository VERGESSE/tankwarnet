package www.vergessen.top;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankFrame extends Frame {

	GameModel gm = new GameModel();

    static final int GAME_WIDTH = 1080,GAME_HEIGHT = 960;

	public TankFrame() {
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setTitle("tank war");
        setVisible(true);

        this.addKeyListener(new MyKeyListener());
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

        });
    }

    Image offScreenImage = null;
	@Override
    public void update(Graphics g){
	    if(offScreenImage == null){
	        offScreenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);
        }
        Graphics gOffscreen = offScreenImage.getGraphics();
        Color c = gOffscreen.getColor();
        gOffscreen.setColor(Color.BLACK);
        gOffscreen.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
        gOffscreen.setColor(c);
        paint(gOffscreen);
        g.drawImage(offScreenImage,0,0,null);
    }
	
	@Override
	public void paint(Graphics g) {
	    gm.paint(g);
	}

    class MyKeyListener extends KeyAdapter {

        boolean bL = false;
        boolean bU = false;
        boolean bR = false;
        boolean bD = false;

        @Override
        public void keyPressed(KeyEvent e) {
//            new Thread(()->new Audio("audio/tank_move.wav").play()).start();
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_A: bL = true;break;
                case KeyEvent.VK_W: bU = true;break;
                case KeyEvent.VK_D: bR = true;break;
                case KeyEvent.VK_S: bD = true;break;
                default: break;
            }

            setMainTankDir();
        }

        private void setMainTankDir() {
            gm.getMainTank().setMoving(true);
            if(bL) gm.getMainTank().setDir(Dir.LEFT);
            if(bU) gm.getMainTank().setDir(Dir.UP);
            if(bR) gm.getMainTank().setDir(Dir.RIGHT);
            if(bD) gm.getMainTank().setDir(Dir.DOWN);
            if(bL && bU) gm.getMainTank().setDir(Dir.LEFT_UP);
            if(bL && bD) gm.getMainTank().setDir(Dir.LEFT_DOWN);
            if(bR && bU) gm.getMainTank().setDir(Dir.RIGHT_UP);
            if(bR && bD) gm.getMainTank().setDir(Dir.RIGHT_DOWN);

            if(!bL&&!bU&&!bR&&!bD) gm.getMainTank().setMoving(false);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_A: bL = false;break;
                case KeyEvent.VK_W: bU = false;break;
                case KeyEvent.VK_D: bR = false;break;
                case KeyEvent.VK_S: bD = false;break;
                case KeyEvent.VK_J:
                    gm.getMainTank().fire();
                    break;
                default: break;
            }
            setMainTankDir();
        }
    }
}