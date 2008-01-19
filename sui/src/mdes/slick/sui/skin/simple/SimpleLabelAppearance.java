/*
 * SimpleLabelAppearance.java
 *
 * Created on November 7, 2007, 8:25 PM
 */

package mdes.slick.sui.skin.simple;

import mdes.slick.sui.Component;
import mdes.slick.sui.Label;
import mdes.slick.sui.Theme;
import mdes.slick.sui.skin.SkinUtil;
import mdes.slick.sui.Skin;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author davedes
 */
public class SimpleLabelAppearance extends SimpleContainerAppearance {
    
    public void render(GUIContext ctx, Graphics g, Component comp, Skin skin, Theme theme) {
        super.render(ctx, g, comp, skin, theme);
        SkinUtil.renderLabelBase(g, (Label)comp);
    }
}
