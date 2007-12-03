/*
 * SimpleLabelAppearance.java
 *
 * Created on November 7, 2007, 8:25 PM
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
public class SimpleLabelAppearance extends SimpleContainerAppearance {
    
    
    public void render(GUIContext ctx, Graphics g, SuiComponent comp, SuiSkin skin, SuiTheme theme) {
        super.render(ctx, g, comp, skin, theme);
        RenderUtil.renderLabelBase(g, (SuiLabel)comp);
    }
}
