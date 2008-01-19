/*
 * SteelAcidTheme.java
 *
 * Created on November 11, 2007, 5:33 PM
 */

package mdes.slick.sui.theme;

import mdes.slick.sui.Theme;
import mdes.slick.sui.skin.ColorUIResource;

import org.newdawn.slick.Color;

/**
 *
 * @author davedes
 */
public class SteelAcidTheme implements Theme {
    private Color activeTitleBar1 = new ColorUIResource(154, 186, 33);
    private Color activeTitleBar2 = new ColorUIResource(226, 234, 195);
    private Color background = new ColorUIResource(248, 250, 239);
    private Color disabledMask = new ColorUIResource(.55f, .55f, .55f, .5f);
    private Color foreground = new ColorUIResource(55, 59, 39);
    private Color primary1 = new ColorUIResource(203, 209, 182);
    private Color primary2 = new ColorUIResource(235, 241, 212);
    private Color primary3 = new ColorUIResource(220, 227, 192);
    private Color primaryBorder1 = new ColorUIResource(136, 143, 108);
    private Color primaryBorder2 = new ColorUIResource(61, 66, 42);
    private Color secondary1 = new ColorUIResource(154, 186, 33);
    private Color secondary2 = new ColorUIResource(235, 241, 212);
    private Color secondary3 = new ColorUIResource(220, 227, 192);
    private Color secondaryBorder1 = new ColorUIResource(154, 163, 120);
    private Color secondaryBorder2 = new ColorUIResource(129, 138, 95);
    private Color titleBar1 = new ColorUIResource(186, 208, 101);
    private Color titleBar2 = new ColorUIResource(226, 234, 195);
    
    public String getName() { return "Steel Acid"; }
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
