/*
 * SimpleToolTipAppearance.java
 *
 * Created on November 7, 2007, 9:00 PM
 */

package mdes.slick.sui.skin.simple;

import mdes.slick.sui.Component;
import mdes.slick.sui.Theme;
import mdes.slick.sui.Skin;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author davedes
 */
public class SimpleToolTipAppearance extends SimpleLabelAppearance {
    
    public void install(Component comp, Skin skin, Theme theme) {
        super.install(comp, skin, theme);
        comp.setOpaque(true);
        comp.getPadding().set(2, 3, 2, 3);
    }
    
    public void render(GUIContext ctx, Graphics g, Component comp, Skin skin, Theme theme) {
        super.render(ctx, g, comp, skin, theme);
        
        if (comp.isBorderRendered()) {
            g.setColor(theme.getPrimaryBorder2());
            g.draw(comp.getAbsoluteBounds());
        }
    }
}
