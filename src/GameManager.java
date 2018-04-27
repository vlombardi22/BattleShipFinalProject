/**
 * This class lets players name themselves and play a single round of battleship
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

public class GameManager implements ActionListener {
    
    private JFrame game;
    private JPanel masterPanel;
    
    private Player player1;
    private Player player2;
    
    private Board armada1;
    private Board armada2;
    private Board armada;
 
    private JButton[][] positionGrid;
    private int xCoordinate1;
    private int yCoordinate1;
    Font font;
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
    
<<<<<<< HEAD
    private void playerSetup(String P1, String P2){
        player1 = new Player(P1);
        armada1 = player1.armada;
        //armada1.displayEnemyBoard();
        
        player2 = new Player(P2);
        armada2 = player2.armada;
        //armada2.displayEnemyBoard();
        
=======
    /**
     * @param P1 String - player name
     * @param P2 String - player name
     */
    private void consolePlay(String P1, String P2){
        String player1 = P1;
        String player2 = P2;
        armada1 = new Board(player1);
        armada2 = new Board(player2);

        armada2.testConfig2();
        armada1.displayPlayerBoard();
        armada2.displayPlayerBoard();
>>>>>>> aed092cb1f1cfbfef1a22e78a4bbf26a2647103c
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
        label1 = new JLabel("ADD YOUR BATTLESHIP");
        label2 = new JLabel("BATTLESHIP: 4 BLOCKS");
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
<<<<<<< HEAD
            if (!armada.isBattleshipExists()) {
                armada.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'B');
                xCoordinate1 = -1;
                yCoordinate1 = -1;
                
                label1.setText("PLACE YOUR CARRIER");
                label2.setText("CARRIER: 5 BLOCKS");
            } else if (!armada.isCarrierExist()) {
                armada.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'A');
                xCoordinate1 = -1;
                yCoordinate1 = -1;
                
                label1.setText("PLACE YOUR CRUISER");
                label2.setText("CRUISER: 3 BLOCKS");
            } else if (!armada.isCruiserExists()) {
                armada.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'C');
                xCoordinate1 = -1;
                yCoordinate1 = -1;
                
                label1.setText("PLACE YOUR SUBMARINE");
                label2.setText("SUBMARINE: 3 BLOCKS");
            } else if (!armada.isSubExists()) {
                armada.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'S');
                xCoordinate1 = -1;
                yCoordinate1 = -1;
                
                label1.setText("PLACE YOUR PATROL BOAT");
                label2.setText("PATROL BOAT: 2 BLOCKS");
            } else if (!armada.isPatrolBoatExists()) {
                armada.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'P');
=======
            if (!armada2.isBattleshipExists()) {
                armada2.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'B');
                xCoordinate1 = -1;
                yCoordinate1 = -1;
            } else if (!armada2.isCarrierExist()) {
                armada2.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'A');
                xCoordinate1 = -1;
                yCoordinate1 = -1;
            } else if (!armada2.isCruiserExists()) {
                armada2.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'C');
                xCoordinate1 = -1;
                yCoordinate1 = -1;
            } else if (!armada2.isSubExists()) {
                armada2.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'S');
                xCoordinate1 = -1;
                yCoordinate1 = -1;
            } else if (!armada2.isPatrolBoatExists()) {
                armada2.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'P');
>>>>>>> aed092cb1f1cfbfef1a22e78a4bbf26a2647103c
                xCoordinate1 = -1;
                yCoordinate1 = -1;
                
                label1.setText("YOU ARE DONE ");
                label2.setText("CLICK CONTINUE");
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == continueButton){
            if(isPlayer1Turn){
                clearPositionGrid();
                isPlayer1Turn = false;
                
                label1.setText("PLACE YOUR BATTLESHIP");
                label2.setText("BATTLESHIP: 4 BLOCKS");
                label3.setText("");
            }else{
                System.exit(0);
            }
        }
    }
}
