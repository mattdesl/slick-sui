/*
 * DemoWindow.java
 *
 * Created on November 10, 2007, 10:45 PM
 */

package mdes.slick.sui.test;

import org.newdawn.slick.gui.*;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.fills.*;
import mdes.slick.sui.*;
import mdes.slick.sui.event.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import mdes.slick.sui.theme.*;
import java.lang.reflect.*;
import java.util.*;

/**
 * A window that holds a bunch of Sui components. Since this
 * is a component itself (a SuiWindow) it can be reused through
 * multiple demos and tests.
 * 
 * @author davedes
 */
public class DemoWindow extends SuiWindow {
    
    /**
     * Creates a new instance of DemoWindow
     */
    public DemoWindow() {
        super();
        
        getContentPane().setName("contentPane");
        
        Image icon = null;
        try { icon = new Image("res/skin/shared/warning_icon.png"); }
        catch (Exception e) {}
        
        setTitle("Demo Window");
        setVisible(true);
        setWindowIcon(icon);
        
        SuiButton btn = new SuiButton("Enabled");
        btn.pack();
        btn.setEnabled(true);
        btn.setLocation(10, 10);
        btn.setName("enabled");
        add(btn);
        
        SuiButton btn2 = new SuiButton("Disabled");
        btn2.pack();
        btn2.setHeight(btn.getHeight());
        btn2.setEnabled(false);
        btn2.setLocation(btn.getX()+btn.getWidth()+5, btn.getY());
        add(btn2);
        
        SuiContainer glass = createGlass(true, btn.getX(), btn2.getY()+btn2.getHeight()+5);
        add(glass);
        
        SuiComponent glass2 = createGlass(false, btn.getX(), glass.getY()+glass.getHeight()+5);
        add(glass2);
        
        SuiCheckBox check = new SuiCheckBox("Checkbox with text.");
        check.pack();
        check.setSelected(true);
        check.setLocation(glass2.getX(), glass2.getY()+glass2.getHeight()+5);
        add(check);
        
        SuiCheckBox check2 = new SuiCheckBox();
        check2.pack();
        check2.setSelected(false);
        check2.setLocation(check.getX(), check.getY()+check.getHeight()+5);
        add(check2);
        
        SuiToggleButton tog = new SuiToggleButton("Toggle Me");
        tog.pack();
        tog.setSelected(false);
        tog.setLocation(check2.getX()+check2.getWidth()+20, check2.getY());
        add(tog);
        
        SuiSlider slider = new SuiSlider(SuiSlider.HORIZONTAL);
        slider.setLocation(tog.getX(), tog.getY()+tog.getHeight()+5);
        slider.setSize(tog.getWidth(), 16);
        slider.setThumbSize(.10f);
        slider.setValue(0.3f);
        add(slider);
        
        setMinimumSize(check.getWidth()+20, 215);
        setMaximumSize(300, 300);
        setSize(getMinimumSize());
        setHeight(getHeight()+20);
    }
    
    private SuiButton createGlass(boolean isGlass, float x, float y) {
        SuiButton under = new SuiButton();
        under.setName("glass");
        under.setToolTipText("This is the button.");
        under.setLocation(x, y);
        
        under.setSize(110,40);
        
        SuiLabel glass = new SuiLabel(isGlass?"Glass Pane":"Not Glass");
        glass.setBackground(new Color(1f, .25f, .25f, .25f));
        glass.setOpaque(true);
        glass.setGlassPane(isGlass);
        glass.setSize(100, 20);
        glass.setHorizontalAlignment(SuiLabel.LEFT_ALIGNMENT);
        glass.getPadding().left = 3;
        glass.translate(5, 5);
        under.add(glass);
        return under;
    }
}
