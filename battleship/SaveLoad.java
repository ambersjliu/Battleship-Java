package battleship;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

import battleship.Attack.*;
import battleship.Model.*;




public class SaveLoad implements Serializable{

    // AI ai = new AI();
        Board ourBoard = new Board(Constants.boardSize);
	    Board enemyBoard = new Board(Constants.boardSize);

        int enemyMisses=0;
        int enemyHits = 0;
		int ourHits = 0;
		int ourMisses = 0;

        ArrayList<Coordinate> pastShots = new ArrayList<Coordinate>();       
        ArrayList<Coordinate> hits = new ArrayList<Coordinate>();  
        ArrayList<Coordinate> userHits = new ArrayList<Coordinate>();  

        Ship carrier = new Ship("Carrier", 5);
		Ship battleship = new Ship("Battleship", 4);
		Ship cruiser = new Ship("Cruiser", 3);
		Ship submarine = new Ship("Submarine", 3);
		Ship destroyer = new Ship("Destroyer", 2);

        Ship[] shipArr = new Ship[] { carrier, battleship, cruiser, submarine, destroyer };


    public void save(int enemyMisses, int enemyHits, int ourHits, int ourMisses, 
    ArrayList<Coordinate> pastShots, ArrayList<Coordinate> hits, ArrayList<Coordinate> userHits, 
    Ship [] shipArr, Ship carrier){

        try{
            FileOutputStream saveFile = new FileOutputStream("SaveFile.sav");
            ObjectOutputStream save = new ObjectOutputStream(saveFile);

            save.writeObject(enemyMisses);
            save.writeObject(enemyHits);
            save.writeObject(ourHits);
            save.writeObject(ourMisses);
           
            save.writeObject(pastShots);
            save.writeObject(hits);
            save.writeObject(userHits);

            save.writeObject(shipArr);


            // save.writeObject(carrier);
            // save.writeObject(battleship);
            // save.writeObject(crusier);
            // save.writeObject(submarine);
            // save.writeObject(destroyer);
            //save our ship placements
            //save time

            save.close();
        }catch (Exception exc){
            exc.printStackTrace();
        }
    }


    public int load(){
             

        try{
            FileInputStream saveFile = new FileInputStream("SaveFile.sav");
            
            ObjectInputStream save = new ObjectInputStream(saveFile);
        
            
            enemyMisses = (Integer) save.readObject();
            enemyHits = (Integer)save.readObject();
            ourHits = (Integer) save.readObject();
            ourMisses = (Integer) save.readObject();

            pastShots = (ArrayList<Coordinate>) save.readObject();
            hits = (ArrayList<Coordinate>) save.readObject();
            userHits = (ArrayList<Coordinate>) save.readObject();


            shipArr = (Ship []) save.readObject();
            // carrier = (Ship) save.readObject();
            // battleship = (Ship) save.readObject();
            // cruiser = (Ship) save.readObject();
            // submarine = (Ship) save.readObject();
            // destroyer = (Ship) save.readObject();

            //load timer
            //load our ship placements

            // for (Coordinate pastShot : hits) {
			// 	System.out.println(pastShot);
			// }

            save.close();

        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        return enemyMisses;

    }

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

    public Ship[] getShipArr(){

        return shipArr;
    }


    public ArrayList<Coordinate> getUserHits() {
        return userHits;
    }

    
}
