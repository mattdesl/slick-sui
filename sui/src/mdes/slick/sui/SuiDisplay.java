/*
 * SuiDisplay.java
 *
 * Created on May 29, 2007, 6:07 PM
 */

package mdes.slick.sui;

import java.util.ArrayList;
import java.util.List;
import mdes.slick.sui.event.*;
import org.newdawn.slick.gui.*;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.util.InputAdapter;

/**
 * This acts as a top-level component which renders various
 * SuiContainers. 
 * 
 * @author davedes
 */
public class SuiDisplay extends SuiContainer {
    
    //TODO: add SuiInputEvent
    //TODO: add getModifiers for input event
    //TODO: use boolean flag for isClipEnabled for efficiency
        
    private Input input;
    private GUIContext context;
    private SlickListener SLICK_LISTENER = new SlickListener();
    private boolean acceptingInput = false;
    //protected SuiToolTip tooltip;
    private boolean globalEvents = true;
    
    static GUIContext cachedContext = null;
    ArrayList activeWindows = new ArrayList();
    private SuiComponent focusOwner = null;
    
    private SuiToolTip toolTip = new SuiToolTip();
    private boolean toolTipEnabled = true;
    
    private Timer tipShowTimer = new Timer(750, new ShowTipAction());
    private Timer tipHideTimer = new Timer(4000, new HideTipAction());
    
    private SuiComponent tipOver;
    private float tipX;
    private float tipY;
        
    /** Creates a new instance of SuiDisplay. */
    public SuiDisplay(GUIContext context) {
        if (context==null)
            throw new IllegalArgumentException("cannot have null context");
        this.cachedContext = context;
        
        this.context = context;
        this.input = context.getInput();
        setSize(context.getWidth(), context.getHeight());
        setLocation(0, 0);
        
        if (Sui.getDefaultFont()==null)
            Sui.setDefaultFont(context.getDefaultFont());
        
        toolTip.setZIndex(SuiComponent.POPUP_LAYER);
        toolTip.setVisible(false);
        toolTip.setOpaque(true);
        toolTip.getPadding().set(2, 3, 2, 3);
        tipShowTimer.setRepeats(false);
        tipHideTimer.setRepeats(false);
        add(toolTip);
                
        input.addPrimaryListener(SLICK_LISTENER);
        
        setFocusable(false);
        setGlassPane(true);
    }
        
    /**
     * Adjusts the size on this display if it has changed from 
     * the context. When a GUIContext (or GameContainer) has 
     * changed its size this method should be called.
     */
    public void reinit() {
        setInput(context.getInput());
        int w = context.getWidth();
        int h = context.getHeight();
        if (w!=getWidth())
            setWidth(w);
        if (h!=getHeight())
            setHeight(h);
    }
    
    public Input getInput() {
        return input;
    }

    public void setInput(Input input) {
        if (input==null)
            throw new IllegalArgumentException("input cannot be null");
        this.input.removeListener(SLICK_LISTENER);
        input.addPrimaryListener(SLICK_LISTENER);
        this.input = input;
    }

    public GUIContext getContext() {
        return context;
    }
    
    public SuiDisplay getDisplay() {
        return this;
    }
    
    /**
     * Used internally to change the focus.
     *
     * @param c the new owner of the focus, or null
     * 			if no component has the focus
     */
    void setFocusOwner(SuiComponent c) {
        //if the container isn't focusable
        if (c!=null && !c.isFocusable())
            return;
        focusOwner = c;
    }
    
    /**
     * Returns the component which currently has the focus.
     * If the current focus owner is not focusable and/or not
     * visible, the focus owner is released and <tt>null</tt> is returned.
     * @return the current focus owner, or <tt>null</tt> if none exists
     * @since b.0.2
     */
    public SuiComponent getFocusOwner() {
        //releases focus if the component is no longer visible,
        //or is no longer focusable
        if (focusOwner==null)
            return null;
        else {
            if (!focusOwner.isFocusable() || !focusOwner.isShowing()) {
                focusOwner.setWindowsActive(false, this);
                focusOwner = null;
            }
            return focusOwner;
        }
    }
            
    public void render(GUIContext c, Graphics g) {
        //Rectangle r = g.getClip();
        if (isShowing() && getWidth()!=0 && getHeight()!=0) {
            acceptingInput = true;
            if (isClipEnabled()) {
                //TODO: remove?
                /*clip.x = getAbsoluteX();
                clip.y = getAbsoluteY();
                clip.width = getWidth();
                clip.height = getHeight();
                
                clip.x = 0;
                clip.y = 0;
                clip.width = c.getWidth();
                clip.height = c.getHeight();   */             
            }
        }
        super.render(c, g);
        //g.setClip(r);
        g.setColor(Color.white);
        //g.drawString("ToolTipShow: "+(100*tipShowTimer.getPercent())+"%", 10, 55);
        //g.drawString("ToolTipShow: "+(100*tipHideTimer.getPercent())+"%", 10, 70);
    }
    
    public void update(GUIContext c, int delta) {
        super.update(c, delta);
        tipShowTimer.update(c, delta);
        tipHideTimer.update(c, delta);
    }
    
    /**
     * Sets up this display with a new context, changing
     * this display's default font, width, height, and 
     */
    public void setContext(GUIContext context) {
        if (context==null)
            throw new IllegalArgumentException("cannot have null context");
        setInput(context.getInput());
        this.context = context;
        cachedContext = context;
    }
        
    private void showToolTip(SuiComponent c, float mouseX, float mouseY) {
        String str = c.getToolTipText();
        if (str!=null&&str.length()!=0) {
            toolTip.setOwner(c);
            toolTip.setText(str);
            toolTip.pack();
            toolTip.setLocation(mouseX, mouseY+20);
            toolTip.setVisible(true);
        }
    }
    
    //TODO: SuiDisplay listeners for global events
    
    public boolean isSendingGlobalEvents() {
        return globalEvents;
    }
    
    public void setSendingGlobalEvents(boolean globalEvents) {
        this.globalEvents = globalEvents;
    }
    
    void clearActiveWindows() {
        for (int i=0; i<activeWindows.size(); i++) {
            ((SuiWindow)activeWindows.get(i)).setActive(false);
        }
        activeWindows.clear();
    }
    
    public SuiComponent getDeepestComponentAt(SuiContainer parent, int x, int y, boolean checkGlassPane) {
        if (!parent.contains(x, y)) {
            return null;
        }
        
        for (int i=parent.getChildCount()-1; i>=0; i--) {
            SuiComponent comp = parent.getChild(i);
            
            if (comp!=null && comp.isShowing()) {
                if (comp.contains(x, y)) {
                    if (checkGlassPane && comp.isGlassPane()) {
                        return parent;
                    }
                    if (comp instanceof SuiToolTip) {
                        SuiComponent owner = ((SuiToolTip)comp).getOwner();
                        if (owner.contains(x, y))
                            return owner;
                    } 
                    
                    if (comp instanceof SuiContainer && 
                            ((SuiContainer)comp).getChildCount()>0)
                        return getDeepestComponentAt((SuiContainer)comp, x, y, checkGlassPane);
                    else
                        return comp;
                }
            }
        }
        return parent;
    }
    
    public SuiComponent getDeepestComponentAt(SuiContainer parent, int x, int y) {
        return getDeepestComponentAt(parent, x, y, false);
    }
    
    private SuiComponent tipOver() {
        return SLICK_LISTENER.tipOver;
    }
    
    private float tipX() {
        return SLICK_LISTENER.tipX;
    }
    
    private float tipY() {
        return SLICK_LISTENER.tipY;
    }
        
    private class ShowTipAction implements SuiActionListener {
        public void actionPerformed(SuiActionEvent e) {
            if (!toolTip.isVisible())
                showToolTip(tipOver(), tipX(), tipY());
            tipHideTimer.restart();
        }
    }
    
    private class HideTipAction implements SuiActionListener {
        public void actionPerformed(SuiActionEvent e) {
            if (toolTip.isVisible())
                toolTip.setVisible(false);
            tipShowTimer.stop();
        }
    }
    
    private class SlickListener extends InputAdapter {
        //the button for dragging
        private int dragb = SuiMouseEvent.NOBUTTON;

        //whether the button is down
        private boolean bdown = false;
        
        private SuiComponent mouseOver = null;
        private SuiComponent tipOver;
        private float tipX;
        private float tipY;
        
        private SuiComponent dragComp = null;
        
        public boolean isAcceptingInput() {
            return isShowing() && acceptingInput;
        }
        
        public void inputEnded() {
            acceptingInput = false;
        }
        
        public boolean inRelativeBounds(int nx, int ny) {
            return nx>=0 && ny>=0 && nx<=getWidth() && ny<=getHeight();
        }
        
        public void mouseMoved(int oldX, int oldY, int newX, int newY) {
            //SuiContainer comp = getDeepestComponentAt(SuiDisplay.this, nx, ny, false);
            if (!isShowing()) {
                return;
            }
            
            //if a button is down check for glass panes
            boolean dragging = dragb != SuiMouseEvent.NOBUTTON;
            SuiComponent comp = getDeepestComponentAt(SuiDisplay.this, newX, newY, true);
            if (comp==null) {
                mouseOver = null;
                return;
            }
              
            //relative positions
            int ox = (int)(oldX-comp.getAbsoluteX());
            int oy = (int)(oldY-comp.getAbsoluteY());
            int nx = (int)(newX-comp.getAbsoluteX());
            int ny = (int)(newY-comp.getAbsoluteY());
            
            if (dragComp!=null&&dragComp!=comp&&dragComp.isEnabled()) {
                dragComp.fireMouseEvent(SuiMouseEvent.MOUSE_DRAGGED, dragb, nx, ny, ox, oy, newX, newY);
                return;
            }
            
            int moveID = dragging ? SuiMouseEvent.MOUSE_DRAGGED : SuiMouseEvent.MOUSE_MOVED;
            if (comp.isEnabled() || moveID==SuiMouseEvent.MOUSE_MOVED)
                comp.fireMouseEvent(moveID, dragb, nx, ny, ox, oy, newX, newY);
            
            //if we should send events to this display
            if (comp!=SuiDisplay.this && globalEvents
                    && (SuiDisplay.this.isEnabled() || moveID==SuiMouseEvent.MOUSE_MOVED) ) {
                fireMouseEvent(moveID, dragb, nx, ny, ox, oy, newX, newY);
            }
            SuiComponent oldComp = mouseOver;
            mouseOver = comp;
            if (oldComp!=mouseOver) {
                if (oldComp!=null) {
                    oldComp.fireMouseEvent(SuiMouseEvent.MOUSE_EXITED, dragb, nx, ny, ox, oy, newX, newY);
                }
                if (mouseOver!=null) 
                    mouseOver.fireMouseEvent(SuiMouseEvent.MOUSE_ENTERED, dragb, nx, ny, ox, oy, newX, newY);
            }
            
            //if the tool tip isn't showing, restart the initial timer since mouse has moved
            //also we can't be dragging
            if (isToolTipEnabled()) {
                tipX = newX;
                tipY = newY;
                
                SuiComponent old = tipOver;
                tipOver = mouseOver;
                
                if (old!=tipOver || dragging)
                    toolTip.setVisible(false);
                if (!toolTip.isVisible() && tipOver.getToolTipText()!=null
                        && tipOver.getToolTipText().length()!=0) {
                    tipShowTimer.restart();
                } else {
                    if (!toolTip.isVisible())
                        tipHideTimer.stop();
                    tipShowTimer.stop();
                }
            }
        }
              
        public void mousePressed(int button, int x, int y) {
            dragb = button;
            if (!isShowing()) {
                return;
            }
            
            SuiComponent comp = getDeepestComponentAt(SuiDisplay.this, x, y, true);
            if (comp==null) {
                bdown = false;
                dragComp = null;
                return;
            }
            
            if (comp.isFocusable()) {
                if (comp.isRequestFocusEnabled())
                    comp.grabFocus();
            } else {
                comp.setWindowsActive(true, SuiDisplay.this);
            }
            //relative positions
            int nx = (int)(x-comp.getAbsoluteX());
            int ny = (int)(y-comp.getAbsoluteY());
            
            dragComp = comp;
            bdown = true;
            if (comp.isEnabled())
                comp.fireMouseEvent(SuiMouseEvent.MOUSE_PRESSED, button, nx, ny, x, y);
            if (comp!=SuiDisplay.this&&globalEvents && SuiDisplay.this.isEnabled())
                fireMouseEvent(SuiMouseEvent.MOUSE_PRESSED, button, nx, ny, x, y);
            
            if (isToolTipEnabled()) {
                //if the timer/tip is running/showing, stop/hide it
                if (tipShowTimer.isRunning())
                    tipShowTimer.stop();
                if (toolTip.isVisible())
                    toolTip.setVisible(false);
            }
        }
        
        public void mouseReleased(int button, int x, int y) {
            dragb = SuiMouseEvent.NOBUTTON;
            bdown = false;
            if (!isShowing()) {
                return;
            }
                          
            if (dragComp!=null) {
                int nx = (int)(x-dragComp.getAbsoluteX());
                int ny = (int)(y-dragComp.getAbsoluteY());
                if (dragComp.isEnabled())
                    dragComp.fireMouseEvent(SuiMouseEvent.MOUSE_RELEASED, button, nx, ny, x, y);
                if (dragComp!=SuiDisplay.this&&globalEvents && SuiDisplay.this.isEnabled())
                    fireMouseEvent(SuiMouseEvent.MOUSE_RELEASED, button, nx, ny, x, y);
                dragComp = null;
                return;
            }
            
            SuiComponent comp = getDeepestComponentAt(SuiDisplay.this, x, y, true);
            if (comp==null) {
                return;
            }
            
            //relative positions
            int nx = (int)(x-comp.getAbsoluteX());
            int ny = (int)(y-comp.getAbsoluteY());
            
            if (comp.isEnabled())
                comp.fireMouseEvent(SuiMouseEvent.MOUSE_RELEASED, button, nx, ny, x, y);
            if (comp!=SuiDisplay.this&&globalEvents && SuiDisplay.this.isEnabled())
                fireMouseEvent(SuiMouseEvent.MOUSE_RELEASED, button, nx, ny, x, y);
        }
        
        public void keyPressed(int key, char c) {
            SuiComponent focused = getFocusOwner();
            if (focused!=null && focused.isEnabled()) {
                focused.fireKeyEvent(SuiKeyEvent.KEY_PRESSED, key, c);
            }
            if (focused!=SuiDisplay.this&&globalEvents && SuiDisplay.this.isEnabled())
                fireKeyEvent(SuiKeyEvent.KEY_PRESSED, key, c);
        }
        
        public void mouseWheelMoved(int change) {
            SuiComponent focused = getFocusOwner();
            if (focused!=null && focused.isEnabled()) {
                focused.fireMouseWheelEvent(change);
            }
            if (focused!=SuiDisplay.this&&globalEvents && SuiDisplay.this.isEnabled())
                fireMouseWheelEvent(change);
        }
        
        public void keyReleased(int key, char c) {
            SuiComponent focused = getFocusOwner();
            if (focused!=null && focused.isEnabled()) {
                focused.fireKeyEvent(SuiKeyEvent.KEY_RELEASED, key, c);
            }
            if (focused!=SuiDisplay.this&&globalEvents && SuiDisplay.this.isEnabled())
                fireKeyEvent(SuiKeyEvent.KEY_RELEASED, key, c);
        }
        
        public void controllerButtonPressed(int controller, int button) {
            SuiComponent focused = getFocusOwner();
            if (focused!=null && focused.isEnabled()) {
                focused.fireControllerEvent(SuiControllerEvent.BUTTON_PRESSED, controller, button);
            }
            if (focused!=SuiDisplay.this&&globalEvents && SuiDisplay.this.isEnabled())
                fireControllerEvent(SuiControllerEvent.BUTTON_PRESSED, controller, button);
        }
        
        public void controllerButtonReleased(int controller, int button) {
            SuiComponent focused = getFocusOwner();
            if (focused!=null && focused.isEnabled()) {
                focused.fireControllerEvent(SuiControllerEvent.BUTTON_RELEASED, controller, button);
            }
            if (focused!=SuiDisplay.this&&globalEvents && SuiDisplay.this.isEnabled())
                fireControllerEvent(SuiControllerEvent.BUTTON_RELEASED, controller, button);
        }
        
        public void controllerDownPressed(int controller) {
            SuiComponent focused = getFocusOwner();
            if (focused!=null && focused.isEnabled()) {
                focused.fireControllerEvent(SuiControllerEvent.BUTTON_PRESSED, controller,
                        SuiControllerEvent.DOWN_BUTTON);
            }
            if (focused!=SuiDisplay.this&&globalEvents && SuiDisplay.this.isEnabled())
                fireControllerEvent(SuiControllerEvent.BUTTON_PRESSED, controller,
                        SuiControllerEvent.DOWN_BUTTON);
        }
        
        public void controllerDownReleased(int controller) {
            SuiComponent focused = getFocusOwner();
            if (focused!=null && focused.isEnabled()) {
                focused.fireControllerEvent(SuiControllerEvent.BUTTON_RELEASED, controller,
                        SuiControllerEvent.DOWN_BUTTON);
            }
            if (focused!=SuiDisplay.this&&globalEvents && SuiDisplay.this.isEnabled())
                fireControllerEvent(SuiControllerEvent.BUTTON_RELEASED, controller,
                        SuiControllerEvent.DOWN_BUTTON);
        }
        
        public void controllerUpPressed(int controller) {
            SuiComponent focused = getFocusOwner();
            if (focused!=null && focused.isEnabled()) {
                focused.fireControllerEvent(SuiControllerEvent.BUTTON_PRESSED, controller,
                        SuiControllerEvent.UP_BUTTON);
            }
            if (focused!=SuiDisplay.this&&globalEvents && SuiDisplay.this.isEnabled())
                fireControllerEvent(SuiControllerEvent.BUTTON_PRESSED, controller,
                        SuiControllerEvent.UP_BUTTON);
        }
        
        public void controllerUpReleased(int controller) {
            SuiComponent focused = getFocusOwner();
            if (focused!=null && focused.isEnabled()) {
                focused.fireControllerEvent(SuiControllerEvent.BUTTON_RELEASED, controller,
                        SuiControllerEvent.UP_BUTTON);
            }
            if (focused!=SuiDisplay.this&&globalEvents && SuiDisplay.this.isEnabled())
                fireControllerEvent(SuiControllerEvent.BUTTON_RELEASED, controller,
                        SuiControllerEvent.UP_BUTTON);
        }
        
        public void controllerLeftPressed(int controller) {
            SuiComponent focused = getFocusOwner();
            if (focused!=null && focused.isEnabled()) {
                focused.fireControllerEvent(SuiControllerEvent.BUTTON_PRESSED, controller,
                        SuiControllerEvent.LEFT_BUTTON);
            }
            if (focused!=SuiDisplay.this&&globalEvents && SuiDisplay.this.isEnabled())
                fireControllerEvent(SuiControllerEvent.BUTTON_PRESSED, controller,
                        SuiControllerEvent.LEFT_BUTTON);
        }
        
        public void controllerLeftReleased(int controller) {
            SuiComponent focused = getFocusOwner();
            if (focused!=null && focused.isEnabled()) {
                focused.fireControllerEvent(SuiControllerEvent.BUTTON_RELEASED, controller,
                        SuiControllerEvent.LEFT_BUTTON);
            }
            if (focused!=SuiDisplay.this&&globalEvents && SuiDisplay.this.isEnabled())
                fireControllerEvent(SuiControllerEvent.BUTTON_RELEASED, controller,
                        SuiControllerEvent.LEFT_BUTTON);
        }
        
        public void controllerRightPressed(int controller) {
            SuiComponent focused = getFocusOwner();
            if (focused!=null && focused.isEnabled()) {
                focused.fireControllerEvent(SuiControllerEvent.BUTTON_PRESSED, controller,
                        SuiControllerEvent.RIGHT_BUTTON);
            }
            if (focused!=SuiDisplay.this&&globalEvents && SuiDisplay.this.isEnabled())
                fireControllerEvent(SuiControllerEvent.BUTTON_PRESSED, controller,
                        SuiControllerEvent.RIGHT_BUTTON);
        }
        
        public void controllerRightReleased(int controller) {
            SuiComponent focused = getFocusOwner();
            if (focused!=null && focused.isEnabled()) {
                focused.fireControllerEvent(SuiControllerEvent.BUTTON_RELEASED, controller,
                        SuiControllerEvent.RIGHT_BUTTON);
            }
            if (focused!=SuiDisplay.this&&globalEvents && SuiDisplay.this.isEnabled())
                fireControllerEvent(SuiControllerEvent.BUTTON_RELEASED, controller,
                        SuiControllerEvent.RIGHT_BUTTON);
        }
    }
    
    /*
    public class SuiToolTip extends SuiPopup {
        
        private SuiLabel label;
        private Color bgFilter = new Color(1f,1f,1f);
        private Color fgFilter = new Color(0f,0f,0f);
        private boolean fadeIn = false, fadeOut = false;
        private float amt = 0.003f;
        
        private int current = 0;
        private SuiContainer over = null;
        
        private int lastX = 0;
        private int lastY = 0;
                        
        public SuiToolTip() {
            super();
            setVisible(false);
            
            label = new SuiLabel();
            label.setHorizontalPadding(4);
            label.setVerticalPadding(4);
            label.setForeground(fgFilter);
            label.setBackground(bgFilter);
            label.setLocation(0, 0);
            label.setHorizontalAlignment(SuiLabel.LEFT_ALIGNMENT);
            label.setVerticalAlignment(SuiLabel.CENTER_ALIGNMENT);
            add(label);
        }
        
        public void updateUI(SuiSkin skin) {
            setUI(skin.getToolTipUI());
        }
        
        public SuiSkin.ToolTipUI getUI() {
            return (SuiSkin.ToolTipUI)super.getUI();
        }
        
        public void updateComponent(GUIContext c, int delta) {
            super.updateComponent(c, delta);
            
            Input in = c.getInput();
            
            int x = in.getMouseX();
            int y = in.getMouseY();
            
            //if it's still at the same position
            if (x==lastX && y==lastY) {
                if (over!=null) {
                    if (!over.isShowing() || !over.contains(x,y)) {
                        setVisible(false);
                        over=null;
                        return;
                    }
                }
                current += delta;
                if (current >= Sui.getToolTipDelay()) {
                    current = 0;
                    if (!isShowing()) {
                        SuiContainer comp = getDeepestComponentAt(SuiDisplay.this, x, y);
                        if (comp!=null) {
                            SuiDisplay.this.showToolTip(comp, x, y);
                            over = comp;
                        }
                    }
                }
            } else {
                if (over==null || !over.contains(x, y) || !over.isShowing()) {
                    current = 0;
                    setVisible(false);
                }
            }
            
            lastX = x;
            lastY = y;
            
            if (fadeIn) {
                bgFilter.a += delta * amt;
                fgFilter.a += delta * amt;
                if (bgFilter.a > 1f || fgFilter.a > 1f) {
                    bgFilter.a = 1f;
                    fgFilter.a = 1f;
                    fadeIn = false;
                }
            } else if (fadeOut) {
                bgFilter.a -= delta * amt;
                fgFilter.a -= delta * amt;
                if (bgFilter.a < 0f || fgFilter.a < 0f) {
                    bgFilter.a = 0f;
                    fgFilter.a = 0f;
                    fadeOut = false;
                    super.setVisible(false);
                }
            }
        }

        public void setVisible(boolean b) {
            if (b!=isShowing()) {
                current = 0;
                if (b && !fadeIn) {
                    fadeIn = true;
                    fadeOut = false;
                    bgFilter.a = 0f;
                    fgFilter.a = 0f;
                    super.setVisible(true);
                } else if (!fadeOut) {
                    fadeOut = true;
                    fadeIn = false;
                    bgFilter.a = 1f;
                    fgFilter.a = 1f;
                }
            }
        }
        
        public SuiLabel getLabel() {
            return label;
        }
        
        public void setLabel(SuiLabel l) {
            this.label = l;
        }
        
        public Color getForeground() {
            return fgFilter;
        }
        
        public Color getBackground() {
            return bgFilter;
        }
        
        public void setForeground(Color c) {
            fgFilter = c;
        }
        
        public void setBackground(Color c) {
            bgFilter = c;
        }
    }*/

    public SuiToolTip getToolTip() {
        return toolTip;
    }

    public void setToolTip(SuiToolTip toolTip) {
        this.toolTip = toolTip;
    }

    public boolean isToolTipEnabled() {
        return toolTipEnabled;
    }

    public void setToolTipEnabled(boolean toolTipEnabled) {
        this.toolTipEnabled = toolTipEnabled;
    }
    
    public void setInitialToolTipDelay(int delay) {
        tipShowTimer.setDelay(delay);
    }
    
    public int getInitialToolTipDelay() {
        return (int)tipShowTimer.getDelay();
    }
    
    public void setDismissToolTipDelay(int delay) {
        tipHideTimer.setDelay(delay);
    }
    
    public int getDismissToolTipDelay() {
        return (int)tipHideTimer.getDelay();
    }
}
