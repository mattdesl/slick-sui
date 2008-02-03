/*
 * ConsumeInputTest.java
 *
 * Created on December 6, 2007, 5:47 PM
 */

package mdes.slick.sui.test;

import mdes.slick.sui.Button;
import mdes.slick.sui.Container;
import mdes.slick.sui.Display;
import mdes.slick.sui.Sui;
import mdes.slick.sui.ToggleButton;
import mdes.slick.sui.event.ActionEvent;
import mdes.slick.sui.event.ActionListener;
import mdes.slick.sui.event.MouseAdapter;
import mdes.slick.sui.event.MouseEvent;
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

        container.getGraphics().setBackground(Sui.getTheme().getBackground());
        
        Container c = new Container();
        c.setSize(200, 200);
        c.setLocation(100, 150);
        c.setOpaque(true);
        c.setBackground(Color.lightGray);
        
        final ToggleButton ena = new ToggleButton("Toggle Enable");
        ena.pack();
        ena.setSelected(true);
        ena.setLocation(10, 10);
        c.add(ena);
        
        final Button btn = new Button("Test");
        btn.pack();
        btn.setLocation(ena.getWidth()+ena.getX()+5, 10);
        
        ena.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btn.setEnabled(ena.isSelected());
            }
        });
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("button press");
            }
        });
        c.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                System.out.println("mouse press");
            }
        });
        c.add(btn);
        disp.add(c);
        
        /*MouseListener press = new MouseAdapter() {
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
        //disp.add(win);*/
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
