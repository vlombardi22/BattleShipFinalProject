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
    private String name1;
    private String name2;
    private JButton[][] positionGrid;
    private JButton[][] shootingGrid;
    private JPanel southPanel; // Panel to contain key and continue button
    Font font; 
    private JButton continueButton = new JButton("CONTINUE");
    private int turnCount = 1;
    private boolean finishTurn = false;
    private JLabel playerLabel;
    private JLabel noticeLabel;

     /**
     * GameBoard constructor
     */
    public GameBoard(Player player1, Player player2){
        // Set up main JFrame and main JPanel
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        
        armada1 = player1.getArmada();
        armada2 = player2.getArmada();
        
        name1 = player1.getName();
        name2 = player2.getName();
        
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
        
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.setBackground(Color.BLACK);
        
        // Creates label for player's board
        playerLabel = new JLabel(name2 + "'s BOARD");
        playerLabel.setBorder(BorderFactory.createEmptyBorder(30,100,0,100));
        playerLabel.setFont(font);
        playerLabel.setForeground(Color.RED);
        labelPanel.add(playerLabel, BorderLayout.LINE_END);
        
        // Creates label for opponent's board
        JLabel opponentLabel = new JLabel("YOUR BOARD");
        opponentLabel.setBorder(BorderFactory.createEmptyBorder(30,100,0,100));
        opponentLabel.setFont(font);        
        opponentLabel.setForeground(Color.RED);
        labelPanel.add(opponentLabel, BorderLayout.LINE_START);
        
        add(labelPanel, BorderLayout.NORTH);
    }
    
    /**
     * Creates a grid 
     * @param bound String - determines which side the grid will go on
     */
    private void createBoard(String bound){
        // Set up encompassing panels, outerPanel is BorderLayout by default
        JPanel outerPanel = new JPanel();
        JPanel innerPanel = new JPanel();
        JButton[][] tempGrid;
        if(bound.equals(BorderLayout.LINE_START)){
            positionGrid = new JButton[10][10];
            tempGrid = positionGrid;
        } else {
            shootingGrid = new JButton[10][10];
            tempGrid = shootingGrid;
        }

        innerPanel.setLayout(new GridBagLayout());

        // Create button grid
        for(int x = 0; x < 10;x++){
            for(int y = 0; y < 10; y++){
                GridBagConstraints constraints = new GridBagConstraints();
                tempGrid[y][x] = new JButton("("+x+","+y+")");
                tempGrid[y][x].setBackground(Color.BLUE);
                constraints.fill = GridBagConstraints.BOTH;
                constraints.gridx = x;
                constraints.gridy = y;
                constraints.weightx = 1;
                constraints.weighty = 1;
                if(!bound.equals(BorderLayout.LINE_START)){
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
        if(bound.equals(BorderLayout.LINE_START)) {
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

    @Override
    public void actionPerformed(ActionEvent e) {        
        assert(turnCount <= 200);
        String name;
        if(turnCount%2==1) {
            name = name1;
        } else {
            name = name2;
        }

        if(e.getSource() == continueButton && finishTurn) {// Continue button action must be changed later
            if(armada1.gameOver() || armada2.gameOver()){

            
                setVisible(false);
                GameManager.getFrame().add(new CongratsScreen(name)); 
            } else {
            
                turnCount++;
                clearPositionGrid();
                clearShootingGrid();
                colorPositionGrid();
                colorShootingGrid();
                finishTurn = false;
                if(turnCount%2==1) {
                    playerLabel.setText(name2 + "'s BOARD");
                } else {
                    playerLabel.setText(name1 + "'s BOARD");
                }
                setNotice();

                // Go to switch screen
                setVisible(false);
                GameManager.getSwitchScreen().setVisible(true);     
            }
        }else if(!finishTurn) {
            AttackListener(e);
        }
    }

    private void clearShootingGrid(){
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                shootingGrid[y][x].setBackground(Color.BLUE);
            }
        }
    }


    private void clearPositionGrid(){
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                positionGrid[y][x].setBackground(Color.BLUE);
            }
        }
    }


    private void colorPositionGrid(){
        Board armada;
        if(turnCount % 2 == 1){
            armada = armada1;

        }else{
            armada = armada2;
        }
        armada.displayPlayerBoard();
        armada.displayEnemyBoard();

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if(armada.checkSpace(x,y)){
                    positionGrid[y][x].setBackground(Color.RED);
                } else if (armada.checkShipSpace(y,x)) {
                    positionGrid[y][x].setBackground(Color.LIGHT_GRAY);
                }
            }
        }
    }

    private void colorShootingGrid(){

        Board armada;
        if(turnCount % 2 == 1){
            armada = armada2;
        }else{
            armada = armada1;
        }

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if(armada.checkSpace(x,y)){
                    shootingGrid[y][x].setBackground(Color.RED);
                } else if (armada.checkHit(x,y)) {
                    shootingGrid[y][x].setBackground(Color.WHITE);
                }
            }
        }
    }

    public void AttackListener(ActionEvent e) {
        Board armada;
        if(turnCount % 2 == 1){
            armada = armada2;
        }else{
            armada = armada1;
        }

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if(e.getSource() == shootingGrid[y][x]){
                    if(armada.shoot(x,y)){
                        finishTurn = true;
                    }
                    if(armada.checkSpace(x,y)){
                        shootingGrid[y][x].setBackground(Color.RED);
                    }else{
                        shootingGrid[y][x].setBackground(Color.WHITE);
                    }
                }
            }
        }
    }

    private void setNotice(){
        Board armada;
        String targetList = "targets destroyed ";

        if(turnCount % 2 == 1){
            armada = armada2;
        }else{
            armada = armada1;
        }

        if(armada.isCarrierDestroyed()){
            targetList += " A ";
        }

        if(armada.isBattleShipDestroyed()){
            targetList += " B ";
        }

        if(armada.isCruiserDestroyed()){
            targetList += " C ";
        }

        if(armada.isSubmarineDestroyed()){
            targetList += " S ";
        }
        if (armada.isPatrolBoatDestroyed()){
            targetList += " P ";
        }
        noticeLabel.setText(targetList);

    }
}
