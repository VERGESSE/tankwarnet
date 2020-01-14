package www.vergessen.top;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankFrame extends Frame {
    
	Tank myTank = new Tank(350,400, Dir.DOWN,Group.GOOD,this);
//	Bullet bullet = new Bullet(300,300,Dir.DOWN);
	List<Bullet> bullets = new ArrayList();
	static final int GAME_WIDTH = 1080,GAME_HEIGHT = 960;
    List<Tank> badTank = new ArrayList<>();
    List<Exploder> exploders = new ArrayList<>();
	
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
        Color color = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量" + bullets.size(),13,48);
        g.drawString("敌人的数量" + badTank.size(),13,68);
        g.setColor(color);
        myTank.paint(g);
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }
        for (int i = 0; i < badTank.size(); i++) {
            badTank.get(i).paint(g);
        }
        for(int i = 0; i < exploders.size(); i++){
            exploders.get(i).paint(g);
        }
        for(int i = 0; i < bullets.size() ; i++)
            for(int j = 0; j < badTank.size(); j++)
                bullets.get(i).collideWith(badTank.get(j));

	}

    class MyKeyListener extends KeyAdapter {

        boolean bL = false;
        boolean bU = false;
        boolean bR = false;
        boolean bD = false;

        @Override
        public void keyPressed(KeyEvent e) {
            new Thread(()->new Audio("audio/tank_move.wav").play()).start();
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT: bL = true;break;
                case KeyEvent.VK_UP: bU = true;break;
                case KeyEvent.VK_RIGHT: bR = true;break;
                case KeyEvent.VK_DOWN: bD = true;break;
                default: break;
            }

            setMainTankDir();
        }

        private void setMainTankDir() {
            myTank.setMoving(true);
            if(bL) myTank.setDir(Dir.LEFT);
            if(bU) myTank.setDir(Dir.UP);
            if(bR) myTank.setDir(Dir.RIGHT);
            if(bD) myTank.setDir(Dir.DOWN);
            if(bL && bU) myTank.setDir(Dir.LEFT_UP);
            if(bL && bD) myTank.setDir(Dir.LEFT_DOWN);
            if(bR && bU) myTank.setDir(Dir.RIGHT_UP);
            if(bR && bD) myTank.setDir(Dir.RIGHT_DOWN);

            if(!bL&&!bU&&!bR&&!bD) myTank.setMoving(false);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT: bL = false;break;
                case KeyEvent.VK_UP: bU = false;break;
                case KeyEvent.VK_RIGHT: bR = false;break;
                case KeyEvent.VK_DOWN: bD = false;break;
                case KeyEvent.VK_CONTROL:
                    myTank.fire();
                    break;
                default: break;
            }
            setMainTankDir();
        }
    }
}