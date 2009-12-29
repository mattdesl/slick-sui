package mdes.slick.sui.event;

import mdes.slick.sui.Component;

/**
 * An interface to receive resize event
 *
 * @author Olivier Arteau
 */

public interface ResizeListener  extends Listener {
    /**
     * Notification that the component has change size
     *
     * @param oldWidth
     * 		The old width of the component
     * 
     * @param oldHeight
     * 		The old height of the component
     */
    
    public void sizeChanged(Component c, float oldWidth, float oldHeight);
}
