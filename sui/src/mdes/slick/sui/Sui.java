/*
 * Sui.java
 *
 * Created on May 31, 2007, 6:49 PM
 */

package mdes.slick.sui;

import mdes.slick.sui.skin.simple.SimpleSkin;
import mdes.slick.sui.theme.SteelSepiaTheme;

import org.newdawn.slick.Font;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.util.Log;

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
    private static Theme theme = new SteelSepiaTheme();
    
    /**
     * The current pluggable skin.
     */
    private static Skin skin = new SimpleSkin();
            
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
    public static void updateComponentTreeSkin(Component comp) {
        if (comp==null)
            return;
        comp.updateAppearance();
        if (comp instanceof Container) {
            Container c = (Container)comp;
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
    public static void updateComponentTreeTheme(Component comp) {
        if (comp==null)
            return;
        comp.setAppearance(comp.getAppearance());
        if (comp instanceof Container) {
            Container c = (Container)comp;
            for (int i=0; i<c.getChildCount(); i++) {
                updateComponentTreeTheme(c.getChild(i));
            }
        }
    }
    
    /**
     * Returns the current color theme to use when rendering.
     * 
     * @return the theme to render with
     * @see mdes.slick.sui.Theme
     */
    public static Theme getTheme() {
        return theme;
    }

    /**
     * Sets the new color theme to use when rendering.
     * 
     * 
     * @param aTheme the new theme
     * @see mdes.slick.sui.Theme
     */
    public static void setTheme(Theme t) {
        if (t==null)
            throw new IllegalArgumentException("theme cannot be null");
        theme = t;
    }
    
    /**
     * Returns the current skin used for rendering.
     * @return the current skin
     */
    public static Skin getSkin() {
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
    public static void setSkin(Skin s) throws SlickException {
        if (s==null)
            throw new IllegalArgumentException("skin cannot be null");
        if (skin!=null)
            skin.uninstall();
        skin = s;
        skin.install();
    }
}
