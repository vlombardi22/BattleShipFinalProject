import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;


public class NamePrompt extends JPanel implements ActionListener{
    //Containers and components
    private Box centerBox = Box.createVerticalBox();
    private JPanel southButtonPanel;
    private JButton continueButton = new JButton("CONTINUE");
    private JButton exitButton = new JButton("EXIT");
    Font font;
    private JTextField field1;
    private JTextField field2;

    public NamePrompt(){
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        //Set up bottom button panel
        southButtonPanel = new JPanel();
        southButtonPanel.setLayout(new BorderLayout());
        southButtonPanel.setBackground(Color.BLACK);

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

        addComponents();
    }

    private void addComponents(){
        font = font.deriveFont(30f);

        JPanel helper = new JPanel();
        helper.setBackground(Color.BLACK);

        addNamePrompts();
        addExitButton();
        addContinueButton();

        helper.add(centerBox);
        add(helper,BorderLayout.CENTER);
        add(southButtonPanel,BorderLayout.SOUTH);
    }

    private void addNamePrompts(){
        centerBox.add(Box.createVerticalStrut(415));
        addPrompt1();
        centerBox.add(Box.createVerticalStrut(50));
        centerBox.add(Box.createGlue());
        addPrompt2();
    }

    private void addPrompt1(){
        JPanel promptPanel = new JPanel();
        promptPanel.setLayout(new FlowLayout());
        promptPanel.setBackground(Color.BLACK);

        JLabel prompt1 = new JLabel("PLAYER 1'S NAME: ");
        prompt1.setForeground(Color.RED);
        prompt1.setFont(font);

        promptPanel.add(prompt1);
        field1 = new JTextField(16);
        promptPanel.add(field1);

        centerBox.add(promptPanel);
    }

    private void addPrompt2(){
        JPanel promptPanel = new JPanel();
        promptPanel.setLayout(new FlowLayout());
        promptPanel.setBackground(Color.BLACK);

        JLabel prompt2 = new JLabel("PLAYER 2'S NAME: ");
        prompt2.setForeground(Color.RED);
        prompt2.setFont(font);

        promptPanel.add(prompt2);
        field2 = new JTextField(16);
        promptPanel.add(field2);

        centerBox.add(promptPanel);
    }

    private void addContinueButton(){
        continueButton.addActionListener(this);
        continueButton.setPreferredSize(new Dimension(200,75));
        continueButton.setFont(font);
        continueButton.setBackground(Color.BLACK);
        continueButton.setBorder(BorderFactory.createLineBorder(Color.RED));
        continueButton.setForeground(Color.RED);

        southButtonPanel.add(continueButton,BorderLayout.LINE_END);
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
        if(e.getSource() == continueButton){
            //Set current panel to not be visible
            //Replace last line by adding GameManager and by setting it to visible
            GameManager game = new GameManager(field1.getText(), field2.getText());


            BattleShip.frame.setVisible(false);
            // BattleShip.frame.add(new StartMenu());
        }else if(e.getSource() == exitButton){
            System.exit(0);
        }
    }
}

