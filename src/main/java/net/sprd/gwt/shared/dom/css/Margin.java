package net.sprd.gwt.shared.dom.css;

import elemental2.dom.CSSProperties;
import jsinterop.annotations.JsOverlay;
import jsinterop.base.Js;

public class Margin {

    public static class StringMargin implements CSSProperties.MarginUnionType {

        @JsOverlay
        public double asDouble() {
            return Js.asDouble(this);
        }

        @JsOverlay
        public String asString() {
            return Js.asString(this);
        }

        @JsOverlay
        public boolean isDouble() {
            return (Object) this instanceof Double;
        }

        @JsOverlay
        public boolean isString() {
            return (Object) this instanceof String;
        }
    }

}
