/*
 * SimpleToolTipAppearance.java
 *
 * Created on November 7, 2007, 9:00 PM
 */

package mdes.slick.sui.skin.simple;

import mdes.slick.sui.SuiComponent;
import mdes.slick.sui.skin.SuiSkin;
import mdes.slick.sui.SuiTheme;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author davedes
 */
public class SimpleToolTipAppearance extends SimpleLabelAppearance {
    
    public void render(GUIContext ctx, Graphics g, SuiComponent comp, SuiSkin skin, SuiTheme theme) {
        super.render(ctx, g, comp, skin, theme);
        
        g.setColor(theme.getPrimaryBorder2());
        g.draw(comp.getAbsoluteBounds());
    }
}
