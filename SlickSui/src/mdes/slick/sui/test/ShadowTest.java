/*
 * ShadowTest.java
 *
 * Created on June 15, 2007, 12:44 AM
 */

package mdes.slick.sui.test;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
 
public class ShadowTest extends JPanel
{
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        int d = Math.min(w, h)/4;
        Rectangle r = new Rectangle(w/16, h/16, w/3, h/4);
        drawShadow(r, g2);
        g2.draw(r);
        Ellipse2D circle = new Ellipse2D.Double(w*3/8, h*3/8, d, d);
        drawShadow(circle, g2);
        g2.draw(circle);
        Line2D line = new Line2D.Double(w/2, h*15/16, w*15/16, h*2/3);
        drawShadow(line, g2);
        g2.draw(line);
    }
 
    private void drawShadow(Shape shape, Graphics2D g2)
    {
        Paint orig = g2.getPaint();
        AffineTransform at = AffineTransform.getTranslateInstance(3.0,2.0);
        Area shadow = new Area(at.createTransformedShape(shape));
        shadow.subtract(new Area(shape));
        g2.setPaint(Color.gray.darker());
        g2.fill(shadow);
        g2.setPaint(orig);
    }
 
    private void drawShadow(Line2D line, Graphics2D g2)
    {
        Paint orig = g2.getPaint();
        AffineTransform at = AffineTransform.getTranslateInstance(3.0,2.0);
        Shape shadow = at.createTransformedShape(line);
        g2.setPaint(Color.gray.brighter());
        g2.draw(shadow);
        g2.setPaint(orig);
    }
 
    public static void main(String[] args)
    {
        ShadowTest shadow = new ShadowTest();
        shadow.setOpaque(true);
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(shadow);
        f.setSize(400,400);
        f.setLocation(200,200);
        f.setVisible(true);
    }
}