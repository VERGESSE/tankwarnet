package www.vergessen.top.decorator;

import www.vergessen.top.GameObject;

import java.awt.*;

public class RectDecorator extends GODecorator {

    public RectDecorator(GameObject go) {
        super(go);
    }

    @Override
    public void paint(Graphics g) {
        this.x = go.x;
        this.y = go.y;
        go.paint(g);
        Color color = g.getColor();
        g.setColor(Color.YELLOW);
        g.drawRect(go.x,go.y,go.getWidth(),go.getHeight());
        g.setColor(color);
    }

    @Override
    public int getWidth() {
        return super.go.getWidth();
    }

    @Override
    public int getHeight() {
        return super.go.getHeight();
    }
}
