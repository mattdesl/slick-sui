/*
 * WfLabelAppearance.java
 *
 * Created on November 7, 2007, 8:25 PM
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
public class WfLabelAppearance extends WfContainerAppearance {
    
    private Color resizerCol = null;
    
    public void install(SuiComponent comp, SuiSkin skin, SuiTheme theme) {
        super.install(comp,skin,theme);
    }
    
    public void render(GUIContext ctx, Graphics g, SuiComponent comp, SuiSkin skin, SuiTheme theme) {
        RenderUtil.renderComponentBase(g, comp);
                
        Rectangle rect = comp.getAbsoluteBounds();
        if (comp instanceof SuiWindow.TitleBar) {
            boolean act = ((SuiWindow.TitleBar)comp).getWindow().isActive();
            Color c = act ? theme.getActiveTitleBar1() : theme.getTitleBar1();
            g.setColor(c);
            g.fill(rect);
            renderBorder(g, rect, theme);
        } else if (comp instanceof SuiWindow.Resizer) {
            g.setColor(theme.getPrimaryBorder1());
            g.fill(rect);
        }
        
        RenderUtil.renderLabelBase(g, (SuiLabel)comp);
    }
}
