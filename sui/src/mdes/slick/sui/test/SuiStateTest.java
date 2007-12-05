/*
 * SuiStateTest.java
 *
 * Created on November 5, 2007, 5:10 PM
 */

package mdes.slick.sui.test;

import mdes.slick.sui.*;
import mdes.slick.sui.event.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import mdes.slick.sui.theme.*;
import mdes.slick.sui.skin.simple.*;
import java.util.*;

/**
 *
 * @author Matt
 */
public class SuiStateTest extends StateBasedGame {
    
    private static final String SINGLETON = "shared display";
    private static final String INDIVIDUAL = "individual displays";
    
    public static void main(String[] args) throws Exception {
        AppGameContainer app = new AppGameContainer(new SuiStateTest());
        app.setDisplayMode(640,480,false);
        app.start();
    }
    
    /** Creates a new instance of SuiStateTest */
    public SuiStateTest() {
        super("SuiStateTest");
    }
    
    private String mode = SINGLETON;
    private SuiDisplay singleton;
    private ArrayList singletonWindows = new ArrayList();
    
    private void renderText(Graphics g) {
        g.setColor(Color.white);
        g.drawString("Press SPACE to change display setup.", 10, 25);
        g.drawString("Currently viewing: "+mode, 10, 40);
        g.drawString("Press W to show all windows in this state.", 10, 55);
        g.drawString("Use keys 1-3 to change states.", 10, 70);
    }
    
    public void initStatesList(GameContainer container) throws SlickException {
        Sui.setTheme(new BitterLemonTheme());
        SimpleSkin.setRoundRectanglesEnabled(true);
        singleton = new SuiDisplay(container);
        singleton.setSendingGlobalEvents(false);
        
        SuiWindow window = new SuiWindow("Singleton Window");
        window.setSize(200, 150);
        window.setLocation(100, 100);
        window.setVisible(true);
        
        final SuiCheckBox box = new SuiCheckBox("Are you sure?");        
        box.pack();
        box.setLocation(10, 25);
        window.add(box);
        window.setMinimumSize(150, 70);
        
        singletonWindows.add(window);
        singleton.add(window);
                
        this.addState(new State1());
        this.addState(new State2());
        this.addState(new State3());
    }
    
    public void keyPressed(int k, char c) {
        if (k==Input.KEY_ESCAPE) {
            System.exit(0);
        } else if (c=='1') {
            this.enterState(State1.ID);
        } else if (c=='2') {
            this.enterState(State2.ID);
        } else if (c=='3') {
            this.enterState(State3.ID);
        } else if (k==Input.KEY_W) {
            ArrayList windows = null;
            
            if (mode==SINGLETON)
                windows = singletonWindows;
            else {
                windows = ((TestState)getCurrentState()).getIndividualWindows();
            }
            
            for (int i=0; i<windows.size(); i++) {
                SuiWindow win = (SuiWindow)windows.get(i);
                win.setVisible(true);
            }
        } else if (k==Input.KEY_SPACE) {
            mode = (mode==SINGLETON) ? INDIVIDUAL : SINGLETON;
        }
    }
    
    public static interface TestState {
        public ArrayList getIndividualWindows();
    }
        
    protected class State1 extends BasicGameState implements TestState {
        public static final int ID = 1;
        private Color bg = new Color(124,71,71);
        private ArrayList indvWindows = new ArrayList();
        private SuiDisplay indv = null;
        
        public ArrayList getIndividualWindows() {
            return indvWindows;
        }
        
        public int getID() {
            return ID;
        }

        public void init(GameContainer container, StateBasedGame game) throws SlickException {
            indv = new SuiDisplay(container);
            indv.setSendingGlobalEvents(false);
            
            SuiWindow win1 = new SuiWindow("State 1");
            win1.setBounds(100, 200, 200, 50);
            
            SuiWindow win2 = new SuiWindow("Testing Window");
            win2.setBounds(125, 225, 200, 100);
            win2.setResizable(false);
            
            SuiButton button = new SuiButton("Button");
            button.setLocation(25, 10);
            button.pack();
            win2.getContentPane().add(button);
            
            win1.setVisible(true);
            win2.setVisible(true);
            
            //add windows to the state's display
            indv.add(win1);
            indv.add(win2);
            
            //add windows to a list
            indvWindows.add(win1);
            indvWindows.add(win2);
            win1.grabFocus();
            win2.grabFocus();
        }

        public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
            g.setBackground(bg);
            renderText(g);
            SuiDisplay current = (mode==SINGLETON) ? singleton : indv;
            current.render(container, g);
        }

        public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
            SuiDisplay current = (mode==SINGLETON) ? singleton : indv;
            current.update(container, delta);
        }
    }
    
    protected class State2 extends BasicGameState implements TestState {
        public static final int ID = 2;
        private Color bg = new Color(71,102,124);
        private ArrayList indvWindows = new ArrayList();
        private SuiDisplay indv = null;
        
        public ArrayList getIndividualWindows() {
            return indvWindows;
        }
        
        public int getID() {
            return ID;
        }

        public void init(GameContainer container, StateBasedGame game) throws SlickException {
            indv = new SuiDisplay(container);
            indv.setSendingGlobalEvents(false);
            
            final SuiWindow win1 = new SuiWindow("State 2");
            win1.setBounds(200, 205, 200, 100);
            win1.setVisible(true);
            win1.setResizable(false);
            indvWindows.add(win1);
            indv.add(win1);
            
            SuiToggleButton button = new SuiToggleButton("Click");
            button.pack();
            button.setLocation(200, 150);
            button.addActionListener(new SuiActionListener() {
                public void actionPerformed(SuiActionEvent e) {
                    win1.setResizable(!win1.isResizable());
                }
            });
            indv.add(button);
            
            SuiLabel label = new SuiLabel("Use the button to toggle window resizing.");
            label.pack();
            label.setLocation(button.getX(), button.getY()-label.getHeight()-5);
            label.setOpaque(true);
            label.setBackground(new Color(1f,1f,1f,0.85f));
            
            indv.add(label);
        }

        public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
            g.setBackground(bg);
            renderText(g);
            SuiDisplay current = (mode==SINGLETON) ? singleton : indv;
            current.render(container, g);
        }

        public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
            SuiDisplay current = (mode==SINGLETON) ? singleton : indv;
            current.update(container, delta);
        }
    }
    
    protected class State3 extends BasicGameState implements TestState {
        public static final int ID = 3;
        private ArrayList indvWindows = new ArrayList();
        private SuiDisplay indv = null;
        
        public ArrayList getIndividualWindows() {
            return indvWindows;
        }
        
        public int getID() {
            return ID;
        }

        public void init(GameContainer container, StateBasedGame game) throws SlickException {
            indv = new SuiDisplay(container);
            indv.setSendingGlobalEvents(false);
            
            float startx = 200;
            float starty = 180;
            float inc = 25;
            for (int i=0; i<5; i++) {
                SuiWindow win = new SuiWindow("Window "+i);
                win.setLocation(startx+(i*inc), starty+(i*inc));
                win.setSize(200, 150);
                win.setVisible(true);
                indv.add(win);
                indvWindows.add(win);
                if (i==4)
                    win.grabFocus();
            }
        }

        public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
            g.setBackground(Color.gray);
            renderText(g);
            SuiDisplay current = (mode==SINGLETON) ? singleton : indv;
            current.render(container, g);
        }

        public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
            SuiDisplay current = (mode==SINGLETON) ? singleton : indv;
            current.update(container, delta);
        }
    }
}