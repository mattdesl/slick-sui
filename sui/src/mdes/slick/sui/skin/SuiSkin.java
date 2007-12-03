/*
 * SuiSkin.java
 *
 * Created on November 6, 2007, 6:00 PM
 */

package mdes.slick.sui.skin;

import org.newdawn.slick.*;

/**
 *
 * @author Matt
 */
public interface SuiSkin {
    
    public String getName();
    
    public void install() throws SlickException;
    public void uninstall() throws SlickException;
    
    //public ComponentAppearance getComponentAppearance();
    public ComponentAppearance getContainerAppearance();
    public ComponentAppearance getCheckBoxAppearance();
    public ComponentAppearance getButtonAppearance();
    public ComponentAppearance getToolTipAppearance();
    public ComponentAppearance getToggleButtonAppearance();
    public ComponentAppearance getLabelAppearance();
    public ComponentAppearance getTextFieldAppearance();
    public WindowAppearance    getWindowAppearance();
    public ScrollBarAppearance getScrollBarAppearance(); 
    public ComponentAppearance getScrollPaneAppearance();
    public SliderAppearance    getSliderAppearance();
}