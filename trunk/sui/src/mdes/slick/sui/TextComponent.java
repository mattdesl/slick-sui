/*
 * TextComponent.java
 *
 * Created on December 2, 2007, 5:00 PM
 */

package mdes.slick.sui;

import mdes.slick.sui.event.*;
import mdes.slick.sui.skin.ComponentAppearance;
import mdes.slick.sui.skin.TextComponentAppearance;
import org.newdawn.slick.Input;

/**
 * 
 * @author davedes
 */
public abstract class TextComponent extends Container {
    
    private String text = null;
    private boolean editable = true;
    private int caretPos = 0;
    protected KeyListener keyListener = new TextKeyListener();
    protected MouseListener mouseListener = new TextMouseListener();
    private int maxChars = Integer.MAX_VALUE;
    
    protected static final String COL_CHAR = "w";
    
    /**
     * Creates a new instance of TextComponent. By default, no call to
     * updateAppearance is made in the construction of this component.
     */
    public TextComponent() {
        super(false);
        addKeyListener(keyListener);
        addMouseListener(mouseListener);
        setFocusable(true);
    }
    
    /**
     * Gets the current appearance for this text component.
     *
     * @return the appearance set for this component
     * 1.5 feature
    public TextComponentAppearance getAppearance() {
        return (TextComponentAppearance)super.getAppearance();
    }*/
        
    /**
     * Sets the appearance for this text component. If <code>appearance</code> is
     * not an instance of TextComponentAppearance, an 
     * <code>IllegalArgumentException</code> is thrown.
     *
     * @param appearance the new appearance to set
     */
    public void setAppearance(ComponentAppearance appearance) {
        if (!(appearance instanceof TextComponentAppearance))
            throw new IllegalArgumentException("must pass instance of text component appearance");
        super.setAppearance(appearance);
    }
    
    public int viewToModel(float x, float y) {
        TextComponentAppearance appearance = (TextComponentAppearance)getAppearance();
        if (appearance!=null) {
            return appearance.viewToModel(this, x, y);
        } else 
            return -1;
    }
    
    public Point modelToView(int pos) {
        TextComponentAppearance appearance = (TextComponentAppearance)getAppearance();
        if (appearance!=null) {
            return appearance.modelToView(this, pos);
        } else 
            return null;
    }
    
    public int getCaretPosition() {
        return caretPos;
    }
    
    public void setCaretPosition(int caretPos) {
        int old = this.caretPos;
        this.caretPos = caretPos;
        if (old!=caretPos)
            caretPositionChanged(old);
    }
        
    public String getText() {
        if (text == null)
            text = "";
        return text;
    }
    
    public void setText(String text) {
        String old = this.text;
        this.text = text;
        if (this.text == null)
            this.text = "";
        caretPos = this.text.length();
        if (old!=text) {
            fireStateChanged();
            textChanged(old);
        }
    }
    
    /**
     * Allows subclasses to tap into changed events directly without
     * the need for listeners.
     * 
     * @param oldText the previous value of the text, before it was changed
     */
    protected void textChanged(String oldText) {
        //do nothing
    }
    
    protected void caretPositionChanged(int old) {
        //do nothing
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
    
    protected class TextKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (!isEditable())
                return;
            
            int key = e.getKeyCode();
            char c = e.getKeyChar();
            
            if (text == null)
                text = "";
            
            String oldText = text;
            int oldCaret = caretPos;
            
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
                if (caretPos < text.length())
                    caretPos++;
            } else if (key == Input.KEY_BACK) {
                if ((caretPos>0) && (text.length()>0)) {
                    if (caretPos < text.length())
                        text = text.substring(0, caretPos-1) + text.substring(caretPos);
                    else
                        text = text.substring(0, caretPos-1);
                    caretPos--;
                }
            } else if (key == Input.KEY_DELETE) {
                if (caretPos < text.length()) {
                    text = text.substring(0, caretPos) + text.substring(caretPos+1);
                }
            }
            
            if (oldText != text) { //changed
                textChanged(oldText);
                fireStateChanged();
            }
            
            if (oldCaret != caretPos) {
                caretPositionChanged(oldCaret);
            }
        }
    }
    
    protected class TextMouseListener extends MouseAdapter {
        
        public void mousePressed(MouseEvent e) {
            if (!isEditable())
                return;
            
            int pos = viewToModel(e.getX(), e.getY());
            if (pos>=0) {
                setCaretPosition(pos);
            }
        }
    }
}
