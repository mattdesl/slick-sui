/*
 * SuiSkin.java
 *
 * Created on November 6, 2007, 6:00 PM
 */

package mdes.slick.sui.skin;

import mdes.slick.sui.SuiButton;
import mdes.slick.sui.SuiCheckBox;
import mdes.slick.sui.SuiContainer;
import mdes.slick.sui.SuiLabel;
import mdes.slick.sui.SuiScrollBar;
import mdes.slick.sui.SuiScrollPane;
import mdes.slick.sui.SuiSlider;
import mdes.slick.sui.SuiTextField;
import mdes.slick.sui.SuiToggleButton;
import mdes.slick.sui.SuiToolTip;
import mdes.slick.sui.SuiWindow;

import org.newdawn.slick.SlickException;

/**
 *
 * @author Matt
 */
public interface SuiSkin {
    
    public String getName();
    
    /**
     * Provides a hint as to whether this skin supports themes. Some skins, 
     * especially image-based skins, are not expected to support themes. It's
     * up to the skin developer to add support.
     *
     * @return <tt>true</tt> if this skin supports the use of color themes
     */
    public boolean isThemeable();
    
    public void install() throws SlickException;
    public void uninstall() throws SlickException;
    
    //public ComponentAppearance getComponentAppearance(SuiComponent comp);
    public ComponentAppearance getContainerAppearance(SuiContainer comp);
    public ComponentAppearance getCheckBoxAppearance(SuiCheckBox comp);
    public ComponentAppearance getButtonAppearance(SuiButton comp);
    public ComponentAppearance getToolTipAppearance(SuiToolTip comp);
    public ComponentAppearance getToggleButtonAppearance(SuiToggleButton comp);
    public ComponentAppearance getLabelAppearance(SuiLabel comp);
    public ComponentAppearance getTextFieldAppearance(SuiTextField comp);
    public WindowAppearance    getWindowAppearance(SuiWindow comp);
    public ScrollBarAppearance getScrollBarAppearance(SuiScrollBar comp); 
    public ComponentAppearance getScrollPaneAppearance(SuiScrollPane comp);
    public SliderAppearance    getSliderAppearance(SuiSlider comp);
}