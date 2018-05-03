/**
 * this class stores an array of tile objects and represents a single ship on the game board. The size and type if this
 * ship is variable. This class also contains a method that checks whether or not the ship has been sunk.
 *
 * CPSC 224-01, Spring 2018
 * Final Project
 * @author Vincent Lombardi
 * @version V1.0 5/3/2018
 */
public class WarShip {
    private Tile[] hull; // array of tile objects that make up this warship

    /**
     * constructor which sets all tiles in the warship to be part of a ship and sets the warships type.
     * @param coordinates array of tile's that are to make up the ship
     * @param shipType char representing this ships type
     */
    public WarShip(Tile[] coordinates, char shipType){
        hull = new Tile[coordinates.length];
        for(int index = 0; index < hull.length;index++){ // this for loop sets all of the tile's in the ship to their appropriate values
            hull[index] = coordinates[index];
            hull[index].setShip(true);
            hull[index].setShipType(shipType);
        }
    }

    /**
     * checks if ship is sunk
     * @return false if shop still has undamaged segments
     */
    public boolean isSunk(){

        for(int index = 0; index < hull.length; index++){
            if(!hull[index].getHit()){
                return false;
            }
        }
        return true;
    }

    /**
     * sinks the warship
     */
    public void scuttle(){
        for(int index = 0; index < hull.length; index++){
            hull[index].setHit(true);
        }
    }
}