/*
 * SimpleComponentAppearance.java
 *
 * Created on November 7, 2007, 8:18 PM
 */

package mdes.slick.sui.skin.simple;

import mdes.slick.sui.SuiComponent;
import mdes.slick.sui.skin.SuiSkin;
import mdes.slick.sui.skin.AbstractComponentAppearance;
import mdes.slick.sui.SuiTheme;
import mdes.slick.sui.skin.SkinUtil;
import org.newdawn.slick.gui.GUIContext;

/**
 * A basic appearance that plugs into a component.
 * @author davedes
 */
public abstract class SimpleComponentAppearance extends AbstractComponentAppearance {
        
    public void update(GUIContext ctx, int delta, SuiComponent comp, SuiSkin skin, SuiTheme theme) {
        //do nothing
    }
    
    public void install(SuiComponent comp, SuiSkin skin, SuiTheme theme) {
        SkinUtil.installFont(comp, ((SimpleSkin)skin).getFont());        
        SkinUtil.installColors(comp, theme.getBackground(), theme.getForeground());
    }
    
    public void uninstall(SuiComponent comp, SuiSkin skin, SuiTheme theme) {   
    }
}
