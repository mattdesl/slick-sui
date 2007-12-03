package mdes.slick.sui.event;

/**
 * A convenience class for SuiKeyListeners which receives
 * key events. Implementation of these methods are optional.
 *
 * @author davedes
 * @since b.0.2
 */
public class SuiKeyAdapter implements SuiKeyListener{
    
    /**
     * Notification that a key has been pressed.
     *
     * @param e the event associated with this listener
     */
    public void keyPressed(SuiKeyEvent e) {}
    
    /**
     * Notification that a key has been released.
     *
     * @param e the event associated with this listener
     */
    public void keyReleased(SuiKeyEvent e) {}
}
