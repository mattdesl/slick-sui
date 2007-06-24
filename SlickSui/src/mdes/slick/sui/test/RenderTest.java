/*
 * RenderTest.java
 *
 * Created on June 4, 2007, 5:31 PM
 */

package mdes.slick.sui.test;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.*;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;

import mdes.slick.sui.*;
import mdes.slick.sui.skin.ClassicSkin;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.fills.*;

/**
 * 
 * @author davedes
 */
public class RenderTest extends BasicGame {
    
    /** Entry point to the application. */
    public static void main(String[] args) {
        try {
            AppGameContainer container = new AppGameContainer(new RenderTest());
            container.setDisplayMode(640, 480, false);
            container.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public RenderTest() {
        super("RenderTest");
    }
    
    public void init(GameContainer container) throws SlickException {
                
    }
    
    public void render(GameContainer container, Graphics g) {
        //g.setColor(Color.pink);
        //g.drawRect(100,100,100,125);
        
        //g.setClip(100,100-1,100+1,100+1);
        g.setWorldClip(100,100,100,100);
        
        g.setColor(Color.red);
        g.fillRect(100,100,100,100);
        
        g.setColor(Color.lightGray);
        g.drawRect(100,100, 100,100);
        
        
    }
    
    public void update(GameContainer container, int delta) {
    }
    
    public void keyPressed(int k, char c) {
        if (k==Input.KEY_ESCAPE) {
            System.exit(0);
        }
    }
    
    public class RoundRectButton extends SuiButton {
        public static final float CORNER = 25;
        private RoundedRectangle bounds = new RoundedRectangle(0f,0f,0f,0f,CORNER);
        
        public RoundRectButton(String t) {
            super(t);
        }
        
        public RoundedRectangle getBounds() {
            bounds.setX(getAbsoluteX());
            bounds.setY(getAbsoluteY());
            bounds.setWidth(getWidth());
            bounds.setHeight(getHeight());
            return bounds;
        }
        
        public boolean contains(int x, int y) {
            return bounds.contains(x, y);
        }
    }
}