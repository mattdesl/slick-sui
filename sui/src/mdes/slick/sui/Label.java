package mdes.slick.sui;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;

/**
 * Label is the base class for displaying
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
 * 
 * @author davedes
 * @since b.0.1
 */
public class Label extends Container {
    
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
    protected float yoff;
    
    /** The current horizontal alignment. */
    protected int horizAlignment = CENTER_ALIGNMENT;
    
    /** The current vertical alignment. */
    protected int vertAlignment = CENTER_ALIGNMENT;
    
    /** The current disabled color, initially gray. */
    protected Color disabledColor = Sui.getTheme().getDisabledMask().darker(.5f);
    
    //TODO: fix disabled foreground
        
    protected Color filter = new Color(1f, 1f, 1f, 1f);
        
    /**
     * Creates a new label with the specified text and image.
     *
     * @param image the image to be displayed (rendered below text)
     * @param text the text to be displayed
     */
    public Label(Image image, String text) {
        this(true);
        this.setImage(image);
        this.setText(text);
    }
    
    /** Creates a new label with the specified text. */
    public Label(String text) {
        this(null, text);
    }
    
    /**
     * Creates a new label with the specified image.
     *
     * @param image the image to be displayed
     */
    public Label(Image image) {
        this(image, null);
    }
    
    /** Creates a new empty label. */
    public Label() {
        this(null, null);
    }
    
    protected Label(boolean updateAppearance) {
        super(false);
        if (updateAppearance)
            updateAppearance();
    }
    
    public void updateAppearance() { 
        setAppearance(Sui.getSkin().getLabelAppearance(this)); 
        setPreferredSize(getPackedSize());
    }
    
    public void setImageFilter(Color filter) {
        this.filter = filter;
    }
    
    public Color getImageFilter() {
        return filter;
    }
        
    /**
     * Packs this label based on current font & text,
     * leaving a space for padding.
     */
    protected Dimension getPackedSize() {
        Font font = getFont();
        float objWidth = 0;
        float objHeight = 0;
        Dimension d = new Dimension();
        
        if (getImage()!=null) {
            objWidth = getImage().getWidth();
            objHeight = getImage().getHeight();
        }        
        
        if (getText()!=null && getText().length()!=0 && font!=null) {
            objWidth = Math.max(objWidth, getTextWidth());
            objHeight = Math.max(objHeight, getTextHeight());
        }
        
        Padding padding = getPadding();
        d.width = padding.left + objWidth + padding.right;
        d.height = padding.top + objHeight + padding.bottom;
        return d;
    }
    
    public void pack() {
        setSize(getPackedSize());
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
     * Gets the text being displayed.
     *
     * @return the text for this label
     */
    public String getText() {
        return text;
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
     * Gets the yoffset if the current font is an instanceof AngelCodeFont,
     * otherwise returns 0. This method on its own does not change the protected
     * variable <tt>yoffset</tt>. Whenever the text changes, this method is
     * used to store the new offset in the <tt>yoffset</tt> variable.
     *
     * @return the yoffset of the font if it is an instanceof AngelCodeFont,
     *				otherwise 0
     */
    protected float getYOffset(String s) {
        Font font = getFont();
        if (s==null||s.length()==0)
            return 0;
        else if (font instanceof AngelCodeFont)
            return ((AngelCodeFont)font).getYOffset(s);
        else
            return 0;
    }
    
    public float getYOffset() {
        return yoff;
    }
        
    public float getTextHeight() {
        String str = getText()!=null ? getText() : "";
        return getFont().getHeight(str)-yoff;
        //return getFont().getLineHeight();
    }
    
    public float getTextWidth() {
        String str = getText()!=null ? getText() : "";
        return getFont().getWidth(str);
    }
}