/*
 * DraggableContainer.java
 *
 * Created on December 9, 2007, 12:07 AM
 */

package mdes.slick.sui;

import mdes.slick.sui.event.MouseAdapter;
import mdes.slick.sui.event.MouseEvent;
import mdes.slick.sui.event.MouseListener;

/**
 *
 * @author davedes
 */
public class DraggableContainer extends Container {
    
    protected MouseListener dragListener = new DragListener();
    
    /** Creates a new instance of DraggableContainer */
    public DraggableContainer() {
        addMouseListener(dragListener);
    }
    
    public MouseListener getDragListener() {
        return dragListener;
    }
    
    protected class DragListener extends MouseAdapter {
        
        float lastX, lastY;
        
        public void mousePressed(MouseEvent e) {
            lastX = e.getAbsoluteX();
            lastY = e.getAbsoluteY();
        }
        
        public void mouseDragged(MouseEvent e) {
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
