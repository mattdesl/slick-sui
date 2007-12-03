/*
 * WfCheckBoxAppearance.java
 *
 * Created on November 7, 2007, 8:58 PM
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
public class WfCheckBoxAppearance extends WfButtonAppearance {
    
    public void render(GUIContext ctx, Graphics g, SuiComponent comp, SuiSkin skin, SuiTheme theme) {
        SuiCheckBox check = (SuiCheckBox)comp;
        
        //renders base
        RenderUtil.renderComponentBase(g, check);
                
        //renders text/image
        RenderUtil.renderCheckBoxBase(g, check);
        
        //get cached bounds from the "check" box button area
        Rectangle btnRect = check.getAbsoluteBoxBounds();
        
        //renders the actual button state for the small box area, using rounded edges
        WfButtonAppearance.renderButtonState(g, check, theme, btnRect);
    }
}
