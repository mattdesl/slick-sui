/*
 * Dimension.java
 *
 * Created on November 6, 2007, 6:42 PM
 */

package mdes.slick.sui;

/**
 * A simple Dimension (width, height) for components.
 * 
 * @author davedes
 * @since b2.0
 */
public class Dimension {
    
    /** The width of this dimension. */
    public float width;
    
    /** The height of this dimension. */
    public float height;
    
    /**
     * Creates a new instance of Dimension with the specified width and height.
     * @param width the width of this dimension
     * @param height the height of this dimension
     */
    public Dimension(float width, float height) {
        this.width = width;
        this.height = height;
    }
    
    /**
     * Creates a new instance of Dimension with a width and height of zero.
     */
    public Dimension() {
        this(0, 0);
    }
    
    /**
     * Creates a new instance of Dimension using the width and height of the 
     * specified Dimension object.
     * @param d a dimension
     */
    public Dimension(Dimension d) {
        this(d.width, d.height);
    }
    
    /**
     * Sets the size of this Dimension to the sepcified width and height.
     * @param width the new width
     * @param height the new height
     */
    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }
    
    /**
     * Sets the size of this dimension to the size of the given dimension object.
     * @param d the dimension to use for the new width and height
     */
    public void setSize(Dimension d) {
        this.setSize(d.width, d.height);
    }
    
    /**
     * Returns <tt>true</tt> if the given Object is an instance of Dimension with the
     * same width and height as this dimension, otherwise returns <tt>false</tt>.
     * @param o the object to check against
     * @return <tt>true</tt> if the two dimensions are equal
     */
    public boolean equals(Object o) {
        if (o instanceof Dimension) {
            Dimension d = (Dimension)o;
            return d.width==this.width && d.height==this.height;
        } else
            return false;
    }
    
    /**
     * Creates a new object of the same class as this object. 
     * @return a clone of this instance. 
     */
    public Object clone() {
        return new Dimension(this);
    }
    
    /**
     * Returns a String representation of this Dimension.
     * @return a String representing this Dimension.
     */
    public String toString() {
        return super.toString()+"["+width+", "+height+"]";
    }
}