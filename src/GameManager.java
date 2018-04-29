/**
 * This class lets players name themselves and play a single round of battleship
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameManager implements ActionListener {
    
    private static JFrame game;
    private JPanel masterPanel;
    private static JPanel gameBoardPanel;
    private static JPanel switchPanel;
    
    private Player player1;
    private Player player2;
    
    private Board armada1;
    private Board armada2;
    private Board armada; // Used to hold either armada1 or armada2
 
    private JButton[][] positionGrid;
    private int xCoordinate1;
    private int yCoordinate1;
    
    private Font font;
    
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private final JButton continueButton = new JButton("CONTINUE");
    
    private Boolean isPlayer1Turn = true;

     /**
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
        masterPanel = new JPanel(new BorderLayout());
        masterPanel.setBackground(Color.BLACK);
        
        // Read from font file
        FontSetup myFont = new FontSetup();
        font = myFont.readFontFile();            
        // Create a board on the left side of the screen
        createBoard();
        // Give instructions to player        
        createMessage();
        // Add continue button
        addContinueButton();
        
        game.add(masterPanel);
        game.setUndecorated(true);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setExtendedState(JFrame.MAXIMIZED_BOTH);
        game.pack();
        game.setVisible(true);
    }
    
     /**
     * Initializes player objects
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
                positionGrid[y][x].addActionListener(this::playerDeploymentListener);
                Panel.add(positionGrid[y][x], constraints);
            }
        }
        
        // Add panels to each other
        Panel.setPreferredSize(new Dimension(800,800));
        Panel.setBackground(Color.BLACK);
        boardPanel.add(Panel,BorderLayout.NORTH);
        boardPanel.setBorder(BorderFactory.createEmptyBorder(120,100,100,100)); 
        boardPanel.setBackground(Color.BLACK);        
        masterPanel.add(boardPanel,BorderLayout.LINE_START);
    }
    
    /**
     * Creates the message on the right side of the screen
     */
    public void createMessage(){
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
        masterPanel.add(boardPanel,BorderLayout.LINE_END);
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
        masterPanel.add(buttonPanel,BorderLayout.SOUTH);
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
                if (e.getSource() == positionGrid[y][x]) {
                    xTemp = x;
                    yTemp = y;
                }
            }
        }
        
        if(isPlayer1Turn)
            armada = armada1;
        else
            armada = armada2;

        if (xCoordinate1 < 0 && yCoordinate1 < 0) {
            xCoordinate1 = xTemp;
            yCoordinate1 = yTemp;
        } else {
            if (!armada.isCarrierExist()) {
                if(armada.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'A')){
                    label1.setText("PLACE YOUR BATTLESHIP");
                    label2.setText("BATTLESHIP: 4 BLOCKS");
                }  
                xCoordinate1 = -1;
                yCoordinate1 = -1;
            } else if (!armada.isBattleshipExists()) {                
                if(armada.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'B')){
                    label1.setText("PLACE YOUR CRUISER");
                    label2.setText("CRUISER: 3 BLOCKS");
                }
                xCoordinate1 = -1;
                yCoordinate1 = -1;
            } else if (!armada.isCruiserExists()) {
                if(armada.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'C')){
                    label1.setText("PLACE YOUR SUBMARINE");
                    label2.setText("SUBMARINE: 3 BLOCKS");
                }
                xCoordinate1 = -1;
                yCoordinate1 = -1;
            } else if (!armada.isSubExists()) {
                if(armada.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'S')){
                    label1.setText("PLACE YOUR PATROL BOAT");
                    label2.setText("PATROL BOAT: 2 BLOCKS");
                }         
                xCoordinate1 = -1;
                yCoordinate1 = -1;
            } else if (!armada.isPatrolBoatExists()) {
                if(armada.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'P')){
                    label1.setText("YOU ARE DONE ");
                    label2.setText("CLICK CONTINUE");
                    label3.setText("");
                }
                xCoordinate1 = -1;
                yCoordinate1 = -1;
            }
            
        }

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (armada.checkShipSpace(x, y)) {
                    positionGrid[x][y].setBackground(Color.gray);
                }
            }
        }
    }

    /**
     * clears player2's board (I just thought that this would be useful)
     */
    private void clearPositionGrid(){
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                positionGrid[x][y].setBackground(Color.BLUE);
            }
        }
    }

    public static JFrame getFrame(){
        return game;
    }
    
    public static JPanel getSwitchScreen(){
        return switchPanel;
    }
    
    public static JPanel getGameBoard(){
        return gameBoardPanel;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == continueButton){
            if(isPlayer1Turn && armada1.isPatrolBoatExists()){
                clearPositionGrid();
                isPlayer1Turn = false;
                
                label1.setText("PLACE YOUR CARRIER");
                label2.setText("CARRIER: 5 BLOCKS");
                label3.setText("SELECT ENDPOINTS OF YOUR SHIP");
            }else if(!isPlayer1Turn && armada2.isPatrolBoatExists()){
                gameBoardPanel = new GameBoard(player1,player2);
                
                masterPanel.setVisible(false);
                game.add(switchPanel);
            }
        }
    }
}