/*
 * ChangeEvent.java
 *
 * Created on November 25, 2007, 6:34 PM
 */

package mdes.slick.sui.event;

/**
 * Indicates that a component/object state has changed.
 *
 * @author davedes
 */
public class ChangeEvent extends EventObject {
    
    /**
     * Creates a new instance of ChangeEvent
     */
    public ChangeEvent(Object source) {
        super(source);
    }
    
}
