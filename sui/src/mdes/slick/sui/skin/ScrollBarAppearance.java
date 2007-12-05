/*
 * ScrollBarAppearance.java
 *
 * Created on November 12, 2007, 2:32 PM
 */

package mdes.slick.sui.skin;

import mdes.slick.sui.ScrollConstants;
import mdes.slick.sui.SuiButton;
import mdes.slick.sui.SuiScrollBar;
import mdes.slick.sui.SuiSlider;

/**
 * ScrollBarAppearance defines the appearance for a scroll bar.
 * 
 * @author Matt
 */
public interface ScrollBarAppearance extends ComponentAppearance, ScrollConstants {
    
    /**
     * Used by subclasses to create the increment/decrement scroll buttons.
     * Direction is one of INCREMENT (up/right buttons) or DECREMENT (down/left buttons). 
     * Usually a scroll button has a visual key (such as an arrow) as to which direction
     * will be scrolled by pressing the button.
     * 
     * @param bar the scroll bar parent
     * @param direction the direction the button will scroll
     * @return the new scroll button
     */
    public SuiButton createScrollButton(SuiScrollBar bar, int direction);
    
    /**
     * Creates a slider used by the scroll bar as a "track" with a thumb button.
     * If the returned SuiSlider's orientation doesn't match that of the given
     * SuiScrollBar, an IllegalArgumentException will be thrown when this appearance
     * is installed on a scroll bar.
     *
     * @param bar the bar to create the slider for
     * @param orientation the orientation (matching that of the passed scroll bar) that
     *      the slider should use
     * @return the new slider
     */
    public SuiSlider createSlider(SuiScrollBar bar, int orientation);
}
