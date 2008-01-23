/*
 * SuiCheckBox_1.java
 *
 * Created on June 10, 2007, 7:20 PM
 */

package mdes.slick.sui;

import mdes.slick.sui.skin.SkinUtil;

import org.newdawn.slick.Font;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

/**
 * 
 * @author davedes
 */
public class CheckBox extends ToggleButton {
    
    private float boxHeight, boxWidth;
    private Padding outerPadding = new Padding();
   
    public static final int LEADING = 0;
    public static final int TRAILING = 1;
    
    private int boxPosition = LEADING;
    private Rectangle boxBounds = new Rectangle(0f,0f,0f,0f);
    
    /**
     * Creates a new instance of SuiCheckBox_1.
     */
    public CheckBox() {
        super(false);
        
        getInnerPadding().set(0);
        getPadding().set(4);
        
        setBoxWidth(15);
        setBoxHeight(15);
                
        setVerticalAlignment(CENTER_ALIGNMENT);
        setHorizontalAlignment(LEFT_ALIGNMENT);
        
        updateAppearance();
    }
    
    public CheckBox(String text) {
        this(null, text);
    }
    
    public CheckBox(Image image) {
        this(image, null);
    }
    
    public CheckBox(Image image, String text) {
        this();
        setImage(image);
        setText(text);
    }
        
    public void updateAppearance() {
        setAppearance(Sui.getSkin().getCheckBoxAppearance(this));
    }
    
    public void setHorizontalBoxPosition(int type) {
        if (type!=LEADING && type!=TRAILING)
            throw new IllegalArgumentException("type must be either LEADING or TRAILING");
        int old = this.boxPosition;
        this.boxPosition = type;
        
        //swap if necessary
        if (old!=this.boxPosition) {
            Padding p = getInnerPadding();
            float temp = p.left;
            p.left = p.right;
            p.right = temp;
        }
    }
    
    public int getHorizontalBoxPosition() {
        return boxPosition;
    }
    
    public void pack() {
        Font font = getFont();
        String text = getText();
        
        float objWidth = 0;
        float objHeight = 0;
        
        if (getImage()!=null) {
            objWidth = getImage().getWidth();
            objHeight = getImage().getHeight();
        }        
        
        if (getText()!=null && getText().length()!=0 && font!=null) {
            objWidth = Math.max(objWidth, getTextWidth());
            objHeight = Math.max(objHeight, getTextHeight());
        }
        
        objHeight = Math.max(objHeight, boxHeight);
        
        Padding outer = getPadding();
        Padding padding = getInnerPadding();
        setWidth(outer.left + padding.left + objWidth + padding.right + outer.right);
        setHeight(outer.top + padding.top + objHeight + padding.bottom + outer.bottom);
    }
        
    public Rectangle getAbsoluteBoxBounds() {
        float x = getAbsoluteX();
        float width = getBoxWidth();
        float height = getBoxHeight();
        
        int pos = getHorizontalBoxPosition();
        Padding pad = getPadding();
        if (pos==LEADING) {
            x += pad.left;
        } else if (pos==TRAILING) {
            x += getWidth() - width - pad.right;
        }
        
        int vert = getVerticalAlignment();
        float y = SkinUtil.getObjectY(vert, getAbsoluteY(), getHeight(), pad, height);
        
        boxBounds.setLocation(x, y);
        boxBounds.setWidth(width);
        boxBounds.setHeight(height);
        
        return boxBounds;
    }
            
    public float getBoxWidth() {
        return boxWidth;
    }

    public void setBoxWidth(int boxWidth) {
        this.boxWidth = boxWidth;
        updatePadding();
    }
    
    public void setText(String text) {
        super.setText(text);
        updatePadding();
    }
    
    private void updatePadding() {
        String text = getText();
        int off = 0;
        if (text!=null&&text.length()!=0) 
            off = 3;
        if (this.boxPosition==LEADING)
            getInnerPadding().left = boxWidth+off;
        else if (this.boxPosition==TRAILING)
            getInnerPadding().right = boxWidth+off;
    }
    
    public Padding getPadding() {
        return outerPadding;
    }
    
    public Padding getInnerPadding() {
        return super.getPadding();
    }

    public float getBoxHeight() {
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