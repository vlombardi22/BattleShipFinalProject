/**
 * This class displays the rules screen
 *
 * CPSC 224-01, Spring 2018
 * Final Project
 * @author Vincent Lombardi, Luke Hartman, Mario Malodonado
 * @version V1.0 5/3/2018
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Rules extends JPanel implements ActionListener{
    //Containers and components
    private JPanel buttonPanel; // Panel to contain exit and return buttons    
    private JButton returnButton = new JButton("RETURN"); // Return button
    private JButton exitButton = new JButton("EXIT"); // Exit button
    
    private Font font; // Object to hold font

    /**
     * Constructor responsible for displaying UI
     */
    public Rules(){
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        //Set up bottom button panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBackground(Color.BLACK);

        //Read from font file
        FontSetup myFont = new FontSetup();
        font = myFont.readFontFile();
        
        addComponents();
    }

    /**
     * Adds title, text, and buttons
     */
    private void addComponents(){
        addTitle();
        addRulesText();
        font = font.deriveFont(30f);
        addReturnButton();
        addExitButton();
        add(buttonPanel,BorderLayout.SOUTH);
    }

    /**
     * Adds title to the top of the screen
     */
    private void addTitle(){
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBackground(Color.BLACK);
        font = font.deriveFont(40f);

        JLabel titleLabel = new JLabel("RULES");
        titleLabel.setFont(font);
        titleLabel.setForeground(Color.RED);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        titlePanel.add(titleLabel,BorderLayout.CENTER);
        add(titlePanel,BorderLayout.NORTH);
    }

    /**
     * Adds the rules text to the center of the screen
     */
    private void addRulesText(){
        Box list = Box.createVerticalBox();
        String strLine;
        BufferedReader in;

        try{
            // Read from .txt files
            in = new BufferedReader(new FileReader("res/Rules.txt"));
            font = font.deriveFont(26f);
            JLabel[] rulesLabel = new JLabel[19];
            int lineCount = 0;

            while((strLine = in.readLine()) != null){
                rulesLabel[lineCount] = new JLabel();
                rulesLabel[lineCount].setFont(font);
                rulesLabel[lineCount].setForeground(Color.RED);
                rulesLabel[lineCount].setText(strLine);
                list.add(rulesLabel[lineCount]);

                lineCount++;
            }
            in.close();
        }catch(FileNotFoundException e){
            System.out.println("File Not Found");
        }catch(IOException e){
            System.out.println("error");
        }

        add(list,BorderLayout.LINE_START);
    }

    /**
     * Displays the return button
     */
    private void addReturnButton(){
        returnButton.addActionListener(this);
        returnButton.setPreferredSize(new Dimension(200,75));
        returnButton.setFont(font);
        returnButton.setBackground(Color.BLACK);
        returnButton.setBorder(BorderFactory.createLineBorder(Color.RED));
        returnButton.setForeground(Color.RED);

        buttonPanel.add(returnButton,BorderLayout.LINE_END);
    }

    /**
     * Displays the exit button
     */
    private void addExitButton(){
        exitButton.addActionListener(this);
        exitButton.setPreferredSize(new Dimension(200,75));
        exitButton.setFont(font);
        exitButton.setBackground(Color.BLACK);
        exitButton.setBorder(BorderFactory.createLineBorder(Color.RED));
        exitButton.setForeground(Color.RED);
        exitButton.setFocusPainted(false);

        buttonPanel.add(exitButton,BorderLayout.LINE_START);
    }

    /**
     * Handles each button's actions
     * 
     * @param e ActionEvent
     */
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == returnButton){
            //Set current panel to not visible
            //Set start menu panel to visible

            setVisible(false);
            StartClass.getStartMenu().setVisible(true);
        }else if(e.getSource() == exitButton){
            System.exit(0);
        }
    }
}