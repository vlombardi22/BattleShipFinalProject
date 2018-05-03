/**
 * This class displays the name prompt screen
 *
 * CPSC 224-01, Spring 2018
 * Final Project
 * @author Vincent Lombardi, Luke Hartman, Mario Malodonado
 * @version V1.0 5/3/2018
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NamePrompt extends JPanel implements ActionListener{
    //Containers and components
    private Box centerBox = Box.createVerticalBox(); // Box to contain labels and text fields
    
    private JPanel southButtonPanel; // Panel to contain exit and continue buttons
    private JButton continueButton = new JButton("CONTINUE"); // Continue buttons
    private JButton exitButton = new JButton("EXIT"); // Exit buttons
    
    private Font font; // Object to hold font
    
    private JTextField field1; // Textfield for player 1 to enter name
    private JTextField field2; // Textfield for player 2 to enter name
    
    private JLabel noNameLabel; // Label to instruct players to enter both names

     /**
     * Constructor responsible for displaying UI
     */
    public NamePrompt(){
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        //Set up bottom button panel
        southButtonPanel = new JPanel(new BorderLayout());
        southButtonPanel.setBackground(Color.BLACK);
        
        noNameLabel = new JLabel();

        // Read from font file
        FontSetup myFont = new FontSetup();
        font = myFont.readFontFile();

        addComponents();
    }

     /**
     * Adds labels, text fields, and buttons
     */
    private void addComponents(){
        JPanel helper = new JPanel();
        helper.setBackground(Color.BLACK);
        
        addNamePrompts();
        font = font.deriveFont(30f);
        addExitButton();
        addContinueButton();

        helper.add(centerBox);
        add(helper,BorderLayout.CENTER);
        add(southButtonPanel,BorderLayout.SOUTH);
    }

     /**
     * Add the label and text fields to the center of the screen
     */
    private void addNamePrompts(){
        centerBox.add(Box.createVerticalStrut(415));
        addPrompt1();
        centerBox.add(Box.createVerticalStrut(50));
        centerBox.add(Box.createGlue());
        addPrompt2();
    }

     /**
     * Add the prompt for player 1
     */
    private void addPrompt1(){
        JPanel promptPanel = new JPanel();
        promptPanel.setLayout(new FlowLayout());
        promptPanel.setBackground(Color.BLACK);

        font = font.deriveFont(30f);
        JLabel prompt1 = new JLabel("PLAYER 1'S NAME: ");
        prompt1.setForeground(Color.RED);
        prompt1.setFont(font);

        font = font.deriveFont(20f);
        promptPanel.add(prompt1);
        field1 = new JTextField(16);
        field1.setFont(font);
        promptPanel.add(field1);

        centerBox.add(promptPanel);
    }

     /**
     * Add the prompt for player2
     */
    private void addPrompt2(){
        JPanel promptPanel = new JPanel();
        promptPanel.setLayout(new FlowLayout());
        promptPanel.setBackground(Color.BLACK);

        font = font.deriveFont(30f);
        JLabel prompt2 = new JLabel("PLAYER 2'S NAME: ");
        prompt2.setForeground(Color.RED);
        prompt2.setFont(font);

        font = font.deriveFont(20f);
        promptPanel.add(prompt2);
        field2 = new JTextField(16);
        field2.setFont(font);
        promptPanel.add(field2);

        centerBox.add(promptPanel);
    }

     /**
     * Display the continue button
     */
    private void addContinueButton(){
        continueButton.addActionListener(this);
        continueButton.setPreferredSize(new Dimension(200,75));
        continueButton.setFont(font);
        continueButton.setBackground(Color.BLACK);
        continueButton.setBorder(BorderFactory.createLineBorder(Color.RED));
        continueButton.setForeground(Color.RED);

        southButtonPanel.add(continueButton,BorderLayout.LINE_END);
    }

     /**
     * Display the exit button
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
        if(e.getSource() == continueButton){
            if(field1.getText().equals("") || field2.getText().equals("")){
                continueButton.setText(" CONTINUE ");
                noNameLabel.setText("BOTH NAMES MUST BE ENTERED TO CONTINUE");
                noNameLabel.setBorder(BorderFactory.createEmptyBorder(0,50,0,0));
                noNameLabel.setFont(font);
                noNameLabel.setForeground(Color.RED);
                southButtonPanel.add(noNameLabel,BorderLayout.CENTER);
            }else{
                //Set current panel to not be visible
                //Replace last line by adding GameManager and by setting it to visible
                GameManager game = new GameManager(field1.getText(), field2.getText());

                StartClass.getFrame().setVisible(false);
                StartClass.getFrame().dispose();
            }
        }else if(e.getSource() == exitButton){
            System.exit(0);
        }
    }
}