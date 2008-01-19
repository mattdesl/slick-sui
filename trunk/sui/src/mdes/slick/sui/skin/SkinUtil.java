/*
 * SkinUtil.java
 *
 * Created on November 7, 2007, 8:27 PM
 */

package mdes.slick.sui.skin;

import mdes.slick.sui.Padding;
import mdes.slick.sui.Sui;
import mdes.slick.sui.Button;
import mdes.slick.sui.CheckBox;
import mdes.slick.sui.Component;
import mdes.slick.sui.Label;
import mdes.slick.sui.ToggleButton;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author davedes
 */
public class SkinUtil {
    
    private final static int TEXT_OFFSET = -1;
    
    /**
     * 
     */
    protected SkinUtil() {
    }
    
    public static boolean installImage(Label c, Image img) {
        Image compImg = c.getImage();
        if (compImg==null || compImg instanceof ImageUIResource) {
            c.setImage(img);
        }
        return compImg != c.getImage();
    }
    
    public static boolean installColors(Component c, Color background, Color foreground) {
        Color bg = c.getBackground();
        
        if (bg==null || bg instanceof ColorUIResource) {
            c.setBackground(background);
        }
        Color fg = c.getForeground();
        if (fg==null || fg instanceof ColorUIResource) {
            c.setForeground(foreground);
        }
        
        return fg!=c.getForeground() || bg!=c.getBackground();
    }
    
    public static boolean installFont(Component c, Font f) {
        Font compFont = c.getFont();
        if (compFont==null || compFont==Sui.getDefaultFont() || compFont instanceof FontUIResource) {
            if (f==null)
                f = Sui.getDefaultFont();
            c.setFont(f);
        }
        return compFont != c.getFont();
    }
    
    public static void renderComponentBase(Graphics g, Component comp) {
        Color background = comp.getBackground();
        boolean opaque = comp.isOpaque();
        if (background!=null && opaque) {
            Color old = g.getColor();
            g.setColor(background);
            Rectangle bounds = comp.getAbsoluteBounds();
            g.fill(bounds);            
            g.setColor(old);
        }
    }
    
    public static void renderLabelBase(Graphics g, Label label, String str, Image image, float textXOff, float textYOff) { 
        if (image==null && (str==null || str.length()==0))
            return;
        
        float abx = label.getAbsoluteX();
        float aby = label.getAbsoluteY();
        float width = label.getWidth();
        float height = label.getHeight();

        Padding pad = label.getPadding();
        int hAlign = label.getHorizontalAlignment();
        int vAlign = label.getVerticalAlignment();
        
        if (image!=null) {
            Color filter = label.getImageFilter();
            int iw = image.getWidth();
            int ih = image.getHeight();

            float x = getObjectX(hAlign, abx, width, pad, iw);
            float y = getObjectY(vAlign, aby, height, pad, ih);
            
            g.drawImage(image, (int)x, (int)y, filter);
        }
                
        if (str!=null && str.length()!=0) {
            Font font = label.getFont();
            
            float tw = label.getTextWidth();
            float th = label.getTextHeight();
                        
            float x = getObjectX(hAlign, abx, width, pad, tw) + textXOff;
            float y = getObjectY(vAlign, aby, height, pad, th) + textYOff;
            
            Color oldColor = g.getColor();
            Font oldFont = g.getFont();

            g.setColor(label.isEnabled() ? label.getForeground() : label.getDisabledForeground());
            g.setFont(font);

            g.drawString(str, (int)x, (int)y);
            
            g.setColor(oldColor);
            g.setFont(oldFont);
        }
    }
    
    public static void renderLabelBase(Graphics g, Label label) {
        String text = label.getText();
        Image image = label.getImage();
        renderLabelBase(g, label, text, image, TEXT_OFFSET, TEXT_OFFSET);
    }
    
    public static void renderButtonBase(Graphics g, Button button) {
        String text = button.getText();
        Image image = getButtonImage(button);
        
        int textOff = button.getState()==Button.DOWN ? 0 : TEXT_OFFSET;
        renderLabelBase(g, button, text, image, textOff, textOff);
    }
    
    public static void renderCheckBoxBase(Graphics g, CheckBox check) {
        String text = check.getText();
        Image image = getToggleButtonImage(check);
        
        //box on left side
        int horiz = check.getHorizontalAlignment();
        int vert = check.getVerticalAlignment();
        int mult = check.getHorizontalBoxPosition()==CheckBox.LEADING ? 1 : -1;
        
        Padding innerPad = check.getInnerPadding();
        float xoff = TEXT_OFFSET;
        if (horiz==Button.LEFT_ALIGNMENT)
            xoff += innerPad.left * mult;
        else if (horiz==Button.RIGHT_ALIGNMENT)
            xoff -= innerPad.right * mult;
        else if (horiz==Button.CENTER_ALIGNMENT) {
            xoff += check.getBoxWidth() * mult;
        }
        
        float yoff = TEXT_OFFSET;
        if (vert==Button.TOP_ALIGNMENT)
            yoff += innerPad.top;
        else if (vert==Button.BOTTOM_ALIGNMENT)
            yoff -= innerPad.bottom;
        
        //uncomment to get the text "sink in" effect when clicking check box
        //if (check.getState()==Button.DOWN) {
        //    xoff++;
        //    yoff++;
        //}
        renderLabelBase(g, check, text, image, xoff, yoff);
    }
    
    /**
     * Determines the Image to draw based on the given state.
     * <p>
     * <b>UP</b>: getImage() if enabled, otherwise getDisabledImage()
     * <b>DOWN</b>: getDownImage() if it exists, otherwise getImage()
     * <b>ROLLOVER</b>: getRolloverImage() if it exists, otherwise getImage()
     */
    public static Image getButtonImage(Button button) {
        Image downImage = button.getDownImage();
        Image rolloverImage = button.getRolloverImage();
        switch (button.getState()) {
            default:
            case Button.UP:
                return button.isEnabled() ? button.getImage() : button.getDisabledImage();
            case Button.DOWN:
                return downImage!=null ? downImage : button.getImage();
            case Button.ROLLOVER:
                return rolloverImage!=null ? rolloverImage : button.getImage();
        }
    }
    
    /**
     * Determines the Image to draw based on the given check box state.
     */    
    public static Image getToggleButtonImage(ToggleButton btn) {
        Image img = null;
        
        //if selected (ie: checked)
        if (btn.isSelected()) { 
            if (!btn.isEnabled()) { //disabled selected
                img = btn.getDisabledSelectedImage();
            } else if (btn.getState()==Button.ROLLOVER) { //rollover selected
                img = btn.getRolloverSelectedImage();
            } else { //normal selected
                img = btn.getSelectedImage();
            }
        }
        //if it's null we'll try using the normal image
        if (img==null)
            img = getButtonImage(btn);
        return img;
    }
    
    /**
     * Gets the y position of an object that has the specified height,
     * based on alignment and padding.
     *
     * @param align the alignment type
     * @param x the component's x position
     * @param width the component's width
     * @param hPad the horizontal padding (used for left/right align)
     * @param objWidth the width of the object being aligned
     * @deprecated use getObjectX(Object, int)
     */
    public static float getObjectX(int align, float x, float width, Padding pad, float objWidth) {
        //x position
        switch (align) {
            case Label.CENTER_ALIGNMENT:
            default:
                return x + ( width/2.0f - objWidth/2.0f );
            case Label.LEFT_ALIGNMENT:
                return x + pad.left;
            case Label.RIGHT_ALIGNMENT:
                return x + width - pad.right - objWidth;
        }
    }
    
    public static float getObjectY(int align, float y, float height, Padding pad, float objHeight) {
        //y position
        switch (align) {
            case Label.CENTER_ALIGNMENT:
            default:
                return Math.max(y, y+(height/2.0f - objHeight/2.0f));
            case Label.TOP_ALIGNMENT:
                return y + pad.top;
            case Label.BOTTOM_ALIGNMENT:
                return Math.max(y, y+height - pad.bottom - objHeight);
        }
    }
}
