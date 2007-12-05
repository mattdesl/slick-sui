/*
 * AbstractComponentAppearance.java
 *
 * Created on November 8, 2007, 4:50 PM
 */

package mdes.slick.sui.skin;

import mdes.slick.sui.SuiComponent;
import mdes.slick.sui.SuiTheme;

/**
 *
 * @author davedes
 */
public abstract class AbstractComponentAppearance implements ComponentAppearance {
    
    public boolean contains(SuiComponent comp, float x, float y) {
        return comp.inside(x, y);
    }
    
    public void install(SuiComponent comp, SuiSkin skin, SuiTheme theme) {    
    }
    
    public void uninstall(SuiComponent comp, SuiSkin skin, SuiTheme theme) {   
    }
}
