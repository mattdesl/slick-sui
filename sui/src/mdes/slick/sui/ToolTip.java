/*
 * ToolTip.java
 *
 * Created on November 25, 2007, 2:52 PM
 */

package mdes.slick.sui;


/**
 *
 * @author davedes
 */
public class ToolTip extends Label {
    
    private Component owner;
    
    /**
     * Creates a new instance of ToolTip
     */
    public ToolTip() {
    }
    
    public void updateAppearance() {
        setAppearance(Sui.getSkin().getToolTipAppearance(this));
    }

    public Component getOwner() {
        return owner;
    }

    public void setOwner(Component owner) {
        this.owner = owner;
    }
}
