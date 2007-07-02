/*
 * TestXML.java
 *
 * Created on June 24, 2007, 8:22 PM
 */

package mdes.slick.sui.xml;

import java.io.InputStream;
import mdes.slick.sui.*;
import mdes.slick.sui.event.*;
import org.newdawn.slick.*;
import org.newdawn.slick.util.*;

/**
 *
 * @author davedes
 */
public class TestXML extends BasicGame {
    
    /** Entry point to the application. */
    public static void main(String[] args) {
        try {
            AppGameContainer container = new AppGameContainer(new TestXML());
            container.setDisplayMode(640, 480, false);
            container.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public TestXML() {
        super("TestXML");
    }
        
    private SuiDisplay display = null;
    public final float VAR_X = 50f;
            
    public void init(GameContainer container) throws SlickException {
        display = Sui.init(container);
        display.setClipEnabled(false);
        
        try { 
            InputStream in = ResourceLoader.getResourceAsStream("res/testgui.xml");
            SuiContainer cont = new SuiXML(this).read(in); 
            if (cont!=null) {
                display.add(cont);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public SuiActionListener getLoadListener() {
        return new SuiActionListener() {
            public void actionPerformed(SuiActionEvent e) {
                System.out.println("Load");
            }
        };
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
