/*
 * RoundedRectangleTest.java
 *
 * Created on June 4, 2007, 10:14 PM
 */

package mdes.slick.sui.test;

import org.newdawn.slick.*;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.*;

public class RoundedRectangleTest extends BasicGame {
    
    public static void main(String[] args) {
        try {
            AppGameContainer container = new AppGameContainer(new RoundedRectangleTest());
            container.setDisplayMode(640, 480, false);
            container.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public RoundedRectangleTest() {
        super("RoundedRectangleTest");
    }
    
    private RoundedRectangle rect = null;
    private Rectangle win = null, winTitle = null;
    private GradientFill grad = null;
    
    private Color buttonBase = new Color(154, 174, 193);
    private Color lightTop = new Color(213, 219, 229);
    private Color lightBottom = new Color(199, 207, 221);
    
    private Color buttonBase2 = new Color(212, 215, 220);
    private Color lightTop2 = new Color(235, 239, 246);
    private Color lightBottom2 = new Color(233, 236, 242);
    
    private Color borderDark = new Color(85, 88, 94);
    private Color borderLight = new Color(149, 152, 158);
    private Color transparent = new Color(1f,1f,1f,0f);
    private Color bg1 = new Color(244, 248, 255);
    
    private Color winBorderLight = new Color(156, 161, 167);
    private Color winBorderDark = new Color(133, 137, 144);
    private Color winTitleStart = new Color(182, 201, 221);
    private Color winTitleEnd = new Color(228, 232, 238);
    
    private AngelCodeFont font;
    
    
    //TODO: fade color animation effect
        
    public void init(GameContainer container) throws SlickException {
        rect = new RoundedRectangle(200f,200f,89f,21f,3f,8); 
        //rect = new RoundedRectangle(200f,200f,15f,15f,3f); 
        win = new Rectangle(100f, 100f, 325f, 130f);
        winTitle = new Rectangle(100f, 100f, 325f, 20f);
        
        grad = new GradientFill(0, 0, transparent, 0, 0, transparent);
        
        try { font = new AngelCodeFont("res/fonts/verdana.fnt", "res/fonts/verdana.png"); }
        catch (SlickException e) {}
    }
    
    public void render(GameContainer container, Graphics g) {
        g.setBackground(Color.lightGray);
        g.setFont(font);
        
        Color oldStart = grad.getStartColor();
        Color oldEnd = grad.getEndColor();
        
        renderWindow(g);
        
        g.setAntiAlias(true);
            
        int mx = container.getInput().getMouseX();
        int my = container.getInput().getMouseY();
        
        Color base=null, lightTop=null, lightBot=null, screen=null;
        
        if (rect.contains(mx, my) && !container.getInput().isMouseButtonDown(0)) {
            base = this.buttonBase;
            lightTop = this.lightTop;
            lightBot = this.lightBottom;
            screen = Color.lightGray;
            screen.a = .5f;
        } else {
            base = this.buttonBase2;
            lightTop = this.lightTop2;
            lightBot = this.lightBottom2;
        }
            
        renderButton(g, base, lightTop, lightBot);
        
        grad.setStartColor(oldStart);
        grad.setEndColor(oldEnd);
        
        g.setAntiAlias(false);
        
        float mid = rect.getHeight()/2.0f;
        g.setColor(Color.black);
        //g.getFont().drawString(rect.getX()+mid, rect.getY()+mid-g.getFont().getLineHeight()/2, "Save", g.getColor());
        g.drawString("Save", rect.getX()+mid, rect.getY()+mid-g.getFont().getLineHeight()/2);
    }
    
    public void update(GameContainer container, int delta) {
        float s = 0.00f;
        rect.setX( rect.getX() + delta * s );
        win.setX( win.getX() + delta * s );
        winTitle.setX( winTitle.getX() + delta * s );
    }
        
    private void renderWindow(Graphics g) {
        float x = win.getX(), y = win.getY(),   
                width = win.getWidth(), height = win.getHeight();
        
        g.setColor(bg1);
        g.fill(win);
        
        winTitle.setWidth(win.getWidth());
        
        float mid = winTitle.getWidth()/2.0f;
        grad.setStartColor(winTitleStart);
        grad.setEndColor(winTitleEnd);
        grad.setStart(-mid, 0);
        grad.setEnd(mid, 0);
        g.fill(winTitle, grad);
        
        g.setColor(winBorderLight);
        g.drawLine(x,y, x+width,y);
        g.drawLine(x,y, x,y+height);
        
        g.drawLine(winTitle.getX(), winTitle.getY()+winTitle.getHeight(), 
                    winTitle.getX()+winTitle.getWidth(), winTitle.getY()+winTitle.getHeight());
        
        g.setColor(winBorderDark);
        g.drawLine(x+width,y, x+width-1,y+height);
        g.drawLine(x,y+height, x+width,y+height);
    }
    
    private void renderButton(Graphics g, Color base, Color lightTop, Color lightBot) {
        float mid = rect.getHeight()/2.0f;
        
        g.setAntiAlias(false);
        
        grad.setStartColor(lightTop);
        grad.setEndColor(base);
        grad.setStart(0, -mid/1.5f);
        grad.setEnd(0, mid/4);
        g.fill(rect, grad);
        
        grad.setStartColor(transparent);
        grad.setEndColor(lightBot);
        grad.setStart(0, 0);
        grad.setEnd(0, mid*2);
        g.fill(rect, grad);
        
        g.setAntiAlias(true);
        
        grad.setStartColor(borderLight);
        grad.setEndColor(borderDark);
        grad.setStart(0, -mid);
        grad.setEnd(0, mid); 
        g.draw(rect, grad);
    }
    
    public void keyPressed(int k, char c) {
        if (k==Input.KEY_ESCAPE) {
            System.exit(0);
        } else if (k==Input.KEY_1) {
            if (rect.getWidth()==200)
                rect.setWidth(400);
            else if (rect.getWidth()==400)
                rect.setWidth(200);
        }
    }
}