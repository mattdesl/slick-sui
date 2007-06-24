/*
 * LineBorder.java
 *
 * Created on June 18, 2007, 3:13 PM
 */

package mdes.slick.sui.border;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.*;
import mdes.slick.sui.*;

/**
 *
 * @author davedes
 */
public class LineBorder implements SuiBorder {
    
    private float thickness;
    private Color color;
    
    /** Creates a new instance of LineBorder. */
    public LineBorder(float thickness, Color color) {
        this.thickness = thickness;
        this.color = color;
    }
    
    public LineBorder(float thickness) {
        this(thickness, new Color(Color.black));
    }
    
    public LineBorder(Color color) {
        this(1, color);
    }
    
    public LineBorder() {
        this(1);
    }
    
    public float getThickness() {
        return thickness;
    }

    public void setThickness(float thickness) {
        this.thickness = thickness;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    public void renderBorder(GUIContext ctx, Graphics g, SuiContainer comp) {
        Color c = g.getColor();
        float t = g.getLineWidth();
        g.setLineWidth(getThickness());
        g.setColor(getColor());
        g.drawRect(comp.getAbsoluteX(), comp.getAbsoluteY(), comp.getWidth(), comp.getHeight());
        g.setLineWidth(t);
        g.setColor(c);
    }
    
    public void updateBorder(GUIContext ctx, int delta, SuiContainer comp) {
    }
}
