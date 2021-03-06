/**
 * This class extends JPanel and displays the start menu screen
 *
 * CPSC 224-01, Spring 2018
 * Final Project
 * @author Vincent Lombardi, Luke Hartman, Mario Malodonado
 * @version V1.0 5/3/2018
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class StartMenu extends JPanel implements ActionListener{
    // Containers and components
    private Box centerButtonBox = Box.createVerticalBox(); // Box to contain center buttons
    
    private JPanel southButtonPanel; // Panel to contain exit and continue buttons
    private JButton startButton = new JButton("START GAME"); // Start button
    private JButton rulesButton = new JButton("VIEW RULES"); // Rules screen button 
    private JButton exitButton = new JButton("EXIT"); // Exit button
    
    private Font font; // Object to hold font

     /**
     * Constructor responsible for displaying UI
     */
    public StartMenu(){
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        // Set up bottom button panel
        southButtonPanel = new JPanel();
        southButtonPanel.setLayout(new BorderLayout());
        southButtonPanel.setBackground(Color.BLACK);
        
        // Read from font file
        FontSetup myFont = new FontSetup();
        font = myFont.readFontFile();

        addComponents();
    }

     /**
     * Adds title and buttons
     */
    private void addComponents(){
        addTitle();
        font = font.deriveFont(30f);

        JPanel helper = new JPanel();
        helper.setBackground(Color.BLACK);

        centerButtonBox.add(Box.createVerticalStrut(150));
        addStartButton();
        centerButtonBox.add(Box.createVerticalStrut(50));
        centerButtonBox.add(Box.createGlue());
        addRulesButton();
        addExitButton();

        helper.add(centerButtonBox);
        add(helper,BorderLayout.CENTER);
        add(southButtonPanel,BorderLayout.SOUTH);
    }

     /**
     * Adds the title to the top of the screen
     */
    private void addTitle(){
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBackground(Color.BLACK);
        font = font.deriveFont(200f);

        JLabel titleLabel = new JLabel("BATTLESHIP");
        titleLabel.setFont(font);
        titleLabel.setForeground(Color.RED);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        titlePanel.add(titleLabel,BorderLayout.CENTER);
        add(titlePanel,BorderLayout.NORTH);
    }

     /**
     * Adds the start button to the Box container
     */
    private void addStartButton(){
        startButton.addActionListener(this);
        startButton.setPreferredSize(new Dimension(250,75));
        startButton.setMaximumSize(new Dimension(250,75));
        startButton.setFont(font);
        startButton.setBackground(Color.BLACK);
        startButton.setBorder(BorderFactory.createLineBorder(Color.RED));
        startButton.setForeground(Color.RED);
        startButton.setFocusPainted(false);

        centerButtonBox.add(startButton);
    }

     /**
     * Adds the rules screen button to the Box container
     */
    private void addRulesButton(){
        rulesButton.addActionListener(this);
        rulesButton.setPreferredSize(new Dimension(250,75));
        rulesButton.setMaximumSize(new Dimension(250,75));
        rulesButton.setFont(font);
        rulesButton.setBackground(Color.BLACK);
        rulesButton.setBorder(BorderFactory.createLineBorder(Color.RED));
        rulesButton.setForeground(Color.RED);

        centerButtonBox.add(rulesButton);
    }

     /**
     * Adds the exit button to the bottom of the screen
     */
    private void addExitButton(){
        exitButton.addActionListener(this);
        exitButton.setPreferredSize(new Dimension(200,75));
        exitButton.setFont(font);
        exitButton.setBackground(Color.BLACK);
        exitButton.setBorder(BorderFactory.createLineBorder(Color.RED));
        exitButton.setForeground(Color.RED);

        southButtonPanel.add(exitButton,BorderLayout.LINE_START);
    }

     /**
     * Handles each button's actions
     * 
     * @param e ActionEvent
     */
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == startButton){
            //Set current panel to not be visible
            //Add the namePromptPanel from StartClass.java to the global frame
            //Set namePromptPanel to visible

            setVisible(false);
            StartClass.getFrame().add(StartClass.getNamePrompt());
            StartClass.getNamePrompt().setVisible(true);
        }else if(e.getSource() == rulesButton){
            //Set current panel to not be visible
            //Add the rulesPanel from StartClass.java to the global frame
            //Set rulesPanel to visible

            setVisible(false);
            StartClass.getFrame().add(StartClass.getRules());
            StartClass.getRules().setVisible(true);
        }else if(e.getSource() == exitButton){
            System.exit(0);
        }
    }
}