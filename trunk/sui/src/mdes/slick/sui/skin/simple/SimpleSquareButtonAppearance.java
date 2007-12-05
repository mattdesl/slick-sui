/*
 * SimpleSquareButtonAppearance.java
 *
 * Created on November 8, 2007, 6:00 PM
 */

package mdes.slick.sui.skin.simple;

import mdes.slick.sui.*;
import mdes.slick.sui.skin.ComponentAppearance;
import mdes.slick.sui.SuiTheme;
import mdes.slick.sui.skin.SkinUtil;
import org.newdawn.slick.fills.*;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import org.lwjgl.opengl.*;
import org.newdawn.slick.opengl.SlickCallable;
import org.newdawn.slick.gui.*;
import mdes.slick.sui.skin.SuiSkin;

/**
 *
 * @author davedes
 */
public class SimpleSquareButtonAppearance extends SimpleButtonAppearance {
    
    public SimpleSquareButtonAppearance(SuiButton button) {
        super(button);
    }
    
    protected RoundedRectangle createRoundedBounds() {
        return null; //stores null
    }    
}
