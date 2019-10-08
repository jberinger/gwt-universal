package net.sprd.gwt.server.dom.element;

import elemental2.core.JsIIterableResult;

public class ServerIterableResult<T> implements JsIIterableResult<T> {
    
    private ServerIteratorIterable<T> iterator;
    private int pos;

    public ServerIterableResult(ServerIteratorIterable<T> iterator, int pos) {
        this.iterator = iterator;
        this.pos = pos;
    }

    @Override
    public T getValue() {
        return pos<iterator.list().size()?iterator.list().get(pos):null;
    }

    @Override
    public boolean isDone() {
        return pos+1<iterator.list().size();
    }

    @Override
    public void setDone(boolean done) {
    }

    @Override
    public void setValue(T value) {
        if (pos<iterator.list().size()) {
            iterator.list().set(pos, value);
        }
    }

}
