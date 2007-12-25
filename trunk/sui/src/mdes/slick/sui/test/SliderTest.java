/*
 * SliderTest.java
 *
 * Created on November 25, 2007, 6:42 PM
 */

package mdes.slick.sui.test;

import mdes.slick.sui.Sui;
import mdes.slick.sui.SuiDisplay;
import mdes.slick.sui.SuiScrollBar;
import mdes.slick.sui.SuiSlider;
import mdes.slick.sui.SuiToggleButton;
import mdes.slick.sui.SuiWindow;
import mdes.slick.sui.event.SuiActionEvent;
import mdes.slick.sui.event.SuiActionListener;
import mdes.slick.sui.event.SuiMouseAdapter;
import mdes.slick.sui.event.SuiMouseEvent;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
/**
 *
 * @author Matt
 */
public class SliderTest extends BasicGame {
    
    public static void main(String[] args) throws Exception {
        AppGameContainer app = new AppGameContainer(new SliderTest());
        app.setDisplayMode(800,600,false);
        app.start();
    }
    
    /** Creates a new instance of SliderTest */
    public SliderTest() {
        super("SliderTest");
    }
    
    SuiDisplay disp = null;
    
    public void init(GameContainer container) throws SlickException {
        disp = new SuiDisplay(container);
                
        SuiSlider slider = new SuiSlider(SuiSlider.HORIZONTAL);
        slider.setBounds(100, 100, 200, 16);
        slider.setValue(0.25f);
        slider.setThumbSize(.1f);
        disp.add(slider);
        
        SuiScrollBar bar = new SuiScrollBar(SuiScrollBar.HORIZONTAL);
        bar.setLocation(slider.getX(), slider.getY()+slider.getHeight()+5);
        bar.setSize(slider.getWidth(), 16);
        bar.getSlider().setThumbSize(.10f);
        disp.add(bar);
        
        SuiSlider slider2 = new SuiSlider(SuiSlider.VERTICAL);
        slider2.setSize(16, 100);
        slider2.setLocation(slider.getX(), slider.getY()+45+slider.getHeight());
        slider2.setThumbSize(.05f);
        disp.add(slider2);
        
        SuiWindow dragger1 = new SuiWindow("No drag.");
        dragger1.setBounds(200, 300, 300, 100);
        dragger1.setDraggable(false);
        dragger1.grabFocus();
        disp.add(dragger1);
        
        SuiWindow dragger2 = new SuiWindow("Drag me!");
        dragger2.setBounds(300, 350, 280, 80);
        dragger2.grabFocus();
        disp.add(dragger2);
        
        final SuiWindow dragger3 = new SuiWindow("Hide me!");
        dragger3.setMinimumSize(150, 50);
        dragger3.getTitleBar().remove(dragger3.getCloseButton());
        dragger3.setBounds(200, 450, 280, 100);
        dragger3.grabFocus();
        disp.add(dragger3);
                
        //TODO: fix glitch when setting minimum/max size on window (inproper size)
        //TODO: fix glitch where X isn't painted on close button if its disabled
        dragger3.setGlassPane(true);
        final SuiToggleButton btn = new SuiToggleButton("Toggle title bar.");
        btn.setSelected(true);
        btn.pack();
        btn.setLocation(10, 10);
        btn.addActionListener(new SuiActionListener() {
            public void actionPerformed(SuiActionEvent e) {
                SuiWindow.TitleBar bar = dragger3.getTitleBar();
                
                boolean old = dragger3.isRootPaneCheckingEnabled();
                dragger3.setRootPaneCheckingEnabled(false);
                if (btn.isSelected()) {
                    dragger3.add(bar);
                } else {
                    dragger3.remove(bar);
                }
                dragger3.setRootPaneCheckingEnabled(old);
            }
        });
        dragger3.add(btn);
        
        //we update for testing purposes
        Sui.updateComponentTreeSkin(disp);
    }
    
    public void update(GameContainer container, int delta) throws SlickException {
        disp.update(container, delta);
        System.out.println(disp.getComponentAtMouse());
        if (container.getInput().isKeyPressed(Input.KEY_ESCAPE))
            container.exit();
    }
    
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.setBackground(Color.lightGray);
        disp.render(container, g);
    }
}
