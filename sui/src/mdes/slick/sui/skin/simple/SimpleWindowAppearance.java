/*
 * SimpleWindowAppearance.java
 *
 * Created on November 7, 2007, 8:58 PM
 */

package mdes.slick.sui.skin.simple;

import mdes.slick.sui.Sui;
import mdes.slick.sui.SuiButton;
import mdes.slick.sui.SuiComponent;
import mdes.slick.sui.SuiLabel;
import mdes.slick.sui.SuiTheme;
import mdes.slick.sui.SuiWindow;
import mdes.slick.sui.skin.ComponentAppearance;
import mdes.slick.sui.skin.SkinUtil;
import mdes.slick.sui.SuiSkin;
import mdes.slick.sui.skin.WindowAppearance;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.opengl.SlickCallable;

/**
 *
 * @author davedes
 */
public class SimpleWindowAppearance extends SimpleContainerAppearance implements WindowAppearance {
        
    private static GradientFill grad = new GradientFill(0f,0f,Color.white,0f,0f,Color.white);
    
    private ComponentAppearance resizerAppearance = new ResizerAppearance();
    
    public void render(GUIContext ctx, Graphics g, SuiComponent comp, SuiSkin skin, SuiTheme theme) {
        Color old = g.getColor();
        
        //borders
        if (comp.isBorderRendered()) {
            SuiWindow win = (SuiWindow)comp;
            Color light = theme.getSecondaryBorder1();
            Color dark = theme.getSecondaryBorder1();

            Rectangle rect = win.getAbsoluteBounds();
            //TODO: this is a hack, fix it
            //HACK: fix window title bar (removed) hack
            if (!win.getTitleBar().isVisible() || !win.containsChild(win.getTitleBar())) {
                float h = win.getTitleBar().getHeight();
                rect.setY(rect.getY()+h-1);
                rect.setHeight(rect.getHeight()-h+1);
            }

            float mid = rect.getWidth()/2f;

            grad.setStartColor(light);
            grad.setEndColor(dark);
            grad.setStart(-mid, 0);
            grad.setEnd(mid, 0);
            g.draw(rect, grad);
        }
    }

    public ComponentAppearance getCloseButtonAppearance(SuiButton closeButton) {
        return new CloseButtonAppearance(closeButton);
    }

    public ComponentAppearance getTitleBarAppearance(SuiWindow.TitleBar titleBar) {
        return new TitleBarAppearance(titleBar);
    }

    public ComponentAppearance getResizerAppearance(SuiWindow.Resizer resizer) {
        return resizerAppearance;
    }
    
    protected class CloseButtonAppearance extends SimpleButtonAppearance {
        
        public CloseButtonAppearance(SuiButton button) {
            super(button);
        }
        
        protected RoundedRectangle createRoundedBounds() {
            return new RoundedRectangle(0f,0f,0f,0f,3f,50);
        }

        public void install(SuiComponent comp, SuiSkin skin, SuiTheme theme) {
            super.install(comp, skin, theme);
            SuiButton btn = (SuiButton)comp;
            
            if (skin instanceof SimpleSkin) {
                Image img = ((SimpleSkin)skin).getCloseButtonImage();
                if (SkinUtil.installImage(btn, img)) {
                    btn.pack();
                }
            }
        }
    }
    
    protected class ResizerAppearance extends SimpleLabelAppearance {
            
        public void render(GUIContext ctx, Graphics g, SuiComponent comp, SuiSkin skin, SuiTheme theme) {
            SuiWindow win = ((SuiWindow.Resizer)comp).getWindow();
            if (!win.isResizable())
                return;

            if (((SuiLabel)comp).getImage()==null) {
                SlickCallable.enterSafeBlock();
                Color t = Sui.getTheme().getSecondaryBorder1();

                //bind texture & color before entering gl
                t.bind();

                float x = comp.getAbsoluteX()-2 , y = comp.getAbsoluteY()-2;
                float w = comp.getWidth(), h = comp.getHeight();

                //begin drawing the triangle
                GL11.glBegin(GL11.GL_TRIANGLES);
                    GL11.glVertex3f(x+w, y, 0);
                    GL11.glVertex3f(x+w, y+h, 0);
                    GL11.glVertex3f(x, y+h, 0);
                GL11.glEnd();
                SlickCallable.leaveSafeBlock();
            } else {
                super.render(ctx, g, comp, skin, theme);
            }
        }
    }
    
    protected class TitleBarAppearance extends SimpleLabelAppearance {

        private GradientFill grad = new GradientFill(0f,0f,Color.white,0f,0f,Color.white);

        private SuiWindow.TitleBar bar;
        
        public TitleBarAppearance(SuiWindow.TitleBar bar) {
            this.bar = bar;
        }
        
        public void render(GUIContext ctx, Graphics g, SuiComponent comp, SuiSkin skin, SuiTheme theme) {
            Rectangle rect = comp.getAbsoluteBounds();

            Color old = g.getColor();
            SuiWindow.TitleBar t = (SuiWindow.TitleBar)comp;
            
            float x1=t.getAbsoluteX(), y1=t.getAbsoluteY();
            float width=t.getWidth(), height=t.getHeight();

            //TODO: fix rectangle + 1

            float mid = width/2.0f;

            Color start, end;

            boolean active = ((SuiWindow)t.getParent()).isActive();

            if (active) {
                start = theme.getActiveTitleBar1();
                end = theme.getActiveTitleBar2();
            } else {
                start = theme.getTitleBar1();
                end = theme.getTitleBar2();
            }

            grad.setStartColor(start);
            grad.setEndColor(end);
            grad.setStart(-mid, 0);
            grad.setEnd(mid, 0);
            g.fill(rect, grad);

            if (t.isBorderRendered()) {
                //borders
                Color light = theme.getSecondaryBorder1();
                Color dark = theme.getSecondaryBorder1();

                grad.setStartColor(light);
                grad.setEndColor(dark);
                grad.setStart(-mid, 0);
                grad.setEnd(mid, 0);
                g.draw(rect, grad);
            }
            
            //g.setColor(old);
            
            SkinUtil.renderLabelBase(g, t);
        }
    }
}
