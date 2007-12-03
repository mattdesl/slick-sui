/*
 * SkinManager.java
 *
 * Created on June 14, 2007, 4:17 PM
 */

package mdes.slick.sui.skin;

import java.util.HashMap;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import mdes.slick.sui.*;
import org.newdawn.slick.Font;

/**
 * Used to store system resources.
 * 
 * @author davedes
 */
public class SkinManager {
    
    private static HashMap map = new HashMap();
    
    /**
     * Creates a new instance of SkinManager.
     */
    public SkinManager() {
    }
    
    public static Object get(String key) { 
        return map.get(key);
    }
    
    public static Object put(String key, Object val) {
        return map.put(key, val);
    }
    
    public static Image getImage(String key) {
        Object o = get(key);
        if (o!=null && o instanceof Image) {
            return (Image)o;
        } else
            return null;
    }
    
    public static Color getColor(String key) {
        Object o = get(key);
        if (o!=null && o instanceof Color) {
            return (Color)o;
        } else
            return null;
    }
        
    public static Font getFont(String key) {
        Object o = get(key);
        if (o!=null && o instanceof Font) {
            return (Font)o;
        } else
            return null;
    }
    
    public static boolean installImage(SuiLabel c, String imgKey) {
        return installImage(c, getImage(imgKey));
    }
    
    public static boolean installImage(SuiLabel c, Image img) {
        Image compImg = c.getImage();
        if (compImg==null || compImg instanceof ImageUIResource) {
            c.setImage(img);
        }
        return compImg != c.getImage();
    }
    
    public static boolean installColors(SuiComponent c, Color background, Color foreground) {
        Color bg = c.getBackground();
        
        if (bg==null || bg instanceof ColorUIResource) {
            c.setBackground(background);
        }
        Color fg = c.getForeground();
        if (fg==null || fg instanceof ColorUIResource) {
            c.setForeground(foreground);
        }
        
        return fg!=c.getForeground() || bg!=c.getBackground();
    }
    
    public static boolean installFont(SuiComponent c, String fontKey) {
        Font f = c.getFont();
        if (f==null || f==Sui.getDefaultFont() || f instanceof FontUIResource) {
            Font f1 = getFont(fontKey);
            if (f1==null)
                f1 = Sui.getDefaultFont();
            if (f1!=null)
                c.setFont(f1);
        }
        return f != c.getFont();
    }
}
