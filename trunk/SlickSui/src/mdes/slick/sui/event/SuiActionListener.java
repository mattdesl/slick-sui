package mdes.slick.sui.event;

/**
 * A listener to receive action events (such as button presses).
 *
 * @author davedes
 * @since b.0.2
 */
public interface SuiActionListener extends SuiListener {
    
    /**
     * Notification that the action has occurred.
     *
     * @param e the event associated with this listener
     */
    public void actionPerformed(SuiActionEvent e);
}
