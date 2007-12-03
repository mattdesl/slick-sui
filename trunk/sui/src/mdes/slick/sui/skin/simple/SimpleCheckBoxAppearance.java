/*
 * SimpleCheckBoxAppearance.java
 *
 * Created on November 7, 2007, 8:58 PM
 */

package mdes.slick.sui.skin.simple;

import mdes.slick.sui.skin.*;
import mdes.slick.sui.event.*;
import mdes.slick.sui.*;
import mdes.slick.sui.skin.SuiSkin;
import mdes.slick.sui.theme.*;
import org.newdawn.slick.*;
import org.newdawn.slick.fills.*;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author davedes
 */
public class SimpleCheckBoxAppearance extends SimpleButtonAppearance {
    
    private Image defaultImage = null;
    private boolean drawOutline = true;
        
    public void install(SuiComponent comp, SuiSkin skin, SuiTheme theme) {
        super.install(comp, skin, theme);
        this.defaultImage = SkinManager.getImage("CheckBox.image");
    }
    //TODO: disabled colours
    
    protected RoundedRectangle createRoundedBounds() {
        return new RoundedRectangle(0f,0f,0f,0f,5f,25);
    }
    
    protected RoundedRectangle createRoundedBoxBounds() {
        return new RoundedRectangle(0f,0f,0f,0f,3f,50);
    }
    
    protected ButtonData createData(SuiComponent comp) {
        CheckBoxButtonData data = new CheckBoxButtonData();
        RoundedRectangle rect = createRoundedBounds();
        data.roundBounds = rect;
        RoundedRectangle rect2 = createRoundedBoxBounds();
        data.roundBoxBounds = rect2;
        return data;
    }
    
    protected ButtonData getButtonData(SuiComponent comp) {
        Object obj = comp.getSkinData();
        CheckBoxButtonData ret = null;
        if (obj!=null && obj instanceof CheckBoxButtonData) {
            ret = (CheckBoxButtonData)obj;
        } else {
            ret = (CheckBoxButtonData)createData(comp);
            comp.setSkinData(ret);
        }
        return ret;
    }
    
    public void render(GUIContext ctx, Graphics g, SuiComponent comp, SuiSkin skin, SuiTheme theme) {
        SuiCheckBox check = (SuiCheckBox)comp;
        Rectangle cachedRect = null;
        boolean roundRectEnabled = SimpleSkin.isRoundRectanglesEnabled();
        CheckBoxButtonData data = (CheckBoxButtonData)getButtonData(check);
        
        //make sure we are showing outline
        //also, the outline will only render if we aren't rendering a background
        if (isShowOutline() && (!check.isOpaque() || check.getBackground()==null)) {
            //get the cached rectangle from the component bounds
            cachedRect = check.getAbsoluteBounds();
            Rectangle bounds = cachedRect;
            
            if (roundRectEnabled) {
                if (data!=null && data.roundBounds!=null) {
                    bounds = data.roundBounds;
                    bounds.setX(cachedRect.getX());
                    bounds.setY(cachedRect.getY());
                    bounds.setWidth(cachedRect.getWidth());
                    bounds.setHeight(cachedRect.getHeight());
                }
            }
            
            Color oldCol = g.getColor();
            boolean oldAA = g.isAntiAlias();
            Color back;

            if (check.getState()!=SuiButton.UP) //hover
                back = theme.getPrimary1();
            else //still
                back = theme.getPrimary3();

            g.setColor(back);
            g.setAntiAlias(true);
            g.fill(bounds);
            
            g.setAntiAlias(oldAA);
            g.setColor(oldCol);
        }
        
        //renders base
        RenderUtil.renderComponentBase(g, check);
                
        //renders text/image
        RenderUtil.renderCheckBoxBase(g, check);
        
        //get cached bounds from the "check" box button area
        Rectangle cachedBox = check.getAbsoluteBoxBounds();
        Rectangle btnRect = cachedBox;
        
        if (roundRectEnabled && data!=null && data.roundBoxBounds!=null) {
            Rectangle bounds = data.roundBoxBounds;
            bounds.setLocation(cachedBox.getX(), cachedBox.getY());
            bounds.setWidth(cachedBox.getWidth());
            bounds.setHeight(cachedBox.getHeight());
            btnRect = bounds;
        }
        
        //renders the actual button state for the small box area, using rounded edges
        SimpleButtonAppearance.renderButtonState(g, theme, check, btnRect);
        
        Image def = getDefaultImage();
        if (def!=null && check.isSelected()) {
            float x = btnRect.getX() + (btnRect.getWidth()/2f - def.getWidth()/2f);
            float y = btnRect.getY() + (btnRect.getHeight()/2f - def.getHeight()/2f);
            g.drawImage(def, (int)x, (int)y, check.getImageFilter());
        }
    }

    public Image getDefaultImage() {
        return defaultImage;
    }

    public boolean isShowOutline() {
        return drawOutline;
    }

    public void setShowOutline(boolean drawOutline) {
        this.drawOutline = drawOutline;
    }
    
    public static class CheckBoxButtonData extends ButtonData {
        RoundedRectangle roundBoxBounds;
    }
}
