package mdes.slick.sui.layout;

import mdes.slick.sui.Component;
import mdes.slick.sui.Container;
import mdes.slick.sui.Dimension;

/**
 * 
 * @author Dantarion
 */
public class RowLayout implements LayoutManager {
    // Horizontal Alignment Constants
    final public static int LEFT = 0;
    final public static int CENTER = 1;
    final public static int RIGHT = 2;
    // Vertical Alignment Constants
    final public static int TOP = 0;
    final public static int MIDDLE = 1;
    final public static int BOTTOM = 2;

    final private static int AUTOSPACING = -1;
    private int vgap = 5;
    private int hgap = 5;
    private boolean autoSpacing = true;
    boolean horizontal = false;
    private int vAlign;
    private int hAlign;

    public RowLayout() {
	this(false);
    }
    
    public RowLayout(boolean horizontal, int hAlign, int vAlign) {
        this.horizontal = horizontal;
        this.hAlign = hAlign;
        this.vAlign = vAlign;
    }

    public RowLayout(boolean horizontal) {
	this(horizontal, TOP, LEFT);
    }
    
    public void setVerticalAlignment(int align) {
	vAlign = align;
    }

    public void setHorizontalAlignment(int align) {
	hAlign = align;
    }
    
    public boolean isHorizontal() {
	return horizontal;
    }

    public void setHorizontal(boolean horizontal) {
	this.horizontal = horizontal;
    }

    public void doLayout(Container container) {
        float availibleWidth = LayoutUtil.getAvailibleWidth(container);
	float availibleHeight = LayoutUtil.getAvailibleHeight(container);
	float maxChildWidth = LayoutUtil.getMaxChildWidth(container);
	float maxChildHeight = LayoutUtil.getMaxChildHeight(container);
	float totalChildWidth = LayoutUtil.getChildrenWidth(container);
	float totalChildHeight = LayoutUtil.getChildrenHeight(container);
	float startX = container.getPadding().left;
	float startY = container.getPadding().top;
	float spacing = computeSpacing();

	if (spacing == AUTOSPACING) {
	    if (horizontal) {
		spacing = (availibleWidth - totalChildWidth)
			/ (container.getChildCount() + 1);
		startX = spacing;
	    } else {
		spacing = (availibleHeight - totalChildHeight)
			/ (container.getChildCount() + 1);
		startY = spacing;
	    }
	}
	float spacingtotal = (container.getChildCount() + 1) * spacing;

	for (int i = 0; i < container.getChildCount(); i++) {
	    Component child = container.getChild(i);
            
	    if (horizontal) {
		switch (vAlign) {
		case BOTTOM:
		    child.setY(availibleHeight - maxChildHeight);
		    break;
		case MIDDLE:
		    child.setY(availibleHeight / 2 - maxChildHeight / 2);
		    break;
		case TOP:
		default:
		    child.setY(startY);
		    break;
		}
		switch (hAlign) {
		case RIGHT:
		    child.setX(startX + availibleWidth - totalChildWidth
			    - spacingtotal);
		    break;
		case CENTER:
		    child.setX(startX + container.getWidth() / 2
			    - (totalChildWidth + spacingtotal) / 2);
		    break;
		case LEFT:
		default:
		    child.setX(startX);
		    break;
		}
		startX += child.getWidth() + spacing;
	    } else {
		switch (hAlign) {
		case BOTTOM:
		    child.setX(availibleWidth - maxChildWidth);
		    break;
		case MIDDLE:
		    child.setX(availibleWidth / 2 - child.getWidth() / 2);
		    break;
		case TOP:
		default:
		    child.setX(startX);
		    break;
		}
		switch (vAlign) {
		case RIGHT:
		    child.setY(startY + availibleHeight - totalChildHeight
			    - spacingtotal);
		    break;
		case CENTER:
		    child.setY(startY + container.getHeight() / 2
			    - (totalChildHeight + spacingtotal) / 2);
		    break;
		case LEFT:
		default:
		    child.setY(startY);
		    break;
		}
		startY += child.getHeight() + spacing;
	    }

	}
    }

    private float computeSpacing() {
	if (this.isAutoSpacing())
	    return AUTOSPACING;
	if (horizontal)
	    return getHorizontalGap();
        else
            return getVerticalGap();
    }

    private void doHorizontal(Container container) {

    }

    private void doVertical(Container container) {

    }

    public int getVerticalGap() {
        return vgap;
    }

    public void setVerticalGap(int vgap) {
        this.vgap = vgap;
    }

    public int getHorizontalGap() {
        return hgap;
    }

    public void setHorizontalGap(int hgap) {
        this.hgap = hgap;
    }

    public boolean isAutoSpacing() {
        return autoSpacing;
    }

    public void setAutoSpacing(boolean autoSpacing) {
        this.autoSpacing = autoSpacing;
    }
}
