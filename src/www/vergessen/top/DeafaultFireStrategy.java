package www.vergessen.top;

import www.vergessen.top.decorator.RectDecorator;

public class DeafaultFireStrategy implements FireStrategy {

    @Override
    public void fire(Tank tank) {
        if(tank.group == Group.GOOD)
            new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
        int bx = tank.x + Tank.GOODWIDTH / 2 - Bullet.WIDTH / 2;
        int by = tank.y + Tank.GOODHEIGHT / 2 - Bullet.HEIGHT /2;
        new Bullet(bx, by, tank.dir,tank.group);
//        GameModel.getInstance().add(new RectDecorator(new Bullet(bx, by, tank.dir,tank.group)));
    }
}
