/*
 * StressTest.java
 *
 * Created on November 8, 2007, 9:31 PM
 */

package mdes.slick.sui.test;

import mdes.slick.sui.Button;
import mdes.slick.sui.Component;
import mdes.slick.sui.Display;
import mdes.slick.sui.skin.simple.SimpleSkin;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Stress tests.
 * 
 * @author davedes
 */
public class StressTest extends BasicGame {
    
    public static void main(String[] args) throws Exception {
        AppGameContainer app = new AppGameContainer(new StressTest());
        app.setDisplayMode(800,600,false);
        app.start();
    }
    
    /** Creates a new instance of StressTest */
    public StressTest() {
        super("StressTest");
    }
    
    private Display disp = null;
    final float area = 10f;
    float cellWidth;
    float cellHeight;
    float cols;
    float rows;
    
    
    public void init(GameContainer container) throws SlickException {
        container.setShowFPS(false);
        
        disp = new Display(container);
        disp.setSendingGlobalEvents(false);
        
        float width = container.getWidth();
        float height = container.getHeight();
        cellWidth = width/area;
        cellHeight = height/area;
        
        cols = width/cellWidth;
        rows = height/cellHeight;
        
        for (int x=0; x<cols; x++) {
            for (int y=0; y<rows; y++) {
                Component btn = new Button();
                //btn.setOpaque(true);
                //btn.setBackground(Color.gray);
                btn.setToolTipText("Button ("+x+", "+y+")");
                
                float ax = x*cellWidth;
                float ay = y*cellHeight;
                
                btn.setLocation(x*cellWidth, y*cellHeight);
                btn.setSize(cellWidth, cellHeight);
                disp.add(btn);
            }
        }
    }
    
    public void update(GameContainer container, int delta) throws SlickException {
        disp.update(container, delta);
        
        if (container.getInput().isKeyDown(Input.KEY_ESCAPE)) 
            container.exit();
    }
    
    public void render(GameContainer container, Graphics g) throws SlickException {
        disp.render(container, g);
        g.setColor(Color.black);
        g.drawString("FPS: "+container.getFPS(), 10, 10);
        g.drawString("Press SPACE to toggle round rectangles.", 10, 25);
    }
    
    public void keyPressed(int key, char c) {
        if (key == Input.KEY_SPACE) {
            SimpleSkin.setRoundRectanglesEnabled(!SimpleSkin.isRoundRectanglesEnabled());
        }
    }
}
