/*
 * SimpleButtonAppearance.java
 *
 * Created on November 7, 2007, 8:59 PM
 */

package mdes.slick.sui.skin.simple;

import mdes.slick.sui.*;
import mdes.slick.sui.skin.ComponentAppearance;
import mdes.slick.sui.SuiTheme;
import mdes.slick.sui.skin.RenderUtil;
import org.newdawn.slick.fills.*;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import org.lwjgl.opengl.*;
import org.newdawn.slick.opengl.SlickCallable;
import org.newdawn.slick.gui.*;
import mdes.slick.sui.skin.SuiSkin;

/**
 *
 * @author davedes
 */
public class SimpleButtonAppearance extends SimpleLabelAppearance {
    
    private static GradientFill grad = new GradientFill(0f,0f,Color.white,0f,0f,Color.white);
    private static final Color TRANSPARENT_COLOR = new Color(1f,1f,1f,0f);
        
    public void install(SuiComponent comp, SuiSkin skin, SuiTheme theme) {
        super.install(comp, skin, theme);
        comp.setSkinData(createData(comp));
    }
    
    protected ButtonData createData(SuiComponent comp) {
        ButtonData data = new ButtonData();
        RoundedRectangle rect = createRoundedBounds();
        data.roundBounds = rect;
        return data;
    }
    
    protected RoundedRectangle createRoundedBounds() {
        return new RoundedRectangle(0f,0f,0f,0f,5f,15);
    }
    
    protected ButtonData getButtonData(SuiComponent comp) {
        Object obj = comp.getSkinData();
        ButtonData ret = null;
        if (obj!=null && obj instanceof ButtonData) {
            ret = (ButtonData)obj;
        } else {
            ButtonData data = createData(comp);
            comp.setSkinData(data);
        }
        return ret;
    }
    
    public boolean contains(SuiComponent comp, float x, float y) {
        if (SimpleSkin.isRoundRectanglesEnabled()) {
            ButtonData btn = getButtonData(comp);
            if (btn.roundBounds!=null) {
                Rectangle roundRect = btn.roundBounds;
                Rectangle bounds = comp.getAbsoluteBounds();
                roundRect.setX(bounds.getX());
                roundRect.setY(bounds.getY());
                roundRect.setWidth(bounds.getWidth());
                roundRect.setHeight(bounds.getHeight());
                return roundRect.contains(x, y);
            }
        }
        return comp.inside(x, y);
    }
        
    public void render(GUIContext ctx, Graphics g, SuiComponent comp, SuiSkin skin, SuiTheme theme) {
        //renders base
        RenderUtil.renderComponentBase(g, comp);
        
        SuiButton btn = (SuiButton)comp;
        
        //renders button state
        if (SimpleSkin.isRoundRectanglesEnabled())
            renderButtonState(g, theme, btn);
        else
            renderButtonState(g, theme, btn, null);
             
        //renders text/image
        RenderUtil.renderButtonBase(g, btn);
    }
        
    protected void renderButtonState(Graphics g, SuiTheme theme, SuiButton btn) {
        RoundedRectangle roundRect = null;
        ButtonData data = getButtonData(btn);
        if (data!=null && data.roundBounds!=null) {
            roundRect = data.roundBounds;
            Rectangle bounds = btn.getAbsoluteBounds();
            roundRect.setX(bounds.getX());
            roundRect.setY(bounds.getY());
            roundRect.setWidth(bounds.getWidth());
            roundRect.setHeight(bounds.getHeight());
        }
        SimpleButtonAppearance.renderButtonState(g, theme, btn, roundRect);
    }
    
    static void renderButtonState(Graphics g, SuiTheme theme, SuiButton btn, Rectangle aRect) {
        Rectangle rect = aRect;
        if (rect==null)
            rect = btn.getAbsoluteBounds();
        
        int state = btn.getState();
        
        Color lightTop, lightBot, borderLight, borderDark, base;
        
        borderLight = theme.getPrimaryBorder1();
        borderDark = theme.getPrimaryBorder2();
        
        if (btn instanceof SuiToggleButton && ((SuiToggleButton)btn).isSelected()) {
            base = theme.getSecondary1();
            lightTop = theme.getSecondary1();
            lightBot = state==SuiButton.ROLLOVER ? theme.getSecondary1() : theme.getPrimary3();
        } else {
            switch (state) {
                default:
                case SuiButton.UP:
                    base = theme.getPrimary1();
                    lightTop = theme.getPrimary2();
                    lightBot = theme.getPrimary3();
                    break;
                case SuiButton.DOWN:
                    base = theme.getSecondary1();
                    lightTop = theme.getSecondary1();
                    lightBot = theme.getSecondary1();
                    break;
                case SuiButton.ROLLOVER:
                    base = theme.getSecondary1();
                    lightTop = theme.getSecondary2();
                    lightBot = theme.getSecondary3();
                    break;
            }
            if (btn.isPressedOutside()) {
                base = theme.getSecondary1();
                lightTop = theme.getPrimary1();
            }
        }
                
        boolean oldAA = g.isAntiAlias();
        
        float mid = rect.getHeight()/2.0f;
                
        g.setAntiAlias(false);
        
        grad.setStartColor(lightTop);
        grad.setEndColor(base);
        grad.setStart(0, -mid/1.5f);
        grad.setEnd(0, mid/4);
        g.fill(rect, grad);
        
        boolean enabled = btn.isEnabled();
        Color disabledColor = theme.getDisabledMask();
        grad.setStartColor(enabled ? TRANSPARENT_COLOR : disabledColor);
        grad.setEndColor(enabled ? lightBot : disabledColor);
        grad.setStart(0, 0);
        grad.setEnd(0, mid*2);
        g.fill(rect, grad);
        
        if (aRect instanceof RoundedRectangle)
            g.setAntiAlias(true);
        
        grad.setStartColor(enabled ? borderLight : disabledColor);
        grad.setEndColor(borderDark);
        grad.setStart(0, -mid);
        grad.setEnd(0, mid); 
        g.draw(rect, grad);
        
        g.setAntiAlias(oldAA);        
    }
    
    public static class ButtonData {
        RoundedRectangle roundBounds;
    }
}
