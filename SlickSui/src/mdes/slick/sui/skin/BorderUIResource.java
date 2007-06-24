/*
 * BorderUIResource.java
 *
 * Created on June 23, 2007, 2:31 PM
 */

package mdes.slick.sui.skin;

import org.newdawn.slick.Color;
import mdes.slick.sui.SuiBorder;

/**
 * 
 * @author davedes
 */
public interface BorderUIResource extends UIResource, SuiBorder {
    
    public static class LineBorder extends mdes.slick.sui.border.LineBorder 
                            implements BorderUIResource, java.io.Serializable {
        
        public LineBorder(float thickness, Color color) {
            super(thickness, color);
        }
        
        public LineBorder(Color color) {
            super(color);
        }
        
        public LineBorder() {
            super();
        }
        
        public LineBorder(float thickness) {
            super(thickness);
        }
    }
}
