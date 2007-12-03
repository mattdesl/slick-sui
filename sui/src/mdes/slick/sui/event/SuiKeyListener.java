package mdes.slick.sui.event;

/**
 * An interface to receive key events.
 *
 * @author davedes
 * @since b.0.2
 */
public interface SuiKeyListener extends SuiListener {
    
    /**
     * Notification that a key has been pressed.
     *
     * @param e the event associated with this listener
     */
    public void keyPressed(SuiKeyEvent e);
    
    /**
     * Notification that a key has been released.
     *
     * @param e the event associated with this listener
     */
    public void keyReleased(SuiKeyEvent e);
}
