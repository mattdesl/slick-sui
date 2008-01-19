/*
 * SimpleSquareButtonAppearance.java
 *
 * Created on November 8, 2007, 6:00 PM
 */

package mdes.slick.sui.skin.simple;

import mdes.slick.sui.Button;

import org.newdawn.slick.geom.RoundedRectangle;

/**
 *
 * @author davedes
 */
public class SimpleSquareButtonAppearance extends SimpleButtonAppearance {
    
    public SimpleSquareButtonAppearance(Button button) {
        super(button);
    }
    
    protected RoundedRectangle createRoundedBounds() {
        return null; //stores null
    }    
}
