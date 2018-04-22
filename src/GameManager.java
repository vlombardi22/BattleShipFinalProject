/**
 * This class lets players name themselves and play a single round of battleship
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
public class GameManager implements ActionListener {
    
    private JFrame game;
    private JPanel masterPanel;
    private Board armada1;
    private Board armada2;
    private JButton[][] player1Grid;
    private JButton[][] player2Grid;
    private JButton[][] positionGrid1;
    private JButton[][] positionGrid2;
    private int xCoordinate1;
    private int yCoordinate1;

     /**
     * @param P1 String - player name
     * @param P2 String - player name
     */
    public GameManager(String P1, String P2){
        // Display to console (Not important to GUI)
        consolePlay(P1,P2);
        
        // Set up main JFrame and main JPanel
        game = new JFrame();
        masterPanel = new JPanel(new BorderLayout());
        masterPanel.setBackground(Color.BLACK);
        
        // Create a board on the left side of the screen
        createBoard();
        
        game.add(masterPanel);
        game.setUndecorated(true);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setExtendedState(JFrame.MAXIMIZED_BOTH);
        game.pack();
        game.setVisible(true);
    }
    
    /**
     * @param P1 String - player name
     * @param P2 String - player name
     */
    private void consolePlay(String P1, String P2){
        String player1 = P1;
        String player2 = P2;
        armada1 = new Board(player1);
        armada2 = new Board(player2);
//        armada1.testConfig1();
        armada2.testConfig2();
        armada1.displayPlayerBoard();
        armada2.displayPlayerBoard();
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
        positionGrid1 = new JButton[10][10];
        Panel.setLayout(new GridBagLayout());

        // Create button grid
        for(int x = 0; x < 10;x++){
            for(int y = 0; y < 10; y++){
                GridBagConstraints constraints = new GridBagConstraints();
                positionGrid1[y][x] = new JButton("("+x+","+y+")");
                positionGrid1[y][x].setBackground(Color.BLUE);
                constraints.fill = GridBagConstraints.BOTH;
                constraints.gridx = x;
                constraints.gridy = y;
                constraints.weightx = 1;
                constraints.weighty = 1;
                positionGrid1[y][x].addActionListener(this::player1DeploymentListener);
                Panel.add(positionGrid1[y][x], constraints);
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

    public void player1AttackListener(ActionEvent e) {
        for(int x = 0; x < 10; x++){
            for(int y = 0; y < 10; y++){
                if(e.getSource() == player1Grid[y][x]){
                    armada2.shoot(x,y);
                    if(armada2.checkSpace(x,y)){
                        player1Grid[y][x].setBackground(Color.RED);
                    }else{
                        player1Grid[y][x].setBackground(Color.WHITE);
                    }
                }
            }
        }
    }

    public void player2AttackListener(ActionEvent e) {
        for(int x = 0; x < 10; x++){
            for(int y = 0; y < 10; y++){
                if(e.getSource() == player2Grid[y][x]){
                    armada1.shoot(x,y);
                    if(armada1.checkSpace(x,y)){
                        player2Grid[y][x].setBackground(Color.RED);
                    }else{
                        player2Grid[y][x].setBackground(Color.WHITE);
                    }
                }
            }
        }
    }

    public void player1DeploymentListener(ActionEvent e) {
        int xTemp = -1;
        int yTemp = -1;
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (e.getSource() == positionGrid1[y][x]) {
                    xTemp = x;
                    yTemp = y;
                }
            }
        }

        if (xCoordinate1 < 0 && yCoordinate1 < 0) {
            xCoordinate1 = xTemp;
            yCoordinate1 = yTemp;
        } else {
            if (!armada1.isBattleshipExists()) {
                armada1.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'B');
                xCoordinate1 = -1;
                yCoordinate1 = -1;
            } else if (!armada1.isCarrierExist()) {
                armada1.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'A');
                xCoordinate1 = -1;
                yCoordinate1 = -1;
            } else if (!armada1.isCruiserExists()) {
                armada1.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'C');
                xCoordinate1 = -1;
                yCoordinate1 = -1;
            } else if (!armada1.isSubExists()) {
                armada1.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'S');
                xCoordinate1 = -1;
                yCoordinate1 = -1;
            } else if (!armada1.isPatrolBoatExists()) {
                armada1.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'P');
                xCoordinate1 = -1;
                yCoordinate1 = -1;
            }
        }

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (armada1.checkShipSpace(x, y)) {
                    positionGrid1[x][y].setBackground(Color.gray);
                }
            }
        }
    }

    public void player2DeploymentListener(ActionEvent e) {
        int xTemp = -1;
        int yTemp = -1;
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (e.getSource() == positionGrid2[y][x]) {
                    xTemp = x;
                    yTemp = y;
                }
            }
        }

        if (xCoordinate1 < 0 && yCoordinate1 < 0) {
            xCoordinate1 = xTemp;
            yCoordinate1 = yTemp;
        } else {
            if (!armada1.isBattleshipExists()) {
                armada2.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'B');
                xCoordinate1 = -1;
                yCoordinate1 = -1;
            } else if (!armada1.isCarrierExist()) {
                armada2.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'A');
                xCoordinate1 = -1;
                yCoordinate1 = -1;
            } else if (!armada1.isCruiserExists()) {
                armada2.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'C');
                xCoordinate1 = -1;
                yCoordinate1 = -1;
            } else if (!armada1.isSubExists()) {
                armada2.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'S');
                xCoordinate1 = -1;
                yCoordinate1 = -1;
            } else if (!armada1.isPatrolBoatExists()) {
                armada2.placeWarship(xCoordinate1, yCoordinate1 , xTemp, yTemp, 'P');
                xCoordinate1 = -1;
                yCoordinate1 = -1;
            }
        }

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (armada2.checkShipSpace(x, y)) {
                    positionGrid2[x][y].setBackground(Color.gray);
                }
            }
        }
    }


    /**
     * clearsPlayer1's board (I just thought that this might be useful
     */
    private void clearBoard1(){
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                positionGrid1[x][y].setBackground(Color.BLUE);
            }
        }
    }

    /**
     * clears player2's board (I just thought that this would be useful)
     */
    private void clearBoard2(){
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                positionGrid2[x][y].setBackground(Color.BLUE);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
