/*
 * SimpleColorPicker.java
 *
 * Created on December 8, 2007, 5:33 PM
 */

package mdes.slick.sui.skin.simple;

import mdes.slick.sui.*;
import mdes.slick.sui.event.*;
import mdes.slick.sui.skin.*;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author davedes
 */
public class SimpleColorPicker extends SuiContainer {
    
    private SuiLabel svPane;
    private SuiLabel svCursor;
    private SuiLabel huePane;
    private SuiLabel hueCursor;
    
    private SuiLabel colorBox;
    private SuiLabel colorHex;
    
    private Color defaultColor = new Color(255, 0, 0);
    
    private Color hueOutput = new Color(defaultColor);
    private Color selectedColor = new Color(defaultColor);
    
    private float[] hsvCache = new float[] { 1f, 1f, 1f };
    private float hue=0f, sat=1f, val=1f;
    
    private static Image svCursorImg, svPaneImg, hueCursorImg, huePaneImg;
    
    private static final String HEX = "0123456789ABCDEF";
        
    /** Creates a new instance of SimpleColorPicker */
    public SimpleColorPicker() {  
        setOpaque(true);
        setSize(207, 195);
        setPadding(4, 4, 4, 4);
        
        //load images if they aren't already cached
        if (svCursorImg==null)
            svCursorImg = tryImage("res/skin/simple/svCursor.png");
        if (svPaneImg==null)
            svPaneImg = tryImage("res/skin/simple/svPane.png");
        if (hueCursorImg==null)
            hueCursorImg = tryImage("res/skin/simple/hueCursor.png");
        if (huePaneImg==null)
            huePaneImg = tryImage("res/skin/simple/huePane.png");
                
        //sat/val pane
        svPane = new ColorPane(svPaneImg);
        svPane.setOpaque(true);
        svPane.setBackground(hueOutput);
        
        //hue slider
        huePane = new ColorPane(huePaneImg);
        
        SuiMouseListener hueDrag = new HueCursorDrag();
        SuiMouseListener svDrag = new SVCursorDrag();
        
        //draggable cursors
        svCursor = new ColorCursor(svCursorImg);
        svCursor.addMouseListener(svDrag);
        svPane.addMouseListener(svDrag);
                
        hueCursor = new ColorCursor(hueCursorImg);
        hueCursor.addMouseListener(hueDrag);
        huePane.addMouseListener(hueDrag);
        
        //box and hex val
        colorBox = new SuiLabel();
        colorBox.setOpaque(true);
        colorBox.setBackground(getSelectedColor());
        colorBox.setSize(13, 13);
        
        //make it one column extra
        colorHex = new SuiLabel("#FFFFFFF");
        colorHex.setHorizontalAlignment(SuiLabel.LEFT_ALIGNMENT);
        colorHex.pack();
        colorHex.setText("#"+toHex(selectedColor));        
        
        //add components
        add(colorBox);
        add(colorHex);
        add(hueCursor);
        add(huePane);
        add(svPane);
        svPane.add(svCursor);
        
        //setup locations
        Padding pad = getPadding();
        
        colorBox.setLocation(pad.left, pad.top);
        colorHex.setX(colorBox.getX()+colorBox.getWidth()+5);
        colorHex.setY(colorBox.getY()+(colorBox.getHeight()/2f-colorHex.getHeight()/2f));
        svPane.setLocation(colorBox.getX(), colorBox.getY()+colorBox.getHeight()+3);
        svCursor.setLocation(svPane.getWidth()-svCursor.getWidth()/2f, 
                        -svCursor.getWidth()/2f);
        
        huePane.setLocation(svPane.getX()+svPane.getWidth()+8, svPane.getY());
        hueCursor.setX(huePane.getX() + (huePane.getWidth()/2f-hueCursor.getWidth()/2f));
        hueCursor.setY(huePane.getY()-hueCursor.getHeight()/2f+.5f);
    }
    
    public void updateAppearance() {
        super.updateAppearance();
    }
            
    private Image tryImage(String ref) {
        try { return new ImageUIResource(ref); }
        catch (Exception e) { return null; }
    }
    
    public Color getSelectedColor() {
        return selectedColor;
    }
    
    public void setSelectedColor(Color color) {
        if (color==null)
            throw new IllegalArgumentException("color cannot be null");
        if (svCursor==null||hueCursor==null)
            return;
        
        RGBtoHSV(color, hsvCache);
        float hue = hsvCache[0];
        float sat = hsvCache[1];
        float val = hsvCache[2];
        
        //HUE
        float huePercent = hue/360; //0 to 1
        float huePoint = (huePane.getHeight()-hueCursor.getHeight())-hueCursor.getHeight()/2f+.5f;
        hueCursor.setY(huePane.getY()+huePercent*huePoint);
        
        //SAT
        svCursor.setX(sat * svPane.getWidth()-svCursor.getWidth()+svCursor.getWidth()/2f);
        
        //VAL
        float valPoint = svPane.getHeight()-svCursor.getHeight()+svCursor.getHeight()/2f;
        float valTotal = svPane.getHeight();
        valPoint = Math.abs(valTotal-valPoint);
        //svCursor.setY(val * valPoint);
        System.out.println(valPoint);
        
        colorBox.setBackground(color);
        colorHex.setText("#"+toHex(color));
        fireStateChanged();
    }
        
    private boolean isInsideHuePane(float y) {
        float mid = hueCursor.getHeight()/2f;
        return (y > huePane.getY()-mid)&&(y < huePane.getY()+huePane.getHeight()-mid);
    }
    
    private void hueChanged() {
        if (hueCursor != null) {
            float mid = hueCursor.getHeight()/2f;
            float y = hueCursor.getY();
            if (isInsideHuePane(y)) {
                float point = hueCursor.getAbsoluteY()-huePane.getAbsoluteY()+mid;
                float total = huePane.getHeight();

                point = Math.abs(total-point) - .5f;
                hue = point/total * 360;
                
                //convert to rgb color
                hsvCache[0] = hue;
                hsvCache[1] = 1f;
                hsvCache[2] = 1f;
                HSVtoRGB(hsvCache, hueOutput);
            }
        }
        svPane.setBackground(hueOutput);
        
        svChanged();
    }
    
    private void svChanged() {
        if (svCursor != null) {
            float midw = svCursor.getWidth()/2f;
            float midh = svCursor.getHeight()/2f;
            float x = svCursor.getX() + midw;
            float y = svCursor.getY() + midh;
            
            float pointx = svCursor.getAbsoluteX()-svPane.getAbsoluteX();
            float totalx = svPane.getHeight();
            
            float pointy = svCursor.getAbsoluteY()-svPane.getAbsoluteY();
            float totaly = svPane.getHeight();
            pointy = Math.abs(totaly-pointy);
            
            float sat = pointx/totalx;
            float val = pointy/totaly;
            
            //convert to rgb color
            hsvCache[0] = this.hue;
            hsvCache[1] = sat;
            hsvCache[2] = val;
            HSVtoRGB(hsvCache, selectedColor);
        }
        colorBox.setBackground(selectedColor);
        colorHex.setText("#"+toHex(selectedColor));
        fireStateChanged();
    }
    
    public static void HSVtoRGB(float[] hsv, Color rgbOut) {
        //http://www.cs.rit.edu/~ncs/color/t_convert.html
        float h=hsv[0], s=hsv[1], v=hsv[2];
        
        int i;
        float r, g, b;
        float f, p, q, t;
        if (s==0) {
            // achromatic (grey)
            r = g = b = v;
        } else {
            h /= 60; // sector 0 to 5
            i = (int)h;
            f = h - i; // factorial part of h
            p = v * (1 - s);
            q = v * (1 - s * f);
            t = v * (1 - s * (1-f));
            switch (i) {
                case 0:
                    r = v;
                    g = t;
                    b = p;
                    break;
                case 1:
                    r = q;
                    g = v;
                    b = p;
                    break;
                case 2:
                    r = p;
                    g = v;
                    b = t;
                    break;
                case 3:
                    r = p;
                    g = q;
                    b = v;
                    break;
                case 4:
                    r = t;
                    g = p;
                    b = v;
                    break;
                default:
                    r = v;
                    g = p;
                    b = q;
                    break;
            }
        }
        rgbOut.r = r;
        rgbOut.g = g;
        rgbOut.b = b;
    }
    
    public static void RGBtoHSV(Color c, float[] hsv) {
        //http://www.cs.rit.edu/~ncs/color/t_convert.html
        float min, max, delta, h, s, v;
        min = Math.min(c.r, Math.min(c.g, c.b));
        max = Math.max(c.r, Math.max(c.g, c.b));
        v = max;				
        delta = max - min;
        if( max != 0 )
            s = delta / max;
        else {
            // r = g = b = 0
            // s = 0, v is undefined
            s = 0;
            h = -1;
            hsv[0] = h;
            hsv[1] = s;
            hsv[2] = v;
            return;
        }
        if( c.r == max )
            h = ( c.g - c.b ) / delta; // between yellow & magenta
        else if( c.g == max )
            h = 2 + ( c.b - c.r ) / delta;	// between cyan & yellow
        else
            h = 4 + ( c.r - c.g ) / delta;	// between magenta & cyan
        h *= 60;				// degrees
        if( h < 0 )
            h += 360;
        hsv[0] = h;
        hsv[1] = s;
        hsv[2] = v;
    }
        
    //assumes color is opaque
    private static String toHex(Color color) {
        return toHex(color.getRedByte())+toHex(color.getGreenByte())+toHex(color.getBlueByte());
    }
    
    //gets hex of specified byte
    private static String toHex(int b) {
        b = Math.min(Math.max(0, b), 255);
        return HEX.charAt((b-b%16)/16) + "" + HEX.charAt(b%16);
    }
    
    public SuiLabel getSvPane() {
        return svPane;
    }

    public SuiLabel getSvCursor() {
        return svCursor;
    }

    public SuiLabel getHuePane() {
        return huePane;
    }

    public SuiLabel getHueCursor() {
        return hueCursor;
    }

    public SuiLabel getColorBoxLabel() {
        return colorBox;
    }

    public SuiLabel getColorHexLabel() {
        return colorHex;
    }
    
    /**
     * Adds the specified listener to the list.
     *
     * @param s the listener to receive events
     */
    public synchronized void addChangeListener(SuiChangeListener s) {
        listenerList.add(SuiChangeListener.class, s);
    }
    
    /**
     * Removes the specified listener from the list.
     *
     * @param s the listener to remove
     */
    public synchronized void removeChangeListener(SuiChangeListener s) {
        listenerList.remove(SuiChangeListener.class, s);
    }
    
    /**
     * Fires a change event to all action listeners
     * in this component.
     *
     * @see mdes.slick.sui.event.SuiChangeEvent
     */
    protected void fireStateChanged() {
        SuiChangeEvent evt = null;
        
        final SuiChangeListener[] listeners =
                (SuiChangeListener[])listenerList.getListeners(SuiChangeListener.class);
        for (int i=0; i<listeners.length; i++) {
            //lazily create it
            if (evt==null) {
                evt = new SuiChangeEvent(this);
            }
            listeners[i].stateChanged(evt);
        }
    }
    
    protected class SVCursorDrag extends SuiMouseAdapter {
        public void mousePressed(SuiMouseEvent e) {
            process(e);
        }
        
        public void mouseDragged(SuiMouseEvent e) {
            process(e);
        }
        
        private void process(SuiMouseEvent e) {
            float abx = e.getAbsoluteX();
            float aby = e.getAbsoluteY();
            float x = abx - getSvPane().getAbsoluteX();
            float y = aby - getSvPane().getAbsoluteY();
            
            if (getSvPane().contains(abx, aby)) {
                getSvCursor().setLocation(x-getSvCursor().getWidth()/2f, 
                            y-getSvCursor().getHeight()/2f);
                svChanged();
            }
        }
    }
    
    protected class HueCursorDrag extends SuiMouseAdapter {
        
        public void mousePressed(SuiMouseEvent e) {
            process(e);
        }
        
        public void mouseDragged(SuiMouseEvent e) {
            process(e);
        }
        
        private void process(SuiMouseEvent e) {
            float aby = e.getAbsoluteY();
            float ny = aby-getHuePane().getAbsoluteY()+getHuePane().getY();
            
            if (isInsideHuePane(ny)) {
                getHueCursor().setY(ny);    
                hueChanged();
            }
        }
    }
    
    protected class ColorPane extends SuiLabel {
        
        public ColorPane(Image paneImg) {
            super(paneImg);
            setSize(paneImg.getWidth(), paneImg.getHeight());
        }
    }
    
    protected class ColorCursor extends SuiLabel {
        
        public ColorCursor(Image cursorImg) {
            super(cursorImg);
            setOpaque(false);
            setBackground(null);
            setZIndex(SuiComponent.DRAG_LAYER);
            setSize(cursorImg.getWidth(), cursorImg.getHeight());
        }
    }
}
