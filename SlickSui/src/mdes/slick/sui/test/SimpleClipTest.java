/*
 * SimpleClipTest.java
 *
 * Created on June 1, 2007, 8:29 PM
 */

package mdes.slick.sui.test;

import org.newdawn.slick.*;
import mdes.slick.sui.*;
import mdes.slick.sui.event.SuiKeyAdapter;
import mdes.slick.sui.event.SuiKeyEvent;
import mdes.slick.sui.event.SuiKeyListener;
import mdes.slick.sui.event.SuiMouseAdapter;
import mdes.slick.sui.event.SuiMouseEvent;
import mdes.slick.sui.event.SuiMouseListener;
import org.newdawn.slick.gui.GUIContext;

/**
 * Lesson A in the documentation, showing how to
 * create a SuiWindow.
 *
 * @author davedes
 */
public class SimpleClipTest extends BasicGame {
    
    /** Entry point to the application. */
    public static void main(String[] args) {
        try {
            AppGameContainer container = new AppGameContainer(new SimpleClipTest());
            container.setDisplayMode(640, 480, false);
            container.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public SimpleClipTest() {
        super("SimpleClipTest");
    }
    
    private ClipBounds clip = new ClipBounds();
    private SuiDisplay display;
    
    public void init(GameContainer container) throws SlickException {
        display = Sui.init(container);
        //display.setClipEnabled(false);
        
        SuiButton button = new SuiButton("Test1");
        button.pack();
        button.setName("Test1");
        button.setLocation(100, 100);
        display.add(button);
        
        SuiButton two = new SuiButton("Second");
        two.setName("Second");
        two.pack();
        two.setLocation(200, 200);
        display.add(two);
        
        SuiLabel content = new SuiLabel();
        display.add(content);
        
        content.setOpaque(true);
        content.setBackground(Color.red);
        
        SuiButton b1 = new SuiButton("b1");
        b1.pack();
        content.add(b1);
        
        SuiButton b2 = new SuiButton("b2");
        b2.pack();
        b2.setX(b1.getWidth());
        content.add(b2);
        
        content.setBounds(300,300,100,100);
        
    }
    
    public void render(GameContainer container, Graphics g) {
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
    }
    
    private void calcClipping(float compx, float compy, float compw, float comph) {
        if (clip.x < compx) {
            clip.width -= compx-clip.x;
            clip.x = compx;
        }
        
        float d = clip.x + compw;
        float dcomp = compx + compw;
        if (d > dcomp) {
            clip.width -= -(d-dcomp);
        }
        
        if (clip.y < compy) {
            clip.height -= (clip.y-compy);
            clip.y = compy;
        }
        
        float a = clip.y + clip.height;
        float acomp = compy + comph;
        if (a > acomp) {
            clip.height -= a-acomp;
        }
    }
    
    private void calcClipping2(float compx, float compy, int compw, int comph) {
        if (clip.x < compx) {
            clip.width -= compx-clip.x;
            clip.x = compx;
        }
        
        float d = clip.x + compw;
        float dcomp = compx + compw;
        if (d > dcomp) {
            clip.width -= -(d-dcomp);
        }
        
        if (clip.y < compy) {
            clip.height -= (clip.y-compy);
            clip.y = compy;
        }
        
        float a = clip.y + clip.height;
        float acomp = compy + comph;
        if (a > acomp) {
            clip.height -= a-acomp;
        }
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