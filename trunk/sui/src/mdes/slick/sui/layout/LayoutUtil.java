package mdes.slick.sui.layout;

import mdes.slick.sui.Container;
import mdes.slick.sui.Frame;

/**
 * 
 * @author Dantarion
 */
public class LayoutUtil {
    public static float getAvailibleHeight(Container container) {
	if(container instanceof Frame)
	    container = ((Frame)container).getContentPane();
	return container.getHeight() - container.getPadding().top
		- container.getPadding().bottom;
    }

    public static float getAvailibleWidth(Container container) {
	if(container instanceof Frame)
	    container = ((Frame)container).getContentPane();
	return container.getWidth() - container.getPadding().left
		- container.getPadding().right;
    }

    public static float getChildrenHeight(Container container) {
	float totalHeight = 0;
	for (int i = 0; i < container.getChildCount(); i++) {
	    totalHeight += container.getChild(i).getHeight();
	}
	return totalHeight;
    }
    public static float getChildrenWidth(Container container) {
	float totalWidth = 0;
	for (int i = 0; i < container.getChildCount(); i++) {
	    totalWidth += container.getChild(i).getWidth();
	}
	return totalWidth;
    }
    public static float getMaxChildHeight(Container container)
    {
	float maxHeight = 0;
	for (int i = 0; i < container.getChildCount(); i++) {
	    
	    maxHeight = Math.max(maxHeight,container.getChild(i).getHeight());
	}
	return maxHeight;
    }
    public static float getMaxChildWidth(Container container)
    {
	float maxWidth = 0;
	for (int i = 0; i < container.getChildCount(); i++) {
	    
	    maxWidth = Math.max(maxWidth,container.getChild(i).getWidth());
	}
	return maxWidth;
    }
}
