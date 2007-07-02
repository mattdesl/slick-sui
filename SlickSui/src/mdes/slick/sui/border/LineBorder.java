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
 * LineBorder is a simple class which will draw a rectangle around the 
 * component bounds, using the specified color and line thickness.
 * @author davedes
 */
public class LineBorder implements SuiBorder {
    
    /**
     * The thickness for this border.
     */
    private float thickness;
    /**
     * The color for this border.
     */
    private Color color;
    
    /**
     * Creates a new instance of LineBorder with the specified thickness and Color.
     * @param thickness the thickness of the line (Graphics.setLineThickness)
     * @param color the color to draw the border with
     */
    public LineBorder(float thickness, Color color) {
        this.thickness = thickness;
        this.color = color;
    }
    
    /**
     * Creates a new instance of LineBorder with the specified thickness and a black Color.
     * @param thickness the thickness of the line (Graphics.setLineThickness)
     */
    public LineBorder(float thickness) {
        this(thickness, new Color(Color.black));
    }
    
    /**
     * Creates a new instance of LineBorder with the specified Color and a thickness of 1.
     * @param color the color to draw the border with
     */
    public LineBorder(Color color) {
        this(1, color);
    }
    
    /**
     * Creates a new instance of LineBorder with a line thickness of 1 and a black color.
     */
    public LineBorder() {
        this(1);
    }
    
    /**
     * Returns the thickness for this border.
     * @return the thickness
     */
    public float getThickness() {
        return thickness;
    }

    /**
     * Sets the thickness for this border.
     * @param thickness the thickness
     */
    public void setThickness(float thickness) {
        this.thickness = thickness;
    }

    /**
     * Returns the color for this border.
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color for this border.
     * @param color the color
     */
    public void setColor(Color color) {
        this.color = color;
    }
    
    /**
     * Called to render the border for the specified container.
     * @param ctx the Slick context
     * @param g the graphics context
     * @param comp the container to render the border over
     */
    public void renderBorder(GUIContext ctx, Graphics g, SuiContainer comp) {
        Color c = g.getColor();
        float t = g.getLineWidth();
        g.setLineWidth(getThickness());
        g.setColor(getColor());
        g.drawRect(comp.getAbsoluteX(), comp.getAbsoluteY(), comp.getWidth(), comp.getHeight());
        g.setLineWidth(t);
        g.setColor(c);
    }
    
    /**
     * Called to update the border for the specified container. Not used.
     * @param ctx the Slick context
     * @param delta the delta time to update by
     * @param comp the container to render the border over
     */
    public void updateBorder(GUIContext ctx, int delta, SuiContainer comp) {
    }
}
