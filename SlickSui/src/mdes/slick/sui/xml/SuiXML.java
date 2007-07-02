/*
 * SuiXML.java
 *
 * Created on June 24, 2007, 6:55 PM
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
import org.newdawn.slick.util.*;
import java.lang.reflect.*;
import org.w3c.dom.*;
import java.util.*;
import java.io.*;

/**
 *
 * @author davedes
 */
public class SuiXML {
    
    private HashMap aliases = new HashMap();
    private Interpreter interp = new PnutsInterpreter();
    private Object client;
    
    /** Creates a new instance of SuiXML. */
    public SuiXML(Object client) {
        this.client = client;        
        alias("window", "mdes.slick.sui.SuiWindow");
        alias("border", "mdes.slick.sui.SuiBorder");
        alias("display", "mdes.slick.sui.SuiDisplay");
        alias("label", "mdes.slick.sui.SuiLabel");
        alias("popup", "mdes.slick.sui.SuiPopup");
        alias("checkbox", "mdes.slick.sui.SuiCheckBox");
        alias("textfield", "mdes.slick.sui.SuiTextField");
        alias("textarea", "mdes.slick.sui.SuiTextArea");
        alias("button", "mdes.slick.sui.SuiButton");
        alias("container", "mdes.slick.sui.SuiContainer");
    }
    
    public Object getTarget() {
        return client;
    }
    
    public String getQualifiedClassName(String alias) {
        return (String)aliases.get(alias);
    }
    
    public String alias(String alias, String qualifiedClassName) {
        return (String)aliases.put(alias, qualifiedClassName);
    }
    
    public SuiContainer read(InputStream in) throws Exception {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(in);
        
        Element element = document.getDocumentElement();
        
        if (!"sui".equals(element.getNodeName())) {
            throw new IOException("not a sui xml file");
        }
        
        SuiContainer ret = null;
        String clientID = element.getAttribute("id");
        
        NodeList list = element.getChildNodes();
        for (int i=0; i<list.getLength(); i++) {
            Node n = list.item(i);
            if (n instanceof Element) {
                Element el = (Element)n;
                if ("theme".equals(el.getNodeName())) {
                    //parse theme
                } else if ("eval".equals(el.getNodeName())) {
                    interp.eval(this, clientID, el.getTextContent()); 
                } else {
                    if (ret!=null)
                        throw new IOException("can only have one return value");
                    ret = buildTree(el, null, clientID);
                }
            }
        }
        
        return ret;
    }   
    
    private SuiContainer buildTree(Element root, SuiContainer parent, String clientID) throws InterpreterException {
        if (root==null)
            return parent;
        
        SuiContainer child = interp.parseContainer(this, root);
        if (parent!=null) {
            parent.add(child);
        }
        parent = child;
        
        String id = root.getAttribute("id");
        
        NamedNodeMap map = root.getAttributes();
        for (int i=0; i<map.getLength(); i++) {
            Node n1 = map.item(i);
            if (n1!=null && n1 instanceof Attr) {
                Attr at = (Attr)n1;
                String name = at.getName();
                String val = at.getValue();
                if (name!=null && val!=null
                        && !"id".equals(name)) {
                    interp.setProperty(this, parent, clientID, id, name, val);
                }
            }
        }
        
        NodeList list = root.getChildNodes();
        for (int i=0; i<list.getLength(); i++) {
            Node node = list.item(i);
            if (node instanceof Element) {
                Element el = (Element)node;
                if ("invoke".equals(node.getNodeName())) {
                    interp.invoke(this, parent, clientID, id, el.getAttribute("method"), el.getTextContent());
                } else if ("eval".equals(node.getNodeName())) {
                    interp.eval(this, parent, clientID, id, node.getTextContent()); 
                } else {
                    buildTree(el, parent, clientID);
                }
            }
        }
        
        return parent;
    }
}
