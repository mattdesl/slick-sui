/*
 * ClassicTheme.java
 *
 * Created on June 26, 2007, 2:29 PM
 */

package mdes.slick.sui.skin;

import mdes.slick.sui.SuiTheme;
import org.newdawn.slick.Color;

/**
 *
 * @author davedes
 */
public class ClassicTheme implements SuiTheme {
    
    private final Color p1 = new ColorUIResource(37, 78, 102);

    private final Color p2 = new ColorUIResource(16, 36, 46);

    private final Color p3 = new ColorUIResource(54, 123, 163);

    private final Color s1 = new ColorUIResource(37, 78, 102);

    private final Color s2 = new ColorUIResource(62, 70, 75);

    private final Color s3 = new ColorUIResource(20, 48, 64);

    private final Color txt = new ColorUIResource(Color.white);

    private Color disabled = new ColorUIResource(.25f, .25f, .25f, .55f);

    private Color background = new ColorUIResource(s2);

    /** Constructs the blue theme. */
    public ClassicTheme() {
        //sets up alpha values for a nicer look
        p1.a = 0.95f;
        p2.a = 0.95f;
        p3.a = 0.95f;

        s1.a = 0.95f;
        s2.a = 0.95f;
        s3.a = 0.95f;

        background.a = .98f;
    }

    public Color getPrimary1() { return s1; }
    public Color getPrimary2() { return s2; }
    public Color getPrimary3() { return s3; }

    public Color getSecondary1() { return p1; }
    public Color getSecondary2() { return p2; }
    public Color getSecondary3() { return p3; }

    public Color getForeground() { return txt; }

    public String getName() { return "Classic Theme"; }

    public Color getBackground() { return background; }

    public Color getPrimaryBorder1() { return s3; }

    public Color getPrimaryBorder2() { return s3; }

    public Color getSecondaryBorder1() { return s3; }

    public Color getSecondaryBorder2() { return s3; }

    public Color getTitleBar1() { return s1; }

    public Color getTitleBar2() { return s2; }

    public Color getActiveTitleBar1() { return p1; }

    public Color getActiveTitleBar2() { return p2; }

    public Color getDisabledMask() { return disabled; }
}
