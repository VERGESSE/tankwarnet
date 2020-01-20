package www.vergessen.top.decorator;

import www.vergessen.top.GameObject;

public abstract class GODecorator extends GameObject {

    GameObject go;

    public GODecorator(GameObject go){
        this.go = go;
    }
}

