package net.sprd.gwt.server.dom;

public interface Function<T,U> {
    U apply(T value);
}
