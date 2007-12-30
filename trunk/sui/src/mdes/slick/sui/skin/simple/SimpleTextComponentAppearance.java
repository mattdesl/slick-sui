/*
 * SimpleTextComponentAppearance.java
 *
 * Created on December 29, 2007, 5:57 PM
 */

package mdes.slick.sui.skin.simple;

import mdes.slick.sui.Point;
import mdes.slick.sui.SuiComponent;
import mdes.slick.sui.SuiSkin;
import mdes.slick.sui.SuiTextComponent;
import mdes.slick.sui.SuiTheme;
import mdes.slick.sui.Timer;
import mdes.slick.sui.event.SuiChangeEvent;
import mdes.slick.sui.event.SuiChangeListener;
import mdes.slick.sui.skin.TextComponentAppearance;
import org.lwjgl.input.Cursor;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author davedes
 */
public class SimpleTextComponentAppearance extends SimpleContainerAppearance 
                                                implements TextComponentAppearance {
    
    protected Timer flashTimer = new Timer(500);
        
    protected boolean renderCaret = false;
    protected boolean still = false;
    
    protected Timer delayTimer = new Timer(800);
    
    protected SuiChangeListener change = new SuiChangeListener() {
        public void stateChanged(SuiChangeEvent e) {
            still = true;
            delayTimer.restart();
        }
    };
        
    public SimpleTextComponentAppearance() {
        flashTimer.setRepeats(true);
        delayTimer.setRepeats(false);
        flashTimer.start();
    }
    
    public void update(GUIContext ctx, int delta, SuiComponent comp, SuiSkin skin, SuiTheme theme) {
        super.update(ctx, delta, comp, skin, theme);
        flashTimer.update(ctx, delta);
        delayTimer.update(ctx, delta);
        
        if (delayTimer.isAction()) {
            still = false;
        }
        
        if (still)
            renderCaret = true;
        else if (flashTimer.isAction())
            renderCaret = !renderCaret;
    }
    
    public void install(SuiComponent comp, SuiSkin skin, SuiTheme theme) {
        super.install(comp, skin, theme);
        if (skin instanceof SimpleSkin) {
            comp.addMouseListener(((SimpleSkin)skin).getSelectCursorListener());
        }
        ((SuiTextComponent)comp).addChangeListener(change);
    }
    
    public void uninstall(SuiComponent comp, SuiSkin skin, SuiTheme theme) {
        super.uninstall(comp, skin, theme);
        if (skin instanceof SimpleSkin) {
            comp.removeMouseListener(((SimpleSkin)skin).getSelectCursorListener());
        }
        ((SuiTextComponent)comp).removeChangeListener(change);
    }
    
    
    public int viewToModel(SuiTextComponent comp, float x, float y) { 
        return -1;
    }
    
    public Point modelToView(SuiTextComponent comp, int pos) {
        return null;
    }
}
