/*
 * TextComponent.java
 *
 * Created on December 2, 2007, 5:00 PM
 */

package mdes.slick.sui;

import mdes.slick.sui.event.ChangeEvent;
import mdes.slick.sui.event.ChangeListener;
import mdes.slick.sui.event.KeyAdapter;
import mdes.slick.sui.event.KeyEvent;
import mdes.slick.sui.event.KeyListener;
import mdes.slick.sui.event.MouseAdapter;
import mdes.slick.sui.event.MouseEvent;
import mdes.slick.sui.event.MouseListener;
import mdes.slick.sui.skin.ComponentAppearance;
import mdes.slick.sui.skin.TextComponentAppearance;

import org.newdawn.slick.Input;
import org.newdawn.slick.gui.GUIContext;

/**
 * 
 * @author davedes
 */
public abstract class TextComponent extends Container {
    
    private String text = null;
    private boolean editable = true;
    private int caretPos = 0;
    private SelectionRange selectionRange = new SelectionRange(0,0);
    protected KeyListener keyListener = new TextKeyListener();
    protected MouseListener mouseListener = new TextMouseListener();
    private int maxChars = Integer.MAX_VALUE;
    
    public static final int STANDARD_INITIAL_REPEAT_DELAY = 400;
    public static final int STANDARD_REPEAT_DELAY = 50;
    
    private static boolean defaultRepeatEnabled = true;
    private static int defaultRepeatDelay = STANDARD_REPEAT_DELAY;
    private static int defaultInitialRepeatDelay = STANDARD_INITIAL_REPEAT_DELAY;
    
    private Timer keyRepeats = new Timer(defaultRepeatDelay);
    private boolean keyRepeating = defaultRepeatEnabled;
            
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
        keyRepeats.setInitialDelay(defaultInitialRepeatDelay);
        keyRepeats.setRepeats(true);
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
        
        System.out.println(x + "," + y);
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
           
    public void updateComponent(GUIContext ctx, int delta) {
        super.updateComponent(ctx, delta);
        
        if (keyRepeating) {
            keyRepeats.update(ctx, delta);
            if (keyRepeats.isAction()) {
                doRepeat();
            }
        }
    }
    
    /**
     * Provides a hint for the creation of new text components to
     * enable key repeat with the specified delays. This does not
     * affect any existing instances of TextComponent. If individual
     * text component timing is desired, use the instance methods.
     * 
     * @param initialDelay the default initial delay for new text components
     *      to use
     * @param delay the default delay for new text components to use
     */
    public static void enableDefaultKeyRepeat(int initialDelay, int delay) {
        defaultRepeatEnabled = true;
        defaultInitialRepeatDelay = initialDelay;
        defaultRepeatDelay = delay;
    }
    
    /**
     * Provides a hint for the creation of new text components to
     * enable key repeat with the last used delays. This does not
     * affect any existing instances of TextComponent. If individual
     * text component timing is desired, use the instance methods. If 
     * no delays were last specified in enableDefaultKeyRepeat, the delays 
     * will be STANDARD_INITIAL_REPEAT_DELAY and STANDARD_REPEAT_DELAY.
     */
    public static void enableDefaultKeyRepeat() {
        defaultRepeatEnabled = true;
    }
    
    /**
     * Provides a hint for the creation of new text components to
     * disable key repeat. This does not affect any existing instances 
     * of TextComponent. If individual text component timing is desired, 
     * use the instance methods.
     */
    public static void disableDefaultKeyRepeat() {
        defaultRepeatEnabled = false;
    }
    
    /**
     * Returns <tt>true</tt> if new text component instances use key repeating.
     * 
     * @return <tt>true</tt> if new instances have key repeat enabled, <tt>false</tt> 
     *      otherwise
     */
    public static boolean isDefaultKeyRepeatEnabled() {
        return defaultRepeatEnabled;
    }
    
    /**
     * Enables key repeating on this text component instance with the specified delays.
     * If a "global" or default setting is desired, use the static methods instead. 
     * <p>
     * If the key repeats timer is currently running, it will be restarted to reflect the
     * new delays.
     *
     * @param initialDelay the initial delay before repeating keys
     * @param delay the delay between each key repeat
     */
    public void enableKeyRepeat(int initialDelay, int delay) {
        keyRepeating = true;
        keyRepeats.setDelay(initialDelay);
        keyRepeats.setInitialDelay(delay);
        if (keyRepeats.isRunning())
            keyRepeats.restart();
    }
    
    /**
     * Enables key repeating on this text component instance with the last used delays.
     * If a "global" or default setting is desired, use the static methods instead. If 
     * no delays were last specified in enableKeyRepeat, the delays will be 
     * STANDARD_INITIAL_REPEAT_DELAY and STANDARD_REPEAT_DELAY.
     * <p>
     * If the key repeats timer is already running, it will <i>not</i> be restarted.
     */
    public void enableKeyRepeat() {
        keyRepeating = true;
    }
    
    /**
     * Disables key repeating on this text component instance. 
     * If a "global" or default setting is desired, use the static methods instead.
     * <p>
     * If the key repeats timer is currently running, it will be stopped.
     */
    public void disableKeyRepeat() {
        keyRepeating = false;
        keyRepeats.stop();
    }
    
    /**
     * Returns <tt>true</tt> if this text component instance has key repeating enabled.
     * @return <tt>true</tt> if key repeating is enabled on this component, <tt>false</tt>
     *      otherwise
     */
    public boolean isKeyRepeatEnabled() {
        return keyRepeating;
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
    
    private int key;
    private char c;
    private boolean isShiftPress = false;
    
    private void doRepeat() {
        if (!isEditable())
            return;

        if (text == null)
            text = "";
        
        String oldText = text;
        int oldCaret = caretPos;

        if ( (c<127) && (c>31) && (text.length() < getMaxChars()) ) {
            if (selectionRange.getEndIndex() == selectionRange.getStartIndex()) {
                if (caretPos < text.length()) {
                    text = text.substring(0, caretPos) + c + text.substring(caretPos);
                } else {
                    text = text.substring(0, caretPos) + c;
                }
                caretPos++;
            } else {
        	text = text.substring(0, selectionRange.getStartIndex()) + c + text.substring(selectionRange.getEndIndex());
        	caretPos = selectionRange.getStartIndex()+1;
            }
            
            this.selectionRange.setStartIndex(caretPos);
            this.selectionRange.setEndIndex(caretPos);
        } else if (key == Input.KEY_LEFT) {
            if (!isShiftPress) {
                if (caretPos > 0)
                    caretPos--;
                
                this.selectionRange.setStartIndex(caretPos);
                this.selectionRange.setEndIndex(caretPos);
            } else {
        	if (caretPos == selectionRange.getEndIndex()) {
        	    if (selectionRange.getStartIndex() > 0) {
        		selectionRange.setStartIndex(selectionRange.getStartIndex() - 1);
        	    }
        	} else {
        	    selectionRange.setEndIndex(selectionRange.getEndIndex() - 1);
        	}
            }
        } else if (key == Input.KEY_RIGHT) {
            if (!isShiftPress) {
                if (caretPos < text.length())
                    caretPos++;
                
                this.selectionRange.setStartIndex(caretPos);
                this.selectionRange.setEndIndex(caretPos);
            } else {
        	if (caretPos == selectionRange.getStartIndex()) {
        	    if (selectionRange.getEndIndex() < text.length()) {
        		selectionRange.setEndIndex(selectionRange.getEndIndex() + 1);
        	    }
        	} else {
        	    selectionRange.setStartIndex(selectionRange.getStartIndex() + 1);
        	}
            }
        } else if (key == Input.KEY_BACK) {
            if (selectionRange.getEndIndex() == selectionRange.getStartIndex()) {
                if ((caretPos>0) && (text.length()>0)) {
                    if (caretPos < text.length())
                        text = text.substring(0, caretPos-1) + text.substring(caretPos);
                    else
                        text = text.substring(0, caretPos-1);
                    caretPos--;
                }
            } else {
        	text = text.substring(0,selectionRange.getStartIndex()) + text.substring(selectionRange.getEndIndex());
        	caretPos = selectionRange.getStartIndex();
        	
        	this.selectionRange.setStartIndex(caretPos);
                this.selectionRange.setEndIndex(caretPos);
            }
        } else if (key == Input.KEY_DELETE) {
            if (selectionRange.getEndIndex() == selectionRange.getStartIndex()) {
                if (caretPos < text.length()) {
                    text = text.substring(0, caretPos) + text.substring(caretPos+1);
                }
            } else {
        	text = text.substring(0,selectionRange.getStartIndex()) + text.substring(selectionRange.getEndIndex());
        	caretPos = selectionRange.getStartIndex();
        	
        	this.selectionRange.setStartIndex(caretPos);
                this.selectionRange.setEndIndex(caretPos);
            }
        } else if (key == Input.KEY_END) {
            if (isShiftPress) {
        	selectionRange.setEndIndex(text.length());
            }
        } else if (key == Input.KEY_HOME) {
            if (isShiftPress) {
        	selectionRange.setStartIndex(0);
            }
        }
        
        doRepeatImpl(key, c);

        if (oldText != text) { //changed
            textChanged(oldText);
            fireStateChanged();
        }

        if (oldCaret != caretPos) {
            caretPositionChanged(oldCaret);
        }
    }
    
    protected void doRepeatImpl(int key, char c) {
        //do nothing
    }
    
    public SelectionRange getSelectionRange() {
        return selectionRange;
    }

    public void setSelectionRange(int startIndex, int endIndex) {
        this.selectionRange.setStartIndex(startIndex);
        this.selectionRange.setEndIndex(endIndex);
    }

    protected class TextKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            key = e.getKeyCode();
            c = e.getKeyChar();
            doRepeat();
            
            if (key == Input.KEY_LSHIFT || key == Input.KEY_RSHIFT) {
        	isShiftPress = true;
            }
            
            if (keyRepeating) {
                keyRepeats.restart();
            }
        }
        
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == Input.KEY_LSHIFT || e.getKeyCode() == Input.KEY_RSHIFT) {
        	isShiftPress = false;
            }
            
            if (e.getKeyCode()==key) {
                keyRepeats.stop();
            }
        }
    }
    
    protected class TextMouseListener extends MouseAdapter {
        
        public void mousePressed(MouseEvent e) {
            if (!isEditable())
                return;
            
            System.out.println("Click");
            int pos = viewToModel(e.getX(), e.getY());
            if (pos>=0) {
                setCaretPosition(pos);
                selectionRange.setStartIndex(pos);
                selectionRange.setEndIndex(pos);
            }
        }
        
        public void mouseDragged(MouseEvent e) {
            if (!isEditable())
                return;
            
            System.out.println("Move");
            int pos = viewToModel(e.getX(), e.getY());
            if (pos>=0) {
        	if (pos < getCaretPosition()) {
        	    selectionRange.setStartIndex(pos);
        	} else {
        	    selectionRange.setEndIndex(pos);
        	}
            }
        }
        
        public void mouseReleased(MouseEvent e) {
            if (!isEditable())
                return;
            
            System.out.println("Finish");
            int pos = viewToModel(e.getX(), e.getY());
            if (pos>=0) {
        	if (pos < getCaretPosition()) {
        	    selectionRange.setStartIndex(pos);
        	} else {
        	    selectionRange.setEndIndex(pos);
        	}
            }
        }
    }
    
    public class SelectionRange {
	private int startIndex = 0;
	private int endIndex = 0;
	
	public SelectionRange(int startIndex, int endIndex) {
	    this.startIndex = startIndex;
	    this.endIndex = endIndex;
	}
	
	public int getStartIndex() {
	    return startIndex;
	}
	public void setStartIndex(int startIndex) {
	    this.startIndex = startIndex;
	    debug();
	}
	public int getEndIndex() {
	    return endIndex;
	}
	public void setEndIndex(int endIndex) {
	    this.endIndex = endIndex;
	    debug();
	}
	
	private void debug() {
	    System.out.println("Selection Range " + startIndex + "," + endIndex);
	}
    }
}
