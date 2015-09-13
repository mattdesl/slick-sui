# Introduction #
This tutorial will discuss how to create a custom skin for SUI, and also how to implement your
own color themes.

## Skins ##
Skins are pluggable designs and "looks" for the GUI system. If you've used Swing, you may be familiar
with the Pluggable Look And Feel. Skinning SUI is similar, but more geared towards _simplicity_ and use in
games.

**Changing the Skin**
When you use `Sui.setSkin()` to change the skin, this will only affect components made thereafter. If you wish
to update the appearance of already-existing components, you must update those components:
```
   //sets the skin and updates the display
  MySkin skin = new MySkin();
  Sui.setSkin(skin);
  Sui.updateComponentTreeSkin(display); //<-- updates the skin
```

## Themes ##
Color themes hold the basic colors for a skin. Not all skins are expected to support themes, and skins may differ
on how exactly each color is used.

**Changing the Theme**
Instead of updating the skin, where each component appearance is reverted to the "skin default", we
instead only wish to re-install the appearance so that the colours can be adjusted. This is done through
the following:
```
   //sets the skin and updates the display
  MyTheme theme = new MyTheme();
  Sui.setTheme(theme);
  Sui.updateComponentTreeTheme(display); //<-- updates the theme
```

## Understanding Appearances ##
The `SuiComponent.updateAppearance` method is called when a component is first created. When called, the component
sets up the "skin default" appearance. In other words, the component reverts to the default appearance defined by
the current skin. For example, SuiButton overrides the method like so:
```
     public void updateAppearance() {
        setAppearance(Sui.getSkin().getButtonAppearance(this));
    }
```

And likewise on other components, such as SuiLabel:
```
     public void updateAppearance() {
        setAppearance(Sui.getSkin().getLabelAppearance(this));
    }
```

The appearance is then used for updating, rendering and bounds checking.

# Your First Skin #

## SuiSkin ##
The first step to creating your own skin is to write a `SuiSkin` implementation. Before we start, let's take a look at
how the interface works.

#### Basics ####
The `install` and `uninstall` methods are used when the skin is changed. When we use `Sui.setSkin` to set a new
skin, the old skin is uninstalled and the new skin is installed. This is where we will set up our resources (such
as fonts and images). It's recommended that you cache the resources so that they are only loaded once.
```
     public void install() throws SlickException;
    public void uninstall() throws SlickException;
```

The next two are easy: the skin name (eg: "CoolSkin") and whether or not it supports color themes. Not all skins
support color themes.
```
     public String getName();
    public boolean isThemeable();
```

The next bit looks a bit daunting, but it's simple once you get the gist of it. These are the
"skin default" appearances for each component.
```
     public ComponentAppearance getContainerAppearance(SuiContainer comp);
    public ComponentAppearance getCheckBoxAppearance(SuiCheckBox comp);
    public ComponentAppearance getButtonAppearance(SuiButton comp);
    public ComponentAppearance getToolTipAppearance(SuiToolTip comp);
    public ComponentAppearance getToggleButtonAppearance(SuiToggleButton comp);
    public ComponentAppearance getLabelAppearance(SuiLabel comp);
    public ComponentAppearance getTextFieldAppearance(SuiTextField comp);
    public WindowAppearance    getWindowAppearance(SuiWindow comp);
    public ScrollBarAppearance getScrollBarAppearance(SuiScrollBar comp); 
    public ComponentAppearance getScrollPaneAppearance(SuiScrollPane comp);
    public SliderAppearance    getSliderAppearance(SuiSlider comp);
```

#### Implementing ####
We'll start by loading the resources for our skin. Here's what we've got so far:
```
 public class CoolSkin implements SuiSkin {
 
    //cached resources
    private Image checkBoxImage;
    private Image closeButtonImage;
    private Font font;
 
    public String getName() {
        return "CoolSkin";
    }
 
    //yes, we do support themes!
    public boolean isThemeable() {
        return true;
    }
    
    ...
```
Right, the above is simple enough. Our resources can be seen at the top, they are all initially null.

Next, we'll set up the `install` and `uninstall` methods to deal with resources.
```
     public void install() throws SlickException {
        //load images if necessary
        if (checkBoxImage==null)
            checkBoxImage = tryImage("res/skin/simple/checkbox.png");
        if (closeButtonImage==null)
            closeButtonImage = tryImage("res/skin/simple/closewindow.png");
        
        //load font if necessary
        if (font==null)
            font = tryFont("res/skin/shared/verdana.fnt", "res/skin/shared/verdana.png");
    }
    
    public void uninstall() throws SlickException {
        //do nothing
    }
    
    ...
```

The above is pretty straight-forward for Slick users. This is similar to the code that would appear
in your game's `init` method. We only load the resources if they are not null (ie: haven't already
been loaded). The methods `tryImage` and `tryFont` are written below:
```
     //ResourceLoader will spit out a log message if there are problems    
    private Image tryImage(String s) {
        try { return new ImageUIResource(s); }
        catch (Exception e) { return null; }
    }
    private Font tryFont(String s1, String s2) {
        try { return new FontUIResource.AngelCodeFont(s1, s2); }
        catch (Exception e) { return null; }
    }
```

The `UIResource` tag is used in Swing, but it's not very well known to most Swing users.

**What is a UIResource?**
A UIResource is a tagging interface used to differentiate between "client properties" and skin
properties. For example, let's say we create a `SuiLabel` and give it some font and background
settings:
```
     Font myFont = new AngelCodeFont("test.fnt", "test.png");
    Color myColor = new Color(225, 200, 210);
    SuiLabel label = new SuiLabel("This is a test.");
    label.setForeground(myColor);
    label.setFont(myFont);
    label.pack(); //pack the label to the new font
```
Since `myFont` and `myForeground` don't implement UIResource, they are considered "client properties",
and they override the default (skin set) properties.

Notice that the foreground and font properties won't change even if we change the skin. If we had created
the font and color like so, then they would be changed with a new skin, because they would **not** be flagged
as client properties.
```
     Font myFont = new FontUIResource.AngelCodeFont("test.fnt", "test.png");
    Color myColor = new ColorUIResource(225, 200, 210);
```

When you are creating skin resources (such as default colors, fonts and images) you should use
UIResource classes to avoid changing the client properties.



Now we've implemented everything except for appearances.


**coming soon**