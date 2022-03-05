package com.readData.DataXML.Utility;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Locale;

interface NodeListIteratorFn {
    void call(Node node);
}
public class SharedContentParser {

    public interface NodeListIteratorFn {
        void call(Node node);
    }
    public static String getAttribute(Node node, String attr) {
        Element el = (Element) node;
        String value="";
        if(el.hasAttribute(attr)) value = el.getAttribute(attr).strip();
        else {
            attr = attr.toLowerCase(Locale.ROOT);
            if(el.hasAttribute(attr)) value = el.getAttribute(attr).strip();
            else {
                attr = attr.toUpperCase(Locale.ROOT);
                if(el.hasAttribute(attr)) value = el.getAttribute(attr).strip();
            }
        }
        return value;
    }
    public static String processContent(Node node) {
        return node.getTextContent().trim();
    }
    public static boolean haveTag(Node n, String t1, String... t) {
        if(n==null) return false;
        if(n.getNodeName().equalsIgnoreCase(t1)) return true;
        for(String s : t) {
            if(n.getNodeName().equalsIgnoreCase(s)) return true;
        }
        return false;
    }

    public static void nodesIterator(NodeList nodes, NodeListIteratorFn fn) {
        for(int i=0,l=nodes.getLength(); i<l; i++) {
           fn.call(nodes.item(i));
        }
    }
}
