/*
 * Menu.java
 *
 * Created on June 11, 2007, 10:40 AM
 */

package mdes.slick.sui.test;
import java.util.Iterator; 

import org.newdawn.slick.Color; 
import org.newdawn.slick.Font; 
import org.newdawn.slick.Graphics; 
import org.newdawn.slick.Input; 
import org.newdawn.slick.SlickException; 
import org.newdawn.slick.Sound; 
import org.newdawn.slick.geom.Rectangle; 
import org.newdawn.slick.gui.BasicComponent; 
import org.newdawn.slick.gui.ComponentListener; 
import org.newdawn.slick.gui.GUIContext; 

/** 
 * Simple text menu, with a font and two colors to make the current option look 
 * apart. 
 */ 
public class Menu extends BasicComponent { 
   public static enum Style { 
      VERTICAL_LEFT, VERTICAL_CENTER, VERTICAL_RIGHT, HORIZONTAL 
   }; 
   
   // Menu options 
   protected String options[]; 

   // Size of the options, so I don't have to compute them every cicle 
   protected int widthOpt[], heightOpt; 

   // Index of the emphasized option 
   protected int emphasized; 

   // Index of the selected option 
   protected int selection; 

   // Font for the options 
   protected Font font; 

   // Text colours 
   protected Color clrNormal, clrEmphasized; 

   // Sounds 
   protected Sound sndEmphasize, sndSelect; 

   protected Style style; 

   protected float space; 

   private int x, y; 

   float width, height; 

   /** 
    * Constructor. 
    * 
    * @param container 
    *            container 
    * @param options 
    *            menu options 
    * @param font 
    *            font 
    * @throws SlickException 
    *             exception when loading resources 
    */ 
   public Menu(GUIContext container, String options[], Font font) throws SlickException { 
      super(container); 

      setLocation(0, 0); 

      clrNormal = Color.white; 
      clrEmphasized = Color.red; 
      sndEmphasize = sndSelect = null; 

      style = Style.VERTICAL_CENTER; 
      space = 10; 

      // This computes the size 
      setFont(font); 
      setOptions(options); 

      reset(); 
   } 

   /** 
    * Moves the component. 
    * 
    * @param x 
    *            X coordinate 
    * @param y 
    *            Y coordinate 
    */ 
   public void setLocation(int x, int y) { 
      this.x = x; 
      this.y = y; 
   } 

   /** 
    * Returns the position in the X coordinate 
    * 
    * @return x 
    */ 
   public int getX() { 
      return x; 
   } 

   /** 
    * Returns the position in the Y coordinate 
    * 
    * @return y 
    */ 
   public int getY() { 
      return y; 
   } 

   /** 
    * Set the menu options. 
    * 
    * @param options options 
    */ 
   public void setOptions(String options[]) { 
      if (options == null || options.length == 0) { 
         throw new IllegalArgumentException("Null or empty options"); 
      } 

      this.options = options; 

      recomputeSize(); 
   } 

   /** 
    * Get the menu options. 
    * 
    * @return options 
    */ 
   public String[] getOptions() { 
      return options; 
   } 

   /** 
    * Sets the blank space between options. 
    * 
    * @param space space in pixels 
    */ 
   public void setSpace(float space) { 
      this.space = space; 

      recomputeSize(); 
   } 

   /** 
    * Gets the blank space between options. 
    * 
    * @return space in pixels 
    */ 
   public float getSpace() { 
      return space; 
   } 

   /** 
    * Sets the font. 
    * 
    * @param font font 
    */ 
   public void setFont(Font font) { 
      if (font == null) { 
         throw new IllegalArgumentException("Null font"); 
      } 

      this.font = font; 

      recomputeSize(); 
   } 

   /** 
    * Gets the font. 
    * 
    * @return font 
    */ 
   public Font getFont() { 
      return font; 
   } 

   /** 
    * Sets the sound to play when emphasizing an option, null if there is none 
    * 
    * @param sndEmphasize sound, null for none 
    */ 
   public void setSndEmphasize(Sound sndEmphasize) { 
      this.sndEmphasize = sndEmphasize; 
   } 
    
   /** 
    * Gets the sound to play when emphasizing an option, null if there is none 
    * 
    * @return sound 
    */ 
   public Sound getSndEmphasize() { 
      return sndEmphasize; 
   } 

   /** 
    * Sets the sound to play when selecting an option, null if there is none 
    * 
    * @param sndSelect sound, null for none 
    */ 
   public void setSndSelect(Sound sndSelect) { 
      this.sndSelect = sndSelect; 
   } 

   /** 
    * Gets the sound to play when selecting an option, null if there is none 
    * 
    * @return sound 
    */ 
   public Sound getSndSelect() { 
      return sndSelect; 
   } 
    
   /** 
    * Sets the style. 
    * 
    * @param style 
    *            style 
    */ 
   public void setStyle(Style style) { 
      this.style = style; 

      recomputeSize(); 
   } 

   /** 
    * Gets the current style. 
    * 
    * @return style 
    */ 
   public Style getStyle() { 
      return style; 
   } 

   private void recomputeSize() { 
      if (font == null || options == null) { 
         width = 0; 
         height = 0; 
      } else { 
         widthOpt = new int[options.length]; 
         width = (float) (widthOpt[0] = font.getWidth(options[0])); 
         for (int i = 1; i < options.length; i++) { 
            widthOpt[i] = font.getWidth(options[i]); 
            heightOpt = Math.max(heightOpt, font.getHeight(options[i])); 

            if (style == Style.HORIZONTAL) { 
               width += space + widthOpt[i]; 
            } else { 
               width = Math.max(width, widthOpt[i]); 
            } 
         } 

         if (style == Style.HORIZONTAL) { 
            height = heightOpt; 
         } else { 
            height = (float) (heightOpt * options.length + (options.length - 1) 
                  * space); 
         } 
      } 
   } 

   /** 
    * Render the current frame. 
    * 
    * @param container game container 
    * @param g graphic context 
    */ 
   public void renderImpl(GUIContext container, Graphics g) { 
      float oX = x, oY = y, styleX; 

      Font temp = g.getFont(); 

      // To be able to tint the menu with whatever color is set before render 
      Color clr = g.getColor(); 
      Color clrNormalT = clrNormal.multiply(clr); 
      Color clrEmphasizedT = clrEmphasized 
            .multiply(clr); 

      g.setFont(font); 
      g.setColor(clrNormalT); 
      for (int i = 0; i < options.length; i++) { 
         styleX = oX; 
         if (style == Style.VERTICAL_CENTER) { 
            styleX = oX + ((width - widthOpt[i]) / 2); 
         } else if (style == Style.VERTICAL_RIGHT) { 
            styleX = oX + (width - widthOpt[i]); 
         } 
          
         if (emphasized == i) { 
            g.setColor(clrEmphasizedT); 
            g.drawString(options[i], (int) styleX, (int) oY); 
            g.setColor(clrNormalT); 
         } else { 
            g.drawString(options[i], (int) styleX, (int) oY); 
         } 
          
         if (style == Style.HORIZONTAL) { 
            oX += space + widthOpt[i]; 
         } else { 
            oY += space + heightOpt; 
         } 
      } 

      g.setColor(clr); 
      g.setFont(temp); 
   } 

   /** 
    * Get the width of the component 
    * 
    * @return The width of the component 
    */ 
   public int getWidth() { 
      return (int) width; 
   } 

   /** 
    * Get the height of the component 
    * 
    * @return The height of the component 
    */ 
   public int getHeight() { 
      return (int) height; 
   } 

   /** 
    * Gets the selected option index, must be called after receiving the 
    * component event. 
    * 
    * @return selected option index, -1 if there is no selection yet 
    */ 
   public int getSelection() { 
      return selection; 
   } 

   /** 
    * Reset the emphasized and selected index. 
    */ 
   public void reset() { 
      selection = -1; 
      emphasized = 0; 
   } 

   // Methods for the listener stuff 

   /** 
    * Sets the selected index as the emphasized one, if it has not been set 
    * previously. 
    */ 
   private void select() { 
      if (selection == -1) { 
         selection = emphasized; 
         if (sndSelect != null) { 
            sndSelect.play(); 
         } 
          
         Iterator it = listeners.iterator(); 
         while (it.hasNext()) { 
            ComponentListener listener = (ComponentListener) it.next(); 
            listener.componentActivated(this); 
         } 
         container.getInput().consumeEvent(); 
      } 
   } 

   /** 
    * Sets the emphasized index. 
    * 
    * @param emphasized emphasized option index 
    */ 
   private void emphasize(int emphasized) { 
      if (emphasized != this.emphasized) { 
         this.emphasized = emphasized; 
         if (sndEmphasize != null) { 
            sndEmphasize.play(); 
         } 
      } 
      container.getInput().consumeEvent(); 
   } 

   /** 
    * Emphasize the previous option. 
    */ 
   private void emphasizePrevious() { 
      if (emphasized == 0) { 
         emphasize(options.length - 1); 
      } else { 
         emphasize((emphasized - 1) % options.length); 
      } 
      container.getInput().consumeEvent(); 
   } 

   /** 
    * Emphasize the next option. 
    */ 
   private void emphasizeNext() { 
      if (emphasized == options.length - 1) { 
         emphasize(0); 
      } else { 
         emphasize((emphasized + 1) % options.length); 
      } 

      container.getInput().consumeEvent(); 
   } 

   // Listener 

   /* 
    * (non-Javadoc) 
    * 
    * @see org.newdawn.slick.gui.BasicComponent#controllerButtonPressed(int, 
    *      int) 
    */ 
   public void controllerButtonPressed(int controller, int button) { 
      if (selection == -1) { 
         select(); 
      } 
   } 

   /* 
    * (non-Javadoc) 
    * 
    * @see org.newdawn.slick.gui.BasicComponent#controllerDownPressed(int) 
    */ 
   public void controllerDownPressed(int controller) { 
      if (selection == -1 && style != Style.HORIZONTAL) { 
         emphasizeNext(); 
      } 
   } 

   /* 
    * (non-Javadoc) 
    * 
    * @see org.newdawn.slick.gui.BasicComponent#controllerLeftPressed(int) 
    */ 
   public void controllerLeftPressed(int controller) { 
      if (selection == -1 && style == Style.HORIZONTAL) { 
         emphasizePrevious(); 
      } 
   } 

   /* 
    * (non-Javadoc) 
    * 
    * @see org.newdawn.slick.gui.BasicComponent#controllerRightPressed(int) 
    */ 
   public void controllerRightPressed(int controller) { 
      if (selection == -1 && style == Style.HORIZONTAL) { 
         emphasizeNext(); 
      } 
   } 

   /* 
    * (non-Javadoc) 
    * 
    * @see org.newdawn.slick.gui.BasicComponent#controllerUpPressed(int) 
    */ 
   public void controllerUpPressed(int controller) { 
      if (selection == -1 && style != Style.HORIZONTAL) { 
         emphasizePrevious(); 
      } 
   } 

   /* 
    * (non-Javadoc) 
    * 
    * @see org.newdawn.slick.gui.BasicComponent#keyPressed(int, char) 
    */ 
   public void keyPressed(int key, char c) { 
      if (selection == -1) { 
         if (key == Input.KEY_ENTER) { 
            select(); 
         } else if (((style != Style.HORIZONTAL) && (key == Input.KEY_UP)) 
               || ((style == Style.HORIZONTAL) && (key == Input.KEY_LEFT))) { 
            emphasizePrevious(); 
         } else if (((style != Style.HORIZONTAL) && (key == Input.KEY_DOWN)) 
               || ((style == Style.HORIZONTAL) && (key == Input.KEY_RIGHT))) { 
            emphasizeNext(); 
         } 
      } 
   } 

   /* 
    * (non-Javadoc) 
    * 
    * @see org.newdawn.slick.gui.BasicComponent#mouseMoved(int, int, int, int) 
    */ 
   public void mouseMoved(int oldx, int oldy, int newx, int newy) { 
      if (selection == -1) { 
         if (Rectangle.contains(newx, newy, x, y, width, height)) { 
            int res = 0; 
            if (style == Style.HORIZONTAL) { 
               float max = x - (space / 2.0f); 
               int i = 0; 
               while ((newx > max) && (i < options.length)) { 
                  max += widthOpt[i] + space; 
                  i++; 
               } 
               res = i - 1; 
            } else { 
               res = (int) ((newy - y - (space / 2.0f)) / (heightOpt + space)); 
            } 
            emphasize(res); 
         } 
      } 
   } 

   /* 
    * (non-Javadoc) 
    * 
    * @see org.newdawn.slick.gui.BasicComponent#mousePressed(int, int, int) 
    */ 
   public void mousePressed(int button, int x, int y) { 
      if (selection == -1) { 
         // Because the emphasized option can be another one 
         mouseMoved(x, y, x, y); 
         select(); 
      } 
   } 
} 