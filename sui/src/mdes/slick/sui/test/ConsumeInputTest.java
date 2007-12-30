/*
 * ConsumeInputTest.java
 *
 * Created on December 6, 2007, 5:47 PM
 */

package mdes.slick.sui.test;

import mdes.slick.sui.SuiDisplay;
import mdes.slick.sui.SuiLabel;
import mdes.slick.sui.SuiWindow;
import mdes.slick.sui.event.SuiMouseAdapter;
import mdes.slick.sui.event.SuiMouseEvent;
import mdes.slick.sui.event.SuiMouseListener;
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
public class ConsumeInputTest extends BasicGame {
    
    public static void main(String[] args) throws Exception {
        AppGameContainer app = new AppGameContainer(new ConsumeInputTest());
        app.setDisplayMode(800,600,false);
        app.start();
    }
    
    /** Creates a new instance of ConsumeInputTest */
    public ConsumeInputTest() {
        super("ConsumeInputTest");
    }
    
    private SuiDisplay disp;
    
    public void init(GameContainer container) throws SlickException {
        disp = new SuiDisplay(container);
        disp.setSendingGlobalEvents(false);
        disp.setName("display");

        SuiMouseListener press = new SuiMouseAdapter() {
            public void mousePressed(SuiMouseEvent e) {
                System.out.println("Press From sui: "+e);
            }
            public void mouseReleased(SuiMouseEvent e) {
                System.out.println("Release From Sui: "+e);
            }
            public void mouseDragged(SuiMouseEvent e) {
                System.out.println("Drag from Sui: "+e);
            }
        };
        
        SuiLabel label = new SuiLabel("Testeroo") {
            protected boolean isConsumingEvents() {
                return true;
            }
        };
        label.pack();
        label.setBackground(Color.blue);
        label.setOpaque(true);
        label.setLocation(250, 300);
        label.setName("label");
        label.addMouseListener(press);
        disp.add(label);
        
        SuiWindow win = new SuiWindow("Test") {
            protected boolean isConsumingEvents() {
                return true;
            }
        };
        win.setName("test window");
        win.setSize(200, 200);
        win.setLocation(200, 200);
        win.getTitleBar().addMouseListener(press);
        //disp.add(win);
    }
    
    public void update(GameContainer container, int delta) throws SlickException {
        disp.update(container, delta);
        
        if (container.getInput().isKeyPressed(Input.KEY_ESCAPE))
            container.exit();
    }
    
    public void render(GameContainer container, Graphics g) throws SlickException {
        disp.render(container, g);
    }
    
    public void mousePressed(int button, int x, int y) {
       System.out.println("Press from slick: "+x+", "+y);
    }
    
    public void mouseReleased(int button, int x, int y) {
        System.out.println("Release from slick: "+x+", "+y);
    }
}
