/*
 * SimpleSkin.java
 *
 * Created on June 18, 2007, 12:40 PM
 */

package mdes.slick.sui.skin;

import mdes.slick.sui.*;
import mdes.slick.sui.border.LineBorder;
import org.newdawn.slick.fills.*;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import org.lwjgl.opengl.*;
import org.newdawn.slick.opengl.SlickCallable;
import org.newdawn.slick.gui.*;

/**
 *
 * @author davedes
 */
public class SimpleSkin implements SuiSkin {
    
    private static GradientFill grad = new GradientFill(0f,0f,Color.white,0f,0f,Color.white);
    private static SimpleWindowBorder windowBorder = new SimpleWindowBorder();
    private static float radius = 3f;
    private static LineBorder toolTipBorder = new LineBorder();
    
    /** Creates a new instance of SimpleSkin. */
    public SimpleSkin() {
    }
    
    private static final Color TRANSPARENT_COLOR = new Color(1f,1f,1f,0f);
        
    private Image checkboxImage;
    private Font font;
    
    public void install() throws SlickException {
                ///////////////////
                // CACHE OBJECTS //
                ///////////////////
        
        //try loading
        //ResourceLoader will spit out a log message if there are problems
        
        //images
        if (checkboxImage==null)
            checkboxImage = tryImage("res/images/simple_checkbox.png");
        
        //fonts
        if (font==null)
            font = tryFont("res/fonts/verdana.fnt", "res/fonts/verdana.png");
        
        //place our skin's defaults into the skin manager
        SkinManager.put("CheckBox.image", checkboxImage);
        SkinManager.put("Window.border", windowBorder);
        SkinManager.put("ToolTip.border", toolTipBorder);
        SkinManager.put("font", font);
    }
    
    private Image tryImage(String s) {
        try { return new Image(s); }
        catch (Exception e) { return null; }
    }
    
    private Font tryFont(String s1, String s2) {
        try { return new FontUIResource.AngelCodeFont(s1, s2); }
        catch (Exception e) { return null; }
    }
    
    public void uninstall() throws SlickException {
    }
    
    public String getName() {
        return "Simple Skin";
    }
    
    public SuiSkin.ButtonUI getButtonUI() { 
        return new SimpleButtonUI(); 
    }
    
    public SuiSkin.CheckBoxUI getCheckBoxUI() { 
        return new SimpleCheckBoxUI(); 
    }
    
    public SuiSkin.WindowUI getWindowUI() { 
        return new SimpleWindowUI(); 
    }
    
    public SuiSkin.ContainerUI getContainerUI() { 
        return new SimpleContainerUI(); 
    }
    
    public SuiSkin.ToolTipUI getToolTipUI() { 
        return new SimpleToolTipUI(); 
    }
    
    public SuiSkin.LabelUI getLabelUI() { 
        return new SimpleLabelUI(); 
    }
    
    public SuiSkin.TitleBarUI getTitleBarUI() {
        return new SimpleTitleBarUI();
    }
    
    private static void renderButtonState(Graphics g, SuiTheme theme, SuiButton btn, Rectangle rect) {        
        float x1=btn.getAbsoluteX(), y1=btn.getAbsoluteY();
        int width=btn.getWidth(), height=btn.getHeight();
        
        rect.setX(x1);
        rect.setY(y1);
        rect.setWidth(width);
        rect.setHeight(height);
        
        renderButtonState(g, theme, btn, x1, y1, width, height, rect, false);
    }
    
    private static void renderButtonState(Graphics g, SuiTheme theme, SuiButton btn, RoundedRectangle rect) {        
        float x1=btn.getAbsoluteX(), y1=btn.getAbsoluteY();
        int width=btn.getWidth(), height=btn.getHeight();
        
        rect.setX(x1);
        rect.setY(y1);
        rect.setWidth(width);
        rect.setHeight(height);
        
        renderButtonState(g, theme, btn, x1, y1, width, height, rect, true);
    }
    
    private static void renderButtonState(Graphics g, SuiTheme theme, SuiButton btn,
                                float x1, float y1, float width, float height, Shape s, boolean aa) {
        int state = btn.getState();
        
        Color lightTop, lightBot, borderLight, borderDark, base;
        
        borderLight = theme.getPrimaryBorder1();
        borderDark = theme.getPrimaryBorder2();
        
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

        boolean oldAA = g.isAntiAlias();
        
        float mid = height/2.0f;
        
        g.setAntiAlias(false);
        
        grad.setStartColor(lightTop);
        grad.setEndColor(base);
        grad.setStart(0, -mid/1.5f);
        grad.setEnd(0, mid/4);
        g.fill(s, grad);
        
        grad.setStartColor(TRANSPARENT_COLOR);
        grad.setEndColor(lightBot);
        grad.setStart(0, 0);
        grad.setEnd(0, mid*2);
        g.fill(s, grad);
        
        if (!btn.isEnabled()) {
            g.setColor(theme.getDisabledMask());
            g.fill(s);
        }
        
        g.setAntiAlias(aa);
        
        grad.setStartColor(borderLight);
        grad.setEndColor(borderDark);
        grad.setStart(0, -mid);
        grad.setEnd(0, mid); 
        g.draw(s, grad);
        
        if (!oldAA)
            g.setAntiAlias(oldAA);
    }
    
    private static class SimpleWindowBorder implements BorderUIResource {
        
        public static SuiTheme theme;
        
        public SimpleWindowBorder(SuiTheme theme) {
            super();
            this.theme = theme;
        }
        
        public SimpleWindowBorder() {
            this(null);
        }
        
        public void updateBorder(GUIContext c, int delta, SuiContainer comp) {
        }
        
        public void renderBorder(GUIContext ctx, Graphics g, SuiContainer c) {
            if (theme==null)
                return;
           
            //border colours
            Color light = theme.getSecondaryBorder1();
            Color dark = theme.getSecondaryBorder1();
            
            //window bounds
            float x1=c.getAbsoluteX(), y1=c.getAbsoluteY();
            float width=c.getWidth(), height=c.getHeight();
                      
            SuiWindow win = (SuiWindow)c;
            SuiWindow.SuiTitleBar bar = win.getTitleBar();
            
            g.setColor(light);
            
            if (bar!=null) {            
                //draw the line below title bar
                g.drawLine(x1, y1+bar.getHeight(), x1+width, y1+bar.getHeight());
            }
            
            //now draw the window's border
            
            //draw the highlighted lines
            g.drawLine(x1,y1, x1+width,y1);
            g.drawLine(x1,y1, x1,y1+height);

            //draw the shadowed lines
            //TODO: fix with rectangle width - 1
            g.setColor(dark);
            g.drawLine(x1+width,y1, x1+width-1,y1+height);
            g.drawLine(x1,y1+height, x1+width,y1+height);
        }
    }
        
    public static class SimpleContainerUI extends ContainerUI {
        public void installUI(SuiContainer c, SuiTheme t) {
            SkinManager.installColors(c, Sui.getTheme().getBackground(), Sui.getTheme().getForeground());
            Font f = SkinManager.getFont("font");
            if (f!=null)
                c.setFont(f);
        }
    }
    
    public static class SimpleCheckBoxUI extends CheckBoxUI {
        private Image img;
        private RoundedRectangle rect = new RoundedRectangle(0f,0f,0f,0f,radius,8);
        
        public void installUI(SuiContainer c, SuiTheme t) {
            img = SkinManager.getImage("CheckBox.image");
            SuiCheckBox btn = (SuiCheckBox)c;
            
            btn.setBoxWidth(15);
            btn.setBoxHeight(15);
            
            SkinManager.installColors(c, Sui.getTheme().getBackground(), Sui.getTheme().getForeground());
            Font f = SkinManager.getFont("font");
            if (f!=null)
                c.setFont(f);
        }
                
        public void renderUI(Graphics g, SuiTheme theme, SuiContainer c) {
            SuiCheckBox btn = (SuiCheckBox)c;
            
            float x1=btn.getAbsoluteX(), y1=btn.getAbsoluteY();
            float width=btn.getBoxWidth(), height=btn.getBoxHeight();
            rect.setX(x1);
            rect.setY(y1);
            rect.setWidth(width);
            rect.setHeight(height);

            renderButtonState(g, theme, btn, x1, y1, width, height, rect, true);
        }
        
        public Image getDefaultImage() {
            return img;
        }
    }
    
    public static class SimpleWindowUI extends WindowUI {
        
        public void installUI(SuiContainer c, SuiTheme t) {
            SuiWindow w = (SuiWindow)c;
            w.setBackground(t.getBackground());
            
            windowBorder.theme = t;
            SkinManager.installBorder(c, "Window.border");
            
            SkinManager.installColors(c, Sui.getTheme().getBackground(), Sui.getTheme().getForeground());
            Font f = SkinManager.getFont("font");
            if (f!=null)
                c.setFont(f);
        }
    }
    
    public static class SimpleButtonUI extends ButtonUI {
        
        private RoundedRectangle rect = new RoundedRectangle(0f,0f,0f,0f,radius,8);
        
        public void installUI(SuiContainer c, SuiTheme t) {
            SkinManager.installColors(c, Sui.getTheme().getBackground(), Sui.getTheme().getForeground());
            Font f = SkinManager.getFont("font");
            if (f!=null)
                c.setFont(f);
        }
        
        public void renderUI(Graphics g, SuiTheme theme, SuiContainer c) {
            SuiButton btn = (SuiButton)c;
            renderButtonState(g, theme, btn, rect);
        }
    }
    
    public static class SimpleTitleBarUI extends TitleBarUI {
        
        private Rectangle rect = new Rectangle(0f,0f,0f,0f);
        private Rectangle closeRect = new Rectangle(0f,0f,0f,0f);
        
        public void installUI(SuiContainer c, SuiTheme t) {
            SkinManager.installColors(c, Sui.getTheme().getBackground(), Sui.getTheme().getForeground());
            Font f = SkinManager.getFont("font");
            if (f!=null)
                c.setFont(f);
        }

        public void renderUI(Graphics g, SuiTheme theme, SuiContainer c) {
            SuiWindow.SuiTitleBar t = (SuiWindow.SuiTitleBar)c;
            
            float x1=t.getAbsoluteX(), y1=t.getAbsoluteY();
            int width=t.getWidth(), height=t.getHeight();
            
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
                        
            rect.setX(x1);
            rect.setY(y1);
            rect.setWidth(width);
            rect.setHeight(height);
            
            grad.setStartColor(start);
            grad.setEndColor(end);
            grad.setStart(-mid, 0);
            grad.setEnd(mid, 0);
            g.fill(rect, grad);
            
            SuiButton cb = t.getCloseButton();
            if (cb!=null && cb.getParent()==t) {
                renderButtonState(g, theme, cb, closeRect);
            }
        }
    } 
    
    public static class SimpleToolTipUI extends ToolTipUI {
        
        public void installUI(SuiContainer c, SuiTheme t) {
            SkinManager.installColors(c, Sui.getTheme().getBackground(), Sui.getTheme().getForeground());
            Font f = SkinManager.getFont("font");
            if (f!=null)
                c.setFont(f);
            
            toolTipBorder.setColor(c.getForeground());
            SkinManager.installBorder(c, "ToolTip.border");
        }

        public void renderUI(Graphics g, SuiTheme theme, SuiContainer container) {
            SuiDisplay.SuiToolTip c = (SuiDisplay.SuiToolTip)container;
            
            float x1=c.getAbsoluteX(), y1=c.getAbsoluteY();
            int width=c.getWidth(), height=c.getHeight();
            
            //get colours
            Color bg = c.getBackground();
            Color fg = c.getForeground();
            
            //render component
            Color old = g.getColor();
            g.setColor(bg);
            g.fillRect(x1, y1, width, height);
        }
    }
    
    public static class SimpleLabelUI extends LabelUI {
        public void installUI(SuiContainer c, SuiTheme t) {
            SkinManager.installColors(c, Sui.getTheme().getBackground(), Sui.getTheme().getForeground());
            
            Font f = SkinManager.getFont("font");
            if (f!=null)
                c.setFont(f);
        }
    }
}