/*
 * TexturePartsTest.java
 *
 * Created on January 19, 2008, 11:56 PM
 */

package mdes.slick.sui.test;

import java.util.StringTokenizer;
import org.newdawn.slick.*;
import org.newdawn.slick.opengl.*;

/**
 *
 * @author davedes
 */
public class TexturePartsTest extends BasicGame {
    
    public static void main(String[] args) throws Exception {
        AppGameContainer app = new AppGameContainer(new TexturePartsTest());
        app.setDisplayMode(800,600,false);
        app.start();
    }
    
    /** Creates a new instance of TexturePartsTest */
    public TexturePartsTest() {
        super("TexturePartsTest");
    }
    
    private int x = 300;
    private int y = 120;
    private int width, defWidth;
    private int height, defHeight;
    private Parts parts = null;
    private Sheet sheet = null;
    private Mask mask = null;
    private boolean hover = false;
    private int sizeMode = 0;
    
    public void init(GameContainer container) throws SlickException {
        container.getGraphics().setBackground(Color.lightGray);
        
        mask = new Mask("res/test/button_mask.png", new Color(255,0,0));
        sheet = new Sheet("res/test/button_ui.png");
        parts = new Parts();
        parts.topleft = sheet.parse("0 0 13 13");
        parts.topright = sheet.parse("191 0 13 13");
        parts.botleft = sheet.parse("0 123 13 13");
        parts.botright = sheet.parse("191 123 13 13");
        parts.bot = sheet.parse("13 123 178 13");
        parts.top = sheet.parse("13 0 178 13");
        parts.left = sheet.parse("0 13 13 110");
        parts.right = sheet.parse("191 13 13 110");
        parts.center = sheet.parse("13 13 178 110");
        parts.area = sheet.image;
        width = defWidth = sheet.image.getWidth();
        height = defHeight = sheet.image.getHeight();
    }
    
    public void update(GameContainer container, int delta) throws SlickException {
        Input in = container.getInput();
        int mx = in.getMouseX();
        int my = in.getMouseY();
        //in bounds of component
        if (mx>=x && my>=y && mx<=x+width && my<=y+height) {
            int imgx = mx-x;
            int imgy = my-y;
            //hover = mask.isColor(imgx, imgy, width, height);
        } else {
            hover = false;
        }
         
        if (in.isKeyPressed(Input.KEY_ESCAPE))
            container.exit();
    }
    
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.drawString("Press space to toggle size change", 10, 25);
        if (hover)
            g.drawString("Mouse over!", 10, 40);
        
        g.drawString("Individual Parts:", x, y-15);
        parts.draw1(g, x, y, width, height, 0);
        
        
        int px = 40;
        g.drawString("Full Image:", px, y-15);
        parts.area.draw(px, y);
        
        g.drawString("Exploded:", px, y+defHeight+5);
        parts.draw1(g, px, y+defHeight+20, defWidth, defHeight, 5);
        
        //parts.area.draw(x, y, width, height);
        
        /*for (int x1=0; x1<10; x1++) {
            for (int y1=0; y1<10; y1++) {
                parts.draw1(g, 50+x1*width, 50+y1*height, width, height, 0);
            }
        }*/
    }
    
    public void keyPressed(int key, char c) {
        if (key == Input.KEY_SPACE) {
            sizeMode++;
            if (sizeMode==1) {
                width = defWidth/2;
                height = defHeight/2;
            } else if (sizeMode==2) {
                width = defWidth*2;
                height = defHeight*2;
            } else {
                width = defWidth;
                height = defHeight;
                sizeMode = 0;
            }
        }
    }
    
    protected class Parts {
        Image topleft, topright, botleft, botright, top, bot, left, right, center, area;
        
        //draw1 -> draws corners at normal size but scales center and mids
        //draw2 -> draws it all at normal size and then scales with g.scale
        public void draw1(Graphics g, int x, int y, int width, int height, int space) {
            //corners
            int cw = topleft.getWidth()+space;
            int ch = topleft.getHeight()+space;
            
            int topheight = top.getHeight();
            int leftwidth = left.getWidth();
            
            topleft.draw(x, y);
            botleft.draw(x, y+height-ch+space);
            topright.draw(x+width-cw+space, y);
            botright.draw(x+width-cw+space, y+height-ch+space);
            top.draw(x+cw, y, width-(cw*2), topheight);
            bot.draw(x+cw, y+height-ch+space, width-(cw*2), topheight);
            left.draw(x, y+ch, leftwidth, height-(ch*2));
            right.draw(x+width-cw+space, y+ch, leftwidth, height-(ch*2));
            center.draw(x+cw, y+cw, width-(cw*2), height-(ch*2));
        }
    }
        
    protected class Sheet {
        
        Image image;
        
        public Sheet(String ref) throws SlickException {
            this.image = new Image(ref);
        }
        
        public Image getSubImage(int x, int y, int width, int height) {
            return image.getSubImage(x, y, width, height);
        }
        
        public Image parse(String rect) {
            StringTokenizer tk = new StringTokenizer(rect);
            int x = Integer.parseInt(tk.nextToken());
            int y = Integer.parseInt(tk.nextToken());
            int width = Integer.parseInt(tk.nextToken());
            int height = Integer.parseInt(tk.nextToken());
            return getSubImage(x, y, width, height);
        }
    }
    
    protected class Mask {
        private Color color;
        private int _r, _g, _b;
        private Image image;
        private byte[] data = null;
        private int width;
        private int offsetMult;
        
        public Mask(String ref, Color color) throws SlickException {
            this.image = new Image(ref);
            this.color = color;
            this._r = color.getRedByte();
            this._g = color.getGreenByte();
            this._b = color.getGreenByte();
            //System.out.println(image.getColor(25, 25));
            
            Texture texture = image.getTexture();
            data = texture.getTextureData();
            width = texture.getTextureWidth();
            offsetMult = texture.hasAlpha() ? 4 : 3;
        }
        
        /**
         * Translate an unsigned int into a signed integer
         *
         * @param b The byte to convert
         * @return The integer value represented by the byte
         */
        private int translate(byte b) {
            if (b < 0)
                return 256 + b;
            return b;
        }
        
        private boolean isColor(int x, int y) {
            int offset = x + (y * width);
            offset *= offsetMult;

            int r = translate(data[offset]);
            int g = translate(data[offset+1]);
            int b = translate(data[offset+2]);
                       
            return r==_r && g==_g && b==_b;
        }
    }
}
