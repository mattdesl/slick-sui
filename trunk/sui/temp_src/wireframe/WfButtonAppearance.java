/*
 * WfButtonAppearance.java
 *
 * Created on November 11, 2007, 1:52 PM
 */

package mdes.slick.sui.skin.wireframe;

import mdes.slick.sui.*;
import mdes.slick.sui.skin.*;
import mdes.slick.sui.SuiSkin;
import mdes.slick.sui.SuiTheme;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.gui.*;

/**
 *
 * @author davedes
 */
public class WfButtonAppearance extends WfContainerAppearance {
    
    public void render(GUIContext ctx, Graphics g, SuiComponent comp, SuiSkin skin, SuiTheme theme) {
        SuiButton btn = (SuiButton)comp;
        RenderUtil.renderComponentBase(g, btn);
        renderButtonState(g, btn, theme, comp.getAbsoluteBounds());
        RenderUtil.renderButtonBase(g, btn);
    }
    
    static void renderButtonState(Graphics g, SuiButton btn, SuiTheme theme, Rectangle rect) {
        if (btn instanceof SuiToggleButton) {
            g.setColor( ((SuiToggleButton)btn).isSelected() 
                ? theme.getSecondary1() : theme.getPrimary1() );
        } else {
            g.setColor( (btn.getState()==SuiButton.DOWN) 
                ? theme.getPrimary1() : theme.getSecondary1() );
        }
        g.fill(rect);
        
        g.setColor(btn.getState()==SuiButton.UP 
                ? theme.getPrimaryBorder1() : theme.getPrimaryBorder2());
        g.draw(rect);
        
        if (!btn.isEnabled()) {
            g.setColor(theme.getDisabledMask());
            g.fill(rect);
        }
    }
}
