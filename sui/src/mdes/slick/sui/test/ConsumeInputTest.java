/*
 * ConsumeInputTest.java
 *
 * Created on December 6, 2007, 5:47 PM
 */

package mdes.slick.sui.test;

import mdes.slick.sui.Display;
import mdes.slick.sui.Label;
import mdes.slick.sui.Frame;
import mdes.slick.sui.event.MouseAdapter;
import mdes.slick.sui.event.MouseEvent;
import mdes.slick.sui.event.MouseListener;
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
    
    private Display disp;
    
    public void init(GameContainer container) throws SlickException {
        disp = new Display(container);
        disp.setSendingGlobalEvents(false);
        disp.setName("display");

        MouseListener press = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                System.out.println("Press From sui: "+e);
            }
            public void mouseReleased(MouseEvent e) {
                System.out.println("Release From Sui: "+e);
            }
            public void mouseDragged(MouseEvent e) {
                System.out.println("Drag from Sui: "+e);
            }
        };
        
        Label label = new Label("Testeroo") {
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
        
        Frame win = new Frame("Test") {
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
