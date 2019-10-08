package net.sprd.gwt.server.dom.element;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.helper.StringUtil;

import elemental2.core.JsIteratorIterable;
import elemental2.dom.DOMTokenList;
import jsinterop.base.Any;

public class ServerDOMTokenList extends DOMTokenList {

    List<String> values;
    
    public ServerDOMTokenList() {
        values = new ArrayList<>();
    }

    public void add(String... var_args) {
        if (var_args != null) {
            for(String value: var_args) {
                values.add(value);
            }
        }
    }

    public boolean contains(String token) {
        return values.contains(token);
    }

    public String item(double index) {
        return values.get((int)index);
    }

    public void remove(String... var_args) {
        if (var_args != null) {
            for(String value: var_args) {
                values.remove(value);
            }
        }
    }

    public String toString_() {
        return StringUtil.join(values, " ");
    }

    public boolean toggle(String token, boolean force) {
        if (values.contains(token)) {
            if (!force) {
                values.remove(token);
                return false;
            } else {
                return true;
            }
        } else {
            if (force) {
                values.add(token);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean toggle(String token) {
        if (values.contains(token)) {
            values.remove(token);
            return false;
        } else {
            values.add(token);
            return true;
        }
    }

    public JsIteratorIterable<String> values() {
        return new ServerIteratorIterable<String>(values);
    }

    public int getLength() {
        return values.size();
    }

    public void setLength(int length) {
    }

    public String getAt(int index) {
        return values.get(index);
    }

    public Any getAnyAt(int index) {
        return null;
    }

    public void setAt(int index, String value) {
        values.set(index, value);
    }

    public List<String> asList() {
        List<String> clone = new ArrayList();
        clone.addAll(values);
        return clone;
    }

}
