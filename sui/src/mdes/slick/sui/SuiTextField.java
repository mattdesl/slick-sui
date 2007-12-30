/*
 * SuiTextField.java
 *
 * Created on December 2, 2007, 5:01 PM
 */

package mdes.slick.sui;

import mdes.slick.sui.event.SuiActionEvent;
import mdes.slick.sui.event.SuiActionListener;
import mdes.slick.sui.event.SuiKeyAdapter;
import mdes.slick.sui.event.SuiKeyEvent;
import mdes.slick.sui.event.SuiKeyListener;
import org.newdawn.slick.Font;
import org.newdawn.slick.Input;

/**
 *
 * @author davedes
 */
public class SuiTextField extends SuiTextComponent {
    
    
    /** The action command, initially null. */
    private String actionCommand = null;
    protected SuiKeyListener keyListener = new FieldKeyListener();
        
    private char maskCharacter = '*';
    private boolean maskEnabled = false;
    private String maskText = "";
    
    public SuiTextField() {
        this(true);
    }
    
    public SuiTextField(int cols) {
        this(null, cols);
    }
    
    public SuiTextField(String text) {
        this(text, 0);
    }
    
    /** Creates a new instance of SuiTextField */
    public SuiTextField(String text, int cols) {
        this();
        setText(text);
        this.actionCommand = text;
        Font f = getFont();
        Padding pad = getPadding();
        if (f!=null) {
            float width = 0;
            float oneCol = f.getWidth(COL_CHAR);
            if (cols<=0) {
                if (text!=null&&text.length()!=0)
                    width = pad.left+f.getWidth(text)+oneCol+pad.right;
            } else {
                width = oneCol * cols;
            }
            setWidth(width);
            setHeight(f.getLineHeight() + pad.top + pad.bottom);
        }
    }    
    
    SuiTextField(boolean updateAppearance) {
        addKeyListener(keyListener);
        
        if (updateAppearance)
            updateAppearance();
    }
    
    public void updateAppearance() {
        setAppearance(Sui.getSkin().getTextFieldAppearance(this));
    }
        
    /**
     * Sets the action command to be passed to
     * <tt>SuiActionEvent</tt>s when this ENTER
     * is pressed on this text field.
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
        
    /**
     * Used by skins to render the text in a text field. This
     * text is sometimes masked 
     */
    public String getDisplayText() {
        return isMaskEnabled() ? maskText : getText();
    }
    
    protected void textChanged(String oldText) {
        super.textChanged(oldText);
        updateMaskText();
    }
    
    private void updateMaskText() {
        String text = getText();
        StringBuffer buf = new StringBuffer();
        char ch = getMaskCharacter();
        for (int i=0; i<text.length(); i++) {
            buf.append(ch);
        }
        maskText = buf.toString();
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
    
    public boolean isMaskEnabled() {
        return maskEnabled;
    }

    public void setMaskEnabled(boolean maskEnabled) {
        this.maskEnabled = maskEnabled;
    }

    public char getMaskCharacter() {
        return maskCharacter;
    }

    public void setMaskCharacter(char maskCharacter) {
        this.maskCharacter = maskCharacter;
        updateMaskText();
    }
    
    protected class FieldKeyListener extends SuiKeyAdapter {
        
        public void keyPressed(SuiKeyEvent e) {
            int key = e.getKeyCode();
            if (key == Input.KEY_ENTER) {
                releaseFocus();
                fireActionPerformed(actionCommand);
            } else if (key == Input.KEY_HOME) {
                setCaretPosition(0);
            } else if (key == Input.KEY_END) {
                setCaretPosition(getText().length());
            }
        }
    }
}