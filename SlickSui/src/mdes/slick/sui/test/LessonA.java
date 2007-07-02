package mdes.slick.sui.test;

import org.newdawn.slick.*;
import mdes.slick.sui.*;
import mdes.slick.sui.border.LineBorder;
import mdes.slick.sui.event.*;
import mdes.slick.sui.skin.ClassicSkin;
import mdes.slick.sui.skin.SimpleSkin;
import mdes.slick.sui.skin.SimpleTheme;

/**
 * Lesson A in the documentation, showing how to
 * create a SuiWindow.
 *
 * @author davedes
 */
public class LessonA extends BasicGame implements SuiActionListener {
    
    /** Entry point to the application. */
    public static void main(String[] args) {
        try {
            AppGameContainer container = new AppGameContainer(new LessonA());
            container.setDisplayMode(640, 480, false);
            container.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public LessonA() {
        super("Sui Demo");
    }
    
    /** The display to render and update SUI. */
    private SuiDisplay display = null;
    private SuiContainer slidePanel = null;
    private boolean slide=false, slideRight = false;
    private SuiButton btn = null;
    private SuiCheckBox check1, check2, check3;
    private int regDelay;
    private SuiWindow win1, win2;
    
    public void init(GameContainer container) throws SlickException {
        regDelay = Sui.getToolTipDelay(); //default

        //initializes Sui
        display = Sui.init(container);
        display.setClipEnabled(false);
        
        //TODO: dynamic tooltip update
        //TODO: remove tooltip after delay
        btn = new SuiButton(">>");
        btn.setToolTipText("Show/Hide Options");
        btn.addActionListener(this);
        btn.pack();
        btn.setHorizontalAlignment(SuiButton.LEFT_ALIGNMENT);
        btn.setLocation(-3, 90);
        display.add(btn);
        
        
        Color scol = new Color(Color.gray);
        scol.a = .25f;
        
        SuiTheme theme = Sui.getTheme();
        
        slidePanel = new SuiContainer();
        slidePanel.setSize(130, 250);
        slidePanel.setOpaque(true);
        slidePanel.setBackground(theme.getBackground());
        slidePanel.setBorder(new LineBorder(theme.getPrimaryBorder2()));
        slidePanel.setLocation(btn.getX()-slidePanel.getWidth(), btn.getY() + btn.getHeight() + 5);
        display.add(slidePanel);
        
        SuiLabel title = new SuiLabel("Options");
        title.pack();
        title.setLocation(8, 5);
        slidePanel.add(title);
        
        check1 = new SuiCheckBox("Show Window 1");
        check2 = new SuiCheckBox("Show Window 2");
        check3 = new SuiCheckBox("Enable Tooltips");
        check1.pack();
        check2.pack();
        check3.pack();
        slidePanel.add(check1);
        slidePanel.add(check2);
        slidePanel.add(check3);
        check1.setLocation(5, title.getY()+title.getHeight()+9);
        check2.setLocation(5, check1.getY()+check1.getHeight()+6);
        check3.setLocation(5, check2.getY()+check2.getHeight()+6);
        
        check1.setSelected(true);
        check2.setSelected(true);
        check3.setSelected(true);
        
        check1.addActionListener(this);
        check2.addActionListener(this);
        check3.addActionListener(this);
        
        win1 = new SuiWindow("Hello!");
        win1.setSize(200, 200);
        win1.setLocation(195, 95);
        win1.setVisible(true);
        win1.setResizable(false);
        display.add(win1);
        
        //set up some buttons
        final SuiButton themeChange = new SuiButton("Change Theme");
        themeChange.pack();
        themeChange.setWidth(120);
        themeChange.setLocationRelativeTo(win1.getContentPane());
        themeChange.translate(0, -themeChange.getHeight());        
        win1.add(themeChange);
        
        final SuiButton skinChange = new SuiButton("Change Skin");
        skinChange.pack();
        skinChange.setLocation(themeChange.getX(), themeChange.getY()+themeChange.getHeight()+5);
        skinChange.setWidth(themeChange.getWidth());
        win1.add(skinChange);
        
        SuiActionListener changeListener = new SuiActionListener() {
            public void actionPerformed(SuiActionEvent e) {
                if (e.getSource()==themeChange) {
                    SuiTheme cur = Sui.getTheme();
                    if (cur instanceof Sui.DefaultTheme)
                        cur = new SimpleTheme();
                    else 
                        cur = new Sui.DefaultTheme();
                    Sui.setTheme(cur);
                    Sui.updateComponentTreeUI(display);
                } else if (e.getSource()==skinChange) {
                    SuiSkin cur = Sui.getSkin();
                    if (cur instanceof ClassicSkin) {
                        cur = new SimpleSkin();
                    } else 
                        cur = new ClassicSkin();
                    try {
                        Sui.setSkin(cur);
                        Sui.updateComponentTreeUI(display);
                    } catch (SlickException exc) {}
                }
            }
        };
        skinChange.addActionListener(changeListener);
        themeChange.addActionListener(changeListener);
        
        win2 = new SuiWindow("Resizable Window");
        win2.setSize(200, 100);
        win2.setLocation(225, 125);
        win2.setVisible(true);
        win2.setMinimumSize(180, 50);
        display.add(win2); 
                
        SuiButton button1 = new SuiButton("Button");
        button1.pack();
        button1.setLocation(20, 20);
        win2.add(button1);
        
        SuiButton button2 = new SuiButton("Close");
        button2.pack();
        button2.setEnabled(false);
        button2.setLocation(button1.getX()+button1.getWidth()+5, button1.getY());
        win2.add(button2);
        
        win1.getCloseButton().addActionListener(this);
        win2.getCloseButton().addActionListener(this);
    }
    
    public void render(GameContainer container, Graphics g) {
        g.setBackground(Color.lightGray);
        
        g.drawString("Current Theme: "+Sui.getTheme().getName(), 10, 25);
        g.drawString("Current Skin: "+Sui.getSkin().getName(), 10, 40);
        
        //render the window and its children
        display.render(container, g);
    }
    
    public void update(GameContainer container, int delta) {
        //updates the window and its children
        display.update(container, delta);
        
        if (slide) {
            if (slideRight) {
                if (slidePanel.getX()>=btn.getX()) {
                    slidePanel.setX(btn.getX());
                    slide = false;
                } else {
                    float x = slidePanel.getX() + delta*0.2f;
                    slidePanel.setX(x);
                }
            } else {
                if (slidePanel.getX()+slidePanel.getWidth()<=btn.getX()) {
                    slidePanel.setX(btn.getX()-slidePanel.getWidth());
                    slide = false;
                } else {
                    float x = slidePanel.getX() - delta*.2f;
                    slidePanel.setX(x);
                }
            }
        }
    }
    
    public void keyPressed(int k, char c) {
        if (k==Input.KEY_ESCAPE) {
            System.exit(0);
        }
    }
    
    public void actionPerformed(SuiActionEvent e) {
        if (e.getSource()==btn) {
            slideRight = !slideRight;
            btn.setText( slideRight ? "<<" : ">>" );
            slide = true;
        } else if (e.getSource()==check1) {
            win1.setVisible(!win1.isVisible());
        } else if (e.getSource()==check3) {
            if (check3.isSelected()) {
                Sui.setToolTipDelay(regDelay);
            } else {
                Sui.setToolTipDelay(Integer.MAX_VALUE);
            }
        } else if (e.getSource()==check2) {
            win2.setVisible(!win2.isVisible());
        } else if (e.getSource()==win1.getCloseButton()) {
            check1.setSelected(false);
        } else if (e.getSource()==win2.getCloseButton()) {
            check2.setSelected(false);
        }
    }
}