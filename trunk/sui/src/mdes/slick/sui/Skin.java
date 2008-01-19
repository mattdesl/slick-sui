/*
 * Skin.java
 *
 * Created on November 6, 2007, 6:00 PM
 */

package mdes.slick.sui;

import mdes.slick.sui.skin.*;

import org.newdawn.slick.SlickException;

/**
 *
 * @author Matt
 */
public interface Skin {
    
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
    public ComponentAppearance  getContainerAppearance(Container comp);
    public ComponentAppearance  getCheckBoxAppearance(CheckBox comp);
    public ComponentAppearance  getButtonAppearance(Button comp);
    public ComponentAppearance  getToolTipAppearance(ToolTip comp);
    public ComponentAppearance  getToggleButtonAppearance(ToggleButton comp);
    public ComponentAppearance  getLabelAppearance(Label comp);
    public ComponentAppearance  getTextFieldAppearance(TextField comp);
    public ComponentAppearance  getTextAreaAppearance(TextArea comp);
    public ComponentAppearance  getWindowAppearance(Window comp);
    public FrameAppearance      getFrameAppearance(Frame comp);
    public ScrollBarAppearance  getScrollBarAppearance(ScrollBar comp); 
    public ScrollPaneAppearance getScrollPaneAppearance(ScrollPane comp);
    public SliderAppearance     getSliderAppearance(Slider comp);
}