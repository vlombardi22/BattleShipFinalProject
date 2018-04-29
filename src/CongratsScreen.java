import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class CongratsScreen extends JPanel implements ActionListener{
    //Containers and components
    private JPanel buttonPanel;
    private JButton menuButton = new JButton("MENU");
    private JButton exitButton = new JButton("EXIT");
    Font font;
    String winnerName;
    
    public CongratsScreen(String name){ 
        winnerName = name;
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        
        //Set up bottom button panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBackground(Color.BLACK);
        
         // Read from font file
        FontSetup myFont = new FontSetup();
        font = myFont.readFontFile();
        
        addComponents();
    }
    
    private void addComponents(){     
        addText();  
        font = font.deriveFont(30f);
        addMenuButton();
        addExitButton();        
        add(buttonPanel,BorderLayout.SOUTH); 
    }
    
    private void addText(){
        //Remove following line when the winner's actual name can be passed into this class
        font = font.deriveFont(125f);
        
        JLabel congrats = new JLabel();
        congrats.setFont(font);
        congrats.setForeground(Color.RED);
        congrats.setText("CONGRATULATIONS " + winnerName + "!");
        congrats.setHorizontalAlignment(JLabel.CENTER);
        
        add(congrats,BorderLayout.CENTER);
    }
    
    private void addMenuButton(){
        menuButton.addActionListener(this);
        menuButton.setPreferredSize(new Dimension(200,75));
        menuButton.setFont(font);
        menuButton.setBackground(Color.BLACK);
        menuButton.setBorder(BorderFactory.createLineBorder(Color.RED));
        menuButton.setForeground(Color.RED);
        
        buttonPanel.add(menuButton,BorderLayout.LINE_END);
    }
    
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
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == menuButton){
            //Set current panel to not visible
            //Set start menu panel to visible
            setVisible(false);
            
            StartClass newGame = new StartClass(); 
            newGame.poopinthepants();
            GameManager.getFrame().dispose();            
        }else if(e.getSource() == exitButton){
            System.exit(0);
        }
    }
}
