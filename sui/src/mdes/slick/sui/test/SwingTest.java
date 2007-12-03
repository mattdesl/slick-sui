/*
 * SwingTest.java
 *
 * Created on November 6, 2007, 3:47 PM
 */

package mdes.slick.sui.test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Matt
 */
public class SwingTest extends JFrame {
    
    /** Creates a new instance of SwingTest */
    public SwingTest() {
        getContentPane().setLayout(new FlowLayout());
        
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        slider.setPaintTicks(true);
        //slider.setSnapToTicks(true);
        slider.setMajorTickSpacing(10);
        //slider.setMinorTickSpacing(20);
        
        getContentPane().add(slider);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 200);
        System.out.println(getContentPane().getSize());
        setLocationRelativeTo(null);
    }
    
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception e) {}
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() { 
                new SwingTest().setVisible(true); 
            }
        });
    }
}
