package net.sprd.gwt.server.dom.element;

import java.util.Map;

import elemental2.dom.CSSStyleDeclaration;
import elemental2.dom.EventListener;
import elemental2.dom.HTMLElement;

public interface ServerHTMLElement {
    
    public static void init(HTMLElement element) {
        element.style = new CSSStyleDeclaration();
        element.classList = new ServerElementClassList(element);
        element.className = "";
    }
    
    default public void addEventListener(String type, EventListener listener) {
        
    }
    
    public void setDataAttribute(String name, String value);
    public Map<String, String> getDataAttributes();
    
}
