import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class BattleShip {
    //Global frame
    public static JFrame frame;
    //Global panels
    public static JPanel startMenuPanel;
    public static JPanel rulesPanel;
    public static JPanel namePromptPanel;
    public static JPanel gameBoardPanel;

    public static void main(String[] args)
            throws FileNotFoundException, IOException, FontFormatException {
        //Set up global frame
        frame = new JFrame();
        frame.setTitle("BattleShip");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);

        //Set up global panels
        startMenuPanel = new StartMenu();
        rulesPanel = new Rules();
        namePromptPanel = new NamePrompt();
        gameBoardPanel = new GameBoard();
        
        //Add start menu to the global frame
        frame.add(gameBoardPanel);

        frame.pack();
        frame.setVisible(true);
    }
}