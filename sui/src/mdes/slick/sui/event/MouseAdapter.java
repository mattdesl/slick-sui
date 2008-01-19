package mdes.slick.sui.event;

/**
 * A convenience class for MouseListener which receives
 * mouse events. Implementation of these methods are optional.
 * 
 * 
 * @author davedes
 * @since b.0.2
 */
public class MouseAdapter implements MouseListener {
    
    /**
     * Notification that the mouse has moved.
     *
     * @param e the event associated with this listener
     */
    public void mouseMoved(MouseEvent e) {}
    
    /**
     * Notification that the mouse has been dragged.
     * Dragging the mouse will not call a mouseMoved 
     * event, and will instead call mouseDragged.
     *
     * @param e the event associated with this listener
     */
    public void mouseDragged(MouseEvent e) {}
    
    /**
     * Notification that the mouse has been pressed.
     *
     * @param e the event associated with this listener
     */
    public void mousePressed(MouseEvent e) {}
    
    /**
     * Notification that the mouse has been released.
     *
     * @param e the event associated with this listener
     */
    public void mouseReleased(MouseEvent e) {}
    
    /**
     * Notification that the mouse has entered the bounds
     * of the component.
     *
     * @param e the event associated with this listener
     */
    public void mouseEntered(MouseEvent e) {}
    
    /**
     * Notification that the mouse has exited the bounds
     * of the component.
     *
     * @param e the event associated with this listener
     */
    public void mouseExited(MouseEvent e) {}
}
