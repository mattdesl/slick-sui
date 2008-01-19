package mdes.slick.sui.event;


/**
 * A keyboard event, such as key press or key release. Each
 * key event has a key code conforming to the Input key constants
 * and a char value for the key.
 *
 * @author davedes
 * @since b.0.2
 */
public class KeyEvent extends EventObject {
    
    /** The event ID for key pressed events. */
    public static final int KEY_PRESSED = 10;
    
    /** The event ID for key released events. */
    public static final int KEY_RELEASED = 11;
    
    /** The key code for this event. */
    protected int key;
    
    /** The key char for this event. */
    protected char chr;
    
    /**
     * 
     * Creates a new KeyEvent with the specified params.
     * 
     * 
     * @param source the source for this event
     * @param id the event id, either KEY_PRESSED or KEY_RELEASED
     * @param key the key code
     * @param chr the key char
     */
    public KeyEvent(Object source, int id, int key, char chr) {
        super(source, id);
        this.key = key;
        this.chr = chr;
    }
    
    /**
     * Gets the key code for this event.
     * 
     * @return the key code, conforming to the Input key constants
     */
    public int getKeyCode() {
        return key;
    }
    
    /**
     * Gets the key char for this event.
     *
     * @return the character of the key
     */
    public char getKeyChar() {
        return chr;
    }
    
    /**
     * Changes the key code for this event.
     *
     * @param key the new key code
     */
    public void setKeyCode(int key) {
        this.key = key;
    }
    
    /**
     * Changes the key char for this event.
     *
     * @param chr the new key char
     */
    public void setKeyChar(char chr) {
        this.chr = chr;
    }
}