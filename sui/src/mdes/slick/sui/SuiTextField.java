/*
 * SuiTextField.java
 *
 * Created on December 2, 2007, 5:01 PM
 */

package mdes.slick.sui;

import org.newdawn.slick.Font;

/**
 *
 * @author davedes
 */
public class SuiTextField extends SuiTextComponent {
    
    private final String COL_CHAR = "w";
    
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
        if (updateAppearance)
            updateAppearance();
    }
    
    public void updateAppearance() {
        setAppearance(Sui.getSkin().getTextFieldAppearance(this));
    }
            
    public int viewToModel(float x, float y) {
        if (getWidth()==0 || getHeight()==0)
            return -1;
        //TODO: support this
        return -1;
    }
}
