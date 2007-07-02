/*
 * EasySkinTest.java
 *
 * Created on June 24, 2007, 4:13 PM
 */

package mdes.slick.sui.test;

import mdes.slick.sui.*;
import mdes.slick.sui.border.*;
import mdes.slick.sui.skin.*;
import org.newdawn.slick.fills.*;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import org.lwjgl.opengl.*;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.opengl.SlickCallable;
import org.newdawn.slick.util.*;

/**
 *
 * @author davedes
 */
public class EasySkinTest extends BasicGame {
    
    /** Entry point to the application. */
    public static void main(String[] args) {
        try {
            AppGameContainer container = new AppGameContainer(new EasySkinTest());
            container.setDisplayMode(640, 480, false);
            container.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public EasySkinTest() {
        super("EasySkinTest");
    }
    
    private SuiDisplay display = null;
    
    public void init(GameContainer container) throws SlickException {
        //set up skin
        EasySkin mySkin = new EasySkin();
        try {
            Sui.setSkin(mySkin);
            Sui.setTheme(new Sui.DefaultTheme());
        } catch (SlickException e) {
            Log.error("Error occurred while setting custom skin. Using default.", e);
        }
        
        //set up the display
        display = Sui.init(container);
        display.setClipEnabled(false);
        
        SuiWindow win = new SuiWindow("Test Skin");
        win.setSize(200,200);
        win.setLocation(300,200);
        win.setResizable(false);
        win.getCloseButton().setVisible(false);
        display.add(win);
        
        SuiButton button = new SuiButton("Test Button");
        button.pack();
        button.setLocation(10, 10);
        win.add(button);
        
        SuiCheckBox box = new SuiCheckBox("Check");
        box.pack();
        box.setLocation(10, 40);
        box.setSelected(true);
        win.add(box);
        
        
        win.setVisible(true);
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
        }
    }
}
