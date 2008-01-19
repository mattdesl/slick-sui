/*
 * SteelSepiaTheme.java
 *
 * Created on November 10, 2007, 10:29 PM
 */

package mdes.slick.sui.theme;

import mdes.slick.sui.Theme;
import mdes.slick.sui.skin.ColorUIResource;

import org.newdawn.slick.Color;

/**
 *
 * @author davedes
 */
public class SteelSepiaTheme implements Theme {
    
    private Color activeTitleBar1 = new ColorUIResource(191, 185, 155);
    private Color activeTitleBar2 = new ColorUIResource(238, 237, 229);
    private Color background = new ColorUIResource(250, 250, 249);
    private Color disabledMask = new ColorUIResource(.55f, .55f, .55f, .5f);
    private Color foreground = new ColorUIResource(73, 67, 45);
    private Color primary1 = new ColorUIResource(220, 215, 199);
    private Color primary2 = new ColorUIResource(243, 243, 238);
    private Color primary3 = new ColorUIResource(233, 231, 221);
    private Color primaryBorder1 = new ColorUIResource(169, 160, 117);
    private Color primaryBorder2 = new ColorUIResource(82, 76, 52);
    private Color secondary1 = new ColorUIResource(191, 185, 155);
    private Color secondary2 = new ColorUIResource(243, 243, 238);
    private Color secondary3 = new ColorUIResource(233, 231, 221);
    private Color secondaryBorder1 = new ColorUIResource(183, 175, 139);
    private Color secondaryBorder2 = new ColorUIResource(165, 155, 111);
    private Color titleBar1 = new ColorUIResource(212, 209, 190);
    private Color titleBar2 = new ColorUIResource(238, 237, 229);
    
    public String getName() { return "Steel Sepia"; }
    public Color getActiveTitleBar1() { return activeTitleBar1; }
    public Color getActiveTitleBar2() { return activeTitleBar2; }
    public Color getBackground() { return background; }
    public Color getDisabledMask() { return disabledMask; }
    public Color getForeground() { return foreground; }
    public Color getPrimary1() { return primary1; }
    public Color getPrimary2() { return primary2; }
    public Color getPrimary3() { return primary3; }
    public Color getPrimaryBorder1() { return primaryBorder1; }
    public Color getPrimaryBorder2() { return primaryBorder2; }
    public Color getSecondary1() { return secondary1; }
    public Color getSecondary2() { return secondary2; }
    public Color getSecondary3() { return secondary3; }
    public Color getSecondaryBorder1() { return secondaryBorder1; }
    public Color getSecondaryBorder2() { return secondaryBorder2; }
    public Color getTitleBar1() { return titleBar1; }
    public Color getTitleBar2() { return titleBar2; }
}
