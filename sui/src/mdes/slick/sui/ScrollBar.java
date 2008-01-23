/*
 * ScrollBar.java
 *
 * Created on November 13, 2007, 4:00 PM
 */

package mdes.slick.sui;

import mdes.slick.sui.event.ActionEvent;
import mdes.slick.sui.event.ActionListener;
import mdes.slick.sui.event.ChangeListener;
import mdes.slick.sui.event.MouseWheelEvent;
import mdes.slick.sui.event.MouseWheelListener;
import mdes.slick.sui.skin.ComponentAppearance;
import mdes.slick.sui.skin.ScrollBarAppearance;

/**
 *
 * @author Alexandre Vieira, davedes
 */
public class ScrollBar extends Container implements ScrollConstants {
    
    protected Button incButton;
    protected Button decButton;
    
    protected IncDecListener incDecListener = new IncDecListener();
    
    private float scrollSpace = 0.03f;
    
    private Slider slider;
    
    private int orientation;
    
    private boolean mouseWheelEnabled = true;
           
    /**
     * Creates a new instance of ScrollBar
     */
    public ScrollBar(int orientation) {
        super(false);
        checkOrientation(orientation);
        this.orientation = orientation;
        updateAppearance();
    }
    
    private void checkOrientation(int orientation) {
        if (orientation!=HORIZONTAL && orientation!=VERTICAL)
            throw new IllegalArgumentException("scroll bar orientation " +
                    "must be either HORIZONTAL or VERTICAL");
    }
    
    //TODO: support setOrientation
    /*public void setOrientation(int orientation) {
        checkOrientation(orientation);
        this.orientation = orientation;
        updateAppearance();
    }*/
    
    public int getOrientation() {
        return orientation;
    }
    
    /** 
     * Creates the appearance and returns the proper type.
     *
     * @return the scroll bar appearance from the current skin
     */
    public void updateAppearance() {
        setAppearance(Sui.getSkin().getScrollBarAppearance(this));
    }
    
    /**
     * Gets the current appearance for this scroll bar component.
     *
     * @return the scroll bar appearance set for this component
     * 1.5 only feature
    public ScrollBarAppearance getAppearance() {
        return (ScrollBarAppearance)super.getAppearance();
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
            throw new IllegalStateException("null appearance not allowed with ScrollBar");
        if (!(appearance instanceof ScrollBarAppearance)) {
            throw new IllegalArgumentException("appearance must be instance of ScrollBarAppearance");
        }
        super.setAppearance(appearance);
        
        //de-init components
        if (slider!=null) {
            remove(slider);
            slider.removeMouseWheelListener(incDecListener);
            slider.getThumbButton().removeMouseWheelListener(incDecListener);
            //slider.getDelayTimer().removeActionListener(delayListener);
            
        }
        if (incButton!=null) {
            remove(incButton);
            incButton.removeActionListener(incDecListener);
        }
        if (decButton!=null) {
            remove(decButton);
            decButton.removeActionListener(incDecListener);
        }
        
        //re-init components
        
        //gets the current appearance
        ScrollBarAppearance a = (ScrollBarAppearance)this.getAppearance();
        if (a==null) {
            throw new IllegalStateException("no appearance set for this scroll bar");
        }
        
        //creates scroll & thumb buttons
        //skins can choose how they should be created
        incButton = a.createScrollButton(this, INCREMENT);
        decButton = a.createScrollButton(this, DECREMENT);
        slider    = a.createSlider(this, orientation);
        
        if (incButton==null||decButton==null||slider==null)
            throw new NullPointerException("null components passed to scroll bar");
        if (slider.getOrientation()!=orientation)
            throw new IllegalArgumentException("passed slider doesn't match orientation of scroll bar");
        
        //add scroll listeners buttons
        incButton.addActionListener(incDecListener);
        decButton.addActionListener(incDecListener);
        //slider.getDelayTimer().addActionListener(delayListener);
        
        //add the new components to this bar
        add(slider);
        add(incButton);
        add(decButton);

        incButton.addMouseWheelListener(incDecListener);
        decButton.addMouseWheelListener(incDecListener);
        slider.addMouseWheelListener(incDecListener);
        slider.getThumbButton().addMouseWheelListener(incDecListener);
        addMouseWheelListener(incDecListener);
        
        updateWidth();
        updateHeight();
    }
                
    /**
     * Gets the "increment" button.
     * 
     * @return the increment button
     * @see mdes.slick.sui.ScrollConstants#INCREMENT
     */
    public Button getIncrementButton() {
        return incButton;
    }
    
    /**
     * Gets the "decrement" button.
     * 
     * @return the decrement button
     * @see mdes.slick.sui.ScrollConstants#DECREMENT
     */
    public Button getDecrementButton() {
        return decButton;
    }
                
    private void updateWidth() {
        if (getWidth()==0) {
            setWidth(DEFAULT_SIZE);
            return;
        }
        if (orientation==HORIZONTAL) {
            incButton.setX(getWidth()-incButton.getWidth());
            slider.setX(decButton.getWidth());
            slider.setWidth(getWidth() - (incButton.getWidth()+decButton.getWidth()));
        } else { //VERTICAL
            incButton.setWidth(Math.min(getWidth(), incButton.getWidth()));
            decButton.setWidth(Math.min(getWidth(), decButton.getWidth()));
            slider.setWidth(Math.min(getWidth(), slider.getWidth()));
            slider.setX(getWidth()/2f - slider.getWidth()/2f);
            incButton.setX(getWidth()/2f - incButton.getWidth()/2f);
            decButton.setX(getWidth()/2f - incButton.getWidth()/2f);
        }
    }
    
    private void updateHeight() {
        if (getHeight()==0) {
            setHeight(DEFAULT_SIZE);
            return;
        }
        if (orientation==HORIZONTAL) {
            incButton.setHeight(Math.min(getHeight(), incButton.getHeight()));
            decButton.setHeight(Math.min(getHeight(), decButton.getHeight()));
            slider.setHeight(Math.min(getHeight(), slider.getHeight()));
            slider.setY(getHeight()/2f - slider.getHeight()/2f);
            incButton.setY(getHeight()/2f - incButton.getHeight()/2f);
            decButton.setY(getHeight()/2f - incButton.getHeight()/2f);
        } else { //VERTICAL
            decButton.setY(getHeight()-decButton.getHeight());
            slider.setY(incButton.getHeight());
            slider.setHeight(getHeight() - (incButton.getHeight()+decButton.getHeight()));
        }
    }
    
    public void setWidth(float width) {
        super.setWidth(width);
        updateWidth();
    }
    
    public void setHeight(float height) {
        super.setHeight(height);
        updateHeight();
    }
        
    MouseWheelListener getMouseWheelListener() {
        return incDecListener;
    }
    
    public Slider getSlider() {
        return slider;
    }
    
    public void setValue(float value) {
        getSlider().setValue(value);
    }
    
    public float getValue() {
        return getSlider().getValue();
    }
    
    public void scrollByUnit(int direction) {
        float size;
        if (orientation==VERTICAL) {
            direction *= -1;
            size = scrollSpace + slider.getThumbButton().getHeight() / slider.getHeight();
        } else
            size = scrollSpace + slider.getThumbButton().getWidth() / slider.getWidth();
        
        if (direction==DECREMENT)
            setValue(getValue() - size);
        else
            setValue(getValue() + size);
    }
    
    public boolean isMouseWheelEnabled() {
        return mouseWheelEnabled;
    }

    public void setMouseWheelEnabled(boolean mouseWheelEnabled) {
        this.mouseWheelEnabled = mouseWheelEnabled;
    }
            
    protected class IncDecListener implements ActionListener, MouseWheelListener {
        public void actionPerformed(ActionEvent e) {
            int direction = (e.getSource() == incButton) 
                    ? INCREMENT 
                    : DECREMENT;
	    scrollByUnit(direction);
        }

        public void mouseWheelMoved(MouseWheelEvent e) {
            //TODO: listen for mouse wheel on thumb
            if (!isMouseWheelEnabled())
                return;
            int amt = e.getAmountChanged();
            int direction = (amt>0)
                    ? INCREMENT
                    : DECREMENT;
            scrollByUnit(direction);
        }
    }
}
