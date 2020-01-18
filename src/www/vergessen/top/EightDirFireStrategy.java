package www.vergessen.top;

public class EightDirFireStrategy implements FireStrategy {
    @Override
    public void fire(Tank tank) {
        if(tank.group == Group.GOOD)
            new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
        int bx = tank.x + Tank.GOODWIDTH / 2 - Bullet.WIDTH / 2;
        int by = tank.y + Tank.GOODHEIGHT / 2 - Bullet.HEIGHT /2;
        for(Dir dir: Dir.values()) {
            new Bullet(bx, by, dir, tank.group);
        }
    }
}
