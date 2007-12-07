package mdes.slick.sui.test;

import mdes.slick.sui.Padding;
import mdes.slick.sui.SuiButton;
import mdes.slick.sui.SuiDisplay;
import mdes.slick.sui.SuiWindow;
import mdes.slick.sui.layout.RowLayout;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class LayoutTest extends BasicGame {
    SuiDisplay display;

    public LayoutTest() {
	super("SuiLayout Test");
    }

    public void init(GameContainer container) throws SlickException {
	display = new SuiDisplay(container);
	SuiWindow window;
	RowLayout layout;
	//
	window = new SuiWindow("Auto Spacing Vertical");
	layout = new RowLayout();
	window.setLayout(layout);
	window.setSize(200, 500);
	populateWindow(window);
	display.add(window);
	
	window = new SuiWindow("Auto Spacing Horizontal Bottom Center");
	layout = new RowLayout(true);
	layout.setVerticalAlignment(RowLayout.BOTTOM);
	layout.setHorizontalAlignment(RowLayout.CENTER);
	window.setLayout(layout);
	window.setSize(200, 500);
	populateWindow(window);
	display.add(window);
	
	window = new SuiWindow("5px Spacing Vertical Center");
	layout = new RowLayout();
	layout.setPadding(new Padding(5));
	layout.setHorizontalAlignment(RowLayout.CENTER);
	window.setLayout(layout);
	window.setSize(200, 500);
	populateWindow(window);
	display.add(window);
	
	window = new SuiWindow("5px Spacing Horizonal Bottom Center");
	layout = new RowLayout(true);
	layout.setPadding(new Padding(5));
	layout.setVerticalAlignment(RowLayout.BOTTOM);
	layout.setHorizontalAlignment(RowLayout.CENTER);
	window.setLayout(layout);
	window.setSize(200, 500);
	populateWindow(window);
	display.add(window);
    }

    public void populateWindow(SuiWindow window) {
	window.setSize(200, 500);
	window.getContentPane().setPadding(5);
	window.add(new SuiButton("Test 1"));
	window.add(new SuiButton("Test 2"));
	window.add(new SuiButton("Wider Button"));
	window.add(new SuiButton("Test 4"));
	window.add(new SuiButton("Test 5"));
	window.add(new SuiButton("Test 6"));
    }

    public void update(GameContainer container, int delta)
	    throws SlickException {
	display.update(container, delta);
    }

    public void render(GameContainer container, Graphics g)
	    throws SlickException {
	display.render(container, g);
    }

    public static void main(String[] args) throws Exception {
	AppGameContainer app = new AppGameContainer(new LayoutTest());
	app.setDisplayMode(800, 600, false);
	app.start();
    }
}
