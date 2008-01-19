package mdes.slick.sui.event;


/**
 * The base class for SuiEvents.
 * 
 * @author davedes
 * @since b.0.2
 */
public class EventObject implements java.io.Serializable {
    
    /** The source object that created this event. */
    protected Object source;
    
    /** The unique ID for this event. */
    protected int id;
    
    /** If the no ID is specified, an ID_UNDEFINED id will be used. */
    public static final int ID_UNDEFINED = 0;
        
    /** 
     * Creates a new event with the specified params. IDs passed
     * should not be equal to 0 unless an ID_UNDEFINED is intended.
     *
     * @param source the object that created this event
     * @param id the id for this event
     */
    public EventObject(Object source, int id) {
        this.id = id;
        this.source = source;
    }
    
    /**
     * Creates a new event with the specified source.
     * All events created using this constructor will have
     * an ID of 0, ID_UNDEFINED.
     *
     * @param source the container which created this event
     */
    public EventObject(Object source) {
        this(source, 0);
    }
    
    /** 
     * Gets the unique ID for this event.
     *
     * @return the event ID
     */
    public int getID() {
        return id;
    }
    
    /**
     * Gets the source object for this event.
     *
     * @return the object that created this event
     */
    public Object getSource() {
        return source;
    }
}