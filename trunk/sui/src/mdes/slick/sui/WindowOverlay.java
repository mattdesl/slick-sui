/*
 * WindowOverlay.java
 *
 * Created on January 2, 2008, 2:20 PM
 */

package mdes.slick.sui;

/**
 * A tagging interface that is used by layerable window-like overlays. This is implemented
 * to make custom window components without using SuiWindow.
 * <p>
 * A window is active when one of its children has the focus. A window that is layerable will
 * change its Z index when focused, bringing the window to the "front". The default layer is 
 * the layer at which the window should be at. Most windows will use MODAL_LAYER. When a window is
 * focused, 
 * 
 * @author davedes
 */
public interface WindowOverlay {
    public void setActive(boolean b);
    public boolean isActive();
    public boolean isLayerable();
    public int getDefaultLayer();
}
