/*
 * SimpleWindowTest.java
 *
 * Created on June 14, 2007, 7:19 PM
 */

package mdes.slick.sui.test;

import org.newdawn.slick.*;
import mdes.slick.sui.*;
import mdes.slick.sui.event.*;
import org.newdawn.slick.gui.GUIContext;

/**
 *  *
 * @author davedes
 */
public class SimpleWindowTest extends BasicGame {
    
    /** Entry point to the application. */
    public static void main(String[] args) {
        try {
            AppGameContainer container = new AppGameContainer(new SimpleWindowTest());
            container.setDisplayMode(640, 480, false);
            container.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public SimpleWindowTest() {
        super("SimpleWindowTest");
    }
    
    private SuiDisplay display = null;
    private SuiWindow w = null;
    
    public void init(GameContainer container) throws SlickException {
        display = Sui.init(container);
        display.setClipEnabled(false); //TODO: test
        
        //TODO: fix top left
        
        w = new SuiWindow("Test");
        w.setSize(200,200);
        w.setLocation(50,190);
        w.setVisible(true);
        display.add(w);
                
        SuiWindow w2 = new SuiWindow("Test 2");
        w2.setSize(90,50);
        w2.setLocation(15,105);
        w2.setVisible(true);
        display.add(w2);
        
        SuiWindow w3 = new SuiWindow("Test 2");
        w3.setSize(90,50);
        w3.setLocation(100,150);
        w3.setVisible(true);
        display.add(w3);
        
        SuiCheckBox box = new SuiCheckBox("Click me.");
        box.pack();
        box.setLocation(50,100);
        display.add(box);
        
        SuiButton b = new SuiButton("Test");
        b.pack();
        b.setLocation(50,50);
        w.add(b);
    }
    
    public void render(GameContainer container, Graphics g) {
        g.setBackground(Color.lightGray);
        display.render(container, g);
    }
    
    public void update(GameContainer container, int delta) {
        display.update(container, delta);
    }
    
    public void keyPressed(int k, char c) {
        if (k==Input.KEY_ESCAPE) {
            System.exit(0);
        } else if (k==Input.KEY_W) {
            w.setVisible(!w.isVisible());
        }
    }
}