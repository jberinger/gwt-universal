package net.sprd.gwt.shared.dom;

import elemental2.dom.Element;
import elemental2.dom.HTMLAnchorElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLImageElement;
import elemental2.dom.HTMLInputElement;
import elemental2.dom.HTMLTextAreaElement;

public abstract class GwtuDom {
    
    public abstract < T extends HTMLElement> T create(String tag, String id, HTMLElement parent, Element nextSibling, Element previousSibling, String... attributes);

    public abstract boolean isServer();

    public abstract boolean isInitialPhase();

    public abstract void setInitialPhase(boolean initial);

    public abstract void setRootId(String rootId);
    
    public abstract void setIdPrefix(String idPrefix);

    public abstract void setAttribute(Element element, String name, String value);
    
    public abstract GwtuCss getGwtuCss();
    
    protected String getTagId(String tag) {
        return Character.toString(tag.charAt(0)).toLowerCase();
    }
    
    public void addAttributes(HTMLElement element, String... attributes) {
        if (attributes != null && attributes.length>1) {
            addElementAttributes(element, attributes);
        }
    }

    private void addElementAttributes(HTMLElement element, String[] attributes) {
        for(int i=0; i+1<attributes.length;i+=2) {
            String name = attributes[i];
            String value = attributes[i+1];
            if (element instanceof HTMLInputElement) {
                setInputElementAttribs((HTMLInputElement)element, name, value);
            } else if (element instanceof HTMLImageElement) {
                setImageElementAttribs((HTMLImageElement)element, name, value);
            } else if (element instanceof HTMLAnchorElement) {
                setAnchorElementAttribs((HTMLAnchorElement)element, name, value);
            } else if (element instanceof HTMLTextAreaElement) {
                setTextAreaElementAttribs((HTMLTextAreaElement)element, name, value);
            } else{
                setElementAttribs(element, name, value);
            }
        }
    }
    
    protected boolean setElementAttribs(HTMLElement element, String name, String value) {
//        if (ID.equals(name)) {
//            if (!Dom.isInitialPhase()) {
//                element.id = value;
//            }
//            return true;
//        }
        if (Attributes.STYLE.equals(name)) {
            element.style.cssText = value;
            return true;
        }
        if (Attributes.CLASS.equals(name)) {
            element.className = value;
            return true;
        }
        if (Attributes.TEXT.equals(name)) {
            element.textContent = value;
            return true;
        }
        if (Attributes.HTML.equals(name)) {
            element.innerHTML = value;
            return true;
        }
        setAttribute(element, name, value);
        return false;
    }
    
    protected boolean setTextAreaElementAttribs(HTMLTextAreaElement element, String name, String value) {
      if (Attributes.VALUE.equals(name)) {
          element.value = value;
          return true;
      }
      return setElementAttribs(element, name, value);
  }
    
    protected boolean setImageElementAttribs(HTMLImageElement element, String name, String value) {
        if (Attributes.SRC.equals(name)) {
            element.src = value;
            return true;
        }
        if (Attributes.ALT.equals(name)) {
            element.alt = value;
            return true;
        }
        return setElementAttribs(element, name, value);
    }
    
    protected boolean setAnchorElementAttribs(HTMLAnchorElement element, String name, String value) {
        if (Attributes.HREF.equals(name)) {
            element.href = value;
            return true;
        }
        return setElementAttribs(element, name, value);
    }
    
    protected boolean setInputElementAttribs(HTMLInputElement element, String name, String value) {
        if (Attributes.TYPE.equals(name)) {
            element.type = value;
            return true;
        }
        if (Attributes.VALUE.equals(name)) {
            element.value = value;
            return true;
        }
        return setElementAttribs(element, name, value);
    }

}
