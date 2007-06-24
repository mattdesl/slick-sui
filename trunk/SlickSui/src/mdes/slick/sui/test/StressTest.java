/*
 * SimpleClipTest.java
 *
 * Created on June 1, 2007, 8:29 PM
 */

package mdes.slick.sui.test;

import org.newdawn.slick.*;
import mdes.slick.sui.*;

/**
 * Lesson A in the documentation, showing how to
 * create a SuiWindow.
 *
 * @author davedes
 */
public class StressTest extends BasicGame {
    
    /** Entry point to the application. */
    public static void main(String[] args) {
        try {
            AppGameContainer container = new AppGameContainer(new StressTest());
            container.setDisplayMode(640, 480, false);
            container.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public StressTest() {
        super("Stress");
    }
    
    private SuiDisplay display;
    
    public void init(GameContainer container) throws SlickException {
        display = Sui.init(container);
        
         /*   //new 650 fps
            //old 220 fps
        for (int i=0; i<100; i++) {
            SuiContainer c = new SuiButton();
            c.setOpaque(true);
            c.setVisible(true);
            c.setBackground(Color.red);
            c.setSize(100,100);
            c.setLocation(50+(i*20),50+(i*20));
            display.add(c);
        }*/
        
        TickBox box = new TickBox();
        box.pack();
        box.setLocation(200,200);
        display.add(box);
    }
    
    public void render(GameContainer container, Graphics g) {
        g.setBackground(Color.white);
        display.render(container, g);
        /*float width, height, x, y;
         
        //calcClipping(0,0,container.getWidth(),container.getHeight());
        clip.x = 0;
        clip.y = 0;
        clip.width = container.getWidth();
        clip.height = container.getHeight();
        g.setWorldClip(clip.x, clip.y, clip.width, clip.height);
         
        //component bounds == clip bounds
        x=200;
        y=200;
        width=200;
        height=200;
        g.setWorldClip(clip.x,clip.y,clip.width,clip.height);
        calcClipping(x,y,width,height);
        g.setColor(Color.red);
        g.fillRect(x,y,width,height);
         
        //new component bounds
        x = 260;
        y = 180;
        width = 150;
        height = 150;
        g.setWorldClip(clip.x,clip.y,clip.width,clip.height);
        calcClipping(x,y,width,height);
        g.setColor(Color.blue);
        g.fillRect(x,y,width,height);
         
        x = 250;
        y = 250;
        width = 300;
        height = 300;
        g.setWorldClip(clip.x,clip.y,clip.width,clip.height);
        calcClipping(x,y,width,height);
        g.setColor(Color.orange);
        g.fillRect(x,y,width,height);*/
    }
    
    public void update(GameContainer container, int delta) {
        display.update(container, delta);
    }
    
    
    public void keyPressed(int k, char c) {
        if (k==Input.KEY_ESCAPE) {
            System.exit(0);
        }
    }
    
    private class ClipBounds {
        float width=0f,height=0f,x=0f,y=0f;
    }
}