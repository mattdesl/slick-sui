/*
 * ScrollPane.java
 *
 * Created on December 3, 2007, 8:03 PM
 */

package mdes.slick.sui;

import mdes.slick.sui.event.ChangeEvent;
import mdes.slick.sui.event.ChangeListener;
import mdes.slick.sui.event.ResizeListener;
import mdes.slick.sui.skin.ComponentAppearance;
import mdes.slick.sui.skin.ScrollPaneAppearance;

import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author davedes and Olivier Arteau
 */
public class ScrollPane extends Container implements ScrollConstants {
    
    public static final int SCROLLBAR_AS_NEEDED = 0;
    public static final int SCROLLBAR_ALWAYS = 10;
    public static final int SCROLLBAR_NEVER = 20;
    
    private int horizontalScrollBarPolicy = SCROLLBAR_AS_NEEDED;
    private int verticalScrollBarPolicy = SCROLLBAR_ALWAYS;
    
    private ScrollBar horizontalScrollBar = null;
    private ScrollBar verticalScrollBar = null;
    
    private Component view = null;
    private Container viewport = new Container();
    
    public static final int CORNER_SIZE = 16;
    
    protected ChangeListener horizListener = new HorizontalChangeListener();
    protected ChangeListener vertListener = new VerticalChangeListener();
    protected ResizeListener resizeListener = new ResizeListenerHandle();
    
    private Dimension lastSize = null;
    private boolean autoScrolling = true;
    
    public ScrollPane(Component view) {
        this(true);
        if (view!=null) {
            this.view = view;
            view.setLocation(0f, 0f);
            if (autoScrolling)
        	view.addResizeListener(resizeListener);
            viewport.add(view);
            viewport.setLocation(-1f, -1f);
            add(viewport);
        }
    }
    
    public ScrollPane() {
        this(null);
    }
    
    /**
     * Return if the auto scrolling is activated.
     * 
     * @return
     */
    
    public boolean isAutoScrolling() {
        return autoScrolling;
    }

    /**
     * Define if the ScrollPane will automaticly scroll when the size of 
     * the view is changed.<br/>
     * <br/> 
     * Note : It will only auto scroll if the scrollbar was previously at the
     * maximum value. 
     *  
     * @param autoScrolling
     */
    
    public void setAutoScrolling(boolean autoScrolling) {
	if (autoScrolling && !this.autoScrolling) {
	    view.addResizeListener(resizeListener);
	} else if (!autoScrolling && this.autoScrolling) {
	    view.removeResizeListener(resizeListener);
	}
	
        this.autoScrolling = autoScrolling;
    }

    public void setWidth(float w) {
        super.setWidth(w);
        float o = 0;
        if (verticalScrollBar.isVisible())
            o = verticalScrollBar.getWidth();
        viewport.setWidth(w - o);
        verticalScrollBar.setLocation(w-verticalScrollBar.getWidth(), 0);
        //TODO: set minimum size of component
    }
    
    public void setHeight(float h) {
        super.setHeight(h);
        float o = 0;
        if (horizontalScrollBar.isVisible())
            o = horizontalScrollBar.getHeight();
        viewport.setHeight(h);
        horizontalScrollBar.setLocation(0, h-horizontalScrollBar.getHeight());
    }
    
    /**
     * Creates a new instance of ScrollPane
     */
    protected ScrollPane(boolean updateAppearance) {
        super(false);
        
        if (updateAppearance) {
            updateAppearance();
        }
    }
    
    
    /** 
     * Creates the appearance and returns the proper type.
     *
     * @return the scroll bar appearance from the current skin
     */
    public void updateAppearance() {
        setAppearance(Sui.getSkin().getScrollPaneAppearance(this));
    }
    
    /**
     * Gets the current appearance for this scroll bar component.
     *
     * @return the scroll bar appearance set for this component
     * 1.5 only feature
    public ScrollPaneAppearance getAppearance() {
        return (ScrollPaneAppearance)super.getAppearance();
    }*/
    
    /**
     * Sets the appearance for this scroll bar. If <code>appearance</code> is
     * not an instance of ScrollBarAppearance, an 
     * <code>IllegalArgumentException</code> is thrown.
     *
     * @param appearance the new appearance to set
     */
    public void setAppearance(ComponentAppearance appearance) {
        if (appearance==null)
            throw new IllegalStateException("null appearance not allowed with ScrollPane");
        if (!(appearance instanceof ScrollPaneAppearance)) {
            throw new IllegalArgumentException("appearance must be instance of ScrollPaneAppearance");
        }
        super.setAppearance(appearance);
        
        //de-init components
        if (horizontalScrollBar!=null) {
            horizontalScrollBar.getSlider().removeChangeListener(horizListener);
            remove(horizontalScrollBar);
        }
        if (verticalScrollBar!=null) {
            verticalScrollBar.getSlider().removeChangeListener(vertListener);
            remove(verticalScrollBar);
        }
        
        //re-init components
        
        //gets the current appearance
        ScrollPaneAppearance a = (ScrollPaneAppearance)this.getAppearance();
        if (a==null) {
            throw new IllegalStateException("no appearance set for ScrollPaneAppearance");
        }
        
        //creates scroll bars
        //skins can choose how they should be created
        horizontalScrollBar = a.createScrollBar(this, HORIZONTAL);
        verticalScrollBar   = a.createScrollBar(this, VERTICAL);
        
        if (horizontalScrollBar==null||verticalScrollBar==null)
            throw new NullPointerException("null components passed to scroll pane");
        if (horizontalScrollBar.getOrientation()!=HORIZONTAL 
                || verticalScrollBar.getOrientation()!=VERTICAL)
            throw new IllegalArgumentException("incorrect scroll bar orientation passed to the scroll pane");
        
        horizontalScrollBar.setValue(0f);
        horizontalScrollBar.getSlider().addChangeListener(horizListener);
        verticalScrollBar.setValue(0f);
        verticalScrollBar.getSlider().addChangeListener(vertListener);
        add(horizontalScrollBar);
        add(verticalScrollBar);
        
        updateScrollBarShowing();
        
        // Adds resize listener to the view component /
    }
        
    public void updateComponent(GUIContext ctx, int delta) {
        super.updateComponent(ctx, delta);
        
        if (view!=null) {
            updateScrollBarShowing();
        }
    }
    
    private void updateScrollBarShowing() {
        boolean horiz = isHorizontalScrollBarNeeded();
        horizontalScrollBar.setVisible(horiz);
        
        boolean vert = isVerticalScrollBarNeeded();
        verticalScrollBar.setVisible(vert);
        
        int corner = horiz && vert ? CORNER_SIZE : 0;
        horizontalScrollBar.setWidth(getWidth()-corner);
        verticalScrollBar.setHeight(getHeight()-corner);
        
        float width = viewport.getWidth();
        float height = viewport.getHeight();
        
        if (vert) //vertical exists
            viewport.setWidth(getWidth()-verticalScrollBar.getWidth());
        else
            viewport.setWidth(getWidth());
        if (horiz)
            viewport.setHeight(getHeight()-horizontalScrollBar.getHeight());
        else
            viewport.setHeight(getHeight());
        
        //horizontalScrollBar.getSlider().setThumbSize()
        if (view!=null) {
            verticalScrollBar.getSlider().setThumbSize(getHeight()/view.getHeight());
            horizontalScrollBar.getSlider().setThumbSize(getWidth()/view.getWidth());
            
            float toScrollX = (view.getWidth() - (getWidth() - ((isVerticalScrollBarNeeded()) ? CORNER_SIZE : 0)));
            float toScrollY = (view.getHeight() - (getHeight() - ((isHorizontalScrollBarNeeded()) ? CORNER_SIZE : 0)));
            
            if (toScrollX != 0) {
                horizontalScrollBar.setValue(-view.getX() / toScrollX);
            } 
            
            if (toScrollY != 0) {
        	verticalScrollBar.setValue(-view.getY() / toScrollY);
            }
        }
    }
    
    protected boolean isHorizontalScrollBarNeeded() {
        if (horizontalScrollBarPolicy == SCROLLBAR_AS_NEEDED) //only show if it's too big
            return view!=null ? (view.getWidth()>getWidth()) : false;
        else
            return (horizontalScrollBarPolicy==SCROLLBAR_ALWAYS);
    }
        
    protected boolean isVerticalScrollBarNeeded() {
        if (verticalScrollBarPolicy == SCROLLBAR_AS_NEEDED) //only show if it's too big
            return view!=null ? (view.getHeight()>getHeight()) : false;
        else
            return (verticalScrollBarPolicy==SCROLLBAR_ALWAYS);
    }
    
    protected class HorizontalChangeListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            float x =
        	-horizontalScrollBar.getValue()*
        	(view.getWidth() - (getWidth() - ((isVerticalScrollBarNeeded()) ? CORNER_SIZE : 0)));
            
            view.setX(x);
        }
    }
    
    protected class VerticalChangeListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            if (view!=null) {
        	float y =
        	    -verticalScrollBar.getValue()*
        	    (view.getHeight() - (getHeight() - ((isHorizontalScrollBarNeeded()) ? CORNER_SIZE : 0)));
        	
                view.setY(y);
            }
        }
    }
    
    /**
     * On resize handle to make the ScrollPane automaticly scroll when the
     * scrolling is at the maximum.
     *
     * @author Olivier Arteau
     */
    
    protected class ResizeListenerHandle implements ResizeListener {
	public void sizeChanged(Component c, float oldWidth, float oldHeight) {
	    if (verticalScrollBar.getValue() == 1.0f ||
	       (verticalScrollBar.getValue() == 0 && view.getHeight()<getHeight())) {
		updateScrollBarShowing();
		verticalScrollBar.setValue(1);
	    }
	    
	    if ((horizontalScrollBar.getValue() == 1 ||
		(horizontalScrollBar.getValue() == 0 && view.getWidth()<getWidth()))) {
		updateScrollBarShowing();
		horizontalScrollBar.setValue(1);
	    }
	}
    }
}
