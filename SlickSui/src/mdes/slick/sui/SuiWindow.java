package mdes.slick.sui;


import org.newdawn.slick.*;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.*;
import mdes.slick.sui.event.*;
import org.newdawn.slick.gui.GUIContext;

/**
 * SuiWindow is the basic top-level dialog window which
 * can be dragged and resized.
 * <p>
 * Windows can be set to be non-draggable, non-resizable,
 * have resize limits (max of x, y) and other features.
 *
 * @author davedes
 * @since b.0.1
 */
public class SuiWindow extends SuiContainer {
    
    /**
     * The title bar for SuiWindow is a SuiLabel itself.
     */
    protected SuiLabel titlePane = new SuiLabel() {
        protected void renderComponent(GUIContext c, Graphics g) {
            super.renderComponent(c, g);
        }
        
        public void setText(String s) {
            super.setText(s);
            pack();
        }
    };
    
    /**
     * The resizer component is a small triangle in the bottom-right corner.
     */
    protected SuiContainer resizer = new SuiContainer() {
        {
            setSize(8, 8);
            addMouseListener(new WindowResizeListener());
        }
        
        protected void renderComponent(GUIContext c, Graphics g) {
            if (!SuiWindow.this.isResizable())
                return;
            
            Color t = Sui.getTheme().getForeground();
            
            //bind texture & color before entering gl
            t.bind();
            
            //begin drawing the triangle
            GL11.glBegin(GL11.GL_TRIANGLES);
            GL11.glVertex3f(getAbsoluteX()+getWidth(), getAbsoluteY(), 0);
            GL11.glVertex3f(getAbsoluteX()+getWidth(), getAbsoluteY()+getHeight(), 0);
            GL11.glVertex3f(getAbsoluteX(), getAbsoluteY()+getHeight(), 0);
            GL11.glEnd();
        }
        
        public float getAbsoluteX() {
            return SuiWindow.this.getAbsoluteX()+SuiWindow.this.getWidth()-getWidth();
        }
        
        public float getAbsoluteY() {
            return SuiWindow.this.getAbsoluteY()+SuiWindow.this.getHeight()-getHeight()-1;
        }
    };
    
    /** Specifies that the window can be resized infinitely. */
    public static final int MAX_RESIZE = Integer.MAX_VALUE;
    
    /** The minimum width for resizing. */
    private int minWidth;
    
    /** The minimum height for resizing. */
    private int minHeight;
    
    /** The maximum width for resizing. */
    private int maxWidth;
    
    /** The minimum height for resizing. */
    private int maxHeight;
    
    /** Used internally, the initial width of the dialog. */
    private final int INITIAL_WIDTH = 200;
    
    /** The close button in the window. */
    protected SuiButton closeButton;
    
    /** Whether this window is resizable. */
    private boolean resizable = true;
    
    /** Whether this window is draggable. */
    private boolean draggable = true;
    
    /** The background color of this window. */
    protected Color background = Color.gray;
    
    /** Whether this window is active (one of its children has the focus). */
    boolean active = false;
    
    /** Window drop shadow color. */
    protected Color shadow = new Color(Color.gray);
    
    /** The width of the drop shadow for this window. */
    protected int shadowWidth = 2;
    
    protected SuiMouseListener dragListener = new WindowDragListener();
    
    //TODO: minimum resize width for title
    //TODO: concact title with "..."
    //TODO: fix absolute Y coord for window
    //TODO: fix background color & change of theme
    
    /**
     * Creates a new SuiWindow with the specified title.
     *
     * @param title the text to display on the title bar
     */
    public SuiWindow(String title) {
        super();
        
        //set up title bar, which is a label
        titlePane.setPadding(5);
        titlePane.setHorizontalAlignment(SuiLabel.LEFT_ALIGNMENT);
        titlePane.setLocation(0, 0);
        
        //quick hack to pack the label height
        titlePane.setText(title);
        titlePane.setHeight(20);
        
        titlePane.setWidth(INITIAL_WIDTH);
        titlePane.addMouseListener(dragListener);
        
        //set up close button
        closeButton = new SuiButton("x");
        closeButton.setPadding(2);
        closeButton.pack();
        closeButton.setY( titlePane.getHeight()/2 - closeButton.getHeight()/2 );
        closeButton.addActionListener(new SuiActionListener() {
            public void actionPerformed(SuiActionEvent e) {
                SuiWindow.this.setVisible(false);
            }
        });
        
        //add close button to titlepane
        titlePane.add(closeButton);
        
        setMinimumSize(resizer.getWidth(), resizer.getHeight()+1);
        setMaximumSize(MAX_RESIZE, MAX_RESIZE);
        
        setSize(INITIAL_WIDTH, titlePane.getHeight());
        setVisible(false);
    }
    
    /**
     * Creates a new SuiWindow with an empty title.
     */
    public SuiWindow() {
        this("");
    }
    
    /**
     * Adds the specified listener to the list.
     *
     * @param l the listener to receive events
     */
    public void addKeyListener(SuiKeyListener l) {
        super.addKeyListener(l);
        titlePane.addKeyListener(l);
    }
    
    /**
     * Removes the specified listener from the list.
     *
     * @param l the listener to remove
     */
    public void removeKeyListener(SuiKeyListener l) {
        super.removeKeyListener(l);
        titlePane.removeKeyListener(l);
    }
    
    /**
     * Adds the specified listener to the list.
     *
     * @param l the listener to receive events
     */
    public void addControllerListener(SuiControllerListener l) {
        super.addControllerListener(l);
        titlePane.addControllerListener(l);
    }
    
    /**
     * Removes the specified listener from the list.
     *
     * @param l the listener to remove
     */
    public void removeControllerListener(SuiControllerListener l) {
        super.removeControllerListener(l);
        titlePane.removeControllerListener(l);
    }
    
    /**
     * Adds the specified listener to the list.
     *
     * @param l the listener to receive events
     */
    public void addMouseWheelListener(SuiMouseWheelListener l) {
        super.addMouseWheelListener(l);
        titlePane.addMouseWheelListener(l);
    }
    
    /**
     * Removes the specified listener from the list.
     *
     * @param l the listener to remove
     */
    public void removeMouseWheelListener(SuiMouseWheelListener l) {
        super.removeMouseWheelListener(l);
        titlePane.removeMouseWheelListener(l);
    }
    
    /**
     * Sets the background color for the window's
     * content pane.
     *
     * @param c the content pane color
     */
    public void setBackground(Color c) {
        this.background = c;
    }
    
    /**
     * Gets the background color for the window's
     * content pane.
     *
     * @return the content pane color
     */
    public Color getBackground() {
        return background;
    }
    
    /**
     * Gets the title bar label.
     * <p>
     * Use carefully.
     *
     * @return this window's title bar
     */
    public SuiLabel getTitleBar() {
        return titlePane;
    }
    
    public SuiContainer getResizer() {
        return resizer;
    }
    
    /**
     * Sets the title of this window.
     *
     * @param text the text for this window's title bar
     */
    public void setTitle(String text) {
        titlePane.setText(text);
    }
    
    /**
     * Gets the title of this window.
     *
     * @return this window's title bar text
     */
    public String getTitle() {
        return titlePane.getText();
    }
    
    /**
     * Sets the x position of this SuiContainer, relative
     * to its parent.
     *
     * @param x the x position of this component
     * @see mdes.slick.sui.SuiContainer#setLocation(float, float)
     */
    public void setX(float x) {
        super.setX(x);
        titlePane.setX(x);
    }
    
    /**
     * Sets the y position of this SuiContainer, relative
     * to its parent.
     *
     * @param y the y position of this component
     * @see mdes.slick.sui.SuiContainer#setLocation(float, float)
     */
    public void setY(float y) {
        super.setY(y);
        titlePane.setY(y-titlePane.getHeight());
    }
    
    /**
     * Used internally to render the drop shadow and titlebar properly.
     *
     * @param c The container displaying this component
     * @param g The graphics context used to render to the display
     * @param topLevel <tt>true</tt> if this is a top-level component
     */
    void render(GUIContext c, Graphics g) {
        if (isVisible())
            drawDropShadow(g, shadowWidth);
        g.setClip((int)getAbsoluteX(),
                (int)getAbsoluteY()-titlePane.getHeight()-1,
                getWidth()+1, getHeight()+1+titlePane.getHeight());
        super.render(c, g);
        if (isVisible())
            resizer.render(c, g);
    }
    
    /**
     * Draws a drop shadow for this window.
     *
     * @param width the width
     */
    protected void drawDropShadow(Graphics g, int width) {
        if (width==0)
            return;
        
        float fact = (65f/width)/100f;
        shadow.a = 0f;
        
        //original values
        final int ox = (int)getAbsoluteX();
        final int oy = (int)getAbsoluteY()-titlePane.getHeight();
        final int w = getWidth();
        final int h = getHeight()+titlePane.getHeight();
        
        for (int i=width; i>=1; i--) {
            shadow.a += fact;
            if (shadow.a>1.0f)
                shadow.a = 1.0f;
            
            g.setColor(shadow);
            
            int nx = ox + i;
            int ny = oy + i;
            
            //bottom line (horizontal)
            g.drawLine(nx, ny+h, nx+w, ny+h);
            
            //right line (vertical)
            g.drawLine(nx+w, ny, nx+w, ny+h);
        }
        shadow.a = 1.0f;
    }
    
    /**
     * Called to render the content pane and title bar.
     * SuiWindows wanting custom rendering on content panes
     * should override
     * {@link mdes.slick.sui.SuiWindow#renderContentPane(GUIContext, Graphics)}.
     *
     * @param c the GUIContext we are rendering to
     * @param g the Graphics context we are rendering with
     */
    protected void renderComponent(GUIContext c, Graphics g) {
        renderContentPane(c, g);
        titlePane.render(c, g);
    }
    
    
    /**
     * Called to render the window's content pane. Initially this method
     * fills a rectangle using the background color. Override without
     * calling super for completely custom container rendering.
     *
     * @param c the GUIContext we are rendering to
     * @param g the Graphics context we are rendering with
     */
    protected void renderContentPane(GUIContext c, Graphics g) {
        Color old = g.getColor();
        g.setColor(background);
        g.fillRect(getAbsoluteX(), getAbsoluteY(),
                getWidth(), getHeight());
        g.setColor(old);
    }
    
    /**
     * Called to render this component's border.
     *
     * @param c the GUIContext we are rendering to
     * @param g the Graphics context we are rendering with
     */
    protected void renderBorder(GUIContext c, Graphics g) {
        Color old = g.getColor();
        
        g.setColor(Sui.getTheme().getSecondary3());
        g.drawRect(getAbsoluteX(), getAbsoluteY()-titlePane.getHeight(),
                getWidth(), getHeight()+titlePane.getHeight());
        g.drawLine(getAbsoluteX(), getAbsoluteY(),
                getAbsoluteX()+getWidth(), getAbsoluteY());
        g.setColor(old);
    }
    
    public SuiButton getCloseButton() {
        return closeButton;
    }
    
    public void setWidth(int width) {
        super.setWidth(width);
        titlePane.setWidth(width);
        closeButton.setX(titlePane.getWidth()
        - closeButton.getWidth() - titlePane.getPadding());
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setDraggable(boolean b) {
        draggable = b;
    }
    
    public boolean isDraggable() {
        return draggable;
    }
    
    public int getMinimumWidth() {
        return minWidth;
    }
    
    public int getMinimumHeight() {
        return minHeight;
    }
    
    public void setMinimumWidth(int min) {
        this.minWidth = min;
        //if current width is less than the minimum
        if (getWidth()<minWidth)
            setWidth(minWidth);
    }
    
    public void setMinimumHeight(int min) {
        this.minHeight = min;
        //if current height is less than the minimum
        if (getHeight()<minHeight)
            setHeight(minHeight);
    }
    
    public void setMinimumSize(int width, int height) {
        setMinimumWidth(width);
        setMinimumHeight(height);
    }
    
    public int getMaximumWidth() {
        return maxWidth;
    }
    
    public int getMaximumHeight() {
        return maxHeight;
    }
    
    public void setMaximumWidth(int max) {
        this.maxWidth = max;
        //if width is greater than the max
        if (getWidth()>maxWidth)
            setWidth(maxWidth);
    }
    
    public void setMaximumHeight(int max) {
        this.maxHeight = max;
        //if height is greater than the max
        if (getHeight()>maxHeight)
            setHeight(maxHeight);
    }
    
    public void setMaximumSize(int width, int height) {
        setMaximumWidth(width);
        setMaximumHeight(height);
    }
    
    public void setResizable(boolean b) {
        this.resizable = b;
    }
    
    public boolean isResizable() {
        return resizable;
    }
    
    /** Used internally to determine how to resize the window. */
    protected class WindowResizeListener extends SuiMouseAdapter {
        public void mouseDragged(SuiMouseEvent e) {
            int b = e.getButton();
            int ox = e.getOldX();
            int oy = e.getOldY();
            int nx = e.getX();
            int ny = e.getY();
            
            if (b==SuiMouseEvent.BUTTON1 && resizable) {
                //get absolute mouse coordinates
                int abX = nx+getWidth()-resizer.getWidth();
                int abY = ny+(getHeight())-resizer.getHeight();
                
                if (minWidth==MAX_RESIZE || abX>=minWidth)
                    if (maxWidth==MAX_RESIZE || abX<maxWidth)
                        setWidth(abX);
                if (minHeight==MAX_RESIZE || abY>=minHeight)
                    if (maxHeight==MAX_RESIZE || abY<maxHeight)
                        setHeight(abY);
            }
        }
    }
    
    protected class WindowDragListener extends SuiMouseAdapter {
        
        public void mouseDragged(SuiMouseEvent e) {
            if (!isDraggable())
                return;
            
            int b = e.getButton();
            int ox = e.getOldX();
            int oy = e.getOldY();
            int nx = e.getX();
            int ny = e.getY();
            int absx = e.getAbsoluteX();
            int absy = e.getAbsoluteY();
            
            if (b==SuiMouseEvent.BUTTON1) {
                SuiWindow.this.translate(nx-ox, ny-oy);
            }
        }
    }
}