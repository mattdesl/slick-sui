/*
 * SwingTest.java
 *
 * Created on December 27, 2007, 1:14 PM
 */

package mdes.slick.sui.test;

import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author davedes
 */
public class SwingTest extends JFrame {
    
    /** Creates a new instance of SwingTest */
    public SwingTest() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new FlowLayout());
        
        final JTextArea textArea = new JTextArea(10, 5);
        textArea.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                System.out.println(textArea.getX());
            }
        });
        textArea.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                System.out.println("enter");
            }
            public void mouseExited(MouseEvent e) {
                System.out.println("exit");
            }
        });
        JScrollPane sp = new JScrollPane(textArea);
        getContentPane().add(sp);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        
        pack();
        setVisible(true);
    }
    
    public static void main(String[] args) { new SwingTest(); }
}
