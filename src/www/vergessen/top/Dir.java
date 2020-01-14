package www.vergessen.top;

import java.util.Random;

public enum Dir {
    LEFT,UP,RIGHT,DOWN,LEFT_UP,RIGHT_UP,LEFT_DOWN,RIGHT_DOWN;

    private static Random random= new Random();
    public static Dir getRandomDir(){
     return values()[random.nextInt(8)];
    }
}
