/*
 * SuiToolTip.java
 *
 * Created on November 25, 2007, 2:52 PM
 */

package mdes.slick.sui;

import mdes.slick.sui.skin.ComponentAppearance;

/**
 *
 * @author davedes
 */
public class SuiToolTip extends SuiLabel {
    
    private SuiComponent owner;
    
    /** Creates a new instance of SuiToolTip */
    public SuiToolTip() {
    }
    
    public void updateAppearance() {
        setAppearance(Sui.getSkin().getToolTipAppearance());
    }

    public SuiComponent getOwner() {
        return owner;
    }

    public void setOwner(SuiComponent owner) {
        this.owner = owner;
    }
}
