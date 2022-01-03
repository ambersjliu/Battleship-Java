package battleship.Attack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.lang.model.util.ElementScanner6;

import battleship.Model.*;
import javafx.scene.layout.CornerRadii;

public class AI {
    private Board enemyBoard;
    private ArrayList<Coordinate> pastShots = new ArrayList<Coordinate>();

    // only used for smartAI
    private int direction = 0; // 0-up, 1-right, 2-down, 3-left
    private boolean targetMode = false; // false = HUNT, true = TARGET
    private boolean directionSet = false;
    private Coordinate firstHit = new Coordinate(-1, -1);
    private ArrayList<Coordinate> hits = new ArrayList<Coordinate>();
    private HashMap<String, Ship> shipDict;
    private boolean endOfCurrentDirection;

    public Board getEnemyBoard() {
        return this.enemyBoard;
    }

    public void setEnemyBoard(Board enemyBoard) {
        this.enemyBoard = enemyBoard;
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

    public Random getRand() {
        return this.rand;
    }

    public void setRand(Random rand) {
        this.rand = rand;
    }

    Random rand = new Random();

    public void resetVals() {
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

    public Coordinate attack() {
        if (!targetMode) {
            return randAttack();
        } else {
            return target();
        }
    }

    // random attack
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

    public Coordinate target() {
        Coordinate returnCoord;
        System.out.println("Directionset: " + directionSet + " firstHit: " + firstHit);
        if (!directionSet) { // havent found the ship's next point, go around
            returnCoord = targetUnknownDirection();
        } else {
            returnCoord = targetWithDirection();
        }
        pastShots.add(returnCoord);
        return returnCoord;
    }

    private Coordinate targetUnknownDirection() {
        Coordinate returnCoord = new Coordinate(-1, -1);
        while (true) {
            System.out.println("Correct direction not found, current direction: " + direction);
            if (direction > 3) {
                System.out.println("Direction too large, exiting");
                System.exit(1);
            }
            if (endOfCurrentDirection) {
                direction++;
                endOfCurrentDirection = false;
            }
            switch (direction) {
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
                direction++;
            else {

                return returnCoord;
            }
        }

    }

    private Coordinate targetWithDirection() {
        Coordinate returnCoord = new Coordinate(-1, -1);
        boolean switchDirection = false;
        Coordinate base;
        while (true) {
            System.out.println("Direction known, current direction: " + direction);
            Coordinate lastHit = hits.get(hits.size() - 1);
            switch (direction) {
                case 0: // up
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

            while (!okShip) { // while the ship isnt ok
                startCol = rand.nextInt(Constants.boardSize); // generate new start coords
                startRow = rand.nextInt(Constants.boardSize);
                orientation = rand.nextInt(2);

                // check if it's valid
                okShip = validShip(board, shipArr[i].getShipLength(), startRow, startCol,
                        Constants.orientation[orientation]);
            }

            Ship newShip = new Ship(board, shipArr[i].getShipName(), shipArr[i].getShipLength(),
                    startRow, startCol, Constants.orientation[orientation]);
            shipDict.put(shipArr[i].getShipName(), newShip);
        }
    }

    public boolean validShip(Board board, int shipLength, int startRow, int startCol, String orientation) {

        if (orientation == "DOWN") {
            int endRow = startRow + shipLength;
            if (endRow > Constants.boardSize) {
                return false;
            }

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
        return true;
    }

    // determine whether we got hit or not
    public AttackResults getEnemyAttackResult(Board board, Coordinate enemyAttackCoord) {
        Point enemyAttackPoint = board.getPoint(enemyAttackCoord.getRow(), enemyAttackCoord.getColumn());
        if (!enemyAttackPoint.getIsTaken())
            return new AttackResults(null, "Miss");

        String hitShipName = enemyAttackPoint.getShipId();
        System.out.println("shipDict has " + shipDict.keySet());
        System.out.println("hitShipName is  " + hitShipName);
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

}
