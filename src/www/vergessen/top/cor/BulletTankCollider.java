package www.vergessen.top.cor;

import www.vergessen.top.Bullet;
import www.vergessen.top.GameObject;
import www.vergessen.top.Tank;

public class BulletTankCollider implements Collider {

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if(o1 instanceof Bullet && o2 instanceof Tank){
            Bullet b = (Bullet)o1;
            Tank t = (Tank)o2;
            if (b.collideWith(t));
                return false;
        }else if(o2 instanceof Bullet && o1 instanceof Tank){
            return collide(o2, o1);
        }
        return true;
    }
}
