/*
 * SuiTextComponentTest.java
 *
 * Created on January 19, 2008, 3:23 PM
 */

package mdes.slick.sui.test;

import mdes.slick.sui.Button;
import mdes.slick.sui.Container;
import mdes.slick.sui.Display;
import mdes.slick.sui.TextField;
import mdes.slick.sui.event.*;
import mdes.slick.sui.layout.RowLayout;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author davedes
 */
public class SuiTextComponentTest extends BasicGame { 
    
    private Container nameInput; 
    private TextField settlmentName; 
    
    public static void main(String[] args) throws Exception { 
        AppGameContainer app = new AppGameContainer(new SuiTextComponentTest()); 
        app.setDisplayMode(800,600,false); 
        app.start(); 
    } 
    
    /** Creates a new instance of TextComponentTest */ 
    public SuiTextComponentTest() { 
        super("TextComponentTest"); 
    } 
    
    private Display display; 
    
    public void init(GameContainer container) throws SlickException { 
        display = new Display(container); 

        nameInput = new Container(); 
        RowLayout layout = new RowLayout(true); 
        layout.setHorizontalAlignment(RowLayout.CENTER); 
        layout.setVerticalAlignment(RowLayout.CENTER); 
        nameInput.setBounds(250, 300, 204, 34); 
        nameInput.setLayout(layout); 
        nameInput.setOpaque(true); 
        nameInput.setVisible(true); 

        settlmentName = new TextField("Enter settlement name"); 
        settlmentName.setWidth(160);
        settlmentName.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int k = e.getKeyCode();
                System.out.println("key "+e.getKeyChar());
            }
        });
        
        Button okBtn = new Button("OK"); 
        okBtn.pack(); 
        
        settlmentName.setHeight(okBtn.getHeight()); 
        nameInput.add(settlmentName); 
        nameInput.add(okBtn); 
        
        display.add(nameInput); 
//       display.add(settlmentName); 
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