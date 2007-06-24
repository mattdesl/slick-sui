/*
 * SuiCheckBox.java
 *
 * Created on June 10, 2007, 7:20 PM
 */

package mdes.slick.sui;

import mdes.slick.sui.event.SuiActionEvent;
import mdes.slick.sui.event.SuiActionListener;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.GUIContext;

/**p
 * 
 * @author davedes
 */
public class SuiCheckBox extends SuiButton {
    
    private boolean selected = false;
    private Image selectedImage = null;
    private Image disabledSelectedImage = null;
    private Image rolloverSelectedImage = null;
    private int boxWidth;
    private int boxHeight;
        
    /** Creates a new instance of SuiCheckBox. */
    public SuiCheckBox() {
        super();
        boxWidth=13;
        boxHeight=13;
        
        setHorizontalPadding(4);
        setVerticalPadding(0);
        setVerticalAlignment(this.CENTER_ALIGNMENT);
        setHorizontalAlignment(this.LEFT_ALIGNMENT);
    }
    
    public SuiCheckBox(String text) {
        this(null, text);
    }
    
    public SuiCheckBox(Image image) {
        this(image, null);
    }
    
    public SuiCheckBox(Image image, String text) {
        this();
        setImage(image);
        setText(text);
    }
    
    public void pack() {
        init();
        
        Font font = getFont();
        String text = getText();
        int hp=getHorizontalPadding(), vp=getVerticalPadding();
        
        int objWidth = 0;
        int objHeight = 0;
        
        Image image = getImage();
        if (image!=null) {
            objWidth = hp*2 + image.getWidth();
            objHeight = vp*2 + image.getHeight();
        }
        
        if (text!=null && text.length()!=0 && font!=null) {
            objWidth = Math.max(objWidth, hp*2 + boxWidth+font.getWidth(text));
            objHeight = Math.max(objHeight, vp*2 + font.getHeight(text)-yoff);
        }
        
        objWidth = Math.max(objWidth, boxWidth);
        objHeight = Math.max(objHeight, boxHeight);
        
        setWidth(objWidth);
        setHeight(objHeight);
    }
        
    /**
     * Overridden to render the button graphics.
     *
     * @param c the GUIContext
     * @param g the Graphics context to draw on
     */
    protected void renderComponent(GUIContext c, Graphics g) {
        Color old = g.getColor();
        Font oldFont = g.getFont();
        
        int state = getState();
        Image img = getImageForState(state);
        
        if (img!=null)
            drawImage(g, img);
        
        //lastly we draw the text if it exists
        if (getText()!=null) {
            drawString(g, getText());
        }
        
        g.setFont(oldFont);
        g.setColor(old);
    }
    
    protected Image getImageForState(int state) {
        Image img = null;
        
        //if selected (ie: checked)
        if (selected) { 
            if (!isEnabled()) { //disabled selected
                img = getDisabledSelectedImage();
            } else if (state==ROLLOVER) { //rollover selected
                img = getRolloverSelectedImage();
            } else { //normal selected
                img = getSelectedImage();
            }
            //if it's still null after that (ie: no image has yet been set)
            //we will try to use the UI default
            if (img == null) {
                SuiSkin.CheckBoxUI ui = getUI();
                if (ui!=null) {
                    img = ui.getDefaultImage();
                }
            }
        }
        
        
        //if it's null we'll use the normal states
        if (img==null)
            img = super.getImageForState(state);
        return img;
    }
    
    protected float getObjectX(Object o, int width) {
        //if the object is a string, return a proper location
        //if the object is an image, return the absolute x (ie: 0)
        if (o instanceof String) {
            switch (getHorizontalAlignment()) {
                case CENTER_ALIGNMENT:
                default:
                    return getAbsoluteX() + boxWidth 
                                + ( (getWidth()-boxWidth)/2.0f - width/2.0f );
                case LEFT_ALIGNMENT:
                    return getAbsoluteX() + boxWidth + getHorizontalPadding();
                case RIGHT_ALIGNMENT:
                    float x = getAbsoluteX();
                    float boxMax = x+boxWidth;
                    float r = Math.max(boxMax, x + getWidth() 
                                    - getHorizontalPadding() - width);
                    return r;
            }
        } else if (o instanceof Image) {
            return getAbsoluteX() + (boxWidth/2.0f - width/2.0f);
        } else {
            return super.getObjectX(o, width);
        }
    }
    
    protected float getObjectY(Object o, int height) {
        //if the object is an image, return the center y
        if (o instanceof Image) {
            return getAbsoluteY() + (getHeight()/2.0f - height/2.0f);
        } else
            return super.getObjectY(o, height);
    }
    
    public boolean isSelected() {
        return selected;
    }
    
    public void setSelected(boolean b) {
        this.selected = b;
    }
    
    public void updateUI(SuiSkin skin) {
        setUI(skin.getCheckBoxUI());
    }
    
    public void press() {
        selected = !selected;
        super.press();
    }
    
    public SuiSkin.CheckBoxUI getUI() {
        return (SuiSkin.CheckBoxUI)super.getUI();
    }
    
    public Image getSelectedImage() {
        return selectedImage;
    }

    public void setSelectedImage(Image selectedImage) {
        this.selectedImage = selectedImage;
    }
  
    public int getBoxWidth() {
        return boxWidth;
    }

    public void setBoxWidth(int boxWidth) {
        this.boxWidth = boxWidth;
    }

    public Image getDisabledSelectedImage() {
        return disabledSelectedImage;
    }

    public void setDisabledSelectedImage(Image disabledSelectedImage) {
        this.disabledSelectedImage = disabledSelectedImage;
    }

    public Image getRolloverSelectedImage() {
        return rolloverSelectedImage;
    }

    public void setRolloverSelectedImage(Image rolloverSelectedImage) {
        this.rolloverSelectedImage = rolloverSelectedImage;
    }

    public int getBoxHeight() {
        return boxHeight;
    }

    public void setBoxHeight(int boxHeight) {
        this.boxHeight = boxHeight;
    }
}

        /*
        img = getImage
        
        if img != null
            if !enabled
                if selected  
                    img getDisabledSelectedImage
                else
                    img = getDisabledImage
            else if pressed
                img = getPressedImage
                if img == null
                    img = getSelectedImage
            else if selected
                if rollover
                    img = getRolloverSelectedImage
                    if img == null
                        img = getSelectedImage
                else
                    img = getSelectedImage
            else if rollover
                img = getRolloverIcon
        else
            img = getDefaultImage
        */