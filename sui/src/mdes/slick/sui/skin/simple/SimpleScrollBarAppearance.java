/*
 * SimpleScrollBarAppearance.java
 *
 * Created on November 12, 2007, 2:58 PM
 */

package mdes.slick.sui.skin.simple;

import mdes.slick.sui.skin.simple.SimpleArrowButton;
import mdes.slick.sui.Button;
import mdes.slick.sui.Component;
import mdes.slick.sui.ScrollBar;
import mdes.slick.sui.Slider;
import mdes.slick.sui.Theme;
import mdes.slick.sui.skin.ScrollBarAppearance;
import mdes.slick.sui.skin.SkinUtil;
import mdes.slick.sui.Skin;

/**
 * 
 * @author davedes
 */
public class SimpleScrollBarAppearance extends SimpleContainerAppearance implements ScrollBarAppearance {
    
    public void install(Component comp, Skin skin, Theme theme) {
        SkinUtil.installFont(comp, ((SimpleSkin)skin).getFont());    
        SkinUtil.installColors(comp, theme.getPrimary1(), theme.getForeground());
    }
    
    public Button createScrollButton(ScrollBar bar, int direction) {
        Button btn = createSimpleScrollButton(bar, direction);
        return btn;
    }
    
    public Slider createSlider(ScrollBar bar, int orientation) {
        Slider slider = new Slider(orientation);
        return slider;
    }
    
    /**
     * A utility method to create a scroll button based on the given
     * scroll bar's orientation, size and direction. This will set the
     * button's dimensions to the width or height (based on orientation)
     * of the given scroll bar.
     * 
     * 
     * 
     * @param bar the scroll bar parent
     * @param direction the direction the bar will scroll, either 
     *      ScrollBar.INCREMENT or ScrollBar.DECREMENT.
     * @return a new SimpleArrowButton based on the given parameters
     */
    protected Button createSimpleScrollButton(ScrollBar bar, int direction) {
        float angle = SimpleArrowButton.getScrollButtonAngle(bar, direction);
        int orientation = bar.getOrientation();
        float size = 0f;
        if (orientation==ScrollBar.HORIZONTAL) {
            size = bar.getHeight();
        } else
            size = bar.getWidth();
        if (size==0)
            size = ScrollBar.DEFAULT_SIZE;
        Button btn = new SimpleArrowButton(angle);
        btn.setSize(size, size);
        return btn;
    }
}
