/*
 * SimpleArrowButton.java
 *
 * Created on November 12, 2007, 4:31 PM
 */

package mdes.slick.sui.skin.simple;

import mdes.slick.sui.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.GUIContext;

/**
 * A Button with an arrow painted on it. The arrow faces in the direction
 * specified (FACE_UP, FACE_LEFT, FACE_DOWN, or FACE_RIGHT). These buttons
 * are typically used in combo boxes and scroll bars.
 * <br /><br /> 
 * This class also has a utility method, 
 * {@link SimpleArrowButton#getScrollButtonAngle(ScrollBar, int)}, which determines
 * the arrow angle based on the scroll bar's orientation and the desired scroll direction.
 * 
 * 
 * 
 * 
 * 
 * @author Alexandre Vieira
 * @deprecated this class is being moved to mdes.slick.sui.skin.simple.SimpleArrowButton 
 *          and will be fixed up to suit nicer aligned arrows
 */
public class SimpleArrowButton extends Button {
    
    public static final float FACE_UP = 0.0f; 
    public static final float FACE_RIGHT = (float) (Math.PI / 2); 
    public static final float FACE_DOWN = (float) Math.PI; 
    public static final float FACE_LEFT = (float) -(Math.PI / 2);
    
    /* The arrow... */
    private Shape arrow;
    
    private float angle = FACE_UP;
    
    private float size = 5f;
        
    /**
     * Creates an <code>ArrowButton</code> which arrow faces the specified angle.
     *
     * @param angle The angle the arrow faces.
     */
    public SimpleArrowButton(float angle) {
        super();
        this.angle = angle;
        
        arrow = new Polygon(getTriangle(size));
        
        this.setSize(ScrollConstants.DEFAULT_SIZE, ScrollConstants.DEFAULT_SIZE);
        setPadding(3,3,5,5);
    }
    
    private float[] getTriangle(float size) {
        if (angle == FACE_UP) // ^
            return new float[] { 0f, size, size, 0f, size*2, size };
        else if (angle == FACE_LEFT) // <
            return new float[] { 0f, size, size, 0f, size, size*2 };
        else if (angle == FACE_RIGHT) // >
            return new float[] { 0f, 0f, size, size, 0f, size*2 };
        else // V
            return new float[] { 0f, 0f, size*2, 0f, size, size };
    }
    
    /**
     * Renders an adicional arrow on top of this button.
     *
     * @param container The container holding the game.
     * @param g         The graphics context to render onto.
     */
    protected void renderComponent(GUIContext container, Graphics g) {
        super.renderComponent(container, g);
        
        //TODO: move to appearance as SimpleArrowButton
        
        float x = getAbsoluteX();
        float y = getAbsoluteY();
        float w = getWidth();
        float h = getHeight();
        
        Padding pad = getPadding();
        
        arrow.setLocation(x+w/2-size,y+h/2-size);
        g.setColor(Sui.getTheme().getForeground());
        g.fill(arrow);
        
        /*float aw = pad.left+(w-pad.right);
        float ah = pad.top+(h-pad.bottom);
        float SIZE = 5;
        SlickCallable.enterSafeBlock();
        
                Sui.getTheme().getForeground().bind();
                
                //begin drawing the triangle
                GL11.glBegin(GL11.GL_TRIANGLES);
                
                    if (angle==FACE_UP) {
                        GL11.glVertex3f(x+w/2, y+pad.top, 0);
                        GL11.glVertex3f(x+pad.left, y+SIZE, 0);
                        GL11.glVertex3f(x+, 0);
                    }
                GL11.glEnd();
        
        SlickCallable.leaveSafeBlock();*/
    }
    
    /**
     * Determines the standard angle of the arrow based on the given scroll bar
     * information.
     *
     * @author davedes
     */
    public static float getScrollButtonAngle(ScrollBar bar, int direction) {
        int barOrientation = bar.getOrientation();
        boolean isInc = direction==ScrollBar.INCREMENT;
        if (barOrientation==ScrollBar.HORIZONTAL) {
            return isInc ? FACE_RIGHT : FACE_LEFT;
        } else
            return isInc ? FACE_UP : FACE_DOWN;
    }
}
