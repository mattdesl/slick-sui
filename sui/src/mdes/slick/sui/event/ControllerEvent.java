package mdes.slick.sui.event;


/**
 * A controller event, such as button pressed or released. 
 * The button returned from getButton() is an index, starting 
 * at 1. However, if this event was due to an UP, DOWN, RIGHT,
 * or LEFT action, the returned integer will conform to the
 * button constants defined in this class.
 *
 * @author davedes
 * @since b.0.2
 */
public class ControllerEvent extends EventObject {
    
    /** An event ID which signals a button press. */
    public static final int BUTTON_PRESSED = 1000;
    
    /** An event ID which signals a button release. */
    public static final int BUTTON_RELEASED = 1001;
    
    /** The constant for the controller UP action. */
    public static final int UP_BUTTON = -1;
    
    /** The constant for the controller DOWN action. */
    public static final int DOWN_BUTTON = -2;
    
    /** The constant for the controller LEFT action. */
    public static final int LEFT_BUTTON = -3;
    
    /** The constant for the controller RIGHT action. */
    public static final int RIGHT_BUTTON = -4;
    
    /** The constant for an unknown or non-controller button. */
    public static final int NOBUTTON = 0;
    
    /** The constant for the controller button 1. */
    public static final int BUTTON1 = 1;
    
    /** The constant for the controller button 2. */
    public static final int BUTTON2 = 2;
    
    /** The constant for the controller button 3. */
    public static final int BUTTON3 = 3;
    
    /** 
     * The button for this event, conforming to the button constants.
     */
    protected int button;
    
    /**
     * The controller that initiated this event.
     */
    protected int controller;
    
    /**
     * Creates a new event using the specified params.
     *
     * @param source the source that created the event
     * @param id the type of event we are dealing with (BUTTON_PRESSED or BUTTON_RELEASED)
     * @param controller the controller
     * @param button the button
     */
    public ControllerEvent(Object source, int id, int controller, int button) {
        super(source, id);
        this.button = button;
        this.controller = controller;
    }
    
    /**
     * Gets the controller that initiated this event. 
     *
     * @return the controller
     */
    public int getController() {
        return controller;
    }
    
    /**
     * The button for this event, conforming to the button constants.
     *
     * @return the button that was pressed or released
     */
    public int getButton() {
        return button;
    }
}