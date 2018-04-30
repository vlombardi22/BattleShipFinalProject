import javax.swing.JFrame;
import javax.swing.JPanel;
import sun.audio.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mario
 */
public class StartClass {
     //Global frame
    private static JFrame frame;
    //Global panels
    private static JPanel startMenuPanel;
    private static JPanel rulesPanel;
    private static JPanel namePromptPanel;

    public static Sound music;
    
    public static void startGame(){
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
        
        //Add start menu to the global frame
        frame.add(startMenuPanel);

        frame.pack();
        frame.setVisible(true);

        music = new Sound("sovietSong", true, true);
    }


    public static JFrame getFrame(){
        return frame;
    }
    
    public static JPanel getStartMenu(){
        return startMenuPanel;
    }
    
    public static JPanel getRules(){
        return rulesPanel;
    }
    
    public static JPanel getNamePrompt(){
        return namePromptPanel;
    }
}