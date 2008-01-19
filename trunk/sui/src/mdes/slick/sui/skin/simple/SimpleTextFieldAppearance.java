/*
 * SimpleTextFieldAppearance.java
 *
 * Created on December 2, 2007, 5:03 PM
 */

package mdes.slick.sui.skin.simple;

import mdes.slick.sui.Component;
import mdes.slick.sui.Padding;
import mdes.slick.sui.Skin;
import mdes.slick.sui.TextField;
import mdes.slick.sui.Theme;
import mdes.slick.sui.skin.SkinUtil;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author davedes
 */
public class SimpleTextFieldAppearance extends SimpleTextComponentAppearance {
        
    protected GradientFill grad;
    protected RoundedRectangle roundBounds;
    protected TextField field;
    
    public SimpleTextFieldAppearance(TextField field) {
        this.field = field;
        grad = new GradientFill(0f,0f,Color.white,0f,0f,Color.white);
    }
    
    protected void checkComponent(Component comp) {
        if (comp != this.field) 
            throw new IllegalStateException("SimpleSkin's text field appearance " +
                            "only handles the field passed in its constructor");
    }
    
    public void install(Component comp, Skin skin, Theme theme) {
        checkComponent(comp);
        super.install(comp, skin, theme);
        comp.setPadding(2, 2, 2, 5);
    }
    
    public void update(GUIContext ctx, int delta, Component comp, Skin skin, Theme theme) {
        checkComponent(comp);
        super.update(ctx, delta, comp, skin, theme);
    }
    
    public void render(GUIContext ctx, Graphics g, Component comp, Skin skin, Theme theme) {
        super.render(ctx, g, comp, skin, theme);
        
        checkComponent(comp);
        
        TextField field = (TextField)comp;
        boolean hasFocus = field.hasFocus();
        
        SkinUtil.renderComponentBase(g, comp);
        
        Color start = theme.getPrimary1();
        Color end = theme.getSecondary1();
                
        Rectangle bounds = field.getAbsoluteBounds();
        float x = bounds.getX();
        float y = bounds.getY();
        float mid = bounds.getHeight()/2f;
        
        grad.setStart(0, -mid);
        grad.setEnd(0, mid);
        grad.setStartColor(start);
        grad.setEndColor(end);
        g.fill(bounds, grad);
        
        Rectangle oldClip = g.getClip();
        
        Font oldFont = g.getFont();
        
        String value = field.getDisplayText();
        Font font = field.getFont();
        int caretPos = field.getCaretPosition();
        Padding pad = field.getPadding();
                
        //use default font
        if (font==null)
            font=g.getFont();
        
        //current pos
        float cpos = pad.left+font.getWidth(value.substring(0, caretPos));
        float tx = 0;
        if (cpos > field.getWidth()-pad.right) {
            tx = field.getWidth() - cpos - pad.right;
        }

        g.translate(tx,0);
                
        g.setFont(font);
        g.setClip(bounds);
        g.setColor(field.getForeground());
        g.drawString(value, x+pad.left, y+pad.top);
        
        if (hasFocus && renderCaret) {
            g.fillRect((int)(x+cpos+1), (int)(y+pad.top), 1, font.getLineHeight()-1);
        }

        g.translate(-tx, 0);
        
        g.setFont(oldFont);
        g.setClip(oldClip);
        
        if (field.isBorderRendered()) {
            g.setColor( hasFocus ? theme.getPrimaryBorder2() : theme.getPrimaryBorder1());
            g.draw(bounds);
        }
    }
}
