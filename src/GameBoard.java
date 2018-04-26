/**
 * This class displays the game screen that allows a player to attack
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class GameBoard extends JPanel implements ActionListener {
    
    private Board armada1;
    private Board armada2;
    private JButton[][] positionGrid;
    private JPanel southPanel; // Panel to contain key and continue button
    Font font; 
    private JButton continueButton = new JButton("CONTINUE");

     /**
     * GameBoard constructor
     */
    public GameBoard(){
        // Set up main JFrame and main JPanel
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        // Set up panel to contain key and continue button
        southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(Color.BLACK);
        
        // Read .ttf file 
        readFontFile();    
        font = font.deriveFont(30f);
        
        addLabels();
        // Create a board on the left side of the screen
        createBoard(BorderLayout.LINE_START);
        // Create a board on the right side of the screen        
        createBoard(BorderLayout.LINE_END);
        addKey();
        addContinueButton();
        
        add(southPanel,BorderLayout.SOUTH);
    }
    
     /**
     * Reads from a .ttf file
     */
    private void readFontFile(){
        //Read from font file
        try{
            //File path may need changing
            InputStream is = new BufferedInputStream(new FileInputStream("res/RobotoMono-Medium.ttf"));
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        }catch(FileNotFoundException e){
            System.out.println("File not found");
        }catch(IOException e){
            System.out.println("Input/Output error");
        }catch(FontFormatException e){
            System.out.println("Font format exception");
        }
    }
    
     /**
     * Adds labels for each board
     */
    private void addLabels(){
        // IMPORTANT!!! The string below will be replaced by the opponent's name
        String opponent = "MARIO" + "'S";
        
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.setBackground(Color.BLACK);
        
        // Creates label for player's board
        JLabel playerLabel = new JLabel("YOUR BOARD");
        playerLabel.setBorder(BorderFactory.createEmptyBorder(30,100,0,100));
        playerLabel.setFont(font);
        playerLabel.setForeground(Color.RED);
        labelPanel.add(playerLabel,BorderLayout.LINE_START);
        
        // Creates label for opponent's board
        JLabel opponentLabel = new JLabel(opponent + " BOARD");
        opponentLabel.setBorder(BorderFactory.createEmptyBorder(30,100,0,100));
        opponentLabel.setFont(font);        
        opponentLabel.setForeground(Color.RED);
        labelPanel.add(opponentLabel,BorderLayout.LINE_END);
        
        add(labelPanel,BorderLayout.NORTH);
    }
    
    /**
     * Creates a grid 
     * @param bound String - determines which side the grid will go on
     */
    private void createBoard(String bound){
        // Set up encompassing panels, outerPanel is BorderLayout by default
        JPanel outerPanel = new JPanel();
        JPanel innerPanel = new JPanel();
        
        positionGrid = new JButton[10][10];
        innerPanel.setLayout(new GridBagLayout());

        // Create button grid
        for(int x = 0; x < 10;x++){
            for(int y = 0; y < 10; y++){
                GridBagConstraints constraints = new GridBagConstraints();
                positionGrid[y][x] = new JButton("("+x+","+y+")");
                positionGrid[y][x].setBackground(Color.BLUE);
                constraints.fill = GridBagConstraints.BOTH;
                constraints.gridx = x;
                constraints.gridy = y;
                constraints.weightx = 1;
                constraints.weighty = 1;
                innerPanel.add(positionGrid[y][x], constraints);
            }
        }
        
        // Set dimensions of the board
        innerPanel.setPreferredSize(new Dimension(800,800));
        innerPanel.setBackground(Color.BLACK);
        
        outerPanel.add(innerPanel,BorderLayout.NORTH);
        outerPanel.setBorder(BorderFactory.createEmptyBorder(40,100,100,100)); 
        outerPanel.setBackground(Color.BLACK);        
        
        // bound is either BorderLayout.LINE_START or BorderLayout.LINE_END
        add(outerPanel,bound);
    }
    
     /**
     * Adds a key for colors
     */
    private void addKey(){
        JPanel keyPanel = new JPanel();
        keyPanel.setLayout(new FlowLayout());
        keyPanel.setBackground(Color.BLACK);
        
        JLabel hitLabel = new JLabel("HIT: RED");
        hitLabel.setForeground(Color.RED);
        hitLabel.setFont(font);
        hitLabel.setBorder(BorderFactory.createEmptyBorder(0,80,10,80));
        keyPanel.add(hitLabel);
        
        JLabel missLabel = new JLabel("MISS: WHITE");
        missLabel.setForeground(Color.WHITE);
        missLabel.setFont(font);
        keyPanel.add(missLabel);
        
        southPanel.add(keyPanel,BorderLayout.LINE_START);
    }
    
    /**
     * Adds the continue button
     */
    private void addContinueButton(){
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(Color.BLACK);
        
        continueButton.addActionListener(this);
        continueButton.setPreferredSize(new Dimension(200,75));
        continueButton.setFont(font);
        continueButton.setBackground(Color.BLACK);
        continueButton.setBorder(BorderFactory.createLineBorder(Color.RED));
        continueButton.setForeground(Color.RED);
        
        southPanel.add(continueButton,BorderLayout.LINE_END);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == continueButton){
            // Continue button action must be changed later
            System.exit(0);
        }
    }
}

