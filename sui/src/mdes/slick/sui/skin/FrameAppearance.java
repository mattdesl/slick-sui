/*
 * FrameAppearance.java
 *
 * Created on November 12, 2007, 2:25 PM
 */

package mdes.slick.sui.skin;

import mdes.slick.sui.Button;
import mdes.slick.sui.Frame;

/**
 *
 * @author davedes
 */
public interface FrameAppearance extends ComponentAppearance {
    //TODO: change to createCloseButton(), createTitleBar() etc
    public ComponentAppearance getCloseButtonAppearance(Button button);
    public ComponentAppearance getTitleBarAppearance(Frame.TitleBar titleBar);
    public ComponentAppearance getResizerAppearance(Frame.Resizer resizer);
}
