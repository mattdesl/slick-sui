/*
 * XMLThemeIO.java
 *
 * Created on November 11, 2007, 8:19 PM
 */

package mdes.slick.sui.theme;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import mdes.slick.sui.Theme;
import mdes.slick.sui.skin.ColorUIResource;

import org.newdawn.slick.Color;
import org.newdawn.slick.util.ResourceLoader;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author davedes
 */
public class XMLThemeIO {
    
    private static Color defaultColor = new ColorUIResource(Color.white);
    
    public static Theme read(InputStream in) throws IOException {
        try { 
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(in);
        
            Element element = document.getDocumentElement();
            return read(element);
        } catch (IOException e) {
            throw e;
        } catch (ParserConfigurationException e) {
            throw new IOException("Cannot parse document.");
        } catch (SAXException e) {
            throw new IOException("Cannot parse document.");
        } finally {
            in.close();
        }
    }
    
    public static Theme read(String str) throws IOException {
        return read(ResourceLoader.getResourceAsStream(str));
    }
    
    /**
     *
     * <pre><theme name="Steel Blue">
     *     <color type="primary1" r="1" g="1" b="1" a="0.5" />
     *     <color type="primary2" r="225" g="225" b="155" a="100" />
     *     ...
     * </theme></pre>
     *
     */  
    public static Theme read(Element element) throws IOException {
        if (!"theme".equals(element.getNodeName())) {
            throw new IOException("not a sui theme file");
        }
        
        final String name = element.getAttribute("name");
        
        HashMap map = new HashMap(); //<String, Color>
        
        NodeList nl = element.getChildNodes();
        for (int i=0; i<nl.getLength(); i++) {
            Node n = nl.item(i);
            if (n instanceof Element) {
                Element e = (Element)n;
                if ("color".equals(e.getNodeName())) {
                    Color c = parseColor(e);
                    map.put(e.getAttribute("type"), c);
                }
            }
        }
        
        //setup variables
        final Color activeTitleBar1 = getColor(map, "activeTitleBar1");
        final Color activeTitleBar2 = getColor(map, "activeTitleBar2");
        final Color background = getColor(map, "background");
        final Color disabledMask = getColor(map, "disabledMask");
        final Color foreground = getColor(map, "foreground");
        final Color primary1 = getColor(map, "primary1");
        final Color primary2 = getColor(map, "primary2");
        final Color primary3 = getColor(map, "primary3"); 
        final Color primaryBorder1 = getColor(map, "primaryBorder1");
        final Color primaryBorder2 = getColor(map, "primaryBorder2");
        final Color secondary1 = getColor(map, "secondary1");
        final Color secondary2 = getColor(map, "secondary2");
        final Color secondary3 = getColor(map, "secondary3");
        final Color secondaryBorder1 = getColor(map, "secondaryBorder1");
        final Color secondaryBorder2 = getColor(map, "secondaryBorder2");
        final Color titleBar1 = getColor(map, "titleBar1"); 
        final Color titleBar2 = getColor(map, "titleBar2");
          
        //return new class
        Theme theme = new Theme() {
            public String getName() { return name; }
            public Color getActiveTitleBar1() { return activeTitleBar1; }
            public Color getActiveTitleBar2() { return activeTitleBar2; }
            public Color getBackground() { return background; }
            public Color getDisabledMask() { return disabledMask; }
            public Color getForeground() { return foreground; }
            public Color getPrimary1() { return primary1; }
            public Color getPrimary2() { return primary2; }
            public Color getPrimary3() { return primary3; }
            public Color getPrimaryBorder1() { return primaryBorder1; }
            public Color getPrimaryBorder2() { return primaryBorder2; }
            public Color getSecondary1() { return secondary1; }
            public Color getSecondary2() { return secondary2; }
            public Color getSecondary3() { return secondary3; }
            public Color getSecondaryBorder1() { return secondaryBorder1; }
            public Color getSecondaryBorder2() { return secondaryBorder2; }
            public Color getTitleBar1() { return titleBar1; }
            public Color getTitleBar2() { return titleBar2; }
        };
        
        return theme;
    }
    
    private static Color getColor(HashMap map, String key) {
        Color color = (Color)map.get(key);
        if (color==null || !(color instanceof Color))
            color = defaultColor;
        return color;
    }
    
    private static Color parseColor(Element e) {
        Color color = null;
        String rstr = e.getAttribute("r");
        String gstr = e.getAttribute("g");
        String bstr = e.getAttribute("b");
        String astr = e.getAttribute("a");
        boolean hasAlpha = true;
        if (astr.length()==0)
            hasAlpha = false;
        
        //first try to parse all as ints
        try { 
            color = new ColorUIResource(
                    Integer.parseInt(rstr),
                    Integer.parseInt(gstr),
                    Integer.parseInt(bstr),
                    hasAlpha ? Integer.parseInt(astr) : 255); 
        } catch (NumberFormatException exc) {
            //if that fails, try all floats
            color = new ColorUIResource(
                    Float.parseFloat(rstr),
                    Float.parseFloat(gstr),
                    Float.parseFloat(bstr),
                    hasAlpha ? Float.parseFloat(astr) : 1f);
        }
        return color;
    }
    
    private int tryInt(String str) { 
        try { return Integer.parseInt(str); }
        catch (Exception e) { return -1; }
    }
        
    public static void write(OutputStream out, Theme theme) throws IOException {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            Document document = builder.newDocument();

            document.appendChild(themeToElement(document, theme));
            Result result = new StreamResult(new OutputStreamWriter(out,
                    "utf-8"));
            DOMSource source = new DOMSource(document);

            TransformerFactory factory = TransformerFactory.newInstance();
            try { factory.setAttribute("indent-number", new Integer(4)); }
            catch (IllegalArgumentException exc) {}

            Transformer xformer = factory.newTransformer();
            xformer.setOutputProperty(OutputKeys.INDENT, "yes");
            xformer.transform(source, result);
        } catch (DOMException ex) {
            throw new IOException("Error writing DOM");
        } catch (UnsupportedEncodingException ex) {
            throw new IOException("Error with encoding");
        } catch (ParserConfigurationException ex) {
            throw new IOException("Error parsing XML");
        } catch (TransformerException ex) {
            throw new IOException("Error transforming XML");
        } finally {
            out.close();
        }
    }
    //TODO: exception thing with setSkin??
    
    public static Element themeToElement(Document document, Theme theme) {
        Element root = document.createElement("theme");
        
        Method[] methods = theme.getClass().getMethods();
        Arrays.sort(methods, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Method)o1).getName().compareTo(((Method)o2).getName());
            }
        });
        
        for (int i=0; i<methods.length; i++) {
            Method m = methods[i];
            if (m.getName().indexOf("getName") >= 0) {
                Object obj = null;
                try { obj = m.invoke(theme, null); }
                catch (Exception e) {}
                String name = "";
                if (obj!=null)
                    name = obj.toString();
                root.setAttribute("name", name);
            } else {
                Object obj = null;
                try { obj = m.invoke(theme, null); }
                catch (Exception e) {}
                if (obj!=null && obj instanceof Color) {
                    Color c = (Color)obj;
                    String methodName = m.getName();
                    String varName = Character.toLowerCase(methodName.charAt(3))+methodName.substring(4);
                    
                    Element el = document.createElement("color");
                    el.setAttribute("type", varName);
                    el.setAttribute("r", colorStr(c.r));
                    el.setAttribute("g", colorStr(c.g));
                    el.setAttribute("b", colorStr(c.b));
                    el.setAttribute("a", colorStr(c.a));
                    root.appendChild(el);
                }
            }
        }
        return root;
    }
    
    private static String colorStr(float component) {
        return Integer.toString( (int)(component*255) );
    }
    
    public static void main(String[] args) throws Exception {
        //File f = new File("steelBlue.xml");
        //OutputStream out = new FileOutputStream(f);
        
        //write(out, new SteelBlueTheme());
        
        //Theme t = read("res/steelBlue.xml");
        
    }
}
