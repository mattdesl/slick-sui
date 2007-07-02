/*
 * Sui.java
 *
 * Created on May 31, 2007, 6:49 PM
 */

package mdes.slick.sui;

import java.util.HashMap;
import mdes.slick.sui.skin.*;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.*;

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
     * The default font to be used when creating new components.
     */
    private static Font defaultFont = null;
    static SuiContainer focusOwner = null;
    /**
     * The tool tip delay used by SuiDisplay.
     */
    private static int toolTipDelay = 1200;
    /**
     * The current theme to render with.
     */
    private static SuiTheme theme = new SimpleTheme();
    /**
     * The current pluggable skin.
     */
    private static SuiSkin skin = new SimpleSkin();
    
    /**
     * Whether or not to install the default skin.
     */
    private static boolean defaultSkinSet=false;
    /**
     * Whether or not to install the default theme.
     */
    private static boolean defaultThemeSet=false;
    
    /**
     * The maximum delay for tooltips.
     */
    public static final int MAX_DELAY = Integer.MAX_VALUE;
    
    /** Creates a new instance of Sui. */
    protected Sui() {
    }
    
    /**
     * Used internally to change the focus.
     *
     * @param c the new owner of the focus, or null
     * 			if no component has the focus
     */
    static void setFocusOwner(SuiContainer c) {
        //if the container isn't focusable
        if (c!=null && !c.isFocusable())
            return;
        focusOwner = c;
    }
    
    /**
     * Returns the component which currently has the focus.
     * If the current focus owner is not focusable and/or not
     * visible, the focus owner is released and <tt>null</tt> is returned.
     * @return the current focus owner, or <tt>null</tt> if none exists
     * @since b.0.2
     */
    public static SuiContainer getFocusOwner() {
        //releases focus if the component is no longer visible,
        //or is no longer focusable
        
        if (focusOwner==null)
            return null;
        else {
            if (!focusOwner.isFocusable() || !focusOwner.isShowing()) {
                focusOwner.setWindowsActive(false);
                focusOwner = null;
            }
            return focusOwner;
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
     */
    public static SuiDisplay init(GUIContext c) throws SlickException {
        SuiDisplay d = new SuiDisplay(c);
        checkDefaultLAF(d);
        return init(d);
    }
    
    /**
     * Initializes the system and returns the passed SuiDisplay.
     * @param d the display to initialize with
     * @return the passed display, <CODE>d</CODE>
     * @throws SlickException if an error occurred when initializing
     */
    public static SuiDisplay init(SuiDisplay d) throws SlickException {
        checkDefaultLAF(d);
        return d;
    }
    
    /**
     * Checks whether or not to install the defaults.
     * @param d the display
     * @throws org.newdawn.slick.SlickException problem installing
     */
    private static void checkDefaultLAF(SuiDisplay d) throws SlickException {
        if (!defaultSkinSet) {
            setSkin(skin);
            defaultSkinSet = true;
        }
        if (!defaultThemeSet) {
            setTheme(theme);
            defaultThemeSet = true;
        }
    }
    
    /**
     * Sets the default font to use when creating new components. This
     * font can be overridden by skins and UIs.
     * @param f the new font
     */
    public static void setDefaultFont(Font f) {
        Sui.defaultFont = f;
    }
    
    /**
     * Returns the default font.
     * @return the default font
     */
    public static Font getDefaultFont() {
        return Sui.defaultFont;
    }

    /**
     * Returns the current tool tip delay, in milliseconds. 
     * The delay determines the amount of time to wait
     * before showing a tool tip (if necessary).
     * <p>
     * All SuiDisplays will use this delay.
     * @return the global tool tip delay
     */
    public static int getToolTipDelay() {
        return toolTipDelay;
    }
    
    /**
     * Sets the tool tip delay. 
     * <p>
     * All SuiDisplays will use this new value.
     * @param aToolTipDelay the new delay to use
     * @see Sui#getToolTipDelay()
     */
    public static void setToolTipDelay(int aToolTipDelay) {
        toolTipDelay = aToolTipDelay;
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
    public static void setTheme(SuiTheme aTheme) {
        theme = aTheme;
        
        if (!defaultThemeSet)
            defaultThemeSet = true;
    }
    
    /**
     * Updates the current Skin, asking each node in the given
     * tree to updateUI().
     * @param c the component tree to update
     */
    public static void updateComponentTreeUI(SuiContainer c) {
        if (c==null)
            return;
        c.updateUI(skin);
        for (int i=0; i<c.getChildCount(); i++) {
            updateComponentTreeUI(c.getChild(i));
        }
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
     * Note that this won't change the UI of already-created components. 
     * Typically, a new skin is set and changed with:
     *  <CODE><PRE>Sui.setSkin(mySkin);
     *  Sui.updateComponentTreeUI(myDisplay);</PRE></CODE>
     * <p>
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
        
        if (!defaultSkinSet)
            defaultSkinSet = true;
    }
    
    /**
     * The default blue theme, used internally. The RGB values
     * are as follows:
     * <code><pre>
     *    //alphas of .85f
     *    primary1 = 37, 78, 102
     *    primary2 = 16, 36, 46
     *    primary3 = 54, 123, 163
     * 
     *    //alphas of .85f
     *    secondary1 = 37, 78, 102
     *    secondary2 = 62, 70, 75
     *    secondary3 = 20, 48, 64
     * 
     *    //alpha of 1.0f
     *    foreground = white
     * </pre></code>
     * @see mdes.slick.sui.SuiTheme
     * @deprecated use ClassicTheme instead
     */
    public static class DefaultTheme implements SuiTheme {
        
        private final Color p1 = new ColorUIResource(37, 78, 102);
        
        private final Color p2 = new ColorUIResource(16, 36, 46);
        
        private final Color p3 = new ColorUIResource(54, 123, 163);
        
        private final Color s1 = new ColorUIResource(37, 78, 102);
        
        private final Color s2 = new ColorUIResource(62, 70, 75);
        
        private final Color s3 = new ColorUIResource(20, 48, 64);
        
        private final Color txt = new ColorUIResource(Color.white);
        
        private Color disabled = new ColorUIResource(.25f, .25f, .25f, .55f);
        
        private Color background = new ColorUIResource(s2);
        
        /** Constructs the blue theme. */
        public DefaultTheme() {
            //sets up alpha values for a nicer look
            p1.a = 0.95f;
            p2.a = 0.95f;
            p3.a = 0.95f;
            
            s1.a = 0.95f;
            s2.a = 0.95f;
            s3.a = 0.95f;
            
            background.a = .98f;
        }
        
        public Color getPrimary1() { return s1; }
        public Color getPrimary2() { return s2; }
        public Color getPrimary3() { return s3; }
        
        public Color getSecondary1() { return p1; }
        public Color getSecondary2() { return p2; }
        public Color getSecondary3() { return p3; }
        
        public Color getForeground() { return txt; }
        
        public String getName() { return "DefaultSuiTheme"; }

        public Color getBackground() { return background; }

        public Color getPrimaryBorder1() { return s3; }

        public Color getPrimaryBorder2() { return s3; }

        public Color getSecondaryBorder1() { return s3; }

        public Color getSecondaryBorder2() { return s3; }

        public Color getTitleBar1() { return s1; }

        public Color getTitleBar2() { return s2; }
        
        public Color getActiveTitleBar1() { return p1; }
        
        public Color getActiveTitleBar2() { return p2; }
        
        public Color getDisabledMask() { return disabled; }
    }
}
