/**
 * this class stores whether or not individual ships have been already shot at, are part of a ship, and what
 * kind of a ship they are a part of.
 */
public class Tile {
    private boolean isShip; // boolean for if this tile is part of a ship
    private boolean isHit;  // boolean for if this tile iis hit
    private char shipType;  // Char for what kind of ship this tile is a part of


    /**
     * default value constructor
     */
    public Tile() {
        this.isShip = false;
        this.isHit = false;
        shipType = '0';
    }

    /**
     *
     * @param shipType char representing ship type
     */
    public void setShipType(char shipType){
        this.shipType=shipType;
    }

    /**
     * returns what kind of ship this is a part of
     * @return char representing ship type
     */
    public char getShipType(){
        return shipType;
    }

    /**
     * isShips getter function
     * @return true if this tile is part of a ship
     */
    public boolean getShip() {
        return isShip;
    }

    /**
     * isShips setter function
     * @param ship boolean representing whether or not this tile is part of a ship
     */
    public void setShip(boolean ship) {
        isShip = ship;
    }

    /**
     * isHits getter function
     * @return true if this tile has been shot at
     */
    public boolean getHit() {
        return isHit;
    }

    /**
     * sets whether or not this tile has been shot at
     * @param hit boolean representing if this tile has been hit
     */
    public void setHit(boolean hit) {
        isHit = hit;
    }


}
