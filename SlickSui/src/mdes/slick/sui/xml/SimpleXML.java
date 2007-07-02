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
public class SimpleXML extends BasicGame {
    
    /** Entry point to the application. */
    public static void main(String[] args) {
        try {
            AppGameContainer container = new AppGameContainer(new SimpleXML());
            container.setDisplayMode(640, 480, false);
            container.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private SuiDisplay display = null;
    
    private int clicks = 0;
    private SuiButton button;
        
    private SuiActionListener listener = new SuiActionListener() {
        public void actionPerformed(SuiActionEvent e) {
            if (e.getSource() == button) {
                clicks++;
                initButton(button);
            }
        }
    };
    
    public SimpleXML() {
        super("SimpleXML");
    }
    
    public SuiActionListener getClickListener() {
        return listener;
    }
    
    public void initButton(SuiButton button) {
        if (this.button!=button)
            this.button = button;
        this.button.setText("Clicks: "+clicks);
    }
    
    public void init(GameContainer container) throws SlickException {
        display = Sui.init(container);
        display.setClipEnabled(false);
        
        try { 
            InputStream in = ResourceLoader.getResourceAsStream("res/simplegui.xml");
            SuiContainer cont = new SuiXML(this).read(in); 
            if (cont!=null) {
                display.add(cont);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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
