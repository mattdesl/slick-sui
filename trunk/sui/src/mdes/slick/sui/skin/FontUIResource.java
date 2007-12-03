/*
 * FontUIResource.java
 *
 * Created on June 23, 2007, 7:56 PM
 */

package mdes.slick.sui.skin;

import org.newdawn.slick.Font;
import org.newdawn.slick.SlickException;

/**
 *
 * @author davedes
 */
public interface FontUIResource extends Font, UIResource {
    
    public static class AngelCodeFont extends org.newdawn.slick.AngelCodeFont 
                                implements FontUIResource, java.io.Serializable {
        
        public AngelCodeFont(String s, String s2) throws SlickException {
            super(s, s2);
        }
    }
}
