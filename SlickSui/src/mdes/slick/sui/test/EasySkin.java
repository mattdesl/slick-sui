/*
 * EasySkin.java
 *
 * Created on June 24, 2007, 4:19 PM
 */

package mdes.slick.sui.test;

import mdes.slick.sui.*;
import mdes.slick.sui.border.*;
import mdes.slick.sui.skin.*;
import org.newdawn.slick.fills.*;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.util.*;
import org.lwjgl.opengl.*;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author davedes
 */
public class EasySkin implements SuiSkin {
    
    //set up a "dummy" fill in a static context
    //this will be reused for all custom UIs in EasySkin (namely the EasyButtonUI)
    private static GradientFill grad = new GradientFill(0,0,Color.white,0,0,Color.white);

    private Image checkImg = null;
    private Font font = null;
    private SuiBorder buttonBorder = null;
    
    public void install() throws SlickException {
        //caches the default checkbox image
        if (checkImg == null) {
            try {
                checkImg = new Image("testdata/checkbox.png");
            } catch (Exception e) {
                Log.warn("Can't find checkbox image!");
            }
        }
        
        //caches the font
        if (font == null) {
            try {
                font = new AngelCodeFont("testdata/arial.fnt", "testdata/arial.png");
            } catch (Exception e) {
                Log.warn("Can't find font!");
            }
        }
        
        //caches the border
        if (buttonBorder == null) {
            Color color = new Color(Color.gray);
            buttonBorder = new BorderUIResource.LineBorder(color);
        }
        
        //We place this in the hash map.
        SkinManager.put("font", font);
        SkinManager.put("CheckBox.image", checkImg);
        SkinManager.put("Button.border", buttonBorder);
        
        //we will rely on ClassicSkin's borders, although we could
        //have created our own easily in the same way that we created buttonBorder
        SkinManager.put("Window.border", ClassicSkin.getWindowBorder());
        SkinManager.put("ToolTip.border", ClassicSkin.getToolTipBorder());
    }

    public void uninstall() throws SlickException {
    }
    
    public String getName() {
        return "Easy Example Skin";
    }
    
    public ButtonUI getButtonUI() {
        return new EasyButtonUI();
    }
    
    //////////////////////////////////////////////////
    //// For the following methods we'll use the UI 
    //// delegates from ClassicSkin.
    //////////////////////////////////////////////////
    
    public ContainerUI getContainerUI() {
        return new ClassicSkin.ClassicContainerUI();
    }
    
    public CheckBoxUI getCheckBoxUI() {
        return new ClassicSkin.ClassicCheckBoxUI();
    }
        
    public WindowUI getWindowUI() {
        return new ClassicSkin.ClassicWindowUI();
    }
    
    public ToolTipUI getToolTipUI() {
        return new ClassicSkin.ClassicToolTipUI();
    }
    
    public LabelUI getLabelUI() {
        return new ClassicSkin.ClassicLabelUI();
    }
    
    public TitleBarUI getTitleBarUI() {
        return new ClassicSkin.ClassicTitleBarUI();
    }
    
    
    //////////////////////////////////////////////////
    //// The following inner class is the custom 
    //// ButtonUI delegate for the EasySkin.
    //////////////////////////////////////////////////
    
    public static class EasyButtonUI extends SuiSkin.ButtonUI {
        
        //set up a "dummy" rectangle
        //this will be reused for any components registered with this UI's instance
        private Rectangle rect = new Rectangle(0f,0f,0f,0f);

        public void installUI(SuiContainer c, SuiTheme t) {
            //this convenience method retrieves the border based
            //on the specified key.
            SkinManager.installBorder(c, "Button.border");
            
            SkinManager.installFont(c, "font");
        }
        
        //called to render the UI for the specified component
        public void renderUI(Graphics g, SuiTheme t, SuiContainer c) {
            SuiButton btn = (SuiButton)c;
            int state = btn.getState();
            
            //match the rectangle to the given component
            rect.setX(c.getAbsoluteX());
            rect.setY(c.getAbsoluteY());
            rect.setWidth(c.getWidth());
            rect.setHeight(c.getHeight());

            //set up our gradient based on size and theme
            if (state == SuiButton.ROLLOVER) {
                grad.setStartColor(t.getPrimary2());
                grad.setEndColor(t.getSecondary1());
            } else {
                grad.setStartColor(t.getSecondary1());
                grad.setEndColor(t.getPrimary2());
            }
            
            //horizontal linear gradient
            float midpoint = c.getWidth()/2.0f;
            grad.setStart(-midpoint, 0);
            grad.setEnd(midpoint, 0);

            //render
            g.fill(rect, grad);
        }
    }
}

