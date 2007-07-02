/*
 * PnutsInterpreter.java
 *
 * Created on June 25, 2007, 2:05 AM
 */

package mdes.slick.sui.xml;

import org.pnuts.lang.*;
import pnuts.lang.*;
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
public class PnutsInterpreter implements Interpreter {
    
    private Object target = null;
    private Class klass = null; 
    private Context context = new Context();
    
    public static void main(String[] args) {
        Context ctx = new Context();
        Pnuts.eval("frame = new javax.swing.JFrame(\"test\");", ctx);
        System.out.println(Pnuts.eval("frame.title", ctx));
        //System.out.println(ctx.getCurrentPackage().get("frame".intern()));
    }
    
    public PnutsInterpreter() {
        try {
            Pnuts.eval("import org.newdawn.slick.*", context);
            Pnuts.eval("import org.newdawn.slick.gui.*", context);
            Pnuts.eval("import org.newdawn.slick.geom.*", context);
            Pnuts.eval("import org.newdawn.slick.fills.*", context);
            Pnuts.eval("import mdes.slick.sui.*", context);
            Pnuts.eval("import mdes.slick.sui.event.*", context);
            Pnuts.eval("import mdes.slick.sui.xml.*", context);
        } catch (Exception exc) {}
    }
    
    public SuiContainer parseContainer(SuiXML xml, Element e) throws InterpreterException {
        try {
            String alias = e.getNodeName();
            String fullName = xml.getQualifiedClassName(alias);
            if (fullName==null)
                fullName = alias;
            Class klass = Class.forName(fullName);
            SuiContainer container = (SuiContainer)klass.newInstance();
            return container;
        } catch (Exception exc) {
            throw new InterpreterException(exc);
        }
    }
        
    public void invoke(SuiXML xml, SuiContainer parent, String clientID, 
                        String id, String method, String text) throws InterpreterException {
        if (text==null)
            text="";
        if (id==null||id.length()==0)
            id = "__container__";
        if (clientID==null||clientID.length()==0)
            clientID = "__client__";
        
        context.getCurrentPackage().set(clientID, xml.getTarget());
        context.getCurrentPackage().set(id.intern(), parent);
        try { Pnuts.eval(id+"."+method+"("+text+");", context); }
        catch (Exception e) { throw new InterpreterException(e); }
    }
    
    public void setProperty(SuiXML xml, SuiContainer parent, String clientID,  
                        String id, String name, String val) throws InterpreterException {
        if (id==null||id.length()==0)
            id = "__container__";
        if (clientID==null||clientID.length()==0)
            clientID = "__client__";
        
        context.getCurrentPackage().set(clientID.intern(), xml.getTarget());
        context.getCurrentPackage().set(id.intern(), parent);
        try {
            Pnuts.eval(id+"."+name+" = "+val, context); }
        catch (Exception e) {
            throw new InterpreterException(e);
        }
    }
    
    public void eval(SuiXML xml, SuiContainer parent, String clientID, String id, String text) throws InterpreterException {
        if (clientID==null||clientID.length()==0)
            clientID = "__client__";
        context.getCurrentPackage().set(clientID.intern(), xml.getTarget());
        
        if (parent!=null) {
            if (id==null||id.length()==0)
                id = "__container__";
            context.getCurrentPackage().set(id.intern(), parent);
        }
        
        try { Pnuts.eval(text, context); }
        catch (Exception e) { throw new InterpreterException(e); }
    }
    
    public void eval(SuiXML xml, String clientID, String text) throws InterpreterException {
        eval(xml, null, clientID, null, text);
    }
}
