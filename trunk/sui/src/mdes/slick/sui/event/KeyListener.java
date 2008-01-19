package mdes.slick.sui.event;

/**
 * An interface to receive key events.
 *
 * @author davedes
 * @since b.0.2
 */
public interface KeyListener extends Listener {
    
    /**
     * Notification that a key has been pressed.
     *
     * @param e the event associated with this listener
     */
    public void keyPressed(KeyEvent e);
    
    /**
     * Notification that a key has been released.
     *
     * @param e the event associated with this listener
     */
    public void keyReleased(KeyEvent e);
}
