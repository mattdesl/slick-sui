/*
 * DemoFrame.java
 *
 * Created on November 10, 2007, 10:45 PM
 */

package mdes.slick.sui.test;

import mdes.slick.sui.Button;
import mdes.slick.sui.CheckBox;
import mdes.slick.sui.Component;
import mdes.slick.sui.Container;
import mdes.slick.sui.Label;
import mdes.slick.sui.Slider;
import mdes.slick.sui.ToggleButton;
import mdes.slick.sui.Frame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

/**
 * A window that holds a bunch of Sui components. Since this
 * is a component itself (a Frame) it can be reused through
 * multiple demos and tests.
 * 
 * 
 * @author davedes
 */
public class DemoFrame extends Frame {
    
    /**
     * Creates a new instance of DemoFrame
     */
    public DemoFrame() {
        super();
        
        getContentPane().setName("contentPane");
        
        Image icon = null;
        try { icon = new Image("res/skin/shared/warning_icon.png"); }
        catch (Exception e) {}
        
        setTitle("Demo Window");
        setVisible(true);
        setWindowIcon(icon);
        
        Button btn = new Button("Enabled");
        btn.pack();
        btn.setEnabled(true);
        btn.setLocation(10, 10);
        btn.setName("enabled");
        add(btn);
        
        Button btn2 = new Button("Disabled");
        btn2.pack();
        btn2.setHeight(btn.getHeight());
        btn2.setEnabled(false);
        btn2.setLocation(btn.getX()+btn.getWidth()+5, btn.getY());
        add(btn2);
        
        Container glass = createGlass(true, btn.getX(), btn2.getY()+btn2.getHeight()+5);
        add(glass);
        
        Component glass2 = createGlass(false, btn.getX(), glass.getY()+glass.getHeight()+5);
        add(glass2);
        
        CheckBox check = new CheckBox("Checkbox with text.");
        check.pack();
        check.setSelected(true);
        check.setLocation(glass2.getX(), glass2.getY()+glass2.getHeight()+5);
        add(check);
        
        CheckBox check2 = new CheckBox();
        check2.pack();
        check2.setSelected(false);
        check2.setLocation(check.getX(), check.getY()+check.getHeight()+5);
        add(check2);
        
        ToggleButton tog = new ToggleButton("Toggle Me");
        tog.pack();
        tog.setSelected(false);
        tog.setLocation(check2.getX()+check2.getWidth()+20, check2.getY());
        add(tog);
        
        Slider slider = new Slider(Slider.HORIZONTAL);
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
    
    private Button createGlass(boolean isGlass, float x, float y) {
        Button under = new Button();
        under.setName("glass");
        under.setToolTipText("This is the button.");
        under.setLocation(x, y);
        
        under.setSize(110,40);
        
        Label glass = new Label(isGlass?"Glass Pane":"Not Glass");
        glass.setBackground(new Color(1f, .25f, .25f, .25f));
        glass.setOpaque(true);
        glass.setGlassPane(isGlass);
        glass.setSize(100, 20);
        glass.setHorizontalAlignment(Label.LEFT_ALIGNMENT);
        glass.getPadding().left = 3;
        glass.translate(5, 5);
        under.add(glass);
        return under;
    }
}
