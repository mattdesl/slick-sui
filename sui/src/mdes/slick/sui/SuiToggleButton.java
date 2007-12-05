/*
 * SuiToggleButton.java
 *
 * Created on November 10, 2007, 7:19 PM
 */

package mdes.slick.sui;

import org.newdawn.slick.Image;


/**
 *
 * @author davedes
 */
public class SuiToggleButton extends SuiButton {
    
    private boolean selected = false;
    private Image selectedImage = null;
    private Image disabledSelectedImage = null;
    private Image rolloverSelectedImage = null;
    
    /** Creates a new instance of SuiToggleButton */
    public SuiToggleButton(String str) {
        super(str);
    }
    
    public SuiToggleButton(Image img) {
        super(img);
    }
    
    public SuiToggleButton() {
        super();
    }
    
    SuiToggleButton(boolean updateAppearance) {
        super(false);
        if (updateAppearance)
            updateAppearance();
    }
    
    public void updateAppearance() {
        setAppearance(Sui.getSkin().getToggleButtonAppearance(this));
    }
    
    public boolean isSelected() {
        return selected;
    }
    
    public void setSelected(boolean b) {
        this.selected = b;
    }
    
    public void press() {
        selected = !selected;
        super.press();
    }
    
    public Image getSelectedImage() {
        return selectedImage;
    }

    public void setSelectedImage(Image selectedImage) {
        this.selectedImage = selectedImage;
    }
    
    public Image getDisabledSelectedImage() {
        return disabledSelectedImage;
    }

    public void setDisabledSelectedImage(Image disabledSelectedImage) {
        this.disabledSelectedImage = disabledSelectedImage;
    }

    public Image getRolloverSelectedImage() {
        return rolloverSelectedImage;
    }

    public void setRolloverSelectedImage(Image rolloverSelectedImage) {
        this.rolloverSelectedImage = rolloverSelectedImage;
    }
}
