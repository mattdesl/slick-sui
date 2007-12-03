/*
 * SimpleComponentAppearance.java
 *
 * Created on November 7, 2007, 8:18 PM
 */

package mdes.slick.sui.skin.simple;

import mdes.slick.sui.SuiComponent;
import mdes.slick.sui.skin.SuiSkin;
import mdes.slick.sui.skin.AbstractComponentAppearance;
import mdes.slick.sui.skin.SkinManager;
import mdes.slick.sui.SuiTheme;

/**
 *
 * @author davedes
 */
public abstract class SimpleComponentAppearance extends AbstractComponentAppearance {
    
    private SuiComponent comp;
    
    protected SuiComponent component() {
        return comp;
    }
    
    //TODO: update(..)
    public void install(SuiComponent comp, SuiSkin skin, SuiTheme theme) {
        SkinManager.installFont(comp, "font");
        boolean b = SkinManager.installColors(comp, theme.getBackground(), theme.getForeground());
        this.comp = comp;
    }
    
    public void uninstall(SuiComponent comp, SuiSkin skin, SuiTheme theme) {   
        this.comp = null;
    }
}
