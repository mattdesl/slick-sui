/*
 * JavaInterpreter.java
 *
 * Created on June 24, 2007, 7:35 PM
 */

package mdes.slick.sui.xml;

import javax.xml.parsers.*;
import mdes.slick.sui.*;
import mdes.slick.sui.event.*;
import mdes.slick.sui.skin.*;
import mdes.slick.sui.border.*;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.*;
import org.newdawn.slick.geom.*;
import java.lang.reflect.*;
import org.w3c.dom.*;
import java.util.*;

/**
 *
 * @author davedes
 */
public class JavaInterpreter {
    
    private float[] locOut = { 0f, 0f };
    private int[] sizeOut = { 0, 0 };
    private Object target = null;
    private Class klass = null; 
    
    public SuiContainer parseContainer(SuiXML xml, Element e) throws InterpreterException {
        try {
            String alias = e.getNodeName();
            String fullName = xml.getQualifiedClassName(alias);
            if (fullName==null)
                fullName = alias;
            System.out.println(fullName);
            Class klass = Class.forName(fullName);
            SuiContainer container = (SuiContainer)klass.newInstance();
            parseContainer2(container, xml, e);
            return container;
        } catch (Exception exc) {
            throw new InterpreterException(exc);
        }
    }
    
    public void eval(String text) throws InterpreterException {}
        
    private void parseContainer2(SuiContainer container, SuiXML xml, Element e) throws Exception {
        target = xml.getTarget();
        klass = target.getClass();
        NamedNodeMap map = e.getAttributes();
                
        //reset bounds
        locOut[0] = 0f;
        locOut[1] = 0f;
        sizeOut[0] = 0;
        sizeOut[1] = 0;
        
        //precedence: bounds > (location || size)
        String bounds = e.getAttribute("bounds");
        if (bounds==null) {
            String loc = e.getAttribute("location");
            if (loc!=null)
                parseLocation(loc, locOut);
            
            String size = e.getAttribute("size");
            if (size!=null)
                parseSize(size, sizeOut);
        } else
            parseBounds(bounds, locOut, sizeOut);
        
        container.setBounds(locOut[0], locOut[1], sizeOut[0], sizeOut[1]);
        
        for (int i=0; i<map.getLength(); i++) {
            Node n1 = map.item(i);
            if (n1!=null && n1 instanceof Attr) {
                Attr at = (Attr)n1;
                String name = at.getName();
                String val = at.getValue();
            }
        }
    }
        
    private String[] splitCommas(String val) {
        StringTokenizer tk = new StringTokenizer(val, ",");
        String[] array = new String[tk.countTokens()];
        int i=0;
        while (tk.hasMoreTokens()) {
            String s = tk.nextToken();
            array[i] = s.trim();
            i++;
        }
        return array;
    }
    
    private Object parseField(String varName) {
        try {
            Field f = klass.getField(varName);
            return f.get(target);
        } catch (Exception e) {}
        return null;
    }
           
    private Number parseNum(String num) {
        Number ret = null;
        
        if (num.length()>1 && num.startsWith("$")) {
            Object o = null;
            char c = num.charAt(0);
            if (c=='$') {
                o = parseField(num.substring(1));
            }
            if (o instanceof Number) {
                ret = ((Number)o);
            }
        } else {
            try { ret = Float.valueOf(num); }
            catch (NumberFormatException e) {}
        }
        
        return ret!=null ? ret : new Float(0f);
    }
        
    private void parseBounds(String val, float[] loc, int[] size) {
        String[] split = splitCommas(val);
        for (int i=0; i<split.length && i<2; i++) {
            loc[i] = parseNum(split[i]).floatValue();
        }
        
        for (int i=2, x=0; i<split.length && i<4; i++, x++) {
            size[x] = parseNum(split[i]).intValue();
        }
    }
    
    private void parseSize(String val, int[] size) {
        String[] split = splitCommas(val);
        for (int i=0; i<split.length && i<2; i++) {
            size[i] = parseNum(split[i]).intValue();
        }
    }
    
    private void parseLocation(String val, float[] loc) {
        String[] split = splitCommas(val);
        for (int i=0; i<split.length && i<2; i++) {
            loc[i] = parseNum(split[i]).floatValue();
        }
    }
}
