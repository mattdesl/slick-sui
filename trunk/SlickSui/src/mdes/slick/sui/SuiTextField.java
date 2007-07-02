/*
 * SuiTextField.java
 *
 * Created on June 26, 2007, 2:41 PM
 */

package mdes.slick.sui;

import mdes.slick.sui.*;
import mdes.slick.sui.event.*;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.*;
import org.newdawn.slick.util.*;

/**
 *
 * @author davedes
 */
public class SuiTextField extends SuiLabel {
    
    /** The maximum number of characters allowed to be input */
    private int maxChars = 10000;
    
    private String actionCommand = null;
    
    /** The current cursor position */
    private int cursorPos;
    
    /** True if the cursor should be visible */
    private boolean visibleCursor = true;
    
    /**
     * Create a new text field
     */
    public SuiTextField(int cols) {
        this(cols, "");
    }
    
    public SuiTextField() {
        this(0);
    }
    
    public SuiTextField(int cols, String text) {
        setHorizontalAlignment(SuiLabel.LEFT_ALIGNMENT);
    
        setVerticalPadding(2);
        setHorizontalPadding(2);
        
        if (cols>0) {
            int charWidth = getFont().getWidth("W");
            setWidth(charWidth*cols+getHorizontalPadding()*2);
        }
        
        setHeight(getFont().getLineHeight()+getVerticalPadding()*2);
        addKeyListener(new FieldListener());
        setText(text);
        setFocusable(true);
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
     * Fires the specified action event to all action listeners
     * in this component.
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
    
    public void setText(String s) {
        cursorPos = s.length();
        super.setText(s);
    }
    
    
    /**
     * Set the position of the cursor
     *
     * @param pos The new position of the cursor
     */
    public void setCursorPos(int pos) {
        cursorPos = pos;
        if (cursorPos > getText().length()) {
            cursorPos = getText().length();
        }
    }
    
    /**
     * Indicate whether the mouse cursor should be visible or not
     *
     * @param visibleCursor True if the mouse cursor should be visible
     */
    public void setCursorVisible(boolean visibleCursor) {
        this.visibleCursor = visibleCursor;
    }
    
    /**
     * Set the length of the allowed input
     *
     * @param length The length of the allowed input
     */
    public void setMaxLength(int length) {
        maxChars = length;
        if (getText().length() > maxChars) {
            setText(getText().substring(0, maxChars));
        }
    }
    
    public void renderComponent(GUIContext container, Graphics g) {
        Color clr = g.getColor();
        Font temp = g.getFont();
        
        float x=getAbsoluteX(), y=getAbsoluteY(), width=getWidth(), height=getHeight();
        String value = getText();
        Font font = getFont();
        
        int cpos = font.getWidth(value.substring(0, cursorPos));
        float tx = 0;
        if (cpos > width) {
            tx = width - cpos - font.getWidth("_");
        }
        
        g.translate(tx + 2, 0);
        g.setFont(font);
        g.drawString(value, x + 1, y + 1);
        if (hasFocus() && visibleCursor) {
            g.drawString("_", x + 1 + cpos + 2, y + 1);
        }
        
        g.translate(-tx - 2, 0);
        
        g.setColor(clr);
        g.setFont(temp);
    }
    
    private class FieldListener extends SuiKeyAdapter {
        public void keyPressed(SuiKeyEvent e) {
            int key = e.getKeyCode();
            char c = e.getKeyChar();

            String value = getText();
            if (key == Input.KEY_LEFT) {
                if (cursorPos > 0) {
                    cursorPos--;
                }
            } else if (key == Input.KEY_RIGHT) {
                if (cursorPos < value.length()) {
                    cursorPos++;
                }
            } else if (key == Input.KEY_RETURN) {
                fireActionPerformed(actionCommand);
            } else if (key == Input.KEY_BACK) {
                if ((cursorPos > 0) && (value.length() > 0)) {
                    if (cursorPos < value.length()) {
                        value = value.substring(0, cursorPos - 1) + value.substring(cursorPos);
                    } else {
                        value = value.substring(0, cursorPos - 1);
                    }
                    cursorPos--;
                }
            } else if ((c < 127) && (c > 31) && (value.length() < maxChars)) {
                if (cursorPos < value.length()) {
                    value = value.substring(0, cursorPos) + c + value.substring(cursorPos);
                } else {
                    value = value.substring(0, cursorPos) + c;
                }
                cursorPos++;
            }

            if (value != getText())
                setText(value);
        }
    }
}
