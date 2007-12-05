/*
 * Sui.java
 *
 * Created on May 31, 2007, 6:49 PM
 */

package mdes.slick.sui;

import java.util.HashMap;
import mdes.slick.sui.skin.SuiSkin;
import mdes.slick.sui.skin.simple.SimpleSkin;
import mdes.slick.sui.theme.SteelBlueTheme;
import mdes.slick.sui.SuiTheme;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.*;
import org.newdawn.slick.util.*;

/**
 * Sui is a utility class for the GUI library. It should be used
 * for initializing the system and changing global settings. 
 * <p>
 * The skin is first installed when one of the init() methods is called.
 * @author davedes
 */
public class Sui {
    
    //TODO: fix problem when input is retained throughout game states
    
    /**
     * The current theme to render with.
     */
    private static SuiTheme theme = new SteelBlueTheme();
    
    /**
     * The current pluggable skin.
     */
    private static SuiSkin skin = new SimpleSkin();
            
    /**
     * The maximum delay for tooltips.
     */
    public static final int MAX_DELAY = Integer.MAX_VALUE;
    
    private static Font defaultFont = null;
    
    static {
        try { setSkin(skin); }
        catch (SlickException e) {
            Log.error("Error creating SimpleSkin (default) for SUI.", e);
        }
    }
    
    /** Creates a new instance of Sui. */
    protected Sui() {
    }
    
    /**
     * Returns the default font.
     * @return the default font
     */
    public static Font getDefaultFont() {
        return defaultFont;
    }

    /**
     * Sets the default font to use when creating new components. This
     * font can be overridden by skins and UIs.
     * @param f the new font
     */
    public static void setDefaultFont(Font aDefaultFont) {
        defaultFont = aDefaultFont;
    }
    
    /**
     * Updates the current skin/theme, asking each node in the given
     * tree to update their appearance by calling:<br />
     * <code>comp.updateAppearance()</code>
     * <p>
     * This will install the "skin default" appearance on the tree. If this
     * is undesired, it is suggested that you use updateComponentTreeTheme,
     * which will re-install the currently set appearance. 
     * 
     * @param c the component tree to update
     */
    public static void updateComponentTreeSkin(SuiComponent comp) {
        if (comp==null)
            return;
        comp.updateAppearance();
        if (comp instanceof SuiContainer) {
            SuiContainer c = (SuiContainer)comp;
            for (int i=0; i<c.getChildCount(); i++) {
                updateComponentTreeSkin(c.getChild(i));
            }
        }
    }
    
    /**
     * Updates the current theme, asking each node in the given
     * tree to update their appearance by calling:<br />
     * <code>comp.setAppearance(comp.getAppearance());</code>
     * 
     * @param c the component tree to update
     */
    public static void updateComponentTreeTheme(SuiComponent comp) {
        if (comp==null)
            return;
        comp.setAppearance(comp.getAppearance());
        if (comp instanceof SuiContainer) {
            SuiContainer c = (SuiContainer)comp;
            for (int i=0; i<c.getChildCount(); i++) {
                updateComponentTreeTheme(c.getChild(i));
            }
        }
    }
    
    /**
     * A convenience method for initializing the system based on the GUIContext.
     * 
     * This is equivalent to:
     * <pre><code>init(new SuiDisplay(c))<code></pre>
     * @return a top-level Sui display
     * @param c the context to initialize with
     * @throws org.newdawn.slick.SlickException if an error occurred when initializing
     *
     * @deprecated no longer needed, will be removed soon
     */
    public static SuiDisplay init(GUIContext c) throws SlickException {
        SuiDisplay d = new SuiDisplay(c);
        return init(d);
    }
    
    /**
     * Initializes the system and returns the passed SuiDisplay.
     * @param d the display to initialize with
     * @return the passed display, <CODE>d</CODE>
     * @throws SlickException if an error occurred when initializing
     *
     * @deprecated no longer needed, will be removed soon
     */
    public static SuiDisplay init(SuiDisplay d) throws SlickException {
        return d;
    }
    
    /**
     * Returns the current color theme to use when rendering.
     * @return the theme to render with
     * @see mdes.slick.sui.SuiTheme
     */
    public static SuiTheme getTheme() {
        return theme;
    }

    /**
     * Sets the new color theme to use when rendering.
     * 
     * @param aTheme the new theme
     * @see mdes.slick.sui.SuiTheme
     */
    public static void setTheme(SuiTheme t) {
        if (t==null)
            throw new IllegalArgumentException("theme cannot be null");
        theme = t;
    }
    
    /**
     * Returns the current skin used for rendering.
     * @return the current skin
     */
    public static SuiSkin getSkin() {
        return skin;
    }

    /**
     * Sets the current skin which components use for rendering.
     * 
     *
     * When a new skin is set, the old skin is uninstalled. The 
     * passed skin will be installed to initialize resources. 
     * Most skins will "cache" their resources to make installation 
     * fast.
     * @param s the new skin to set
     * @throws SlickException if there was a problem uninstalling 
     * the old skin or reinstalling the new skin
     */
    public static void setSkin(SuiSkin s) throws SlickException {
        if (s==null)
            throw new IllegalArgumentException("skin cannot be null");
        if (skin!=null)
            skin.uninstall();
        skin = s;
        skin.install();
    }
}
