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
public class SuiTextField extends SuiContainer {
    
    private String text = null;
    private boolean editable = true;
    
    public SuiTextField() {
        this(null);
    }
    
    public SuiTextField(int cols) {
        this(null, cols);
    }
    
    public SuiTextField(String text) {
        this(text, 0);
    }
    
    /** Creates a new instance of SuiTextField */
    public SuiTextField(String text, int cols) {
        this(true);
        this.text = text;
        Font f = getFont();
        if (f!=null) {
            float width;
            if (cols<=0) {
                if (text!=null&&text.length()!=0)
                    f.getWidth(text);
            } else {
                int colWidth = f.getWidth("w");
                setWidth(colWidth * cols);
            }
            setHeight(f.getLineHeight());
        }
    }    
    
    SuiTextField(boolean updateAppearance) {
        super(false);
        if (updateAppearance)
            updateAppearance();
        
        setFocusable(true);
    }
    
    public void updateAppearance() {
        setAppearance(Sui.getSkin().getTextFieldAppearance(this));
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public void setEditable(boolean editable) {
        this.editable = editable;
        setFocusable(editable);
    }
    
    public boolean isEditable() {
        return editable;
    }
    
    
}
