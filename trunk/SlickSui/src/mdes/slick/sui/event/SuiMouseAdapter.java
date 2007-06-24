package mdes.slick.sui.event;

/**
 * A convenience class for SuiMouseListener which receives
 * mouse events. Implementation of these methods are optional.
 *
 * @author davedes
 * @since b.0.2
 */
public class SuiMouseAdapter implements SuiMouseListener {
    
    /**
     * Notification that the mouse has moved.
     *
     * @param e the event associated with this listener
     */
    public void mouseMoved(SuiMouseEvent e) {}
    
    /**
     * Notification that the mouse has been dragged.
     * Dragging the mouse will not call a mouseMoved 
     * event, and will instead call mouseDragged.
     *
     * @param e the event associated with this listener
     */
    public void mouseDragged(SuiMouseEvent e) {}
    
    /**
     * Notification that the mouse has been pressed.
     *
     * @param e the event associated with this listener
     */
    public void mousePressed(SuiMouseEvent e) {}
    
    /**
     * Notification that the mouse has been released.
     *
     * @param e the event associated with this listener
     */
    public void mouseReleased(SuiMouseEvent e) {}
    
    /**
     * Notification that the mouse has entered the bounds
     * of the component.
     *
     * @param e the event associated with this listener
     */
    public void mouseEntered(SuiMouseEvent e) {}
    
    /**
     * Notification that the mouse has exited the bounds
     * of the component.
     *
     * @param e the event associated with this listener
     */
    public void mouseExited(SuiMouseEvent e) {}
}
