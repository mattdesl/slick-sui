/*
 * SuiPopup.java
 *
 * Created on June 1, 2007, 10:37 PM
 */

package mdes.slick.sui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;

/**
 * A container which will "pop up" above other components. Add a SuiPopup
 * to a display to allow it to pop up above all other components in a scene. 
 * A SuiPopup component can be used for dialogs, tooltips, popup menus, etc.
 * <p>
 * Clipping will be recalculated to display the popup without clipping it, and 
 * the initial z-index will be set to Sui.POPUP_LAYER. Children of the popup
 * will be clipped normally, based on the popup's bounds.
 * 
 * @author davedes
 */
public class SuiPopup extends SuiContainer {
    
    /** Creates a new instance of SuiPopup. */
    public SuiPopup() {
        setClipEnabled(false);
        setZIndex(SuiContainer.POPUP_LAYER);
    }
    
    void render(GUIContext c, Graphics g) {
        //get old clip
        float cx=clip.x, cy=clip.y, cw=clip.width, ch=clip.height;
        
        //set clip to this component
        g.setClip((int)getAbsoluteX(), (int)getAbsoluteY(), (int)getWidth(), (int)getHeight());
        
        //render component & children
        super.render(c, g);
        
        //reset clip to previous values
        g.setClip((int)cx, (int)cy, (int)cw, (int)ch);
    }
}
