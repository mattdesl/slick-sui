/*
 * ColorUIResource.java
 *
 * Created on June 10, 2007, 11:36 PM
 */

package mdes.slick.sui.skin;

import org.newdawn.slick.Color;

/**
 * 
 * @author davedes
 */
public class ColorUIResource extends Color implements java.io.Serializable, UIResource {
    
    public ColorUIResource(Color c) {
        super(c);
    }
    
    public ColorUIResource(float r, float g, float b, float a) {
        super(r,g,b,a);
    }
    
    public ColorUIResource(float r, float g, float b) {
        super(r,g,b);
    }
    
    public ColorUIResource(int r, int g, int b, int a) {
        super(r,g,b,a);
    }
    
    public ColorUIResource(int r, int g, int b) {
        super(r,g,b);
    }
}
