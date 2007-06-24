/*
 * SimpleSuiTest.java
 *
 * Created on May 31, 2007, 7:18 PM
 */

package mdes.slick.sui.test;

import org.newdawn.slick.*;
import mdes.slick.sui.*;
import mdes.slick.sui.event.*;
import org.newdawn.slick.gui.GUIContext;

/**
 * Lesson A in the documentation, showing how to
 * create a SuiWindow.
 *
 * @author davedes
 */
public class SimpleSuiTest extends BasicGame {
    
    /** Entry point to the application. */
    public static void main(String[] args) {
        try {
            AppGameContainer container = new AppGameContainer(new SimpleSuiTest());
            container.setDisplayMode(640, 480, false);
            container.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public SimpleSuiTest() {
        super("SimpleSuiTest");
    }
    
    private SuiDisplay display = null;
    private Color c1c = Color.red;
    private Color c2c = Color.blue;
    private Color c3c = Color.orange;
    
    public void init(GameContainer container) throws SlickException {
        //initializes Sui
        display = new SuiDisplay(container) {
            public void renderComponent(GUIContext c, Graphics g) {
                g.setColor(Color.gray);
                g.fillRect(getAbsoluteX(), getAbsoluteY(), getWidth(), getHeight());
            }
        };
        Sui.init(display);
        
        SuiContainer c1 = new SuiContainer() {
            public void renderComponent(GUIContext c, Graphics g) {
                g.setColor(c1c);
                g.fillRect(getAbsoluteX(), getAbsoluteY(), getWidth(), getHeight());
            }
        };
        display.add(c1);
        
        SuiContainer c2 = new SuiContainer() {
            public void renderComponent(GUIContext c, Graphics g) {
                g.setColor(c2c);
                g.fillRect(getAbsoluteX(), getAbsoluteY(), getWidth(), getHeight());
            }
        };
        
        SuiLabel c3 = new SuiButton("Test");
        c3.setPadding(5);
        c3.setToolTipText("Test1");
        c2.setToolTipText("Test2");
        
        c1.add(c2);
        c2.add(c3);
        
        c1.setLocation(200,200);
        c2.setLocation(5,5);
        c3.setLocation(5,5);
        
        SuiMouseListener l = new SuiMouseAdapter() {
            
            public void mouseDragged(SuiMouseEvent e) {
                int b = e.getButton();
      		int ox = e.getOldX();
      		int oy = e.getOldY();
    	  	int nx = e.getX();
	      	int ny = e.getY();
	      	int absx = e.getAbsoluteX();
	      	int absy = e.getAbsoluteY();
	      		      	    
      		if (b==SuiMouseEvent.BUTTON1) {
                    SuiContainer c = e.getSource();
                    c.translate(nx-ox, ny-oy);
      		}
            }
        };
                        
        c1.addMouseListener(l);
        c2.addMouseListener(l);
        c3.addMouseListener(l);
        
        c1.setSize(200, 200);
        c2.setSize(150, 150);
        c3.pack();
        
        display.setName("display");
        c1.setName("red");
        c2.setName("blue");
        c3.setName("orange");
    }
    
    public void render(GameContainer container, Graphics g) {
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