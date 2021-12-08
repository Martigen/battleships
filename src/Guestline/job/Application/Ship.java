package Guestline.job.Application;

import java.awt.*;
import java.util.ArrayList;

public class Ship {

    private String name;
    private int size;
    private int damage;
    private boolean alive;
    private ArrayList<Point> position;
    private String sign;

    public Ship(String name,String sign, int size) {
        this.name = name;
        this.sign = sign;
        this.size = size;
        this.damage = 0;
        this.alive = true;
    }

    public Ship(Ship ship) {
        this.name = ship.name;
        this.sign = ship.sign;
        this.size = ship.size;
        this.damage = ship.damage;
        this.alive = ship.alive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public ArrayList<Point> getPosition() {
        return position;
    }

    public void setPosition(ArrayList<Point> position) {
        this.position = position;
    }

    public void takeDamage(Point point){
        this.damage++;
        if(this.damage >= size){
            this.alive = false;
        }
        this.position.removeIf(p -> p.equals(point));
    }




}
