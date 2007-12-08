/*
 * Padding.java
 *
 * Created on November 8, 2007, 8:27 PM
 */

package mdes.slick.sui;

/**
 * The Padding class holds the basic insets for a component. These insets include top, left, right and bottom size.
 * @author davedes
 */
public class Padding {
    
    /**
     * The top spacing.
     */
    public float top;
    /**
     * The left spacing.
     */
    public float left; 
    /**
     * The bottom spacing.
     */
    public float bottom;
    /**
     * The right spacing.
     */
    public float right;
    
    /**
     * Creates a new instance of Padding with the specified spacings.
     * @param top the top spacing
     * @param left the left spacing
     * @param bottom the bottom spacing
     * @param right the right spacing
     */
    public Padding(float top, float left, float bottom, float right) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }
    
    /**
     * Creates a new instance of Padding with the specified spacing.
     * @param num the spacing to apply to all edges
     */
    public Padding(float num) {
        this(num,num,num,num);
    }
    
    /**
     * Creates a new instance of Padding with zero spacing.
     */
    public Padding() {
        this(0);
    }
    
    /**
     * Creates a new instance of Padding from the specified Padding object.
     * @param pad the Padding object to use for the spacing
     */
    public Padding(Padding pad) {
        this(pad.top, pad.left, pad.bottom, pad.right);
    }
    
    /**
     * Returns <tt>true</tt> if the given Object is an instance of Padding with the
     * same spacing as this Padding, otherwise returns <tt>false</tt>.
     * @param obj the object to check against
     * @return <tt>true</tt> if the two Padding objects are equal
     */
    public boolean equals(Object obj) {
        if (obj instanceof Padding) {
            Padding p = (Padding)obj;
            return top==p.top && left==p.left && bottom==p.bottom && right==p.right;
        } else
            return false;
    }
    
    /**
     * Creates a new object of the same class as this object. 
     * @return a clone of this instance. 
     */
    public Object clone() {
        return new Padding(this);
    }
    
    /**
     * Sets this Padding to the spacing of the specified Padding object.
     * @param pad the Padding to use for the new spacings
     */
    public void set(Padding pad) {
        set(pad.top, pad.left, pad.bottom, pad.right);
    }
    
    /**
     * Sets this Padding to the specified spacings.
     * @param top the top spacing
     * @param left the left spacing
     * @param bottom the bottom spacing
     * @param right the right spacing
     */
    public void set(float top, float left, float bottom, float right) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }
    
    /**
     * Sets this Padding to the specified spacing.
     * @param p the spacing to apply to all edges
     */
    public void set(float p) {
        set(p, p, p, p);
    }
    
    /**
     * Returns a String representation of this Dimension.
     * @return a String representing this Dimension.
     */
    public String toString() {
        return super.toString()+"["+top+", "+left+", "+bottom+", "+right+"]";
    }
}
