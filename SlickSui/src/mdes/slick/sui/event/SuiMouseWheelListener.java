package mdes.slick.sui.event;

/**
 * An interface to receive mouse wheel events.
 *
 * @author davedes
 * @since b.0.2
 */
public interface SuiMouseWheelListener extends SuiListener {
    
    /**
     * Notification that the mouse wheel has changed.
     *
     * @param e the event associated with this listener
     */
    public void mouseWheelMoved(SuiMouseWheelEvent e);
}
