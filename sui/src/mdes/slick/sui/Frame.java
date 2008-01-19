/*
 * Frame.java
 *
 * Created on June 14, 2007, 6:06 PM
 */

package mdes.slick.sui;

import mdes.slick.sui.event.ActionEvent;
import mdes.slick.sui.event.ActionListener;
import mdes.slick.sui.event.ControllerListener;
import mdes.slick.sui.event.KeyListener;
import mdes.slick.sui.event.MouseAdapter;
import mdes.slick.sui.event.MouseEvent;
import mdes.slick.sui.event.MouseWheelListener;
import mdes.slick.sui.layout.LayoutManager;
import mdes.slick.sui.skin.ComponentAppearance;
import mdes.slick.sui.skin.FrameAppearance;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

/**
 * A dialog window which can be moved, resized, and hidden.
 * <p>
 * <code>Frame</code> uses a content pane to hold its children. The following
 * methods make calls to the content pane for convenience: add, remove, setLayout, 
 * setBackground and remove. It's good practice to always call getContentPane() first 
 * when dealing with the window's children.
 * 
 * @author davedes
 */
public class Frame extends Window {
    
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
    private Container contentPane = new Container();
    
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
            
    //TODO: use padding for content pane insets
    private Padding padding = new Padding();
    
    private boolean rootCheck = true;
    
    /**
     * Creates a new Frame with the specified title.
     * 
     * 
     * @param title the text to display on the title bar
     */
    public Frame(String title) {
        super(false);
        updateAppearance();
        
        titleBar = new TitleBar(this);
        
        setFocusable(true);
        contentPane.setFocusable(true);
        setOpaque(true);
        contentPane.setOpaque(true);
        contentPane.setBackground(Sui.getTheme().getBackground());
        
        resizer.setZIndex(Container.DRAG_LAYER);
        setZIndex(Container.MODAL_LAYER);
        
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
     * Creates a new Frame with an empty title.
     */
    public Frame() {
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
        setAppearance(Sui.getSkin().getFrameAppearance(this));
        
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
     * Gets the current appearance for this frame component.
     *
     * @return the scroll bar appearance set for this component
     * 1.5 feature
    public FrameAppearance getAppearance() {
        return (FrameAppearance)super.getAppearance();
    }*/
        
    /**
     * Sets the appearance for this slider. If <code>appearance</code> is
     * not an instance of FrameAppearance, an 
     * <code>IllegalArgumentException</code> is thrown.
     * 
     * 
     * @param appearance the new appearance to set
     */
    public void setAppearance(ComponentAppearance appearance) {
        if (!(appearance instanceof FrameAppearance))
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
    public void addKeyListener(KeyListener l) {
        super.addKeyListener(l);
        titleBar.addKeyListener(l);
    }
    
    /**
     * Removes the specified listener from the list.
     *
     * @param l the listener to remove
     */
    public void removeKeyListener(KeyListener l) {
        super.removeKeyListener(l);
        titleBar.removeKeyListener(l);
    }
    
    /**
     * Adds the specified listener to the list.
     *
     * @param l the listener to receive events
     */
    public void addControllerListener(ControllerListener l) {
        super.addControllerListener(l);
        titleBar.addControllerListener(l);
    }
    
    /**
     * Removes the specified listener from the list.
     *
     * @param l the listener to remove
     */
    public void removeControllerListener(ControllerListener l) {
        super.removeControllerListener(l);
        titleBar.removeControllerListener(l);
    }
    
    /**
     * Adds the specified listener to the list.
     *
     * @param l the listener to receive events
     */
    public void addMouseWheelListener(MouseWheelListener l) {
        super.addMouseWheelListener(l);
        titleBar.addMouseWheelListener(l);
    }
    
    /**
     * Removes the specified listener from the list.
     *
     * @param l the listener to remove
     */
    public void removeMouseWheelListener(MouseWheelListener l) {
        super.removeMouseWheelListener(l);
        titleBar.removeMouseWheelListener(l);
    }
    
    public void setLayout(LayoutManager l) {
        if(rootCheck)
            contentPane.setLayout(l);
        else
            super.setLayout(l);
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
    public boolean add(Component child) {
        if (rootCheck)
            return contentPane.add(child);
        else
            return super.add(child);
    }
    
    /**
     * Inserts a child to this Container at the specified index.
     * 
     * 
     * @param child the child container to add
     * @param index the index to insert it to
     */
    public boolean add(Component child, int index) {
        if (rootCheck)
            return contentPane.add(child, index);
        else
            return super.add(child, index);
    }
    
    /**
     * Removes the child from this Container if it exists.
     * 
     * 
     * @param child the child container to remove
     * @return <tt>true</tt> if the child was removed
     */
    public boolean remove(Component child) {
        if (rootCheck)
            return contentPane.remove(child);
        else
            return super.remove(child);
    }
        
    public Button getCloseButton() {
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
        contentPane.layout.doLayout(contentPane);
        updateResizerX();
    }
    
    public void setHeight(float height) {
        super.setHeight(height);
        contentPane.setHeight(Math.max(0, height-titleBar.getHeight()));
        contentPane.layout.doLayout(contentPane);
        updateResizerY();
    }
    
    void updateResizerY() {
        //HACK: get rid of offset hacks in updateResizerX, updateResizerY and setWidth 
        if (resizer!=null)
            resizer.setY(getHeight() - resizer.getHeight()-2);
    }
    
    void updateResizerX() {
        if (resizer!=null)
            resizer.setX(getWidth() - resizer.getWidth()-1);
    }
        
    public Container getContentPane() {
        return contentPane;
    }
    
    public void setContentPane(Container cp) {
        if (cp==null)
            throw new IllegalArgumentException("Can't have a null content pane!");
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
                   
    //TODO: setSize w/ min/max
    
    public static class TitleBar extends Label {
        
        protected CloseButton closeButton;
        protected float leftPadding = 5f;
                
        protected Label windowIcon = new Label() {
            public void setWidth(float width) {
                super.setWidth(width);
                TitleBar.this.updateWindowIcon();
            }
            public void setHeight(float height) {
                super.setHeight(height);
                TitleBar.this.updateWindowIcon();
            }
        };
        
        private Frame window;
        
        public TitleBar(Frame window) {
            super(false);
            this.window = window;
            updateAppearance();
            
            closeButton = new CloseButton(window);
            
            //set up title bar, which is a label
            setPadding(leftPadding);
            getPadding().top = 4;
            getPadding().bottom = 3;
            setHorizontalAlignment(Label.LEFT_ALIGNMENT);
            setLocation(0, 0);
            setHeight(Frame.TITLE_BAR_HEIGHT);
            
            setWidth(Frame.INITIAL_WIDTH);
            
            addMouseListener(new Frame.WindowDragListener(window));
            
            windowIcon.setVisible(false);
            windowIcon.getPadding().bottom = 1;
            
            //set up close button
            closeButton.setToolTipText("Close");
            closeButton.setPadding(4);
            closeButton.setSize(15,15);
            this.updateCloseButtonLocation();
                
            closeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    TitleBar.this.window.setVisible(false);
                }
            });

            //add icon
            add(windowIcon);
            //add close button to titlepane
            add(closeButton);
        }
        
        /**
         * 
         * Overridden to return the same value as the attached Frame's
         * isConsumingEvents method.
         * 
         * @return getWindow().isConsumingEvents()
         */
        protected boolean isConsumingEvents() {
            return window.isConsumingEvents();
        }
        
        public void updateAppearance() {
            FrameAppearance a = (FrameAppearance)window.getAppearance();
            if (a!=null)
                setAppearance(a.getTitleBarAppearance(this));
        }
        
        public void setVisible(boolean b) {
            if (isVisible() && !b) {
                
            }
            super.setVisible(b);
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
        
        public Frame getWindow() {
            return this.window;
        }
        
        public void setCloseButton(CloseButton b) {
            this.closeButton = b;
        }
       
        public boolean isBorderRendered() {
            return getWindow() != null 
                        ? getWindow().isBorderRendered()
                        : super.isBorderRendered();
        }
    }
    
    public static class Resizer extends Label {
        
        private Frame window;
        
        public Resizer(Frame window) {
            super(false);
            this.window = window;
            updateAppearance();
            setSize(8, 8);
            addMouseListener(new WindowResizeListener(window));
        }
        
        /**
         * 
         * Overridden to return the same value as the attached Frame's
         * isConsumingEvents method.
         * 
         * @return getWindow().isConsumingEvents()
         */
        protected boolean isConsumingEvents() {
            return window.isConsumingEvents();
        }
        
        public void updateAppearance() {
            FrameAppearance a = (FrameAppearance)window.getAppearance();
            if (a!=null)
                setAppearance(a.getResizerAppearance(this));
        }
        
        public Frame getWindow() {
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
    
    public static class CloseButton extends Button {
        
        private Frame window;
        
        public CloseButton(Frame window) {
            super(false);
            this.window = window;
            updateAppearance();
        }
        
        public void updateAppearance() {
            FrameAppearance a = (FrameAppearance)window.getAppearance();
            if (a!=null)
                setAppearance(a.getCloseButtonAppearance(this));
        }
        
        public Frame getWindow() {
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
    private static class WindowResizeListener extends MouseAdapter {
        
        private Frame window;
        
        public WindowResizeListener(Frame window) {
            this.window = window;
        }
        
        public void mouseDragged(MouseEvent e) {
            if (!window.isResizable())
                return;
            
            int b = e.getButton();
            int ox = e.getOldX();
            int oy = e.getOldY();
            int nx = e.getX();
            int ny = e.getY();
            
            
            if (b==MouseEvent.BUTTON1) {
                int abX = e.getAbsoluteX() - (int)window.getAbsoluteX();
                int abY = e.getAbsoluteY() - (int)window.getAbsoluteY();
                Frame.TitleBar titleBar = window.getTitleBar();
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
    
    private static class WindowDragListener extends MouseAdapter {
        
        private Frame window;
        
        public WindowDragListener(Frame window) {
            this.window = window;
        }
        
        public void mouseDragged(MouseEvent e) {
            if (!window.isDraggable())
                return;
            
            int b = e.getButton();
            int ox = e.getOldX();
            int oy = e.getOldY();
            int nx = e.getX();
            int ny = e.getY();
            int absx = e.getAbsoluteX();
            int absy = e.getAbsoluteY();
            
            if (b==MouseEvent.BUTTON1) {
                window.translate(nx-ox, ny-oy);
            }
        }
    }
}
