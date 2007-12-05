package mdes.slick.sui.event;


/**
 * A mouse wheel event which holds the amount the wheel
 * has changed since the last event.
 *
 * @author davedes
 * @since b.0.2
 */
public class SuiMouseWheelEvent extends SuiEvent {
    
    /** An event ID for the mouse wheel event. */
    public static final int MOUSE_WHEEL = SuiMouseEvent.LAST_EVENT+1;
    
    /** The amount of change. */
    private int change;
    
    /** 
     * Creates a new mouse wheel event with the specified params.
     *
     * @param source the source
     * @param change the amount of change
     */
    public SuiMouseWheelEvent(Object source, int change) {
        super(source, MOUSE_WHEEL);
        this.change = change;
    }
    
    /** 
     * Gets the amount of change.
     * 
     * @return the amount that the mouse wheel has changed
     */
    public int getAmountChanged() {
        return change;
    }
}
