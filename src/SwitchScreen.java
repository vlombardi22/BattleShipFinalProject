import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SwitchScreen extends JPanel implements ActionListener{
    //Containers and components
    private JPanel buttonPanel;
    private JButton continueButton = new JButton("CONTINUE");
    private JButton exitButton = new JButton("EXIT");
    private Font font;
    
    public SwitchScreen(){ 
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
        addContinueButton();
        addExitButton();
        add(buttonPanel,BorderLayout.SOUTH); 
    }
    
    private void addText(){
        //Remove following line when the winner's actual name can be passed into this class
        font = font.deriveFont(125f);
        
        JLabel congrats = new JLabel();
        congrats.setFont(font);
        congrats.setForeground(Color.RED);
        congrats.setText("SWITCH PLAYER");
        congrats.setHorizontalAlignment(JLabel.CENTER);
        
        add(congrats,BorderLayout.CENTER);
    }
    
    private void addContinueButton(){
        continueButton.addActionListener(this);
        continueButton.setPreferredSize(new Dimension(200,75));
        continueButton.setFont(font);
        continueButton.setBackground(Color.BLACK);
        continueButton.setBorder(BorderFactory.createLineBorder(Color.RED));
        continueButton.setForeground(Color.RED);
        
        buttonPanel.add(continueButton,BorderLayout.LINE_END);
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
        if(e.getSource() == continueButton){
            //Set current panel to not visible
            //Set game screen to visible
            setVisible(false);
            GameManager.getFrame().add(GameManager.getGameBoard());
            GameManager.getGameBoard().setVisible(true);
        }else if(e.getSource() == exitButton){
            System.exit(0);
        }
    }
}