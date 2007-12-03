/*
 * WfContainerAppearance.java
 *
 * Created on November 7, 2007, 8:17 PM
 */

package mdes.slick.sui.skin.wireframe;

import mdes.slick.sui.*;
import mdes.slick.sui.skin.*;
import mdes.slick.sui.SuiSkin;
import mdes.slick.sui.SuiTheme;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.*;
import org.newdawn.slick.geom.*;

/**
 *
 * @author davedes
 */
public class WfContainerAppearance extends WfComponentAppearance {
    
    public void render(GUIContext ctx, Graphics g, SuiComponent comp, SuiSkin skin, SuiTheme theme) {
        RenderUtil.renderComponentBase(g, comp);
        
        if (comp instanceof SuiWindow) {
            renderBorder(g, comp.getAbsoluteBounds(), theme);
        }
    }
    
    protected void renderBorder(Graphics g, Rectangle bounds, SuiTheme theme) {
        g.setColor(theme.getPrimaryBorder2());
        g.draw(bounds);
    }
}
