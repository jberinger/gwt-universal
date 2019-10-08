package net.sprd.gwt.client.dom;

import elemental2.dom.CSSProperties.HeightUnionType;
import elemental2.dom.CSSProperties.MarginTopUnionType;
import elemental2.dom.CSSProperties.MarginUnionType;
import elemental2.dom.CSSProperties.MaxHeightUnionType;
import elemental2.dom.CSSProperties.MaxWidthUnionType;
import elemental2.dom.CSSProperties.WidthUnionType;
import net.sprd.gwt.shared.dom.GwtuCss;

public class GwtuCssClient implements GwtuCss {

    private static GwtuCssClient INSTANCE = new GwtuCssClient();

    protected static GwtuCssClient getInstance() {
        return INSTANCE;
    }

    private GwtuCssClient() {
    }

    @Override
    public HeightUnionType height(String height) {
        return HeightUnionType.of(height);
    }

    @Override
    public WidthUnionType width(String width) {
        return WidthUnionType.of(width);
    }

    @Override
    public MarginUnionType margin(String margin) {
        return MarginUnionType.of(margin);
    }

    @Override
    public MaxHeightUnionType maxHeight(String maxHeight) {
        return MaxHeightUnionType.of(maxHeight);
    }

    @Override
    public MaxWidthUnionType maxWidth(String maxWidth) {
        return MaxWidthUnionType.of(maxWidth);
    }

    @Override
    public MarginTopUnionType marginTop(String marginTop) {
        return MarginTopUnionType.of(marginTop);
    }

}
