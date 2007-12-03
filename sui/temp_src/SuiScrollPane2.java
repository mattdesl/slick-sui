/*
 * SuiScrollPane2.java
 *
 * Created on November 12, 2007, 4:04 PM
 */

package mdes.slick.sui;

import mdes.slick.sui.skin.*;
import org.newdawn.slick.gui.GUIContext;
/**
 *
 * @author Alexandre Vieira, davedes
 */
public class SuiScrollPane2 extends SuiContainer {
    
    private SuiComponent view;
    public static final int SHOW_AS_NEEDED = 0;
    public static final int SHOW_ALWAYS = 1;
    public static final int SHOW_NEVER = 2;
    
    private int verticalScrollBarPolicy = SHOW_AS_NEEDED;
    private int horizontalScrollBarPolicy = SHOW_AS_NEEDED;
    
    private SuiScrollBar2 horizontalScrollBar = null;
    private SuiScrollBar2 verticalScrollBar = null;
    
    /**
     * Creates a new instance of SuiScrollPane2
     */
    public SuiScrollPane2(SuiComponent view) {
        setView(view);
        setHorizontalScrollBar(new SuiScrollBar2(this, SuiScrollBar2.HORIZONTAL)); 
        setVerticalScrollBar(new SuiScrollBar2(this, SuiScrollBar2.VERTICAL)); 
    }
    
    protected ComponentAppearance createAppearance() {
        return Sui.getSkin().getScrollPaneAppearance();
    }
    
    /** 
     * This method is overridden to correct the behavior of the scrollbars. 
     * 
     * @param visible The visibility of this component. 
     */ 
    public void setVisible(boolean visible) { 
        boolean old = this.isVisible();
        super.setVisible(visible); 
        if (old!=visible) {
            horizontalScrollBar.setVisible(this.isHorizontalScrollBarNeeded()); 
            verticalScrollBar.setVisible(this.isVerticalScrollBarNeeded()); 
        }
    }
    
    /** 
     * Updates this component. If an horizontal and/or vertical bar is needed 
     * it's set visible, otherwise it's set invisible. 
     * 
     * @param container The container (context) holding the game. 
     * @param delta     The time elapsed since the last update. 
     */ 
    protected void updateComponent(GUIContext container, int delta) { 
        super.updateComponent(container, delta); 
        if (isHorizontalScrollBarNeeded()) { 
            if (!horizontalScrollBar.isVisible()) { 
                horizontalScrollBar.setVisible(true); 
                horizontalScrollBar.updateHorizontalSize(); 
            } 
        } else { 
            horizontalScrollBar.setVisible(false); 
            verticalScrollBar.updateVerticalSize(); 
        } 
        if (isVerticalScrollBarNeeded()) { 
            if (!verticalScrollBar.isVisible()) {
                verticalScrollBar.setVisible(true); 
                verticalScrollBar.updateVerticalSize(); 
            } 
        } else { 
            verticalScrollBar.setVisible(false); 
            horizontalScrollBar.updateHorizontalSize(); 
        } 
    }
    
    public void setWidth(float width) {
        super.setWidth(width);
        verticalScrollBar.setX(getWidth()-verticalScrollBar.getWidth());
    }
    
    public void setHeight(float height) {
        super.setHeight(height);
        horizontalScrollBar.setY(getHeight()-horizontalScrollBar.getHeight());
    }

    public void setView(SuiComponent view) {
        if (view==null)
            throw new IllegalArgumentException("view cannot be null");
        
        SuiComponent old = this.view;
        if (old!=null)
            this.remove(old);
        this.view = view;
        view.setLocation(0.0f, 0.0f);
        this.add(view, 0); //insert below scroll bars
    }
    
    public SuiComponent getView() {
        return view;
    }
    
    public boolean isVerticalScrollBarNeeded() {
        switch (getVerticalScrollBarPolicy()) {
            default:
            case SHOW_AS_NEEDED:
                return view.getHeight() > getHeight();
            case SHOW_ALWAYS:
                return true;
            case SHOW_NEVER:
                return false;
        }
    }
    
    public boolean isHorizontalScrollBarNeeded() {
        switch (getHorizontalScrollBarPolicy()) {
            default:
            case SHOW_AS_NEEDED:
                return view.getWidth() > getWidth();
            case SHOW_ALWAYS:
                return true;
            case SHOW_NEVER:
                return false;
        }
    }

    public int getVerticalScrollBarPolicy() {
        return verticalScrollBarPolicy;
    }

    public void setVerticalScrollBarPolicy(int verticalScrollBarPolicy) {
        this.verticalScrollBarPolicy = verticalScrollBarPolicy;
    }

    public int getHorizontalScrollBarPolicy() {
        return horizontalScrollBarPolicy;
    }

    public void setHorizontalScrollBarPolicy(int horizontalScrollBarPolicy) {
        this.horizontalScrollBarPolicy = horizontalScrollBarPolicy;
    }

    public SuiScrollBar2 getHorizontalScrollBar() {
        return horizontalScrollBar;
    }

    public void setHorizontalScrollBar(SuiScrollBar2 horizontalScrollBar) {
        if (horizontalScrollBar==null)
            throw new IllegalArgumentException("scroll bar cannot be null");
        SuiScrollBar2 old = this.horizontalScrollBar;
        if (old!=null)
            remove(old);
        this.horizontalScrollBar = horizontalScrollBar;
        add(horizontalScrollBar);
        horizontalScrollBar.setVisible(false);
    }

    public SuiScrollBar2 getVerticalScrollBar() {
        return verticalScrollBar;
    }

    public void setVerticalScrollBar(SuiScrollBar2 verticalScrollBar) {
        if (verticalScrollBar==null)
            throw new IllegalArgumentException("scroll bar cannot be null");
        SuiScrollBar2 old = this.verticalScrollBar;
        if (old!=null)
            remove(old);
        this.verticalScrollBar = verticalScrollBar;
        add(verticalScrollBar);
        verticalScrollBar.setVisible(false);
    }


}
