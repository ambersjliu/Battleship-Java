package battleship;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import battleship.Attack.*;
import battleship.Model.*;


public class SaveLoad {

    //inizialize objects for load() to load into
    String username;
    int elapsedTime;
    int stage;

    Stats ourStats, enemyStats;

    ArrayList<Coordinate> pastShots = new ArrayList<Coordinate>();  
    ArrayList<Coordinate> userShots = new ArrayList<Coordinate>();     
    ArrayList<Coordinate> userHits = new ArrayList<Coordinate>();
    ArrayList<Coordinate> aiHits = new ArrayList<Coordinate>();

    ArrayList<Ship> shipsPlaced;

    //for smart ai
    StartUpParams sup;
    ArrayList<Coordinate> hits = new ArrayList<Coordinate>();  
    boolean targetMode = false;
    boolean directionSet = false;
    int direction = 0;
    Coordinate firstHit;
    boolean endOfCurrentDirection;
    
    
    public void save(String username, int elapsedTime, int stage, Stats ourStats, Stats enemyStats,
        ArrayList<Coordinate> pastShots, ArrayList<Coordinate> aiHits, ArrayList<Coordinate> userShots, 
        ArrayList<Coordinate> userHits, ArrayList<Ship> shipsPlaced,
        StartUpParams sup, ArrayList<Coordinate> hits, boolean targetMode, boolean directionSet, 
        int direction, Coordinate firstHit, boolean EndOfCurrentDirection){

        System.out.println("saving");
        
        try{
            
            //Creats a save file named after the username
            FileOutputStream saveFile = new FileOutputStream(username);
            // FileOutputStream saveFile = new FileOutputStream("SaveFile.sav");
            ObjectOutputStream save = new ObjectOutputStream(saveFile);
            
            save.writeObject(elapsedTime);//saves gamestate
            save.writeObject(stage);

            save.writeObject(enemyStats);//saves stats 
            save.writeObject(ourStats);

            save.writeObject(pastShots);//saves shots fired, for looding the board
            save.writeObject(userShots);    //as well for ai not repeating shots
            save.writeObject(userHits);
            save.writeObject(aiHits);

            save.writeObject(shipsPlaced); //saves all ship objects

            save.writeObject(sup); //saves start up parameters 

            save.writeObject(hits); //Saves smart ai's hit statuses
            save.writeObject(targetMode);
            save.writeObject(directionSet);
            save.writeObject(direction);
            save.writeObject(firstHit);
            save.writeObject(EndOfCurrentDirection);

            save.close();
        
        }catch (Exception exc){
            exc.printStackTrace();
        }


    }

    public boolean load(boolean isfileFound){
        System.out.println("loading");

        try{

            FileInputStream saveFile = new FileInputStream(username);
            // FileInputStream saveFile = new FileInputStream("SaveFile.sav");
            ObjectInputStream save = new ObjectInputStream(saveFile);

            elapsedTime = (Integer) save.readObject();
            stage = (Integer) save.readObject();
            
            enemyStats = (Stats) save.readObject();
            ourStats = (Stats) save.readObject();

            pastShots = (ArrayList<Coordinate>) save.readObject();
            userShots = (ArrayList<Coordinate>) save.readObject();
            userHits = (ArrayList<Coordinate>) save.readObject();
            aiHits = (ArrayList<Coordinate>) save.readObject();

            shipsPlaced = (ArrayList<Ship>) save.readObject();

            sup = (StartUpParams) save.readObject();

            hits = (ArrayList<Coordinate>) save.readObject();
            targetMode = (boolean) save.readObject();
            directionSet =(boolean) save.readObject();
            direction = (Integer) save.readObject();
            firstHit = (Coordinate) save.readObject();
            endOfCurrentDirection = (boolean) save.readObject();

            save.close();
            return isfileFound = true;
        }
        catch(Exception exc){
            //exc.printStackTrace();
            System.out.println(username+ " file not found");
            return isfileFound = false;
        }


    }


    public String getSaveName(){

        return username;
    }

    public void setSaveName(String username){
        this.username = username;
    }

    //getters to get the info back to control or ai

    public int getElapsedTime(){
        return elapsedTime;
    }

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

	public StartUpParams getSup() {
		return sup;
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

    public boolean getEndOfCurrentDirection() {
        return endOfCurrentDirection;
    }
}
