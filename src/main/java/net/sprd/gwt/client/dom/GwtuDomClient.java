package net.sprd.gwt.client.dom;


import java.util.List;

import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;
import net.sprd.gwt.shared.dom.GwtuCss;
import net.sprd.gwt.shared.dom.GwtuDom;
import net.sprd.gwt.shared.dom.IdService;

public class GwtuDomClient extends GwtuDom {
    
    private static List<String> excludedDebugClasses;
    
    public static List<String> getExcludedDebugClasses() {
        return excludedDebugClasses;
    }

    public static void setExcludedDebugClasses(List<String> excludedDebugClasses) {
        GwtuDomClient.excludedDebugClasses = excludedDebugClasses;
    }

    private boolean initial = true;
    private String rootId;
    private String idPrefix="";
    
    private IdService idService = new IdService();
    
    public GwtuDomClient() {
    }
    
    public < T extends HTMLElement> T create(String tag, String id, HTMLElement parent, Element nextSibling, Element previousSibling, String... attributes) {
        if (isInitialPhase()) {
            id = idService.getId((id==null || id.isEmpty())?idPrefix+getTagId(tag): id);
        }
        T element = createNative(tag, id, parent, nextSibling, previousSibling, attributes);
        return element;
    }

    
    protected < T extends HTMLElement> T createNative(String tag, String id, HTMLElement parent, Element nextSibling, Element previousSibling, String... attributes) {
        T element = (id==null || id.isEmpty())?null:(T)DomGlobal.document.getElementById(id);
        if (element == null || !isInitialPhase()) {
            boolean withId = (id!= null && !id.isEmpty() && element == null);
            element = (T)DomGlobal.document.createElement(tag);
            if (withId) {
                element.id = id;
            }
            addAttributes(element, attributes);
            if (parent == null) {
                if (rootId == null) {
                    parent = DomGlobal.document.body;
                } else {
                    parent = (HTMLElement) DomGlobal.document.getElementById(rootId);
                }
            }
            if (nextSibling != null) {
                parent.insertBefore(element, nextSibling);
            } else if (previousSibling != null && previousSibling.nextElementSibling!= null ) {
                parent.insertBefore(element, previousSibling.nextElementSibling);
            } else {
                parent.appendChild(element);
            }
        } else if (DomGlobal.location.getSearch().contains("gwtu=debug") && element!= null && isInitialPhase()) {
            T element2 = (T)DomGlobal.document.createElement(tag);
            element2.id = id;
            addAttributes(element2, attributes);
            compareElements(element, element2);
        }
        return element;
    }


    private void compareElements(HTMLElement element, HTMLElement element2) {
        if (!element.tagName.equals(element2.tagName)) {
            throw new RuntimeException("tagname should be "+element2.tagName+" of "+element.id);
        }
        JsPropertyMap<Object> map1 = Js.asPropertyMap(element);
        JsPropertyMap<Object> map2 = Js.asPropertyMap(element2);
//        map1.forEach(key -> {
//            if (!map2.has(key) || !map1.get(key).equals(map2.get(key))) {
//                throw new RuntimeException("key should be not "+map1.get(key)+" of "+element.id);
//            }
//        });
        map2.forEach(key -> {
            if (!map1.has(key)) {
                throw new RuntimeException("attribute "+key+" should be "+map2.get(key)+" of element with id="+element.id);
            }
        });
        if (element2.classList != null && element2.classList.length>0) {
            for (int i=0;i<element2.classList.length;i++) {
                String clazz = element2.classList.getAt(i);
                if ((excludedDebugClasses == null || !excludedDebugClasses.contains(clazz))  
                        && !element.classList.contains(clazz)) {
                    throw new RuntimeException("element with id="+element.id+ " should contain class "+clazz);
                }
            }
        }
    }

    @Override
    public boolean isServer() {
        return false;
    }


    @Override
    public boolean isInitialPhase() {
        return initial;
    }
    
    public void setInitialPhase(boolean initial) {
        this.initial=initial;
    }

    @Override
    public void setRootId(String rootId) {
        this.rootId = rootId;
    }
    
    @Override
    public void setIdPrefix(String idPrefix) {
        if (idPrefix == null) {
            this.idPrefix = "";
        } else {
            this.idPrefix = idPrefix;
        }
    }

    @Override
    public void setAttribute(Element element, String name, String value) {
        element.setAttribute(name, value);
    }

    @Override
    public GwtuCss getGwtuCss() {
        return GwtuCssClient.getInstance();
    }
    
}
