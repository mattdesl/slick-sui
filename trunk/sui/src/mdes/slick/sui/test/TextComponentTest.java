/*
 * TextComponentTest.java
 *
 * Created on December 13, 2007, 4:28 PM
 */

package mdes.slick.sui.test;

import mdes.slick.sui.Sui;
import mdes.slick.sui.SuiButton;
import mdes.slick.sui.SuiCheckBox;
import mdes.slick.sui.SuiDisplay;
import mdes.slick.sui.SuiLabel;
import mdes.slick.sui.SuiTextArea;
import mdes.slick.sui.SuiTextField;
import mdes.slick.sui.event.SuiActionEvent;
import mdes.slick.sui.event.SuiActionListener;
import mdes.slick.sui.event.SuiChangeEvent;
import mdes.slick.sui.event.SuiChangeListener;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


/**
 *
 * @author Matt
 */
public class TextComponentTest extends BasicGame {
    
    public static void main(String[] args) throws Exception {
        AppGameContainer app = new AppGameContainer(new TextComponentTest());
        app.setDisplayMode(800,600,false);
        app.start();
    }
    
    /** Creates a new instance of TextComponentTest */
    public TextComponentTest() {
        super("TextComponentTest");
    }
    
    private SuiDisplay display;
    
    public void init(GameContainer container) throws SlickException {
        container.getInput().enableKeyRepeat(400, 50);
        container.getGraphics().setBackground(Sui.getTheme().getBackground());
        
        display = new SuiDisplay(container);
               
        final SuiCheckBox passBox = new SuiCheckBox("Password?");
        passBox.pack();
        passBox.setRequestFocusEnabled(false);
        passBox.setLocation(200, 150);
        display.add(passBox);
        
        final char DEFAULT_MASK = '*';
                
        final SuiTextField field = new SuiTextField("Test", 10);
        field.setLocation(passBox.getX(), passBox.getY()+passBox.getHeight()+10); 
        field.setMaskCharacter(DEFAULT_MASK);
        display.add(field);
        
        final SuiButton btn = new SuiButton("OK");
        btn.pack();
        btn.setLocation(field.getX(), field.getY()+field.getHeight()+10);
        display.add(btn);
        
        final SuiLabel label = new SuiLabel();
        label.setLocation(btn.getX(), btn.getY()+btn.getHeight()+20);
        display.add(label);
           
        //when check box is changed we change the masking on the field
        passBox.addActionListener(new SuiActionListener() {
            public void actionPerformed(SuiActionEvent e) {                
                field.setMaskEnabled(passBox.isSelected());
            }
        });
        
        //when ENTER key or OK button is pressed, we show the input
        SuiActionListener textAction = new SuiActionListener() {
           public void actionPerformed(SuiActionEvent ev) {
               String text = field.getText();
               if (text.length()==0)
                   return;
               label.setText("You entered " + text);
               label.pack();          
           } 
        };
        
        //clear the "You entered" label when we change the text
        field.addChangeListener(new SuiChangeListener() {
            public void stateChanged(SuiChangeEvent e) {
                label.setText(null);
            }
        });
       
        //add text listener
        field.addActionListener(textAction);
        btn.addActionListener(textAction);
       
        //start off with focused field
        field.grabFocus();
        
        String longStr = "This is a test text area set to a limit of 350 characters.";
        SuiTextArea area = new SuiTextArea(longStr, 30, 8);
        area.setMaxChars(350);
        area.setLocation(label.getX(), label.getY()+80);
        display.add(area);
    }
    
    public void update(GameContainer container, int delta) throws SlickException {
        display.update(container, delta);
        
        if (container.getInput().isKeyPressed(Input.KEY_ESCAPE))
            container.exit();
    }
    
    public void render(GameContainer container, Graphics g) throws SlickException {
        display.render(container, g);
    }
}
