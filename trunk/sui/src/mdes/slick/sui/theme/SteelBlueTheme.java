/*
 * SteelBlueTheme.java
 *
 * Created on June 18, 2007, 1:00 PM
 */

package mdes.slick.sui.theme;

import mdes.slick.sui.Theme;
import mdes.slick.sui.skin.ColorUIResource;

import org.newdawn.slick.Color;

/**
 *
 * @author davedes
 */
public class SteelBlueTheme implements Theme {
    
    private Color buttonBase2 = new ColorUIResource(154, 174, 193);  
    private Color lightTop2 = new ColorUIResource(213, 219, 229);    
    private Color lightBottom2 = new ColorUIResource(199, 207, 221); 
    
    private Color buttonBase = new ColorUIResource(206, 208, 213); 
    private Color lightTop = new ColorUIResource(235, 239, 246); 
    private Color lightBottom = new ColorUIResource(223, 226, 232);
    
    private Color borderDark = new ColorUIResource(64, 66, 70); 
    private Color borderLight = new ColorUIResource(139, 142, 148);
    private Color background = new ColorUIResource(244, 248, 255);
    
    private Color winBorderLight = new ColorUIResource(156, 161, 167);
    private Color winBorderDark = new ColorUIResource(133, 137, 144);
    private Color winTitleStart = new ColorUIResource(182, 201, 221);
    private Color winTitleEnd = new ColorUIResource(228, 232, 238);
    
    private Color foreground = new ColorUIResource(57, 59, 62); 
    private Color disabledMask = new ColorUIResource(.55f, .55f, .55f, .5f);
    
    public String getName() {
        return "Steel Blue";
    }
        
    public Color getPrimaryBorder1() {
        return borderLight;
    }
    
    public Color getPrimaryBorder2() {
        return borderDark;
    }
    
    public Color getPrimary1() {
        return buttonBase;
    }
    
    public Color getPrimary2() {
        return lightTop;
    }
    
    public Color getPrimary3() {
        return lightBottom;
    }
    
    public Color getSecondary1() {
        return buttonBase2;
    }
    
    public Color getSecondary2() {
        return lightTop;
    }
    
    public Color getSecondary3() {
        return lightBottom;
    }
    
    public Color getBackground() {
        return background;
    }
    
    public Color getForeground() {
        return foreground;
    }

    public Color getSecondaryBorder1() {
        return winBorderLight;
    }

    public Color getSecondaryBorder2() {
        return winBorderDark;
    }

    public Color getTitleBar1() {
        return winTitleStart;
    }

    public Color getTitleBar2() {
        return winTitleEnd;
    }
    
    public Color getActiveTitleBar1() {
        return buttonBase2;
    }
    
    public Color getActiveTitleBar2() {
        return winTitleEnd;
    }
    
    public Color getDisabledMask() {
        return disabledMask;
    }
}
