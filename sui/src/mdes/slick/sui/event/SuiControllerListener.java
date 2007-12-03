package mdes.slick.sui.event;

/**
 * An interface to receive controller events.
 *
 * @author davedes
 * @since b.0.2
 */
public interface SuiControllerListener extends SuiListener {
    
    /**
     * Notification that a controller button has been pressed.
     *
     * @param e the event associated with this listener
     */
    public void controllerButtonPressed(SuiControllerEvent e);
    
    /**
     * Notification that a controller button has been released.
     *
     * @param e the event associated with this listener
     */
    public void controllerButtonReleased(SuiControllerEvent e);
}
