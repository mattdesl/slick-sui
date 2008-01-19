/*
 * Container.java
 *
 * Created on November 6, 2007, 7:05 PM
 */

package mdes.slick.sui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import mdes.slick.sui.layout.StaticLayout;
import mdes.slick.sui.layout.LayoutManager;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author davedes
 */
public class Container extends Component {
    
    private ZComparator zCompare = new ZComparator();
    boolean childrenDirty = true;
    private boolean clipEnabled;
    private ArrayList children = new ArrayList();
    protected LayoutManager layout = new StaticLayout();
        
    /**
     * Creates a new instance of Container
     */
    public Container() {
        this(true);
        children = new ArrayList();
    }
    
    /** 
     * A constructor that should only be used by subclasses. This constructor 
     * allows subclasses to utilize a custom call to updateAppearance()  by
     * passing false. This avoids issues that would be caused by updating 
     * the appearance before, say, certain variables are initialized on the
     * subclass. Most users don't need to worry about this constructor.
     */
    protected Container(boolean updateAppearance) {
        if (updateAppearance)
            updateAppearance();
    }
    
    public void updateAppearance() {
        setAppearance(Sui.getSkin().getContainerAppearance(this));
        
    }
    
    public void setZIndex(int z) {
        super.setZIndex(z);
        if (parent!=null && parent instanceof Container)
            parent.childrenDirty = true;
    }
        
    public void setLayout(LayoutManager layout) {
        if (layout==null)
            throw new IllegalArgumentException("layout cannot be null");
        this.layout = layout;
    }

    public LayoutManager getLayout() {
        return layout;
    }
    
    /**
     * Called to ensure the z-ordering of 
     * this container's children is correct.
     * If it isn't, it will be sorted appropriately.
     */
    public void ensureZOrder() {
        if (childrenDirty) {
            Collections.sort(children, zCompare);
            childrenDirty = false;
        }
    }
    
    /**
     * Returns an array of this Container's children.
     * 
     * 
     * @return an array of Container children
     */
    public Component[] getChildren() {
        ensureZOrder();
        Component[] c = new Component[children.size()];
        return (Component[])children.toArray(c);
    }
    
    /**
     * Adds a child to this Container.
     * 
     * 
     * @param child the child container to add
     * @return <tt>true</tt> if the child list changed as a result
     *              of this call
     */
    public boolean add(Component child) {
        int old = children.size();
        if (!containsChild(child)) {
            childrenDirty = true;
            child.parent = this;
            child.display = this.findDisplay();
            child.displayDirty = child.display==null;
            children.add(child);
            layout.doLayout(this);
        }
        return old != children.size();
    }
    
    
    /**
     * Inserts a child to this Container at the specified index.
     * 
     * 
     * @param child the child container to add
     * @param index the index to insert it to
     * @return <tt>true</tt> if the child list changed as a result
     *              of this call
     */
    public boolean add(Component child, int index) {
        int old = children.size();
        if (!containsChild(child)) {
            childrenDirty = true;
            child.parent = this;
            child.display = this.findDisplay();
            children.add(index, child);
            layout.doLayout(this);
        }
        return old != children.size();
    }
    
    /**
     * Removes the child from this Container if it exists.
     * 
     * 
     * @param child the child container to remove
     * @return <tt>true</tt> if the child was removed
     */
    public boolean remove(Component child) {
        boolean contained = children.remove(child);
        if (contained) {
            childrenDirty = true;
            child.parent = null;
            child.display = null;
            child.releaseFocus();
            layout.doLayout(this);
        }
        return contained;
    }
    
    /**
     * Gets the child at the specified index.
     *
     * @param index the index of the child
     * @return the child
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Component getChild(int index) {
        ensureZOrder();
        return (Component)children.get(index);
    }
    
    /**
     * Gets the number of components in this container.
     *
     * @return the number of components in this container
     */
    public int getChildCount() {
        return children.size();
    }
    
    /**
     * Whether this container contains the specified Container.
     * 
     * 
     * @param c the container to check against
     * @return <tt>true</tt> if this container contains the specified
     * 			Container
     */
    public boolean containsChild(Component c) {
        return children.contains(c); //TODO: use binarySearch(c,zcomp) if not dirty
    }
    
    /**
     * Removes all children from this Container.
     */
    public void removeAll() {
        for (int i=0; i<getChildCount(); i++) {
            if (!childrenDirty)
                childrenDirty = true;
            Component c = getChild(i);
            c.parent = null;
            c.display = null;
        }
        children.clear();
    }
    
    /**
     * Called to recursively render all children of this container.
     *
     * @param container the GUIContext we are rendering to
     * @param g the Graphics context we are rendering with
     */
    protected void renderChildren(GUIContext container, Graphics g) {
        ensureZOrder();
        for (int i=0; i<getChildCount(); i++) {
            Component child = getChild(i);
            
            Rectangle oldclip = g.getWorldClip();
            Rectangle clip = new Rectangle(getAbsoluteX(), getAbsoluteY(), getWidth(), getHeight());
            g.setWorldClip(clip);
            
            //TODO: fix clipping
            
            child.render(container, g);
            g.setWorldClip(oldclip);
        }
    }
    
    /**
     * Called to recursively update all children of this container.
     *
     * @param container the GUIContext we are rendering to
     * @param delta the delta time (in ms)
     */
    protected void updateChildren(GUIContext container, int delta) {
        ensureZOrder();
        for (int i=0; i<getChildCount(); i++)
            getChild(i).update(container, delta);
    }
    
    protected void updateComponent(GUIContext container, int delta) {
        super.updateComponent(container, delta);
        updateChildren(container, delta);
    }
    
    protected void renderComponent(GUIContext container, Graphics g) {
        super.renderComponent(container, g);
        renderChildren(container, g);
    }
    
    private class ZComparator implements Comparator{
        public int compare(Object o1, Object o2) {
            Component c1 = (Component)o1;
            Component c2 = (Component)o2;
            int res;
            
            if (c1 == null || c2 == null || c1.equals(c2)) {
                res = 0;
            } else {
                res = (c2.getZIndex() < c1.getZIndex() ? 1 : -1);
            }
            
            return res;
        }
    }

    public boolean isClipEnabled() {
        return clipEnabled;
    }

    public void setClipEnabled(boolean clipEnabled) {
        this.clipEnabled = clipEnabled;
    }
}
