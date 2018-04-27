
// package com.lvm.battleship.players;

// import com.lvm.battleship.game_pieces.Fleet;

public class Player {

    String name;
    public Board armada;
    //public static Player player1;
    //public static Player player2;    

    // protected Fleet fleet;

    public Player(String name) {
        this.name = name;
        armada = new Board(this.name);
    }

    public String getName() {
        return this.name;
    }
}