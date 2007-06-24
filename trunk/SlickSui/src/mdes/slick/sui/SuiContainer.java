package mdes.slick.sui;

import mdes.slick.sui.event.*;
import org.newdawn.slick.*;
import java.util.*;
import org.lwjgl.opengl.*;
import org.newdawn.slick.geom.*;
import javax.swing.event.EventListenerList;
import org.newdawn.slick.gui.*;

/**
 * SuiContainer is the base class for any Sui component. It doesn't
 * contain any information about fonts, colors, or images. It simply
 * holds local and absolute positions, sizes, listeners, children, etc.
 * <p>
 * The listener system has been changed in b.0.2 of SUI. The new listener
 * system mimics Swing, using a Listener interface (and an Adapter if needed)
 * where each method takes a subclass of SuiEvent.
 * <p>
 * For a SuiContainer to receive keyboard, controller and mouse wheel
 * events, it must be focusable and have the focus (ie: it must be
 * the focus owner).
 * <p>
 * Before a SuiContainer is created you should always call Sui.init(GUIContext).
 *
 * @author davedes
 * @since b.0.1
 */
public class SuiContainer implements java.io.Serializable {
    
    public static final int DEFAULT_LAYER = 0;
    public static final int PALETTE_LAYER = 100;
    public static final int MODAL_LAYER = 200;
    public static final int POPUP_LAYER = 300;
    public static final int DRAG_LAYER = 400;
    
    /** A list of children in this container. */
    private List children;
    private ZComparator zCompare = new ZComparator();
    private boolean childrenDirty = true;
    private int zIndex = DEFAULT_LAYER;
    private String tooltipText = null;
    private String name = null;
    static ClipBounds clip = new ClipBounds();
    protected boolean hasClip = true;
    private boolean skinEnabled = true;
    protected SuiSkin.ContainerUI ui = null;
    private SuiBorder border = null;
    
    /** The Slick Input fused in this SuiContainer. */
    private Input input;
    
    /** Whether this component is focusable, initially true. */
    private boolean focusable = true;
    
    /** Whether this component is visible. */
    private boolean visible = true;
    
    /** A type-safe list which holds the different listeners. */
    protected EventListenerList listenerList = new EventListenerList();
    
    /** A fully transparent color used internally. */
    private final Color TRANSPARENT_COLOR = new Color(0f, 0f, 0f, 0f);
    
    /** The parent of this container, used internally. */
    SuiContainer parent = null;
        
    //TODO: handle sharing of container instances with multiple displays
    
    /** The x position of this container. */
    private float x = 0f;
    
    /** The y position of this container. */
    private float y = 0f;
    
    /** The width this container. */
    private int width = 0;
    
    /** The height this container. */
    private int height = 0;
    
    /** Whether this label is opaque; drawing all pixels. */
    private boolean opaque = false;
        
    /** The current background color. */
    protected Color background = null;
    
    /** The current foreground color. */
    protected Color foreground = Sui.getTheme().getForeground();
    
    /** The current font. */
    private Font font = Sui.getDefaultFont();
    
    /** Whether this label is enabled. */
    private boolean enabled = true;
    
    /**
     * Whether this component is ignoring events
     * and letting them pass through to underlying
     * components.
     */
    private boolean glassPane = false;
    
    protected boolean inited = false;
        
    //TODO: fix static default font implementation
    //(incase font is changed after construction)
    
    /** Constructs an empty SuiContainer. */
    public SuiContainer() {
        super();
        children = new ArrayList();
    }
        
    protected final void init() {
        if (!inited) {
            inited=true;
            updateUI(Sui.getSkin());
        }
    }
    
    public void setZIndex(int z) {
        this.zIndex = z;
        if (parent!=null) {
            parent.childrenDirty = true;
        }
    }
    
    public int getZIndex() {
        return zIndex;
    }
    
    /**
     * Called to ensure the z-ordering of 
     * this container's children is correct.
     * If it isn't, it will be sorted appropriately.
     */
    public void ensureZOrder() {
        if (childrenDirty) {
            Collections.sort(children, zCompare);
            childrenDirty = false;
        }
    }
        
    /**
     * Adds the specified listener to the list.
     *
     * @param s the listener to receive events
     */
    public synchronized void addKeyListener(SuiKeyListener s) {
        listenerList.add(SuiKeyListener.class, s);
    }
    
    /**
     * Removes the specified listener from the list.
     *
     * @param s the listener to remove
     */
    public synchronized void removeKeyListener(SuiKeyListener s) {
        listenerList.remove(SuiKeyListener.class, s);
    }
    
    /**
     * Adds the specified listener to the list.
     *
     * @param s the listener to receive events
     */
    public synchronized void addMouseListener(SuiMouseListener s) {
        listenerList.add(SuiMouseListener.class, s);
    }
    
    /**
     * Removes the specified listener from the list.
     *
     * @param s the listener to remove
     */
    public synchronized void removeMouseListener(SuiMouseListener s) {
        listenerList.remove(SuiMouseListener.class, s);
    }
    
    /**
     * Adds the specified listener to the list.
     *
     * @param s the listener to receive events
     */
    public synchronized void addMouseWheelListener(SuiMouseWheelListener s) {
        listenerList.add(SuiMouseWheelListener.class, s);
    }
    
    /**
     * Removes the specified listener from the list.
     *
     * @param s the listener to remove
     */
    public synchronized void removeMouseWheelListener(SuiMouseWheelListener s) {
        listenerList.remove(SuiMouseWheelListener.class, s);
    }
    
    /**
     * Adds the specified listener to the list.
     *
     * @param s the listener to receive events
     */
    public synchronized void addControllerListener(SuiControllerListener s) {
        listenerList.add(SuiControllerListener.class, s);
    }
    
    /**
     * Removes the specified listener from the list.
     *
     * @param s the listener to remove
     */
    public synchronized void removeControllerListener(SuiControllerListener s) {
        listenerList.remove(SuiControllerListener.class, s);
    }
    
    /**
     * Adds the specified listener to the list.
     *
     * @param s the listener to receive events
     */
    public synchronized void addActionListener(SuiActionListener s) {
        listenerList.add(SuiActionListener.class, s);
    }
    
    /**
     * Removes the specified listener from the list.
     *
     * @param s the listener to remove
     */
    public synchronized void removeActionListener(SuiActionListener s) {
        listenerList.remove(SuiActionListener.class, s);
    }
    
    /**
     * Sets the visibility for this component and all of its
     * children. Invisible components are not rendered.
     *
     * @param b <tt>true</tt> if it should be renderable
     */
    public void setVisible(boolean b) {
        this.visible = b;
    }
    
    /**
     * Whether this component is currently visible.
     *
     * @return <tt>true</tt> if this component is visible
     */
    public boolean isVisible() {
        return visible;
    }
    
    /**
     * Returns an array of this SuiContainer's children.
     *
     * @return an array of SuiContainer children
     */
    public SuiContainer[] getChildren() {
        ensureZOrder();
        SuiContainer[] c = new SuiContainer[children.size()];
        return (SuiContainer[])children.toArray(c);
    }
    
    /**
     * Adds a child to this SuiContainer.
     *
     * @param child the child container to add
     * @return the child which was passed
     */
    public void add(SuiContainer child) {
        if (!containsChild(child)) {
            childrenDirty = true;
            child.parent = this;
            children.add(child);
        }
    }
    
    /**
     * Inserts a child to this SuiContainer at the specified index.
     *
     * @param child the child container to add
     * @param index the index to insert it to
     */
    public void add(SuiContainer child, int index) {
        if (!containsChild(child)) {
            childrenDirty = true;
            child.parent = this;
            children.add(index, child);
        }
    }
    
    /**
     * Removes the child from this SuiContainer if it exists.
     *
     * @param child the child container to remove
     * @return <tt>true</tt> if the child was removed
     */
    public boolean remove(SuiContainer child) {
        boolean contained = children.remove(child);
        if (contained) {
            childrenDirty = true;
            child.parent = null;
            child.releaseFocus();
        }
        return contained;
    }
    
    public SuiDisplay getDisplay() {
        SuiContainer top = parent;
        while (top!=null) {
            if (top instanceof SuiDisplay) 
                return (SuiDisplay)top;
            top = top.parent;
        }
        return null;
    }
    
    /**
     * Gets the child at the specified index.
     *
     * @param index the index of the child
     * @return the child
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public SuiContainer getChild(int index) {
        ensureZOrder();
        return (SuiContainer)children.get(index);
    }
    
    /**
     * Gets the number of components in this container.
     *
     * @return the number of components in this container
     */
    public int getChildCount() {
        return children.size();
    }
    
    /**
     * Whether this container contains the specified SuiContainer.
     *
     * @param c the container to check against
     * @return <tt>true</tt> if this container contains the specified
     *			SuiContainer
     */
    public boolean containsChild(SuiContainer c) {
        return children.contains(c);
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
            SuiContainer parent = this.parent;
            return (parent == null) || parent.isShowing();
        }
        return false;
    }
    
    /**
     * Removes all children from this SuiContainer.
     */
    public void removeAll() {
        for (int i=0; i<getChildCount(); i++) {
            if (!childrenDirty)
                childrenDirty = true;
            SuiContainer c = getChild(i);
            c.parent = null;
        }
        children.clear();
    }
    
    /**
     * Gets the parent of this container.
     *
     * @return the parent container, or <tt>null</tt>
     *			if this is a top-level component
     */
    public SuiContainer getParent() {
        return parent;
    }
    
    /**
     * Called to render this component.
     *
     * @param container the GUIContext we are rendering to
     * @param g the Graphics context we are rendering with
     */
    protected void renderComponent(GUIContext container, Graphics g) {
    }
    
    protected void renderSkin(GUIContext container, Graphics g, SuiTheme theme) {
        SuiSkin.ContainerUI ui = getUI();
        if (ui!=null)
            ui.renderUI(g, theme, this);
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
        init();
        this.enabled = b;
    }
    
    /**
     * Sets the font of this label.
     *
     * @param f the new font to use
     */
    public void setFont(Font f) {
        init();
        this.font = f;
    }
    
    /**
     * Gets the font being used by this label.
     *
     * @return the font being used
     */
    public Font getFont() {
        init();
        return font;
    }
    
    /**
     * Sets the foreground text color.
     *
     * @param c the new foreground color
     */
    public void setForeground(Color c) {
        init();
        this.foreground = c;
    }
    
    /**
     * Gets the foreground text color.
     *
     * @return the foreground color
     */
    public Color getForeground() {
        init();
        return foreground;
    }
    
    /**
     * Gets the background color. This will
     * only be drawn if this label is opaque.
     *
     * @return the background color
     */
    public Color getBackground() {
        init();
        return background;
    }
    
    /**
     * Sets the background color. This will
     * only be drawn if this label is opaque.
     *
     * @param c the new background color
     */
    public void setBackground(Color c) {
        init();
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
        init();
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
        init();
        return opaque;
    }
    
    /**
     * Called to update this component.
     *
     * @param container the GUIContext we are rendering to
     * @param delta the delta time (in ms)
     */
    protected void updateComponent(GUIContext container, int delta) {
    }
    
    /**
     * Called to recursively render all children of this container.
     *
     * @param container the GUIContext we are rendering to
     * @param g the Graphics context we are rendering with
     */
    protected void renderChildren(GUIContext container, Graphics g) {
        ensureZOrder();
        for (int i=0; i<getChildCount(); i++) {
            SuiContainer child = getChild(i);
            
            clip.x = getAbsoluteX();
            clip.y = getAbsoluteY();
            clip.width = getWidth();
            clip.height = getHeight();
            
            //TODO: fix clipping
            
            child.render(container, g);
        }
    }
    
    /**
     * Called after rendering the component and children to
     * render the border.
     *
     * @param container the GUIContext we are rendering to
     * @param g the Graphics context we are rendering with
     */
    protected void renderBorder(GUIContext container, Graphics g) {
        if (border!=null) {
            border.renderBorder(container, g, this);
        }
    }
    
    /**
     * Called after updating the component and children to
     * update the border.
     *
     * @param container the GUIContext we are rendering to
     * @param delta the delta time (in ms)
     */
    protected void updateBorder(GUIContext container, int delta) {
        if (border!=null) {
            border.updateBorder(container, delta, this);
        }
    }
    
    /**
     * Called to recursively update all children of this container.
     *
     * @param container the GUIContext we are rendering to
     * @param delta the delta time (in ms)
     */
    protected void updateChildren(GUIContext container, int delta) {
        ensureZOrder();
        for (int i=0; i<getChildCount(); i++)
            getChild(i).update(container, delta);
    }
    
    /**
     * Used internally for top-level components.
     *
     * @param container The container displaying this component
     * @param g The graphics context used to render to the display
     * @param topLevel <tt>true</tt> if this is a top-level component
     */
    void render(GUIContext container, Graphics g) {
        if (isShowing() && width!=0 && height!=0) {
            Color c = g.getColor();
            Font f = g.getFont();
            
            if (isClipEnabled()) {
                calcClipping(this);
                g.setClip((int)clip.x, (int)clip.y, (int)clip.width, (int)clip.height);
            }
            
            if (background!=null && opaque) {
                Color old = g.getColor();
                g.setColor(background);
                g.fillRect(getAbsoluteX(), getAbsoluteY(), getWidth(), getHeight());
                g.setColor(old);
            }
            
            if (this.isSkinEnabled()) {
                renderSkin(container, g, Sui.getTheme());
            }
            
            renderTree(container, g);
            
            g.setColor(c);
            g.setFont(f);
        }
    }
    
    void renderTree(GUIContext container, Graphics g) {
        renderComponent(container, g);
        renderBorder(container, g);
        renderChildren(container, g);
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
        //TODO: update only while visible?
        updateComponent(container, delta);
        updateBorder(container, delta);
        updateChildren(container, delta);
    }
        
    /**
     * Centers this container relative to the specified
     * container. If the passed container is <tt>null</tt>,
     * it is centered based on the GUIContext.
     *
     * @param c the container to center with
     */
    public void setLocationRelativeTo(SuiContainer c) {
        int pw=0, ph=0;
        if (c!=null) {
            pw=c.getWidth();
            ph=c.getHeight();
        } else {
            SuiDisplay d = getDisplay();
            GUIContext ctx = d!=null ? d.getContext() : SuiDisplay.cachedContext;
            if (ctx==null) {
                throw new IllegalStateException("could not find GUIContext");
            }
            pw=ctx.getWidth();
            ph=ctx.getHeight();
        }
        
        float cx = (pw/2.0f - getWidth()/2.0f);
        float cy = (ph/2.0f - getHeight()/2.0f);
        setLocation(Math.max(0,(int)cx), Math.max(0,(int)cy));
    }
    
    public void setLocationRelativeTo(GUIContext context) {
        int pw=context.getWidth(), ph=context.getHeight();
        float cx = (pw/2.0f - getWidth()/2.0f);
        float cy = (ph/2.0f - getHeight()/2.0f);
        setLocation((int)cx, (int)cy);
    }
    
    /**
     * Sets the x and y positions of this SuiContainer, relative
     * to its parent. If no parent exists, the location is absolute
     * to the GUIContext.
     *
     * @param x the x position of this component
     * @param y the y position of this component
     * @see mdes.slick.sui.SuiContainer#setBounds(float, float, int, int)
     */
    public void setLocation(float x, float y) {
        setX(x);
        setY(y);
    }
    
    /**
     * Sets the x position of this SuiContainer, relative
     * to its parent.
     *
     * @param x the x position of this component
     * @see mdes.slick.sui.SuiContainer#setLocation(float, float)
     */
    public void setX(float x) {
        this.x = x;
    }
    
    /**
     * Sets the y position of this SuiContainer, relative
     * to its parent.
     *
     * @param y the y position of this component
     * @see mdes.slick.sui.SuiContainer#setLocation(float, float)
     */
    public void setY(float y) {
        this.y = y;
    }
    
    /**
     * Gets the x position of this SuiContainer, relative
     * to its parent.
     *
     * @return the x position of this component
     * @see mdes.slick.sui.SuiContainer#setLocation(float, float)
     */
    public float getX() {
        return x;
    }
    
    /**
     * Gets the y position of this SuiContainer, relative
     * to its parent.
     *
     * @return the y position of this component
     * @see mdes.slick.sui.SuiContainer#setLocation(float, float)
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
     * Sets the bounds of this SuiContainer.
     * <p>
     * The x and y positions are relative to this
     * component's parent. However, if no parent exists,
     * the x and y positions are equivalent to the
     * absolute x and y positions.
     *
     * @param x the x position of this component
     * @param y the y position of this component
     * @param width the width of this component
     * @param height the height of this component
     */
    public void setBounds(float x, float y, int width, int height) {
        setLocation(x, y);
        setSize(width, height);
    }
    
    /**
     * Sets the size of this SuiContainer.
     *
     * @param width the width of this component
     * @param height the height of this component
     */
    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }
    
    /**
     * Sets the height of this SuiContainer.
     *
     * @param height the height of this component
     */
    public void setHeight(int height) {
        this.height = height;
    }
    
    /**
     * Sets the width of this SuiContainer.
     *
     * @param width the width of this component
     */
    public void setWidth(int width) {
        this.width = width;
    }
    
    /**
     * Gets the width of this SuiContainer.
     *
     * @return the width of this component
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Gets the height of this SuiContainer.
     *
     * @return the height of this component
     */
    public int getHeight() {
        return height;
    }
    
    public void setClipEnabled(boolean b) {
        this.hasClip = b;
    }
    
    public boolean isClipEnabled() {
        return hasClip && (parent==null||parent.isClipEnabled());
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
        focusable = b;
    }
    
    public void grabFocus() {
        if (!focusable)
            return;
        Sui.setFocusOwner(this);
        setWindowsActive(true);
    }
    
    void setWindowsActive(boolean b) {
        SuiContainer top = this;
        while (top!=null) {
            if (top instanceof SuiWindow2) {
                SuiWindow2 win = (SuiWindow2)top;
                win.setActive(b);
                if (b)
                    SuiDisplay.activeWindows.add(win);
            }
            top = top.parent;
        }
    }
    
    public boolean hasFocus() {
        return (focusable && Sui.getFocusOwner()==this);
    }
    
    /**
     * Releases the focus on this container and all of its
     * SuiWindow parents. If this container is not focusable no change
     * will be made.
     * <p>
     * If the top-level parent of this component is an
     * instance of SuiWindow, it will be deactivated.
     */
    public void releaseFocus() {
        if (!focusable) 
            return;
        if (Sui.getFocusOwner()==this) {
            Sui.setFocusOwner(null);
            setWindowsActive(false);
        }
    }
    
    /** Called on creation. Initially does nothing. */
    public void updateUI(SuiSkin skin) {
        if (!inited)
            inited = true;
        setUI(skin.getContainerUI());
    }
    
    public void setUI(SuiSkin.ContainerUI ui) {
        if (ui==null) {
            return;
        }
        SuiTheme theme = Sui.getTheme();
        if (this.ui!=null)
            this.ui.uninstallUI(this, theme);
        this.ui = ui;
        this.ui.installUI(this, theme);
    }
    
    public SuiSkin.ContainerUI getUI() {
        init();
        return ui;
    }
    
    public void setSkinEnabled(boolean b) {
        init();
        this.skinEnabled = b;
    }
    
    public boolean isSkinEnabled() {
        init();
        return skinEnabled;
    }
    
    /**
     * Whether the component is focusable.
     *
     * @return whether this component can receive focus
     */
    public boolean isFocusable() {
        init();
        return focusable;
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
        init();
        if (ui!=null)
            return ui.contains(this,x,y);
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
    
    //TODO: package-protected setParent() which sets parent x/y
    //TODO: local space ints
    //TODO: check for isVisible()
        
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
        init();
        glassPane = b;
    }
    
    /**
     * Whether this component is a glass pane component,
     * ignoring events and letting them pass through
     * to underlying components.
     *
     * @return whether this is a glass pane component
     */
    public boolean isGlassPane() {
        init();
        return glassPane;
    }
    
    public String getToolTipText() {
        init();
        return tooltipText;
    }

    public void setToolTipText(String tooltipText) {
        init();
        this.tooltipText = tooltipText;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    void calcClipping(SuiContainer comp) {
        float compx = comp.getAbsoluteX();
        float compy = comp.getAbsoluteY();
        int compw = comp.getWidth();
        int comph = comp.getHeight();
                
        if (clip.x < compx) {
            clip.width -= (compx-clip.x);
            clip.x = compx;
        }
        
        float d = clip.x + clip.width;
        float dcomp = compx + compw;
        if (d > dcomp) {
            clip.width -= (d-dcomp);
        }
        
        if (clip.y < compy) {
            clip.height -= (compy-clip.y);
            clip.y = compy;
        }
        
        float a = clip.y + clip.height;
        float acomp = compy + comph;
        if (a > acomp) {
            clip.height -= (a-acomp);
        }
        
        clip.x = Math.max(clip.x,0);
        clip.y = Math.max(clip.y,0);
        clip.width = Math.max(clip.width,0);
        clip.height = Math.max(clip.height,0);
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
     * Fires the specified key event to all key listeners
     * in this component.
     *
     * @param id the SuiKeyEvent id constant
     * @param key the Input constant
     * @param chr the character of the key
     * @see mdes.slick.sui.event.SuiKeyEvent
     */
    protected void fireKeyEvent(int id, int key, char chr) {
        SuiKeyEvent evt = null;
        
        final SuiKeyListener[] listeners =
                (SuiKeyListener[])listenerList.getListeners(SuiKeyListener.class);
        for (int i=0; i<listeners.length; i++) {
            //lazily create it
            if (evt==null) {
                evt = new SuiKeyEvent(this, id, key, chr);
            }
            switch (id) {
                case SuiKeyEvent.KEY_PRESSED:
                    listeners[i].keyPressed(evt);
                    break;
                case SuiKeyEvent.KEY_RELEASED:
                    listeners[i].keyReleased(evt);
                    break;
            }
        }
    }
    
    /**
     * Fires the specified mouse event to all mouse listeners
     * in this component.
     *
     * @param id the SuiMouseEvent id constant
     * @param button the index of the button (starting at 0)
     * @param x the local new x position
     * @param y the local new y position
     * @param ox the local old x position
     * @param oy the local old y position
     * @param absx the absolute x position
     * @param absy the absolute y position
     * @see mdes.slick.sui.event.SuiMouseEvent
     */
    protected void fireMouseEvent(int id, int button, int x, int y, int ox, int oy, int absx, int absy) {
        SuiMouseEvent evt = null;
        
        final SuiMouseListener[] listeners =
                (SuiMouseListener[])listenerList.getListeners(SuiMouseListener.class);
        for (int i=0; i<listeners.length; i++) {
            //lazily create it
            if (evt==null) {
                evt = new SuiMouseEvent(this, id, button, x, y, ox, oy, absx, absy);
            }
            switch (id) {
                case SuiMouseEvent.MOUSE_MOVED:
                    listeners[i].mouseMoved(evt);
                    break;
                case SuiMouseEvent.MOUSE_PRESSED:
                    listeners[i].mousePressed(evt);
                    break;
                case SuiMouseEvent.MOUSE_RELEASED:
                    listeners[i].mouseReleased(evt);
                    break;
                case SuiMouseEvent.MOUSE_DRAGGED:
                    listeners[i].mouseDragged(evt);
                    break;
                case SuiMouseEvent.MOUSE_ENTERED:
                    listeners[i].mouseEntered(evt);
                    break;
                case SuiMouseEvent.MOUSE_EXITED:
                    listeners[i].mouseExited(evt);
                    break;
            }
        }
    }
    
    /**
     * Fires the specified mouse event to all mouse listeners
     * in this component.
     *
     * @param id the SuiMouseEvent id constant
     * @param button the index of the button (starting at 0)
     * @param x the local x position
     * @param y the local y position
     * @param absx the absolute x position
     * @param absy the absolute y position
     * @see mdes.slick.sui.event.SuiMouseEvent
     */
    protected void fireMouseEvent(int id, int button, int x, int y, int absx, int absy) {
        fireMouseEvent(id, button, x, y, x, y, absx, absy);
    }
    
    /**
     * Fires the specified mouse event to all mouse listeners
     * in this component.
     *
     * @param id the SuiMouseEvent id constant
     * @param x the local x position
     * @param y the local y position
     * @param ox the local old x position
     * @param oy the local old y position
     * @param absx the absolute x position
     * @param absy the absolute y position
     * @see mdes.slick.sui.event.SuiMouseEvent
     */
    protected void fireMouseEvent(int id, int x, int y, int ox, int oy, int absx, int absy) {
        fireMouseEvent(id, SuiMouseEvent.NOBUTTON, x, y, ox, oy, absx, absy);
    }
    
    /**
     * Fires the specified mouse wheel event to all mouse wheel
     * listeners in this component.
     *
     * @param change the amount the mouse wheel has changed
     * @see mdes.slick.sui.event.SuiMouseWheelEvent
     */
    protected void fireMouseWheelEvent(int change) {
        SuiMouseWheelEvent evt = null;
        
        final SuiMouseWheelListener[] listeners =
                (SuiMouseWheelListener[])listenerList.getListeners(
                SuiMouseWheelListener.class);
        for (int i=0; i<listeners.length; i++) {
            //lazily create it
            if (evt==null) {
                evt = new SuiMouseWheelEvent(this, change);
            }
            listeners[i].mouseWheelMoved(evt);
        }
    }
    
    /**
     * Fires the specified controller event to all controller
     * listeners in this component.
     *
     * @param id the SuiControllerEvent id
     * @param controller the controller being used
     * @param button the button that was pressed/released
     * @see mdes.slick.sui.event.SuiControllerEvent
     */
    protected void fireControllerEvent(int id, int controller, int button) {
        SuiControllerEvent evt = null;
        
        final SuiControllerListener[] listeners = (SuiControllerListener[])listenerList.getListeners(SuiControllerListener.class);
        for (int i=0; i<listeners.length; i++) {
            //lazily create it
            if (evt==null) {
                evt = new SuiControllerEvent(this, id, controller, button);
            }
            switch (id) {
                case SuiControllerEvent.BUTTON_PRESSED:
                    listeners[i].controllerButtonPressed(evt);
                    break;
                case SuiControllerEvent.BUTTON_RELEASED:
                    listeners[i].controllerButtonReleased(evt);
                    break;
            }
        }
    }
    
    private class ZComparator implements Comparator{
        public int compare(Object o1, Object o2) {
            if (!(o1 instanceof SuiContainer) || !(o2 instanceof SuiContainer)) {
                throw new ClassCastException();
            }
            
            SuiContainer c1 = (SuiContainer)o1;
            SuiContainer c2 = (SuiContainer)o2;
            int res;
            
            if (c1 == null || c2 == null || c1.equals(c2)) {
                res = 0;
            } else {
                res = (c2.getZIndex() < c1.getZIndex() ? 1 : -1);
            }
            
            return res;
        }
    } 
    
    static class ClipBounds {
        float width=0f,height=0f,x=0f,y=0f;
    }

    public SuiBorder getBorder() {
        return border;
    }

    public void setBorder(SuiBorder border) {
        this.border = border;
    }
}