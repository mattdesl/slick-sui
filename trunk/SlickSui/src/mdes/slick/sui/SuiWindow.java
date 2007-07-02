/*
 * SuiWindow.java
 *
 * Created on June 14, 2007, 6:06 PM
 */

package mdes.slick.sui;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import mdes.slick.sui.border.LineBorder;
import org.newdawn.slick.*;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.*;
import mdes.slick.sui.event.*;
import org.newdawn.slick.gui.GUIContext;

/**
 * A dialog window which can be moved, resized, and hidden.
 * <p>
 * <code>SuiWindow</code> uses a content pane to hold its children. The following
 * methods make calls to the content pane for convenience: add, remove, setBackground
 * and remove. It's good practice to always call getContentPane() first when dealing 
 * with the window's children.
 * @author davedes
 */
public class SuiWindow extends SuiContainer {
    
    /**
     * the title pane
     */
    private SuiTitleBar titlePane;
    /**
     * the resizer component
     */
    private SuiContainer resizer = new SuiContainer() {
        {
            setSize(8, 8);
            addMouseListener(new WindowResizeListener());
        }
        
        protected void renderComponent(GUIContext c, Graphics g) {
            if (!SuiWindow.this.isResizable())
                return;
            
            SlickCallable.enterSafeBlock();
            Color t = Sui.getTheme().getPrimaryBorder1();
            
            //bind texture & color before entering gl
            t.bind();
            
            float x = getAbsoluteX()-2 , y = getAbsoluteY()-1;
            int w = getWidth(), h = getHeight();
            
            //begin drawing the triangle
            GL11.glBegin(GL11.GL_TRIANGLES);
                GL11.glVertex3f(x+w, y, 0);
                GL11.glVertex3f(x+w, y+h, 0);
                GL11.glVertex3f(x, y+h, 0);
            GL11.glEnd();
            SlickCallable.leaveSafeBlock();
        }
    };
        
    /**
     * the content pane
     */
    private SuiContainer contentPane = new SuiContainer();
    
    //TODO: minimum resize width for title
    //TODO: concact title with "..."
    //TODO: fix absolute Y coord for window
    //TODO: fix background color & change of theme
    
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
    /**
     * the initial height of the titlebar
     */
    private final int TITLEBAR_HEIGHT = 20;
    
    /** Whether this window is resizable. */
    private boolean resizable = true;
    
    /** Whether this window is draggable. */
    private boolean draggable = true;
        
    /** Whether this window is active (ie: one of its children has the focus). */
    private boolean active = false;
    
    /**
     * Creates a new SuiWindow with the specified title.
     *
     * @param title the text to display on the title bar
     */
    public SuiWindow(String title) {
        super();
        
        titlePane = createTitleBar();
        
        setFocusable(true);
        contentPane.setFocusable(true);
        setOpaque(true);
        contentPane.setOpaque(true);
        
        resizer.setZIndex(SuiContainer.DRAG_LAYER);
        setZIndex(SuiContainer.MODAL_LAYER);
        
        titlePane.setText(title);
        titlePane.setForeground(Sui.getTheme().getForeground());
        
        contentPane.setLocation(0,titlePane.getHeight());
        contentPane.setWidth(INITIAL_WIDTH);
        
        super.add(titlePane);
        super.add(contentPane);
        super.add(resizer);
        
        setMinimumSize(100, resizer.getHeight()+10);
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
    
    protected SuiTitleBar createTitleBar() {
        return new SuiTitleBar();
    }
    
    public void setFont(Font f) {
        titlePane.setFont(f);
        super.setFont(f);
    }
    
    public Font getFont() {
        return titlePane.getFont();
    }
    
    void renderTree(GUIContext container, Graphics g) {
        renderComponent(container, g);
        renderChildren(container, g);
        renderBorder(container, g);
    }
    
    //TODO: @URGENT fix isVisible with windows
            //use a system like isClipEnabled instead
    
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
        contentPane.setBackground(c);
    }
    
    /**
     * Gets the background color for the window's
     * content pane.
     *
     * @return the content pane color
     */
    public Color getBackground() {
        return contentPane.getBackground();
    }
    
    /**
     * Gets the title bar label.
     * <p>
     * Use carefully.
     *
     * @return this window's title bar
     */
    public SuiTitleBar getTitleBar() {
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
     * Adds a child to this frame's content pane.
     *
     * @param child the child container to add
     */
    public void add(SuiContainer child) {
        contentPane.add(child);
    }
    
    /**
     * Inserts a child to this SuiContainer at the specified index.
     *
     * @param child the child container to add
     * @param index the index to insert it to
     */
    public void add(SuiContainer child, int index) {
        contentPane.add(child, index);
    }
    
    /**
     * Removes the child from this SuiContainer if it exists.
     *
     * @param child the child container to remove
     * @return <tt>true</tt> if the child was removed
     */
    public boolean remove(SuiContainer child) {
        return contentPane.remove(child);
    }
    
                
    public SuiButton getCloseButton() {
        return titlePane.getCloseButton();
    }
    
    public void setWidth(int width) {
        super.setWidth(width);
        titlePane.setWidth(width);
        contentPane.setWidth(width);
        resizer.setX(width - resizer.getWidth());
    }
    
    public void setHeight(int height) {
        super.setHeight(height);
        contentPane.setHeight(Math.max(0, height-titlePane.getHeight()));
        resizer.setY(height - resizer.getHeight()-1);
    }
    
    public boolean isActive() {
        return active && isVisible();
    }
    
    public SuiContainer getContentPane() {
        return contentPane;
    }
    
    public void setContentPane(SuiContainer cp) {
        this.contentPane = cp;
        contentPane.setFocusable(true);
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
    
    /** Called on creation. Initially does nothing. */
    public void updateUI(SuiSkin skin) {
        setUI(skin.getWindowUI());
    }
        
    void setActive(boolean active) {
        this.active = active;
        if (active)
            setZIndex(SuiLabel.MODAL_LAYER+1);
        else
            setZIndex(SuiLabel.MODAL_LAYER);
    }
       
    //TODO: setSize w/ min/max
    
    public SuiSkin.WindowUI getUI() {
        return (SuiSkin.WindowUI)super.getUI();
    }
        
    public class SuiTitleBar extends SuiLabel {
        
        protected SuiButton closeButton = new SuiButton("x");
        
        public SuiTitleBar() {
            super();
            
            //set up title bar, which is a label
            setPadding(5);
            setHorizontalAlignment(SuiLabel.LEFT_ALIGNMENT);
            setLocation(0, 0);
            setHeight(TITLEBAR_HEIGHT);
            
            setWidth(INITIAL_WIDTH);
            
            addMouseListener(new WindowDragListener());
            
            //set up close button
            closeButton.setSkinEnabled(false);
            closeButton.setToolTipText("Close");
            closeButton.setVerticalPadding(3);
            closeButton.setHorizontalPadding(3);
            closeButton.pack();
            closeButton.setY( this.getHeight()/2f - closeButton.getHeight()/2f );
            
            closeButton.addActionListener(new SuiActionListener() {
                public void actionPerformed(SuiActionEvent e) {
                    SuiWindow.this.setVisible(false);
                }
            });

            //add close button to titlepane
            add(closeButton);
        }
        
        /** Called on creation. Initially does nothing. */
        public void updateUI(SuiSkin skin) {
            setUI(skin.getTitleBarUI());
        }

        public SuiSkin.TitleBarUI getUI() {
            return (SuiSkin.TitleBarUI)super.getUI();
        }
                
        public void setWidth(int width) {
            super.setWidth(width);
            closeButton.setX(getWidth() - closeButton.getWidth() 
                        - getHorizontalPadding());
        }
        
        public SuiButton getCloseButton() {
            return closeButton;
        }
        
        public void setCloseButton(SuiButton b) {
            this.closeButton = b;
        }
    }
    
    /** Used internally to determine how to resize the window. */
    private class WindowResizeListener extends SuiMouseAdapter {
        public void mouseDragged(SuiMouseEvent e) {
            if (!resizable)
                return;
            
            int b = e.getButton();
            int ox = e.getOldX();
            int oy = e.getOldY();
            int nx = e.getX();
            int ny = e.getY();
            
            if (b==SuiMouseEvent.BUTTON1) {
                int abX = e.getAbsoluteX() - (int)getAbsoluteX();
                int abY = e.getAbsoluteY() - (int)getAbsoluteY();
                
                if (minWidth==MAX_RESIZE || abX>=minWidth)
                    if (maxWidth==MAX_RESIZE || abX<maxWidth)
                        setWidth(abX);
                if (minHeight==MAX_RESIZE || abY-titlePane.getHeight()>=minHeight)
                    if (maxHeight==MAX_RESIZE || abY-titlePane.getHeight()<maxHeight)
                        setHeight(abY);
            }
        }
    }
    
    private class WindowDragListener extends SuiMouseAdapter {
        
        public void mouseDragged(SuiMouseEvent e) {
            if (!draggable)
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
