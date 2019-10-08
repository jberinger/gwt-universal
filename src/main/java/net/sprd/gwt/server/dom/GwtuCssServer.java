package net.sprd.gwt.server.dom;

import elemental2.dom.CSSProperties.HeightUnionType;
import elemental2.dom.CSSProperties.MarginTopUnionType;
import elemental2.dom.CSSProperties.MarginUnionType;
import elemental2.dom.CSSProperties.MaxHeightUnionType;
import elemental2.dom.CSSProperties.MaxWidthUnionType;
import elemental2.dom.CSSProperties.WidthUnionType;
import net.sprd.gwt.shared.dom.GwtuCss;

public class GwtuCssServer implements GwtuCss {

    private static GwtuCssServer INSTANCE = new GwtuCssServer();

    public static GwtuCssServer getInstance() {
        return INSTANCE;
    }
    
    private GwtuCssServer() {
        
    }

    public static class StringUnionType {

        private String value;

        public StringUnionType(String value) {
            this.value = value;
        }

        public double asDouble() {
            throw new UnsupportedOperationException("is String");
        }

        public String asString() {
            return value;
        }

        public boolean isDouble() {
            return false;
        }

        public boolean isString() {
            return true;
        }

    }

    public static class DoubleUnionType {

        private double value;

        public DoubleUnionType(double value) {
            this.value = value;
        }

        public double asDouble() {
            return value;
        }

        public String asString() {
            throw new UnsupportedOperationException("is double");
        }

        public boolean isDouble() {
            return true;
        }

        public boolean isString() {
            return false;
        }
    }

    public class StringHeightUnionType extends StringUnionType implements HeightUnionType {
        public StringHeightUnionType(String value) {
            super(value);
        }
    }
    
    public class StringWidthUnionType extends StringUnionType implements WidthUnionType {
        public StringWidthUnionType(String value) {
            super(value);
        }
    }
    
    public class StringMaxHeightUnionType extends StringUnionType implements MaxHeightUnionType {
        public StringMaxHeightUnionType(String value) {
            super(value);
        }
    }
    
    public class StringMaxWidthUnionType extends StringUnionType implements MaxWidthUnionType {
        public StringMaxWidthUnionType(String value) {
            super(value);
        }
    }
    
    public class StringMarginUnionType extends StringUnionType implements MarginUnionType {
        public StringMarginUnionType(String value) {
            super(value);
        }
    }
    
    public class StringMarginTopUnionType extends StringUnionType implements MarginTopUnionType {
        public StringMarginTopUnionType(String value) {
            super(value);
        }
    }

    @Override
    public HeightUnionType height(final String height) {
        return new StringHeightUnionType(height);
    }
    
    @Override
    public WidthUnionType width(String width) {
        return new StringWidthUnionType(width);
    }

    @Override
    public MarginUnionType margin(String margin) {
        return new StringMarginUnionType(margin);
    }

    @Override
    public MaxHeightUnionType maxHeight(String maxHeight) {
        return new StringMaxHeightUnionType(maxHeight);
    }

    @Override
    public MaxWidthUnionType maxWidth(String maxWidth) {
        return new StringMaxWidthUnionType(maxWidth);
    }

    @Override
    public MarginTopUnionType marginTop(String marginTop) {
        return new StringMarginTopUnionType(marginTop);
    }

   

}
