_Sui_ is a GUI toolkit that should be familiar to users of other popular toolkits, such as Swing/AWT. These lessons assume the reader has prior knowledge of the Slick API and a general understanding of GUI toolkits.

# Introduction #

All widgets derive from a base `Component` class. A panel, or `Container`, is a widget that can hold child components. Most SUI components extend `Container`.

The top-level container, `Display`, is used to render, update, and gather input for the GUI tree. For most of our tests we will be using a single `Display` instance, but in your final games you may choose to use multiple GUI trees throughout different game states.

Below we have a simple block of code that can be re-used for most SUI tests and lessons. It creates an empty display then updates and renders it using Slick's BasicGame class. From here, we just need to add components to our display to create our GUI.
```
import org.newdawn.slick.*;
import org.newdawn.slick.gui.*;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.fills.*;
import mdes.slick.sui.*;
import mdes.slick.sui.event.*;

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
```


# Lesson 1 #
In this lesson we'll learn about containers, buttons, labels and layouts. We'll create a panel that contains a button and a label and give it a RowLayout to evenly space the two child components.
You can see the full source for the lesson [here](http://slick-sui.googlecode.com/svn/trunk/sui/src/mdes/slick/sui/test/Lesson1.java).

The first thing we will learn about is a `Container` widget. These are meant to hold child components. With the default skin, a `Container` is initially not renderable and its bounds (x, y, width, height) are all set to zero.

After we create our Display, we need to set up our components. The content panel has a size of 155 by 100 px, a location of (100,100) and a dark gray background color.
```
         Container content = new Container();
        content.setSize(155, 100); //sets panel size
        content.setLocation(100, 100); //sets panel loc relative to parent (display)
        content.setOpaque(true); //ensures that the background is drawn
        content.setBackground(Color.darkGray); //sets the background color
```

_Note:_ Component coordinates (x,y) are always relative to the component's parent. If no parent exist, the coordinates are relative to the game container (such as with Displays). The "absolute location" is the sum of all parent locations.

Next we set up a layout for our content panel, which evenly spaces the components horizontally:
```
         //give our content a row layout that is aligned (horiz) left and (vert) center.
        RowLayout layout = new RowLayout(true, RowLayout.LEFT, RowLayout.CENTER);
        content.setLayout(layout);
```

Finally, we finish by initializing and adding a button and label to our content panel.
```
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
```

![http://img406.imageshack.us/img406/6017/lesson1jf3.jpg](http://img406.imageshack.us/img406/6017/lesson1jf3.jpg)

# Lesson 2 #
In this lesson we'll take a look at events, frames, check boxes and draggable windows.

# Lesson 3 #
In this lesson we'll learn about text components, sliders, and tooltips.

# Lesson 4 #
In this lesson we'll learn about basic skin and theme manipulation.