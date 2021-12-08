package Guestline.job.Application;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Grid {

    static final String WATER = "~";
    private static final String MISS = "M";
    private static final String HIT = "X";

    private static final Random rand = new Random();

    private String [][] grid;
    private int size;
    private ArrayList<Ship> ships;


    public Grid(int size) {
        this.ships = new ArrayList<>();
    this.size = size;
        this.grid = new String [size][size];
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.grid[i][j] = WATER;
            }
        }
    }

    public String [][] getGrid() {
        return grid;
    }

    public void setGrid(String [][] grid) {
        this.grid = grid;
    }

    public int getSize() {
        return size;
    }

    public void setSizeAndRemake(int size) {
        this.size = size;
        this.grid = new String [size][size];
    }

    public void setShips(ArrayList<Ship> ships) {

        this.ships = ships;
        for( Ship s : this.ships) {
            s.setPosition(placeShip(s));
            System.out.println(s.getPosition());
        }


    }

    public boolean shoot(Point p){
        if(grid[p.x][p.y].equals(WATER) || grid[p.x][p.y].equals(MISS)){
            grid[p.x][p.y] = MISS;
            return false;
        }else{
            grid[p.x][p.y] = HIT;
            for (Ship s :ships){
                if(s.getPosition().contains(p)){
                    s.takeDamage(p);
                }
            }
            return true;
        }
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    private ArrayList<Point> placeShip(Ship ship){

        ArrayList<Point> points = new ArrayList<>();
        Point startPoint = new Point(rand.nextInt(size),rand.nextInt(size));
        Point nextPoint = (Point) startPoint.clone();

        int x = rand.nextInt(3)-1;
        int y = 0;

        if(x == 0)
            while( y == 0)
                y = rand.nextInt(3)-1;


        for (int i = 0; i < ship.getSize(); i++) {

            if(nextPoint.x >= this.size && x == 1){
                nextPoint.x = startPoint.x -1;
                x = -1;
            } else if (nextPoint.x <= 0 && x == -1){
                nextPoint.x = startPoint.x +1;
                x = 1;
            } else if (nextPoint.y >= this.size && y == 1){
                nextPoint.y = startPoint.y -1;
                y = -1;
            } else if (nextPoint.y <= 0 && y == -1){
                nextPoint.y = startPoint.y +1;
                y = 1;
            }

                if(!this.grid[nextPoint.x][nextPoint.y].equals(WATER)) {
                points = new ArrayList<>();
                i = 0;
                    startPoint = new Point(rand.nextInt(size),rand.nextInt(size));
                    nextPoint = new Point((Point) startPoint.clone());
            }

            points.add((Point) nextPoint.clone());
            nextPoint.x+=x;
            nextPoint.y+=y;


        }

        for (Point p: points)
            this.grid[p.x][p.y] = ship.getSign();


        return points;
    }


    public String getValueAt(int row, int col,boolean visible){
        if(!visible && !this.grid[row][col].equals(WATER) && !this.grid[row][col].equals(HIT) && !this.grid[row][col].equals(MISS)) {
            return WATER;
        }
        return this.grid[row][col];
    }
}
