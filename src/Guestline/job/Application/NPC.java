package Guestline.job.Application;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class NPC {

    private static final Random rand = new Random();

    private Grid battlefield;
    private HashMap<Point,Boolean> actionsTaken;
    private int difficulty;
    private ArrayList<Ship> ships;

    public NPC(Grid battlefield, int difficulty) {
        this.battlefield = battlefield;
        this.difficulty = difficulty;
        this.actionsTaken = new HashMap<>();
        this.ships = battlefield.getShips();
    }

    public Point takeAction(){
        int bfSize = battlefield.getSize();
        Point p = new Point();

        if(rand.nextInt(11) <= difficulty && difficulty != 0 ){
            for(Ship s : ships){
                if(s.isAlive()) {
                    p = s.getPosition().get(0);
                }
            }

        } else
            p = new Point(rand.nextInt(bfSize),rand.nextInt(bfSize));
            while(actionsTaken.containsKey(p)){
                p = new Point(rand.nextInt(bfSize),rand.nextInt(bfSize));
            }

        boolean result = battlefield.shoot(p);
        actionsTaken.put(p,result);
        return p;
    }
}
