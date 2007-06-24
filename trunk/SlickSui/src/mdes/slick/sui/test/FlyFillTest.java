/*
 * RoundedRectangleTest.java
 *
 * Created on June 4, 2007, 10:14 PM
 */

package mdes.slick.sui.test;

import mdes.slick.sui.Sui;
import mdes.slick.sui.SuiButton;
import mdes.slick.sui.SuiDisplay;
import mdes.slick.sui.event.SuiActionEvent;
import mdes.slick.sui.event.SuiActionListener;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.state.*;

public class FlyFillTest extends StateBasedGame {
    
    public static void main(String[] args) {
        try {
            AppGameContainer container = new AppGameContainer(new FlyFillTest());
            container.setDisplayMode(640, 480, false);
            container.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private Image img = null;
    
    public FlyFillTest() {
        super("RoundedRectangleTest");
    }
    
    Menu m = null;
    
    public void initStatesList(GameContainer container) throws SlickException {
        display = new SuiDisplay(container) {
            public void renderComponent(GUIContext c, Graphics g) {
                super.renderComponent(c, g);
                g.setColor(Color.red);
                g.fillRect(getAbsoluteX(), getAbsoluteY(), getWidth(), getHeight());
            }
        };
        display.setName("disp");
        //display.setClipEnabled(false);
        
        addState(new State1());
        addState(new State2());
    }
    
    public void keyPressed(int k, char c) {
        if (k==Input.KEY_ESCAPE) {
            System.exit(0);
        } else if (c=='1') {
            this.enterState(State1.ID);
        } else if (c=='2') {
            this.enterState(State2.ID);
        }
    }
    
    SuiDisplay display;
    
    class State1 extends BasicGameState {
        public static final int ID = 1;
        public int getID() { return ID; }
        
        
        
        public void init(GameContainer c, StateBasedGame game) throws SlickException {
            SuiButton b = new SuiButton("First") {               
                public void renderComponent(GUIContext c, Graphics g) {
                    super.renderComponent(c, g);
                }
            };
            b.addActionListener(new SuiActionListener() {
                public void actionPerformed(SuiActionEvent e) {
                    System.out.println("first");
                    
                    SuiButton a = new SuiButton("Added");
                    a.pack();
                    a.setLocation(50, 50);
                    display.add(a);
                }
            });
            display.add(b);
            
            b.pack();
            b.setLocation(20, 80);
        }
        
        public void update(GameContainer c, StateBasedGame game, int delta) {
            display.update(c, delta);
        }
        
        public void render(GameContainer c, StateBasedGame game, Graphics g) {
            display.render(c, g);
        }
    }
    
    class State2 extends BasicGameState {
        public static final int ID = 2;
        public int getID() { return ID; }
        
        public void init(GameContainer c, StateBasedGame game) throws SlickException {
            SuiButton b = new SuiButton("Second");
            b.addActionListener(new SuiActionListener() {
                public void actionPerformed(SuiActionEvent e) {
                    SuiButton a = new SuiButton("Added");
                    a.pack();
                    a.setLocation(50, 50);
                    display.add(a);
                }
            });
            display.add(b);
            
            b.pack();
            b.setLocation(20, 20);
        }
        
        public void update(GameContainer c, StateBasedGame game, int delta) {
            display.update(c, delta);
        }
        
        public void render(GameContainer c, StateBasedGame game, Graphics g) {
            display.render(c, g);
        }
    }
}