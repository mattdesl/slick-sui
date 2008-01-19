package mdes.slick.sui.event;

/**
 * A convenience class for ControllerListener which receives
 * controller events. Implementation of these methods are optional.
 * 
 * 
 * @author davedes
 * @since b.0.2
 */
public class ControllerAdapter implements ControllerListener {
    
    /**
     * Notification that a controller button has been pressed.
     *
     * @param e the event associated with this listener
     */
    public void controllerButtonPressed(ControllerEvent e) {}
    
    /**
     * Notification that a controller button has been released.
     *
     * @param e the event associated with this listener
     */
    public void controllerButtonReleased(ControllerEvent e) {}
}
