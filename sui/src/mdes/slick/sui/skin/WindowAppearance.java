/*
 * WindowAppearance.java
 *
 * Created on November 12, 2007, 2:25 PM
 */

package mdes.slick.sui.skin;

/**
 *
 * @author davedes
 */
public interface WindowAppearance extends ComponentAppearance {
    //TODO: change to createCloseButton(), createTitleBar() etc
    public ComponentAppearance getCloseButtonAppearance();
    public ComponentAppearance getTitleBarAppearance();
    public ComponentAppearance getResizerAppearance();
}
