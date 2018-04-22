import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;


public class StartMenu extends JPanel implements ActionListener{
    //Containers and components
    private Box centerButtonBox = Box.createVerticalBox();
    private JPanel southButtonPanel;
    private JButton startButton = new JButton("START GAME");
    private JButton rulesButton = new JButton("VIEW RULES");
    private JButton exitButton = new JButton("EXIT");
    Font font;

    public StartMenu(){
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        //Set up bottom button panel
        southButtonPanel = new JPanel();
        southButtonPanel.setLayout(new BorderLayout());
        southButtonPanel.setBackground(Color.BLACK);

        //Read from font file
        try{
            //File path may need changing
            //Maybe consider making this font global
            InputStream is = new BufferedInputStream(new FileInputStream("res/RobotoMono-Medium.ttf"));
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        }catch(FileNotFoundException e){
            System.out.println("File not found");
        }catch(IOException e){
            System.out.println("Input/Output error");
        }catch(FontFormatException e){
            System.out.println("Font format exception");
        }

        addComponents();
    }

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

    private void addExitButton(){
        exitButton.addActionListener(this);
        exitButton.setPreferredSize(new Dimension(200,75));
        exitButton.setFont(font);
        exitButton.setBackground(Color.BLACK);
        exitButton.setBorder(BorderFactory.createLineBorder(Color.RED));
        exitButton.setForeground(Color.RED);

        southButtonPanel.add(exitButton,BorderLayout.LINE_START);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == startButton){
            //Set current panel to not be visible
            //Add the namePromptPanel from BattleShip.java to the global frame
            //Set namePromptPanel to visible

            setVisible(false);
            BattleShip.frame.add(BattleShip.namePromptPanel);
            BattleShip.namePromptPanel.setVisible(true);
        }else if(e.getSource() == rulesButton){
            //Set current panel to not be visible
            //Add the rulesPanel from BattleShip.java to the global frame
            //Set rulesPanel to visible

            setVisible(false);
            BattleShip.frame.add(BattleShip.rulesPanel);
            BattleShip.rulesPanel.setVisible(true);
        }else if(e.getSource() == exitButton){
            System.exit(0);
        }
    }
}
