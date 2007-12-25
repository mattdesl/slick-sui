/*
 * SimpleContainerAppearance.java
 *
 * Created on November 7, 2007, 8:17 PM
 */

package mdes.slick.sui.skin.simple;

import mdes.slick.sui.SuiComponent;
import mdes.slick.sui.SuiTheme;
import mdes.slick.sui.skin.SkinUtil;
import mdes.slick.sui.SuiSkin;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author davedes
 */
public class SimpleContainerAppearance extends SimpleComponentAppearance {
    
    //TODO: client properties for square (not round) buttons if desired
    
    public void render(GUIContext ctx, Graphics g, SuiComponent comp, SuiSkin skin, SuiTheme theme) {
        SkinUtil.renderComponentBase(g, comp);
    }
}
