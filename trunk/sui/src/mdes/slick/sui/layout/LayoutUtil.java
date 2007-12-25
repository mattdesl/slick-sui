package mdes.slick.sui.layout;

import mdes.slick.sui.SuiContainer;
import mdes.slick.sui.SuiWindow;

/**
 * 
 * @author Dantarion
 */
public class LayoutUtil {
    public static float getAvailibleHeight(SuiContainer container) {
	if(container instanceof SuiWindow)
	    container = ((SuiWindow)container).getContentPane();
	return container.getHeight() - container.getPadding().top
		- container.getPadding().bottom;
    }

    public static float getAvailibleWidth(SuiContainer container) {
	if(container instanceof SuiWindow)
	    container = ((SuiWindow)container).getContentPane();
	return container.getWidth() - container.getPadding().left
		- container.getPadding().right;
    }

    public static float getChildrenHeight(SuiContainer container) {
	float totalHeight = 0;
	for (int i = 0; i < container.getChildCount(); i++) {
	    totalHeight += container.getChild(i).getHeight();
	}
	return totalHeight;
    }
    public static float getChildrenWidth(SuiContainer container) {
	float totalWidth = 0;
	for (int i = 0; i < container.getChildCount(); i++) {
	    totalWidth += container.getChild(i).getWidth();
	}
	return totalWidth;
    }
    public static float getMaxChildHeight(SuiContainer container)
    {
	float maxHeight = 0;
	for (int i = 0; i < container.getChildCount(); i++) {
	    
	    maxHeight = Math.max(maxHeight,container.getChild(i).getHeight());
	}
	return maxHeight;
    }
    public static float getMaxChildWidth(SuiContainer container)
    {
	float maxWidth = 0;
	for (int i = 0; i < container.getChildCount(); i++) {
	    
	    maxWidth = Math.max(maxWidth,container.getChild(i).getWidth());
	}
	return maxWidth;
    }
}
