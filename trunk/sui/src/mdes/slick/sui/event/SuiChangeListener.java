/*
 * SuiChangeListener.java
 *
 * Created on November 25, 2007, 6:28 PM
 */

package mdes.slick.sui.event;

/**
 *
 * @author davedes
 */
public interface SuiChangeListener extends SuiListener {
    
    public void stateChanged(SuiChangeEvent e);
}
