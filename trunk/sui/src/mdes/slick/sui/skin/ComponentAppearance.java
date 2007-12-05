/*
 * ComponentAppearance.java
 *
 * Created on November 7, 2007, 8:06 PM
 */

package mdes.slick.sui.skin;

import mdes.slick.sui.*;
import mdes.slick.sui.skin.SuiSkin;
import mdes.slick.sui.SuiTheme;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author Matt
 */
public interface ComponentAppearance {
    public void render(GUIContext ctx, Graphics g, SuiComponent comp, SuiSkin skin, SuiTheme theme);
    public void update(GUIContext ctx, int delta, SuiComponent comp, SuiSkin skin, SuiTheme theme);
    
    public boolean contains(SuiComponent comp, float x, float y);
    
    public void install(SuiComponent comp, SuiSkin skin, SuiTheme theme);
    public void uninstall(SuiComponent comp, SuiSkin skin, SuiTheme theme);
}
