/*
 * SuiBorder.java
 *
 * Created on June 18, 2007, 3:10 PM
 */

package mdes.slick.sui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;

/**
 * A simple border.
 * 
 * @author davedes
 */
public interface SuiBorder extends java.io.Serializable {
    
    public void renderBorder(GUIContext c, Graphics g, SuiContainer comp);    
    public void updateBorder(GUIContext c, int delta, SuiContainer comp);
}
