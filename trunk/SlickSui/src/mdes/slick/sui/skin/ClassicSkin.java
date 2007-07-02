/*
 * ClassicSkin.java
 *
 * Created on June 4, 2007, 5:46 PM
 */

package mdes.slick.sui.skin;

import mdes.slick.sui.*;
import mdes.slick.sui.border.*;
import org.newdawn.slick.fills.*;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import org.lwjgl.opengl.*;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.opengl.SlickCallable;

/**
 * 
 * @author davedes
 */
public class ClassicSkin implements SuiSkin {
    
    private static final Color TRANSPARENT_COLOR = new Color(0f,0f,0f,0f);
    
    private static GradientFill grad = new GradientFill(0f,0f,Color.white,0f,0f,Color.white);
    private static ClassicWindowBorder windowBorder = new ClassicWindowBorder();
    private static LineBorder toolTipBorder = new LineBorder();
    
    private Image checkboxImage;
    private Color winCol, bg, fg;
    private Font font;
    
    public void install() throws SlickException {
                ///////////////////
                // CACHE OBJECTS //
                ///////////////////
        
        //images
        if (checkboxImage==null)
            checkboxImage = tryImage("res/images/classic_checkbox.png");
        
        //fonts
        if (font==null)
            font = Sui.getDefaultFont(); //TODO: fonts       
        
        //place our skin's defaults into the skin manager
        SkinManager.put("CheckBox.image", checkboxImage);
        SkinManager.put("Window.border", windowBorder);
        SkinManager.put("ToolTip.border", toolTipBorder);
        SkinManager.put("font", font);
    }
    
    public static SuiBorder getWindowBorder() {
        return windowBorder;
    }
    
    public static SuiBorder getToolTipBorder() {
        return toolTipBorder;
    }
    
    private Image tryImage(String s) {
        //ResourceLoader will spit out a log message if there are problems
        try { return new Image(s); }
        catch (Exception e) { return null; }
    }

    public void uninstall() throws SlickException {
    }
    
    public String getName() {
        return "Classic Skin";
    }
    
    public SuiSkin.ButtonUI getButtonUI() { 
        return new ClassicButtonUI(); 
    }
    
    public SuiSkin.CheckBoxUI getCheckBoxUI() { 
        return new ClassicCheckBoxUI(); 
    }
    
    public SuiSkin.WindowUI getWindowUI() { 
        return new ClassicWindowUI(); 
    }
    
    public SuiSkin.ContainerUI getContainerUI() { 
        return new ClassicContainerUI(); 
    }
    
    public SuiSkin.ToolTipUI getToolTipUI() { 
        return new ClassicToolTipUI(); 
    }
    
    public SuiSkin.LabelUI getLabelUI() { 
        return new ClassicLabelUI(); 
    }
    
    public SuiSkin.TitleBarUI getTitleBarUI() {
        return new ClassicTitleBarUI();
    }
    
    private static void renderButtonState(Graphics g, SuiTheme theme, SuiButton btn, Rectangle rect) {        
        float x1=btn.getAbsoluteX(), y1=btn.getAbsoluteY();
        int width=btn.getWidth(), height=btn.getHeight();
        
        rect.setX(x1);
        rect.setY(y1);
        rect.setWidth(width);
        rect.setHeight(height);
        
        renderButtonState(g, theme, btn, x1, y1, width, height, rect);
    }
    
    private static void renderButtonState(Graphics g, SuiTheme theme, SuiButton btn,
                                float x1, float y1, float width, float height, Shape s) {
        int state = btn.getState();
        
        Color start, end, border;
        
        switch (state) {
            default:
            case SuiButton.UP:
                start = theme.getPrimary1();
                end = theme.getPrimary2();
                border = theme.getPrimaryBorder1();
                break;
            case SuiButton.DOWN:
                start = theme.getPrimary3();
                end = theme.getPrimary2();
                border = theme.getPrimaryBorder2();
                break;
            case SuiButton.ROLLOVER:
                start = theme.getSecondary1();
                end = theme.getSecondary2();
                border = theme.getPrimaryBorder2();
                break;
        }

        boolean oldAA = g.isAntiAlias();
        
        float mid = height/2.0f;
        
        g.setAntiAlias(false);
        
        grad.setStartColor(start);
        grad.setEndColor(end);
        grad.setStart(0, -mid);
        grad.setEnd(0, mid);
        g.fill(s, grad);
        
        if (!btn.isEnabled()) {
            g.setColor(theme.getDisabledMask());
            g.fill(s);
        }
        
        g.setColor(border);
        g.draw(s);
        
        g.setAntiAlias(oldAA);
    }
    
    
    private static class ClassicWindowBorder implements BorderUIResource {
        public static SuiTheme theme;
        
        public ClassicWindowBorder(SuiTheme theme) {
            super();
            this.theme = theme;
        }
        
        public ClassicWindowBorder() {
            this(null);
        }
        
        public void updateBorder( GUIContext c, int delta, SuiContainer comp) {
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
            
            //now we draw the window border
            g.setColor(dark);
            g.drawRect(x1,y1,width-1,height);
        }
    }
        
    public static class ClassicContainerUI extends ContainerUI {
        public void installUI(SuiContainer c, SuiTheme t) {
            SkinManager.installColors(c, Sui.getTheme().getBackground(), Sui.getTheme().getForeground());
            SkinManager.installFont(c, "font");
        }
    }
    
    public static class ClassicCheckBoxUI extends CheckBoxUI {
        private Image img;
        private Rectangle rect = new Rectangle(0f,0f,0f,0f);
        
        public void installUI(SuiContainer c, SuiTheme t) {
            img = SkinManager.getImage("CheckBox.image");
            SuiCheckBox btn = (SuiCheckBox)c;
            
            btn.setBoxWidth(13);
            btn.setBoxHeight(13);
            
            SkinManager.installColors(c, Sui.getTheme().getBackground(), Sui.getTheme().getForeground());
            SkinManager.installFont(c, "font");
        }
        
        public void renderUI(Graphics g, SuiTheme theme, SuiContainer c) {
            SuiCheckBox btn = (SuiCheckBox)c;
            
            float x1=btn.getAbsoluteX(), y1=btn.getAbsoluteY();
            float width=btn.getBoxWidth(), height=btn.getBoxHeight();
            rect.setX(x1);
            rect.setY(y1);
            rect.setWidth(width);
            rect.setHeight(height);

            renderButtonState(g, theme, btn, x1, y1, width, height, rect);
        }
        
        public Image getDefaultImage() {
            return img;
        }
    }
    
    public static class ClassicWindowUI extends WindowUI {
        
        public void installUI(SuiContainer c, SuiTheme t) {
            SuiWindow w = (SuiWindow)c;
            w.setBackground(t.getBackground());
            
            windowBorder.theme = t;
            SkinManager.installBorder(c, "Window.border");
            
            SkinManager.installColors(c, Sui.getTheme().getBackground(), Sui.getTheme().getForeground());
            SkinManager.installFont(c, "font");
        }
    }
    
    public static class ClassicButtonUI extends ButtonUI {

        private Rectangle rect = new Rectangle(0f,0f,0f,0f);
        
        public void installUI(SuiContainer c, SuiTheme t) {
            SkinManager.installColors(c, Sui.getTheme().getBackground(), Sui.getTheme().getForeground());
            SkinManager.installFont(c, "font");
        }

        public void renderUI(Graphics g, SuiTheme theme, SuiContainer c) {
            SuiButton btn = (SuiButton)c;
            renderButtonState(g, theme, btn, rect);
        }
    }
    
    public static class ClassicTitleBarUI extends TitleBarUI {
        
        private Rectangle rect = new Rectangle(0f,0f,0f,0f);
        private Rectangle closeRect = new Rectangle(0f,0f,0f,0f);
        
        public void installUI(SuiContainer c, SuiTheme t) {
            SkinManager.installColors(c, Sui.getTheme().getBackground(), Sui.getTheme().getForeground());
            SkinManager.installFont(c, "font");
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
            grad.setStart(0, -mid);
            grad.setEnd(0, mid);
            g.fill(rect, grad);
            
            SuiButton cb = t.getCloseButton();
            if (cb!=null && cb.getParent()==t && cb.isVisible()) {
                renderButtonState(g, theme, cb, closeRect);
            }
        }
    }
    
    public static class ClassicToolTipUI extends ToolTipUI {
        public void installUI(SuiContainer c, SuiTheme t) {
            SkinManager.installColors(c, Sui.getTheme().getBackground(), Sui.getTheme().getForeground());
            SkinManager.installFont(c, "font");
            
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
            
            g.setColor(old);
        }
    }
    
    public static class ClassicLabelUI extends LabelUI {
        public void installUI(SuiContainer c, SuiTheme t) {
            SkinManager.installColors(c, Sui.getTheme().getBackground(), Sui.getTheme().getForeground());
            SkinManager.installFont(c, "font");
        }
    }
}


/*
    private final GradientFill gradient = new GradientFill(0,0,DEFAULT_COLOR,0,0,DEFAULT_COLOR);
    private final Rectangle rect = new Rectangle(0f,0f,0f,0f);
    
    public void renderButton(SuiButton btn) {
        int state = btn.getState();
        
        Color start, end;
        
        SuiTheme theme = Sui.getTheme();
        switch (state) {
            default:
            case SuiButton.UP:
                start = theme.getPrimary1();
                end = theme.getPrimary2();
                break;
            case SuiButton.DOWN:
                start = theme.getSecondary3();
                end = theme.getPrimary2();
                break;
            case SuiButton.ROLLOVER:
                start = theme.getPrimary3();
                end = theme.getPrimary2();
                break;
        }
        
        gradient(btn, start, end);
    }
    
    public void renderButtonBorder(SuiButton c) {
        Color col = Sui.getTheme().getPrimary2();
        float x1=c.getAbsoluteX(), y1=c.getAbsoluteY();
        int width=c.getWidth(), height=c.getHeight();
        
        Color old = graphics.getColor();
        graphics.setColor(col);
        graphics.drawRect(x1, y1, width, height);
        graphics.setColor(old);
    }
    
    
    private void gradient(SuiContainer c, Color start, Color end) {
        float x1=c.getAbsoluteX(), y1=c.getAbsoluteY();
        int width=c.getWidth(), height=c.getHeight();
        
        float mid = height/2.0f;
        
        rect.setHeight(height);
        rect.setWidth(width);
        //rect.setX(x1);
        //rect.setY(y1);
        
        gradient.setStartColor(start);
        gradient.setEndColor(end);
        gradient.setStart(0, -mid);
        gradient.setEnd(0, mid);
        
        graphics.fill(rect, gradient);
    }*/
