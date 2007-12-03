/*
 * SimpleContainerAppearance.java
 *
 * Created on November 7, 2007, 8:17 PM
 */

package mdes.slick.sui.skin.simple;

import mdes.slick.sui.*;
import mdes.slick.sui.skin.*;
import mdes.slick.sui.skin.SuiSkin;
import mdes.slick.sui.SuiTheme;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.*;

/**
 *
 * @author davedes
 */
public class SimpleContainerAppearance extends SimpleComponentAppearance {
    
    //TODO: client properties for square (not round) buttons if desired
    
    public void render(GUIContext ctx, Graphics g, SuiComponent comp, SuiSkin skin, SuiTheme theme) {
        RenderUtil.renderComponentBase(g, comp);
    }
}
