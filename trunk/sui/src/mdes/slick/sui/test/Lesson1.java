/*
 * Lesson1.java
 *
 * Created on January 19, 2008, 4:16 PM
 */

package mdes.slick.sui.test;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.*;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.fills.*;
import mdes.slick.sui.*;
import mdes.slick.sui.event.*;
import mdes.slick.sui.layout.*;

/**
 * In this lesson we will learn about containers, buttons, labels and layouts. 
 * We will create a panel that contains a button and a label and we will 
 * give it a RowLayout to evenly space the two child components.
 *
 * @author davedes
 */
public class Lesson1 extends BasicGame {
    
    public static void main(String[] args) throws Exception {
        AppGameContainer app = new AppGameContainer(new Lesson1());
        app.setDisplayMode(800,600,false);
        app.start();
    }
    
    /** The top-level Sui display. */
    private Display display;
    
    public Lesson1() {
        super("Lesson1");
    }
    
    public void init(GameContainer container) throws SlickException {
        //set up a nice blue background
        Color background = new Color(71,102,124);
        container.getGraphics().setBackground(background);

        //we create a Sui display from our Slick container
        display = new Display(container);
        
        //create a dark gray colored container for our components
        Container content = new Container();
        content.setSize(155, 100); //sets panel size
        content.setLocation(100, 100); //sets panel loc relative to parent (display)
        content.setOpaque(true); //ensures that the background is drawn
        content.setBackground(Color.darkGray); //sets the background color
        
        //give our content a row layout that is aligned (horiz) left and (vert) center.
        RowLayout layout = new RowLayout(true, RowLayout.LEFT, RowLayout.CENTER);
        content.setLayout(layout);
        
        //add a button to our content panel
        Button btn = new Button("Clicky");
        btn.pack(); //pack the button to the text
        content.add(btn);
        
        //add a label to our content panel
        Label label = new Label("This is a test");
        label.setForeground(Color.white); //sets the foreground (text) color
        label.pack(); //pack the label with the current text, font and padding
        label.setHeight(btn.getHeight()); //set same size between the two components
        content.add(label); //add the label to this display so it can be rendered
        
        //add the content panel to the display so it can be rendered
        display.add(content);
    }
    
    public void update(GameContainer container, int delta) throws SlickException {
        //update the display and its components
        display.update(container, delta);
        
        if (container.getInput().isKeyPressed(Input.KEY_ESCAPE))
            container.exit();
    }
    
    public void render(GameContainer container, Graphics g) throws SlickException {
        //render the display and its components
        display.render(container, g);
    }
}
