/*
 * ColorSwatch.java
 *
 * Created on June 10, 2007, 4:33 PM
 */

package mdes.slick.sui.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 *
 * @author davedes
 */
public class ColorSwatch extends JFrame implements ActionListener {
    
    public static void main(String[] args) { new ColorSwatch(); }
    
    private JTextField input = new JTextField(8) {;
        protected Document createDefaultModel() {
            return new LimitDocument();
        }
    };
    private JTextField output = new JTextField(15);
    private JButton copy = new JButton("Copy");
    
    /** Creates a new instance of ColorSwatch. */
    public ColorSwatch() {
        super("Color Swatch");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        //setAlwaysOnTop(true);
        addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent e) {
                String s = getClipboard();
                if (s!=null&&s.length()!=0) {
                    try { 
                        Color.decode("#"+s);
                        input.setText(s);
                    } catch (NumberFormatException exc) {
                    }
                }
            }
        });
        
        getContentPane().setLayout(new BorderLayout());
        
        FlowLayout ly = new FlowLayout(FlowLayout.LEFT);
        
        JPanel inp = new JPanel(ly);
        inp.add(new JLabel("#"));
        inp.add(input);
        
        JPanel outp = new JPanel(ly);
        outp.add(output);
        outp.add(copy);
        
        getContentPane().add(inp, BorderLayout.NORTH);
        getContentPane().add(outp, BorderLayout.CENTER);
        
        output.setEditable(false);
        
        input.addActionListener(this);
        output.addActionListener(this);
        copy.addActionListener(this);
        input.getDocument().addDocumentListener(new MyDocListener());
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private static String getClipboard() {
        Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
    
        try {
            if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                String text = (String)t.getTransferData(DataFlavor.stringFlavor);
                return text;
            }
        } catch (Exception e) { }
        return null;
    }
    
    private void updateOutput() {
        String str = input.getText();
        if (str==null||str.length()!=6) {
            output.setText("");
            return;
        }
        try { 
            print(Color.decode("#"+str));
        } catch (NumberFormatException e) {
            output.setText("Cannot format given string.");
        }
    }
    
    private void print(Color c) {
        int r=c.getRed(), g=c.getGreen(), b=c.getBlue();
        output.setText("new ColorUIResource("+r+", "+g+", "+b+");");
    }
    
    private void copy() {
        StringSelection ss = new StringSelection(output.getText());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==input) {
            updateOutput();
        } else if (e.getSource()==copy) {
            copy();
        }
    }
    
    private class MyDocListener implements DocumentListener {
        public void insertUpdate(DocumentEvent e) {
            updateOutput();
        }

        public void removeUpdate(DocumentEvent e) {
            updateOutput();
        }

        public void changedUpdate(DocumentEvent e) {
            updateOutput();
        }
    }
    
    private class LimitDocument extends PlainDocument {
        
        private int limit = 6;
        
        public void insertString(int offs, String str, AttributeSet a)
                                                throws BadLocationException {
            if (str == null) {
                return;
            }
            
            String txt = input.getText();
            if (txt!=null && txt.length()+str.length() > limit) {
                return;
            }
            
            super.insertString(offs, str, a);
        }
    }
}
