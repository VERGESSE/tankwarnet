package www.vergessen.top;

import java.awt.*;

public class Bullet extends GameObject {
    private static final int SPEED = 15;
    public static int WIDTH = ResourceMgr.bulletD.getWidth();
    public static int HEIGHT = ResourceMgr.bulletD.getHeight();
    private int x,y;
    private Dir dir;
    private boolean living = true;
    private Group group = Group.BAD;
    private GameModel gameModel;

    private Rectangle rectangle = new Rectangle();

    public Bullet(int x, int y, Dir dir,Group group,GameModel gameModel) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.gameModel = gameModel;

        rectangle.x = this.x;
        rectangle.y = this.y;
        rectangle.width = WIDTH;
        rectangle.height = HEIGHT;

        gameModel.add(this);
    }

    public void paint(Graphics g){
        if(!living){
            gameModel.remove(this);
        }
        switch (dir){
            case UP:g.drawImage(ResourceMgr.bulletU,x,y,null);break;
            case DOWN:g.drawImage(ResourceMgr.bulletD,x,y,null);break;
            case LEFT:g.drawImage(ResourceMgr.bulletL,x,y,null);break;
            case RIGHT:g.drawImage(ResourceMgr.bulletR,x,y,null);break;
            case LEFT_UP:g.drawImage(ResourceMgr.bulletLU,x,y,null);break;
            case RIGHT_UP:g.drawImage(ResourceMgr.bulletRU,x,y,null);break;
            case LEFT_DOWN:g.drawImage(ResourceMgr.bulletLD,x,y,null);break;
            case RIGHT_DOWN:g.drawImage(ResourceMgr.bulletRD,x,y,null);break;
        }

        move();
    }

    private void move() {
        switch (dir){
            case UP: y-=SPEED;break;
            case LEFT: x-=SPEED;break;
            case RIGHT: x+=SPEED;break;
            case DOWN: y+=SPEED;break;
            case LEFT_UP: x-=SPEED/1.414;y-=SPEED/1.414;break;
            case LEFT_DOWN: x-=SPEED/1.414;y+=SPEED/1.414;break;
            case RIGHT_DOWN: x+=SPEED/1.414;y+=SPEED/1.414;break;
            case RIGHT_UP: x+=SPEED/1.414;y-=SPEED/1.414;break;
        }

        //update rectangle
        rectangle.x = this.x;
        rectangle.y = this.y;

        if(x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT)
            living = false;
    }

    public boolean collideWith(Tank tank) {
        if(this.group == tank.getGroup())return false;

        Rectangle rectangle1 = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
        Rectangle rectangle2 = new Rectangle(tank.getX(), tank.getY(), Tank.GOODWIDTH, Tank.GOODHEIGHT);
        if (rectangle1.intersects(rectangle2)) {
            this.die();
            tank.die();
            gameModel.add(new Explode(tank.getX() + Tank.GOODWIDTH/2- Explode.WIDTH/2,tank.getY()+Tank.GOODHEIGHT/2- Explode.HEIGHT/2,gameModel));
            return true;
        }
        return false;
    }

    private void die() {
        this.living = false;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
