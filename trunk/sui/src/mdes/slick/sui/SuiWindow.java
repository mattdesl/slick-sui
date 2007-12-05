/*
 * SuiWindow.java
 *
 * Created on June 14, 2007, 6:06 PM
 */

package mdes.slick.sui;

import mdes.slick.sui.event.SuiActionEvent;
import mdes.slick.sui.event.SuiActionListener;
import mdes.slick.sui.event.SuiControllerListener;
import mdes.slick.sui.event.SuiKeyListener;
import mdes.slick.sui.event.SuiMouseAdapter;
import mdes.slick.sui.event.SuiMouseEvent;
import mdes.slick.sui.event.SuiMouseWheelListener;
import mdes.slick.sui.skin.ComponentAppearance;
import mdes.slick.sui.skin.WindowAppearance;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

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
    private TitleBar titleBar;
    /**
     * the resizer component
     */
    private Resizer resizer = new Resizer(this);
        
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
    private float minWidth;
    
    /** The minimum height for resizing. */
    private float minHeight;
    
    /** The maximum width for resizing. */
    private float maxWidth;
    
    /** The minimum height for resizing. */
    private float maxHeight;
    
    /** Used internally, the initial width of the dialog. */
    private static final int INITIAL_WIDTH = 200;
    
    /**
     * the initial height of the titlebar
     */
    private static final float TITLE_BAR_HEIGHT = 23;
    
    /** Whether this window is resizable. */
    private boolean resizable = true;
    
    /** Whether this window is draggable. */
    private boolean draggable = true;
        
    /** Whether this window is active (ie: one of its children has the focus). */
    private boolean active = false;
    
    //TODO: use padding for content pane insets
    private Padding padding = new Padding();
    
    private boolean rootCheck = true;
    
    
    /**
     * Creates a new SuiWindow with the specified title.
     *
     * @param title the text to display on the title bar
     */
    public SuiWindow(String title) {
        super(false);
        updateAppearance();
        
        titleBar = new TitleBar(this);
        
        setFocusable(true);
        contentPane.setFocusable(true);
        setOpaque(true);
        contentPane.setOpaque(true);
        contentPane.setBackground(Sui.getTheme().getBackground());
        
        resizer.setZIndex(SuiContainer.DRAG_LAYER);
        setZIndex(SuiContainer.MODAL_LAYER);
        
        titleBar.setText(title);
        titleBar.setX(0);
        titleBar.setForeground(Sui.getTheme().getForeground());
        
        int lineHeight = getFont()!=null ? getFont().getLineHeight() : 0;
        titleBar.setHeight(Math.max(lineHeight, TITLE_BAR_HEIGHT));
        
        contentPane.setLocation(1, titleBar.getHeight()-1);
        contentPane.setWidth(INITIAL_WIDTH);
        
        super.add(titleBar);
        super.add(contentPane);
        super.add(resizer);
        
        setMinimumSize(100, resizer.getHeight()+10);
        setMaximumSize(MAX_RESIZE, MAX_RESIZE);
        
        setSize(INITIAL_WIDTH, titleBar.getHeight());
        //setVisible(false);
    }
    
    /**
     * Creates a new SuiWindow with an empty title.
     */
    public SuiWindow() {
        this("");
    }
    
    
    /**
     * Allows users to add/remove components directly to the window's
     * container. Set to <tt>true</tt> if we wish to forward calls to the
     * content pane, false if we wish the calls to directly affect this container. 
     * Calls that are affected are add, remove, setBackground, getBackground.
     *
     * @param enabled <tt>true</tt> if we wish to forward calls to the content
     *      pane (the default)
     */
    public void setRootPaneCheckingEnabled(boolean enabled) {
        this.rootCheck = enabled;
    }
    
    public boolean isRootPaneCheckingEnabled() {
        return rootCheck;
    }
    
    public void updateAppearance() {
        setAppearance(Sui.getSkin().getWindowAppearance(this));
        
        //update extra components attached to this
        if (titleBar!=null) {
            titleBar.updateAppearance();
            if (titleBar.getCloseButton()!=null)
                titleBar.getCloseButton().updateAppearance();
        }
        if (resizer!=null)
            resizer.updateAppearance();
    }
    
    /**
     * Gets the current appearance for this window component.
     *
     * @return the scroll bar appearance set for this component
     * 1.5 feature
    public WindowAppearance getAppearance() {
        return (WindowAppearance)super.getAppearance();
    }*/
        
    /**
     * Sets the appearance for this slider. If <code>appearance</code> is
     * not an instance of WindowAppearance, an 
     * <code>IllegalArgumentException</code> is thrown.
     *
     * @param appearance the new appearance to set
     */
    public void setAppearance(ComponentAppearance appearance) {
        if (!(appearance instanceof WindowAppearance))
            throw new IllegalArgumentException("must pass instance of window appearance");
        super.setAppearance(appearance);
    }
                
    //TODO: rootpane checking so client can access actual component (rather than just content pane)
    //TODO: @URGENT fix isVisible with windows
            //use a system like isClipEnabled instead
    
    /**
     * Adds the specified listener to the list.
     *
     * @param l the listener to receive events
     */
    public void addKeyListener(SuiKeyListener l) {
        super.addKeyListener(l);
        titleBar.addKeyListener(l);
    }
    
    /**
     * Removes the specified listener from the list.
     *
     * @param l the listener to remove
     */
    public void removeKeyListener(SuiKeyListener l) {
        super.removeKeyListener(l);
        titleBar.removeKeyListener(l);
    }
    
    /**
     * Adds the specified listener to the list.
     *
     * @param l the listener to receive events
     */
    public void addControllerListener(SuiControllerListener l) {
        super.addControllerListener(l);
        titleBar.addControllerListener(l);
    }
    
    /**
     * Removes the specified listener from the list.
     *
     * @param l the listener to remove
     */
    public void removeControllerListener(SuiControllerListener l) {
        super.removeControllerListener(l);
        titleBar.removeControllerListener(l);
    }
    
    /**
     * Adds the specified listener to the list.
     *
     * @param l the listener to receive events
     */
    public void addMouseWheelListener(SuiMouseWheelListener l) {
        super.addMouseWheelListener(l);
        titleBar.addMouseWheelListener(l);
    }
    
    /**
     * Removes the specified listener from the list.
     *
     * @param l the listener to remove
     */
    public void removeMouseWheelListener(SuiMouseWheelListener l) {
        super.removeMouseWheelListener(l);
        titleBar.removeMouseWheelListener(l);
    }
    
    /**
     * Sets the background color for the window's
     * content pane.
     *
     * @param c the content pane color
     */
    public void setBackground(Color c) {
        if (rootCheck)
            contentPane.setBackground(c);
        else
            super.setBackground(c);
    }
    
    /**
     * Gets the background color for the window's
     * content pane.
     *
     * @return the content pane color
     */
    public Color getBackground() {
        if (rootCheck)
            return contentPane.getBackground();
        else
            return super.getBackground();
    }
    
    /**
     * Gets the title bar label.
     * <p>
     * Use carefully.
     *
     * @return this window's title bar
     */
    public TitleBar getTitleBar() {
        return titleBar;
    }
    
    public Resizer getResizer() {
        return resizer;
    }
    
    public void setTitleBar(TitleBar tb) {
        if (tb==null)
            throw new IllegalArgumentException("tb cannot be null");
        this.titleBar = tb;
    }
        
    /**
     * Sets the title of this window.
     *
     * @param text the text for this window's title bar
     */
    public void setTitle(String text) {
        titleBar.setText(text);
    }
    
    /**
     * Gets the title of this window.
     *
     * @return this window's title bar text
     */
    public String getTitle() {
        return titleBar.getText();
    }
    
    /**
     * Adds a child to this frame's content pane.
     *
     * @param child the child container to add
     */
    public void add(SuiComponent child) {
        if (rootCheck)
            contentPane.add(child);
        else
            super.add(child);
    }
    
    /**
     * Inserts a child to this SuiContainer at the specified index.
     *
     * @param child the child container to add
     * @param index the index to insert it to
     */
    public void add(SuiComponent child, int index) {
        if (rootCheck)
            contentPane.add(child, index);
        else
            super.add(child, index);
    }
    
    /**
     * Removes the child from this SuiContainer if it exists.
     *
     * @param child the child container to remove
     * @return <tt>true</tt> if the child was removed
     */
    public boolean remove(SuiComponent child) {
        if (rootCheck)
            return contentPane.remove(child);
        else
            return super.remove(child);
    }
        
    public SuiButton getCloseButton() {
        return titleBar.getCloseButton();
    }
    
    public Dimension getMinimumSize() {
        return new Dimension(minWidth, minHeight);
    }
    
    public Dimension getMaximumSize() {
        return new Dimension(maxWidth, maxHeight);
    }
    
    public void setWidth(float width) {
        super.setWidth(width);
        titleBar.setWidth(width);
        contentPane.setWidth(width-1);
        updateResizerX();
    }
    
    public void setHeight(float height) {
        super.setHeight(height);
        contentPane.setHeight(Math.max(0, height-titleBar.getHeight()));
        updateResizerY();
    }
    
    void updateResizerY() {
        if (resizer!=null)
            resizer.setY(getHeight() - resizer.getHeight()-2);
    }
    
    void updateResizerX() {
        if (resizer!=null)
            resizer.setX(getWidth() - resizer.getWidth()-1);
    }
    
    public boolean isActive() {
        return active && isVisible();
    }
    
    public SuiContainer getContentPane() {
        return contentPane;
    }
    
    public void setContentPane(SuiContainer cp) {
        //TODO: null check
        this.contentPane = cp;
        contentPane.setFocusable(true);
    }
    
    public void setDraggable(boolean b) {
        draggable = b;
    }
    
    public boolean isDraggable() {
        return draggable;
    }
    
    public float getMinimumWidth() {
        return minWidth;
    }
    
    public float getMinimumHeight() {
        return minHeight;
    }
    
    public void setMinimumWidth(float min) {
        this.minWidth = min;
        //if current width is less than the minimum
        if (getWidth()<minWidth)
            setWidth(minWidth);
    }
    
    public void setMinimumHeight(float min) {
        this.minHeight = min;
        //if current height is less than the minimum
        if (getHeight()<minHeight)
            setHeight(minHeight);
    }
    
    public void setMinimumSize(float width, float height) {
        setMinimumWidth(width);
        setMinimumHeight(height);
    }
    
    public float getMaximumWidth() {
        return maxWidth;
    }
    
    public float getMaximumHeight() {
        return maxHeight;
    }
    
    public void setMaximumWidth(float max) {
        this.maxWidth = max;
        //if width is greater than the max
        if (getWidth()>maxWidth)
            setWidth(maxWidth);
    }
    
    public void setMaximumHeight(float max) {
        this.maxHeight = max;
        //if height is greater than the max
        if (getHeight()>maxHeight)
            setHeight(maxHeight);
    }
    
    public void setMaximumSize(float width, float height) {
        setMaximumWidth(width);
        setMaximumHeight(height);
    }
    
    public void setWindowIcon(Image icon) {
        this.titleBar.setWindowIcon(icon);
    }
    
    public Image getWindowIcon() {
        return titleBar.getWindowIcon();
    }
    
    public void setResizable(boolean b) {
        this.resizable = b;
    }
    
    public boolean isResizable() {
        return resizable;
    }
    
    void setActive(boolean active) {
        this.active = active;
        if (active)
            setZIndex(SuiLabel.MODAL_LAYER+1);
        else
            setZIndex(SuiLabel.MODAL_LAYER);
    }
           
    //TODO: setSize w/ min/max
    
    public static class TitleBar extends SuiLabel {
        
        protected CloseButton closeButton;
        protected float leftPadding = 5f;
                
        protected SuiLabel windowIcon = new SuiLabel() {
            public void setWidth(float width) {
                super.setWidth(width);
                TitleBar.this.updateWindowIcon();
            }
            public void setHeight(float height) {
                super.setHeight(height);
                TitleBar.this.updateWindowIcon();
            }
        };
        
        private SuiWindow window;
        
        public TitleBar(SuiWindow window) {
            super(false);
            this.window = window;
            updateAppearance();
            
            closeButton = new CloseButton(window);
            
            //set up title bar, which is a label
            setPadding(leftPadding);
            getPadding().top = 4;
            getPadding().bottom = 3;
            setHorizontalAlignment(SuiLabel.LEFT_ALIGNMENT);
            setLocation(0, 0);
            setHeight(SuiWindow.TITLE_BAR_HEIGHT);
            
            setWidth(SuiWindow.INITIAL_WIDTH);
            
            addMouseListener(new SuiWindow.WindowDragListener(window));
            
            windowIcon.setVisible(false);
            windowIcon.getPadding().bottom = 1;
            
            //set up close button
            closeButton.setToolTipText("Close");
            closeButton.setPadding(4);
            closeButton.setSize(15,15);
            this.updateCloseButtonLocation();
                
            closeButton.addActionListener(new SuiActionListener() {
                public void actionPerformed(SuiActionEvent e) {
                    TitleBar.this.window.setVisible(false);
                }
            });

            //add icon
            add(windowIcon);
            //add close button to titlepane
            add(closeButton);
        }
        
        public void updateAppearance() {
            WindowAppearance a = (WindowAppearance)window.getAppearance();
            if (a!=null)
                setAppearance(a.getTitleBarAppearance(this));
        }
                        
        public void setWidth(float width) {
            super.setWidth(width);
            updateCloseButtonLocation();
        }
        
        public void setHeight(float height) {
            super.setHeight(height);
            updateCloseButtonLocation();
        }
        
        void updateWindowIcon() {
            if (windowIcon.getImage()!=null) {
                getPadding().left = windowIcon.getWidth()+leftPadding;
                windowIcon.setX(2);
                windowIcon.setY( this.getHeight()/2f - windowIcon.getHeight()/2f );
                windowIcon.setVisible(true);
            } else {
                getPadding().left = leftPadding;
                windowIcon.setVisible(false);
            }
        }
        
        public void setWindowIcon(Image image) {
            windowIcon.setImage(image);
            windowIcon.getPadding().left = 2;
            windowIcon.pack();
        }
        
        public Image getWindowIcon() {
            return windowIcon.getImage();
        }
        
        void updateCloseButtonLocation() {
            closeButton.setX(getWidth() - closeButton.getWidth() - getPadding().right);
            closeButton.setY( this.getHeight()/2f - closeButton.getHeight()/2f );
        }
                
        public CloseButton getCloseButton() {
            return closeButton;
        }
        
        public SuiWindow getWindow() {
            return this.window;
        }
        
        public void setCloseButton(CloseButton b) {
            this.closeButton = b;
        }
    }
    
    public static class Resizer extends SuiLabel {
        
        private SuiWindow window;
        
        public Resizer(SuiWindow window) {
            super(false);
            this.window = window;
            updateAppearance();
            setSize(8, 8);
            addMouseListener(new WindowResizeListener(window));
        }
        
        public void updateAppearance() {
            WindowAppearance a = (WindowAppearance)window.getAppearance();
            if (a!=null)
                setAppearance(a.getResizerAppearance(this));
        }
        
        public SuiWindow getWindow() {
            return window;
        }
        
        public void setWidth(float width) {
            super.setWidth(width);
            getWindow().updateResizerX();
        }
        
        public void setHeight(float height) {
            super.setHeight(height);
            getWindow().updateResizerY();
        }
    }
    
    public static class CloseButton extends SuiButton {
        
        private SuiWindow window;
        
        public CloseButton(SuiWindow window) {
            super(false);
            this.window = window;
            updateAppearance();
        }
        
        public void updateAppearance() {
            WindowAppearance a = (WindowAppearance)window.getAppearance();
            if (a!=null)
                setAppearance(a.getCloseButtonAppearance(this));
        }
        
        public SuiWindow getWindow() {
            return window;
        }
        
        public TitleBar getTitleBar() {
            return window.getTitleBar();
        }
        
        public void setWidth(float width) {
            super.setWidth(width);
            if (getTitleBar()!=null)
                getTitleBar().updateCloseButtonLocation();
        }
        
        public void setHeight(float height) {
            super.setHeight(height);
            if (getTitleBar()!=null)
                getTitleBar().updateCloseButtonLocation();
        }
    }
    
    /** Used internally to determine how to resize the window. */
    private static class WindowResizeListener extends SuiMouseAdapter {
        
        private SuiWindow window;
        
        public WindowResizeListener(SuiWindow window) {
            this.window = window;
        }
        
        public void mouseDragged(SuiMouseEvent e) {
            if (!window.isResizable())
                return;
            
            int b = e.getButton();
            int ox = e.getOldX();
            int oy = e.getOldY();
            int nx = e.getX();
            int ny = e.getY();
            
            
            if (b==SuiMouseEvent.BUTTON1) {
                int abX = e.getAbsoluteX() - (int)window.getAbsoluteX();
                int abY = e.getAbsoluteY() - (int)window.getAbsoluteY();
                SuiWindow.TitleBar titleBar = window.getTitleBar();
                float minWidth = window.getMinimumWidth();
                float minHeight = window.getMinimumHeight();
                float maxWidth = window.getMaximumWidth();
                float maxHeight = window.getMaximumHeight();
                        
                if (minWidth==MAX_RESIZE || abX>=minWidth)
                    if (maxWidth==MAX_RESIZE || abX<maxWidth)
                        window.setWidth(abX);
                if (minHeight==MAX_RESIZE || abY-titleBar.getHeight()>=minHeight)
                    if (maxHeight==MAX_RESIZE || abY-titleBar.getHeight()<maxHeight)
                        window.setHeight(abY);
            }
        }
    }
    
    private static class WindowDragListener extends SuiMouseAdapter {
        
        private SuiWindow window;
        
        public WindowDragListener(SuiWindow window) {
            this.window = window;
        }
        
        public void mouseDragged(SuiMouseEvent e) {
            if (!window.isDraggable())
                return;
            
            int b = e.getButton();
            int ox = e.getOldX();
            int oy = e.getOldY();
            int nx = e.getX();
            int ny = e.getY();
            int absx = e.getAbsoluteX();
            int absy = e.getAbsoluteY();
            
            if (b==SuiMouseEvent.BUTTON1) {
                window.translate(nx-ox, ny-oy);
            }
        }
    }
}
