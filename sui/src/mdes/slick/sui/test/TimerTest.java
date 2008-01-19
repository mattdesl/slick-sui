/*
 * TimerTest.java
 *
 * Created on November 22, 2007, 3:36 PM
 */

package mdes.slick.sui.test;

import mdes.slick.sui.Button;
import mdes.slick.sui.Container;
import mdes.slick.sui.Display;
import mdes.slick.sui.Timer;
import mdes.slick.sui.event.ActionEvent;
import mdes.slick.sui.event.ActionListener;

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
public class TimerTest extends BasicGame {
    
    public static void main(String[] args) throws Exception {
        AppGameContainer app = new AppGameContainer(new TimerTest());
        app.setDisplayMode(800,600,false);
        app.start();
    }
    
    /** Creates a new instance of TimerTest */
    public TimerTest() {
        super("TimerTest");
    }
    
    private Display disp;
    private Timer timer = new Timer(1500);
    private Container panel;
    
    public void init(GameContainer container) throws SlickException {
        disp = new Display(container);
        
        timer.setRepeats(true);
        
        Button btn = new Button("Start Timer");
        btn.pack();
        btn.setLocation(200, 200);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer.start();
            }
        });
        disp.add(btn);
        btn.setToolTipText("This starts the timer.");
        
        Button btn2 = new Button("Stop Timer");
        btn2.pack();
        btn2.setWidth(btn.getWidth());
        btn2.setLocation(btn.getX(), btn.getY()+btn2.getHeight()+5);
        btn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer.stop();
            }
        });
        disp.add(btn2);
        btn2.setToolTipText("This stops the timer.");
        
        panel = new Container();
        panel.setOpaque(true);
        panel.setBackground(Color.blue);
        panel.setBounds(btn.getX()+btn.getWidth()+25, 5, 50, 50);
        disp.add(panel);
    }
    
    public void update(GameContainer container, int delta) throws SlickException {
        disp.update(container, delta);
        timer.update(container, delta);
                
        if (timer.isAction()) {
            //jumps down
            panel.translate(0, 25);
            if (panel.getY()>container.getHeight())
                panel.setY(0);
        }
        
        if (container.getInput().isKeyPressed(Input.KEY_ESCAPE))
            container.exit();
    }
    
    public void render(GameContainer container, Graphics g) throws SlickException {
        disp.render(container, g);
        g.setColor(Color.white);
        g.drawString("Move Timer (1.5 s) - "+ ((int)(timer.getPercent()*100)) + "%",
                    10, 25);
    }
}
