/*
 * DraggableContainer.java
 *
 * Created on December 9, 2007, 12:07 AM
 */

package mdes.slick.sui;

import mdes.slick.sui.event.SuiMouseAdapter;
import mdes.slick.sui.event.SuiMouseEvent;
import mdes.slick.sui.event.SuiMouseListener;

/**
 *
 * @author davedes
 */
public class DraggableContainer extends SuiContainer {
    
    protected SuiMouseListener dragListener = new DragListener();
    
    /** Creates a new instance of DraggableContainer */
    public DraggableContainer() {
        addMouseListener(dragListener);
    }
    
    public SuiMouseListener getDragListener() {
        return dragListener;
    }
    
    protected class DragListener extends SuiMouseAdapter {
        
        float lastX, lastY;
        
        public void mousePressed(SuiMouseEvent e) {
            lastX = e.getAbsoluteX();
            lastY = e.getAbsoluteY();
        }
        
        public void mouseDragged(SuiMouseEvent e) {
            float abx = e.getAbsoluteX();
            float aby = e.getAbsoluteY();
            
            float x = getX() + abx-lastX;
            float y = getY() + aby-lastY;
            
            setLocation(x, y);
            
            lastX = abx;
            lastY = aby;
        }
    }
}
