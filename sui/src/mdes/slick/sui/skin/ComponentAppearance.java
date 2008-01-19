/*
 * ComponentAppearance.java
 *
 * Created on November 7, 2007, 8:06 PM
 */

package mdes.slick.sui.skin;

import mdes.slick.sui.Component;
import mdes.slick.sui.Skin;
import mdes.slick.sui.Theme;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author Matt
 */
public interface ComponentAppearance {
    public void render(GUIContext ctx, Graphics g, Component comp, Skin skin, Theme theme);
    public void update(GUIContext ctx, int delta, Component comp, Skin skin, Theme theme);
    
    public boolean contains(Component comp, float x, float y);
    
    public void install(Component comp, Skin skin, Theme theme);
    public void uninstall(Component comp, Skin skin, Theme theme);
}
