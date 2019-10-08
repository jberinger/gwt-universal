package net.sprd.gwt.server.dom.element;

import java.util.HashMap;
import java.util.Map;

import elemental2.dom.EventListener;
import elemental2.dom.HTMLElement;

public class ServerSpanElement extends HTMLElement implements ServerHTMLElement{
    
    public ServerSpanElement() {
        ServerHTMLElement.init(this);
    }

    public void addEventListener(String type, EventListener listener) {

    }
    
    private Map<String, String> attributes = new HashMap<>();
    
    public void setDataAttribute(String name, String value) {
        attributes.put(name, value);
    }
    public Map<String, String> getDataAttributes() {
        return attributes;
    }
    
    public boolean hasChildNodes( ) {
        return false;
    }
    
    

}