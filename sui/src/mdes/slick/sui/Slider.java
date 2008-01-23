/*
 * Slider.java
 *
 * Created on November 15, 2007, 5:53 PM
 */

package mdes.slick.sui;

import mdes.slick.sui.event.ActionEvent;
import mdes.slick.sui.event.ActionListener;
import mdes.slick.sui.event.ChangeEvent;
import mdes.slick.sui.event.ChangeListener;
import mdes.slick.sui.event.MouseAdapter;
import mdes.slick.sui.event.MouseEvent;
import mdes.slick.sui.skin.ComponentAppearance;
import mdes.slick.sui.skin.SliderAppearance;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author davedes
 */
public class Slider extends Container implements ScrollConstants {
        
    /** The "thumb" or "knob" button for this slider component. */
    protected Button thumbButton = null;
    
    /** The orientation of this slider: either VERTICAL or HORIZONTAL. */ 
    private int orientation;
    
    /** The percentage that this slider is at. */
    private float value;
    
    /** The space to jump when the track is clicked. */
    private float jumpSpace = .05f;
    
    private boolean isValueAdjusting = false;
    
    private Timer slideDelay;
        
    private float minThumbSize = 8;
    private float thumbPercent = 0f;

    private boolean trackDown = false;
    
    private Rectangle trackSelect = null;
    private int incrementing = 0;
    
    private final ThumbDragListener THUMB_DRAG_LISTENER = new ThumbDragListener();
    
    /**
     * Creates a new instance of Slider with the specified orientation.
     */
    public Slider(int orientation) {
        this(orientation, true);
    }
    
    protected Slider(int orientation, boolean updateAppearance) {
        super(false);
        checkOrientation(orientation);
        this.orientation = orientation;
        
        //setup default sizes
        if (orientation==HORIZONTAL)
            setHeight(DEFAULT_SIZE);
        else
            setWidth(DEFAULT_SIZE);
        
        if (updateAppearance)
            updateAppearance();
        
        TrackListener trackListener = new TrackListener();
        slideDelay = new Timer(80, trackListener);
        slideDelay.setInitialDelay(300);
        this.addMouseListener(trackListener);
    }
    
    public void updateAppearance() {
        setAppearance(Sui.getSkin().getSliderAppearance(this));
    }
    
    private void checkOrientation(int orientation) {
        if (orientation!=HORIZONTAL && orientation!=VERTICAL)
            throw new IllegalArgumentException("slider orientation " +
                    "must be either HORIZONTAL or VERTICAL");
    }
    
    public int getOrientation() {
        return orientation;
    }
    
    public void setOrientation(int orientation) {
        checkOrientation(orientation);
        this.orientation = orientation;
    }
    
    public void setThumbSize(float percent) {
        this.thumbPercent = percent;
        setSize(getSize());
    }
        
    /**
     * Gets the current appearance for this slider component.
     *
     * @return the scroll bar appearance set for this component
     * 1.5 feature
    public SliderAppearance getAppearance() {
        return (SliderAppearance)super.getAppearance();
    }*/
        
    /**
     * Sets the appearance for this slider. If <code>appearance</code> is
     * not an instance of SliderAppearance, an 
     * <code>IllegalArgumentException</code> is thrown.
     *
     * @param appearance the new appearance to set
     */
    public void setAppearance(ComponentAppearance appearance) {
        if (appearance==null)
            throw new IllegalStateException("null appearance not allowed with slider");
        if (!(appearance instanceof SliderAppearance))
            throw new IllegalArgumentException("must pass instance of slider appearance");
        super.setAppearance(appearance);
        
        if (thumbButton!=null) {
            thumbButton.removeMouseListener(THUMB_DRAG_LISTENER);
            remove(thumbButton);
        }
        
        SliderAppearance sliderAppearance = (SliderAppearance)getAppearance();
        
        //init the thumb button
        thumbButton = sliderAppearance.createThumbButton(this);
        if (thumbButton==null)
            throw new IllegalStateException("thumb button can't be null");
                
        thumbButton.addMouseListener(THUMB_DRAG_LISTENER);
        add(thumbButton);
        
        if (getWidth()==0||getHeight()==0)
            return;
        
        if (orientation==HORIZONTAL) {
            float width = Math.max(getWidth()*thumbPercent, minThumbSize);
            thumbButton.setWidth(width);
            thumbButton.setHeight(Math.min(getHeight(), Math.max(thumbButton.getHeight(), minThumbSize)));
            thumbButton.setY(getHeight()/2f - thumbButton.getHeight()/2f);
            thumbButton.setX((getWidth()-thumbButton.getWidth())*value);
        } else {
            float height = Math.max(getHeight()*thumbPercent, minThumbSize);
            thumbButton.setHeight(height);
            thumbButton.setWidth(Math.min(getWidth(), Math.max(thumbButton.getWidth(), minThumbSize)));
            thumbButton.setX(getWidth()/2f - thumbButton.getWidth()/2f);
            thumbButton.setY((getHeight()-thumbButton.getHeight())*value);
        }
    }
    
    public Button getThumbButton() {
        return thumbButton;
    }
    
    private void updateHeight() {
        if (orientation==HORIZONTAL) {
            thumbButton.setHeight(Math.min(getHeight(), Math.max(thumbButton.getHeight(), minThumbSize)));
            thumbButton.setY(getHeight()/2f - thumbButton.getHeight()/2f);
        } else {
            float btnHeight = Math.max(getHeight()*thumbPercent, minThumbSize);
            thumbButton.setHeight(btnHeight);
            thumbButton.setY((getHeight()-thumbButton.getHeight())*value);
        }
    }
    
    private void updateWidth() {
        if (orientation==HORIZONTAL) {
            float btnWidth = Math.max(getWidth()*thumbPercent, minThumbSize);
            thumbButton.setWidth(btnWidth);
            thumbButton.setX((getWidth()-thumbButton.getWidth())*value);
        } else {
            thumbButton.setWidth(Math.min(getWidth(), Math.max(thumbButton.getWidth(), minThumbSize)));
            thumbButton.setX(getWidth()/2f - thumbButton.getWidth()/2f);
        }
    }
    
    Timer getDelayTimer() {
        return slideDelay;
    }
    
    public void setHeight(float height) {
        super.setHeight(height);
        if (thumbButton!=null)
            updateHeight();
    }
    
    public void setWidth(float width) {
        super.setWidth(width);
        if (thumbButton!=null)
            updateWidth();
    }
         
    /**
     * Adds the specified listener to the list.
     *
     * @param s the listener to receive events
     */
    public synchronized void addChangeListener(ChangeListener s) {
        listenerList.add(ChangeListener.class, s);
    }
    
    /**
     * Removes the specified listener from the list.
     *
     * @param s the listener to remove
     */
    public synchronized void removeChangeListener(ChangeListener s) {
        listenerList.remove(ChangeListener.class, s);
    }
    
    /**
     * Fires a change event to all action listeners
     * in this component.
     * 
     * 
     * @see mdes.slick.sui.event.ChangeEvent
     */
    protected void fireStateChanged() {
        ChangeEvent evt = null;
        
        final ChangeListener[] listeners =
                (ChangeListener[])listenerList.getListeners(ChangeListener.class);
        for (int i=0; i<listeners.length; i++) {
            //lazily create it
            if (evt==null) {
                evt = new ChangeEvent(this);
            }
            listeners[i].stateChanged(evt);
        }
    }
    
    protected void updateComponent(GUIContext ctx, int delta) {
        super.updateComponent(ctx, delta);
        slideDelay.update(ctx, delta);
    }
    
    public void setValue(float value) {
        float old = this.value;
        if(value > 1) 
            value = 1;
        if(value < 0)
            value = 0;
        this.value = value;
        if (old!=this.value) {
            if(orientation==HORIZONTAL) 
                thumbButton.setX(value*(getWidth()-thumbButton.getWidth()));
            else
                thumbButton.setY(value*(getHeight()-thumbButton.getHeight()));
            fireStateChanged();
        }
    }
    
    public float getValue() {
        return value;
    }
    
    public void setJumpDelay(int delay) {
        slideDelay.setDelay(delay);
    }
    
    public int getJumpDelay() {
        return slideDelay.getDelay();
    }
    
    public boolean isTrackDown() {
        return trackDown;
    }
    
    /**
     * Many skins will choose to highlight the portion 
     * of the track that is selected, whether it be 
     * the section BEFORE the thumb, or the section AFTER
     * it.
     */
    public Rectangle getAbsoluteTrackSelectionBounds() {
        float x = 0;
        float y = 0;
        float width = 0;
        float height = 0;
        float value = getValue();
        
        //if we are lastIncrementing or decrementing
        if (incrementing != 0 && value!=0f && value!=1f) {
            x = getAbsoluteX();
            y = getAbsoluteY();
            if (orientation==HORIZONTAL) {
                //horizontal increment is on the right
                if (incrementing==1) {
                    float th = thumbButton.getX()+thumbButton.getWidth()-thumbButton.getWidth()/2f;
                    x += th;
                    width = getWidth()-th;
                } else {
                    width = thumbButton.getX()+thumbButton.getWidth()/2f;
                }
                height = getHeight();
            } else {
                //vertical increment is on the top
                //so we only change with decrement
                if (incrementing==-1) {
                    height = thumbButton.getY()+thumbButton.getWidth()/2f;
                } else {
                    float th = thumbButton.getY()+thumbButton.getHeight()-thumbButton.getHeight()/2f;
                    y += th;
                    height = getHeight()-th;
                }
                width = getWidth();
            }
        }
        
        if (trackSelect==null) {
            trackSelect = new Rectangle(x, y, width, height);
        } else {
            trackSelect.setX(x);
            trackSelect.setY(y);
            trackSelect.setWidth(width);
            trackSelect.setHeight(height);
        }
        
        return trackSelect;
    }
    
    protected class TrackListener extends MouseAdapter implements ActionListener {
        
        private int button;
        private int targetX, targetY;
        private int lastIncrementing = 0;
        
        public void actionPerformed(ActionEvent e) {
            jump(button, targetX, targetY);
        }
        
        public void mousePressed(MouseEvent e) {
            trackDown = true;
            slideDelay.start();
            button = e.getButton();
            targetX = e.getAbsoluteX();
            targetY = e.getAbsoluteY();
            jump(button, targetX, targetY);
        }

        public void mouseReleased(MouseEvent e) {
            trackDown = false;
            lastIncrementing = incrementing = 0;
            slideDelay.stop();
            button = e.getButton();
            targetX = e.getAbsoluteX();
            targetY = e.getAbsoluteY();
        }
        
        public void mouseDragged(MouseEvent e) {
            button = e.getButton();
            targetX = e.getAbsoluteX();
            targetY = e.getAbsoluteY();
        }
        
        private void jump(int button, int targetX, int targetY) {
            //if button is released and we need to stop timer
            if (button!=MouseEvent.BUTTON1 && slideDelay.isRunning()) {
                lastIncrementing = incrementing = 0;
                slideDelay.stop();
                return;
            }
            if (!isEnabled()) {
                lastIncrementing = incrementing = 0;
                slideDelay.stop();
                return;
            }
            
            boolean contains = contains(targetX, targetY);
            boolean thumbCont = thumbButton.contains(targetX, targetY);
            
            //if we are pressed over the track
            trackDown = contains && button!=MouseEvent.NOBUTTON && !thumbCont;
            
            if (!contains) { //not incrementing or decrementing
                return;
            }
            
            int oldIncrementing = lastIncrementing;
            
            if (orientation==HORIZONTAL) {
                //thumbButton.getWidth() /
                float size = jumpSpace + thumbButton.getWidth() / getWidth();
                boolean dec = targetX < getAbsoluteX()+thumbButton.getX();
                lastIncrementing = !dec ? 1 : -1;
                incrementing = lastIncrementing;
                
                if (oldIncrementing!=0 && lastIncrementing!=oldIncrementing) {
                    lastIncrementing = oldIncrementing;
                    incrementing = 0;
                    return;
                }
                
                if (thumbButton.contains(targetX,
                        thumbButton.getAbsoluteY()+thumbButton.getHeight()/2f)) {
                    slideDelay.restart();
                    return;
                }
                
                if (dec)
                    setValue(getValue() - size);
                else
                    setValue(getValue() + size);
            } else {                
                float size = jumpSpace + thumbButton.getHeight() / getHeight();
                boolean dec = targetY < getAbsoluteY()+thumbButton.getY();
                lastIncrementing = !dec ? 1 : -1;
                incrementing = lastIncrementing;
                if (oldIncrementing!=0 && lastIncrementing!=oldIncrementing) {
                    lastIncrementing = oldIncrementing;
                    incrementing = 0;
                    return;
                }
                if (thumbButton.contains(
                        thumbButton.getAbsoluteX()+thumbButton.getWidth()/2f, 
                        targetY)) {
                    slideDelay.restart();
                    return;
                }
                
                if (dec)
                    setValue(getValue() - size);
                else
                    setValue(getValue() + size);
            }
        }
    }
    
    protected class ThumbDragListener extends MouseAdapter {
        
        private float lastX = 0;
        private float lastY = 0;
        
        public void mousePressed(MouseEvent e) {
            lastX = e.getAbsoluteX();
            lastY = e.getAbsoluteY();            
        }
        
        public void mouseDragged(MouseEvent e) {            
            float abx = e.getAbsoluteX();
            float aby = e.getAbsoluteY();
            
            if (!contains(abx, getAbsoluteY()+getHeight()/2f))
                return;
            if (orientation==HORIZONTAL) {
                float x = abx-lastX;
                x = thumbButton.getX()+x;
                float s = getWidth()-thumbButton.getWidth();
                if (s==0)
                    setValue(0f);
                else
                    setValue(x/s);
            } else {
                float y = aby-lastY;
                y = thumbButton.getY()+y;
                float s = getHeight()-thumbButton.getHeight();
                if (s==0)
                    setValue(0f);
                else 
                    setValue(y/s);
            }
            
            lastX = abx;
            lastY = aby;
        }
    }
}
