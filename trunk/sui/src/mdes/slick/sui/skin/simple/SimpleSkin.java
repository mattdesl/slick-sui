/*
 * SimpleSkin.java
 *
 * Created on November 7, 2007, 6:52 PM
 */

package mdes.slick.sui.skin.simple;

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
import mdes.slick.sui.skin.ComponentAppearance;
import mdes.slick.sui.skin.FontUIResource;
import mdes.slick.sui.skin.ImageUIResource;
import mdes.slick.sui.skin.ScrollBarAppearance;
import mdes.slick.sui.skin.SliderAppearance;
import mdes.slick.sui.SuiSkin;
import mdes.slick.sui.SuiTextArea;
import mdes.slick.sui.event.SuiMouseAdapter;
import mdes.slick.sui.event.SuiMouseEvent;
import mdes.slick.sui.event.SuiMouseListener;
import mdes.slick.sui.skin.WindowAppearance;

import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.CursorLoader;
import org.newdawn.slick.util.Log;

/**
 *
 * @author davedes
 */
public class SimpleSkin implements SuiSkin {
    
    private Image checkBoxImage;
    private Image closeButtonImage;
    private Image resizerImage;
    private Font font;
    private Cursor selectCursor;
    private Cursor resizeCursor;
    
    private boolean selectCursorFailed = false;
    private boolean resizeCursorFailed = false;
        
    private SuiMouseListener selectCursorListener;
    private SuiMouseListener resizeCursorListener;
    
    private static boolean roundRectanglesEnabled = true;
           
    //we can cache some of our appearances, others need to be created & attached to components
    private ComponentAppearance containerAppearance = new SimpleContainerAppearance();
    private ComponentAppearance toolTipAppearance = new SimpleToolTipAppearance();
    private ComponentAppearance labelAppearance = new SimpleLabelAppearance();
    private ComponentAppearance scrollPaneAppearance = new SimpleScrollPaneAppearance();
    private ComponentAppearance textAreaAppearance = new SimpleTextAreaAppearance();
    
    private WindowAppearance windowAppearance = new SimpleWindowAppearance();
    private ScrollBarAppearance scrollBarAppearance = new SimpleScrollBarAppearance();
    private SliderAppearance sliderAppearance = new SimpleSliderAppearance();
        
    public static boolean isRoundRectanglesEnabled() {
        return roundRectanglesEnabled;
    }

    public static void setRoundRectanglesEnabled(boolean aRoundRectanglesEnabled) {
        roundRectanglesEnabled = aRoundRectanglesEnabled;
    }
    
    public String getName() {
        return "Simple";
    }
    
    public boolean isThemeable() {
        return true;
    }
    
    public void install() throws SlickException {
                ///////////////////
                // CACHE OBJECTS //
                ///////////////////
        
        //try loading
        //ResourceLoader will spit out a log message if there are problems
        
        //images
        if (checkBoxImage==null)
            checkBoxImage = tryImage("res/skin/simple/checkbox.png");
        if (closeButtonImage==null)
            closeButtonImage = tryImage("res/skin/simple/closewindow.png");
        if (selectCursor==null) 
            selectCursor = tryCursor("res/skin/shared/cursor_select.png", 4, 8);
            //selectCursor = tryCursor("res/skin/shared/cursor_hand.png", 6, 0);
        //if (resizeCursor==null)
        //    resizeCursor = tryCursor("res/skin/shared/cursor_resize.png", 4, 4);
        
        if (selectCursorListener==null && selectCursor!=null)
            selectCursorListener = new CursorListener(selectCursor);
        if (resizeCursorListener==null && resizeCursor!=null)
            resizeCursorListener = new CursorListener(resizeCursor);
        
        //fonts
        if (font==null)
            font = tryFont("res/skin/shared/verdana.fnt", "res/skin/shared/verdana.png");
    }
    
    private Cursor tryCursor(String ref, int x, int y) {
        try {
            return CursorLoader.get().getCursor(ref, x, y);
        } catch (Exception e) {
            Log.error("Failed to load and apply SUI 'select' cursor.", e);
            return null;
        }
    }
    
    private Image tryImage(String s) {
        try { return new ImageUIResource(s); }
        catch (Exception e) { return null; }
    }
    
    private Font tryFont(String s1, String s2) {
        try { return new FontUIResource.AngelCodeFont(s1, s2); }
        catch (Exception e) { return null; }
    }

    public void uninstall() throws SlickException {
    }
    
    public Image getCheckBoxImage() {
        return checkBoxImage;
    }
    
    public Image getCloseButtonImage() {
        return closeButtonImage;
    }
    
    public Font getFont() {
        return font;
    }
    
    public Cursor getSelectCursor() {
        return selectCursor;
    }

    public Cursor getResizeCursor() {
        return resizeCursor;
    }
    
    public SuiMouseListener getSelectCursorListener() {
        return selectCursorListener;
    }

    public SuiMouseListener getResizeCursorListener() {
        return resizeCursorListener;
    }
    
    public ComponentAppearance getContainerAppearance(SuiContainer comp) {
        return containerAppearance;
    }

    public ComponentAppearance getCheckBoxAppearance(SuiCheckBox comp) {
        return new SimpleCheckBoxAppearance(comp);
    }

    public WindowAppearance getWindowAppearance(SuiWindow comp) {
        return windowAppearance;
    }

    public ComponentAppearance getButtonAppearance(SuiButton comp) {
        return new SimpleButtonAppearance(comp);
    }

    public ComponentAppearance getToolTipAppearance(SuiToolTip comp) {
        return toolTipAppearance;
    }

    public ComponentAppearance getLabelAppearance(SuiLabel comp) {
        return labelAppearance;
    }
    
    public ComponentAppearance getToggleButtonAppearance(SuiToggleButton comp) {
        return new SimpleButtonAppearance(comp);
    }

    public ScrollBarAppearance getScrollBarAppearance(SuiScrollBar comp) {
        return scrollBarAppearance;
    }

    public ComponentAppearance getScrollPaneAppearance(SuiScrollPane comp) {
        return scrollPaneAppearance;
    }
    
    public SliderAppearance getSliderAppearance(SuiSlider comp) {
        return sliderAppearance;
    }
    
    public ComponentAppearance getTextFieldAppearance(SuiTextField comp) {
        return new SimpleTextFieldAppearance(comp);
    }
    
    public ComponentAppearance getTextAreaAppearance(SuiTextArea comp) {
        return textAreaAppearance;
    }
    
    private class CursorListener extends SuiMouseAdapter {
        
        private Cursor c;
        private boolean failed = false;
        private boolean dragging = false;
        private boolean inside = false;
        
        public CursorListener(Cursor c) {
            this.c = c;
        }
        
        public void mouseReleased(SuiMouseEvent ev) {
            dragging = false;
            if (!inside)
                release();
        }
        
        public void mouseDragged(SuiMouseEvent ev) {
            dragging = true;
        }
        
        public void mouseEntered(SuiMouseEvent ev) {
            inside = true;
            if (!failed) {
                try { Mouse.setNativeCursor(c); }
                catch (Exception e) {
                    failed = true; 
                }
            }
        }
        
        public void mouseExited(SuiMouseEvent ev) {
            inside = false;
            if (!dragging)
                release();
        }
        
        void release() {
            try { Mouse.setNativeCursor(null); }
            catch (Exception e) { }
        }
    }
}
