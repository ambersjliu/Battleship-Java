package battleship.Attack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


import battleship.Model.*;

public class AI {
    private ArrayList<Coordinate> pastShots = new ArrayList<Coordinate>();

    // only used for smartAI
    private int direction = 0; // 0-up, 1-right, 2-down, 3-left
    private boolean targetMode = false; // false = HUNT, true = TARGET
    private boolean directionSet = false; //whether we have found the correct direction of ship
    private Coordinate firstHit = new Coordinate(-1, -1); //first hit on a ship
    private ArrayList<Coordinate> hits = new ArrayList<Coordinate>(); //stores successful ship hits, cleared after sunk
    private HashMap<String, Ship> shipDict; //used for ship placement
    private boolean endOfCurrentDirection;
  

    Random rand = new Random();

    public void resetVals() { //clears all values used in targeting a ship after sinking
        targetMode = false;
        directionSet = false;
        direction = 0;
        firstHit.setColumn(-1);
        firstHit.setRow(-1);
        hits.clear();
        endOfCurrentDirection = false;
    }

    public Coordinate getNextMove(int gameMode) { // for console 
        if (gameMode == 1) {
            System.out.println("You picked complex.");
            return attack();
        } else {
            System.out.println("You picked simple.");
            return randAttack();
        }
    }

    public Coordinate getNextMove(boolean gameMode) { // for GUI
        if (!gameMode) {
            // System.out.println("You picked complex.");
            return attack();
        } else {
            // System.out.println("You picked simple.");
            return randAttack();
        }
    }

    public Coordinate attack() { //returns a different attack based on mode chosen
        if (!targetMode) {
            return randAttack();
        } else {
            return target();
        }
    }

    // random attack
    //used by randomAI constantly and as hunt mode for smartAI
    public Coordinate randAttack() { 
        System.out.println("Rand attack is running.");
        Coordinate coord = new Coordinate(1, 2);
        while ((coord.getRow() + coord.getColumn()) % 2 == 1 && !pastShots.contains(coord)) {
            coord.setRow(rand.nextInt(Constants.boardSize));
            coord.setColumn(rand.nextInt(Constants.boardSize));
        }
        pastShots.add(coord);
        return coord;
    }

    //two stages when targeting a ship:
    //1. the first hit has been made, look for the direction the ship is aligned in
    //2. the direction has been found, keep shooting in that direction
    public Coordinate target() {
        Coordinate returnCoord;
        System.out.println("Directionset: " + directionSet + " firstHit: " + firstHit);
        if (!directionSet) { // havent found the ship's next point, go around
            returnCoord = targetUnknownDirection();
        } else { //found the next point and direction, keep firing
            returnCoord = targetWithDirection();
        }
        //to prevent future repeated hits
        pastShots.add(returnCoord);
        return returnCoord;
    }

    //direction not found, shooting around
    private Coordinate targetUnknownDirection() {
        Coordinate returnCoord = new Coordinate(-1, -1);
        while (true) {
            System.out.println("Correct direction not found, current direction: " + direction);
            if (direction > 3) { //just in case
                System.out.println("Direction too large, exiting");
                System.exit(1);
            }
            if (endOfCurrentDirection) { //increment direction if last hit missed
                direction++;
                endOfCurrentDirection = false; //set false for next time
            }
            switch (direction) {
                //if statements to protect against going out of bounds
                case 0: // up
                    if (firstHit.getRow() != 0) {
                        returnCoord.setRow(firstHit.getRow() - 1);
                        returnCoord.setColumn(firstHit.getColumn());
                    }
                    break;
                case 1: // right
                    if (firstHit.getColumn() < Constants.boardSize - 1) {
                        returnCoord.setRow(firstHit.getRow());
                        returnCoord.setColumn(firstHit.getColumn() + 1);
                    }
                    break;
                case 2:// go down
                    if (firstHit.getRow() < Constants.boardSize - 1) {
                        returnCoord.setRow(firstHit.getRow() + 1);
                        returnCoord.setColumn(firstHit.getColumn());
                    }
                    break;
                case 3: // left
                    if (firstHit.getColumn() > 0) {
                        returnCoord.setRow(firstHit.getRow());
                        returnCoord.setColumn(firstHit.getColumn() - 1);
                    }
                    break;
                default:
                    break;
            }

            if ((returnCoord.getColumn() == -1) || (returnCoord.getRow() == -1))
            //change direction if hitting one point away from firstHit would exceed bounds
                direction++;
            else {
                return returnCoord;
            }
        }

    }


    //direction found
    private Coordinate targetWithDirection() {
        Coordinate returnCoord = new Coordinate(-1, -1);
        boolean switchDirection = false; //in case we missed but ship not sunk, go in the opposite direction from firstHit
        Coordinate base; 
        while (true) {
            System.out.println("Direction known, current direction: " + direction);
            Coordinate lastHit = hits.get(hits.size() - 1);
            switch (direction) {
                case 0: // up
                //if our last hit up/right missed (endOfCurrentDirection), then we exit and change direction to down/left
                    if (!(lastHit.getRow() == 0 || endOfCurrentDirection)) {
                        returnCoord.setRow(lastHit.getRow() - 1);
                        returnCoord.setColumn(lastHit.getColumn());
                    }
                    break;
                case 1:
                    if (!(lastHit.getColumn() == Constants.boardSize - 1) || endOfCurrentDirection) {
                        returnCoord.setRow(lastHit.getRow());
                        returnCoord.setColumn(lastHit.getColumn() + 1);
                    }
                    break;
                case 2:// go down
                //since up and right are always considered first, it's impossible to go from down to up or left to right
                //if we need to switch directions, then we proceed from the first point hit on the ship
                //otherwise, continue from the last hit fired
                    base = switchDirection ? firstHit : lastHit;

                    if (!(base.getRow() == Constants.boardSize - 1) || endOfCurrentDirection) {
                        returnCoord.setRow(base.getRow() + 1);
                        returnCoord.setColumn(base.getColumn());
                    }
                    break;
                case 3:
                    base = switchDirection ? firstHit : lastHit;
                    if (!(base.getColumn() == 0) || endOfCurrentDirection) {
                        returnCoord.setRow(base.getRow());
                        returnCoord.setColumn(base.getColumn() - 1);
                    }
                    break;
                default:
                    break;
            }
            if ((returnCoord.getColumn() == -1) || (returnCoord.getRow() == -1)) {
                if (direction < 2) { 
                    direction += 2;
                    switchDirection = true;
                } else {// We are going either left or down, and on edge or missed last shot
                        // should go back to random mode
                    resetVals();
                    return randAttack();
                }
            } else {
                return returnCoord;
            }

        }
    }

    // ship placement methods
    public void placeShips(Board board) {

        Ship carrier = new Ship("Carrier", 5);
        Ship battleship = new Ship("Battleship", 4);
        Ship cruiser = new Ship("Cruiser", 3);
        Ship submarine = new Ship("Submarine", 3);
        Ship destroyer = new Ship("Destroyer", 2);

        Ship[] shipArr = new Ship[] { carrier, battleship, cruiser, submarine, destroyer };

        shipDict = new HashMap<>();

        Random rand = new Random();
        for (int i = 0; i < shipArr.length; i++) {
            boolean okShip = false;

            int startCol = 0;
            int startRow = 0;
            int orientation = 0; // either 1 or 0: 0 is down, 1 is right
            //to simplify things, only two directions (no difference between up/down or left/right)

            while (!okShip) { // while the ship isn't in a valid position
                startCol = rand.nextInt(Constants.boardSize); // generate new start coords
                startRow = rand.nextInt(Constants.boardSize);
                orientation = rand.nextInt(2); //generate new orientation

                // check if it's valid
                okShip = validShip(board, shipArr[i].getShipLength(), startRow, startCol,
                        Constants.orientation[orientation]);
            }

            Ship newShip = new Ship(board, shipArr[i].getShipName(), shipArr[i].getShipLength(),
                    startRow, startCol, Constants.orientation[orientation]);
            shipDict.put(shipArr[i].getShipName(), newShip);
        }
    }

    //validates ship placement
    public boolean validShip(Board board, int shipLength, int startRow, int startCol, String orientation) {
        

        if (orientation == "DOWN") {
            //check if ship would exceed board size
            int endRow = startRow + shipLength;
            if (endRow > Constants.boardSize) {
                return false;
            }

            //check if ship ever overlaps other ships
            for (int i = startRow; i < endRow; i++) {
                if (!board.getPoint(i, startCol).getShipId().equals("default")) {
                    return false;
                }
            }
        }

        if (orientation == "RIGHT") {
            int endCol = startCol + shipLength;

            if (endCol > Constants.boardSize) {
                return false;
            }

            for (int i = startCol; i < endCol; i++) {
                if (!board.getPoint(startRow, i).getShipId().equals("default")) {
                    return false;
                }
            }
        }
        //if ship passes all the checks, it's ok
        return true;
    }

    // determine whether we got hit or not
    public AttackResults getEnemyAttackResult(Board board, Coordinate enemyAttackCoord) {
        Point enemyAttackPoint = board.getPoint(enemyAttackCoord.getRow(), enemyAttackCoord.getColumn());
        //if no ship at point
        if (!enemyAttackPoint.getIsTaken())
            return new AttackResults(null, "Miss");

        String hitShipName = enemyAttackPoint.getShipId();
        Ship hitShip = shipDict.get(hitShipName);

        if (hitShip.checkIfSunk(hitShipName, shipDict))
            return new AttackResults(hitShipName, "Sink");
        else
            return new AttackResults(hitShipName, "Hit");
    }

    // setters, getters for hunt and target ai...

    public int getDirection() {
        return this.direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isTargetMode() {
        return this.targetMode;
    }

    public void setTargetMode(boolean mode) {
        this.targetMode = mode;
    }

    public boolean isDirectionSet() {
        return this.directionSet;
    }

    public boolean getDirectionSet() {
        return this.directionSet;
    }

    public void setDirectionSet(boolean directionSet) {
        this.directionSet = directionSet;
    }

    public Coordinate getFirstHit() {
        return this.firstHit;
    }

    public void setFirstHit(Coordinate firstHit) {
        this.firstHit = firstHit;
    }

    public ArrayList<Coordinate> getHits() {
        return hits;
    }

    public ArrayList<Coordinate> getPastShots() {
        return this.pastShots;
    }

    public void setPastShots(ArrayList<Coordinate> pastShots) {
        this.pastShots = pastShots;
    }

    public void setHits(ArrayList<Coordinate> hits) {
        this.hits = hits;
    }

    public HashMap<String, Ship> getShipDict() {
        return this.shipDict;
    }

    public void setShipDict(HashMap<String, Ship> shipDict) {
        this.shipDict = shipDict;
    }

    public boolean isEndOfCurrentDirection() {
        return this.endOfCurrentDirection;
    }

    public boolean getEndOfCurrentDirection() {
        return this.endOfCurrentDirection;
    }

    public void setEndOfCurrentDirection(boolean endOfCurrentDirection) {
        this.endOfCurrentDirection = endOfCurrentDirection;
    }


}
