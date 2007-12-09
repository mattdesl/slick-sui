package mdes.slick.sui;

import mdes.slick.sui.event.SuiActionEvent;
import mdes.slick.sui.event.SuiActionListener;
import mdes.slick.sui.event.SuiMouseAdapter;
import mdes.slick.sui.event.SuiMouseEvent;

import org.newdawn.slick.Image;

/**
 * A basic, clickable button component. <tt>SuiButtons</tt> are
 * rendered based on their state: UP, DOWN, or ROLLOVER.
 * Images can be set for each state.
 * <p>
 * A button will be "hit" when the first mouse button is
 * clicked and released over it (SuiMouseEvent.BUTTON1).
 * <p>
 * All SuiButtons are created with a padding of 5, and
 * with text and images initially centered.
 *
 * @author davedes
 * @since b.0.1
 */
public class SuiButton extends SuiLabel {
    
    /** A constant for the UP state. */
    public static final int UP = -10;
    
    /** A constant for the DOWN state. */
    public static final int DOWN = -9;
    
    /** A constant for the ROLLOVER state. */
    public static final int ROLLOVER = -8;
    
    /** The current state. */
    private int state = UP;
    
    /** The image to draw when the mouse rolls over the button. */
    private Image rolloverImage = null;
    
    /** The image to draw when the mouse presses down on the button. */
    private Image downImage = null;
    
    /** The image to draw when the button is disabled. */
    private Image disabledImage = null;
    
    /** The action command, initially an empty String. */
    private String actionCommand = "";
    
    private boolean pressedOutside = false;
    
    /**
     * Creates a button with the specified text, which
     * also acts as the action command String.
     *
     * @param text the text to display on the button
     */
    public SuiButton(String text) {
        this();
        setText(text);
        actionCommand = text;
        pack();
    }
    
    /**
     * Creates a button with the specified UP image.
     *
     * @param img the image to display on the button
     */
    public SuiButton(Image img) {
        this();
        setImage(img);
    }
    
    /** Creates an empty button. */
    public SuiButton() {
        this(true);
    }
    
    protected SuiButton(boolean updateAppearance) {
        super(false);
        setRequestFocusEnabled(true);
        setVerticalPadding(5);
        setHorizontalPadding(8);
        setFocusable(true);
        addMouseListener(new ButtonListener());
        if (updateAppearance)
            updateAppearance();
        
    }
    
    public void updateAppearance() {
        setAppearance(Sui.getSkin().getButtonAppearance(this));
    }
        
    /**
     * Sets the action command to be passed to
     * <tt>SuiActionEvent</tt>s when this button
     * is clicked.
     *
     * @param t the command
     */
    public void setActionCommand(String t) {
        this.actionCommand = t;
    }
    
    /**
     * Gets the action command.
     *
     * @return the action command
     */
    public String getActionCommand() {
        return actionCommand;
    }
    
    /**
     * Adds the specified listener to the list.
     *
     * @param s the listener to receive events
     */
    public synchronized void addActionListener(SuiActionListener s) {
        listenerList.add(SuiActionListener.class, s);
    }
    
    /**
     * Removes the specified listener from the list.
     *
     * @param s the listener to remove
     */
    public synchronized void removeActionListener(SuiActionListener s) {
        listenerList.remove(SuiActionListener.class, s);
    }
        
    public void setVisible(boolean b) {
        if (!isVisible()&&b)
            state = UP;
        super.setVisible(b);
    }
    
    /**
     * Determines the Image to draw based on the given state.
     * <p>
     * <b>UP</b>: getImage() if enabled, otherwise getDisabledImage()
     * <b>DOWN</b>: getDownImage() if it exists, otherwise getImage()
     * <b>ROLLOVER</b>: getRolloverImage() if it exists, otherwise getImage()
     */
    protected Image getImageForState(int state) {
        switch (state) {
            default:
            case SuiButton.UP:
                return isEnabled() ? getImage() : disabledImage;
            case SuiButton.DOWN:
                return downImage!=null ? downImage : getImage();
            case SuiButton.ROLLOVER:
                return rolloverImage!=null ? rolloverImage : getImage();            
        }
    }
         
    /**
     * Gets the current state of the button.
     *
     * @return the state id: either UP, DOWN, or ROLLOVER.
     */
    public int getState() {
        return state;
    }
    
    /**
     * Overridden to set the state of the button to UP if
     * it's being disabled.
     *
     * @param b <tt>true</tt> if the button should accept clicks
     * @see mdes.slick.sui.SuiLabel#setEnabled(boolean)
     */
    public void setEnabled(boolean b) {
        //a disabled button is always in the UP state
        if (!b && isEnabled()) {
            state = UP;
        }
        super.setEnabled(b);
    }
    
    /**
     * Returns the disabled image.
     *
     * @return the image or <tt>null</tt> if it hasn't been set
     */
    public Image getDisabledImage() {
        return disabledImage;
    }
    
    /**
     * Returns the down (pressed) image.
     *
     * @return the image or <tt>null</tt> if it hasn't been set
     */
    public Image getDownImage() {
        return downImage;
    }
    
    /**
     * Returns the rollover image.
     *
     * @return the image or <tt>null</tt> if it hasn't been set
     */
    public Image getRolloverImage() {
        return rolloverImage;
    }
    
    /**
     * Sets the disabled image.
     *
     * @param i the image or <tt>null</tt> if it shouldn't be drawn
     */
    public void setDisabledImage(Image i) {
        this.disabledImage = i;
    }
    
    /**
     * Sets the down (pressed) image.
     *
     * @param i the image or <tt>null</tt> if it shouldn't be drawn
     */
    public void setDownImage(Image i) {
        this.downImage = i;
    }
    
    /**
     * Sets the rollover image.
     *
     * @param i the image or <tt>null</tt> if it shouldn't be drawn
     */
    public void setRolloverImage(Image i) {
        this.rolloverImage = i;
    }
    
    /**
     * Fires a virtual press. Subclasses may override to react
     * to button press <i>before</i> actions are sent.
     */
    public void press() {
        fireActionPerformed(actionCommand);
    }
    
    public boolean isPressedOutside() {
        return pressedOutside;
    }
        
    /**
     * Fires an action event with the specified command
     * to all action listeners registered with this component.
     *
     * @param command the action command for the event
     * @see mdes.slick.sui.event.SuiActionEvent
     */
    protected void fireActionPerformed(String command) {
        SuiActionEvent evt = null;
        
        final SuiActionListener[] listeners =
                (SuiActionListener[])listenerList.getListeners(SuiActionListener.class);
        for (int i=0; i<listeners.length; i++) {
            //lazily create it
            if (evt==null) {
                evt = new SuiActionEvent(this, command);
            }
            listeners[i].actionPerformed(evt);
        }
    }
    
    /**
     * A SuiMouseListener for handling the button clicks.
     */
    protected class ButtonListener extends SuiMouseAdapter {
        
        private boolean inside = false;
        
        public void mousePressed(SuiMouseEvent e) {
            if (!isEnabled()) {
                state = UP;
                return;
            }
            inside = true;
            
            if (e.getButton()==SuiMouseEvent.BUTTON1) {
                state = DOWN;
            }
            pressedOutside = false;
        }
        
        public void mouseDragged(SuiMouseEvent e) {
            //if we are dragging outside
            inside = SuiButton.this.contains(e.getAbsoluteX(), e.getAbsoluteY());
            state = inside ? DOWN : UP;
            pressedOutside = !inside;
        }
        
        public void mouseEntered(SuiMouseEvent e) {
            if (!isEnabled()) {
                state = UP;
                return;
            }
            
            if (state!=DOWN)
                state = ROLLOVER;
            
            inside = true;
        }
        
        //TODO: don't call these methods if a container is disabled??
        public void mouseReleased(SuiMouseEvent e) {
            if (!isEnabled()) {
                state = UP;
                return;
            }
            
            pressedOutside = false;
            
            //only fire action if we are releasing button 1
            if (inside && e.getButton()==SuiMouseEvent.BUTTON1) {
                state = ROLLOVER;
                press();
            }
        }
        
        public void mouseExited(SuiMouseEvent e) {
            if (!isEnabled()) {
                state = UP;
                return;
            }
            inside = false;
            state = UP;
        }
    }
}
