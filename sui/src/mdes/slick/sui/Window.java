/*
 * Window.java
 *
 * Created on January 2, 2008, 2:58 PM
 */

package mdes.slick.sui;

/**
 *
 * @author davedes
 */
public class Window extends Container {
    
    private boolean active = false;
    private boolean alwaysOnTop = false;
    
    public Window() {
        this(true);
    }
    
    protected Window(boolean updateAppearance) {
        super(false);
        setZIndex(getDefaultLayer());
        
        if (updateAppearance)
            updateAppearance();
    }
    
    public void updateAppearance() {
        setAppearance(Sui.getSkin().getWindowAppearance(this));
    }
    
    protected void setActive(boolean active) {
        this.active = active;
        int z = getDefaultLayer();
        if (active)
            z++;
        if (isAlwaysOnTop())
            z+=2;
        setZIndex(z);
    }
    
    public boolean isActive() {
        return active;
    }
    
    public boolean isAlwaysOnTop() {
        return alwaysOnTop;
    }
    
    public void setAlwaysOnTop(boolean alwaysOnTop) {
        this.alwaysOnTop = alwaysOnTop;
        if (alwaysOnTop)
            setZIndex(getDefaultLayer()+2);
        else
            setZIndex(getDefaultLayer());
    }
    
    protected int getDefaultLayer() {
        return MODAL_LAYER;
    }
}
