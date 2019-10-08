package net.sprd.gwt.server.dom.element;

import java.util.ArrayList;
import java.util.List;

import elemental2.core.JsIteratorIterable;
import elemental2.dom.DOMTokenList;
import elemental2.dom.HTMLElement;
import jsinterop.base.Any;

public class ServerElementClassList extends DOMTokenList {

    protected HTMLElement element;
    
    public ServerElementClassList(HTMLElement element) {
        this.element = element;
    }
    
    protected List<String> getList() {
        String[] classArray = element.className!=null?element.className.split(" "):new String[0];
        List<String> classList = new ArrayList<>();
        for(String className: classArray) {
            className = className.trim();
            if (!className.isEmpty()) {
                classList.add(className);
            }
        }
        return classList;
    }
    
    protected void setList(List<String> classList) {
        element.className = classList!=null?String.join(" ", classList):"";
    }

    public void add(String... var_args) {
        if (var_args != null) {
            List<String> classList = getList();
            for(String value: var_args) {
                classList.add(value);
            }
            setList(classList);
        }
    }

    public boolean contains(String token) {
        List<String> classList = getList();
        return classList.contains(token);
    }

    public String item(double index) {
        return getList().get((int)index);
    }

    public void remove(String... var_args) {
        if (var_args != null) {
            List<String> classList = getList();
            for(String value: var_args) {
                classList.remove(value);
            }
            setList(classList);
        }
    }

    public String toString_() {
        return element.className;
    }

    public boolean toggle(String token, boolean force) {
        boolean toggleResult=false;
        List<String> classList = getList();
        if (classList.contains(token)) {
            if (!force) {
                classList.remove(token);
                toggleResult= false;
            } else {
                toggleResult= true;
            }
        } else {
            if (force) {
                classList.add(token);
                toggleResult= true;
            } else {
                toggleResult= false;
            }
        }
        setList(classList);
        return toggleResult;
    }

    public boolean toggle(String token) {
        boolean toggleResult=false;
        List<String> classList = getList();
        if (classList.contains(token)) {
            classList.remove(token);
            toggleResult= false;
        } else {
            classList.add(token);
            toggleResult= true;
        }
        setList(classList);
        return toggleResult;
    }

    public JsIteratorIterable<String> values() {
        return new ServerIteratorIterable<String>(getList());
    }

    public int getLength() {
        return getList().size();
    }

    public void setLength(int length) {
    }

    public String getAt(int index) {
        return item(index);
    }

    public Any getAnyAt(int index) {
        return null;
    }

    public void setAt(int index, String value) {
        List<String> classList = getList();
        classList.set(index, value);
        setList(classList);
    }

    public List<String> asList() {
        List<String> clone = getList();
        return clone;
    }

}
