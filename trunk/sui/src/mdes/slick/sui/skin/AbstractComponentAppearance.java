/*
 * AbstractComponentAppearance.java
 *
 * Created on November 8, 2007, 4:50 PM
 */

package mdes.slick.sui.skin;

import mdes.slick.sui.Component;
import mdes.slick.sui.Skin;
import mdes.slick.sui.Theme;

/**
 *
 * @author davedes
 */
public abstract class AbstractComponentAppearance implements ComponentAppearance {
    
    public boolean contains(Component comp, float x, float y) {
        return comp.inside(x, y);
    }
    
    public void install(Component comp, Skin skin, Theme theme) {    
    }
    
    public void uninstall(Component comp, Skin skin, Theme theme) {   
    }
}
