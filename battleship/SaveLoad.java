package battleship;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import battleship.Attack.*;
import battleship.Model.*;




public class SaveLoad implements Serializable{

     //AI ai = new AI();

        String saveName;

        int enemyMisses=0;
        int enemyHits = 0;
		int ourHits = 0;
		int ourMisses = 0;

        ArrayList<Coordinate> pastShots = new ArrayList<Coordinate>();       
        ArrayList<Coordinate> userHits = new ArrayList<Coordinate>();
        ArrayList<Coordinate> aiHits = new ArrayList<Coordinate>();

        
        Ship carrier = new Ship("Carrier", 5);
		Ship battleship = new Ship("Battleship", 4);
		Ship cruiser = new Ship("Cruiser", 3);
		Ship submarine = new Ship("Submarine", 3);
		Ship destroyer = new Ship("Destroyer", 2);

        ArrayList<Ship> shipsPlaced;

        int gameMode;
        ArrayList<Coordinate> hits = new ArrayList<Coordinate>();  
        boolean mode = false;
        boolean directionSet = false;
        int direction = 0;
        Coordinate firstHit;

    public void save(String saveName, int enemyMisses, int enemyHits, int ourHits, int ourMisses, 
    ArrayList<Coordinate> pastShots,  ArrayList<Coordinate> userHits, ArrayList<Coordinate> aiHits,
     ArrayList<Ship> shipsPlaced, int gameMode, ArrayList<Coordinate> hits, boolean mode, boolean directionSet,
     int direction, Coordinate firstHit){

        try{

            FileOutputStream saveFile = new FileOutputStream(saveName);

            // FileOutputStream saveFile = new FileOutputStream("SaveFile.sav");
            ObjectOutputStream save = new ObjectOutputStream(saveFile);

            save.writeObject(enemyMisses);
            save.writeObject(enemyHits);
            save.writeObject(ourHits);
            save.writeObject(ourMisses);
           
            save.writeObject(pastShots);
            save.writeObject(userHits);
            save.writeObject(aiHits);

            save.writeObject(shipsPlaced);

            save.writeObject(gameMode);
            save.writeObject(hits);
            save.writeObject(mode);
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



    public String load(){
             

        try{
            
            FileInputStream saveFile = new FileInputStream(saveName);

            // FileInputStream saveFile = new FileInputStream("SaveFile.sav");
            
            ObjectInputStream save = new ObjectInputStream(saveFile);
        
            
            enemyMisses = (Integer) save.readObject();
            enemyHits = (Integer)save.readObject();
            ourHits = (Integer) save.readObject();
            ourMisses = (Integer) save.readObject();

            pastShots = (ArrayList<Coordinate>) save.readObject();
            userHits = (ArrayList<Coordinate>) save.readObject();
            aiHits = (ArrayList<Coordinate>) save.readObject();

            shipsPlaced = (ArrayList<Ship>) save.readObject();

            gameMode = (Integer) save.readObject();
            hits = (ArrayList<Coordinate>) save.readObject();
            mode = (boolean) save.readObject();
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
        String loadMessage = "Loading";
        
        return loadMessage;

    }


    public String getSaveName(){

        return saveName;
    }

    public void setSaveName(String saveName){
        this.saveName = saveName;
    }

    //getters to get the info back to main or ai

    public ArrayList<Coordinate> getHits(){
        return hits;
    }

    public ArrayList<Coordinate> getPastShots(){

        return pastShots;
    }
    public int getEnemyMisses(){

        return enemyMisses;
    }
    public int getEnemyHits(){
        
        return enemyHits;
    }
    public int getOurMisses(){
        
        return ourMisses;
    }
    public int getOurHits(){
        
        return ourHits;
    }

    public ArrayList<Ship> getShipsPlaced(){

        return shipsPlaced;
    }

    public ArrayList<Coordinate> getUserHits() {
        return userHits;
    }

    public ArrayList<Coordinate> getAiHits() {
        return aiHits;
    }


    public int getGameMode(){
        return gameMode;
    }

    public int getDirection() {
        return direction;
    }

    public boolean getMode() {
        return mode;
    }

    public boolean getDirectionSet() {
        return directionSet;
    }

    public Coordinate getFirstHit() {
        return firstHit;
    }
    


}
