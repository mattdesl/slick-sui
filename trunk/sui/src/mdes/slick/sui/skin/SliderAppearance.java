/*
 * SliderAppearance.java
 *
 * Created on November 15, 2007, 5:54 PM
 */

package mdes.slick.sui.skin;

import mdes.slick.sui.ScrollConstants;
import mdes.slick.sui.Button;
import mdes.slick.sui.Slider;

/**
 *
 * @author Matt
 */
public interface SliderAppearance extends ComponentAppearance, ScrollConstants {
    
    /**
     * This is the knob or thumb button whic appears on the slider.
     */
    public Button createThumbButton(Slider slider); 
}
