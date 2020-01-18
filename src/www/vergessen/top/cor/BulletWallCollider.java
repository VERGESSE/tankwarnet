package www.vergessen.top.cor;

import www.vergessen.top.Bullet;
import www.vergessen.top.GameObject;
import www.vergessen.top.Tank;
import www.vergessen.top.Wall;

public class BulletWallCollider implements Collider {

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if(o1 instanceof Bullet && o2 instanceof Wall){
            Bullet b = (Bullet)o1;
            Wall w = (Wall)o2;
            if(b.rectangle.intersects(w.rectangle)){
                b.die();
            }
        }else if(o2 instanceof Bullet && o1 instanceof Wall){
            return collide(o2, o1);
        }
        return true;
    }
}
