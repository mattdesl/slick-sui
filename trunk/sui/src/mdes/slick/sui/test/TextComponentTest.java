/*
 * TextComponentTest.java
 *
 * Created on December 13, 2007, 4:28 PM
 */

package mdes.slick.sui.test;

import mdes.slick.sui.Sui;
import mdes.slick.sui.SuiButton;
import mdes.slick.sui.SuiDisplay;
import mdes.slick.sui.SuiTextField;
import mdes.slick.sui.event.SuiActionEvent;
import mdes.slick.sui.event.SuiActionListener;
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
public class TextComponentTest extends BasicGame {
    
    public static void main(String[] args) throws Exception {
        AppGameContainer app = new AppGameContainer(new TextComponentTest());
        app.setDisplayMode(800,600,false);
        app.start();
    }
    
    /** Creates a new instance of TextComponentTest */
    public TextComponentTest() {
        super("TextComponentTest");
    }
    
    private SuiDisplay display;
    
    public void init(GameContainer container) throws SlickException {
        container.getInput().enableKeyRepeat(400, 50);
        container.getGraphics().setBackground(Sui.getTheme().getBackground());
        
        display = new SuiDisplay(container);
                
        final SuiTextField field = new SuiTextField("Test", 10);
        field.setLocation(200, 200);        
        field.setOpaque(true);
        field.setBackground(Color.blue);
        field.addActionListener(new SuiActionListener() {
           public void actionPerformed(SuiActionEvent ev) {
               System.out.println("Entered "+field.getText());
           } 
        });
        display.add(field);
        
        SuiButton btn = new SuiButton("Testeroo");
        btn.pack();
        btn.setLocation(250, 250);
        display.add(btn);
        
        field.grabFocus();
    }
    
    public void update(GameContainer container, int delta) throws SlickException {
        display.update(container, delta);
        
        if (container.getInput().isKeyPressed(Input.KEY_ESCAPE))
            container.exit();
    }
    
    public void render(GameContainer container, Graphics g) throws SlickException {
        display.render(container, g);
    }
}
