/**
 * This class displays the game screen that allows a player to attack
 *
 * CPSC 224-01, Spring 2018
 * Final Project
 * @author Vincent Lombardi
 * @version V1.0 5/3/2018
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoard extends JPanel implements ActionListener {

    private Board armada1; // board object for player 1
    private Board armada2; // board object for player 2
    private String name1;  // player1's name
    private String name2;  // player2's name
    private JButton[][] positionGrid; // grid for displaying current players ships
    private JButton[][] shootingGrid; // grid for displaying enemy ships
    private JPanel southPanel; // Panel to contain key and continue button

    private JLabel playerLabel; // Displays player's name
    private JLabel opponentLabel; // Displays opponent's name
    private JLabel noticeLabel; // Displays destroyed targets
    
    private Font font; // Object to hold font
    
    private JButton continueButton = new JButton("CONTINUE"); // Continue button
    private int turnCount; // Keeps track of how many turns have passed
    private boolean finishTurn = false; // Keeps track if a turn is 

     /**
     * GameBoard constructor initializes 2 ten by ten JButton grids
     */
    public GameBoard(Player player1, Player player2){
        // Set up main JFrame and main JPanel
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        
        armada1 = player1.getArmada();
        armada2 = player2.getArmada();
        armada2.apocalypse();
        
        name1 = player1.getName();
        name2 = player2.getName();
		
        turnCount = 1;
        
        // Set up panel to contain key and continue button
        southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(Color.BLACK);
        
        // Read from font file
        FontSetup myFont = new FontSetup();
        font = myFont.readFontFile();  
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
     * Adds labels for each board
     */
    private void addLabels() {
        // IMPORTANT!!! The string below will be replaced by the opponent's name
        String name = null;
        if(turnCount%2==0) {
            name = name1;
        } else if(turnCount%2==1) {
            name = name2;
        }
        
        String opponent = name + "'S";
        
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.setBackground(Color.BLACK);
        
        // Creates label for player's board
        opponentLabel = new JLabel(name2 + "'s BOARD");
        opponentLabel.setBorder(BorderFactory.createEmptyBorder(30,100,0,100));
        opponentLabel.setFont(font);
        opponentLabel.setForeground(Color.RED);
        labelPanel.add(opponentLabel, BorderLayout.LINE_END);
        
        // Creates label for opponent's board
        playerLabel = new JLabel("YOUR BOARD");
        playerLabel.setBorder(BorderFactory.createEmptyBorder(30,100,0,100));
        playerLabel.setFont(font);        
        playerLabel.setForeground(Color.RED);
        labelPanel.add(playerLabel, BorderLayout.LINE_START);
        
        add(labelPanel, BorderLayout.NORTH);
    }
    
    /**
     * Creates a grid 
     * @param bound String - determines which side the grid will go on
     */
    private void createBoard(String bound) {
        // Set up encompassing panels, outerPanel is BorderLayout by default
        JPanel outerPanel = new JPanel();
        JPanel innerPanel = new JPanel();
        JButton[][] tempGrid;
        if (bound.equals(BorderLayout.LINE_START)) {
            positionGrid = new JButton[10][10];
            tempGrid = positionGrid;
        } else {
            shootingGrid = new JButton[10][10];
            tempGrid = shootingGrid;
        }

        innerPanel.setLayout(new GridBagLayout());

        // Create button grid
        for (int x = 0; x < 10;x++) {
            for (int y = 0; y < 10; y++) {
                GridBagConstraints constraints = new GridBagConstraints();
                tempGrid[y][x] = new JButton("("+x+","+y+")");
                tempGrid[y][x].setBackground(Color.BLUE);
                constraints.fill = GridBagConstraints.BOTH;
                constraints.gridx = x;
                constraints.gridy = y;
                constraints.weightx = 1;
                constraints.weighty = 1;
                if (!bound.equals(BorderLayout.LINE_START)) { // adds the action listener to left grid
                    tempGrid[y][x].addActionListener(this);
                }
                innerPanel.add(tempGrid[y][x], constraints);
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
        if (bound.equals(BorderLayout.LINE_START)) { // colors ships on position grid
            colorPositionGrid();
        }
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
        missLabel.setBorder(BorderFactory.createEmptyBorder(0,80,10,80));
        keyPanel.add(missLabel);

        noticeLabel = new JLabel("");
        noticeLabel.setForeground(Color.BLUE);
        noticeLabel.setFont(font);
        keyPanel.add(noticeLabel);

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

    /**
     * this method takes in a click and then checks if the player is shooting or switching turns
     * @param e the button that was pressed
     */
    @Override
    public void actionPerformed(ActionEvent e) {        
        assert(turnCount <= 200);
        String name; // player name
        if (turnCount%2==1) {
            name = name1;
        } else {
            name = name2;
        }
		
        if (e.getSource() == continueButton && finishTurn) { // Continue button action must be changed later
            if (armada1.gameOver() || armada2.gameOver()) {  // checks for a game over
                setVisible(false);
                GameManager.getFrame().add(new CongratsScreen(name)); 
            } else { // switches turns
                turnCount++;
                clearPositionGrid();
                clearShootingGrid();
                colorPositionGrid();
                colorShootingGrid();
                finishTurn = false;
                setNotice();
                if (turnCount%2==1) { // switches player names
                    name = name2;
                } else {
                    name = name1;
                }
                // Go to switch screen
                setVisible(false);
                GameManager.getSwitchScreen().setVisible(true);     
                opponentLabel.setText(name + "'S BOARD");
            }
        }else if(!finishTurn) { // called if current player is attacking
            AttackListener(e);
        }
    }

    /**
     * clears the shooting grid to be blue
     */
    private void clearShootingGrid(){
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                shootingGrid[y][x].setBackground(Color.BLUE);
            }
        }
    }

    /**
     * clears the Position grid to be blue
     */
    private void clearPositionGrid(){
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                positionGrid[y][x].setBackground(Color.BLUE);
            }
        }
    }


    /**
     * displays current players ships as grey spaces and damaged segments as red
     */
    private void colorPositionGrid() {
        Board armada;
        if (turnCount % 2 == 1) { // set board to current player
            armada = armada1;
        } else {
            armada = armada2;
        }
        armada.displayPlayerBoard();
        armada.displayEnemyBoard();

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (armada.checkSpace(x,y)) { // checks if there is a damaged ship
                    positionGrid[y][x].setBackground(Color.RED);
                } else if (armada.checkShipSpace(y,x)) { // checks if there is an undamaged ship
                    positionGrid[y][x].setBackground(Color.gray);
                }
            }
        }
    }

    /**
     * this method simply displays the shooting grid. It recolors hit tiles red or white depending on whether they
     * have been hit or not.
     */
    private void colorShootingGrid() {
        Board armada;
        if (turnCount % 2 == 1) { // sets board to current player
            armada = armada2;
        } else {
            armada = armada1;
        }

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (armada.checkSpace(x,y)) { // turns damaged ship spaces red
                    shootingGrid[y][x].setBackground(Color.RED);
                } else if (armada.checkHit(x,y)) { // turns spaces that are not part of a ship but have been shot at white.
                    shootingGrid[y][x].setBackground(Color.WHITE);
                }
            }
        }
    }

    /**
     * allows users to attack by taking in the coordinates of the button and calling board's shoot method.
     * @param e the current button
     */
    public void AttackListener(ActionEvent e) {
        Board armada;
        if (turnCount % 2 == 1) { // sets board to current player
            armada = armada2;
        } else {
            armada = armada1;
        }

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (e.getSource() == shootingGrid[y][x]) {
                    if (armada.shoot(x,y)) { // sets finishTurn to true if target has not been hit yet
                        finishTurn = true;
                    }
                    if (armada.checkSpace(x,y)) { // displays current target as red if it hit a ship
                        shootingGrid[y][x].setBackground(Color.RED);
                        Sound boom = new Sound("boom", false, true);

                    } else { // displays targeted space as white if it is a miss
                        shootingGrid[y][x].setBackground(Color.WHITE);
                        Sound splash = new Sound("splash", false, true);

                    }
                }
            }
        }
    }

    /**
     * This method displays what ships the current player has destroyed
     */
    private void setNotice() {
        Board armada;
        String targetList = "TARGETS DESTROYED ";

        if (turnCount % 2 == 1) { // sets board to current player
            armada = armada2;
        } else {
            armada = armada1;
        }

        if (!armada.isCarrierExists()) { // displays A if aircraftCarrier is sunk
            targetList += " A ";
        }

        if (!armada.isBattleshipExists()) { // displays B if the Battleship is sunk
            targetList += " B ";
        }

        if (!armada.isCruiserExists()) { // displays C if the Cruiser is sunk
            targetList += " C ";
        }

        if (!armada.isSubExists()) { // displays S if the sub is sunk
            targetList += " S ";
        }
        if (!armada.isPatrolBoatExists()) { // displays P if the patrolboat is sunk
            targetList += " P ";
        }
        noticeLabel.setText(targetList); // displays targetList

    }
}