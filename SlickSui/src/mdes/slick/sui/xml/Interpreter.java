/*
 * Interpreter.java
 *
 * Created on June 24, 2007, 7:32 PM
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

/**
 *
 * @author davedes
 */
public interface Interpreter {
    
    public SuiContainer parseContainer(SuiXML xml, Element e) throws InterpreterException;
    
    public void eval(SuiXML xml, SuiContainer parent, String clientID, 
                        String id, String text) throws InterpreterException;
    
    public void eval(SuiXML xml, String clientID, String text) throws InterpreterException;
    
    public void invoke(SuiXML xml, SuiContainer parent, String clientID, 
                        String id, String name, String text) throws InterpreterException;
    
    public void setProperty(SuiXML xml, SuiContainer parent, String clientID, 
                        String id, String name, String val) throws InterpreterException;
}
