/*
 * SuiChangeEvent.java
 *
 * Created on November 25, 2007, 6:34 PM
 */

package mdes.slick.sui.event;

/**
 * Indicates that a component/object state has changed.
 *
 * @author davedes
 */
public class SuiChangeEvent extends SuiEvent {
    
    /**
     * Creates a new instance of SuiChangeEvent
     */
    public SuiChangeEvent(Object source) {
        super(source);
    }
    
}
