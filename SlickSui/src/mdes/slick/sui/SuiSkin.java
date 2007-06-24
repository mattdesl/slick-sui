/*
 * SuiRenderer.java
 *
 * Created on May 31, 2007, 5:38 PM
 */

package mdes.slick.sui;

import org.newdawn.slick.*;
import mdes.slick.sui.skin.*;
import org.newdawn.slick.gui.GUIContext;
import org.lwjgl.opengl.*;

/**
 * A Sui skin defines the base layer for 
 * rendering components. This doesn't render any 
 * specifics for the component, only the shape.
 * 
 * @author davedes
 */
public interface SuiSkin {
        
    public String getName();
    
    public void install() throws SlickException;
    public void uninstall() throws SlickException;
    
    public SuiSkin.ContainerUI getContainerUI();
    public SuiSkin.CheckBoxUI getCheckBoxUI();
    public SuiSkin.WindowUI getWindowUI();
    public SuiSkin.ButtonUI getButtonUI();
    public SuiSkin.ToolTipUI getToolTipUI();
    public SuiSkin.LabelUI getLabelUI();
    public SuiSkin.TitleBarUI getTitleBarUI();
    
    public static abstract class ContainerUI {
        public void installUI(SuiContainer c, SuiTheme theme) {}
        public void uninstallUI(SuiContainer c, SuiTheme theme) {}
        public void renderUI(Graphics g, SuiTheme theme, SuiContainer c) {}
        public void updateUI(int delta, SuiTheme theme, SuiContainer c) {}
        
        public boolean contains(SuiContainer c, float x, float y) {
            return c.inside(x,y);
        }
    }
    
    public static abstract class CheckBoxUI extends ButtonUI {
        public abstract Image getDefaultImage();
    }
    
    public static abstract class WindowUI extends ContainerUI {
    }
    
    public static abstract class TitleBarUI extends LabelUI {
    }
    
    public static abstract class ButtonUI extends LabelUI {
    }
    
    public static abstract class ToolTipUI extends ContainerUI {
    }
    
    public static abstract class LabelUI extends ContainerUI {
    }
}

/**public static Color TRANSPARENT_COLOR = new Color(0f,0f,0f,0f);
    
    public SuiRenderer() {
    }
    
    void fillArc(float x1,float y1,float width,float height,int segments,float start,float end) {
        while (end < start) {
            end += 360;
        }
        
        float cx = x1 + (width/2.0f);
        float cy = y1 + (height/2.0f);
        
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
            int step = 360 / segments;

            GL11.glVertex2f(cx,cy);

            for (int a=(int) start;a<(int) (end+step);a+=step) {
                float ang = a;
                if (ang > end) {
                    ang = end;
                }

                float x = (float) (cx+(FastTrig.cos(Math.toRadians(ang))*width/2.0f));
                float y = (float) (cy+(FastTrig.sin(Math.toRadians(ang))*height/2.0f));

                GL11.glVertex2f(x,y);
            }
        GL11.glEnd();
        
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
            GL11.glVertex2f(cx,cy);
            if (end != 360) {
                end -= 10;
            }

            for (int a=(int) start;a<(int) (end+step);a+=step) {
                float ang = a;
                if (ang > end) {
                    ang = end;
                }

                float x = (float) (cx+(FastTrig.cos(Math.toRadians(ang+10))*width/2.0f));
                float y = (float) (cy+(FastTrig.sin(Math.toRadians(ang+10))*height/2.0f));

                GL11.glVertex2f(x,y);
            }
        GL11.glEnd();
    }
    
    void drawArc(float x1, float y1, float width, float height,int segments,float start,float end) {
        while (end < start) {
            end += 360;
        }
        
        float cx = x1 + (width/2.0f);
        float cy = y1 + (height/2.0f);
        
        GL11.glBegin(GL11.GL_LINE_STRIP);
            int step = 360 / segments;

            for (int a=(int) start;a<(int) (end+step);a+=step) {
                float ang = a;
                if (ang > end) {
                    ang = end;
                }
                float x = (float) (cx+(FastTrig.cos(Math.toRadians(ang))*width/2.0f));
                float y = (float) (cy+(FastTrig.sin(Math.toRadians(ang))*height/2.0f));

                GL11.glVertex2f(x,y);
            }
        GL11.glEnd();
    }
    
    void drawLine(float x1,float y1, float x2, float y2) {
        GL11.glBegin(GL11.GL_LINES);
            GL11.glVertex2f(x1,y1);
            GL11.glVertex2f(x2,y2);
        GL11.glEnd();
    }
    
    void fillRect(float x1,float y1,float width,float height) {
        GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2f(x1,y1);
            GL11.glVertex2f(x1+width,y1);
            GL11.glVertex2f(x1+width,y1+height);
            GL11.glVertex2f(x1,y1+height);
        GL11.glEnd();
    }
    
    void drawRect(float x1,float y1,float width,float height) {
        GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex2f(x1,y1);
            GL11.glVertex2f(x1+width,y1);
            GL11.glVertex2f(x1+width,y1+height);
            GL11.glVertex2f(x1,y1+height);
            GL11.glVertex2f(x1,y1);
        GL11.glEnd();
    }
    
    void drawRoundRect(float x, float y, float width, float height, int cornerRadius, int segs) {
        if (cornerRadius<0)
            throw new IllegalArgumentException("corner radius must be > 0");
        
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
        
        if (cornerRadius==0) {
            drawRect(x, y, width, height);
            return;
        }
        
        int mr = (int)Math.min(width, height)/2;
        // make sure that w & h are larger than 2*cornerRadius
        if (cornerRadius > mr) {
            cornerRadius = mr;
        }
        
        //top line
        drawLine(x+cornerRadius, y, x+width-cornerRadius, y);
        //left line
        drawLine(x, y+cornerRadius, x, y+height-cornerRadius);
        //right line
        drawLine(x+width, y+cornerRadius, x+width, y+height-cornerRadius);
        //bottom line
        drawLine(x+cornerRadius, y+height, x+width-cornerRadius, y+height);
        
        float d = cornerRadius*2;
        //bottom right - 0, 90
        drawArc(x+width-d, y+height-d, d, d, segs, 0, 90);
        //bottom left - 90, 180
        drawArc(x, y+height-d, d, d, segs, 90, 180);
        //top right - 270, 360
        drawArc(x+width-d, y, d, d, segs, 270, 360);
        //top left - 180, 270
        drawArc(x, y, d, d, segs, 180, 270);
        
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
    }
    
    void fillRoundRect(float x, float y, float width, float height, int cornerRadius, int segs) {
        if (cornerRadius<0)
            throw new IllegalArgumentException("corner radius must be > 0");
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
            //top left
            roundrect_vertices(segs, cornerRadius, x + cornerRadius, y + cornerRadius, 180, 270);

            //top right
            roundrect_vertices(segs, cornerRadius, x + width - cornerRadius, y + cornerRadius, 270, 360);

            //bottom right
            roundrect_vertices(segs, cornerRadius, x + width - cornerRadius, y + height - cornerRadius, 0, 90);

            //bottom left
            roundrect_vertices(segs, cornerRadius, x + cornerRadius, y + height - cornerRadius, 90, 180);
        GL11.glEnd();
    }
    
    void roundrect_vertices(int numberOfSegments, float radius, float cx, float cy, float start, float end) {
        int step = 360 / numberOfSegments;
        
        int count = 1;
        float total=end+step;
        int mid = (int)((total-start)/step - 1);
        
        for (float a=start;a<=total;a+=step, count++) {
            float ang = a;
            if (ang > end) {
                ang = end;
            }
            float x = (float) (cx + (FastTrig.cos(Math.toRadians(ang)) * radius));
            float y = (float) (cy + (FastTrig.sin(Math.toRadians(ang)) * radius));
            
            //if (count==mid && ec!=null)
                //bind color?
            
            GL11.glVertex2f(x, y);
        }
    }
    
    public void fillGradientRect(Color start, Color end, float x1, float y1, int width, int height) {
        Texture.bindNone();
        if (start!=null)
            start.bind();
        else
            TRANSPARENT_COLOR.bind();
        
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x1,y1);
        GL11.glVertex2f(x1+width,y1);
        if (end!=null)
            end.bind();
        else
            TRANSPARENT_COLOR.bind();
        GL11.glVertex2f(x1+width,y1+height);
        GL11.glVertex2f(x1,y1+height);
        GL11.glEnd();
    }*/

/**
     * Fills a rectangle using GL to draw a gradient. Sui uses gradients
     * for button and window rendering, this is mainly meant for internal use.
     *
     * @param start the color the gradient will start with (null for transparent)
     * @param end the color the gradient will end with (null for transparent)
     * @param x1 the x position of the rectangle
     * @param y1 the y position of the rectangle
     * @param width the width of the rectangle
     * @param height the height of the rectangle
    
    protected void fillGradientRect(Color start, Color end, float x1, float y1, int width, int height) {
        Texture.bindNone();
        if (start!=null)
            start.bind();
        else
            TRANSPARENT_COLOR.bind();
        
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x1,y1);
        GL11.glVertex2f(x1+width,y1);
        if (end!=null)
            end.bind();
        else
            TRANSPARENT_COLOR.bind();
        GL11.glVertex2f(x1+width,y1+height);
        GL11.glVertex2f(x1,y1+height);
        GL11.glEnd();
    } */
