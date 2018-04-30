/**
 *  This Class stores a 10 by 10 array of tile objects and a and a 5 array of warships. It allows players to
 *  positions their ships, shoot at their opponents, and print out the grid.
 *
 *  Array ship positions battleship[0] aircraftCarrier[1] submarine[2] cruiser[3] patrolBoat[4]
 */

public class Board {
    private Tile[][] board;  // array of tiles representing the players board
    private WarShip[] fleet; // array of warship objects representing players fleet
    private String name;    // string for the players name
    private boolean carrierExist;
    private boolean battleshipExists;
    private boolean subExists;
    private boolean cruiserExists;
    private boolean patrolBoatExists;

    /**
     * constructor initializes 10 by 10 grid and a 5 ship fleet. all tile's on the grid are set to no be part
     * of a ship and to not be hit.
     */
    public Board(){
        board = new Tile[10][10];
        fleet = new WarShip[5];
        
        name = "Player";
        
        for(int y = 0; y < 10; y++){
            for(int x = 0; x < 10; x++ ){
                board[x][y] = new Tile();
            }
        }
    }

    /**
     * returns whether or not there is an aircraft carrier on the board
     * @return true if you placed the carrier
     */
    public boolean isCarrierExists() {
        if(carrierExist){
            return !fleet[1].isSunk();
        }
        return false;
    }

    /**
     * returns whether or not there is an battleship on the board
     * @return true if you placed the battleship
     */
    public boolean isBattleshipExists() {
        if(battleshipExists){
            return !fleet[0].isSunk();
        }
        return false;
    }

    /**
     * returns whether or not there is a submarine on the board
     * @return true if you placed the submarine
     */
    public boolean isSubExists() {
        if(subExists){
            return !fleet[2].isSunk();
        }
        return false;
    }

    /**
     * returns whether or not there is a cruiser on the board
     * @return true if you placed the cruiser
     */
    public boolean isCruiserExists() {
        if(cruiserExists){
            return !fleet[3].isSunk();
        }
        return false;
    }

    /**
     * returns whether or not there is a patrol boat on the board
     * @return true if you placed the patrol boat
     */
    public boolean isPatrolBoatExists() {
        if(patrolBoatExists){
            return !fleet[4].isSunk();
        }
        return false;
    }

    /**
     * displays your board to the enemy. Only damaged portions of ships are shown. also displays missed shots.
     */
    public void displayEnemyBoard(){
        System.out.println("  1|2|3|4|5|6|7|8|9|10");
        for(int Y = 0; Y < 10; Y++){
            System.out.print((Y+1)+"|");
            for(int X = 0; X < 10; X++) {
                if (board[Y][X].getHit() && board[Y][X].getShip()) { // displays hit ships
                    System.out.print("X|");
                } else if (board[Y][X].getHit() && !board[Y][X].getShip()) { // displays missed shots
                    System.out.print("M|");
                }else{
                    System.out.print("0|"); // displays unknown spaces
                }
            }
            System.out.println("");
        }
        System.out.println("");
    }

    /**
     * displays characters own board with ship positions and types. does not display enemy missed shots.
     */
    public void displayPlayerBoard(){
        System.out.println(name + "'s fleet");
        System.out.println("  1|2|3|4|5|6|7|8|9|10");
        for(int Y = 0; Y < 10; Y++){
            System.out.print((Y+1)+"|");
            for(int X = 0; X < 10; X++) {
                if (board[Y][X].getHit() && board[Y][X].getShip()) { // displays damaged ship components
                    System.out.print("X|");
                } else if (!board[Y][X].getHit() && board[Y][X].getShip()) { // displays undamaged ship components
                    System.out.print(board[Y][X].getShipType()+"|");
                }else{
                    System.out.print("0|"); // displays unkonwn spaces
                }
            }
            System.out.println("");
        }
        System.out.println("");
    }

    /**
     * this method allows opponents to shoot at the players board and sets target coordinates toHit feild to true.
     * If the target has already been shot at the method returns false.
     * @param x target x coordinate
     * @param y target y coordinate
     * @return true if target has not been hit yet
     */
    public boolean shoot(int x, int y){
        if(!board[y][x].getHit()) {
            board[y][x].setHit(true);
            return true;
        }
        System.out.println("that space has already been hit try again");
        return false;
    }


    /**
     * checks if their is a hit ship at this location. This method helps locate spaces that should be marked
     * red or white
     * @param x target x coordinates
     * @param y target y coordinates
     * @return true if there is a damaged ship on the specified tile
     */
    public boolean checkSpace(int x, int y){
        if((board[y][x].getShip()) && (board[y][x].getHit())){
            return true;
        }
        return false;
    }
    
    public boolean checkHit(int x, int y){
        return board[y][x].getHit();
    }

    /**
     * returns true if all ships are sunk
     * @return true if all ships are sunk
     */
    public boolean gameOver(){
        for(int index = 0; index < fleet.length; index++){
            if(!fleet[index].isSunk()){
                return false;
            }
        }
        return true;
    }

    /**
     * this is just a preset fleet configuration
     */
    public void testConfig1(){
        placeWarship(0,0,0,3,'B');
        placeWarship(2,9,7,9,'A');
        placeWarship(3,3,5,3,'S');
        placeWarship(8,5,8,7,'C');
        placeWarship(9,0,9,1,'P');
    }

    /**
     * this is just a preset fleet configuration
     */
    public void testConfig2(){
        placeWarship(0,0,3,0,'B');
        placeWarship(9,0,9,4,'A');
        placeWarship(2,4,4,4,'S');
        placeWarship(8,5,8,7,'C');
        placeWarship(1,6,1,7,'P');
    }

    /**
     * sinks everything but the patrol boat for testing
     */
    public void apocalypse(){
        fleet[0].scuttle();
        fleet[1].scuttle();
        fleet[2].scuttle();
    }

    /**
     *
     * @param x1Temp the ships first x coordinate
     * @param y1Temp the ships first y coordinate
     * @param x2Temp the ships second x coordinate
     * @param y2Temp the ships second y coordinate
     * @param shipType the ships type
     * @return if the ship was placed
     */
    public boolean placeWarship(int x1Temp, int y1Temp, int x2Temp, int y2Temp, char shipType){
        System.out.println(x1Temp + "," + y1Temp);
        System.out.println(x2Temp + "," + y2Temp);

        int size = findShipSize(shipType);
        int shipIndex = findShipIndex(shipType);
        Tile[] tempCoordinates = new Tile[size];
        int x1 = x1Temp;
        int y1 = y1Temp;
        int x2 = x2Temp;
        int y2 = y2Temp;
        int temp;
        if(x1 > x2){ // checks to make sure x-coordinates are in the correct order
            temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if(y1 > y2){ // checks to make sure y-coordinates are in the correct order
            temp = y1;
            y1 = y2;
            y2 = temp;
        }
        if(x1 == x2){
            if((y2 - y1) != (size - 1)){
                System.out.println("incorrect size");
                return false;
            }
            for(int index = 0; index < size; index++){
                if(!board[y1 + index][x1].getShip()) {
                    tempCoordinates[index] = board[y1 + (index)][x1];
                }else{
                    System.out.println("error incorrect coordinates");
                    return false;
                }
            }
        }else if(y1 == y2){
            if((x2 - x1) != (size - 1)){
                System.out.println("incorrect size");
                return false;
            }
            for(int index = 0; index < size; index++) {
                if(!board[y1][x1 + index].getShip()) {
                    tempCoordinates[index] = board[y1][x1 + (index)];
                } else {
                    System.out.println("error incorrect coordinates");
                    return false;
                }
            }
        }else{
            System.out.println("error incorrect coordinates");
            return false;
        }
        fleet[shipIndex] = new WarShip(tempCoordinates, shipType);
        setShipExists(shipType);
        return true;
    }

    /**
     * returns the index of the ship you are trying to place in the array fleet based on the type you entered
     * Array ship positions battleship[0] aircraftCarrier[1] submarine[2] cruiser[3] patrolBoat[4]
     * @param shipType a char representing the ships type
     * @return the index for the specified ship in fleet[]
     */
    private int findShipIndex(char shipType){
        if(shipType == 'B'){ // battleship
            return 0;
        } else if(shipType == 'A'){ // aircraftCarrier
            return 1;
        } else if(shipType == 'S'){ // submarine
            return 2;
        } else if(shipType == 'C'){ // cruiser
            return 3;
        } else {  // patrol boat
            return 4;
        }
    }

    /**
     * returns the size of the ship based on the type you passed in
     * @param shipType a char representing the ships type
     * @return returns the ships size
     */
    private int findShipSize(char shipType){
        if(shipType == 'A'){
            return 5;
        } else if(shipType == 'B'){
            return 4;
        } else if(shipType == 'P'){
            return 2;
        } else { // this covers the submarine and the cruiser
            return 3;
        }
    }

    /**
     * combined setter method for the exists booleans
     * @param shipType ship type
     */
    private void setShipExists(char shipType){
        if(shipType == 'B'){ // battleship
            battleshipExists = true;
        } else if(shipType == 'A'){ // aircraftCarrier
            carrierExist = true;
        } else if(shipType == 'S'){ // submarine
            subExists = true;
        } else if(shipType == 'C'){ // cruiser
            cruiserExists = true;
        } else if(shipType == 'P'){ // patrolBoat
            patrolBoatExists = true;
        }
    }

    /**
     * checks if their is a ship on a given tile
     * @param x x coordinate
     * @param y y coordinate
     * @return true if there is a ship on that tile
     */
    public boolean checkShipSpace(int x, int y){
        return board[x][y].getShip();
    }
}