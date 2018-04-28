
// package com.lvm.battleship.players;

// import com.lvm.battleship.game_pieces.Fleet;

public class Player {

    private String name;
    private Board armada;
    
    public Player(){
        armada = new Board();
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public Board getArmada(){
        return this.armada;
    }
    
    public void setArmada(Board armada){
        this.armada = armada;
    }
}