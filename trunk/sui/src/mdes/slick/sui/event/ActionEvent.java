package mdes.slick.sui.event;


/**
 * An event that indicates a component-defined event
 * has occurred.
 *
 * @author davedes
 * @since b.0.2
 */
public class ActionEvent extends EventObject {
    
    /** An event ID which signals a generic action. */
    public static final int ACTION_PERFORMED = 2000;
    
    /** The action command for this event. */
    private String command;
        
    /**
     * Creates a new event using the specified params.
     *
     * @param source the source that created the event
     * @param command the action command
     */
    public ActionEvent(Object source, String command) {
        super(source, ACTION_PERFORMED);
        this.command = command;
    }
    
    /**
     * Gets the action command for this event.
     *
     * @return the action command
     */
    public String getActionCommand() {
        return command;
    }
}
