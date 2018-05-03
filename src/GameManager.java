/**
 * This class lets players place their ships. Players do this by clicking on the
 * tiles representing the endpoints of their ships
 *
 * CPSC 224-01, Spring 2018
 * Final Project
 * @author Vincent Lombardi
 * @version V1.0 5/3/20
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameManager implements ActionListener {
    
    public static Sound redAlert;

    private static JFrame game; // Main game JFrame
    private JPanel placingPanel; // Panel where players place their fleets
    private static JPanel gameBoardPanel; // Panel where players attack each other
    private static JPanel switchPanel; // Panel where players are told to switch
    
    private Player player1; // player object for player 1
    private Player player2; // player object for player 2
    
    private Board armada1;  // Board object for player 1
    private Board armada2;  // Board object for player 2
    private Board armada; // Used to hold either armada1 or armada2
 
    private JButton[][] positionGrid; // JButton grid for ship placement
    private int xCoordinate1; // temporary x coordinate
    private int yCoordinate1; // temporary y coordinate
    
    private Font font; // Object to hold font
    
    // Labels for ship placement guidance
    private JLabel label1; 
    private JLabel label2;
    private JLabel label3;
    private final JButton continueButton = new JButton("CONTINUE"); // continue button
    
    private Boolean isPlayer1Turn = true; // Boolean that is true when it is player 1's turn

    /**
     * constructor for gameMangager which sets up a new JFrame
     * 
     * @param P1 String - player name
     * @param P2 String - player name
     */
    public GameManager(String P1, String P2){
        switchPanel = new SwitchScreen();
        
        P1 = P1.toUpperCase();
        P2 = P2.toUpperCase();
        // Initialize player objects     
        playerSetup(P1,P2);
        
        // Set up main JFrame and main JPanel
        game = new JFrame();
        placingPanel = new JPanel(new BorderLayout());
        placingPanel.setBackground(Color.BLACK);
        
        // Read from font file
        FontSetup myFont = new FontSetup();
        font = myFont.readFontFile();            
        // Create a board on the left side of the screen
        createBoard();
        // Give instructions to player        
        createMessage();
        // Add continue button
        addContinueButton();
        
        game.add(placingPanel);
        game.setUndecorated(true);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setExtendedState(JFrame.MAXIMIZED_BOTH);
        game.pack();
        game.setVisible(true);
    }
    
     /**
     * Initializes player objects
     * 
     * @param P1 String
     * @param P2 String 
     */
    private void playerSetup(String P1, String P2){
        player1 = new Player();
        player1.setName(P1);
        armada1 = player1.getArmada();
        
        player2 = new Player();
        player2.setName(P2);
        armada2 = player2.getArmada();
        
        xCoordinate1 = -1;
        yCoordinate1 = -1;
    }
    
    /**
     * Creates a grid on the left side of the screen
     */
    private void createBoard(){
        // Set up encompassing panels
        JPanel boardPanel = new JPanel(new BorderLayout());
        JPanel Panel = new JPanel();
        positionGrid = new JButton[10][10];
        Panel.setLayout(new GridBagLayout());


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
                positionGrid[y][x].addActionListener(this::playerDeploymentListener); // sets action listener
                Panel.add(positionGrid[y][x], constraints);
            }
        }
        
        // Add panels to each other
        Panel.setPreferredSize(new Dimension(800,800));
        Panel.setBackground(Color.BLACK);
        boardPanel.add(Panel,BorderLayout.NORTH);
        boardPanel.setBorder(BorderFactory.createEmptyBorder(120,100,100,100)); 
        boardPanel.setBackground(Color.BLACK);        
        placingPanel.add(boardPanel,BorderLayout.LINE_START);
    }
    
    /**
     * Creates the message on the right side of the screen
     */
    private void createMessage(){
        JPanel boardPanel = new JPanel(new BorderLayout());
        JPanel Panel = new JPanel();
        
        font = font.deriveFont(50f);
        label1 = new JLabel("ADD YOUR CARRIER");
        label2 = new JLabel("CARRIER: 5 BLOCKS");
        label3 = new JLabel("SELECT ENDPOINTS OF YOUR SHIP");
        
        label1.setFont(font);
        label1.setForeground(Color.RED);
        label2.setFont(font);
        label2.setForeground(Color.RED);
        font = font.deriveFont(30f);
        label3.setFont(font);
        label3.setForeground(Color.RED);
        
        Panel.add(label1);
        Panel.add(label2,BorderLayout.CENTER);
        Panel.add(label3,BorderLayout.SOUTH);
        
        Panel.setPreferredSize(new Dimension(800,800));
        Panel.setBackground(Color.BLACK);
        Panel.setBorder(BorderFactory.createLineBorder(Color.RED));
        boardPanel.add(Panel,BorderLayout.NORTH);
        boardPanel.setBorder(BorderFactory.createEmptyBorder(120,100,100,100)); 
        boardPanel.setBackground(Color.BLACK);        
        placingPanel.add(boardPanel,BorderLayout.LINE_END);
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
        
        buttonPanel.add(continueButton,BorderLayout.LINE_END);
        placingPanel.add(buttonPanel,BorderLayout.SOUTH);
    }

    /**
     * Handles the placement of ships on the board
     * @ param e ActionEvent
     */
    public void playerDeploymentListener(ActionEvent e) {
        int xTemp = -1;
        int yTemp = -1;
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (e.getSource() == positionGrid[y][x]) { // gets x and y coordinates of current button
                    xTemp = x;
                    yTemp = y;
                }
            }
        }
        
        if (isPlayer1Turn) { // sets board to current player
            armada = armada1;
        } else {
            armada = armada2;
        }
        // if this is the first set of xy coordinates they are stored in xCoordinate1 and yCoordinate1
        if (xCoordinate1 < 0 && yCoordinate1 < 0) {
            xCoordinate1 = xTemp;
            yCoordinate1 = yTemp;
        } else {
            if (!armada.isCarrierExists()) { // if not already done places the carrier
                if (armada.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'A')) {
                    label1.setText("PLACE YOUR BATTLESHIP");
                    label2.setText("BATTLESHIP: 4 BLOCKS");
                }  
                xCoordinate1 = -1; // reset first xy coordinate pair
                yCoordinate1 = -1;
            } else if (!armada.isBattleshipExists()) { // if not already done places the battleship
                if(armada.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'B')){
                    label1.setText("PLACE YOUR CRUISER");
                    label2.setText("CRUISER: 3 BLOCKS");
                }
                xCoordinate1 = -1; // reset first xy coordinate pair
                yCoordinate1 = -1;
            } else if (!armada.isCruiserExists()) { // if not already done places the cruiser
                if(armada.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'C')){
                    label1.setText("PLACE YOUR SUBMARINE");
                    label2.setText("SUBMARINE: 3 BLOCKS");
                }
                xCoordinate1 = -1; // reset first xy coordinate pair
                yCoordinate1 = -1;
            } else if (!armada.isSubExists()) { // if not already done places the sub
                if(armada.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'S')){
                    label1.setText("PLACE YOUR PATROL BOAT");
                    label2.setText("PATROL BOAT: 2 BLOCKS");
                }         
                xCoordinate1 = -1; // reset first xy coordinate pair
                yCoordinate1 = -1;
            } else if (!armada.isPatrolBoatExists()) { // if not already done places the patrolBoat
                if(armada.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'P')){
                    label1.setText("YOU ARE DONE ");
                    label2.setText("CLICK CONTINUE");
                    label3.setText("");
                }
                xCoordinate1 = -1; // reset first xy coordinate pair
                yCoordinate1 = -1;
            }
            
        }

        for (int x = 0; x < 10; x++) { // changes tiles to gray if they are part of a ship
            for (int y = 0; y < 10; y++) {
                if (armada.checkShipSpace(x, y)) {
                    positionGrid[x][y].setBackground(Color.gray);
                }
            }
        }
    }

    /**
     * clears position board so that all spaces are blue
     */
    private void clearPositionGrid(){
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                positionGrid[x][y].setBackground(Color.BLUE);
            }
        }
    }

    /**
     * game getter method
     * @return JFRame
     */
    public static JFrame getFrame(){
        return game;
    }

    /**
     * switchscreens getter
     * @return switchPanel
     */
    public static JPanel getSwitchScreen(){
        return switchPanel;
    }

    /**
     * gameBoardPanels getter
     * @return gameBoardPanel
     */
    public static JPanel getGameBoard(){
        return gameBoardPanel;
    }

    /**
     * Action Listener for the board and continue
     * @param e the button that was pressed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == continueButton) { // if the button was continue switch players
            if (isPlayer1Turn && armada1.isPatrolBoatExists()) {
                clearPositionGrid();
                isPlayer1Turn = false;
                
                label1.setText("PLACE YOUR CARRIER");
                label2.setText("CARRIER: 5 BLOCKS");
                label3.setText("SELECT ENDPOINTS OF YOUR SHIP");
            } else if(!isPlayer1Turn && armada2.isPatrolBoatExists()) { // switch to gameBoard
                gameBoardPanel = new GameBoard(player1,player2);
                
                placingPanel.setVisible(false);
                game.add(switchPanel);

                // stop the soviet anthem
                StartClass.music.killSound();

                // start soviet march theme
                redAlert = new Sound("CCRedAlert3Theme", true, true); 
            }
        }
    }
}