package net.sprd.gwt.client.dom;

import elemental2.dom.Element;

public class DomUtil {
    
    public static void removeAllChildren(Element parent) {
        while (parent.hasChildNodes()) {
            parent.removeChild(parent.lastChild);
        }
    }

}
