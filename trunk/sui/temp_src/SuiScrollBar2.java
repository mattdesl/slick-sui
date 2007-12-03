/*
 * SuiScrollBar2.java
 *
 * Created on November 12, 2007, 1:56 PM
 */

package mdes.slick.sui;

import mdes.slick.sui.event.SuiActionEvent;
import mdes.slick.sui.event.SuiActionListener;
import mdes.slick.sui.event.SuiMouseWheelEvent;
import mdes.slick.sui.event.SuiMouseWheelListener;
import mdes.slick.sui.skin.*;
import org.newdawn.slick.Color;

/**
 * A SuiScrollBar2 defines the horizontal and vertical bars
 * found in a SuiScrollPane2. The scroll bar is initially 
 * an empty component -- it is up to the appearance to add
 * buttons, sliders, etc. appropriately. 
 * 
 * 
 * 
 * @author Alexandre Vieira
 */
public class SuiScrollBar2 extends SuiContainer implements ScrollBarConstants {
    
    /** Indicates a horizontal orientation. */ 
    public static final int HORIZONTAL = 1; 
    /** Indicates a vertical orientation. */ 
    public static final int VERTICAL = 2;
    
    /** The orientation. This affects the visual aspect and scrolling direction. */ 
    private int orientation;
    /** The width or height according to the orientation. */ 
    private static final int SIZE = 16; 
    /** */
    private SuiScrollPane2 scrollPane;
    
    private SuiButton thumb;
    private SuiButton incButton;
    private SuiButton decButton;
    
    private boolean initedAppearance = false;
    /** The minimum size of the thumb. */ 
    private static final int MIN_THUMB_SIZE = 8; 
    private float maxThumbSize = 0f;
    
    private float minimum = 0.0f;
    private float maximum = 1.0f;
    private float value = 0;
    /** Units to adjust the view. */
    private float unitsFactor = 10f; 
    private IncDecListener incDecListener = new IncDecListener();
    
    /**
     * Creates a new instance of SuiScrollBar2 with the specified orientation.
     */
    public SuiScrollBar2(SuiScrollPane2 pane, int orientation) {
        setScrollPane(pane);
        setOrientation(orientation);
        setOpaque(true);
        
        //setup default sizes
        if (orientation==HORIZONTAL)
            setHeight(SIZE);
        else
            setWidth(SIZE);
    }
    
    public ScrollBarAppearance createAppearance() {
        return Sui.getSkin().getScrollBarAppearance();
    }
    
    public ScrollBarAppearance getAppearance() {
        return (ScrollBarAppearance)super.getAppearance();
    }
    
    public void setAppearance(ComponentAppearance appearance) {
        if (!(appearance instanceof ScrollBarAppearance)) {
            throw new IllegalArgumentException("appearance must be instance of ScrollBarAppearance");
        }
        super.setAppearance(appearance);
    }
    
    public SuiButton getIncrementButton() {
        ensureAppearance();
        return incButton;
    }
    
    public SuiButton getDecrementButton() {
        ensureAppearance();
        return decButton;
    }
    
    public void ensureAppearance() {
        //first ensure the appearance exists
        super.ensureAppearance();
        if (!initedAppearance) {
            initedAppearance = true;
            //now attempt to set up using the given appearance
            ScrollBarAppearance a = this.getAppearance();
            if (a!=null) { //if appearance if found
                Padding p = getPadding();
                thumb = new ThumbButton(this);
                if (orientation == HORIZONTAL) {
                    incButton = a.createIncrementButton(FACE_RIGHT);
                    decButton = a.createDecrementButton(FACE_LEFT);
                    incButton.setHeight(getHeight());
                    decButton.setHeight(getHeight());
                    thumb.setHeight(getHeight());
                    thumb.setX(decButton.getX()+decButton.getWidth());
                } else {
                    incButton = a.createIncrementButton(FACE_UP);
                    decButton = a.createDecrementButton(FACE_DOWN);
                    incButton.setWidth(getWidth());
                    decButton.setWidth(getWidth());
                    thumb.setWidth(getWidth());
                    thumb.setY(incButton.getY()+incButton.getHeight());
                }
                incButton.addActionListener(incDecListener);
                decButton.addActionListener(incDecListener);
                add(incButton);
                add(decButton);
                add(thumb);
                if (orientation==VERTICAL) {
                    incButton.addMouseWheelListener(incDecListener);
                    decButton.addMouseWheelListener(incDecListener);
                    thumb.addMouseWheelListener(incDecListener);
                    addMouseWheelListener(incDecListener);
                }
            } else { //no appearance found, use basic buttons so we don't crash the system
                throw new IllegalStateException("no appearance set for SuiScrollPane");
            }
        }
    }
    
    private void updateButtonLocations() {
        if (orientation==HORIZONTAL) {
            if (incButton!=null) {
                incButton.setX(getWidth()-incButton.getWidth());
            }
        } else {
            if (decButton!=null) {
                decButton.setY(getHeight()-decButton.getHeight());
            }
        }
    }
    
    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
        if (orientation!=HORIZONTAL && orientation!=VERTICAL)
            throw new IllegalArgumentException("Orientation must either HORIZONTAL or VERTICAL.");
    }
    
    protected void onIncrement() { 
        //if we aren't at the max yet
        SuiComponent view = scrollPane.getView();
        float x=thumb.getX(), y=thumb.getY();
        if (orientation == HORIZONTAL) {
            float nx = x + unitsFactor;
            if (nx+thumb.getWidth() > incButton.getWidth()) {
                thumb.setX(nx);
                view.setX(view.getX() - unitsFactor);
            } else {
             
                thumb.setX(incButton.getWidth());
            }
        }  else {
            float ny = y - unitsFactor;
            if (ny > incButton.getHeight()) {
                view.setY(view.getY() + unitsFactor); 
                thumb.setY(ny);
            } else {
                //view.setY();
                thumb.setY(incButton.getHeight());
            }
        }
    } 

    protected void onDecrement() { 
        SuiComponent view = scrollPane.getView();
        float x=thumb.getX(), y=thumb.getY();
        if (orientation == HORIZONTAL) { 
            float nx = x - unitsFactor;
            if (nx > decButton.getWidth()) {
                view.setX(view.getX() + unitsFactor); 
                thumb.setX(nx);
            } else {
                
                thumb.setX(decButton.getWidth());
            }
        }  else { 
            float ny = y + unitsFactor;
            if (ny+thumb.getHeight() < decButton.getY()) {
                view.setY(view.getY() - unitsFactor); 
                thumb.setY(ny);
            } else {
                thumb.setY(decButton.getY()-thumb.getHeight());
            }
        }
    }
    
    public SuiScrollPane2 getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(SuiScrollPane2 scrollPane) {
        if (scrollPane==null)
            throw new IllegalArgumentException("scrollPane cannot be null");
        this.scrollPane = scrollPane;
    }
    
    /**
     * Adjust the horizontal locations of this <code>SuiScrollBar2</code> and its 
     * increment button, accordingly to the view.
     */ 
    void updateHorizontalSize() { 
        if (orientation != HORIZONTAL)
            throw new IllegalStateException("The orientation should be HORIZONTAL."); 
        if (scrollPane.isVerticalScrollBarNeeded())
            setWidth(scrollPane.getWidth()-SIZE);
        else
            setWidth(scrollPane.getWidth());
        this.updateButtonLocations();
        maxThumbSize = getWidth() - incButton.getWidth() - decButton.getWidth();
        thumb.setWidth( (int)(maxThumbSize * ((float)scrollPane.getWidth()/scrollPane.getView().getWidth())) );
    } 

    /**
     * 
     * Adjust the vertical locations of this <code>SuiScrollBar2</code> and its 
     * increment button, accordingly to the view.
     */ 
    void updateVerticalSize() { 
        if (orientation != VERTICAL)
            throw new IllegalStateException("The orientation should be VERTICAL."); 
        if (scrollPane.isHorizontalScrollBarNeeded())
            setHeight(scrollPane.getHeight() - SIZE); 
        else
            setHeight(scrollPane.getHeight()); 
        this.updateButtonLocations();
        maxThumbSize = getHeight() - incButton.getHeight() - decButton.getHeight(); 
        //thumb.setHeight((int) (maxThumbSize * ((float) scrollPane.getHeight() /scrollPane.getView().getHeight())));
        thumb.setHeight( maxThumbSize/(unitsFactor+1) );
    }
    
    SuiMouseWheelListener getMouseWheelListener() {
        return incDecListener;
    }
    
    private class IncDecListener implements SuiActionListener, SuiMouseWheelListener {
        public void actionPerformed(SuiActionEvent e) {
            SuiComponent src = e.getSource(); 
            if (src==incButton) 
                onIncrement(); 
            else if (src==decButton)
                onDecrement(); 
        }

        public void mouseWheelMoved(SuiMouseWheelEvent e) {
            int amt = e.getAmountChanged();
            if (amt>0)
                onIncrement();
            else if (amt<0)
                onDecrement();
        }
    }
    
    public static class ThumbButton extends SuiButton {
        private SuiScrollBar2 bar;
        
        public ThumbButton(SuiScrollBar2 bar) {
            this.bar = bar;
        }
        
        public SuiScrollBar2 getScrollBar() {
            return bar;
        }
        
        protected ComponentAppearance createAppearance() {
            ScrollBarAppearance a = Sui.getSkin().getScrollBarAppearance();
            return a!=null ? a.getThumbButtonAppearance() : null;
        }
    }
}
