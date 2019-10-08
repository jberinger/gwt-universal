package net.sprd.gwt.shared.page;


import java.util.HashMap;
import java.util.Map;

import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import net.sprd.gwt.shared.dom.GwtuCss;
import net.sprd.gwt.shared.dom.GwtuDom;
import net.sprd.gwt.shared.dom.GwtuDomCreator;

public class AppDom {
    
    public static AppDom createGenericAppDom() {
        AppDom appDom = new AppDom();
        appDom.setInitialPhase(false);
        return appDom;
    }
    

    private GwtuDom gwtuDom = GwtuDomCreator.create();
    
    public AppDom() {
    }
    
    public <T extends HTMLElement> T create(String tag, HTMLElement parent) {
        return create(tag, null, parent);
    }

    public <T extends HTMLElement> T create(String tag, String id, HTMLElement parent) {
        return gwtuDom.create(tag, id, parent, null, null);
    }

    public <T extends HTMLElement> T create(String tag, HTMLElement parent, String... attributes) {
        return create(tag, null, parent, attributes);
    }
    
   

    public <T extends HTMLElement> T create(String tag, String id, HTMLElement parent, String[] attributes, Child... childs) {
        T element = create(tag, id, parent, attributes);
        createChilds(element, childs, null);
        return element;
    }

    public <T extends HTMLElement> T create(String tag, HTMLElement parent, String[] attributes, Child... childs) {
        return create(tag, null, parent, attributes, childs);
    }
    
    public void addChilds(HTMLElement parent, Child[] childs) {
        createChilds(parent, childs, null);
    }
    
    public Map<String, HTMLElement> createAll(String tag, HTMLElement parent, String[] attributes, Child... childs) {
        return createAll(tag, null, parent, attributes, childs);
    }
    
    public Map<String, HTMLElement> createAll(String tag, String id, HTMLElement parent, String[] attributes, Child... childs) {
        Map<String, HTMLElement> idElements = new HashMap<>();
        HTMLElement element = create(tag, id, parent, attributes);
        if (idElements != null && id != null) {
            idElements.put(id, element);
        }
        createChilds(element, childs, idElements);
        return idElements;
    }
    
    public Map<String, HTMLElement> addAllChilds(HTMLElement parent, Child[] childs) {
        Map<String, HTMLElement> idElements = new HashMap<>();
        createChilds(parent, childs, idElements);
        return idElements;
    }
    
    private void createChilds(HTMLElement parent, Child[] childs, Map<String, HTMLElement> idElements) {
        if (childs != null) {
            for (Child child:childs) {
                HTMLElement childElement = create(child, parent);
                createChilds(childElement, child.getChilds(), idElements);
                if (idElements != null && child.getId() != null) {
                    idElements.put(child.getId(), childElement);
                }
            }
        }
    }
    
    public <T extends HTMLElement> T create(Child child, HTMLElement parent) {
        return create(child.getTag(), child.getId(), parent, child.getAttributes(), child.getChilds());
    }

    public <T extends HTMLElement> T create(String tag, String id, HTMLElement parent,
            String... attributes) {
        return createBefore(tag, id, parent, null, attributes);
    }
    
    public <T extends HTMLElement> T createBefore(String tag, HTMLElement parent, Element nextSibling, String... attributes) {
        return createBefore(tag, null, parent, nextSibling, attributes);
    }
    
    public <T extends HTMLElement> T createBefore(String tag, String id, HTMLElement parent, Element nextSibling,
            String... attributes) {
        return gwtuDom.create(tag, id, parent, nextSibling, null, attributes);
    }
    
    public <T extends HTMLElement> T createAfter(String tag, HTMLElement parent, Element previousSibling, String... attributes) {
        return createAfter(tag, null, parent, previousSibling, attributes);
    }
    
    public <T extends HTMLElement> T createAfter(String tag, String id, HTMLElement parent, Element previousSibling,
            String... attributes) {
        return gwtuDom.create(tag, id, parent, null, previousSibling, attributes);
    }
    
    public Child child(String tag, String id, String[] attributes, Child... childs) {
        return new Child(tag, id, attributes, childs);
    }
    
    public Child child(String tag, String[] attributes, Child... childs) {
        return new Child(tag, attributes, childs);
    }
    
    public String[] attributes(String... attributes) {
        return attributes;
    }

    public boolean isServer() {
        return gwtuDom.isServer();
    }

    public boolean isClient() {
        return !gwtuDom.isServer();
    }

    public boolean isInitialPhase() {
        return gwtuDom.isInitialPhase();
    }

    public void setInitialPhase(boolean initial) {
        gwtuDom.setInitialPhase(initial);
    }

    public void setRootId(String rootId) {
        gwtuDom.setRootId(rootId);
    }
    
    public void setIdPrefix(String idPrefix) {
        gwtuDom.setIdPrefix(idPrefix);
    }
    
    public void setAttribute(Element element, String name, String value) {
        gwtuDom.setAttribute(element, name, value);
    }
    
    public GwtuCss css() {
        return gwtuDom.getGwtuCss();
    }
    
    public GwtuDom getGwtuDom() {
        return gwtuDom;
    }
    
}
