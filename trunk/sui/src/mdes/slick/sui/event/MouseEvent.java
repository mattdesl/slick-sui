package mdes.slick.sui.event;


/**
 * A mouse event, including motion and press/release. 
 * Mouse events will hold the absolute and local x and y
 * positions and often the button that is pressed.
 *
 * @author davedes
 * @since b.0.2
 */
public class MouseEvent extends EventObject {
    
    /** An event ID for the mouse moved event. */
    public static final int MOUSE_MOVED = 100;
    
    /** An event ID for the mouse dragged event. */
    public static final int MOUSE_DRAGGED = 101;
    
    /** An event ID for the mouse pressed event. */
    public static final int MOUSE_PRESSED = 102;
    
    /** An event ID for the mouse entered event. */
    public static final int MOUSE_ENTERED = 103;
    
    /** An event ID for the mouse exited event. */
    public static final int MOUSE_EXITED = 104;
    
    /** An event ID for the mouse released event. */
    public static final int MOUSE_RELEASED = 105;
    
    /** The last event ID, used internally. */
    public static final int LAST_EVENT = MOUSE_RELEASED;
    
    /** A constant for mouse button 1, index 0. */
    public static final int BUTTON1 = 0;
    
    /** A constant for mouse button 2, index 1. */
    public static final int BUTTON2 = 1;
    
    /** A constant for mouse button 3, index 2. */
    public static final int BUTTON3 = 2;
    
    /** A constant for no mouse button or unknown mouse button, index -1. */
    public static final int NOBUTTON = -1;
    
    /** The button for this event. */
    private int button;
    
    /** The local x position for this event. */
    private int x;
    
    /** The local y position for this event. */
    private int y;
    
    /** The absolute x position for this event. */
    private int absx;
    
    /** The absolute y position for this event. */
    private int absy;
    
    /** The old local y position for this event. */
    private int ox;
    
    /** The old local y position for this event. */
    private int oy;
            
    /**
     * Creates a new mouse event with the specified params.
     *
     * @param source the source
     * @param id the event type id
     * @param button the button index or -1 if it's unknown/undefined
     * @param x the x position of the mouse when the event occurred
     * @param y the y position of the mouse when the event occurred
     * @param ox the old x position
     * @param oy the old y position
     * @param absx the absolute x position to the game container
     * @param absy the absolute y position to the game container 
     */
    public MouseEvent(Object source, int id, int button, 
                        int x, int y, int ox, int oy, int absx, int absy) {
        super(source, id);
        this.button = button;
        this.x = x;
        this.y = y;
        this.ox = ox;
        this.oy = oy;
        this.absx = absx;
        this.absy = absy;
    }
    
    /**
     * Creates a new mouse event with the specified params. The
     * old x and y values will be equal to the x and y values using
     * this constructor.
     *
     * @param source the source
     * @param id the event type id
     * @param button the button index or -1 if it's unknown/undefined
     * @param x the x position of the mouse when the event occurred
     * @param y the y position of the mouse when the event occurred
     * @param absx the absolute x position to the game container
     * @param absy the absolute y position to the game container 
     */
    public MouseEvent(Object source, int id, int button,
                        int x, int y, int absx, int absy) {
        this(source, id, button, x, y, x, y, absx, absy);
    }
    
    /**
     * Creates a new mouse event with the specified params. The
     * old x and y values will be equal to the x and y values using
     * this constructor. Also, the button index will be -1, equal to
     * NOBUTTON.
     *
     * @param source the source
     * @param id the event type id
     * @param x the x position of the mouse when the event occurred
     * @param y the y position of the mouse when the event occurred
     * @param absx the absolute x position to the game container
     * @param absy the absolute y position to the game container 
     */
    public MouseEvent(Object source, int id, int x, int y, 
                        int absx, int absy) {
        this(source, id, NOBUTTON, x, y, absx, absy);
    }
    
    /**
     * Gets the old x value before the mouse position was changed.
     *
     * @return the old x position, local to the parent container
     */
    public int getOldX() {
        return ox;
    }
    
    /**
     * Gets the old y value before the mouse position was changed.
     *
     * @return the old y position, local to the parent container
     */
    public int getOldY() {
        return oy;
    }
    
    /**
     * Gets the absolute x position relative to the GameContainer.
     *
     * @return the absolute x position
     */
    public int getAbsoluteX() {
        return absx;
    }
    
    /**
     * Gets the absolute y position relative to the GameContainer.
     *
     * @return the absolute y position
     */
    public int getAbsoluteY() {
        return absy;
    }
    
    /**
     * Gets the x position relative to the parent container.
     *
     * @return the x position in the container's local space
     */
    public int getX() {
        return x;
    }
    
    /**
     * Gets the y position relative to the parent container.
     *
     * @return the y position in the container's local space
     */
    public int getY() {
        return y;
    }
    
    /**
     * Gets the button that initiated the event, or NOBUTTON if no
     * mouse button was used.
     *
     * @return the button index (starting at 0, 
     *              conforming to the button 
     *              constants BUTTON1, BUTTON2 and BUTTON3)
     *          or NOBUTTON (-1) if it's unknown
     */
    public int getButton() {
        return button;
    }
}