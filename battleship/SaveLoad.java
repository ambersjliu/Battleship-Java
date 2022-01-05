package battleship;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import battleship.Attack.*;
import battleship.Model.*;


public class SaveLoad {

    public static final boolean IsRandomPicked = false;
    String saveName;
    int stage;

    Stats ourStats, enemyStats;

    ArrayList<Coordinate> pastShots = new ArrayList<Coordinate>();  
    ArrayList<Coordinate> userShots = new ArrayList<Coordinate>();     
    ArrayList<Coordinate> userHits = new ArrayList<Coordinate>();
    ArrayList<Coordinate> aiHits = new ArrayList<Coordinate>();


    Ship carrier = new Ship("Carrier", 5);
    Ship battleship = new Ship("Battleship", 4);
    Ship cruiser = new Ship("Cruiser", 3);
    Ship submarine = new Ship("Submarine", 3);
    Ship destroyer = new Ship("Destroyer", 2);

    ArrayList<Ship> shipsPlaced;

    //for smart ai
    boolean israndomAIPicked;
    ArrayList<Coordinate> hits = new ArrayList<Coordinate>();  
    boolean targetMode = false;
    boolean directionSet = false;
    int direction = 0;
    Coordinate firstHit;
    

    public void save(String saveName, int stage, Stats ourStats, Stats enemyStats,
        ArrayList<Coordinate> pastShots, ArrayList<Coordinate> aiHits, ArrayList<Coordinate> userShots, 
        ArrayList<Coordinate> userHits, ArrayList<Ship> shipsPlaced,
        boolean israndomAIPicked, ArrayList<Coordinate> hits, boolean targetMode, boolean directionSet, 
        int direction, Coordinate firstHit){


        System.out.println("saving");
        
        try{

            //FileOutputStream saveFile = new FileOutputStream(saveName);

            FileOutputStream saveFile = new FileOutputStream("SaveFile.sav");
            ObjectOutputStream save = new ObjectOutputStream(saveFile);

            save.writeObject(stage);

            save.writeObject(enemyStats);
            save.writeObject(ourStats);

            save.writeObject(pastShots);
            save.writeObject(userShots);
            save.writeObject(userHits);
            save.writeObject(aiHits);

            save.writeObject(shipsPlaced);

            save.writeObject(israndomAIPicked);
            save.writeObject(hits);
            save.writeObject(targetMode);
            save.writeObject(directionSet);
            save.writeObject(direction);
            save.writeObject(firstHit);

            System.out.println(firstHit.toString());



            //save time

            save.close();
        
        }catch (Exception exc){
            exc.printStackTrace();
        }


        


    }

    public void load(){
        System.out.println("loading");

        try{

            //FileInputStream saveFile = new FileInputStream(saveName);

            FileInputStream saveFile = new FileInputStream("SaveFile.sav");
            ObjectInputStream save = new ObjectInputStream(saveFile);

            stage = (Integer) save.readObject();
            
            enemyStats = (Stats) save.readObject();
            ourStats = (Stats) save.readObject();

            pastShots = (ArrayList<Coordinate>) save.readObject();
            userShots = (ArrayList<Coordinate>) save.readObject();
            userHits = (ArrayList<Coordinate>) save.readObject();
            aiHits = (ArrayList<Coordinate>) save.readObject();

            shipsPlaced = (ArrayList<Ship>) save.readObject();

            israndomAIPicked = (Boolean) save.readObject();
            hits = (ArrayList<Coordinate>) save.readObject();
            targetMode = (boolean) save.readObject();
            directionSet =(boolean) save.readObject();
            direction = (Integer) save.readObject();
            firstHit = (Coordinate) save.readObject();

            System.out.println(firstHit.toString());


            //load timer

            // for (Coordinate pastShot : hits) {
			// 	System.out.println(pastShot);
			// }
            save.close();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }


    }


    public String getSaveName(){

        return saveName;
    }

    public void setSaveName(String saveName){
        this.saveName = saveName;
    }


    //getters to get the info back to control or ai

    public int getStage(){
        return stage;
    }

    public Stats getOurStats(){
        return ourStats;
    }

    public Stats getEnmeyStats(){
        return enemyStats;
    }

    public ArrayList<Ship> getShipsPlaced(){

        return shipsPlaced;
    }

    public ArrayList<Coordinate> getPastShots() {

        return pastShots;
    }

    public ArrayList<Coordinate> getUserShots() {
        return userShots;
    }

    public ArrayList<Coordinate> getUserHits() {
        return userHits;
    }

    public ArrayList<Coordinate> getAiHits() {
        return aiHits;
    }

	public boolean israndomAIPicked() {
		return israndomAIPicked;
	}

    public ArrayList<Coordinate> getHits(){
        return hits;
    }

    public int getDirection() {
        return direction;
    }

    public boolean getTargetMode() {
        return targetMode;
    }

    public boolean getDirectionSet() {
        return directionSet;
    }

    public Coordinate getFirstHit() {
        return firstHit;
    }
}
