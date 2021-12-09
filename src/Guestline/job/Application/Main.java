package Guestline.job.Application;

import java.awt.*;
import java.io.Console;
import java.util.*;

public class Main {


    static final Scanner in = new Scanner(System.in);
    static boolean displayShipsLife = true;
    static boolean displayEnemyShips = false;
    static boolean AdvancedMode = false;
    static int difficultyLevel = 5;


    public static void main(String[] args) {

        Grid battlefield = new Grid(10);
        Grid battlefield2 = new Grid(10);

        Ship ship1 = new Ship("Battleship","B",5);
        Ship ship2 = new Ship("Destroyer","D",1);
        Ship ship3 = new Ship("Destroyer","D",1);

        ArrayList<Ship> ships = new ArrayList<>(Arrays.asList(ship1,ship2,ship3));
        ArrayList<Ship> ships2 = new ArrayList<>(Arrays.asList(new Ship(ship1),new Ship(ship2),new Ship(ship3)));

        battlefield.setShips(ships);
        battlefield2.setShips(ships2);

        settings();

        // npc difficulty 0 to 10 ( 0 = random actions, 10 = 100% accuracy)
        NPC npc = new NPC(battlefield,difficultyLevel);


        play(battlefield,battlefield2,npc);

    }

    public static void settings() {

        displayShipsLife = getSetting("Enemy ships life visible (T=True, F=False): ");
        displayEnemyShips = getSetting("Enemy ships visible (T=True, F=False): ");
        // number of shoots equals to available ships
        AdvancedMode = getSetting("Advanced Mode? (T=True, F=False): ");

        int dl = -1;
        while (true) {

            System.out.print("Difficulty Level (0...10): ");
            // Exceptions are not the best for this, wanted to keep it simple
            try {
               dl = Integer.parseInt(in.nextLine());

               if(dl <0 || dl >10) {
                   throw new Exception();
               }
            }catch (Exception e){
                System.out.println("Wrong value!");
                continue;
            }

            difficultyLevel = dl;
            break;
        }

    }


    private static boolean getSetting(String msg){

        while (true) {
            System.out.print(msg);
            String tmp = in.nextLine();
            if (tmp.equalsIgnoreCase("t")) {
                return true;
            }else if (tmp.equalsIgnoreCase("f")){
                return false;
            }
            System.out.println("Wrong value!");
        }

    }

    private static void play(Grid battlefield, Grid battlefield2, NPC npc){

        while(battlefield.getShips().stream().anyMatch(Ship::isAlive) && battlefield2.getShips().stream().anyMatch(Ship::isAlive)){
            printBattlefield(battlefield2,displayEnemyShips,displayShipsLife);
            printBattlefield(battlefield,true,true);

            ArrayList<Point> pointsToShoot = new ArrayList<>();
            if(AdvancedMode){
                for (int i = 0; i < battlefield.getShips().stream().filter(Ship::isAlive).count(); i++) {

                    System.out.println("Shot number " + (i+1) +":");
                    pointsToShoot.add(takeShoot(battlefield.getSize()));
                }
            } else
                pointsToShoot.add(takeShoot(battlefield.getSize()));
            System.out.println("\n\n");

            for (Point pointToShoot: pointsToShoot) {
                if(battlefield2.shoot(pointToShoot)){
                    System.out.println( "("+ pointToShoot.x+","+pointToShoot.y+")= HIT!!");
                } else{
                    System.out.println("("+ pointToShoot.x+","+pointToShoot.y+")= MISS");
                }
            }
            if(AdvancedMode){
                for (int i = 0; i < battlefield.getShips().size(); i++) {
                    npc.takeAction();
                }
            } else {
                npc.takeAction();
            }

        }

        if(battlefield.getShips().stream().anyMatch(Ship::isAlive))
            System.out.println("WIN!!");
        else
            System.out.println("LOSE");
    }

    private static Point takeShoot(int bfSize){

        boolean error = false;
        Point pointToShoot = new Point();

        do{
            System.out.print("Enter Y: ");
            String y = in.nextLine();
            System.out.print("Enter X: ");
            String x = in.nextLine();

            // Exceptions are not the best for this, wanted to keep it simple
            try {
                pointToShoot.y = (int) y.toLowerCase(Locale.ROOT).toCharArray()[0] - 97;
                pointToShoot.x = Integer.parseInt(x);
                if (pointToShoot.x > bfSize || pointToShoot.y > bfSize || pointToShoot.y < 0 || y.length() > 1)
                    throw new Exception();
                error = false;
            }catch (Exception e){
                System.out.println("Wrong Coordinates !! (Example: y=a,x=1)");
                error = true;
            }


        }while (error);

        return pointToShoot;
    }

    private static void printBattlefield(Grid battlefield,boolean enemy,boolean showShips){
        char[] alphabet = "ABCDEFGHIJKLMNOPQRTSUVWXYZ".toCharArray();
        int printShipsAt = battlefield.getSize() - battlefield.getShips().size();
        for (int i = -1; i < battlefield.getSize(); i++) {
            if(i < 0)
                System.out.print("_| ");
            else
                System.out.print(i + "| " );
            for (int j = 0; j < battlefield.getSize(); j++) {
                if(i == -1 )
                    System.out.print(alphabet[j]+"|");
                else
                    System.out.print(battlefield.getValueAt(i,j,enemy)+"|");
            }
            if(i >= printShipsAt && showShips){
                System.out.print("\t"+printShip(battlefield.getShips().get(i-printShipsAt)));
            }
            System.out.println();
        }
        System.out.println();
    }

    private static String printShip(Ship s){

        return "X".repeat(Math.max(0, s.getDamage())) +
                String.valueOf(s.getSign()).repeat(Math.max(0, s.getSize() - s.getDamage()));

    }
}
