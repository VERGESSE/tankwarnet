package www.vergessen.top;

import www.vergessen.top.net.Client;
import www.vergessen.top.net.TankDirChangedMsg;
import www.vergessen.top.net.TankStartMovingMsg;
import www.vergessen.top.net.TankStopMsg;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TankFrame extends Frame {
    public static final TankFrame INSTANCE = new TankFrame();

    Random random = new Random();
    
	Tank myTank = new Tank(random.nextInt(GAME_WIDTH - Tank.GOODWIDTH),random.nextInt(GAME_HEIGHT - Tank.GOODHEIGHT), Dir.DOWN,Group.GOOD,this);
//	Bullet bullet = new Bullet(300,300,Dir.DOWN);
	List<Bullet> bullets = new ArrayList();
	static final int GAME_WIDTH = 960,GAME_HEIGHT = 540;
    Map<UUID,Tank> tanks = new ConcurrentHashMap<>();
    List<Exploder> exploders = new ArrayList<>();
	
	private TankFrame() {
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
        g.drawString("敌人的数量" + tanks.size(),13,68);
        g.setColor(color);
        myTank.paint(g);
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }
//        for (int i = 0; i < tanks.size(); i++) {
//            tanks.get(i).paint(g);
//        }
        tanks.values().stream().forEach((e) -> e.paint(g));

        for(int i = 0; i < exploders.size(); i++){
            exploders.get(i).paint(g);
        }
        Collection<Tank> values = tanks.values();
        for(int i=0; i<bullets.size(); i++) {
            for(Tank t : values )
                bullets.get(i).collideWith(t);
        }

	}

    public Tank findByUUID(UUID id) {
        return this.tanks.get(id);
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
                case KeyEvent.VK_W: bU= true;break;
                case KeyEvent.VK_D: bR = true;break;
                case KeyEvent.VK_S: bD = true;break;
                default: break;
            }

            setMainTankDir();
        }

        private void setMainTankDir() {
            //save the old dir
            Dir dir = myTank.getDir();

            if(!bL&&!bU&&!bR&&!bD){
                myTank.setMoving(false);
                Client.INSTANCE.send(new TankStopMsg(getMyTank()));
            }else {
                if (bL) myTank.setDir(Dir.LEFT);
                if (bU) myTank.setDir(Dir.UP);
                if (bR) myTank.setDir(Dir.RIGHT);
                if (bD) myTank.setDir(Dir.DOWN);
                if (bL && bU) myTank.setDir(Dir.LEFT_UP);
                if (bL && bD) myTank.setDir(Dir.LEFT_DOWN);
                if (bR && bU) myTank.setDir(Dir.RIGHT_UP);
                if (bR && bD) myTank.setDir(Dir.RIGHT_DOWN);
                if(!myTank.isMoving())
                    Client.INSTANCE.send(new TankStartMovingMsg(getMyTank()));
                myTank.setMoving(true);

                if(dir != myTank.getDir()) {
                    Client.INSTANCE.send(new TankDirChangedMsg(myTank));
                }
            }
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
                    myTank.fire();
                    break;
                default: break;
            }
            setMainTankDir();
        }
    }

    public Tank getMyTank(){
	    return this.myTank;
    }

    public Bullet findBulletByUUID(UUID id) {
        for(int i=0; i<bullets.size(); i++) {
            if(bullets.get(i).getId().equals(id))
                return bullets.get(i);
        }

        return null;
    }

    public void addTank(Tank tank) {
        this.tanks.put(tank.id,tank);
    }

    public void addBullet(Bullet b) {
        bullets.add(b);
    }

}