/*
 * WindowTest.java
 *
 * Created on January 19, 2008, 3:43 PM
 */

package mdes.slick.sui.test;

import mdes.slick.sui.Button;
import mdes.slick.sui.Display;
import mdes.slick.sui.DraggableContainer;
import mdes.slick.sui.Window;
import mdes.slick.sui.layout.RowLayout;
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
public class WindowTest extends BasicGame {
    
    public static void main(String[] args) throws Exception {
        AppGameContainer app = new AppGameContainer(new WindowTest());
        app.setDisplayMode(800,600,false);
        app.start();
    }
    
    /** Creates a new instance of WindowTest */
    public WindowTest() {
        super("WindowTest");
    }
    
    private Display disp = null;
    
    public void init(GameContainer container) throws SlickException {
        disp = new Display(container);
        
        Window red = new Window();
        red.setOpaque(true);
        red.setSize(150, 200);
        red.setLocation(110, 120);
        red.setBackground(Color.red);
        red.addMouseListener(DraggableContainer.createDragListener(red));
        disp.add(red);
        
        Window green = new Window();
        green.setOpaque(true);
        green.setSize(200, 150);
        green.setLocation(200, 100);
        green.setBackground(Color.green);
        green.addMouseListener(DraggableContainer.createDragListener(green));
        disp.add(green);
        
        Window top = new Window();
        top.setOpaque(true);
        top.setSize(150, 150);
        top.setLocation(300, 250);
        top.setBackground(Color.lightGray);
        top.setAlwaysOnTop(true);
        top.addMouseListener(DraggableContainer.createDragListener(top));
        top.setLayout(new RowLayout(true, RowLayout.CENTER, RowLayout.CENTER));
        top.add(new Button("Click"));
        disp.add(top);
        
        red.grabFocus();
    }
    
    public void update(GameContainer container, int delta) throws SlickException {
        disp.update(container, delta);
        if (container.getInput().isKeyPressed(Input.KEY_ESCAPE))
            container.exit();
    }
    
    public void render(GameContainer container, Graphics g) throws SlickException {
        disp.render(container, g);
        
        g.setColor(Color.white);
        g.drawString("These windows are draggable and layerable. Try moving them with the left mouse button.", 10, 25);
        g.drawString("The gray window is always on top.", 10, 40);
    }
}
