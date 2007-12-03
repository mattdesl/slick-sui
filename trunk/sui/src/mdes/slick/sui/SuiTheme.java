/*
 * SuiTheme.java
 *
 * Created on May 31, 2007, 10:28 PM
 */

package mdes.slick.sui;

import org.newdawn.slick.*;
import org.newdawn.slick.opengl.*;
import org.newdawn.slick.util.*;

/**
 * Buttons, text fields and windows are rendered
 * using the colors defined in the current SuiTheme.
 * <p>
 * Themes in Sui will be reworked shortly.
 *
 * @author davedes
 * @since b.0.1
 * @see mdes.slick.sui.Sui#setTheme(SuiTheme)
 */
public interface SuiTheme {
    
    /** The name of the theme. */
    public String getName();
    
    /** 
     * Component base color. 
     * Primary colours are generally used for 
     * an "up" or "still" state.
     */
    public Color getPrimary1();
    
    /** 
     * Component light color. 
     * Primary colours are generally used for 
     * an "up" or "still" state.
     */
    public Color getPrimary2();
    
    /**
     * Component dark color. 
     * Primary colours are generally used for 
     * an "up" or "still" state.
     */
    public Color getPrimary3();
    
    /**
     * Component base color (secondary). 
     * Secondary colours are generally used for 
     * an "rollover" or "animate" state.
     */
    public Color getSecondary1();
    
    /**
     * Component light color (secondary). 
     * Secondary colours are generally used for 
     * an "rollover" or "animate" state.
     */
    public Color getSecondary2();
    
    /**
     * Component dark color (secondary). 
     * Secondary colours are generally used for 
     * an "rollover" or "animate" state.
     */
    public Color getSecondary3();
    
    /**
     * Component foreground (text) color.
     */
    public Color getForeground();
    
    /**
     * Component background color.
     */
    public Color getBackground();
    
    /**
     * Component border light color.
     */
    public Color getPrimaryBorder1();
    
    /**
     * Component border dark color.
     */
    public Color getPrimaryBorder2();
    
    /**
     * Component border light color (secondary).
     * Generally used for special components such as 
     * windows/dialogs.
     */
    public Color getSecondaryBorder1();
    
    /**
     * Component border dark color (secondary).
     * Generally used for special components such as 
     * windows/dialogs.
     */
    public Color getSecondaryBorder2();
    
    /**
     * Window title start color (light).
     */
    public Color getTitleBar1();
    
    /**
     * Window title end color (dark).
     */
    public Color getTitleBar2();
    
    /**
     * Active window title start color (light).
     */
    public Color getActiveTitleBar1();
    
    /**
     * Active window title end color (dark).
     */
    public Color getActiveTitleBar2();
    
    /**
     * Usually a low-opacity grey to mask over disabled components.
     */
    public Color getDisabledMask();
}