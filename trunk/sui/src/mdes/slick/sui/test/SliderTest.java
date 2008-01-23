/*
 * SliderTest.java
 *
 * Created on November 25, 2007, 6:42 PM
 */

package mdes.slick.sui.test;

import mdes.slick.sui.Sui;
import mdes.slick.sui.Display;
import mdes.slick.sui.ScrollBar;
import mdes.slick.sui.Slider;
import mdes.slick.sui.ToggleButton;
import mdes.slick.sui.Frame;
import mdes.slick.sui.event.ActionEvent;
import mdes.slick.sui.event.ActionListener;
import mdes.slick.sui.event.MouseAdapter;
import mdes.slick.sui.event.MouseEvent;

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
    
    Display disp = null;
    
    public void init(GameContainer container) throws SlickException {
        disp = new Display(container);
        Slider slider = new Slider(Slider.HORIZONTAL);
        slider.setBounds(100, 100, 200, 16);
        slider.setValue(0.25f);
        slider.setThumbSize(.1f);
        disp.add(slider);
        
        ScrollBar bar = new ScrollBar(ScrollBar.HORIZONTAL);
        bar.setLocation(slider.getX(), slider.getY()+slider.getHeight()+5);
        bar.setSize(100, 16);
        bar.getSlider().setThumbSize(.10f);
        disp.add(bar);
        
        Slider slider2 = new Slider(Slider.VERTICAL);
        slider2.setSize(16, 100);
        slider2.setLocation(slider.getX(), slider.getY()+45+slider.getHeight());
        slider2.setThumbSize(.05f);
        //disp.add(slider2);
        
        Frame dragger1 = new Frame("No drag.");
        dragger1.setBounds(200, 300, 300, 100);
        dragger1.setDraggable(false);
        dragger1.grabFocus();
        disp.add(dragger1);
        
        Frame dragger2 = new Frame("Drag me!");
        dragger2.setBounds(300, 350, 280, 80);
        dragger2.grabFocus();
        disp.add(dragger2);
        
        final Frame dragger3 = new Frame("Hide me!");
        dragger3.setMinimumSize(150, 50);
        dragger3.getTitleBar().remove(dragger3.getCloseButton());
        dragger3.setBounds(200, 450, 280, 100);
        dragger3.grabFocus();
        disp.add(dragger3);
                
        //TODO: fix glitch when setting minimum/max size on window (inproper size)
        //TODO: fix glitch where X isn't painted on close button if its disabled
        dragger3.setGlassPane(true);
        final ToggleButton btn = new ToggleButton("Toggle title bar.");
        btn.setSelected(true);
        btn.pack();
        btn.setLocation(10, 10);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Frame.TitleBar bar = dragger3.getTitleBar();
                
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
        
        if (container.getInput().isKeyPressed(Input.KEY_ESCAPE))
            container.exit();
    }
    
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.setBackground(Color.lightGray);
        disp.render(container, g);
    }
}
