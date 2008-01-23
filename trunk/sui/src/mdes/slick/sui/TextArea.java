/*
 * TextArea.java
 *
 * Created on December 26, 2007, 10:48 PM
 */

package mdes.slick.sui;

import java.util.ArrayList;
import mdes.slick.sui.event.KeyAdapter;
import mdes.slick.sui.event.KeyEvent;
import mdes.slick.sui.event.KeyListener;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Font;
import org.newdawn.slick.Input;

/**
 *
 * @author davedes
 */
public class TextArea extends TextComponent {
    
    private ArrayList lines = new ArrayList();
    
    private boolean wrapEnabled = true;
    private boolean wrapDirty = true;
    private Dimension minSize = new Dimension();
    private boolean resizing = true;
    
    private int currentLine = 0;
    
    protected KeyListener keyListener = new AreaKeyListener();
    
    public TextArea() {
        this(true);
    }
    
    public TextArea(String text) {
        this(text, 0, 0);
    }
    
    public TextArea(int cols, int rows) {
        this(null, 0, 0);
    }
    
    /** Creates a new instance of SuiTextField */
    public TextArea(String text, int cols, int rows) {
        this();
        setText(text);
        
        Font f = getFont();
        if (f!=null) {
            Padding pad = getPadding();
            float oneCol = f.getWidth(COL_CHAR);
            float oneRow = f.getLineHeight();
            
            float width, height;
            
            if (cols>0)
                width = oneCol * cols;
            else {
                float maxWidth = 0f;
                for (int i=0; i<getLineCount(); i++) {
                    maxWidth = Math.max(maxWidth, getLine(i).width);
                }
                width = pad.left+maxWidth+pad.right;
            }
            
            if (rows>0)
                height = oneRow * rows;
            else {
                height = oneRow * getLineCount();               
            }
            setSize(width, height);
            getMinimumSize().setSize(width, height);
        }
    }
    
    /**
     * Creates a new instance of TextArea
     */
    TextArea(boolean updateAppearance) {
        addKeyListener(keyListener);
        
        if (updateAppearance)
            updateAppearance();
    }
    
    public void updateAppearance() {
        setAppearance(Sui.getSkin().getTextAreaAppearance(this));
    }
       
    public void setFont(Font font) {
        super.setFont(font);
        wrapDirty = true;
    }
    
    public void setWidth(float width) {
        super.setWidth(width);
        wrapDirty = true;
    }
        
    protected void textChanged(String oldText) {
        super.textChanged(oldText);
        wrapDirty = true;
    }
    public void setAutoResize(boolean resizing) {
        this.resizing = resizing;
    }
    
    public boolean isAutoResize() {
        return resizing;
    }
    
    protected void caretPositionChanged() {
        //ensureLines();
    }
       
    public int getCurrentLine() {
        int pos = getCaretPosition();
        if (pos < getText().length()) {
            for (int i=0; i<getLineCount(); i++) {
                if (getLine(i).containsPosition(pos)) {
                    return i;
                }
            }
            return -1;
        } else {
            int count = getLineCount();
            if (count>0)
                count--;
            return count;
        }
    }
    
    protected void ensureLines() {
        if (wrapDirty) {        
            wrapDirty = false;
            
            Font font = getFont();
            Padding pad = getPadding();
            String value = getText();
            
            float maxWidth = getWidth()-pad.left-pad.right;

            //use clear() instead?
            lines = new ArrayList();
            
            int offset = 0;
            int lastLineLen = 0;
            
            float defaultLineHeight = font.getLineHeight();
            
            //if we are wrap-disabled
            if (!isWrapEnabled()) {
                float yoff = 0f;
                if (font instanceof AngelCodeFont) {
                    yoff = ((AngelCodeFont)font).getYOffset(value);
                }
                //TODO: add '\n' support for non-wrapped text
                float width = font.getWidth(value);
                lines.add( new Line(value, yoff, width, defaultLineHeight, 0) );
                
                float maxHeight = pad.top + defaultLineHeight + pad.bottom;
                if (resizing) {
                    setHeight(Math.max(getMinimumSize().height, maxHeight));
                    setWidth(Math.max(getMinimumSize().width, pad.left+width+pad.right));
                }
                return;
            }
            
            while (value.length() > 0) {
                String aLine = value;
                
                int newLine = aLine.indexOf('\n');
                if (newLine >= 0) { //split the line based on newline
                    aLine = aLine.substring(0, newLine)+"\n";
                }
                
                boolean appendSpace = false;
                while (font.getWidth(aLine) > maxWidth) {
                    appendSpace = true;
                    int rSpace = aLine.lastIndexOf(' ');
                    if (rSpace==-1) {
                        if (aLine.length()>1) {
                            int maxChars = 0;
                            for (int i=0; i<aLine.length(); i++) {
                                if (font.getWidth(aLine.substring(0, i)) < maxWidth)
                                    maxChars++;
                            }
                            if (maxChars>0)
                                maxChars--;
                            aLine = aLine.substring(0, maxChars);
                        }
                        appendSpace = false;
                        break;
                    }
                    aLine = aLine.substring(0, rSpace);
                }
                if (appendSpace)
                    aLine += " ";

                //get yoffset for line
                float yoff = 0f;
                if (font instanceof AngelCodeFont) {
                    yoff = ((AngelCodeFont)font).getYOffset(aLine);
                }
                
                float width = font.getWidth(aLine);
                //float height = font.getHeight(aLine);
                //if (height==0f)
                //    height = defaultLineHeight;
                
                offset += lastLineLen;
                lines.add( new Line(aLine, yoff, width, defaultLineHeight, offset) );
                lastLineLen = aLine.length();
                
                value = value.substring(aLine.length());
            }
            
            if (lines.isEmpty())
                lines.add( new Line("", 0f, 0f, defaultLineHeight, 0) );
            
            float maxHeight = pad.top + defaultLineHeight*lines.size() + pad.bottom;
            if (resizing) {
                setHeight(Math.max(getMinimumSize().height, maxHeight));
            }
        }
    }
    
    public int getLineCount() {
        ensureLines();
        return lines.size();
    }
    
    public Line getLine(int index) {
        ensureLines();
        return (Line)lines.get(index);
    }
    
    public String getLineAsText(int index) {
        return getLine(index).str;
    }
    
    public Line[] getLines() {
        ensureLines();
        return (Line[])lines.toArray(new Line[lines.size()]);
    }
    
    public String[] getLinesAsText() {
        ensureLines();
        String[] array = new String[lines.size()];
        for (int i=0; i<array.length; i++) {
            array[i] = ((Line)lines.get(i)).str;
        }
        return array;
    }
    
    //an individual line which holds the string and the yoffset 
    public class Line {
        public final String str;
        public final float yoff;
        public final float width;
        public final float height;
        public final int offset;
        
        protected Line(String str, float yoff, float width, float height, int offset) {
            this.str = str;
            this.yoff = yoff;
            this.width = width;
            this.height = height;
            this.offset = offset;
        }
        
        /**
         * A convenience method to check whether the position (eg. caret) 
         * is contained within this line's offset bounds.
         */
        public boolean containsPosition(int position) {
            return position >= offset && position < offset+str.length();                
        }
        
        public String toString() {
            return str;
        }
    }
    
    /*
    protected void drawWrappedString(Graphics g, String text) {
        Color oldColor = g.getColor();
        Font oldFont = g.getFont();
        
        g.setFont(getFont());
        g.setColor(isEnabled()?getForeground():getDisabledForeground());
        
        Font f = getFont();
        
        int pad = getPadding();
        
        float x = getAbsoluteX()+pad;
        float y = getAbsoluteY()+pad;
        
        int maxWidth = getWidth()-pad;
        int height = getHeight()-pad;
        
        Vector lines = new Vector();
        while (text.length() > 0) {
            String aLine = text;
            while (getFont().getWidth(aLine) > maxWidth) {
                int rSpace = aLine.lastIndexOf(" ");
                if (rSpace==-1) {
                    break;
                }
                aLine = aLine.substring(0, rSpace);
            }
            lines.add(aLine);
            text = text.substring(aLine.length()).trim();
        }
        
        final int numLines = lines.size();
        final int lineHeight = f.getHeight(" ");
        final float startY = y;
        
        for( int i = 0; i < numLines; i++ ) {
                String line = (String)lines.elementAt( i );
                float width = f.getWidth(line);
                float thisLineX = x;
                int yoff = getYOffset(line);
                g.drawString(line, (int)thisLineX, (int)(startY+(i*lineHeight-yoff)));
        }
        
        g.setFont(oldFont);
        g.setColor(oldColor);
    }*/

    public boolean isWrapEnabled() {
        return wrapEnabled;
    }

    public void setWrapEnabled(boolean wrapEnabled) {
        this.wrapEnabled = wrapEnabled;
    }
    
    protected class AreaKeyListener extends KeyAdapter {
        
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            
            String text = getText();
            int caretPos = getCaretPosition();
                    
            if (key == Input.KEY_ENTER) {
                char c = '\n';
                if (text.length() < getMaxChars()) {
                    if (caretPos < text.length()) { //HACK: double adding of '\n' fixes it??
                        text = text.substring(0, caretPos) + c + text.substring(caretPos);
                    } else {
                        text = text.substring(0, caretPos) + c + c;
                    }
                    caretPos++;
                }
            } else if (key == Input.KEY_HOME) {
                int index = getCurrentLine();
                if (index>=0) {
                    int off = getLine(index).offset;
                    caretPos = off;
                }
            } else if (key == Input.KEY_END) {
                int index = getCurrentLine();
                if (index>=0) {
                    Line line = getLine(index);
                    int off = line.offset;
                    int tot = line.str.trim().length();
                    caretPos = off+tot;
                }
            } else if (key == Input.KEY_UP) {
                int index = getCurrentLine();
                if (index > 0) {
                    Line line = getLine(index);
                    Line pre = getLine(index-1);
                    
                    //finds index of current line
                    //eg: 'abc def'
                    //      ^ index 1
                    int currentIndex = caretPos - line.offset;
                    int preIndex = Math.max(0, Math.min(pre.str.trim().length(), currentIndex));
                    caretPos = pre.offset + preIndex;
                }
            } else if (key == Input.KEY_DOWN) {
                int index = getCurrentLine();
                if (index >= 0 && index < getLineCount()-1) {
                    Line line = getLine(index);
                    Line next = getLine(index+1);
                    
                    //finds index of current line
                    //eg: 'abc def'
                    //      ^ index 1
                    int currentIndex = caretPos - line.offset;
                    int nextIndex = Math.max(0, Math.min(next.str.trim().length(), currentIndex));
                    if (nextIndex <= next.str.trim().length())
                        caretPos = next.offset + nextIndex;
                }
            }
            
            setText(text);
            setCaretPosition(caretPos);
        }
    }

    public Dimension getMinimumSize() {
        return minSize;
    }

    public void setMinimumSize(Dimension minSize) {
        this.minSize = minSize;
    }
}
