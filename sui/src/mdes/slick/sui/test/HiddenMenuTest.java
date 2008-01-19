/*
 * HiddenMenuTest.java
 *
 * Created on November 26, 2007, 10:01 PM
 */

package mdes.slick.sui.test;

import mdes.slick.sui.Button;
import mdes.slick.sui.Container;
import mdes.slick.sui.Display;
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
public class HiddenMenuTest extends BasicGame {
    
    public static void main(String[] args) throws Exception {
        AppGameContainer app = new AppGameContainer(new HiddenMenuTest());
        app.setDisplayMode(800,600,false);
        app.start();
    }
    
    /** Creates a new instance of HiddenMenuTest */
    public HiddenMenuTest() {
        super("HiddenMenuTest");
    }
    
    private Display display = null;
    private Container hiddenPanel;
    private int moveDir = 0;
    private boolean doneMoving = false;
    
    public void init(GameContainer container) throws SlickException {
        display = new Display(container);
        display.setSendingGlobalEvents(true);
        
        final Container hoverArea = new Container();
        //hoverArea.setOpaque(true);
        //hoverArea.setBackground(new Color(.25f, .25f, .85f, .25f));
        hoverArea.setLocation(100, 0);
        hoverArea.setSize(600, 35);
        display.add(hoverArea);
        
        display.addMouseListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                int old = moveDir;
                
                if (hoverArea.contains(e.getAbsoluteX(), e.getAbsoluteY())) {
                    moveDir = 1;
                } else
                    moveDir = -1;
                
                //if we have changed direction
                doneMoving = moveDir!=old;
            }
        });
        
        hiddenPanel = new Container();
        hiddenPanel.setOpaque(true);
        hiddenPanel.setBackground(Color.white);
        hiddenPanel.setSize(hoverArea.getSize());
        hiddenPanel.setLocation(hoverArea.getLocation());
        display.add(hiddenPanel);
        
        final Button btn1 = new Button("File");
        btn1.pack();
        btn1.setLocation(10, (int)(hiddenPanel.getHeight()/2f-btn1.getHeight()/2f));
        hiddenPanel.add(btn1);
        
        moveDir = -1;
    }
    
    public void update(GameContainer container, int delta) throws SlickException {
        display.update(container, delta);
        
        if (moveDir!=0 && !doneMoving) {
            float dy = moveDir * delta * 0.06f;
            hiddenPanel.translate(0, dy);
            if (hiddenPanel.getY() < (-hiddenPanel.getHeight())) {
                hiddenPanel.setY(-hiddenPanel.getHeight());
                doneMoving = true;
                moveDir = 0;
            }
            if (hiddenPanel.getY() > 0) {
                hiddenPanel.setY(0);
                doneMoving = true;
                moveDir = 0;
            }
        }
        
        if (container.getInput().isKeyPressed(Input.KEY_ESCAPE))
            container.exit();
    }
    
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.setBackground(Color.lightGray);
        display.render(container, g);
    }
}
