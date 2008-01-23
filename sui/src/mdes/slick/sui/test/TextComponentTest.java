/*
 * TextComponentTest.java
 *
 * Created on December 13, 2007, 4:28 PM
 */

package mdes.slick.sui.test;

import mdes.slick.sui.Sui;
import mdes.slick.sui.*;
import mdes.slick.sui.CheckBox;
import mdes.slick.sui.Display;
import mdes.slick.sui.Label;
import mdes.slick.sui.ScrollPane;
import mdes.slick.sui.TextArea;
import mdes.slick.sui.TextField;
import mdes.slick.sui.event.ActionEvent;
import mdes.slick.sui.event.ActionListener;
import mdes.slick.sui.event.ChangeEvent;
import mdes.slick.sui.event.ChangeListener;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


/**
 *
 * @author davedes
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
    
    private Display display;
    
    public void init(GameContainer container) throws SlickException {
        container.getInput().enableKeyRepeat(400, 50);
        container.getGraphics().setBackground(Sui.getTheme().getBackground());
        
        display = new Display(container);
               
        final CheckBox passBox = new CheckBox("Password?");
        passBox.pack();
        passBox.setRequestFocusEnabled(false);
        passBox.setLocation(200, 150);
        display.add(passBox);
        
        final char DEFAULT_MASK = '*';
                
        final TextField field = new TextField("Test", 10);
        field.setLocation(passBox.getX(), passBox.getY()+passBox.getHeight()+10); 
        field.setMaskCharacter(DEFAULT_MASK);
        display.add(field);
        
        final Button btn = new Button("OK");
        btn.pack();
        btn.setLocation(field.getX(), field.getY()+field.getHeight()+10);
        display.add(btn);
        
        final Label label = new Label();
        label.setLocation(btn.getX(), btn.getY()+btn.getHeight()+20);
        display.add(label);
           
        //when check box is changed we change the masking on the field
        passBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {                
                field.setMaskEnabled(passBox.isSelected());
            }
        });
        
        //when ENTER key or OK button is pressed, we show the input
        ActionListener textAction = new ActionListener() {
           public void actionPerformed(ActionEvent ev) {
               String text = field.getText();
               if (text.length()==0)
                   return;
               label.setText("You entered " + text);
               label.pack();          
           } 
        };
        
        //clear the "You entered" label when we change the text
        field.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                label.setText(null);
            }
        });
       
        //add text listener
        field.addActionListener(textAction);
        btn.addActionListener(textAction);
       
        //start off with focused field
        field.grabFocus();
        
        String longStr = "Hello, feel free to write some stuff here. Hello, feel free to write some stuff here. Hello, feel free to write some stuff here. Hello, feel free to write some stuff here. Hello, feel free to write some stuff here. Hello, feel free to write some stuff here.";
        TextArea area = new TextArea(longStr, 10, 6);
        //area.setBackground(Color.red);
        //area.setOpaque(true);
        //area.setMaxChars(350);
                
        ScrollPane pane = new ScrollPane(area);
        pane.setLocation(label.getX(), label.getY()+80);
        pane.setSize(area.getWidth(), area.getHeight());
        //pane.setOpaque(true);
        //pane.setBackground(Color.green);
        /*ScrollBar bar = new ScrollBar(ScrollBar.VERTICAL);
        bar.setLocation(100, 100);
        bar.setSize(16, 100);
        bar.getSlider().setThumbSize(.10f);
        bar.setBackground(Color.blue);
        bar.setOpaque(true);*/
        display.add(pane);
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
