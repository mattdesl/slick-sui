/*
 * WfComponentAppearance.java
 *
 * Created on November 7, 2007, 8:18 PM
 */

package mdes.slick.sui.skin.wireframe;

import mdes.slick.sui.SuiComponent;
import mdes.slick.sui.SuiSkin;
import mdes.slick.sui.skin.AbstractComponentAppearance;
import mdes.slick.sui.skin.SkinManager;
import mdes.slick.sui.SuiTheme;

/**
 *
 * @author davedes
 */
public abstract class WfComponentAppearance extends AbstractComponentAppearance {
    
    public void install(SuiComponent comp, SuiSkin skin, SuiTheme theme) {
        SkinManager.installFont(comp, "font");
        SkinManager.installColors(comp, "background", "foreground");
    }
}
