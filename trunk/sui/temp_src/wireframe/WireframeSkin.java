/*
 * WireframeSkin.java
 *
 * Created on November 7, 2007, 6:52 PM
 */

package mdes.slick.sui.skin.wireframe;

import mdes.slick.sui.*;
import mdes.slick.sui.skin.ComponentAppearance;
import mdes.slick.sui.SuiSkin;
import mdes.slick.sui.SuiTheme;
import mdes.slick.sui.skin.*;
import org.newdawn.slick.fills.*;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import org.lwjgl.opengl.*;
import org.newdawn.slick.opengl.SlickCallable;
import org.newdawn.slick.gui.*;

/**
 *
 * @author davedes
 */
public class WireframeSkin implements SuiSkin {
    private Font font;
    
    private ComponentAppearance containerSkin = new WfContainerAppearance();
    private ComponentAppearance checkBoxSkin = new WfCheckBoxAppearance();
    private ComponentAppearance buttonSkin = new WfButtonAppearance();
    private ComponentAppearance labelSkin = new WfLabelAppearance();
    
    public String getName() {
        return "Wireframe";
    }
    
    public void install() throws SlickException {
                ///////////////////
                // CACHE OBJECTS //
                ///////////////////
        
        //try loading
        //ResourceLoader will spit out a log message if there are problems
                
        //fonts
        if (font==null)
            font = tryFont("res/skin/shared/verdana.fnt", "res/skin/shared/verdana.png");
        
        SkinManager.put("background", Sui.getTheme().getBackground());
        SkinManager.put("foreground", Sui.getTheme().getForeground());
        SkinManager.put("font", font);
    }
        
    private Font tryFont(String s1, String s2) {
        try { return new FontUIResource.AngelCodeFont(s1, s2); }
        catch (Exception e) { return null; }
    }

    public void uninstall() throws SlickException {
    }
    
    public ComponentAppearance getContainerAppearance() {
        return containerSkin;
    }

    public ComponentAppearance getCheckBoxAppearance() {
        return checkBoxSkin;
    }

    public ComponentAppearance getWindowAppearance() {
        return containerSkin;
    }

    public ComponentAppearance getButtonAppearance() {
        return buttonSkin;
    }

    public ComponentAppearance getToolTipAppearance() {
        return containerSkin;
    }

    public ComponentAppearance getLabelAppearance() {
        return labelSkin;
    }

    public ComponentAppearance getWindowTitleBarAppearance() {
        return labelSkin;
    }
    
    public ComponentAppearance getWindowResizerAppearance() {
        return labelSkin;
    }

    public ComponentAppearance getWindowCloseButtonAppearance() {
        return buttonSkin;
    }
    
    public ComponentAppearance getToggleButtonAppearance() {
        return buttonSkin;
    }
}
