/*
 * SwingTest.java
 *
 * Created on May 31, 2007, 5:55 PM
 */

package mdes.slick.sui.test;

import javax.swing.*;
import javax.swing.plaf.ButtonUI;

/**
 *
 * @author davedes
 */
public class SwingTest {
    
    public static void main(String[] args) {
        new SwingTest();
    }
    
    /** Creates a new instance of SwingTest. */
    public SwingTest() {
        long mstart, nstart, mend, nend;
        
        final JFrame f1 = new JFrame("Test1");
        
        f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f1.setBounds(20,20,200,100);
        
        JCheckBox button = new JCheckBox("Test") {
            public void updateUI() {
                throw new RuntimeException();
            }
        };
        //f1.getContentPane().setLayout(new FlowLayout());
        //f1.getContentPane().add(button);
        button.setBounds(5,5,100,50);
                
        f1.setLayout(null);
        f1.add(button);
        f1.getContentPane().setSize(500,500);
                        
        button.setIcon(null);
        
        //button.updateUI();
        
        
        f1.setVisible(true);
    }
    
}

/*
        
        
        SuiButton.Skin
       
        */