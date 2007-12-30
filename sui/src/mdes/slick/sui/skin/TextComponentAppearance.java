/*
 * TextComponentAppearance.java
 *
 * Created on December 29, 2007, 9:52 PM
 */

package mdes.slick.sui.skin;

import mdes.slick.sui.Point;
import mdes.slick.sui.SuiTextComponent;

/**
 *
 * @author davedes
 */
public interface TextComponentAppearance extends ComponentAppearance {
    
    public int viewToModel(SuiTextComponent comp, float x, float y);
    public Point modelToView(SuiTextComponent comp, int pos);
}
