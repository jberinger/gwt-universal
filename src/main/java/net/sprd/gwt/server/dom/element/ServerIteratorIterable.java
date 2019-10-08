package net.sprd.gwt.server.dom.element;

import java.util.List;

import elemental2.core.JsIIterableResult;
import elemental2.core.JsIteratorIterable;

public class ServerIteratorIterable<T> implements JsIteratorIterable<T> {
    
    private List<T> list;
    private int pos = -1;

    public ServerIteratorIterable(List<T> list) {
        this.list=list;
    }
    
    protected List<T> list() {
        return list;
    }

    @Override
    public JsIIterableResult<T> next() {
        pos++;
        return list.size()>pos?new ServerIterableResult<T>(this, pos):null;
    }

    @Override
    public JsIIterableResult<T> next(T p0) {
        return next();
    }

}
