/*
 * SuiTextComponent.java
 *
 * Created on December 2, 2007, 5:00 PM
 */

package mdes.slick.sui;

import mdes.slick.sui.event.*;
import org.newdawn.slick.Input;

/**
 * 
 * @author davedes
 */
public abstract class SuiTextComponent extends SuiContainer {
    
    protected String text = null;
    private boolean editable = true;
    private int caretPos = 0;
    protected SuiKeyListener keyListener = new TextKeyListener();
    protected SuiMouseListener mouseListener = new TextMouseListener();
    private int maxChars = Integer.MAX_VALUE;
    
    /**
     * Creates a new instance of SuiTextComponent. By default, no call to
     * updateAppearance is made in the construction of this component.
     */
    public SuiTextComponent() {
        super(false);
        addKeyListener(keyListener);
        addMouseListener(mouseListener);
        setFocusable(true);
    }
    
    public abstract int viewToModel(float x, float y);
    
    public int getCaretPosition() {
        return caretPos;
    }
    
    public void setCaretPosition(int caretPos) {
        this.caretPos = caretPos;
    }
    
    public String getText() {
        if (text == null)
            text = "";
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
        if (this.text == null)
            this.text = "";
        caretPos = this.text.length();
    }
    
    public void setEditable(boolean editable) {
        this.editable = editable;
        setFocusable(editable);
    }
    
    public boolean isEditable() {
        return editable;
    }
    
    public int getMaxChars() {
        return maxChars;
    }

    public void setMaxChars(int maxChars) {
        this.maxChars = maxChars;
    }
                        
    protected class TextKeyListener extends SuiKeyAdapter {
        public void keyPressed(SuiKeyEvent e) {
            if (!isEditable())
                return;
            
            int key = e.getKeyCode();
            char c = e.getKeyChar();
            
            if (text == null)
                text = "";
            
            if ( (c<127) && (c>31) && (text.length() < getMaxChars()) ) {
                if (caretPos < text.length()) {
                    text = text.substring(0, caretPos) + c + text.substring(caretPos);
                } else {
                    text = text.substring(0, caretPos) + c;
                }
                caretPos++;
            } else if (key == Input.KEY_LEFT) {
                if (caretPos > 0)
                    caretPos--;
            } else if (key == Input.KEY_RIGHT) {
                if (caretPos < getText().length())
                    caretPos++;
            } else if (key == Input.KEY_BACK) {
                if ((caretPos>0) && (text.length()>0)) {
                    if (caretPos < text.length())
                        text = text.substring(0, caretPos-1) + text.substring(caretPos);
                    else
                        text = text.substring(0, caretPos-1);
                    caretPos--;
                }
            }
        }
    }
    
    protected class TextMouseListener extends SuiMouseAdapter {
        
        public void mousePressed(SuiMouseEvent e) {
            if (!isEditable())
                return;
            
            int pos = viewToModel(e.getX(), e.getY());
            if (pos>=0) {
                setCaretPosition(pos);
            }
        }
    }
}
