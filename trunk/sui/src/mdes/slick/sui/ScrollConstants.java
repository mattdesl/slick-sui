package mdes.slick.sui;

//TODO: move this class to SuiScrollBar

/** 
 * This class contains a set of constants used with scroll bars and scrollable
 * components (such as sliders).
 *
 * @author davedes
 */
public interface ScrollConstants {
    
    public static final int DEFAULT_SIZE = 16;
    
    /** 
     * A constant for the "increment" direction of a SuiScrollBar (1).
     * This direction usually deals with the up (for vertical) and right (for 
     * horizontal) buttons on a scroll bar.
     */
    public static final int INCREMENT = 1;
    
    /**
     * A constant for the "decrement" direction of a SuiScrollBar (-1).
     * This direction usually deals with the down (for vertical) and left (for
     * horizontal) buttons on a scroll bar.
     */
    public static final int DECREMENT = -1;
    
    /** A constant to indicate horizontal orientation of a SuiScrollBar. */
    public static final int HORIZONTAL = 20;
    
    /** A constant to indicate vertical orientation of a SuiScrollBar. */
    public static final int VERTICAL = 21;
    
}