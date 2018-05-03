/**
 * This class instantiates a startClass object and calls startGame
 *
 * CPSC 224-01, Spring 2018
 * Final Project
 * @author Vincent Lombardi, Luke Hartman, Mario Malodonado
 * @version V1.0 5/3/2018
 */

import java.awt.*;
import java.io.*;

public class BattleShip {

    /**
     * this method instantiates startClass
     * @param args
     * @throws FileNotFoundException
     * @throws IOException
     * @throws FontFormatException
     */
    public static void main(String[] args)
            throws FileNotFoundException, IOException, FontFormatException {
        StartClass game = new StartClass();
        game.startGame();
    }
}