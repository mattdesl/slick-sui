package mdes.slick.sui;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.GUIContext;

/**
 * SuiLabel is the base class for displaying
 * a String and/or Image on a component. Text is
 * always drawn over images, which are drawn over
 * the color background (if opaque).
 * <p>
 * Text and images can be aligned horizontally and/or
 * vertically through setHorizontalAlignment
 * and setVerticalAlignment.
 * <p>
 * All labels start with a padding of 0, initially
 * centered.
 *
 * @author davedes
 * @since b.0.1
 */
public class SuiLabel extends SuiContainer {
    
    /** A constant for the horizontal alignment. */
    public static final int LEFT_ALIGNMENT = 0;
    
    /** A constant for the horizontal alignment. */
    public static final int RIGHT_ALIGNMENT = 1;
    
    /** A constant for the vertical/horizontal alignment. */
    public static final int CENTER_ALIGNMENT = 2;
    
    /** A constant for the vertical alignment. */
    public static final int TOP_ALIGNMENT = 3;
    
    /** A constant for the vertical alignment. */
    public static final int BOTTOM_ALIGNMENT = 4;
        
    /** The text to be displayed. */
    private String text = null;
    
    /** The image to be displayed. */
    private Image image = null;
    
    /** A cached yoff. */
    protected int yoff;
    
    /** The current horizontal alignment. */
    protected int horizAlignment = CENTER_ALIGNMENT;
    
    /** The current vertical alignment. */
    protected int vertAlignment = CENTER_ALIGNMENT;
    
    /** The current amount of horizontal padding. */
    private int horizontalPadding = 0;
    
    private int verticalPadding = 0;
      
    /** The current disabled color, initially gray. */
    protected Color disabledColor = Color.gray;
    
    protected Color filter = new Color(1f, 1f, 1f, 1f);
        
    /**
     * Creates a new label with the specified text and image.
     *
     * @param image the image to be displayed (rendered below text)
     * @param text the text to be displayed
     */
    public SuiLabel(Image image, String text) {
        this.setImage(image);
        this.setText(text);
        this.yoff = getYOffset(text);
    }
    
    /** Creates a new label with the specified text. */
    public SuiLabel(String text) {
        this.setText(text);
        this.yoff = getYOffset(text);
    }
    
    /**
     * Creates a new label with the specified image.
     *
     * @param image the image to be displayed
     */
    public SuiLabel(Image image) {
        this.setImage(image);
    }
    
    /** Creates a new empty label. */
    public SuiLabel() {
    }
    
    public void setImageFilter(Color filter) {
        this.filter = filter;
    }
    
    public Color getImageFilter() {
        return filter;
    }
    
    /** Called on creation. Initially does nothing. */
    public void updateUI(SuiSkin skin) {
        setUI(skin.getLabelUI());
    }
    
    public SuiSkin.LabelUI getUI() {
        return (SuiSkin.LabelUI)super.getUI();
    }
    
    /**
     * Packs this label based on current font & text,
     * leaving a space for padding.
     */
    public void pack() {
        init();
        
        Font font = getFont();
        int hp=getHorizontalPadding(), vp=getVerticalPadding();
        
        int objWidth = 0;
        int objHeight = 0;
        
        if (getImage()!=null) {
            objWidth = getImage().getWidth();
            objHeight = getImage().getHeight();
        }        
        
        if (getText()!=null && getText().length()!=0 && font!=null) {
            objWidth = Math.max(objWidth, font.getWidth(getText()));
            objHeight = Math.max(objHeight, font.getHeight(getText())-yoff);
        }
        
        setWidth(hp*2 + objWidth);
        setHeight(vp*2 + objHeight);
    }
    
    public void setFont(Font f) {
        super.setFont(f);
        this.yoff = getYOffset(getText());
    }
        
    /**
     * Sets the disabled foreground color to be
     * used.
     *
     * @param c the new foreground color
     */
    public void setDisabledForeground(Color c) {
        this.disabledColor = c;
    }
    
    /**
     * Gets the disabled foreground color.
     *
     * @return the disabled foreground color
     */
    public Color getDisabledForeground() {
        return disabledColor;
    }
    
    /**
     * Sets the Image to be displayed.
     *
     * @param i the Image to draw
     */
    public void setImage(Image i) {
        this.image = i;
    }
    
    /**
     * Gets the Image being displayed.
     *
     * @return the label's image
     */
    public Image getImage() {
        return image;
    }
    
    /**
     * Sets the text to be displayed.
     *
     * @param text the text to draw
     */
    public void setText(String text) {
        this.text = text;
        this.yoff = getYOffset(text);
    }
    
    /**
     * Gets the padding of this label.
     * This will get the max of the horizontal/vertical
     * padding.
     *
     * @return the padding, in pixels
     * @deprecated use horizontal/vertical padding instead
     */
    public int getPadding() {
        return Math.max(getHorizontalPadding(), getVerticalPadding());
    }
    
    /**
     * Sets the padding of this label.
     *
     * @param i the padding, in pixels
     */
    public void setPadding(int i) {
        setHorizontalPadding(i);
        setVerticalPadding(i);
    }
    
    /**
     * Gets the text being displayed.
     *
     * @return the text for this label
     */
    public String getText() {
        return text;
    }
    
    /**
     * Overridden to render the label.
     *
     * @param c the GUIContext to draw in
     * @param g the Graphics context to draw with
     */
    protected void renderComponent(GUIContext c, Graphics g) {
        Image img = getImage();
        if (img!=null) {
            drawImage(g, img);
        }
        
        String str = getText();
        if (str!=null && str.length()!=0) {
            drawString(g, str);
        }
    }
    
    /**
     * Sets the horizontal alignment of the text/image.
     *
     * @param horizAlignment the alignment constant; either LEFT_ALIGNMENT,
     *							RIGHT_ALIGNMENT, or CENTER_ALIGNMENT
     */
    public void setHorizontalAlignment(int horizAlignment) {
        this.horizAlignment = horizAlignment;
    }
    
    /**
     * Gets the horizontal alignment of the text/image.
     *
     * @return the horizontal alingment constant
     */
    public int getHorizontalAlignment() {
        return horizAlignment;
    }
    
    /**
     * Sets the vertical alignment of the text/image.
     *
     * @param vertAlignment the alignment constant; either TOP_ALIGNMENT,
     *							BOTTOM_ALIGNMENT, or CENTER_ALIGNMENT
     */
    public void setVerticalAlignment(int vertAlignment) {
        this.vertAlignment = vertAlignment;
    }
    
    /**
     * Gets the vertical alignment of the text/image.
     *
     * @return the vertical alingment constant
     */
    public int getVerticalAlignment() {
        return vertAlignment;
    }
    
    //TODO: support for alignment X and Y
    //TODO: tweak getTextX/Y by reusing width/height
    
    /**
     * Should be used by subclasses to draw an Image at the correct
     * location (based on alignment & padding).
     *
     * @param g the graphics to draw with
     * @param image the image to draw
     */
    protected void drawImage(Graphics g, Image image) {
        int iw = image.getWidth();
        int ih = image.getHeight();
        
        int x = (int)getObjectX(image, iw);
        int y = (int)getObjectY(image, ih);
        
        g.drawImage(image, x, y, filter);
    }
    
    /**
     * Should be used by subclasses to draw a String at the correct
     * location (based on yoffset, alignment & padding).
     *
     * @param g the graphics to draw with
     * @param str the text to draw
     */
    protected void drawString(Graphics g, String str) {
        int yoff = getYOffset(str);
        
        Font font = getFont();
        
        int tw = font.getWidth(str);
        int th = font.getHeight(str)-yoff;
        
        float x = getObjectX(str, tw);
        float y = getObjectY(str, th);
        
        //DEBUG
        //g.setColor(Color.red);
        //g.drawRect(x,y,tw,th);
        
        drawString(g, str, x, y-yoff);
    }
    
    /**
     * Should be used by subclasses to draw a String at the correct
     * location (based on yoffset).
     *
     * @param g the graphics to draw with
     * @param str the text to draw
     * @param x the absolute x value
     * @param y the absolute y value
     */
    protected void drawString(Graphics g, String str, float x, float y) {
        if (getFont()==null||getForeground()==null)
            return;
        
        //TODO: check str.equals ??
        //TODO: use textChanged boolean switch
        
        //attempts to use cached yoff
        int yoff = str==getText() ? this.yoff : getYOffset(str);
                
        Color oldColor = g.getColor();
        Font oldFont = g.getFont();
        
        g.setColor(isEnabled() ? getForeground() : disabledColor);
        g.setFont(getFont());
                
        g.drawString(str, x, y);
               
        g.setColor(oldColor);
        g.setFont(oldFont);
    }
    
    /**
     * Gets the y position of an object that has the specified height,
     * based on alignment and padding.
     *
     * @param o included for easier identification, may be null
     * @param height the height of the object (eg: image, text)
     */
    protected float getObjectY(Object o, int height) {
        return getObjectY(height);
    }
    
    /**
     * Gets the y position of an object that has the specified height,
     * based on alignment and padding.
     *
     * @param o included for easier identification, null object 
     * @param height the height of the object (eg: image, text)
     * @deprecated use getObjectY(Object, int)
     */
    protected float getObjectY(int height) {
        float y = getAbsoluteY();
        //y position
        switch (vertAlignment) {
            case CENTER_ALIGNMENT:
            default:
                return Math.max(y, y+(getHeight()/2.0f - height/2.0f));
            case TOP_ALIGNMENT:
                return Math.max(y, y+verticalPadding);
            case BOTTOM_ALIGNMENT:
                return Math.max(y, y+getHeight() - verticalPadding - height);
        }
    }
    
    /**
     * Gets the x position of an object that has the specified width,
     * based on alignment and padding.
     *
     * @param o included for easier identification, may be null
     * @param width the width of the object (eg: image, text)
     */
    protected float getObjectX(Object o, int width) {
        return getObjectX(width);
    }
    
    /**
     * Gets the y position of an object that has the specified height,
     * based on alignment and padding.
     *
     * @param o included for easier identification, null object 
     * @param height the height of the object (eg: image, text)
     * @deprecated use getObjectX(Object, int)
     */
    protected float getObjectX(int width) {
        //x position
        switch (horizAlignment) {
            case CENTER_ALIGNMENT:
            default:
                return getAbsoluteX() + ( getWidth()/2.0f - width/2.0f );
            case LEFT_ALIGNMENT:
                return getAbsoluteX() + horizontalPadding;
            case RIGHT_ALIGNMENT:
                return getAbsoluteX() + getWidth() - horizontalPadding - width;
        }
    }
    
    /**
     * Gets the yoffset if the current font is an instanceof AngelCodeFont,
     * otherwise returns 0. This method on its own does not change the protected
     * variable <tt>yoffset</tt>. Whenever the text changes, this method is
     * used to store the new offset in the <tt>yoffset</tt> variable.
     *
     * @return the yoffset of the font if it is an instanceof AngelCodeFont,
     *				otherwise 0
     */
    protected int getYOffset(String s) {
        Font font = getFont();
        if (s==null||s.length()==0)
            return 0;
        else if (font instanceof AngelCodeFont)
            return ((AngelCodeFont)font).getYOffset(s);
        else
            return 0;
    }

    public int getHorizontalPadding() {
        return horizontalPadding;
    }

    public void setHorizontalPadding(int horizontalPadding) {
        this.horizontalPadding = horizontalPadding;
    }

    public int getVerticalPadding() {
        return verticalPadding;
    }

    public void setVerticalPadding(int verticalPadding) {
        this.verticalPadding = verticalPadding;
    }
}