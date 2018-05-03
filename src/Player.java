/**
 * This class stores a player object which has a board object and a name.
 *
 * CPSC 224-01, Spring 2018
 * Final Project
 * @author Vincent Lombardi
 * @version V1.0 5/3/20
 */

// package com.lvm.battleship.players;

// import com.lvm.battleship.game_pieces.Fleet;

public class Player {

    private String name; // player name
    private Board armada; // player board

    /**
     * constructor
     */
    public Player(){
        armada = new Board();
    }

    /**
     * names get method
     * @return players name
     */
    public String getName() {
        return this.name;
    }

    /**
     * name's set method
     * @param name string representing new name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * boards get method
     * @return players board
     */
    public Board getArmada(){
        return this.armada;
    }

    /**
     * boards set method
     * @param armada new board
     */
    public void setArmada(Board armada){
        this.armada = armada;
    }
}