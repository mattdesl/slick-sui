/*
 * Padding.java
 *
 * Created on November 8, 2007, 8:27 PM
 */

package mdes.slick.sui;

/**
 *
 * @author davedes
 */
public class Padding {
    
    public float top, left, bottom, right;
    
    /**
     * Creates a new instance of Padding
     */
    public Padding(float top, float left, float bottom, float right) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }
    
    public Padding(float num) {
        this(num,num,num,num);
    }
    
    public Padding() {
        this(0);
    }
    
    public boolean equals(Object obj) {
        if (obj instanceof Padding) {
            Padding p = (Padding)obj;
            return top==p.top && left==p.left && bottom==p.bottom && right==p.right;
        } else
            return false;
    }
    
    public Object clone() {
        return new Padding(top, left, bottom, right);
    }
    
    public void set(float top, float left, float bottom, float right) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }
    
    public void set(float p) {
        set(p, p, p, p);
    }
    
    public String toString() {
        return super.toString()+"["+top+", "+left+", "+bottom+", "+right+"]";
    }
}
