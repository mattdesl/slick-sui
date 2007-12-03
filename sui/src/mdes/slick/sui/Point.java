/*
 * Point.java
 *
 * Created on November 6, 2007, 6:46 PM
 */

package mdes.slick.sui;

/**
 * A simple Point class to hold (x,y) coordinates.
 * @author davedes
 * @since b2.0
 */
public class Point {
    
    /**
     * The x position of this point.
     */
    public float x;
    /**
     * The y position of this point.
     */
    public float y;
    
    /** Creates a new instance of Point at (0,0). */
    public Point() {
        this(0, 0);
    }
    
    /**
     * Creates a new instance of Point with the specified x and y location.
     * @param x the x position in the coordinate space
     * @param y the y position in the coordinate space
     */
    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Creates a new instance of Point using the (x,y) coordinates from the specified
     * point.
     * @param p a point
     */
    public Point(Point p) {
        this(p.x, p.y);
    }
    
    /**
     * Sets the location of this Point to the specified x and y positions.
     * @param x the new x position
     * @param y the new y position
     */
    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Sets the location of this Point using the location of the specified
     * Point object.
     * @param p a point to use for new x and y positions
     */
    public void setLocation(Point p) {
        setLocation(p.x, p.y);
    }
    
    /**
     * Returns <tt>true</tt> if the given Object is an instance of Point with the
     * same location as this point, otherwise returns <tt>false</tt>.
     * @param o the object to check against
     * @return <tt>true</tt> if the two points are equal
     */
    public boolean equals(Object o) {
        if (o instanceof Point) {
            Point p = (Point)o;
            return p.x==this.x && p.y==this.y;
        } else
            return false;
    }
    
    /**
     * Creates a new object of the same class as this object. 
     * @return a clone of this instance.
     */
    public Object clone() {
        return new Point(this);
    }
    
    /**
     * Returns a String representation of this Point.
     * @return a String representing this Point
     */
    public String toString() {
        return super.toString()+"("+x+", "+y+")";
    }
}


