/*
 * SimpleTextFieldAppearance.java
 *
 * Created on December 2, 2007, 5:03 PM
 */

package mdes.slick.sui.skin.simple;

import mdes.slick.sui.*;
import mdes.slick.sui.SuiSkin;
import mdes.slick.sui.skin.*;
import mdes.slick.sui.SuiTheme;

import org.newdawn.slick.*;
import org.newdawn.slick.fills.*;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.gui.*;

/**
 *
 * @author davedes
 */
public class SimpleTextFieldAppearance extends SimpleComponentAppearance {
        
    protected GradientFill grad;
    protected RoundedRectangle roundBounds;
    protected SuiTextField field;
    
    public SimpleTextFieldAppearance(SuiTextField field) {
        this.field = field;        
        grad = new GradientFill(0f,0f,Color.white,0f,0f,Color.white);
    }
    
    protected void checkComponent(SuiComponent comp) {
        if (comp != this.field) 
            throw new IllegalStateException("SimpleSkin's text field appearance " +
                            "only handles the field passed in its constructor");
    }
    
    public void install(SuiComponent comp, SuiSkin skin, SuiTheme theme) {
        checkComponent(comp);
        super.install(comp, skin, theme);
        comp.setPadding(2, 2, 2, 2);
    }
    
    public void render(GUIContext ctx, Graphics g, SuiComponent comp, SuiSkin skin, SuiTheme theme) {
        checkComponent(comp);
        
        SuiTextField field = (SuiTextField)comp;
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
        float cpos = font.getWidth(value.substring(0, caretPos));
        float tx = 0;
        if (cpos > field.getWidth()) {
            tx = field.getWidth() - cpos - font.getWidth("_");
        }

        g.translate(tx,0);
                
        g.setFont(font);
        g.setClip(bounds);
        g.setColor(field.getForeground());
        g.drawString(value, x+pad.left, y+pad.top);
        
        if (hasFocus) {
            g.drawString("_", x+pad.left+cpos, y+pad.top);
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
