/*
 * Component.java
 *
 * Created on November 6, 2007, 3:38 PM
 */

package mdes.slick.sui;

import javax.swing.event.EventListenerList;

import mdes.slick.sui.event.ControllerEvent;
import mdes.slick.sui.event.ControllerListener;
import mdes.slick.sui.event.KeyEvent;
import mdes.slick.sui.event.KeyListener;
import mdes.slick.sui.event.MouseEvent;
import mdes.slick.sui.event.MouseListener;
import mdes.slick.sui.event.MouseWheelEvent;
import mdes.slick.sui.event.MouseWheelListener;
import mdes.slick.sui.skin.ComponentAppearance;
import mdes.slick.sui.Theme;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.GUIContext;

/**
 * An abstract base component which all other components must
 * derive from.
 * 
 * @author davedes
 * @since b.2.0
 */
public abstract class Component {
    
    boolean debugRender = false;
    
    /** The parent of this container, used internally. */
    Container parent = null;
    Display display = null;
    boolean displayDirty = true;
    boolean sizeChanged = false;
    
    public static final int DEFAULT_LAYER = 0;
    public static final int PALETTE_LAYER = 100;
    public static final int MODAL_LAYER = 200;
    public static final int POPUP_LAYER = 300;
    public static final int DRAG_LAYER = 400;
    
    //static ClipBounds clip = new ClipBounds();
    //protected boolean hasClip = true;
    //protected SuiSkin.ContainerUI ui = null;
    
    private int zIndex = DEFAULT_LAYER;
    private String tooltipText = null;
    private String name = null;
    private boolean appearanceEnabled = true;
    boolean hasFocus = false;
    private Rectangle bounds = new Rectangle(0f,0f,0f,0f);
    private Object skinData = null;
    
    private boolean borderVisible = true;
        
    /**
     * The Slick Input fused in this Container.
     */
    private Input input;
    
    /** Whether this component is focusable, initially true. */
    private boolean focusable = true;
    
    /** Whether this component is visible. */
    private boolean visible = true;
    
    /** A type-safe list which holds the different listeners. */
    protected EventListenerList listenerList = new EventListenerList();
        
    /** The x position of this container. */
    private float x = 0f;
    
    /** The y position of this container. */
    private float y = 0f;
    
    /** The width this container. */
    private float width = 0;
    
    /** The height this container. */
    private float height = 0;
    
    /** Whether this label is opaque; drawing all pixels. */
    private boolean opaque = false;
    
    /** The current background color. */
    protected Color background = getTheme().getBackground();
    
    /** The current foreground color. */
    protected Color foreground = getTheme().getForeground();
    
    /** The current font. */
    private Font font = Sui.getDefaultFont();
    
    /** Whether this label is enabled. */
    private boolean enabled = true;
    
    //protected boolean isInit = false;
    //protected boolean isEnsuredAppearance = false;
    private boolean requestFocusEnabled = true;
    
    private ComponentAppearance appearance;
    
    private Padding padding = new Padding(0);
       
    //cache the objects to avoid allocating new memory for them
    private Dimension cachedSize = new Dimension();
    private Point cachedLocation = new Point();
    private Point cachedAbsoluteLocation = new Point();
    private Dimension preferredSize = new Dimension();
    
    /**
     * Whether this component is ignoring events
     * and letting them pass through to underlying
     * components.
     */
    private boolean glassPane = false;
        
    //TODO: fix static default font implementation
    //(incase font is changed after construction)
    
    /**
     * Creates a new instance of Component
     */
    public Component() {
    }
    
    /**
     * <p>This method updates the appearance by retrieving it from
     * the current skin.</p>
     * <p>This method is typically called from the constructor of a 
     * subclass. It is usually implemented in the following manner:
     * <pre><code>public void updateAppearance() {
     *     setAppearance(Sui.getSkin().getXXXAppearance());
     * }</code></pre>
     * Where <code>getXXXAppearance</code> represents the component 
     * appearance (such as getButtonAppearance).
     */
    public abstract void updateAppearance();
    
    public void setAppearance(ComponentAppearance appearance) {
        if (this.appearance!=null)
            this.appearance.uninstall(this, Sui.getSkin(), Sui.getTheme());
        this.appearance = appearance;
        if (this.appearance!=null)
            this.appearance.install(this, Sui.getSkin(), Sui.getTheme());
    }
    
    public ComponentAppearance getAppearance() {
        return appearance;
    }
        
    /**
     * Adds the specified listener to the list.
     *
     * @param s the listener to receive events
     */
    public synchronized void addKeyListener(KeyListener s) {
        listenerList.add(KeyListener.class, s);
    }
    
    /**
     * Removes the specified listener from the list.
     *
     * @param s the listener to remove
     */
    public synchronized void removeKeyListener(KeyListener s) {
        listenerList.remove(KeyListener.class, s);
    }
    
    /**
     * Adds the specified listener to the list.
     *
     * @param s the listener to receive events
     */
    public synchronized void addMouseListener(MouseListener s) {
        listenerList.add(MouseListener.class, s);
    }
    
    /**
     * Removes the specified listener from the list.
     *
     * @param s the listener to remove
     */
    public synchronized void removeMouseListener(MouseListener s) {
        listenerList.remove(MouseListener.class, s);
    }
    
    /**
     * Adds the specified listener to the list.
     *
     * @param s the listener to receive events
     */
    public synchronized void addMouseWheelListener(MouseWheelListener s) {
        listenerList.add(MouseWheelListener.class, s);
    }
    
    /**
     * Removes the specified listener from the list.
     *
     * @param s the listener to remove
     */
    public synchronized void removeMouseWheelListener(MouseWheelListener s) {
        listenerList.remove(MouseWheelListener.class, s);
    }
    
    /**
     * Adds the specified listener to the list.
     *
     * @param s the listener to receive events
     */
    public synchronized void addControllerListener(ControllerListener s) {
        listenerList.add(ControllerListener.class, s);
    }
    
    /**
     * Removes the specified listener from the list.
     *
     * @param s the listener to remove
     */
    public synchronized void removeControllerListener(ControllerListener s) {
        listenerList.remove(ControllerListener.class, s);
    }
    
    /**
     * Sets the visibility for this component and all of its
     * children. Invisible components are not rendered. If a 
     * component with the focus is hidden, the focus is released.
     *
     * @param b <tt>true</tt> if it should be renderable
     */
    public void setVisible(boolean b) {
        boolean old = this.visible;
        this.visible = b;
        if (this.visible != old && hasFocus()) //changed
            releaseFocus();
    }
    
    /**
     * Whether this component is currently visible.
     *
     * @return <tt>true</tt> if this component is visible
     */
    public boolean isVisible() {
        return visible;
    }
    
    public void setZIndex(int z) {
        this.zIndex = z;
    }
    
    public int getZIndex() {
        return zIndex;
    }
    
    /**
     * Used internally to determine the display.
     */
    protected Display findDisplay() {
        if(parent == null) 
            return null;
        return parent.getDisplay(); //TODO: cache display
    }
    
    public Display getDisplay() {
        if (displayDirty) {
            display = findDisplay();
            displayDirty = display==null;
        }
        return display;
    }
    
    /**
     * Determines whether this component is showing on screen. This means
     * that the component must be visible, and it must be in a container
     * that is visible and showing.
     * @return <code>true</code> if the component is showing,
     *          <code>false</code> otherwise
     * @see #setVisible
     */
    public boolean isShowing() {
        if (visible) {
            Container parent = this.parent;
            return (parent == null) || parent.isShowing();
        }
        return false;
    }
    
    Component getFirstNonGlassPane() {
        if (!isGlassPane())
            return this;
        if (parent==null)
            return null;
        return parent.getFirstNonGlassPane();
    }
        
    /**
     * Gets the parent of this container.
     *
     * @return the parent container, or <tt>null</tt>
     *			if this is a top-level component
     */
    public Container getParent() {
        return parent;
    }
    
    /**
     * Called to render this component. By default, this method will
     * render the current appearance if it exists and is enabled. 
     *
     * @param container the GUIContext we are rendering to
     * @param g the Graphics context we are rendering with
     */
    protected void renderComponent(GUIContext container, Graphics g) {
        ComponentAppearance appearance = getAppearance();
        if (this.isAppearanceEnabled() && appearance!=null) {
            appearance.render(container, g, this, Sui.getSkin(), Sui.getTheme());
        }
    }
    
    
    
    /**
     * Whether this label is enabled
     *
     * @return <tt>true</tt> if this label is enabled
     */
    public boolean isEnabled() {
        return enabled;
    }
    
    /**
     * Set whether to enable or disable this label.
     *
     * @param b <tt>true</tt> if this label should be enabled
     */
    public void setEnabled(boolean b) {
        this.enabled = b;
    }
    
    /**
     * Sets the font of this label.
     *
     * @param f the new font to use
     */
    public void setFont(Font f) {
        this.font = f;
    }
    
    /**
     * Gets the font being used by this label.
     *
     * @return the font being used
     */
    public Font getFont() {
        return font;
    }
    
    /**
     * Sets the foreground text color.
     *
     * @param c the new foreground color
     */
    public void setForeground(Color c) {
        this.foreground = c;
    }
    
    /**
     * Gets the foreground text color.
     *
     * @return the foreground color
     */
    public Color getForeground() {
        return foreground;
    }
    
    /**
     * Gets the background color. This will
     * only be drawn if this label is opaque.
     *
     * @return the background color
     */
    public Color getBackground() {
        return background;
    }
    
    /**
     * Sets the background color. This will
     * only be drawn if this label is opaque.
     *
     * @param c the new background color
     */
    public void setBackground(Color c) {
        this.background = c;
    }
    
    /**
     * Sets whether this label is opaque.
     * Opaque components will draw any
     * transparent pixels with the
     * background color.
     *
     * @param b <tt>true</tt> if background color
     *			should be drawn
     */
    public void setOpaque(boolean b) {
        this.opaque = b;
    }
    
    /**
     * Returns whether this label is opaque.
     * Opaque labels will fill a rectangle
     * of the background color before drawing the
     * text or image.
     *
     * @return <tt>true</tt> if background color
     *			should be drawn
     */
    public boolean isOpaque() {
        return opaque;
    }
    
    /**
     * Called to update this component. By default, this method will
     * render the current appearance if it exists and is enabled. 
     *
     *
     * @param container the GUIContext we are rendering to
     * @param delta the delta time (in ms)
     */
    protected void updateComponent(GUIContext container, int delta) {
    }
    
    /**
     * Called to render this component as a whole. For subclasses looking
     * for custom rendering (outside of skins), it is suggested that you
     * override renderComponent and disable the appearance.
     *
     * @param container The container displaying this component
     * @param g The graphics context used to render to the display
     * @param topLevel <tt>true</tt> if this is a top-level component
     */
    public void render(GUIContext container, Graphics g) {        
        if (isShowing() && width!=0 && height!=0) {
            Color c = g.getColor();
            Font f = g.getFont();
            
            renderComponent(container, g);
            
            if (debugRender) {
                g.setColor(Color.red);
                g.draw(getAbsoluteBounds());
            }
            
            g.setColor(c);
            g.setFont(f);
        }
    }
    
    /**
     * Updates this container and its children to the screen.
     * <p>
     * The order of updating is as follows:<ol>
     * <li>Update this component through updateComponent</li>
     * <li>Update this component's border through updateBorder</li>
     * <li>Update this component's children through updateChildren</li>
     * </ol>
     * <p>
     * For custom updating of the component, override renderComponent.<br>
     * For cusotm updating of the border, override renderBorder.<br>
     * For custom updating of the children, override renderChildren.<br>
     *
     * @param container The container displaying this component
     * @param delta The delta to update by
     */
    public void update(GUIContext container, int delta) {     
        if (this.isAppearanceEnabled() && appearance!=null) {
            appearance.update(container, delta, this, Sui.getSkin(), Sui.getTheme());
        }
        
        //TODO: update only while visible?
        updateComponent(container, delta);
    }
       
    /**
     * Centers this container relative to the specified
     * Component. If the passed container is <tt>null</tt>,
     * the size of the game context is used instead. To determine
     * the context, this component must first call getDisplay() --
     * if that fails, the most recently cached context is used. If
     * no context is found, the method returns <tt>false</tt> without
     * changing the location.
     * 
     * 
     * @param c the component to center with
     * @return <tt>fale</tt> if no display was found and if this component
     *      was not moved, <tt>true</tt> otherwise
     */
    public boolean setLocationRelativeTo(Component c) {
        float pw=0, ph=0;
        if (c!=null) {
            pw=c.getWidth();
            ph=c.getHeight();
            setX(c.getWidth()/2f - getWidth()/2f);
            setY(c.getHeight()/2f - getHeight()/2f);
        } else {
            Display d = getDisplay();
            GUIContext ctx = d!=null ? d.getContext() : Display.cachedContext;
            if (ctx==null)
                return false;
            else
                setLocationRelativeToContext(ctx);
        }
        return true;
    }
    
    /**
     * Centers this component based on the size of the given context.
     * If <tt>null</tt> is passed, no change is made.
     *
     * @param context the context to center to
     */
    public void setLocationRelativeToContext(GUIContext context) {
        if (context==null)
            return;
        setLocation(context.getWidth()/2f - getWidth()/2f, 
                    context.getHeight()/2f - getHeight()/2f);
    }
    
    /**
     * Sets the x and y positions of this Container, relative
     * to its parent. If no parent exists, the location is absolute
     * to the GUIContext.
     * 
     * 
     * @param x the x position of this component
     * @param y the y position of this component
     * @see mdes.slick.sui.Container#setBounds(float, float, int, int)
     */
    public void setLocation(float x, float y) {
        setX(x);
        setY(y);
    }
    
    public void setLocation(Point p) {
        this.setLocation(p.x, p.y);
    }
    
    /**
     * Sets the x position of this Container, relative
     * to its parent.
     * 
     * 
     * @param x the x position of this component
     * @see mdes.slick.sui.Container#setLocation(float, float)
     */
    public void setX(float x) {
        this.x = x;
    }
    
    /**
     * Sets the y position of this Container, relative
     * to its parent.
     * 
     * 
     * @param y the y position of this component
     * @see mdes.slick.sui.Container#setLocation(float, float)
     */
    public void setY(float y) {
        this.y = y;
    }
    
    /**
     * Gets the x position of this Container, relative
     * to its parent.
     * 
     * 
     * @return the x position of this component
     * @see mdes.slick.sui.Container#setLocation(float, float)
     */
    public float getX() {
        return x;
    }
    
    /**
     * Gets the y position of this Container, relative
     * to its parent.
     * 
     * 
     * @return the y position of this component
     * @see mdes.slick.sui.Container#setLocation(float, float)
     */
    public float getY() {
        return y;
    }
    
    /**
     * Translates the location of this container.
     *
     * @param x the x amount to translate by
     * @param y the y amount to translate by
     */
    public void translate(float x, float y) {
        float dx = getX()+x;
        float dy = getY()+y;
        setLocation(dx, dy);
    }
    
    /**
     * Gets the absolute x position of this
     * component. This is <i>not</i> relative to the
     * parent's position.
     *
     * @return the x position in the GUIContext
     */
    public float getAbsoluteX() {
        return (parent==null) ? x : x+parent.getAbsoluteX();
    }
    
    /**
     * Gets the absolute y position of this
     * component. This is <i>not</i> relative to the
     * parent's position.
     *
     * @return the y position in the GUIContext
     */
    public float getAbsoluteY() {
        return (parent==null) ? y : y+parent.getAbsoluteY();
    }
    
    /**
     * Sets the bounds of this Container.
     * <p>
     * The x and y positions are relative to this
     * component's parent. However, if no parent exists,
     * the x and y positions are equivalent to the
     * absolute x and y positions.
     * 
     * 
     * @param x the x position of this component
     * @param y the y position of this component
     * @param width the width of this component
     * @param height the height of this component
     */
    public void setBounds(float x, float y, float width, float height) {
        setLocation(x, y);
        setSize(width, height);
    }
    
    public Rectangle getAbsoluteBounds() {
        bounds.setX(getAbsoluteX());
        bounds.setY(getAbsoluteY());
        bounds.setWidth(getWidth());
        bounds.setHeight(getHeight());
        return bounds;
    }
    
    /**
     * Sets the size of this Container.
     * 
     * 
     * @param width the width of this component
     * @param height the height of this component
     */
    public void setSize(float width, float height) {
        setWidth(width);
        setHeight(height);
    }
    
    public void setSize(Dimension d) {
        this.setSize(d.width, d.height);
    }
    
    public Dimension getSize() {
        cachedSize.width = getWidth();
        cachedSize.height = getHeight();
        return cachedSize;
    }
    
    public Point getLocation() {
        cachedLocation.x = getX();
        cachedLocation.y = getY();
        return cachedLocation;
    }
    
    public Point getAbsoluteLocation() {
        cachedAbsoluteLocation.x = getAbsoluteX();
        cachedAbsoluteLocation.y = getAbsoluteY();
        return cachedAbsoluteLocation;
    }
    
    /**
     * Sets the height of this Container.
     * 
     * 
     * @param height the height of this component
     */
    public void setHeight(float height) {
        float old = this.height;
        this.height = height;
        if (old != this.width) {
            sizeChanged = true;
            onResize();
        }
    }
    
    /**
     * Sets the width of this Container.
     * 
     * 
     * @param width the width of this component
     */
    public void setWidth(float width) {
        float old = this.width;
        this.width = width;
        if (old != this.width) {
            sizeChanged = true;
            onResize();
        }
    }
    
    /**
     * Gets the width of this Container.
     * 
     * 
     * @return the width of this component
     */
    public float getWidth() {
        return width;
    }
    
    /**
     * Gets the height of this Container.
     * 
     * 
     * @return the height of this component
     */
    public float getHeight() {
        return height;
    }
    
    /**
     * Grow the rectangle at all edges by the given amounts. This will 
     * result in the rectangle getting larger around it's centre.
     * 
     * @param h the horizontal amount to adjust
     * @param v the vertical amount to adjust
     */
    public void grow(float h, float v) {
        setX(getX() - h);
        setY(getY() - v);
        setWidth(getWidth() + (h*2));
        setHeight(getHeight() + (v*2));
        
    }
    
    /**
     * Grow the rectangle based on scaling it's size
     *
     * @param h The scale to apply to the horizontal
     * @param v The scale to appy to the vertical
     */
    public void scaleGrow(float h, float v) {
        grow(getWidth() * (h-1), getHeight() * (v-1));
    }

    public void setAppearanceEnabled(boolean b) {
        this.appearanceEnabled = b;
    }
    
    public boolean isAppearanceEnabled() {
        return appearanceEnabled;
    }
        
    /**
     * Sets the focus ability of this component. Focusable components can
     * receive key, controller or mouse wheel events if they have the focus.
     * Non-focusable components will never receive key, controller or mouse
     * wheel events.
     *
     * @param b <tt>true</tt> if this component should receive key, controller
     *				or mouse wheel events when it has the focus
     */
    public void setFocusable(boolean b) {
        boolean old = b;
        focusable = b;
        
        //going from focusable to non-focusable 
        //ensure that it is not the focus owner
        if (old && !focusable) {
            releaseFocus();
        }
    }
    
    public boolean isFocusable() {
        return focusable;
    }
    
    public void grabFocus() {
        if (!focusable)
            return;
        Display d = getDisplay();
        if (d==null)
            return;
        d.setFocusOwner(this);
        setWindowsActive(true, d);
    }
    
    public boolean hasFocus() {
        return hasFocus && focusable;
        /*Display d = getDisplay();
        return d!=null ? d.getFocusOwner()==this : false;*/
    }
    
    /**
     * Releases the focus on this container and all of its
     * Window parents. If this component did not have the 
     * focus, no change is made.
     * <p>
     * If the top-level parent of this component is an
     * instance of Window, it will be deactivated.
     */
    public void releaseFocus() {
        Display display = getDisplay();
        if (display==null)
            return;
        
        if (display.getFocusOwner()==this) {
            display.setFocusOwner(null);
            setWindowsActive(false, display);
        }
    }
    
    void setWindowsActive(boolean b, Display display) {
        display.clearActiveWindows();
        Component top = this;
        while (top!=null) {
            if (top instanceof Window) {
                Window win = (Window)top;
                boolean old = win.isActive();
                win.setActive(b);
                
                if (b)
                    display.activeWindows.add(win);
            }
            top = top.parent;
        }
    }
    
    //TODO: skip input for overlapping buttons
    //EG: click button on a content pane
    //	  but content pane receives event
        
    /**
     * Whether the absolute (relative to the GUIContext) x
     * and y positions are contained within this component.
     *
     * @param x the x position
     * @param y the y position
     * @return <tt>true</tt> if this component contains the specified
     *			point
     */
    public boolean contains(float x, float y) {
        ComponentAppearance appearance = getAppearance();
        if (appearance!=null)
            return appearance.contains(this, x, y);
        else
            return inside(x,y);
    }
    
    /**
     * Checks whether the specified point is within the bounds 
     * of this component.
     */
    public boolean inside(float x, float y) {
        float ax = getAbsoluteX(), ay = getAbsoluteY();
        return x>=ax && y>=ay && x<=ax+getWidth() && y<=ay+getHeight();
    }
    
    /**
     * Glass pane components will ignore events and
     * the underlying components (ie: the parent panel) will
     * pick them up instead.
     * <p>
     * Still testing this.
     *
     * @param b whether this component should be glass pane
     */
    public void setGlassPane(boolean b) {
        glassPane = b;
    }
    
    //TODO: package-protected setParent() which sets parent x/y
    //TODO: local space ints
    //TODO: check for isVisible()
    
    /**
     * Whether this component is a glass pane component,
     * ignoring events and letting them pass through
     * to underlying components.
     *
     * @return whether this is a glass pane component
     */
    public boolean isGlassPane() {
        return glassPane;
    }
    
    public String getToolTipText() {
        return tooltipText;
    }

    public void setToolTipText(String tooltipText) {
        this.tooltipText = tooltipText;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
        
    protected Theme getTheme() {
        return Sui.getTheme();
    }
    
    
    public void setPadding(Padding p) {
        if (p==null)
            throw new IllegalArgumentException("padding cannot be null");
        this.padding = p;
    }
    
    public void setPadding(float top, float left, float bottom, float right) {
        padding.set(top, left, bottom, right);
    }
    
    /**
     * Sets the padding of this label.
     *
     * @param i the padding, in pixels
     */
    public void setPadding(float p) {
        padding.set(p);
    }
    
    /**
     * Sets the top and bottom padding of this component.
     */
    public void setVerticalPadding(float p) {
        padding.top = p;
        padding.bottom = p;
    }
    
    /**
     * Sets the left and right padding of this component.
     */
    public void setHorizontalPadding(float p) {
        padding.left = p;
        padding.right = p;
    }
    
    public Padding getPadding() {
        return padding;
    }
        
    /**
     * Returns a String representation of this container.
     *
     * @return a String representation of this container
     */
    public String toString() {
        String str = super.toString();
        if (name!=null)
            str += " ["+name+"]";
        return str;
    }
    
    /**
     * Can be overridden if you wish this component's mouse
     * dragged, pressed and released events to pass down to slick.
     * <p>
     * For example, the Display does not (by default) consume
     * events.
     * 
     * 
     * @return <tt>true</tt> if we should consume an even on a
     *      mouse press/release of this component
     */
    protected boolean isConsumingEvents() {
        return true;
    }
    
    /**
     * Fires the specified key event to all key listeners
     * in this component.
     * 
     * 
     * @param id the KeyEvent id constant
     * @param key the Input constant
     * @param chr the character of the key
     * @see mdes.slick.sui.event.KeyEvent
     */
    protected void fireKeyEvent(int id, int key, char chr) {
        KeyEvent evt = null;
        
        final KeyListener[] listeners =
                (KeyListener[])listenerList.getListeners(KeyListener.class);
        for (int i=0; i<listeners.length; i++) {
            //lazily create it
            if (evt==null) {
                evt = new KeyEvent(this, id, key, chr);
            }
            switch (id) {
                case KeyEvent.KEY_PRESSED:
                    listeners[i].keyPressed(evt);
                    break;
                case KeyEvent.KEY_RELEASED:
                    listeners[i].keyReleased(evt);
                    break;
            }
        }
    }
    
    /**
     * Fires the specified mouse event to all mouse listeners
     * in this component.
     * 
     * 
     * @param id the MouseEvent id constant
     * @param button the index of the button (starting at 0)
     * @param x the local new x position
     * @param y the local new y position
     * @param ox the local old x position
     * @param oy the local old y position
     * @param absx the absolute x position
     * @param absy the absolute y position
     * @see mdes.slick.sui.event.MouseEvent
     */
    protected void fireMouseEvent(int id, int button, int x, int y, int ox, int oy, int absx, int absy) {
        MouseEvent evt = null;
        
        final MouseListener[] listeners =
                (MouseListener[])listenerList.getListeners(MouseListener.class);
        for (int i=0; i<listeners.length; i++) {
            //lazily create it
            if (evt==null) {
                evt = new MouseEvent(this, id, button, x, y, ox, oy, absx, absy);
            }
            switch (id) {
                case MouseEvent.MOUSE_MOVED:
                    listeners[i].mouseMoved(evt);
                    break;
                case MouseEvent.MOUSE_PRESSED:
                    listeners[i].mousePressed(evt);
                    break;
                case MouseEvent.MOUSE_RELEASED:
                    listeners[i].mouseReleased(evt);
                    break;
                case MouseEvent.MOUSE_DRAGGED:
                    listeners[i].mouseDragged(evt);
                    break;
                case MouseEvent.MOUSE_ENTERED:
                    listeners[i].mouseEntered(evt);
                    break;
                case MouseEvent.MOUSE_EXITED:
                    listeners[i].mouseExited(evt);
                    break;
            }
        }
    }
    
    /**
     * Fires the specified mouse event to all mouse listeners
     * in this component.
     * 
     * 
     * @param id the MouseEvent id constant
     * @param button the index of the button (starting at 0)
     * @param x the local x position
     * @param y the local y position
     * @param absx the absolute x position
     * @param absy the absolute y position
     * @see mdes.slick.sui.event.MouseEvent
     */
    protected void fireMouseEvent(int id, int button, int x, int y, int absx, int absy) {
        fireMouseEvent(id, button, x, y, x, y, absx, absy);
    }
    
    /**
     * Fires the specified mouse event to all mouse listeners
     * in this component.
     * 
     * 
     * @param id the MouseEvent id constant
     * @param x the local x position
     * @param y the local y position
     * @param ox the local old x position
     * @param oy the local old y position
     * @param absx the absolute x position
     * @param absy the absolute y position
     * @see mdes.slick.sui.event.MouseEvent
     */
    protected void fireMouseEvent(int id, int x, int y, int ox, int oy, int absx, int absy) {
        fireMouseEvent(id, MouseEvent.NOBUTTON, x, y, ox, oy, absx, absy);
    }
    
    /**
     * Fires the specified mouse wheel event to all mouse wheel
     * listeners in this component.
     * 
     * 
     * @param change the amount the mouse wheel has changed
     * @see mdes.slick.sui.event.MouseWheelEvent
     */
    protected void fireMouseWheelEvent(int change) {
        MouseWheelEvent evt = null;
        
        final MouseWheelListener[] listeners =
                (MouseWheelListener[])listenerList.getListeners(
                MouseWheelListener.class);
        for (int i=0; i<listeners.length; i++) {
            //lazily create it
            if (evt==null) {
                evt = new MouseWheelEvent(this, change);
            }
            listeners[i].mouseWheelMoved(evt);
        }
    }
    
    /**
     * Fires the specified controller event to all controller
     * listeners in this component.
     * 
     * 
     * @param id the ControllerEvent id
     * @param controller the controller being used
     * @param button the button that was pressed/released
     * @see mdes.slick.sui.event.ControllerEvent
     */
    protected void fireControllerEvent(int id, int controller, int button) {
        ControllerEvent evt = null;
        
        final ControllerListener[] listeners = (ControllerListener[])listenerList.getListeners(ControllerListener.class);
        for (int i=0; i<listeners.length; i++) {
            //lazily create it
            if (evt==null) {
                evt = new ControllerEvent(this, id, controller, button);
            }
            switch (id) {
                case ControllerEvent.BUTTON_PRESSED:
                    listeners[i].controllerButtonPressed(evt);
                    break;
                case ControllerEvent.BUTTON_RELEASED:
                    listeners[i].controllerButtonReleased(evt);
                    break;
            }
        }
    }
    
    public void onResize() {
	if(parent!= null)
            parent.layout.doLayout(parent);
    }
    
    public Object getSkinData() {
        return skinData;
    }

    public void setSkinData(Object skinData) {
        this.skinData = skinData;
    }

    public boolean isRequestFocusEnabled() {
        return requestFocusEnabled;
    }

    public void setRequestFocusEnabled(boolean requestFocusEnabled) {
        this.requestFocusEnabled = requestFocusEnabled;
    }

    public boolean isBorderRendered() {
        return borderVisible;
    }

    /**
     * Provides a hint for skins as to whether or not this component should 
     * display its border. The default value is <tt>true</tt>, even if the 
     * component has no border. It is up to the skin developer to choose whether
     * or not it is worth their while to implement support for visible borders
     * on components.
     *
     * @param borderVisible <tt>true</tt> if a border should be rendered on this 
     *          component, assuming it has one defined by the current skin
     */
    public void setBorderRendered(boolean borderVisible) {
        this.borderVisible = borderVisible;
    }

    public boolean isSizeChanged() {
        return sizeChanged;
    }

    public void setSizeChanged(boolean sizeChanged) {
        this.sizeChanged = sizeChanged;
    }
    
    //TODO: fix crappy polling stuff
    void pollEnded() {
        this.sizeChanged = false;
    }
    
    public Dimension getPreferredSize() {
        return preferredSize;
    }
    
    //used by layouts
    public void setPreferredSize(Dimension preferredSize) {
        this.preferredSize = preferredSize;
    }
}
