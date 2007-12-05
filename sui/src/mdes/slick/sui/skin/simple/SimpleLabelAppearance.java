/*
 * SimpleLabelAppearance.java
 *
 * Created on November 7, 2007, 8:25 PM
 */

package mdes.slick.sui.skin.simple;

import mdes.slick.sui.SuiComponent;
import mdes.slick.sui.SuiLabel;
import mdes.slick.sui.SuiTheme;
import mdes.slick.sui.skin.SkinUtil;
import mdes.slick.sui.skin.SuiSkin;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author davedes
 */
public class SimpleLabelAppearance extends SimpleContainerAppearance {
    
    public void render(GUIContext ctx, Graphics g, SuiComponent comp, SuiSkin skin, SuiTheme theme) {
        super.render(ctx, g, comp, skin, theme);
        SkinUtil.renderLabelBase(g, (SuiLabel)comp);
    }
}
