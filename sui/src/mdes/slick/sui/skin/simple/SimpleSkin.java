/*
 * SimpleSkin.java
 *
 * Created on November 7, 2007, 6:52 PM
 */

package mdes.slick.sui.skin.simple;

import mdes.slick.sui.*;
import mdes.slick.sui.skin.ComponentAppearance;
import mdes.slick.sui.skin.SuiSkin;
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
public class SimpleSkin implements SuiSkin {
    
    
    private Image checkboxImage;
    private Image closeButtonImage;
    private Image resizerImage;
    private Font font;
    
    private ComponentAppearance containerSkin = new SimpleContainerAppearance();
    private ComponentAppearance checkBoxSkin = new SimpleCheckBoxAppearance();
    private WindowAppearance windowSkin = new SimpleWindowAppearance();
    private ComponentAppearance buttonSkin = new SimpleButtonAppearance();
    private ComponentAppearance toolTipSkin = new SimpleToolTipAppearance();
    private ComponentAppearance labelSkin = new SimpleLabelAppearance();
    private ScrollBarAppearance scrollBarAppearance = new SimpleScrollBarAppearance();
    private ComponentAppearance scrollPaneAppearance = new SimpleScrollPaneAppearance();
    
    private static boolean roundRectanglesEnabled = true;
                
    public String getName() {
        return "Simple";
    }
    
    public void install() throws SlickException {
                ///////////////////
                // CACHE OBJECTS //
                ///////////////////
        
        //try loading
        //ResourceLoader will spit out a log message if there are problems
        
        //images
        if (checkboxImage==null)
            checkboxImage = tryImage("res/skin/simple/checkbox.png");
        if (closeButtonImage==null)
            closeButtonImage = tryImage("res/skin/simple/closewindow.png");
        if (resizerImage==null)
            resizerImage = tryImage("res/skin/simple/resizer.png");
        
        //fonts
        if (font==null)
            font = tryFont("res/skin/shared/verdana.fnt", "res/skin/shared/verdana.png");
                
        //TODO: remove this
        //Sui.setDefaultFont(font);
        
        SkinManager.put("font", font);
        SkinManager.put("CheckBox.image", checkboxImage);
        SkinManager.put("Window.CloseButton.image", closeButtonImage);
        //SkinManager.put("Window.Resizer.image", resizerImage);
    }
    
    private Image tryImage(String s) {
        try { return new ImageUIResource(s); }
        catch (Exception e) { return null; }
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

    public WindowAppearance getWindowAppearance() {
        return windowSkin;
    }

    public ComponentAppearance getButtonAppearance() {
        return buttonSkin;
    }

    public ComponentAppearance getToolTipAppearance() {
        return toolTipSkin;
    }

    public ComponentAppearance getLabelAppearance() {
        return labelSkin;
    }
    
    public ComponentAppearance getToggleButtonAppearance() {
        return buttonSkin;
    }

    public static boolean isRoundRectanglesEnabled() {
        return roundRectanglesEnabled;
    }

    public static void setRoundRectanglesEnabled(boolean aRoundRectanglesEnabled) {
        roundRectanglesEnabled = aRoundRectanglesEnabled;
    }

    public ScrollBarAppearance getScrollBarAppearance() {
        return scrollBarAppearance;
    }

    public ComponentAppearance getScrollPaneAppearance() {
        return scrollPaneAppearance;
    }
    
    public SliderAppearance getSliderAppearance() {
        return new SimpleSliderAppearance();
    }
    
    public ComponentAppearance getTextFieldAppearance() {
        return new SimpleTextFieldAppearance();
    }
}
