package www.vergessen.top;

import java.util.Random;

public enum Dir {
    LEFT,UP,RIGHT,DOWN,LEFT_UP,RIGHT_UP,LEFT_DOWN,RIGHT_DOWN;

    private static Random random= new Random();
    public static Dir getRandomDir(){
     return values()[random.nextInt(8)];
    }
    public static Dir getOppositeDir(Dir dir){
        if (dir == LEFT) return RIGHT;
        if (dir == UP) return DOWN;
        if (dir == RIGHT) return LEFT;
        if (dir == DOWN) return UP;
        if (dir == LEFT_DOWN) return RIGHT_UP;
        if (dir == LEFT_UP) return RIGHT_DOWN;
        if (dir == RIGHT_DOWN) return LEFT_UP;
        if (dir == RIGHT_UP) return LEFT_DOWN;
        return null;
    }
}
