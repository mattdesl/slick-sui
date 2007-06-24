package mdes.slick.sui.test;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.*;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;

/**
 * Lesson A in the documentation, showing how to
 * create a SuiWindow.
 *
 * @author davedes
 */
public class FontPadTest extends BasicGame {
    
    /** Entry point to the application. */
    public static void main(String[] args) {
        try {
            AppGameContainer container = new AppGameContainer(new FontPadTest());
            container.setDisplayMode(640, 480, false);
            container.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public FontPadTest() {
        super("GeomSpeedTest");
    }
    
    private Font font1, font2, font3, currentFont;
    private int pad = 0;
    
    public void init(GameContainer container) throws SlickException {
       currentFont = font1 = new AngelCodeFont("res/titlefont.fnt", "res/titlefont.png");
       font2 = new AngelCodeFont("res/arial.fnt", "res/arial.png");
       font3 = container.getDefaultFont();
    }
    
    public void render(GameContainer container, Graphics g) { 
        g.drawString("Change font set with number keys (1-3).", 10, 100);
        g.drawString("Change padding with space (Current Style: "+(pad+1)+")", 10, 120);
        
        g.setFont(currentFont);
        Font f = g.getFont();
        
        String str1 = "TestTest"; 
        float yoff1 = ((AngelCodeFont)f).getYOffset(str1); 
        g.setColor(Color.red); 
        if (pad==0)
            g.drawRect(100,20,f.getWidth(str1),f.getHeight(str1)-yoff1); 
        else 
            g.drawRect(100,20+yoff1,f.getWidth(str1),f.getHeight(str1)-yoff1*2);
        g.setColor(Color.white); 
        g.drawString(str1, 100, 20-yoff1); 
        
        String str2 = "Test Test"; 
        float yoff2 = ((AngelCodeFont)f).getYOffset(str2); 
        g.setColor(Color.red); 
        if (pad==0)
            g.drawRect(100,50,f.getWidth(str2),f.getHeight(str2)-yoff2); 
        else 
            g.drawRect(100,50,f.getWidth(str2),f.getLineHeight()-yoff2);
        g.setColor(Color.white); 
        g.drawString(str2, 100, 50-yoff2); 
        
    }
    
    public void update(GameContainer container, int delta) {
        
    }
    
    public void keyPressed(int k, char c) {
        if (k==Input.KEY_ESCAPE) {
            System.exit(0);
        } else if (c=='1') {
            currentFont = font1;
        } else if (c=='2') {
            currentFont = font2;
        } else if (c=='3') {
            currentFont = font3;
        } else if (k==Input.KEY_SPACE) {
            pad = pad==0 ? 1 : 0;
        }
    }
}