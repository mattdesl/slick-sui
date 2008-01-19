/*
 * ColorPickerTest.java
 *
 * Created on December 8, 2007, 5:35 PM
 */

package mdes.slick.sui.test;

import mdes.slick.sui.DraggableContainer;
import mdes.slick.sui.Sui;
import mdes.slick.sui.Component;
import mdes.slick.sui.Container;
import mdes.slick.sui.Display;
import mdes.slick.sui.Theme;
import mdes.slick.sui.Frame;
import mdes.slick.sui.skin.simple.SimpleColorPicker;
import mdes.slick.sui.theme.CopperTheme;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author davedes
 */
public class ColorPickerTest extends BasicGame {
    
    public static void main(String[] args) throws Exception {
        AppGameContainer app = new AppGameContainer(new ColorPickerTest());
        app.setDisplayMode(800,600,false);
        app.start();
    }
    
    /**
     * Creates a new instance of ColorPickerTest
     */
    public ColorPickerTest() {
        super("ColorPickerTest");
    }
    
    private Display disp;
    private SimpleColorPicker picker;
    private Frame window;
    private DraggableContainer dragger;
    
    private Theme theme1;
    private Theme theme2;
        
    public void init(GameContainer container) throws SlickException {
        container.getGraphics().setBackground(new Color(90,163,245));
        
        theme1 = Sui.getTheme();
        theme2 = new CopperTheme();
        Sui.setTheme(theme1);
        
        disp = new Display(container);
        
        picker = new SimpleColorPicker();
        picker.setGlassPane(true);
        picker.getColorHexLabel().setGlassPane(true);
        picker.setName("picker");
        picker.setToolTipText("Drag me!");
        
        dragger = new DraggableContainer();
        dragger.setSize(picker.getSize());
        dragger.setLocation(200, 230);
        dragger.add(picker);
        
        window = new Frame("Choose a Color");
        window.setSize(picker.getWidth()+5, picker.getHeight()+window.getTitleBar().getHeight());
        window.setResizable(false);
        window.setLocation(200, 230);
        window.setVisible(false);
        
        Container pane = new Container();
        pane.setOpaque(true);
        pane.setBackground(picker.getSelectedColor());
        pane.setSize(200, 100);
        pane.setLocation(100, 100);
        pane.setToolTipText("Currently selected color");
        
        disp.add(pane);
        disp.add(dragger);
        disp.add(window);
    }
    
    public void update(GameContainer container, int delta) throws SlickException {
        disp.update(container, delta);
        
        //gets the component directly under the mouse
        Component comp = disp.getComponentAtMouse();
                
        if (container.getInput().isKeyPressed(Input.KEY_ESCAPE))
            container.exit();
    }
    
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.setColor(picker.getSelectedColor());
        g.drawString("Lorem ipsum dolor.", 100, 200);
        
        disp.render(container, g);
                
        g.drawString("Change the picker container with SPACE (window / draggable)", 10, 25);
        g.drawString("Show/hide the picker with W", 10, 40);
        g.drawString("Change the theme with T", 10, 55);
        g.drawString("Hover your mouse over components for tooltips", 10, 70);
    }
    
    public void keyPressed(int key, char c) {
        if (key == Input.KEY_W) {
            if (dragger.containsChild(picker)) {
                dragger.setVisible(!dragger.isVisible());
            } else {
                window.setVisible(!window.isVisible());
            }
        } else if (key == Input.KEY_SPACE) {
            //currently dragger, set to window
            if (dragger.containsChild(picker)) {
                dragger.remove(picker);
                dragger.setVisible(false);
                window.getContentPane().add(picker);
                window.setLocation(dragger.getLocation());
                window.setVisible(true);
                picker.setToolTipText(null);
            } else { //set to dragger
                window.getContentPane().remove(picker);
                window.setVisible(false);
                dragger.add(picker);
                dragger.setLocation(window.getLocation());
                dragger.setVisible(true);
                picker.setToolTipText("Drag me!");
            }
        } else if (key == Input.KEY_T) {
            Theme nt = theme1;
            if (Sui.getTheme()==theme1)
                nt = theme2;
            Sui.setTheme(nt);
            Sui.updateComponentTreeTheme(disp);
        }
    }
}
