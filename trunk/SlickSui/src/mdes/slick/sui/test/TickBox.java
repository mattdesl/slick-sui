/*
 * TickBox.java
 *
 * Created on June 17, 2007, 2:01 AM
 */

package mdes.slick.sui.test;

import mdes.slick.sui.event.*;
import mdes.slick.sui.*;
import mdes.slick.sui.skin.SkinManager;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.*;

/**
 *
 * @author davedes
 */
public class TickBox extends SuiContainer {
    
    //whether the box is ticked
    private boolean ticked = false;
        
    private Box box;
    
    private Text label;
    
    /**
     * Creates a new instance of TickBox.
     */
    public TickBox() {
        box = new Box();
        box.setSize(15, 15); //initial size of the tick box
        
        label = new Text();
        label.setText("TEST");
        label.setForeground(Color.black);
        
        add(box);
        add(label);
        
        box.setDisabledImage(SkinManager.getImage("CheckBox.image"));
    }
    
    public void setText(String text) {
        label.setText(text);
    }
    
    public String getText() {
        return label.getText();
    }
    
    public void addActionListener(SuiActionListener a) {
        box.addActionListener(a);
    }
    
    public void removeActionListener(SuiActionListener a) {
        box.removeActionListener(a);
    }
    
    public void pack() {
        label.pack();
        label.setX(box.getWidth());
        
        int max = Math.max(box.getHeight(), label.getHeight());
        
        label.setHeight(max);
        box.setHeight(max);
        
        //we call super to avoid dealing with setHeight/getHeight overrides
        super.setSize(box.getWidth()+label.getWidth(),
                    max);
    }
    
    public void setHeight(int h) {
        box.setHeight(h);
        label.setHeight(h);
        super.setHeight(h);
    }
    
    public void setWidth(int w) {
        label.setWidth(w-box.getWidth());
        super.setWidth(w);
    }
    
    public void updateComponent(GUIContext c, int delta) {
        //if we are ticked
        if (ticked) {
            Color filter = box.getImageFilter();
            if (filter!=null) {
                //if we should fade more
                if (filter.a > 0f)
                    filter.a -= delta * 0.002f;
                
                //no longer ticked
                if (filter.a <= 0f) {
                    //0% opacity
                    filter.a = 0f;
                    
                    //not ticked
                    ticked = false;
                    
                    //enable
                    box.setEnabled(true);
                }
            } else {
                box.setEnabled(true);
                ticked = true;
            }
        }
    }
    
    private class Box extends SuiButton {
        
        //called when events are about to be fired
        public void press() {
            //if the box isn't ticked
            if (!ticked) {                
                //disable the button
                setEnabled(false);
                
                //set image to 100% opacity
                Color filter = box.getImageFilter();
                if (filter!=null) {
                    filter.a = 1f;
                }
                
                ticked = true;

                //fire the action event
                super.press();
            }
        }
    }
    
    private class Text extends SuiLabel {
        
    }
}
