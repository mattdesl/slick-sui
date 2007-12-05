/*
 * WindowAppearance.java
 *
 * Created on November 12, 2007, 2:25 PM
 */

package mdes.slick.sui.skin;

import mdes.slick.sui.SuiButton;
import mdes.slick.sui.SuiWindow;

/**
 *
 * @author davedes
 */
public interface WindowAppearance extends ComponentAppearance {
    //TODO: change to createCloseButton(), createTitleBar() etc
    public ComponentAppearance getCloseButtonAppearance(SuiButton button);
    public ComponentAppearance getTitleBarAppearance(SuiWindow.TitleBar titleBar);
    public ComponentAppearance getResizerAppearance(SuiWindow.Resizer resizer);
}
